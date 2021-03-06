/*
 * Copyright (c) 2012, 2013 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.security;

import org.eclipse.emf.cdo.etypes.EtypesPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.cdo.security.SecurityFactory
 * @model kind="package"
 * @generated
 */
public interface SecurityPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "security"; //$NON-NLS-1$

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.eclipse.org/emf/CDO/security/4.1.0"; //$NON-NLS-1$

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "security"; //$NON-NLS-1$

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  SecurityPackage eINSTANCE = org.eclipse.emf.cdo.security.impl.SecurityPackageImpl.init();

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.security.impl.SecurityElementImpl <em>Element</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.cdo.security.impl.SecurityElementImpl
   * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getSecurityElement()
   * @generated
   */
  int SECURITY_ELEMENT = 0;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SECURITY_ELEMENT__ANNOTATIONS = EtypesPackage.MODEL_ELEMENT__ANNOTATIONS;

  /**
   * The number of structural features of the '<em>Element</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SECURITY_ELEMENT_FEATURE_COUNT = EtypesPackage.MODEL_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.security.impl.RealmImpl <em>Realm</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.cdo.security.impl.RealmImpl
   * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getRealm()
   * @generated
   */
  int REALM = 2;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.security.impl.DirectoryImpl <em>Directory</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.cdo.security.impl.DirectoryImpl
   * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getDirectory()
   * @generated
   */
  int DIRECTORY = 3;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.security.impl.SecurityItemImpl <em>Item</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.cdo.security.impl.SecurityItemImpl
   * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getSecurityItem()
   * @generated
   */
  int SECURITY_ITEM = 1;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SECURITY_ITEM__ANNOTATIONS = SECURITY_ELEMENT__ANNOTATIONS;

  /**
   * The number of structural features of the '<em>Item</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SECURITY_ITEM_FEATURE_COUNT = SECURITY_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REALM__ANNOTATIONS = SECURITY_ELEMENT__ANNOTATIONS;

  /**
   * The feature id for the '<em><b>Items</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REALM__ITEMS = SECURITY_ELEMENT_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>All Users</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REALM__ALL_USERS = SECURITY_ELEMENT_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>All Groups</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REALM__ALL_GROUPS = SECURITY_ELEMENT_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>All Roles</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REALM__ALL_ROLES = SECURITY_ELEMENT_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>All Permissions</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REALM__ALL_PERMISSIONS = SECURITY_ELEMENT_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REALM__NAME = SECURITY_ELEMENT_FEATURE_COUNT + 5;

  /**
   * The feature id for the '<em><b>Default Access</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REALM__DEFAULT_ACCESS = SECURITY_ELEMENT_FEATURE_COUNT + 6;

  /**
   * The feature id for the '<em><b>Default User Directory</b></em>' reference.
   * <!-- begin-user-doc -->
   * @since 4.2
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REALM__DEFAULT_USER_DIRECTORY = SECURITY_ELEMENT_FEATURE_COUNT + 7;

  /**
   * The feature id for the '<em><b>Default Group Directory</b></em>' reference.
   * <!-- begin-user-doc -->
   * @since 4.2
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REALM__DEFAULT_GROUP_DIRECTORY = SECURITY_ELEMENT_FEATURE_COUNT + 8;

  /**
   * The feature id for the '<em><b>Default Role Directory</b></em>' reference.
   * <!-- begin-user-doc -->
   * @since 4.2
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REALM__DEFAULT_ROLE_DIRECTORY = SECURITY_ELEMENT_FEATURE_COUNT + 9;

  /**
   * The number of structural features of the '<em>Realm</em>' class.
   * <!-- begin-user-doc -->
   * @noreference This field is not intended to be referenced by clients.
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REALM_FEATURE_COUNT = SECURITY_ELEMENT_FEATURE_COUNT + 10;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DIRECTORY__ANNOTATIONS = SECURITY_ITEM__ANNOTATIONS;

  /**
   * The feature id for the '<em><b>Items</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DIRECTORY__ITEMS = SECURITY_ITEM_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DIRECTORY__NAME = SECURITY_ITEM_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Directory</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DIRECTORY_FEATURE_COUNT = SECURITY_ITEM_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.security.impl.RoleImpl <em>Role</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.cdo.security.impl.RoleImpl
   * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getRole()
   * @generated
   */
  int ROLE = 4;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLE__ANNOTATIONS = SECURITY_ITEM__ANNOTATIONS;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLE__ID = SECURITY_ITEM_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Permissions</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLE__PERMISSIONS = SECURITY_ITEM_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Assignees</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLE__ASSIGNEES = SECURITY_ITEM_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Role</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLE_FEATURE_COUNT = SECURITY_ITEM_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.security.impl.AssigneeImpl <em>Assignee</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.cdo.security.impl.AssigneeImpl
   * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getAssignee()
   * @generated
   */
  int ASSIGNEE = 5;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNEE__ANNOTATIONS = SECURITY_ITEM__ANNOTATIONS;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNEE__ID = SECURITY_ITEM_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Roles</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNEE__ROLES = SECURITY_ITEM_FEATURE_COUNT + 1;

  /**
   * The number of structural features of the '<em>Assignee</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ASSIGNEE_FEATURE_COUNT = SECURITY_ITEM_FEATURE_COUNT + 2;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.security.impl.GroupImpl <em>Group</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.cdo.security.impl.GroupImpl
   * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getGroup()
   * @generated
   */
  int GROUP = 6;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GROUP__ANNOTATIONS = ASSIGNEE__ANNOTATIONS;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GROUP__ID = ASSIGNEE__ID;

  /**
   * The feature id for the '<em><b>Roles</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GROUP__ROLES = ASSIGNEE__ROLES;

  /**
   * The feature id for the '<em><b>Users</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GROUP__USERS = ASSIGNEE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Inherited Groups</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GROUP__INHERITED_GROUPS = ASSIGNEE_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Inheriting Groups</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GROUP__INHERITING_GROUPS = ASSIGNEE_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>All Inherited Groups</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GROUP__ALL_INHERITED_GROUPS = ASSIGNEE_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>All Inheriting Groups</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GROUP__ALL_INHERITING_GROUPS = ASSIGNEE_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>All Roles</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GROUP__ALL_ROLES = ASSIGNEE_FEATURE_COUNT + 5;

  /**
   * The number of structural features of the '<em>Group</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int GROUP_FEATURE_COUNT = ASSIGNEE_FEATURE_COUNT + 6;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.security.impl.UserImpl <em>User</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.cdo.security.impl.UserImpl
   * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getUser()
   * @generated
   */
  int USER = 7;

  /**
   * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int USER__ANNOTATIONS = ASSIGNEE__ANNOTATIONS;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int USER__ID = ASSIGNEE__ID;

  /**
   * The feature id for the '<em><b>Roles</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int USER__ROLES = ASSIGNEE__ROLES;

  /**
   * The feature id for the '<em><b>Groups</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int USER__GROUPS = ASSIGNEE_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Label</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int USER__LABEL = ASSIGNEE_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>First Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int USER__FIRST_NAME = ASSIGNEE_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Last Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int USER__LAST_NAME = ASSIGNEE_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Email</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int USER__EMAIL = ASSIGNEE_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>Default Access Override</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int USER__DEFAULT_ACCESS_OVERRIDE = ASSIGNEE_FEATURE_COUNT + 5;

  /**
   * The feature id for the '<em><b>Default Access</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int USER__DEFAULT_ACCESS = ASSIGNEE_FEATURE_COUNT + 6;

  /**
   * The feature id for the '<em><b>Locked</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int USER__LOCKED = ASSIGNEE_FEATURE_COUNT + 7;

  /**
   * The feature id for the '<em><b>Password</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int USER__PASSWORD = ASSIGNEE_FEATURE_COUNT + 8;

  /**
   * The feature id for the '<em><b>All Groups</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int USER__ALL_GROUPS = ASSIGNEE_FEATURE_COUNT + 9;

  /**
   * The feature id for the '<em><b>All Roles</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int USER__ALL_ROLES = ASSIGNEE_FEATURE_COUNT + 10;

  /**
   * The feature id for the '<em><b>All Permissions</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int USER__ALL_PERMISSIONS = ASSIGNEE_FEATURE_COUNT + 11;

  /**
   * The feature id for the '<em><b>Unassigned Roles</b></em>' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int USER__UNASSIGNED_ROLES = ASSIGNEE_FEATURE_COUNT + 12;

  /**
   * The number of structural features of the '<em>User</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int USER_FEATURE_COUNT = ASSIGNEE_FEATURE_COUNT + 13;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.security.impl.UserPasswordImpl <em>User Password</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.cdo.security.impl.UserPasswordImpl
   * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getUserPassword()
   * @generated
   */
  int USER_PASSWORD = 8;

  /**
   * The feature id for the '<em><b>Encrypted</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int USER_PASSWORD__ENCRYPTED = 0;

  /**
   * The number of structural features of the '<em>User Password</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int USER_PASSWORD_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.security.impl.PermissionImpl <em>Permission</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.cdo.security.impl.PermissionImpl
   * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getPermission()
   * @generated
   */
  int PERMISSION = 9;

  /**
   * The feature id for the '<em><b>Role</b></em>' container reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERMISSION__ROLE = 0;

  /**
   * The feature id for the '<em><b>Access</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERMISSION__ACCESS = 1;

  /**
   * The number of structural features of the '<em>Permission</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PERMISSION_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.security.impl.ClassPermissionImpl <em>Class Permission</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.cdo.security.impl.ClassPermissionImpl
   * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getClassPermission()
   * @generated
   */
  int CLASS_PERMISSION = 10;

  /**
   * The feature id for the '<em><b>Role</b></em>' container reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASS_PERMISSION__ROLE = PERMISSION__ROLE;

  /**
   * The feature id for the '<em><b>Access</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASS_PERMISSION__ACCESS = PERMISSION__ACCESS;

  /**
   * The feature id for the '<em><b>Applicable Class</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASS_PERMISSION__APPLICABLE_CLASS = PERMISSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Class Permission</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLASS_PERMISSION_FEATURE_COUNT = PERMISSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.security.impl.PackagePermissionImpl <em>Package Permission</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.cdo.security.impl.PackagePermissionImpl
   * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getPackagePermission()
   * @generated
   */
  int PACKAGE_PERMISSION = 11;

  /**
   * The feature id for the '<em><b>Role</b></em>' container reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PACKAGE_PERMISSION__ROLE = PERMISSION__ROLE;

  /**
   * The feature id for the '<em><b>Access</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PACKAGE_PERMISSION__ACCESS = PERMISSION__ACCESS;

  /**
   * The feature id for the '<em><b>Applicable Package</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PACKAGE_PERMISSION__APPLICABLE_PACKAGE = PERMISSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Package Permission</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PACKAGE_PERMISSION_FEATURE_COUNT = PERMISSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.security.impl.ResourcePermissionImpl <em>Resource Permission</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.cdo.security.impl.ResourcePermissionImpl
   * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getResourcePermission()
   * @generated
   */
  int RESOURCE_PERMISSION = 12;

  /**
   * The feature id for the '<em><b>Role</b></em>' container reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESOURCE_PERMISSION__ROLE = PERMISSION__ROLE;

  /**
   * The feature id for the '<em><b>Access</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESOURCE_PERMISSION__ACCESS = PERMISSION__ACCESS;

  /**
   * The feature id for the '<em><b>Pattern</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESOURCE_PERMISSION__PATTERN = PERMISSION_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Resource Permission</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int RESOURCE_PERMISSION_FEATURE_COUNT = PERMISSION_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.security.impl.ObjectPermissionImpl <em>Object Permission</em>}' class.
   * <!-- begin-user-doc -->
   * @since 4.2
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.cdo.security.impl.ObjectPermissionImpl
   * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getObjectPermission()
   * @generated
   */
  int OBJECT_PERMISSION = 13;

  /**
   * The feature id for the '<em><b>Role</b></em>' container reference.
   * <!-- begin-user-doc -->
   * @since 4.2
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OBJECT_PERMISSION__ROLE = PERMISSION__ROLE;

  /**
   * The feature id for the '<em><b>Access</b></em>' attribute.
   * <!-- begin-user-doc -->
   * @since 4.2
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OBJECT_PERMISSION__ACCESS = PERMISSION__ACCESS;

  /**
   * The number of structural features of the '<em>Object Permission</em>' class.
   * <!-- begin-user-doc -->
   * @since 4.2
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OBJECT_PERMISSION_FEATURE_COUNT = PERMISSION_FEATURE_COUNT + 0;

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.security.Access <em>Access</em>}' enum.
   * <!-- begin-user-doc -->
   * @noreference This field is not intended to be referenced by clients.
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.cdo.security.Access
   * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getAccess()
   * @generated
   */
  int ACCESS = 14;

  /**
   * The meta object id for the '<em>Access Object</em>' data type.
   * <!-- begin-user-doc -->
   * @noreference This field is not intended to be referenced by clients.
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.cdo.security.Access
   * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getAccessObject()
   * @generated
   */
  int ACCESS_OBJECT = 15;

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.security.SecurityElement <em>Element</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Element</em>'.
   * @see org.eclipse.emf.cdo.security.SecurityElement
   * @generated
   */
  EClass getSecurityElement();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.security.Realm <em>Realm</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Realm</em>'.
   * @see org.eclipse.emf.cdo.security.Realm
   * @generated
   */
  EClass getRealm();

  /**
   * Returns the meta object for the containment reference list '{@link org.eclipse.emf.cdo.security.Realm#getItems <em>Items</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Items</em>'.
   * @see org.eclipse.emf.cdo.security.Realm#getItems()
   * @see #getRealm()
   * @generated
   */
  EReference getRealm_Items();

  /**
   * Returns the meta object for the reference list '{@link org.eclipse.emf.cdo.security.Realm#getAllUsers <em>All Users</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>All Users</em>'.
   * @see org.eclipse.emf.cdo.security.Realm#getAllUsers()
   * @see #getRealm()
   * @generated
   */
  EReference getRealm_AllUsers();

  /**
   * Returns the meta object for the reference list '{@link org.eclipse.emf.cdo.security.Realm#getAllGroups <em>All Groups</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>All Groups</em>'.
   * @see org.eclipse.emf.cdo.security.Realm#getAllGroups()
   * @see #getRealm()
   * @generated
   */
  EReference getRealm_AllGroups();

  /**
   * Returns the meta object for the reference list '{@link org.eclipse.emf.cdo.security.Realm#getAllRoles <em>All Roles</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>All Roles</em>'.
   * @see org.eclipse.emf.cdo.security.Realm#getAllRoles()
   * @see #getRealm()
   * @generated
   */
  EReference getRealm_AllRoles();

  /**
   * Returns the meta object for the reference list '{@link org.eclipse.emf.cdo.security.Realm#getAllPermissions <em>All Permissions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>All Permissions</em>'.
   * @see org.eclipse.emf.cdo.security.Realm#getAllPermissions()
   * @see #getRealm()
   * @generated
   */
  EReference getRealm_AllPermissions();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.security.Realm#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.emf.cdo.security.Realm#getName()
   * @see #getRealm()
   * @generated
   */
  EAttribute getRealm_Name();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.security.Realm#getDefaultAccess <em>Default Access</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Default Access</em>'.
   * @see org.eclipse.emf.cdo.security.Realm#getDefaultAccess()
   * @see #getRealm()
   * @generated
   */
  EAttribute getRealm_DefaultAccess();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.emf.cdo.security.Realm#getDefaultUserDirectory <em>Default User Directory</em>}'.
   * <!-- begin-user-doc -->
   * @since 4.2
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Default User Directory</em>'.
   * @see org.eclipse.emf.cdo.security.Realm#getDefaultUserDirectory()
   * @see #getRealm()
   * @generated
   */
  EReference getRealm_DefaultUserDirectory();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.emf.cdo.security.Realm#getDefaultGroupDirectory <em>Default Group Directory</em>}'.
   * <!-- begin-user-doc -->
   * @since 4.2
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Default Group Directory</em>'.
   * @see org.eclipse.emf.cdo.security.Realm#getDefaultGroupDirectory()
   * @see #getRealm()
   * @generated
   */
  EReference getRealm_DefaultGroupDirectory();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.emf.cdo.security.Realm#getDefaultRoleDirectory <em>Default Role Directory</em>}'.
   * <!-- begin-user-doc -->
   * @since 4.2
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Default Role Directory</em>'.
   * @see org.eclipse.emf.cdo.security.Realm#getDefaultRoleDirectory()
   * @see #getRealm()
   * @generated
   */
  EReference getRealm_DefaultRoleDirectory();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.security.Directory <em>Directory</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Directory</em>'.
   * @see org.eclipse.emf.cdo.security.Directory
   * @generated
   */
  EClass getDirectory();

  /**
   * Returns the meta object for the containment reference list '{@link org.eclipse.emf.cdo.security.Directory#getItems <em>Items</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Items</em>'.
   * @see org.eclipse.emf.cdo.security.Directory#getItems()
   * @see #getDirectory()
   * @generated
   */
  EReference getDirectory_Items();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.security.Directory#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.emf.cdo.security.Directory#getName()
   * @see #getDirectory()
   * @generated
   */
  EAttribute getDirectory_Name();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.security.SecurityItem <em>Item</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Item</em>'.
   * @see org.eclipse.emf.cdo.security.SecurityItem
   * @generated
   */
  EClass getSecurityItem();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.security.Role <em>Role</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Role</em>'.
   * @see org.eclipse.emf.cdo.security.Role
   * @generated
   */
  EClass getRole();

  /**
   * Returns the meta object for the reference list '{@link org.eclipse.emf.cdo.security.Role#getAssignees <em>Assignees</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Assignees</em>'.
   * @see org.eclipse.emf.cdo.security.Role#getAssignees()
   * @see #getRole()
   * @generated
   */
  EReference getRole_Assignees();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.security.Role#getId <em>Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Id</em>'.
   * @see org.eclipse.emf.cdo.security.Role#getId()
   * @see #getRole()
   * @generated
   */
  EAttribute getRole_Id();

  /**
   * Returns the meta object for the containment reference list '{@link org.eclipse.emf.cdo.security.Role#getPermissions <em>Permissions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Permissions</em>'.
   * @see org.eclipse.emf.cdo.security.Role#getPermissions()
   * @see #getRole()
   * @generated
   */
  EReference getRole_Permissions();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.security.Assignee <em>Assignee</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Assignee</em>'.
   * @see org.eclipse.emf.cdo.security.Assignee
   * @generated
   */
  EClass getAssignee();

  /**
   * Returns the meta object for the reference list '{@link org.eclipse.emf.cdo.security.Assignee#getRoles <em>Roles</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Roles</em>'.
   * @see org.eclipse.emf.cdo.security.Assignee#getRoles()
   * @see #getAssignee()
   * @generated
   */
  EReference getAssignee_Roles();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.security.Assignee#getId <em>Id</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Id</em>'.
   * @see org.eclipse.emf.cdo.security.Assignee#getId()
   * @see #getAssignee()
   * @generated
   */
  EAttribute getAssignee_Id();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.security.Group <em>Group</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Group</em>'.
   * @see org.eclipse.emf.cdo.security.Group
   * @generated
   */
  EClass getGroup();

  /**
   * Returns the meta object for the reference list '{@link org.eclipse.emf.cdo.security.Group#getUsers <em>Users</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Users</em>'.
   * @see org.eclipse.emf.cdo.security.Group#getUsers()
   * @see #getGroup()
   * @generated
   */
  EReference getGroup_Users();

  /**
   * Returns the meta object for the reference list '{@link org.eclipse.emf.cdo.security.Group#getInheritedGroups <em>Inherited Groups</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Inherited Groups</em>'.
   * @see org.eclipse.emf.cdo.security.Group#getInheritedGroups()
   * @see #getGroup()
   * @generated
   */
  EReference getGroup_InheritedGroups();

  /**
   * Returns the meta object for the reference list '{@link org.eclipse.emf.cdo.security.Group#getInheritingGroups <em>Inheriting Groups</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Inheriting Groups</em>'.
   * @see org.eclipse.emf.cdo.security.Group#getInheritingGroups()
   * @see #getGroup()
   * @generated
   */
  EReference getGroup_InheritingGroups();

  /**
   * Returns the meta object for the reference list '{@link org.eclipse.emf.cdo.security.Group#getAllInheritingGroups <em>All Inheriting Groups</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>All Inheriting Groups</em>'.
   * @see org.eclipse.emf.cdo.security.Group#getAllInheritingGroups()
   * @see #getGroup()
   * @generated
   */
  EReference getGroup_AllInheritingGroups();

  /**
   * Returns the meta object for the reference list '{@link org.eclipse.emf.cdo.security.Group#getAllInheritedGroups <em>All Inherited Groups</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>All Inherited Groups</em>'.
   * @see org.eclipse.emf.cdo.security.Group#getAllInheritedGroups()
   * @see #getGroup()
   * @generated
   */
  EReference getGroup_AllInheritedGroups();

  /**
   * Returns the meta object for the reference list '{@link org.eclipse.emf.cdo.security.Group#getAllRoles <em>All Roles</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>All Roles</em>'.
   * @see org.eclipse.emf.cdo.security.Group#getAllRoles()
   * @see #getGroup()
   * @generated
   */
  EReference getGroup_AllRoles();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.security.User <em>User</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>User</em>'.
   * @see org.eclipse.emf.cdo.security.User
   * @generated
   */
  EClass getUser();

  /**
   * Returns the meta object for the reference list '{@link org.eclipse.emf.cdo.security.User#getGroups <em>Groups</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Groups</em>'.
   * @see org.eclipse.emf.cdo.security.User#getGroups()
   * @see #getUser()
   * @generated
   */
  EReference getUser_Groups();

  /**
   * Returns the meta object for the reference list '{@link org.eclipse.emf.cdo.security.User#getAllGroups <em>All Groups</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>All Groups</em>'.
   * @see org.eclipse.emf.cdo.security.User#getAllGroups()
   * @see #getUser()
   * @generated
   */
  EReference getUser_AllGroups();

  /**
   * Returns the meta object for the reference list '{@link org.eclipse.emf.cdo.security.User#getAllRoles <em>All Roles</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>All Roles</em>'.
   * @see org.eclipse.emf.cdo.security.User#getAllRoles()
   * @see #getUser()
   * @generated
   */
  EReference getUser_AllRoles();

  /**
   * Returns the meta object for the reference list '{@link org.eclipse.emf.cdo.security.User#getAllPermissions <em>All Permissions</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>All Permissions</em>'.
   * @see org.eclipse.emf.cdo.security.User#getAllPermissions()
   * @see #getUser()
   * @generated
   */
  EReference getUser_AllPermissions();

  /**
   * Returns the meta object for the reference list '{@link org.eclipse.emf.cdo.security.User#getUnassignedRoles <em>Unassigned Roles</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference list '<em>Unassigned Roles</em>'.
   * @see org.eclipse.emf.cdo.security.User#getUnassignedRoles()
   * @see #getUser()
   * @generated
   */
  EReference getUser_UnassignedRoles();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.security.User#getLabel <em>Label</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Label</em>'.
   * @see org.eclipse.emf.cdo.security.User#getLabel()
   * @see #getUser()
   * @generated
   */
  EAttribute getUser_Label();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.security.User#getFirstName <em>First Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>First Name</em>'.
   * @see org.eclipse.emf.cdo.security.User#getFirstName()
   * @see #getUser()
   * @generated
   */
  EAttribute getUser_FirstName();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.security.User#getLastName <em>Last Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Last Name</em>'.
   * @see org.eclipse.emf.cdo.security.User#getLastName()
   * @see #getUser()
   * @generated
   */
  EAttribute getUser_LastName();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.security.User#getEmail <em>Email</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Email</em>'.
   * @see org.eclipse.emf.cdo.security.User#getEmail()
   * @see #getUser()
   * @generated
   */
  EAttribute getUser_Email();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.security.User#getDefaultAccessOverride <em>Default Access Override</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Default Access Override</em>'.
   * @see org.eclipse.emf.cdo.security.User#getDefaultAccessOverride()
   * @see #getUser()
   * @generated
   */
  EAttribute getUser_DefaultAccessOverride();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.security.User#getDefaultAccess <em>Default Access</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Default Access</em>'.
   * @see org.eclipse.emf.cdo.security.User#getDefaultAccess()
   * @see #getUser()
   * @generated
   */
  EAttribute getUser_DefaultAccess();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.security.User#isLocked <em>Locked</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Locked</em>'.
   * @see org.eclipse.emf.cdo.security.User#isLocked()
   * @see #getUser()
   * @generated
   */
  EAttribute getUser_Locked();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.emf.cdo.security.User#getPassword <em>Password</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Password</em>'.
   * @see org.eclipse.emf.cdo.security.User#getPassword()
   * @see #getUser()
   * @generated
   */
  EReference getUser_Password();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.security.UserPassword <em>User Password</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>User Password</em>'.
   * @see org.eclipse.emf.cdo.security.UserPassword
   * @generated
   */
  EClass getUserPassword();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.security.UserPassword#getEncrypted <em>Encrypted</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Encrypted</em>'.
   * @see org.eclipse.emf.cdo.security.UserPassword#getEncrypted()
   * @see #getUserPassword()
   * @generated
   */
  EAttribute getUserPassword_Encrypted();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.security.Permission <em>Permission</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Permission</em>'.
   * @see org.eclipse.emf.cdo.security.Permission
   * @generated
   */
  EClass getPermission();

  /**
   * Returns the meta object for the container reference '{@link org.eclipse.emf.cdo.security.Permission#getRole <em>Role</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the container reference '<em>Role</em>'.
   * @see org.eclipse.emf.cdo.security.Permission#getRole()
   * @see #getPermission()
   * @generated
   */
  EReference getPermission_Role();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.security.Permission#getAccess <em>Access</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Access</em>'.
   * @see org.eclipse.emf.cdo.security.Permission#getAccess()
   * @see #getPermission()
   * @generated
   */
  EAttribute getPermission_Access();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.security.ClassPermission <em>Class Permission</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Class Permission</em>'.
   * @see org.eclipse.emf.cdo.security.ClassPermission
   * @generated
   */
  EClass getClassPermission();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.emf.cdo.security.ClassPermission#getApplicableClass <em>Applicable Class</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Applicable Class</em>'.
   * @see org.eclipse.emf.cdo.security.ClassPermission#getApplicableClass()
   * @see #getClassPermission()
   * @generated
   */
  EReference getClassPermission_ApplicableClass();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.security.PackagePermission <em>Package Permission</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Package Permission</em>'.
   * @see org.eclipse.emf.cdo.security.PackagePermission
   * @generated
   */
  EClass getPackagePermission();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.emf.cdo.security.PackagePermission#getApplicablePackage <em>Applicable Package</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Applicable Package</em>'.
   * @see org.eclipse.emf.cdo.security.PackagePermission#getApplicablePackage()
   * @see #getPackagePermission()
   * @generated
   */
  EReference getPackagePermission_ApplicablePackage();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.security.ResourcePermission <em>Resource Permission</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Resource Permission</em>'.
   * @see org.eclipse.emf.cdo.security.ResourcePermission
   * @generated
   */
  EClass getResourcePermission();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.emf.cdo.security.ResourcePermission#getPattern <em>Pattern</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Pattern</em>'.
   * @see org.eclipse.emf.cdo.security.ResourcePermission#getPattern()
   * @see #getResourcePermission()
   * @generated
   */
  EAttribute getResourcePermission_Pattern();

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.security.ObjectPermission <em>Object Permission</em>}'.
   * <!-- begin-user-doc -->
   * @since 4.2
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Object Permission</em>'.
   * @see org.eclipse.emf.cdo.security.ObjectPermission
   * @generated
   */
  EClass getObjectPermission();

  /**
   * Returns the meta object for enum '{@link org.eclipse.emf.cdo.security.Access <em>Access</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Access</em>'.
   * @see org.eclipse.emf.cdo.security.Access
   * @generated
   */
  EEnum getAccess();

  /**
   * Returns the meta object for data type '{@link org.eclipse.emf.cdo.security.Access <em>Access Object</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for data type '<em>Access Object</em>'.
   * @see org.eclipse.emf.cdo.security.Access
   * @model instanceClass="org.eclipse.emf.cdo.security.Access"
   *        extendedMetaData="name='Access:Object' baseType='Access'"
   * @generated
   */
  EDataType getAccessObject();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  SecurityFactory getSecurityFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * @noimplement This interface is not intended to be implemented by clients.
   * @noextend This interface is not intended to be extended by clients.
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link org.eclipse.emf.cdo.security.impl.SecurityElementImpl <em>Element</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.cdo.security.impl.SecurityElementImpl
     * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getSecurityElement()
     * @generated
     */
    EClass SECURITY_ELEMENT = eINSTANCE.getSecurityElement();

    /**
     * The meta object literal for the '{@link org.eclipse.emf.cdo.security.impl.RealmImpl <em>Realm</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.cdo.security.impl.RealmImpl
     * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getRealm()
     * @generated
     */
    EClass REALM = eINSTANCE.getRealm();

    /**
     * The meta object literal for the '<em><b>Items</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REALM__ITEMS = eINSTANCE.getRealm_Items();

    /**
     * The meta object literal for the '<em><b>All Users</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REALM__ALL_USERS = eINSTANCE.getRealm_AllUsers();

    /**
     * The meta object literal for the '<em><b>All Groups</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REALM__ALL_GROUPS = eINSTANCE.getRealm_AllGroups();

    /**
     * The meta object literal for the '<em><b>All Roles</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REALM__ALL_ROLES = eINSTANCE.getRealm_AllRoles();

    /**
     * The meta object literal for the '<em><b>All Permissions</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REALM__ALL_PERMISSIONS = eINSTANCE.getRealm_AllPermissions();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REALM__NAME = eINSTANCE.getRealm_Name();

    /**
     * The meta object literal for the '<em><b>Default Access</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REALM__DEFAULT_ACCESS = eINSTANCE.getRealm_DefaultAccess();

    /**
     * The meta object literal for the '<em><b>Default User Directory</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * @since 4.2
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REALM__DEFAULT_USER_DIRECTORY = eINSTANCE.getRealm_DefaultUserDirectory();

    /**
     * The meta object literal for the '<em><b>Default Group Directory</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * @since 4.2
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REALM__DEFAULT_GROUP_DIRECTORY = eINSTANCE.getRealm_DefaultGroupDirectory();

    /**
     * The meta object literal for the '<em><b>Default Role Directory</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * @since 4.2
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REALM__DEFAULT_ROLE_DIRECTORY = eINSTANCE.getRealm_DefaultRoleDirectory();

    /**
     * The meta object literal for the '{@link org.eclipse.emf.cdo.security.impl.DirectoryImpl <em>Directory</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.cdo.security.impl.DirectoryImpl
     * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getDirectory()
     * @generated
     */
    EClass DIRECTORY = eINSTANCE.getDirectory();

    /**
     * The meta object literal for the '<em><b>Items</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference DIRECTORY__ITEMS = eINSTANCE.getDirectory_Items();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DIRECTORY__NAME = eINSTANCE.getDirectory_Name();

    /**
     * The meta object literal for the '{@link org.eclipse.emf.cdo.security.impl.SecurityItemImpl <em>Item</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.cdo.security.impl.SecurityItemImpl
     * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getSecurityItem()
     * @generated
     */
    EClass SECURITY_ITEM = eINSTANCE.getSecurityItem();

    /**
     * The meta object literal for the '{@link org.eclipse.emf.cdo.security.impl.RoleImpl <em>Role</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.cdo.security.impl.RoleImpl
     * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getRole()
     * @generated
     */
    EClass ROLE = eINSTANCE.getRole();

    /**
     * The meta object literal for the '<em><b>Assignees</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ROLE__ASSIGNEES = eINSTANCE.getRole_Assignees();

    /**
     * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ROLE__ID = eINSTANCE.getRole_Id();

    /**
     * The meta object literal for the '<em><b>Permissions</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ROLE__PERMISSIONS = eINSTANCE.getRole_Permissions();

    /**
     * The meta object literal for the '{@link org.eclipse.emf.cdo.security.impl.AssigneeImpl <em>Assignee</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.cdo.security.impl.AssigneeImpl
     * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getAssignee()
     * @generated
     */
    EClass ASSIGNEE = eINSTANCE.getAssignee();

    /**
     * The meta object literal for the '<em><b>Roles</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ASSIGNEE__ROLES = eINSTANCE.getAssignee_Roles();

    /**
     * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ASSIGNEE__ID = eINSTANCE.getAssignee_Id();

    /**
     * The meta object literal for the '{@link org.eclipse.emf.cdo.security.impl.GroupImpl <em>Group</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.cdo.security.impl.GroupImpl
     * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getGroup()
     * @generated
     */
    EClass GROUP = eINSTANCE.getGroup();

    /**
     * The meta object literal for the '<em><b>Users</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GROUP__USERS = eINSTANCE.getGroup_Users();

    /**
     * The meta object literal for the '<em><b>Inherited Groups</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GROUP__INHERITED_GROUPS = eINSTANCE.getGroup_InheritedGroups();

    /**
     * The meta object literal for the '<em><b>Inheriting Groups</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GROUP__INHERITING_GROUPS = eINSTANCE.getGroup_InheritingGroups();

    /**
     * The meta object literal for the '<em><b>All Inheriting Groups</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GROUP__ALL_INHERITING_GROUPS = eINSTANCE.getGroup_AllInheritingGroups();

    /**
     * The meta object literal for the '<em><b>All Inherited Groups</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GROUP__ALL_INHERITED_GROUPS = eINSTANCE.getGroup_AllInheritedGroups();

    /**
     * The meta object literal for the '<em><b>All Roles</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference GROUP__ALL_ROLES = eINSTANCE.getGroup_AllRoles();

    /**
     * The meta object literal for the '{@link org.eclipse.emf.cdo.security.impl.UserImpl <em>User</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.cdo.security.impl.UserImpl
     * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getUser()
     * @generated
     */
    EClass USER = eINSTANCE.getUser();

    /**
     * The meta object literal for the '<em><b>Groups</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference USER__GROUPS = eINSTANCE.getUser_Groups();

    /**
     * The meta object literal for the '<em><b>All Groups</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference USER__ALL_GROUPS = eINSTANCE.getUser_AllGroups();

    /**
     * The meta object literal for the '<em><b>All Roles</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference USER__ALL_ROLES = eINSTANCE.getUser_AllRoles();

    /**
     * The meta object literal for the '<em><b>All Permissions</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference USER__ALL_PERMISSIONS = eINSTANCE.getUser_AllPermissions();

    /**
     * The meta object literal for the '<em><b>Unassigned Roles</b></em>' reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference USER__UNASSIGNED_ROLES = eINSTANCE.getUser_UnassignedRoles();

    /**
     * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute USER__LABEL = eINSTANCE.getUser_Label();

    /**
     * The meta object literal for the '<em><b>First Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute USER__FIRST_NAME = eINSTANCE.getUser_FirstName();

    /**
     * The meta object literal for the '<em><b>Last Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute USER__LAST_NAME = eINSTANCE.getUser_LastName();

    /**
     * The meta object literal for the '<em><b>Email</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute USER__EMAIL = eINSTANCE.getUser_Email();

    /**
     * The meta object literal for the '<em><b>Default Access Override</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute USER__DEFAULT_ACCESS_OVERRIDE = eINSTANCE.getUser_DefaultAccessOverride();

    /**
     * The meta object literal for the '<em><b>Default Access</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute USER__DEFAULT_ACCESS = eINSTANCE.getUser_DefaultAccess();

    /**
     * The meta object literal for the '<em><b>Locked</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute USER__LOCKED = eINSTANCE.getUser_Locked();

    /**
     * The meta object literal for the '<em><b>Password</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference USER__PASSWORD = eINSTANCE.getUser_Password();

    /**
     * The meta object literal for the '{@link org.eclipse.emf.cdo.security.impl.UserPasswordImpl <em>User Password</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.cdo.security.impl.UserPasswordImpl
     * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getUserPassword()
     * @generated
     */
    EClass USER_PASSWORD = eINSTANCE.getUserPassword();

    /**
     * The meta object literal for the '<em><b>Encrypted</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute USER_PASSWORD__ENCRYPTED = eINSTANCE.getUserPassword_Encrypted();

    /**
     * The meta object literal for the '{@link org.eclipse.emf.cdo.security.impl.PermissionImpl <em>Permission</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.cdo.security.impl.PermissionImpl
     * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getPermission()
     * @generated
     */
    EClass PERMISSION = eINSTANCE.getPermission();

    /**
     * The meta object literal for the '<em><b>Role</b></em>' container reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PERMISSION__ROLE = eINSTANCE.getPermission_Role();

    /**
     * The meta object literal for the '<em><b>Access</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PERMISSION__ACCESS = eINSTANCE.getPermission_Access();

    /**
     * The meta object literal for the '{@link org.eclipse.emf.cdo.security.impl.ClassPermissionImpl <em>Class Permission</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.cdo.security.impl.ClassPermissionImpl
     * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getClassPermission()
     * @generated
     */
    EClass CLASS_PERMISSION = eINSTANCE.getClassPermission();

    /**
     * The meta object literal for the '<em><b>Applicable Class</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CLASS_PERMISSION__APPLICABLE_CLASS = eINSTANCE.getClassPermission_ApplicableClass();

    /**
     * The meta object literal for the '{@link org.eclipse.emf.cdo.security.impl.PackagePermissionImpl <em>Package Permission</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.cdo.security.impl.PackagePermissionImpl
     * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getPackagePermission()
     * @generated
     */
    EClass PACKAGE_PERMISSION = eINSTANCE.getPackagePermission();

    /**
     * The meta object literal for the '<em><b>Applicable Package</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PACKAGE_PERMISSION__APPLICABLE_PACKAGE = eINSTANCE.getPackagePermission_ApplicablePackage();

    /**
     * The meta object literal for the '{@link org.eclipse.emf.cdo.security.impl.ResourcePermissionImpl <em>Resource Permission</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.cdo.security.impl.ResourcePermissionImpl
     * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getResourcePermission()
     * @generated
     */
    EClass RESOURCE_PERMISSION = eINSTANCE.getResourcePermission();

    /**
     * The meta object literal for the '<em><b>Pattern</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute RESOURCE_PERMISSION__PATTERN = eINSTANCE.getResourcePermission_Pattern();

    /**
     * The meta object literal for the '{@link org.eclipse.emf.cdo.security.impl.ObjectPermissionImpl <em>Object Permission</em>}' class.
     * <!-- begin-user-doc -->
     * @since 4.2
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.cdo.security.impl.ObjectPermissionImpl
     * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getObjectPermission()
     * @generated
     */
    EClass OBJECT_PERMISSION = eINSTANCE.getObjectPermission();

    /**
     * The meta object literal for the '{@link org.eclipse.emf.cdo.security.Access <em>Access</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.cdo.security.Access
     * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getAccess()
     * @generated
     */
    EEnum ACCESS = eINSTANCE.getAccess();

    /**
     * The meta object literal for the '<em>Access Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.cdo.security.Access
     * @see org.eclipse.emf.cdo.security.impl.SecurityPackageImpl#getAccessObject()
     * @generated
     */
    EDataType ACCESS_OBJECT = eINSTANCE.getAccessObject();

  }

} // SecurityPackage
