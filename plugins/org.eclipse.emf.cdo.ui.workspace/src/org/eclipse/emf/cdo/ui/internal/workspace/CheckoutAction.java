/**
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Victor Roldan Betancort - maintenance
 */
package org.eclipse.emf.cdo.ui.internal.workspace;

import org.eclipse.emf.cdo.location.ICheckoutSource;
import org.eclipse.emf.cdo.ui.internal.workspace.bundle.OM;
import org.eclipse.emf.cdo.workspace.efs.CDOWorkspaceFSUtil;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Eike Stepper
 */
public class CheckoutAction implements IObjectActionDelegate
{
  private IWorkbenchPart part;

  private ISelection selection;

  public CheckoutAction()
  {
  }

  public void setActivePart(IAction action, IWorkbenchPart part)
  {
    this.part = part;
  }

  public void selectionChanged(IAction action, ISelection selection)
  {
    this.selection = selection;
  }

  public void run(IAction action)
  {
    if (selection instanceof IStructuredSelection)
    {
      final Object element = ((IStructuredSelection)selection).getFirstElement();
      if (element instanceof ICheckoutSource)
      {
        final ICheckoutSource checkoutSource = (ICheckoutSource)element;
        String projectNameDefault = checkoutSource.getRepositoryLocation().getRepositoryName();

        Shell shell = part.getSite().getShell();
        CheckoutDialog dialog = new CheckoutDialog(shell, projectNameDefault);

        if (dialog.open() == CheckoutDialog.OK)
        {
          final String projectName = dialog.getProjectName();

          new Job("Checking out...")
          {
            @Override
            protected IStatus run(IProgressMonitor monitor)
            {
              try
              {
                CDOWorkspaceFSUtil.checkout(checkoutSource, projectName, monitor);
                return Status.OK_STATUS;
              }
              catch (CoreException ex)
              {
                ex.printStackTrace();
                return ex.getStatus();
              }
              catch (Exception ex)
              {
                ex.printStackTrace();
                return new Status(IStatus.ERROR, OM.BUNDLE_ID, ex.getLocalizedMessage(), ex);
              }
            }
          }.schedule();
        }
      }
    }
  }
}