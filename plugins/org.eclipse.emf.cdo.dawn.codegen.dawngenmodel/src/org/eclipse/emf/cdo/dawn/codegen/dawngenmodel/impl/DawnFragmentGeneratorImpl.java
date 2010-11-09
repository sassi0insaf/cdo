/**
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Martin Fluegge - initial API and implementation
 */
package org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.impl;

import org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnFragmentGenerator;
import org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawngenmodelPackage;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Dawn Fragment Generator</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.impl.DawnFragmentGeneratorImpl#getFragmentName <em>Fragment
 * Name</em>}</li>
 * <li>{@link org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.impl.DawnFragmentGeneratorImpl#getDawnEditorClassName <em>
 * Dawn Editor Class Name</em>}</li>
 * <li>{@link org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.impl.DawnFragmentGeneratorImpl#getEmfGenModel <em>Emf Gen
 * Model</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class DawnFragmentGeneratorImpl extends EObjectImpl implements DawnFragmentGenerator
{
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public static final String copyright = "Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others.\r\nAll rights reserved. This program and the accompanying materials\r\nare made available under the terms of the Eclipse Public License v1.0\r\nwhich accompanies this distribution, and is available at\r\nhttp://www.eclipse.org/legal/epl-v10.html\r\n\r\nContributors:\r\n   Martin Fluegge - initial API and implementation";

  /**
   * The default value of the '{@link #getFragmentName() <em>Fragment Name</em>}' attribute. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @see #getFragmentName()
   * @generated
   * @ordered
   */
  protected static final String FRAGMENT_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFragmentName() <em>Fragment Name</em>}' attribute. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @see #getFragmentName()
   * @generated
   * @ordered
   */
  protected String fragmentName = FRAGMENT_NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getDawnEditorClassName() <em>Dawn Editor Class Name</em>}' attribute. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getDawnEditorClassName()
   * @generated
   * @ordered
   */
  protected static final String DAWN_EDITOR_CLASS_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDawnEditorClassName() <em>Dawn Editor Class Name</em>}' attribute. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see #getDawnEditorClassName()
   * @generated
   * @ordered
   */
  protected String dawnEditorClassName = DAWN_EDITOR_CLASS_NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getEmfGenModel() <em>Emf Gen Model</em>}' reference. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @see #getEmfGenModel()
   * @generated
   * @ordered
   */
  protected GenModel emfGenModel;

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected DawnFragmentGeneratorImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return DawngenmodelPackage.Literals.DAWN_FRAGMENT_GENERATOR;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public String getFragmentName()
  {
    return fragmentName;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setFragmentName(String newFragmentName)
  {
    String oldFragmentName = fragmentName;
    fragmentName = newFragmentName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DawngenmodelPackage.DAWN_FRAGMENT_GENERATOR__FRAGMENT_NAME,
          oldFragmentName, fragmentName));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public String getDawnEditorClassName()
  {
    return dawnEditorClassName;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setDawnEditorClassName(String newDawnEditorClassName)
  {
    String oldDawnEditorClassName = dawnEditorClassName;
    dawnEditorClassName = newDawnEditorClassName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET,
          DawngenmodelPackage.DAWN_FRAGMENT_GENERATOR__DAWN_EDITOR_CLASS_NAME, oldDawnEditorClassName,
          dawnEditorClassName));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public GenModel getEmfGenModel()
  {
    if (emfGenModel != null && emfGenModel.eIsProxy())
    {
      InternalEObject oldEmfGenModel = (InternalEObject)emfGenModel;
      emfGenModel = (GenModel)eResolveProxy(oldEmfGenModel);
      if (emfGenModel != oldEmfGenModel)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE,
              DawngenmodelPackage.DAWN_FRAGMENT_GENERATOR__EMF_GEN_MODEL, oldEmfGenModel, emfGenModel));
      }
    }
    return emfGenModel;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public GenModel basicGetEmfGenModel()
  {
    return emfGenModel;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  public void setEmfGenModel(GenModel newEmfGenModel)
  {
    GenModel oldEmfGenModel = emfGenModel;
    emfGenModel = newEmfGenModel;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DawngenmodelPackage.DAWN_FRAGMENT_GENERATOR__EMF_GEN_MODEL,
          oldEmfGenModel, emfGenModel));
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
    case DawngenmodelPackage.DAWN_FRAGMENT_GENERATOR__FRAGMENT_NAME:
      return getFragmentName();
    case DawngenmodelPackage.DAWN_FRAGMENT_GENERATOR__DAWN_EDITOR_CLASS_NAME:
      return getDawnEditorClassName();
    case DawngenmodelPackage.DAWN_FRAGMENT_GENERATOR__EMF_GEN_MODEL:
      if (resolve)
        return getEmfGenModel();
      return basicGetEmfGenModel();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
    case DawngenmodelPackage.DAWN_FRAGMENT_GENERATOR__FRAGMENT_NAME:
      setFragmentName((String)newValue);
      return;
    case DawngenmodelPackage.DAWN_FRAGMENT_GENERATOR__DAWN_EDITOR_CLASS_NAME:
      setDawnEditorClassName((String)newValue);
      return;
    case DawngenmodelPackage.DAWN_FRAGMENT_GENERATOR__EMF_GEN_MODEL:
      setEmfGenModel((GenModel)newValue);
      return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
    case DawngenmodelPackage.DAWN_FRAGMENT_GENERATOR__FRAGMENT_NAME:
      setFragmentName(FRAGMENT_NAME_EDEFAULT);
      return;
    case DawngenmodelPackage.DAWN_FRAGMENT_GENERATOR__DAWN_EDITOR_CLASS_NAME:
      setDawnEditorClassName(DAWN_EDITOR_CLASS_NAME_EDEFAULT);
      return;
    case DawngenmodelPackage.DAWN_FRAGMENT_GENERATOR__EMF_GEN_MODEL:
      setEmfGenModel((GenModel)null);
      return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
    case DawngenmodelPackage.DAWN_FRAGMENT_GENERATOR__FRAGMENT_NAME:
      return FRAGMENT_NAME_EDEFAULT == null ? fragmentName != null : !FRAGMENT_NAME_EDEFAULT.equals(fragmentName);
    case DawngenmodelPackage.DAWN_FRAGMENT_GENERATOR__DAWN_EDITOR_CLASS_NAME:
      return DAWN_EDITOR_CLASS_NAME_EDEFAULT == null ? dawnEditorClassName != null : !DAWN_EDITOR_CLASS_NAME_EDEFAULT
          .equals(dawnEditorClassName);
    case DawngenmodelPackage.DAWN_FRAGMENT_GENERATOR__EMF_GEN_MODEL:
      return emfGenModel != null;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy())
      return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (fragmentName: ");
    result.append(fragmentName);
    result.append(", dawnEditorClassName: ");
    result.append(dawnEditorClassName);
    result.append(')');
    return result.toString();
  }

} // DawnFragmentGeneratorImpl