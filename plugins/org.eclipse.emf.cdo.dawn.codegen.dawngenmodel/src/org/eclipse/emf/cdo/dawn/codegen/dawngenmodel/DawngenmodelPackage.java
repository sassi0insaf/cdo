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
package org.eclipse.emf.cdo.dawn.codegen.dawngenmodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawngenmodelFactory
 * @model kind="package"
 * @generated
 */
public interface DawngenmodelPackage extends EPackage
{
  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  String copyright = "Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others.\r\nAll rights reserved. This program and the accompanying materials\r\nare made available under the terms of the Eclipse Public License v1.0\r\nwhich accompanies this distribution, and is available at\r\nhttp://www.eclipse.org/legal/epl-v10.html\r\n\r\nContributors:\r\n   Martin Fluegge - initial API and implementation";

  /**
   * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  String eNAME = "dawngenmodel";

  /**
   * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  String eNS_URI = "http://www.eclipse.org/emf/cdo/dawn/2010/GenModel";

  /**
   * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  String eNS_PREFIX = "dawngenmodel";

  /**
   * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  DawngenmodelPackage eINSTANCE = org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.impl.DawngenmodelPackageImpl.init();

  /**
   * The meta object id for the '{@link org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.impl.DawnGMFGeneratorImpl
   * <em>Dawn GMF Generator</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @see org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.impl.DawnGMFGeneratorImpl
   * @see org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.impl.DawngenmodelPackageImpl#getDawnGMFGenerator()
   * @generated
   */
  int DAWN_GMF_GENERATOR = 0;

  /**
   * The feature id for the '<em><b>Fragment Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int DAWN_GMF_GENERATOR__FRAGMENT_NAME = 0;

  /**
   * The feature id for the '<em><b>Dawn Editor Class Name</b></em>' attribute. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int DAWN_GMF_GENERATOR__DAWN_EDITOR_CLASS_NAME = 1;

  /**
   * The feature id for the '<em><b>Dawn Document Provider Class Name</b></em>' attribute. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int DAWN_GMF_GENERATOR__DAWN_DOCUMENT_PROVIDER_CLASS_NAME = 2;

  /**
   * The feature id for the '<em><b>Dawn Editor Util Class Name</b></em>' attribute. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int DAWN_GMF_GENERATOR__DAWN_EDITOR_UTIL_CLASS_NAME = 3;

  /**
   * The feature id for the '<em><b>Dawn Creation Wizard Class Name</b></em>' attribute. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int DAWN_GMF_GENERATOR__DAWN_CREATION_WIZARD_CLASS_NAME = 4;

  /**
   * The feature id for the '<em><b>Dawn Canonical Editing Policy Class Name</b></em>' attribute. <!-- begin-user-doc
   * --> <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int DAWN_GMF_GENERATOR__DAWN_CANONICAL_EDITING_POLICY_CLASS_NAME = 5;

  /**
   * The feature id for the '<em><b>Dawn Diagram Edit Part Class Name</b></em>' attribute. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int DAWN_GMF_GENERATOR__DAWN_DIAGRAM_EDIT_PART_CLASS_NAME = 6;

  /**
   * The feature id for the '<em><b>Dawn Edit Part Factory Class Name</b></em>' attribute. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int DAWN_GMF_GENERATOR__DAWN_EDIT_PART_FACTORY_CLASS_NAME = 7;

  /**
   * The feature id for the '<em><b>Dawn Edit Part Provider Class Name</b></em>' attribute. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int DAWN_GMF_GENERATOR__DAWN_EDIT_PART_PROVIDER_CLASS_NAME = 8;

  /**
   * The feature id for the '<em><b>Dawn Edit Policy Provider Class Name</b></em>' attribute. <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int DAWN_GMF_GENERATOR__DAWN_EDIT_POLICY_PROVIDER_CLASS_NAME = 9;

  /**
   * The feature id for the '<em><b>GMF Gen Editor Generator</b></em>' reference. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int DAWN_GMF_GENERATOR__GMF_GEN_EDITOR_GENERATOR = 10;

  /**
   * The number of structural features of the '<em>Dawn GMF Generator</em>' class. <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * 
   * @generated
   * @ordered
   */
  int DAWN_GMF_GENERATOR_FEATURE_COUNT = 11;

  /**
   * Returns the meta object for class '{@link org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator
   * <em>Dawn GMF Generator</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for class '<em>Dawn GMF Generator</em>'.
   * @see org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator
   * @generated
   */
  EClass getDawnGMFGenerator();

  /**
   * Returns the meta object for the attribute '
   * {@link org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getFragmentName <em>Fragment Name</em>}'.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Fragment Name</em>'.
   * @see org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getFragmentName()
   * @see #getDawnGMFGenerator()
   * @generated
   */
  EAttribute getDawnGMFGenerator_FragmentName();

  /**
   * Returns the meta object for the attribute '
   * {@link org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getDawnEditorClassName
   * <em>Dawn Editor Class Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Dawn Editor Class Name</em>'.
   * @see org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getDawnEditorClassName()
   * @see #getDawnGMFGenerator()
   * @generated
   */
  EAttribute getDawnGMFGenerator_DawnEditorClassName();

  /**
   * Returns the meta object for the attribute '
   * {@link org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getDawnDocumentProviderClassName
   * <em>Dawn Document Provider Class Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Dawn Document Provider Class Name</em>'.
   * @see org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getDawnDocumentProviderClassName()
   * @see #getDawnGMFGenerator()
   * @generated
   */
  EAttribute getDawnGMFGenerator_DawnDocumentProviderClassName();

  /**
   * Returns the meta object for the attribute '
   * {@link org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getDawnEditorUtilClassName
   * <em>Dawn Editor Util Class Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Dawn Editor Util Class Name</em>'.
   * @see org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getDawnEditorUtilClassName()
   * @see #getDawnGMFGenerator()
   * @generated
   */
  EAttribute getDawnGMFGenerator_DawnEditorUtilClassName();

  /**
   * Returns the meta object for the attribute '
   * {@link org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getDawnCreationWizardClassName
   * <em>Dawn Creation Wizard Class Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Dawn Creation Wizard Class Name</em>'.
   * @see org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getDawnCreationWizardClassName()
   * @see #getDawnGMFGenerator()
   * @generated
   */
  EAttribute getDawnGMFGenerator_DawnCreationWizardClassName();

  /**
   * Returns the meta object for the attribute '
   * {@link org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getDawnCanonicalEditingPolicyClassName
   * <em>Dawn Canonical Editing Policy Class Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Dawn Canonical Editing Policy Class Name</em>'.
   * @see org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getDawnCanonicalEditingPolicyClassName()
   * @see #getDawnGMFGenerator()
   * @generated
   */
  EAttribute getDawnGMFGenerator_DawnCanonicalEditingPolicyClassName();

  /**
   * Returns the meta object for the attribute '
   * {@link org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getDawnDiagramEditPartClassName
   * <em>Dawn Diagram Edit Part Class Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Dawn Diagram Edit Part Class Name</em>'.
   * @see org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getDawnDiagramEditPartClassName()
   * @see #getDawnGMFGenerator()
   * @generated
   */
  EAttribute getDawnGMFGenerator_DawnDiagramEditPartClassName();

  /**
   * Returns the meta object for the attribute '
   * {@link org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getDawnEditPartFactoryClassName
   * <em>Dawn Edit Part Factory Class Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Dawn Edit Part Factory Class Name</em>'.
   * @see org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getDawnEditPartFactoryClassName()
   * @see #getDawnGMFGenerator()
   * @generated
   */
  EAttribute getDawnGMFGenerator_DawnEditPartFactoryClassName();

  /**
   * Returns the meta object for the attribute '
   * {@link org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getDawnEditPartProviderClassName
   * <em>Dawn Edit Part Provider Class Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Dawn Edit Part Provider Class Name</em>'.
   * @see org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getDawnEditPartProviderClassName()
   * @see #getDawnGMFGenerator()
   * @generated
   */
  EAttribute getDawnGMFGenerator_DawnEditPartProviderClassName();

  /**
   * Returns the meta object for the attribute '
   * {@link org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getDawnEditPolicyProviderClassName
   * <em>Dawn Edit Policy Provider Class Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the attribute '<em>Dawn Edit Policy Provider Class Name</em>'.
   * @see org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getDawnEditPolicyProviderClassName()
   * @see #getDawnGMFGenerator()
   * @generated
   */
  EAttribute getDawnGMFGenerator_DawnEditPolicyProviderClassName();

  /**
   * Returns the meta object for the reference '
   * {@link org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getGMFGenEditorGenerator
   * <em>GMF Gen Editor Generator</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the meta object for the reference '<em>GMF Gen Editor Generator</em>'.
   * @see org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.DawnGMFGenerator#getGMFGenEditorGenerator()
   * @see #getDawnGMFGenerator()
   * @generated
   */
  EReference getDawnGMFGenerator_GMFGenEditorGenerator();

  /**
   * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @return the factory that creates the instances of the model.
   * @generated
   */
  DawngenmodelFactory getDawngenmodelFactory();

  /**
   * <!-- begin-user-doc --> Defines literals for the meta objects that represent
   * <ul>
   * <li>each class,</li>
   * <li>each feature of each class,</li>
   * <li>each enum,</li>
   * <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * 
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.impl.DawnGMFGeneratorImpl
     * <em>Dawn GMF Generator</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.impl.DawnGMFGeneratorImpl
     * @see org.eclipse.emf.cdo.dawn.codegen.dawngenmodel.impl.DawngenmodelPackageImpl#getDawnGMFGenerator()
     * @generated
     */
    EClass DAWN_GMF_GENERATOR = eINSTANCE.getDawnGMFGenerator();

    /**
     * The meta object literal for the '<em><b>Fragment Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated
     */
    EAttribute DAWN_GMF_GENERATOR__FRAGMENT_NAME = eINSTANCE.getDawnGMFGenerator_FragmentName();

    /**
     * The meta object literal for the '<em><b>Dawn Editor Class Name</b></em>' attribute feature. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     */
    EAttribute DAWN_GMF_GENERATOR__DAWN_EDITOR_CLASS_NAME = eINSTANCE.getDawnGMFGenerator_DawnEditorClassName();

    /**
     * The meta object literal for the '<em><b>Dawn Document Provider Class Name</b></em>' attribute feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    EAttribute DAWN_GMF_GENERATOR__DAWN_DOCUMENT_PROVIDER_CLASS_NAME = eINSTANCE
        .getDawnGMFGenerator_DawnDocumentProviderClassName();

    /**
     * The meta object literal for the '<em><b>Dawn Editor Util Class Name</b></em>' attribute feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    EAttribute DAWN_GMF_GENERATOR__DAWN_EDITOR_UTIL_CLASS_NAME = eINSTANCE
        .getDawnGMFGenerator_DawnEditorUtilClassName();

    /**
     * The meta object literal for the '<em><b>Dawn Creation Wizard Class Name</b></em>' attribute feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    EAttribute DAWN_GMF_GENERATOR__DAWN_CREATION_WIZARD_CLASS_NAME = eINSTANCE
        .getDawnGMFGenerator_DawnCreationWizardClassName();

    /**
     * The meta object literal for the '<em><b>Dawn Canonical Editing Policy Class Name</b></em>' attribute feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    EAttribute DAWN_GMF_GENERATOR__DAWN_CANONICAL_EDITING_POLICY_CLASS_NAME = eINSTANCE
        .getDawnGMFGenerator_DawnCanonicalEditingPolicyClassName();

    /**
     * The meta object literal for the '<em><b>Dawn Diagram Edit Part Class Name</b></em>' attribute feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    EAttribute DAWN_GMF_GENERATOR__DAWN_DIAGRAM_EDIT_PART_CLASS_NAME = eINSTANCE
        .getDawnGMFGenerator_DawnDiagramEditPartClassName();

    /**
     * The meta object literal for the '<em><b>Dawn Edit Part Factory Class Name</b></em>' attribute feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    EAttribute DAWN_GMF_GENERATOR__DAWN_EDIT_PART_FACTORY_CLASS_NAME = eINSTANCE
        .getDawnGMFGenerator_DawnEditPartFactoryClassName();

    /**
     * The meta object literal for the '<em><b>Dawn Edit Part Provider Class Name</b></em>' attribute feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    EAttribute DAWN_GMF_GENERATOR__DAWN_EDIT_PART_PROVIDER_CLASS_NAME = eINSTANCE
        .getDawnGMFGenerator_DawnEditPartProviderClassName();

    /**
     * The meta object literal for the '<em><b>Dawn Edit Policy Provider Class Name</b></em>' attribute feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    EAttribute DAWN_GMF_GENERATOR__DAWN_EDIT_POLICY_PROVIDER_CLASS_NAME = eINSTANCE
        .getDawnGMFGenerator_DawnEditPolicyProviderClassName();

    /**
     * The meta object literal for the '<em><b>GMF Gen Editor Generator</b></em>' reference feature. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated
     */
    EReference DAWN_GMF_GENERATOR__GMF_GEN_EDITOR_GENERATOR = eINSTANCE.getDawnGMFGenerator_GMFGenEditorGenerator();

  }

} // DawngenmodelPackage