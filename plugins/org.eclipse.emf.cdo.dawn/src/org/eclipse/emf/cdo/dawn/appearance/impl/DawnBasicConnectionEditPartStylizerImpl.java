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
package org.eclipse.emf.cdo.dawn.appearance.impl;

import org.eclipse.emf.cdo.dawn.appearance.DawnAppearancer;
import org.eclipse.emf.cdo.dawn.appearance.DawnEditPartStylizer;

import org.eclipse.emf.workspace.AbstractEMFOperation;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.util.StringStatics;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.ChangePropertyValueRequest;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.emf.core.util.PackageUtil;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.swt.graphics.Color;

/**
 * @author Martin Fluegge
 */
public class DawnBasicConnectionEditPartStylizerImpl implements DawnEditPartStylizer
{

  public void setDefault(EditPart editPart)
  {
    setEdge(editPart, DawnAppearancer.COLOR_NO_CONFLICT);
  }

  public void setConflicted(EditPart editPart, int type)
  {
    Color color = DawnAppearancer.COLOR_DELETE_CONFLICT;
    setEdge(editPart, color);
  }

  private void setEdge(EditPart editPart, Color color)
  {
    ChangePropertyValueRequest request = new ChangePropertyValueRequest(StringStatics.BLANK,
        PackageUtil.getID(NotationPackage.eINSTANCE.getLineStyle_LineColor()), FigureUtilities.colorToInteger(color));
    final Command command = editPart.getCommand(request);

    AbstractEMFOperation operation = new AbstractEMFOperation(((IGraphicalEditPart)editPart).getEditingDomain(),
        StringStatics.BLANK, null)
    {
      @Override
      protected IStatus doExecute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException
      {
        command.execute();
        return Status.OK_STATUS;
      }
    };

    try
    {
      operation.execute(new NullProgressMonitor(), null);
    }
    catch (ExecutionException e)
    {
    }

    editPart.refresh();
    editPart.getRoot().refresh();
  }

  public void setLocked(EditPart editPart, int type)
  {
    Color color = null;
    switch (type)
    {
    case DawnAppearancer.TYPE_LOCKED_LOCALLY:
    {
      color = DawnAppearancer.COLOR_LOCKED_REMOTELY;
      break;
    }
    case DawnAppearancer.TYPE_LOCKED_GLOBALLY:
    {
      color = DawnAppearancer.COLOR_LOCKED_LOCALLY;
      break;
    }

    default:
      break;
    }
    if (color != null)
    {
      setEdge(editPart, color);
    }
  }
}