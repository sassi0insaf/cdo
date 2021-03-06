/*
 * Copyright (c) 2007-2009, 2011, 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Victor Roldan Betancort - maintenance
 */
package org.eclipse.emf.cdo.internal.ui.actions;

import org.eclipse.emf.cdo.internal.ui.messages.Messages;
import org.eclipse.emf.cdo.ui.CDOEditorUtil;
import org.eclipse.emf.cdo.view.CDOView;

import org.eclipse.emf.internal.cdo.view.CDOStateMachine;

import org.eclipse.emf.spi.cdo.InternalCDOObject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Eike Stepper
 */
public class ReloadObjectsAction extends EditingDomainAction
{
  public static final String ID = "reload-objects"; //$NON-NLS-1$

  private static final String TITLE = Messages.getString("ReloadObjectsAction.1"); //$NON-NLS-1$

  private List<InternalCDOObject> objects = new ArrayList<InternalCDOObject>();

  public ReloadObjectsAction()
  {
    super(TITLE);
    setId(ID);
  }

  public void selectionChanged(IStructuredSelection selection)
  {
    objects.clear();
    if (selection != null)
    {
      for (Iterator<?> it = selection.iterator(); it.hasNext();)
      {
        Object object = it.next();
        if (object instanceof InternalCDOObject)
        {
          objects.add((InternalCDOObject)object);
        }
      }
    }

    update();
  }

  @Override
  public void update()
  {
    setEnabled(!objects.isEmpty());
  }

  @Override
  protected void doRun(IProgressMonitor progressMonitor) throws Exception
  {
    if (!objects.isEmpty())
    {
      InternalCDOObject[] array = objects.toArray(new InternalCDOObject[objects.size()]);

      CDOStateMachine.INSTANCE.reload(array);

      IWorkbenchPage page = getPage();
      if (page != null)
      {
        CDOView view = array[0].cdoView();
        CDOEditorUtil.refreshEditors(page, view);
      }
    }
  }
}
