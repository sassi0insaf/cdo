/*
 * Copyright (c) 2007, 2008, 2011, 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.protocol;

/**
 * Provides {@link IProtocol protocol} instances for given types.
 *
 * @author Eike Stepper
 */
public interface IProtocolProvider
{
  /**
   * @since 2.0
   */
  public IProtocol<?> getProtocol(String type);
}
