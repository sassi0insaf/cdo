/*
 * Copyright (c) 2009, 2011, 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.server.internal.net4j.protocol;

import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;
import org.eclipse.emf.cdo.common.util.NotAuthenticatedException;

import org.eclipse.net4j.signal.RemoteException;
import org.eclipse.net4j.signal.RequestWithMonitoring;
import org.eclipse.net4j.util.io.ExtendedDataInputStream;
import org.eclipse.net4j.util.io.ExtendedDataOutputStream;
import org.eclipse.net4j.util.om.monitor.OMMonitor;
import org.eclipse.net4j.util.security.DiffieHellman.Client.Response;
import org.eclipse.net4j.util.security.DiffieHellman.Server.Challenge;

/**
 * @author Eike Stepper
 */
public class AuthenticationRequest extends RequestWithMonitoring<Response>
{
  private Challenge challenge;

  public AuthenticationRequest(CDOServerProtocol protocol, Challenge challenge)
  {
    super(protocol, CDOProtocolConstants.SIGNAL_AUTHENTICATION);
    this.challenge = challenge;
  }

  @Override
  protected void requesting(ExtendedDataOutputStream out, OMMonitor monitor) throws Exception
  {
    challenge.write(out);
  }

  @Override
  protected Response confirming(ExtendedDataInputStream in, OMMonitor monitor) throws Exception
  {
    try
    {
      if (in.readBoolean())
      {
        return new Response(in);
      }
    }
    catch (RemoteException ex)
    {
      if (ex.getCause() instanceof NotAuthenticatedException)
      {
        // Skip silently because user has canceled the authentication
      }
      else
      {
        throw ex;
      }
    }
    catch (Exception ex)
    {
      if (ex instanceof NotAuthenticatedException)
      {
        // Skip silently because user has canceled the authentication
      }
      else
      {
        throw ex;
      }
    }

    return null;
  }
}
