/***************************************************************************
 * Copyright (c) 2004 - 2008 Eike Stepper, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 **************************************************************************/
package org.eclipse.net4j.buddies.internal.server;

import org.eclipse.net4j.buddies.common.IAccount;
import org.eclipse.net4j.buddies.common.IBuddy;
import org.eclipse.net4j.buddies.common.IBuddyStateEvent;
import org.eclipse.net4j.buddies.common.ICollaboration;
import org.eclipse.net4j.buddies.common.ISession;
import org.eclipse.net4j.buddies.internal.common.Account;
import org.eclipse.net4j.buddies.internal.common.Buddy;
import org.eclipse.net4j.buddies.internal.common.Collaboration;
import org.eclipse.net4j.buddies.internal.common.CollaborationContainer;
import org.eclipse.net4j.buddies.internal.common.Membership;
import org.eclipse.net4j.buddies.internal.common.protocol.BuddyStateNotification;
import org.eclipse.net4j.buddies.internal.server.bundle.OM;
import org.eclipse.net4j.buddies.internal.server.protocol.BuddyRemovedNotification;
import org.eclipse.net4j.buddies.internal.server.protocol.CollaborationInitiatedNotification;
import org.eclipse.net4j.buddies.server.IBuddyAdmin;
import org.eclipse.net4j.buddies.spi.common.ServerFacilityFactory;
import org.eclipse.net4j.channel.IChannel;
import org.eclipse.net4j.protocol.IProtocol;
import org.eclipse.net4j.util.ObjectUtil;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.event.IEvent;
import org.eclipse.net4j.util.event.IListener;
import org.eclipse.net4j.util.lifecycle.ILifecycleEvent;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.trace.ContextTracer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Eike Stepper
 */
public class BuddyAdmin extends CollaborationContainer implements IBuddyAdmin, IListener
{
  public static final BuddyAdmin INSTANCE = new BuddyAdmin();

  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG, BuddyAdmin.class);

  private ConcurrentMap<String, IAccount> accounts = new ConcurrentHashMap<String, IAccount>();

  private ConcurrentMap<String, ISession> sessions = new ConcurrentHashMap<String, ISession>();

  private long lastCollaborationID;

  public BuddyAdmin()
  {
    activate();
  }

  public Map<String, IAccount> getAccounts()
  {
    return accounts;
  }

  public ISession getSession(IBuddy buddy)
  {
    return getSession(buddy.getUserID());
  }

  public ISession getSession(String userID)
  {
    return sessions.get(userID);
  }

  public ISession[] getSessions()
  {
    return sessions.values().toArray(new ISession[sessions.size()]);
  }

  public IBuddy[] getBuddies()
  {
    List<IBuddy> buddies = new ArrayList<IBuddy>();
    for (ISession session : sessions.values())
    {
      buddies.add(session.getSelf());
    }

    return buddies.toArray(new IBuddy[buddies.size()]);
  }

  public IBuddy getBuddy(String userID)
  {
    ISession session = getSession(userID);
    if (session == null)
    {
      return null;
    }

    return session.getSelf();
  }

  public synchronized ISession openSession(IChannel channel, String userID, String password, String[] facilityTypes)
  {
    if (sessions.containsKey(userID))
    {
      return null;
    }

    IAccount account = accounts.get(userID);
    if (account != null)
    {
      if (!account.authenticate(password))
      {
        return null;
      }
    }
    else
    {
      account = new Account(userID, password);
      accounts.put(userID, account);
    }

    ServerBuddy buddy = new ServerBuddy(account, facilityTypes);
    buddy.activate();
    buddy.addListener(this);

    ServerSession session = new ServerSession(channel, buddy);
    ((IProtocol)channel.getReceiveHandler()).setInfraStructure(session);
    session.addListener(this);
    buddy.setSession(session);
    LifecycleUtil.activate(session);

    if (TRACER.isEnabled())
    {
      TRACER.trace("Opened session: " + userID);
    }
    sessions.put(userID, session);
    return session;
  }

  public ICollaboration initiateCollaboration(IBuddy initiator, String... userIDs)
  {
    long collaborationID;
    synchronized (this)
    {
      collaborationID = ++lastCollaborationID;
    }

    Collaboration collaboration = new Collaboration(collaborationID);
    collaboration.activate();
    Membership.create(initiator, collaboration);

    Set<IBuddy> buddies = new HashSet<IBuddy>();
    buddies.add(initiator);
    for (String userID : userIDs)
    {
      Buddy buddy = (Buddy)getBuddy(userID);
      if (buddy != null)
      {
        buddies.add(buddy);
        Membership.create(buddy, collaboration);
      }
    }

    addCollaboration(collaboration);

    Set<IBuddy> invitations = new HashSet<IBuddy>(buddies);
    for (IBuddy buddy : buddies)
    {
      if (buddy != initiator)
      {
        try
        {
          invitations.remove(buddy);
          IChannel channel = buddy.getSession().getChannel();
          new CollaborationInitiatedNotification(channel, collaborationID, invitations, null).send();
        }
        catch (Exception ex)
        {
          OM.LOG.error(ex);
        }
        finally
        {
          invitations.add(buddy);
        }
      }
    }

    return collaboration;
  }

  @Override
  public void notifyEvent(IEvent event)
  {
    if (event.getSource() instanceof ServerSession)
    {
      if (event instanceof ILifecycleEvent)
      {
        if (((ILifecycleEvent)event).getKind() == ILifecycleEvent.Kind.DEACTIVATED)
        {
          String userID = ((ServerSession)event.getSource()).getSelf().getUserID();
          synchronized (this)
          {
            ServerSession removed = (ServerSession)sessions.remove(userID);
            if (removed != null)
            {
              removed.removeListener(this);
              removed.getSelf().removeListener(this);
              for (ISession session : sessions.values())
              {
                try
                {
                  new BuddyRemovedNotification(session.getChannel(), userID).send();
                }
                catch (Exception ex)
                {
                  OM.LOG.error(ex);
                }
              }
            }
          }
        }
      }
    }
    else if (event.getSource() instanceof ServerBuddy)
    {
      if (event instanceof IBuddyStateEvent)
      {
        IBuddyStateEvent e = (IBuddyStateEvent)event;
        synchronized (this)
        {
          for (ISession session : sessions.values())
          {
            try
            {
              if (!ObjectUtil.equals(session.getSelf(), e.getBuddy()))
              {
                new BuddyStateNotification(session.getChannel(), e.getBuddy().getUserID(), e.getNewState()).send();
              }
            }
            catch (Exception ex)
            {
              OM.LOG.error(ex);
            }
          }
        }
      }
    }
  }

  public static Set<String> getFacilityTypes()
  {
    return IPluginContainer.INSTANCE.getFactoryTypes(ServerFacilityFactory.PRODUCT_GROUP);
  }
}
