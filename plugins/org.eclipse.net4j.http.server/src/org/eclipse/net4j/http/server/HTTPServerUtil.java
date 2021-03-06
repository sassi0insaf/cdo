/*
 * Copyright (c) 2008, 2011, 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.http.server;

import org.eclipse.net4j.http.internal.server.HTTPAcceptorFactory;
import org.eclipse.net4j.http.internal.server.RandomizerInjector;
import org.eclipse.net4j.util.container.IManagedContainer;

/**
 * A utility class with static convenience methods.
 * 
 * @author Eike Stepper
 */
public final class HTTPServerUtil
{
  private HTTPServerUtil()
  {
  }

  public static void prepareContainer(IManagedContainer container)
  {
    container.registerFactory(new HTTPAcceptorFactory());
    container.addPostProcessor(new RandomizerInjector());
  }

  public static IHTTPAcceptor getAcceptor(IManagedContainer container, String description)
  {
    return (IHTTPAcceptor)container
        .getElement(HTTPAcceptorFactory.PRODUCT_GROUP, HTTPAcceptorFactory.TYPE, description);
  }
}
