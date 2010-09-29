/*******************************************************************************
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Martin Fluegge - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.cdo.dawn.ui.helper;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.PlatformUI;

/**
 * @author Martin Fluegge
 */
public class EditorDescriptionHelper
{
  public static String getEditorIdForDawnEditor(String resourceName)
  {
    IEditorDescriptor editorDescriptor = getEditorDescriptorForDawnEditor(resourceName);
    if (editorDescriptor != null)
    {
      return editorDescriptor.getId();
    }
    return null;
  }

  public static IEditorDescriptor getEditorDescriptorForDawnEditor(String resourceName)
  {
    IEditorDescriptor[] editors = PlatformUI.getWorkbench().getEditorRegistry().getEditors(resourceName);

    for (IEditorDescriptor editorDescriptor : editors)
    {
      // TODO make this more stable by getting the class name more reliably
      if (editorDescriptor.getId().contains(".Dawn"))
      {
        return editorDescriptor;
      }
    }

    return null;
  }

  public static IEditorDescriptor getEditorDescriptorFromFirstEditor(String resourceName)
  {
    IEditorDescriptor[] editors = PlatformUI.getWorkbench().getEditorRegistry().getEditors(resourceName);
    if (editors.length > 0)
    {
      return editors[0];
    }

    return null;
  }

  public static IEditorDescriptor getEditorDescriptorForDawnEditor(Resource resource)
  {
    return getEditorDescriptorForDawnEditor(resource.getURI().lastSegment());
  }

  public static Image getImageForEditor(String resourceName)
  {
    IEditorDescriptor editorDescriptor = getEditorDescriptorForDawnEditor(resourceName);

    if (editorDescriptor == null)
    {
      editorDescriptor = getEditorDescriptorFromFirstEditor(resourceName);
    }

    if (editorDescriptor != null)
    {
      return editorDescriptor.getImageDescriptor().createImage();
    }

    return null;
  }
}