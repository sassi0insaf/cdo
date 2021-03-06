/*
 * Copyright (c) 2009-2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Victor Roldan Betancort - initial API and implementation
 *    Eike Stepper - maintenance
 */
package org.eclipse.emf.cdo.ui;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.eresource.CDOResourceLeaf;
import org.eclipse.emf.cdo.internal.ui.CDOEditorInputImpl;
import org.eclipse.emf.cdo.internal.ui.CDOLobEditorInput;
import org.eclipse.emf.cdo.internal.ui.bundle.OM;
import org.eclipse.emf.cdo.internal.ui.editor.CDOEditor;
import org.eclipse.emf.cdo.view.CDOView;

import org.eclipse.net4j.util.ObjectUtil;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Some utility methods to cope with CDOEditor and CDOEditorInput
 *
 * @author Victor Roldan Betancort
 * @since 2.0
 */
public final class CDOEditorUtil
{
  /**
   * @since 4.1
   */
  public static final String EDITOR_ID = "org.eclipse.emf.cdo.ui.CDOEditor"; //$NON-NLS-1$

  private static final IEditorRegistry EDITOR_REGISTRY = PlatformUI.getWorkbench().getEditorRegistry();

  private static final Map<CDOResourceLeaf, String> EDITOR_OVERRIDES = new WeakHashMap<CDOResourceLeaf, String>();

  private static final String TEXT_EDITOR_ID = "org.eclipse.ui.DefaultTextEditor";

  private static String editorID = EDITOR_ID;

  private CDOEditorUtil()
  {
  }

  /**
   * @since 4.1
   */
  public static String getEditorID()
  {
    return editorID;
  }

  /**
   * @since 4.1
   */
  public static void setEditorID(String editorID)
  {
    CDOEditorUtil.editorID = editorID;
  }

  /**
   * Returns an implementation of the CDOEditorInput interface.
   */
  public static CDOEditorInput createCDOEditorInput(CDOView view, String resourcePath, boolean viewOwned)
  {
    return new CDOEditorInputImpl(view, resourcePath, viewOwned);
  }

  /**
   * Opens the specified resource in CDOEditor
   *
   * @param page
   *          The page in which the editor will be opened
   * @param view
   *          the CDOView that will be used to access the resource
   * @param resourcePath
   *          absolute path to the resource in the repository
   */
  public static void openEditor(final IWorkbenchPage page, final CDOView view, final String resourcePath)
  {
    Display display = page.getWorkbenchWindow().getShell().getDisplay();
    display.asyncExec(new Runnable()
    {
      public void run()
      {
        try
        {
          IEditorReference[] references = findEditor(page, view, resourcePath);
          if (references.length != 0)
          {
            IEditorPart editor = references[0].getEditor(true);
            page.activate(editor);
          }
          else
          {
            IEditorInput input = createCDOEditorInput(view, resourcePath, false);
            page.openEditor(input, editorID);
          }
        }
        catch (Exception ex)
        {
          OM.LOG.error(ex);
        }
      }
    });
  }

  /**
   * Returns references to possibly opened instances of CDOEditor with certain CDOView and resource
   *
   * @param page
   *          The page where to search for opened editors
   * @param view
   *          The editors to find are using the specified CDOView
   * @param resourcePath
   *          The editors are editing the CDOResource specified with this path
   */
  public static IEditorReference[] findEditor(IWorkbenchPage page, CDOView view, String resourcePath)
  {
    List<IEditorReference> result = new ArrayList<IEditorReference>();
    IEditorReference[] editorReferences = page.getEditorReferences();
    for (IEditorReference editorReference : editorReferences)
    {
      try
      {
        if (ObjectUtil.equals(editorReference.getId(), editorID))
        {
          IEditorInput editorInput = editorReference.getEditorInput();
          if (editorInput instanceof CDOEditorInput)
          {
            CDOEditorInput cdoInput = (CDOEditorInput)editorInput;
            if (cdoInput.getView() == view)
            {
              if (resourcePath == null || ObjectUtil.equals(cdoInput.getResourcePath(), resourcePath))
              {
                result.add(editorReference);
              }
            }
          }
        }
      }
      catch (PartInitException ex)
      {
        OM.LOG.error(ex);
      }
    }

    return result.toArray(new IEditorReference[result.size()]);
  }

  /**
   * @since 4.2
   */
  public static void populateMenu(IMenuManager manager, CDOResourceLeaf resource, IWorkbenchPage page)
  {
    String effectiveEditorID = getEffectiveEditorID(resource);
    manager.add(new OpenEditorAction(page, effectiveEditorID, resource, false));

    String[] editorIDs = getAllEditorIDs(resource);
    if (editorIDs.length > 1 || editorIDs.length == 1 && !editorIDs[0].equals(effectiveEditorID))
    {
      MenuManager subMenu = new MenuManager("Open With");
      manager.add(subMenu);

      for (String id : editorIDs)
      {
        OpenEditorAction action = new OpenEditorAction(page, id, resource, true);
        subMenu.add(action);
        if (id.equals(effectiveEditorID))
        {
          action.setChecked(true);
        }
      }
    }
  }

  /**
   * @since 4.2
   */
  public static String getEffectiveEditorID(CDOResourceLeaf resource)
  {
    String editorID = EDITOR_OVERRIDES.get(resource);
    if (editorID != null)
    {
      return editorID;
    }

    if (resource instanceof CDOResource)
    {
      return EDITOR_ID;
    }

    String name = resource.getName();
    IEditorDescriptor editorDescriptor = EDITOR_REGISTRY.getDefaultEditor(name);
    if (editorDescriptor != null)
    {
      return editorDescriptor.getId();
    }

    return TEXT_EDITOR_ID;
  }

  /**
   * @since 4.2
   */
  public static String[] getAllEditorIDs(CDOResourceLeaf resource)
  {
    List<String> editorIDs = new ArrayList<String>();
    if (resource instanceof CDOResource)
    {
      editorIDs.add(EDITOR_ID);
    }

    String name = resource.getName();
    for (IEditorDescriptor editorDescriptor : EDITOR_REGISTRY.getEditors(name))
    {
      editorIDs.add(editorDescriptor.getId());
    }

    if (!editorIDs.contains(TEXT_EDITOR_ID) && EDITOR_REGISTRY.findEditor(TEXT_EDITOR_ID) != null)
    {
      editorIDs.add(TEXT_EDITOR_ID);
    }

    Collections.sort(editorIDs);
    return editorIDs.toArray(new String[editorIDs.size()]);
  }

  /**
   * Returns an implementation of the IEditorInput interface.
   *
   * @since 4.2
   */
  public static IEditorInput createEditorInput(String editorID, CDOResourceLeaf resource, boolean viewOwned)
  {
    if (resource instanceof CDOResource)
    {
      if (EDITOR_ID.equals(editorID))
      {
        CDOView view = resource.cdoView();
        String path = resource.getPath();
        return createCDOEditorInput(view, path, viewOwned);
      }
    }

    return new CDOLobEditorInput(resource);
  }

  /**
   * Returns an implementation of the IEditorInput interface.
   *
   * @since 4.2
   */
  public static IEditorInput createEditorInput(String editorID, CDOResourceLeaf resource)
  {
    return createEditorInput(editorID, resource, false);
  }

  /**
   * Opens the specified resource in CDOEditor
   *
   * @param page
   *          The page in which the editor will be opened
   * @since 4.2
   */
  public static void openEditor(IWorkbenchPage page, CDOResourceLeaf resource)
  {
    String editorID = CDOEditorUtil.getEffectiveEditorID(resource);
    if (editorID != null)
    {
      openEditor(page, editorID, resource);
    }
  }

  /**
   * Opens the specified resource in CDOEditor
   *
   * @param page
   *          The page in which the editor will be opened
   * @since 4.2
   */
  public static void openEditor(final IWorkbenchPage page, final String editorID, final CDOResourceLeaf resource)
  {
    Display display = page.getWorkbenchWindow().getShell().getDisplay();
    display.asyncExec(new Runnable()
    {
      public void run()
      {
        try
        {
          IEditorInput editorInput = createEditorInput(editorID, resource);
          page.openEditor(editorInput, editorID);
        }
        catch (Exception ex)
        {
          OM.LOG.error(ex);
        }
      }
    });
  }

  /**
   * Refreshes all editor's viewers that are using certain CDOView.
   *
   * @param page
   *          the IWorkbenchPage where CDOEditor is opened
   * @param view
   *          instance of CDOView our editors are using
   */
  public static void refreshEditors(IWorkbenchPage page, CDOView view)
  {
    IEditorReference[] references = findEditor(page, view, null);
    for (IEditorReference reference : references)
    {
      CDOEditor editor = (CDOEditor)reference.getEditor(false);
      if (editor != null)
      {
        editor.refreshViewer(null);
      }
    }
  }

  /**
   * @author Eike Stepper
   */
  private static class OpenEditorAction extends Action
  {
    private IWorkbenchPage page;

    private String editorID;

    private CDOResourceLeaf resource;

    private boolean overrideOnRun;

    public OpenEditorAction(IWorkbenchPage page, String editorID, CDOResourceLeaf resource, boolean overrideOnRun)
    {
      this.page = page;
      this.editorID = editorID;
      this.resource = resource;
      this.overrideOnRun = overrideOnRun;

      IEditorDescriptor editorDescriptor = EDITOR_REGISTRY.findEditor(editorID);
      String label = editorDescriptor.getLabel();
      if (overrideOnRun)
      {
        setText(label);
      }
      else
      {
        setText("Open With " + label);
      }

      setImageDescriptor(editorDescriptor.getImageDescriptor());
      setToolTipText("Open the " + label + " editor on this resource");
    }

    @Override
    public void run()
    {
      if (overrideOnRun)
      {
        EDITOR_OVERRIDES.put(resource, editorID);
      }

      openEditor(page, editorID, resource);
    }
  }
}
