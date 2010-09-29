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
package org.eclipse.net4j.buddies.chat;

import org.eclipse.net4j.buddies.common.IFacility;

/**
 * @author Eike Stepper
 */
public interface IChat extends IFacility
{
  public static final String TYPE = "chat"; //$NON-NLS-1$

  public IComment[] getComments();

  public void sendComment(String text);
}