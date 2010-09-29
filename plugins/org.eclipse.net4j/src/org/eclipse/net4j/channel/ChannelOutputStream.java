/**
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.channel;

import org.eclipse.net4j.buffer.BufferOutputStream;
import org.eclipse.net4j.buffer.IBuffer;
import org.eclipse.net4j.buffer.IBufferProvider;

/**
 * @author Eike Stepper
 */
public class ChannelOutputStream extends BufferOutputStream
{
  public ChannelOutputStream(IChannel channel)
  {
    super(channel, channel.getID());
  }

  public ChannelOutputStream(IChannel channel, IBufferProvider bufferProvider)
  {
    super(channel, bufferProvider, channel == null ? IBuffer.NO_CHANNEL : channel.getID());
  }
}