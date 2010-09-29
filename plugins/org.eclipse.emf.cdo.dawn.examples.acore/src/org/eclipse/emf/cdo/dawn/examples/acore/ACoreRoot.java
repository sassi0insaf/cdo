/**
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *     Martin Fluegge - initial API and implementation
 * 
 */
package org.eclipse.emf.cdo.dawn.examples.acore;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>ACore Root</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.cdo.dawn.examples.acore.ACoreRoot#getTitle <em>Title</em>}</li>
 * <li>{@link org.eclipse.emf.cdo.dawn.examples.acore.ACoreRoot#getClasses <em>Classes</em>}</li>
 * <li>{@link org.eclipse.emf.cdo.dawn.examples.acore.ACoreRoot#getInterfaces <em>Interfaces</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.cdo.dawn.examples.acore.AcorePackage#getACoreRoot()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ACoreRoot extends CDOObject
{
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  String copyright = "Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany).\r\nAll rights reserved. This program and the accompanying materials\r\nare made available under the terms of the Eclipse Public License v1.0\r\nwhich accompanies this distribution, and is available at\r\nhttp://www.eclipse.org/legal/epl-v10.html\r\n \r\nContributors:\r\n    Martin Fluegge - initial API and implementation\r\n";

  /**
   * Returns the value of the '<em><b>Title</b></em>' attribute. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Title</em>' attribute isn't clear, there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * 
   * @return the value of the '<em>Title</em>' attribute.
   * @see #setTitle(String)
   * @see org.eclipse.emf.cdo.dawn.examples.acore.AcorePackage#getACoreRoot_Title()
   * @model
   * @generated
   */
  String getTitle();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.dawn.examples.acore.ACoreRoot#getTitle <em>Title</em>}'
   * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @param value
   *          the new value of the '<em>Title</em>' attribute.
   * @see #getTitle()
   * @generated
   */
  void setTitle(String value);

  /**
   * Returns the value of the '<em><b>Classes</b></em>' containment reference list. The list contents are of type
   * {@link org.eclipse.emf.cdo.dawn.examples.acore.AClass}. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Classes</em>' containment reference list isn't clear, there really should be more of a
   * description here...
   * </p>
   * <!-- end-user-doc -->
   * 
   * @return the value of the '<em>Classes</em>' containment reference list.
   * @see org.eclipse.emf.cdo.dawn.examples.acore.AcorePackage#getACoreRoot_Classes()
   * @model containment="true"
   * @generated
   */
  EList<AClass> getClasses();

  /**
   * Returns the value of the '<em><b>Interfaces</b></em>' containment reference list. The list contents are of type
   * {@link org.eclipse.emf.cdo.dawn.examples.acore.AInterface}. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Interfaces</em>' containment reference list isn't clear, there really should be more of
   * a description here...
   * </p>
   * <!-- end-user-doc -->
   * 
   * @return the value of the '<em>Interfaces</em>' containment reference list.
   * @see org.eclipse.emf.cdo.dawn.examples.acore.AcorePackage#getACoreRoot_Interfaces()
   * @model containment="true"
   * @generated
   */
  EList<AInterface> getInterfaces();

} // ACoreRoot