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
package org.eclipse.emf.cdo.releng.setup.impl;

import org.eclipse.emf.cdo.releng.setup.Preferences;
import org.eclipse.emf.cdo.releng.setup.SetupPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Preferences</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.cdo.releng.setup.impl.PreferencesImpl#getUserName <em>User Name</em>}</li>
 *   <li>{@link org.eclipse.emf.cdo.releng.setup.impl.PreferencesImpl#getBundlePool <em>Bundle Pool</em>}</li>
 *   <li>{@link org.eclipse.emf.cdo.releng.setup.impl.PreferencesImpl#getInstallFolder <em>Install Folder</em>}</li>
 *   <li>{@link org.eclipse.emf.cdo.releng.setup.impl.PreferencesImpl#getGitPrefix <em>Git Prefix</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PreferencesImpl extends ToolInstallationImpl implements Preferences
{
  /**
   * The default value of the '{@link #getUserName() <em>User Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUserName()
   * @generated
   * @ordered
   */
  protected static final String USER_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getUserName() <em>User Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUserName()
   * @generated
   * @ordered
   */
  protected String userName = USER_NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getBundlePool() <em>Bundle Pool</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBundlePool()
   * @generated
   * @ordered
   */
  protected static final String BUNDLE_POOL_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getBundlePool() <em>Bundle Pool</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBundlePool()
   * @generated
   * @ordered
   */
  protected String bundlePool = BUNDLE_POOL_EDEFAULT;

  /**
   * The default value of the '{@link #getInstallFolder() <em>Install Folder</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInstallFolder()
   * @generated
   * @ordered
   */
  protected static final String INSTALL_FOLDER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getInstallFolder() <em>Install Folder</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInstallFolder()
   * @generated
   * @ordered
   */
  protected String installFolder = INSTALL_FOLDER_EDEFAULT;

  /**
   * The default value of the '{@link #getGitPrefix() <em>Git Prefix</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGitPrefix()
   * @generated
   * @ordered
   */
  protected static final String GIT_PREFIX_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getGitPrefix() <em>Git Prefix</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGitPrefix()
   * @generated
   * @ordered
   */
  protected String gitPrefix = GIT_PREFIX_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PreferencesImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return SetupPackage.Literals.PREFERENCES;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getUserName()
  {
    return userName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setUserName(String newUserName)
  {
    String oldUserName = userName;
    userName = newUserName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SetupPackage.PREFERENCES__USER_NAME, oldUserName, userName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getBundlePool()
  {
    return bundlePool;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setBundlePool(String newBundlePool)
  {
    String oldBundlePool = bundlePool;
    bundlePool = newBundlePool;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SetupPackage.PREFERENCES__BUNDLE_POOL, oldBundlePool,
          bundlePool));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getInstallFolder()
  {
    return installFolder;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInstallFolder(String newInstallFolder)
  {
    String oldInstallFolder = installFolder;
    installFolder = newInstallFolder;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SetupPackage.PREFERENCES__INSTALL_FOLDER, oldInstallFolder,
          installFolder));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getGitPrefix()
  {
    return gitPrefix;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setGitPrefix(String newGitPrefix)
  {
    String oldGitPrefix = gitPrefix;
    gitPrefix = newGitPrefix;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, SetupPackage.PREFERENCES__GIT_PREFIX, oldGitPrefix,
          gitPrefix));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
    case SetupPackage.PREFERENCES__USER_NAME:
      return getUserName();
    case SetupPackage.PREFERENCES__BUNDLE_POOL:
      return getBundlePool();
    case SetupPackage.PREFERENCES__INSTALL_FOLDER:
      return getInstallFolder();
    case SetupPackage.PREFERENCES__GIT_PREFIX:
      return getGitPrefix();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
    case SetupPackage.PREFERENCES__USER_NAME:
      setUserName((String)newValue);
      return;
    case SetupPackage.PREFERENCES__BUNDLE_POOL:
      setBundlePool((String)newValue);
      return;
    case SetupPackage.PREFERENCES__INSTALL_FOLDER:
      setInstallFolder((String)newValue);
      return;
    case SetupPackage.PREFERENCES__GIT_PREFIX:
      setGitPrefix((String)newValue);
      return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
    case SetupPackage.PREFERENCES__USER_NAME:
      setUserName(USER_NAME_EDEFAULT);
      return;
    case SetupPackage.PREFERENCES__BUNDLE_POOL:
      setBundlePool(BUNDLE_POOL_EDEFAULT);
      return;
    case SetupPackage.PREFERENCES__INSTALL_FOLDER:
      setInstallFolder(INSTALL_FOLDER_EDEFAULT);
      return;
    case SetupPackage.PREFERENCES__GIT_PREFIX:
      setGitPrefix(GIT_PREFIX_EDEFAULT);
      return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
    case SetupPackage.PREFERENCES__USER_NAME:
      return USER_NAME_EDEFAULT == null ? userName != null : !USER_NAME_EDEFAULT.equals(userName);
    case SetupPackage.PREFERENCES__BUNDLE_POOL:
      return BUNDLE_POOL_EDEFAULT == null ? bundlePool != null : !BUNDLE_POOL_EDEFAULT.equals(bundlePool);
    case SetupPackage.PREFERENCES__INSTALL_FOLDER:
      return INSTALL_FOLDER_EDEFAULT == null ? installFolder != null : !INSTALL_FOLDER_EDEFAULT.equals(installFolder);
    case SetupPackage.PREFERENCES__GIT_PREFIX:
      return GIT_PREFIX_EDEFAULT == null ? gitPrefix != null : !GIT_PREFIX_EDEFAULT.equals(gitPrefix);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy())
      return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (userName: ");
    result.append(userName);
    result.append(", bundlePool: ");
    result.append(bundlePool);
    result.append(", installFolder: ");
    result.append(installFolder);
    result.append(", gitPrefix: ");
    result.append(gitPrefix);
    result.append(')');
    return result.toString();
  }

} // PreferencesImpl
