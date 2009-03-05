/**
 * Copyright (c) 2004 - 2009 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.server.internal.db.mapping;

import org.eclipse.emf.cdo.server.db.IDBStore;

import org.eclipse.emf.ecore.EModelElement;

/**
 * @author Eike Stepper
 */
public abstract class ServerInfo
{
  public static int getID(EModelElement modelElement, IDBStore store)
  {
    // TODO: implement ServerInfo.getID(modelElement, store)
    throw new UnsupportedOperationException();
  }
}
