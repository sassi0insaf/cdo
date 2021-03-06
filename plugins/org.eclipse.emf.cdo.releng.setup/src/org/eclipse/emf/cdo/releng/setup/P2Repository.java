/*
 * Copyright (c) 2013 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.releng.setup;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>P2 Repository</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.cdo.releng.setup.P2Repository#getDirectorCall <em>Director Call</em>}</li>
 *   <li>{@link org.eclipse.emf.cdo.releng.setup.P2Repository#getUrl <em>Url</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.cdo.releng.setup.SetupPackage#getP2Repository()
 * @model
 * @generated
 */
public interface P2Repository extends EObject
{
  /**
   * Returns the value of the '<em><b>Director Call</b></em>' container reference.
   * It is bidirectional and its opposite is '{@link org.eclipse.emf.cdo.releng.setup.DirectorCall#getP2Repositories <em>P2 Repositories</em>}'.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Director Call</em>' container reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Director Call</em>' container reference.
   * @see #setDirectorCall(DirectorCall)
   * @see org.eclipse.emf.cdo.releng.setup.SetupPackage#getP2Repository_DirectorCall()
   * @see org.eclipse.emf.cdo.releng.setup.DirectorCall#getP2Repositories
   * @model opposite="p2Repositories" transient="false"
   * @generated
   */
  DirectorCall getDirectorCall();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.releng.setup.P2Repository#getDirectorCall <em>Director Call</em>}' container reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Director Call</em>' container reference.
   * @see #getDirectorCall()
   * @generated
   */
  void setDirectorCall(DirectorCall value);

  /**
   * Returns the value of the '<em><b>Url</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Url</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Url</em>' attribute.
   * @see #setUrl(String)
   * @see org.eclipse.emf.cdo.releng.setup.SetupPackage#getP2Repository_Url()
   * @model
   * @generated
   */
  String getUrl();

  /**
   * Sets the value of the '{@link org.eclipse.emf.cdo.releng.setup.P2Repository#getUrl <em>Url</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Url</em>' attribute.
   * @see #getUrl()
   * @generated
   */
  void setUrl(String value);

} // P2Repository
