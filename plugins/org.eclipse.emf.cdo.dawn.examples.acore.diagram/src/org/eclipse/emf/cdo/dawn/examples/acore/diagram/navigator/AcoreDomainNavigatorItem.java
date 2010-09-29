/*
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
package org.eclipse.emf.cdo.dawn.examples.acore.diagram.navigator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

/**
 * @generated
 */
public class AcoreDomainNavigatorItem extends PlatformObject
{

  /**
   * @generated
   */
  static
  {
    final Class[] supportedTypes = new Class[] { EObject.class, IPropertySource.class };
    Platform.getAdapterManager().registerAdapters(new IAdapterFactory()
    {

      public Object getAdapter(Object adaptableObject, Class adapterType)
      {
        if (adaptableObject instanceof org.eclipse.emf.cdo.dawn.examples.acore.diagram.navigator.AcoreDomainNavigatorItem)
        {
          org.eclipse.emf.cdo.dawn.examples.acore.diagram.navigator.AcoreDomainNavigatorItem domainNavigatorItem = (org.eclipse.emf.cdo.dawn.examples.acore.diagram.navigator.AcoreDomainNavigatorItem)adaptableObject;
          EObject eObject = domainNavigatorItem.getEObject();
          if (adapterType == EObject.class)
          {
            return eObject;
          }
          if (adapterType == IPropertySource.class)
          {
            return domainNavigatorItem.getPropertySourceProvider().getPropertySource(eObject);
          }
        }

        return null;
      }

      public Class[] getAdapterList()
      {
        return supportedTypes;
      }
    }, org.eclipse.emf.cdo.dawn.examples.acore.diagram.navigator.AcoreDomainNavigatorItem.class);
  }

  /**
   * @generated
   */
  private Object myParent;

  /**
   * @generated
   */
  private EObject myEObject;

  /**
   * @generated
   */
  private IPropertySourceProvider myPropertySourceProvider;

  /**
   * @generated
   */
  public AcoreDomainNavigatorItem(EObject eObject, Object parent, IPropertySourceProvider propertySourceProvider)
  {
    myParent = parent;
    myEObject = eObject;
    myPropertySourceProvider = propertySourceProvider;
  }

  /**
   * @generated
   */
  public Object getParent()
  {
    return myParent;
  }

  /**
   * @generated
   */
  public EObject getEObject()
  {
    return myEObject;
  }

  /**
   * @generated
   */
  public IPropertySourceProvider getPropertySourceProvider()
  {
    return myPropertySourceProvider;
  }

  /**
   * @generated
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof org.eclipse.emf.cdo.dawn.examples.acore.diagram.navigator.AcoreDomainNavigatorItem)
    {
      return EcoreUtil.getURI(getEObject()).equals(
          EcoreUtil.getURI(((org.eclipse.emf.cdo.dawn.examples.acore.diagram.navigator.AcoreDomainNavigatorItem)obj)
              .getEObject()));
    }
    return super.equals(obj);
  }

  /**
   * @generated
   */
  public int hashCode()
  {
    return EcoreUtil.getURI(getEObject()).hashCode();
  }

}