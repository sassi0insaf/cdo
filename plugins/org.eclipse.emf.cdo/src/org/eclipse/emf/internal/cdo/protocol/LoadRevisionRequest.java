/***************************************************************************
 * Copyright (c) 2004 - 2007 Eike Stepper, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 **************************************************************************/
package org.eclipse.emf.internal.cdo.protocol;

import org.eclipse.emf.cdo.internal.protocol.CDOIDImpl;
import org.eclipse.emf.cdo.internal.protocol.revision.CDORevisionImpl;
import org.eclipse.emf.cdo.protocol.CDOID;
import org.eclipse.emf.cdo.protocol.CDOProtocolConstants;

import org.eclipse.net4j.IChannel;
import org.eclipse.net4j.internal.util.om.trace.ContextTracer;
import org.eclipse.net4j.util.io.ExtendedDataInputStream;
import org.eclipse.net4j.util.io.ExtendedDataOutputStream;

import org.eclipse.emf.internal.cdo.bundle.OM;

import java.io.IOException;

/**
 * @author Eike Stepper
 */
public class LoadRevisionRequest extends CDOClientRequest<CDORevisionImpl>
{
  private static final ContextTracer PROTOCOL = new ContextTracer(OM.DEBUG_PROTOCOL, LoadRevisionRequest.class);

  private CDOID id;

  private Long timeStamp;

  private int referenceChunk;

  public LoadRevisionRequest(IChannel channel, CDOID id, int referenceChunk)
  {
    super(channel, CDOProtocolConstants.SIGNAL_LOAD_REVISION);
    this.id = id;
    this.referenceChunk = referenceChunk;
  }

  public LoadRevisionRequest(IChannel channel, CDOID id, int referenceChunk, long timeStamp)
  {
    this(channel, id, referenceChunk);
    this.timeStamp = timeStamp;
  }

  @Override
  protected void requesting(ExtendedDataOutputStream out) throws IOException
  {
    if (referenceChunk == CDORevisionImpl.COMPLETE_REFERENCES)
    {
      if (PROTOCOL.isEnabled())
      {
        PROTOCOL.format("Writing ID: {0}", id);
      }

      CDOIDImpl.write(out, id);
    }
    else
    {
      if (PROTOCOL.isEnabled())
      {
        PROTOCOL.format("Writing ID: {0}", id);
      }

      CDOID negID = CDOIDImpl.create(-id.getValue());
      CDOIDImpl.write(out, negID);

      if (PROTOCOL.isEnabled())
      {
        PROTOCOL.format("Writing referenceChunk: {0}", referenceChunk);
      }

      out.writeInt(referenceChunk);
    }

    if (timeStamp != null)
    {
      if (PROTOCOL.isEnabled())
      {
        PROTOCOL.format("Writing timeStamp: {0}", timeStamp);
      }

      out.writeBoolean(true);
      out.writeLong(timeStamp);
    }
    else
    {
      out.writeBoolean(false);
    }
  }

  @Override
  protected CDORevisionImpl confirming(ExtendedDataInputStream in) throws IOException
  {
    return new CDORevisionImpl(in, getSession().getPackageManager());
  }
}
