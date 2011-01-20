/**
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Andre Dietisheim - initial API and implementation
 *    Eike Stepper - maintenance
 */
package org.eclipse.emf.cdo.defs;

import org.eclipse.net4j.defs.ConnectorDef;
import org.eclipse.net4j.util.defs.Def;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Fail Over Strategy Def</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.cdo.defs.FailOverStrategyDef#getConnectorDef <em>Connector Def</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.cdo.defs.CDODefsPackage#getFailOverStrategyDef()
 * @model abstract="true"
 * @generated
 */
public interface FailOverStrategyDef extends Def
{
  /**
   * Returns the value of the '<em><b>Connector Def</b></em>' reference. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Connector Def</em>' reference isn't clear, there really should be more of a description
   * here...
   * </p>
   * <!-- end-user-doc -->
   * 
   * @return the value of the '<em>Connector Def</em>' reference.
   * @see #setConnectorDef(ConnectorDef)
   * @see org.eclipse.emf.cdo.defs.CDODefsPackage#getFailOverStrategyDef_ConnectorDef()
   * @model required="true"
   * @generated
   */
  ConnectorDef getConnectorDef();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.defs.FailOverStrategyDef#getConnectorDef <em>Connector Def</em>}'
   * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @param value
   *          the new value of the '<em>Connector Def</em>' reference.
   * @see #getConnectorDef()
   * @generated
   */
  void setConnectorDef(ConnectorDef value);

} // FailOverStrategyDef