/*
 * Copyright (c) 2009-2013 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Simon McDuff - initial API and implementation
 *    Eike Stepper - maintenance
 *    Simon McDuff - bug 204890
 */
package org.eclipse.emf.internal.cdo.transaction;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.branch.CDOBranchVersion;
import org.eclipse.emf.cdo.common.commit.CDOChangeSetData;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.common.revision.CDOIDAndVersion;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.CDORevisionKey;
import org.eclipse.emf.cdo.common.revision.delta.CDOFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDORevisionDelta;
import org.eclipse.emf.cdo.internal.common.commit.CDOChangeSetDataImpl;
import org.eclipse.emf.cdo.internal.common.revision.delta.CDORevisionDeltaImpl;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDOFeatureDelta;

import org.eclipse.net4j.util.collection.MultiMap;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;

import org.eclipse.emf.spi.cdo.CDOTransactionStrategy;
import org.eclipse.emf.spi.cdo.InternalCDOSavepoint;
import org.eclipse.emf.spi.cdo.InternalCDOTransaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Simon McDuff
 * @since 2.0
 */
public class CDOSavepointImpl extends CDOUserSavepointImpl implements InternalCDOSavepoint
{
  private final InternalCDOTransaction transaction;

  private Map<CDOID, CDORevision> baseNewObjects = CDOIDUtil.createMap();

  private Map<CDOID, CDOObject> newObjects = CDOIDUtil.createMap();

  // Bug 283985 (Re-attachment)
  private Map<CDOID, CDOObject> reattachedObjects = CDOIDUtil.createMap();

  private Map<CDOID, CDOObject> detachedObjects = new HashMap<CDOID, CDOObject>()
  {
    private static final long serialVersionUID = 1L;

    @Override
    public CDOObject put(CDOID key, CDOObject object)
    {
      synchronized (transaction)
      {
        baseNewObjects.remove(key);
        newObjects.remove(key);
        reattachedObjects.remove(key);
        dirtyObjects.remove(key);
        revisionDeltas.remove(key);
        return super.put(key, object);
      }
    }
  };

  private Map<CDOID, CDOObject> dirtyObjects = CDOIDUtil.createMap();

  private Map<CDOID, CDORevisionDelta> revisionDeltas = new HashMap<CDOID, CDORevisionDelta>()
  {
    private static final long serialVersionUID = 1L;

    @Override
    public CDORevisionDelta put(CDOID id, CDORevisionDelta delta)
    {
      transaction.clearResourcePathCacheIfNecessary(delta);
      return super.put(id, delta);
    }

    @Override
    public void putAll(Map<? extends CDOID, ? extends CDORevisionDelta> m)
    {
      throw new UnsupportedOperationException();
    }
  };

  private boolean wasDirty;

  public CDOSavepointImpl(InternalCDOTransaction transaction, InternalCDOSavepoint lastSavepoint)
  {
    super(transaction, lastSavepoint);
    this.transaction = transaction;
    wasDirty = transaction.isDirty();
  }

  @Override
  public InternalCDOTransaction getTransaction()
  {
    return (InternalCDOTransaction)super.getTransaction();
  }

  @Override
  public InternalCDOSavepoint getFirstSavePoint()
  {
    synchronized (transaction)
    {
      return (InternalCDOSavepoint)super.getFirstSavePoint();
    }
  }

  @Override
  public InternalCDOSavepoint getPreviousSavepoint()
  {
    synchronized (transaction)
    {
      return (InternalCDOSavepoint)super.getPreviousSavepoint();
    }
  }

  @Override
  public InternalCDOSavepoint getNextSavepoint()
  {
    synchronized (transaction)
    {
      return (InternalCDOSavepoint)super.getNextSavepoint();
    }
  }

  public void clear()
  {
    synchronized (transaction)
    {
      newObjects.clear();
      dirtyObjects.clear();
      revisionDeltas.clear();
      baseNewObjects.clear();
      detachedObjects.clear();
      reattachedObjects.clear();
    }
  }

  public boolean wasDirty()
  {
    return wasDirty;
  }

  public Map<CDOID, CDOObject> getNewObjects()
  {
    return newObjects;
  }

  public Map<CDOID, CDOObject> getDetachedObjects()
  {
    return detachedObjects;
  }

  // Bug 283985 (Re-attachment)
  public Map<CDOID, CDOObject> getReattachedObjects()
  {
    return reattachedObjects;
  }

  public Map<CDOID, CDOObject> getDirtyObjects()
  {
    return dirtyObjects;
  }

  @Deprecated
  public Set<CDOID> getSharedDetachedObjects()
  {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  public void recalculateSharedDetachedObjects()
  {
    throw new UnsupportedOperationException();
  }

  @Deprecated
  public ConcurrentMap<CDOID, CDORevisionDelta> getRevisionDeltas()
  {
    return new ConcurrentMap<CDOID, CDORevisionDelta>()
    {
      public int size()
      {
        return revisionDeltas.size();
      }

      public boolean isEmpty()
      {
        return revisionDeltas.isEmpty();
      }

      public boolean containsKey(Object key)
      {
        return revisionDeltas.containsKey(key);
      }

      public boolean containsValue(Object value)
      {
        return revisionDeltas.containsValue(value);
      }

      public CDORevisionDelta get(Object key)
      {
        return revisionDeltas.get(key);
      }

      public CDORevisionDelta put(CDOID key, CDORevisionDelta value)
      {
        return revisionDeltas.put(key, value);
      }

      public CDORevisionDelta remove(Object key)
      {
        return revisionDeltas.remove(key);
      }

      public void putAll(Map<? extends CDOID, ? extends CDORevisionDelta> m)
      {
        revisionDeltas.putAll(m);
      }

      public void clear()
      {
        revisionDeltas.clear();
      }

      public Set<CDOID> keySet()
      {
        return revisionDeltas.keySet();
      }

      public Collection<CDORevisionDelta> values()
      {
        return revisionDeltas.values();
      }

      public Set<java.util.Map.Entry<CDOID, CDORevisionDelta>> entrySet()
      {
        return revisionDeltas.entrySet();
      }

      @Override
      public boolean equals(Object o)
      {
        return revisionDeltas.equals(o);
      }

      @Override
      public int hashCode()
      {
        return revisionDeltas.hashCode();
      }

      public CDORevisionDelta putIfAbsent(CDOID key, CDORevisionDelta value)
      {
        return null;
      }

      public boolean remove(Object key, Object value)
      {
        return false;
      }

      public boolean replace(CDOID key, CDORevisionDelta oldValue, CDORevisionDelta newValue)
      {
        return false;
      }

      public CDORevisionDelta replace(CDOID key, CDORevisionDelta value)
      {
        return null;
      }
    };
  }

  public Map<CDOID, CDORevisionDelta> getRevisionDeltas2()
  {
    return revisionDeltas;
  }

  public CDOChangeSetData getChangeSetData()
  {
    synchronized (transaction)
    {
      return createChangeSetData(newObjects, revisionDeltas, detachedObjects);
    }
  }

  public CDOChangeSetData getAllChangeSetData()
  {
    synchronized (transaction)
    {
      return createChangeSetData(getAllNewObjects(), getAllRevisionDeltas(), getAllDetachedObjects());
    }
  }

  private CDOChangeSetData createChangeSetData(Map<CDOID, CDOObject> newObjects,
      Map<CDOID, CDORevisionDelta> revisionDeltas, Map<CDOID, CDOObject> detachedObjects)
  {
    List<CDOIDAndVersion> newList = new ArrayList<CDOIDAndVersion>(newObjects.size());
    for (CDOObject object : newObjects.values())
    {
      newList.add(object.cdoRevision());
    }

    List<CDORevisionKey> changedList = new ArrayList<CDORevisionKey>(revisionDeltas.size());
    for (CDORevisionDelta delta : revisionDeltas.values())
    {
      changedList.add(delta);
    }

    List<CDOIDAndVersion> detachedList = new ArrayList<CDOIDAndVersion>(detachedObjects.size());
    for (CDOID id : detachedObjects.keySet())
    {
      detachedList.add(CDOIDUtil.createIDAndVersion(id, CDOBranchVersion.UNSPECIFIED_VERSION));
    }

    return new CDOChangeSetDataImpl(newList, changedList, detachedList);
  }

  public Map<CDOID, CDORevision> getBaseNewObjects()
  {
    return baseNewObjects;
  }

  /**
   * Return the list of new objects from this point.
   */
  public Map<CDOID, CDOObject> getAllDirtyObjects()
  {
    synchronized (transaction)
    {
      if (getPreviousSavepoint() == null)
      {
        return Collections.unmodifiableMap(getDirtyObjects());
      }

      MultiMap.ListBased<CDOID, CDOObject> dirtyObjects = new MultiMap.ListBased<CDOID, CDOObject>();
      for (InternalCDOSavepoint savepoint = this; savepoint != null; savepoint = savepoint.getPreviousSavepoint())
      {
        dirtyObjects.getDelegates().add(savepoint.getDirtyObjects());
      }

      return dirtyObjects;
    }
  }

  /**
   * Return the list of new objects from this point without objects that are removed.
   */
  public Map<CDOID, CDOObject> getAllNewObjects()
  {
    synchronized (transaction)
    {
      if (getPreviousSavepoint() == null)
      {
        return Collections.unmodifiableMap(getNewObjects());
      }

      Map<CDOID, CDOObject> newObjects = CDOIDUtil.createMap();
      for (InternalCDOSavepoint savepoint = getFirstSavePoint(); savepoint != null; savepoint = savepoint
          .getNextSavepoint())
      {
        newObjects.putAll(savepoint.getNewObjects());
        for (CDOID removedID : savepoint.getDetachedObjects().keySet())
        {
          newObjects.remove(removedID);
        }
      }

      return newObjects;
    }
  }

  /**
   * @since 2.0
   */
  public Map<CDOID, CDORevision> getAllBaseNewObjects()
  {
    synchronized (transaction)
    {
      if (getPreviousSavepoint() == null)
      {
        return Collections.unmodifiableMap(getBaseNewObjects());
      }

      MultiMap.ListBased<CDOID, CDORevision> newObjects = new MultiMap.ListBased<CDOID, CDORevision>();
      for (InternalCDOSavepoint savepoint = this; savepoint != null; savepoint = savepoint.getPreviousSavepoint())
      {
        newObjects.getDelegates().add(savepoint.getBaseNewObjects());
      }

      return newObjects;
    }
  }

  /**
   * Return the list of all deltas without objects that are removed.
   */
  public Map<CDOID, CDORevisionDelta> getAllRevisionDeltas()
  {
    synchronized (transaction)
    {
      if (getPreviousSavepoint() == null)
      {
        return Collections.unmodifiableMap(getRevisionDeltas2());
      }

      // We need to combined the result for all delta in different Savepoint
      Map<CDOID, CDORevisionDelta> allRevisionDeltas = CDOIDUtil.createMap();
      for (InternalCDOSavepoint savepoint = getFirstSavePoint(); savepoint != null; savepoint = savepoint
          .getNextSavepoint())
      {
        for (CDORevisionDelta revisionDelta : savepoint.getRevisionDeltas2().values())
        {
          CDOID id = revisionDelta.getID();
          if (!isNewObject(id))
          {
            CDORevisionDeltaImpl oldRevisionDelta = (CDORevisionDeltaImpl)allRevisionDeltas.get(id);
            if (oldRevisionDelta == null)
            {
              allRevisionDeltas.put(id, revisionDelta.copy());
            }
            else
            {
              for (CDOFeatureDelta delta : revisionDelta.getFeatureDeltas())
              {
                CDOFeatureDelta copy = ((InternalCDOFeatureDelta)delta).copy();
                oldRevisionDelta.addFeatureDelta(copy, null);
              }
            }
          }
        }

        Set<CDOID> reattachedObjects = savepoint.getReattachedObjects().keySet();
        for (CDOID detachedID : savepoint.getDetachedObjects().keySet())
        {
          if (!reattachedObjects.contains(detachedID))
          {
            allRevisionDeltas.remove(detachedID);
          }
        }
      }

      return Collections.unmodifiableMap(allRevisionDeltas);
    }
  }

  public Map<CDOID, CDOObject> getAllDetachedObjects()
  {
    synchronized (transaction)
    {
      if (getPreviousSavepoint() == null && reattachedObjects.isEmpty())
      {
        return Collections.unmodifiableMap(getDetachedObjects());
      }

      Map<CDOID, CDOObject> detachedObjects = CDOIDUtil.createMap();
      for (InternalCDOSavepoint savepoint = getFirstSavePoint(); savepoint != null; savepoint = savepoint
          .getNextSavepoint())
      {
        for (Entry<CDOID, CDOObject> entry : savepoint.getDetachedObjects().entrySet())
        {
          CDOID detachedID = entry.getKey();
          if (!isNewObject(detachedID))
          {
            CDOObject detachedObject = entry.getValue();
            detachedObjects.put(detachedID, detachedObject);
          }
        }

        for (CDOID reattachedID : savepoint.getReattachedObjects().keySet())
        {
          detachedObjects.remove(reattachedID);
        }
      }

      return detachedObjects;
    }
  }

  public boolean isNewObject(CDOID id)
  {
    if (id.isTemporary())
    {
      return true;
    }

    synchronized (transaction)
    {
      for (InternalCDOSavepoint savepoint = this; savepoint != null; savepoint = savepoint.getPreviousSavepoint())
      {
        if (savepoint.getNewObjects().containsKey(id))
        {
          return true;
        }
      }
    }

    return false;
  }

  // TODO Not sure if this new implementation is needed. The existing one passes all tests.
  // public boolean isNewObject(CDOID id)
  // {
  // if (id.isTemporary())
  // {
  // return true;
  // }
  //
  // boolean isNew = false;
  // boolean wasNew = false;
  // synchronized (transaction)
  // {
  // for (InternalCDOSavepoint savepoint = this; savepoint != null; savepoint = savepoint.getPreviousSavepoint())
  // {
  // if (savepoint.getNewObjects().containsKey(id))
  // {
  // isNew = true;
  // wasNew = true;
  // }
  //
  // if (isNew && savepoint.getDetachedObjects().containsKey(id))
  // {
  // isNew = false;
  // }
  //
  // if (!isNew && wasNew && savepoint.getReattachedObjects().containsKey(id))
  // {
  // isNew = true;
  // }
  // }
  // }
  //
  // return isNew;
  // }

  public void rollback()
  {
    synchronized (transaction)
    {
      InternalCDOTransaction transaction = getTransaction();
      LifecycleUtil.checkActive(transaction);

      CDOTransactionStrategy transactionStrategy = transaction.getTransactionStrategy();
      transactionStrategy.rollback(transaction, this);
    }
  }
}
