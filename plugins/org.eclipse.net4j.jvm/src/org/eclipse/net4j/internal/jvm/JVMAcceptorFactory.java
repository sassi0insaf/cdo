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
package org.eclipse.net4j.internal.jvm;

import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.factory.ProductCreationException;

import org.eclipse.internal.net4j.AcceptorFactory;

/**
 * @author Eike Stepper
 */
public class JVMAcceptorFactory extends AcceptorFactory<JVMAcceptor>
{
  public static final String TYPE = "jvm";

  public JVMAcceptorFactory()
  {
    super(TYPE);
  }

  public JVMAcceptor create(String description) throws ProductCreationException
  {
    JVMAcceptor acceptor = new JVMAcceptor();
    acceptor.setName(description);
    return acceptor;
  }

  @Override
  public String getDescriptionFor(JVMAcceptor acceptor)
  {
    return acceptor.getName();
  }

  public static JVMAcceptor get(IManagedContainer container, String description)
  {
    return (JVMAcceptor)container.getElement(PRODUCT_GROUP, TYPE, description);
  }
}
