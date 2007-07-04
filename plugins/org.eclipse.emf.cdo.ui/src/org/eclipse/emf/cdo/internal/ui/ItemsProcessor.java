/***************************************************************************
 * Copyright (c) 2004 - 2007 Eike Stepper, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 **************************************************************************/
package org.eclipse.emf.cdo.internal.ui;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.protocol.CDOID;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.TreeItem;

import java.util.Set;

/**
 * @author Eike Stepper
 */
public abstract class ItemsProcessor
{
  public ItemsProcessor()
  {
  }

  public void processCDOObjects(TreeViewer viewer)
  {
    processCDOObjects(viewer, null);
  }

  public void processCDOObjects(final TreeViewer viewer, final Set<CDOID> ids)
  {
    try
    {
      viewer.getControl().getDisplay().asyncExec(new Runnable()
      {
        public void run()
        {
          try
          {
            processItems(viewer, ids, viewer.getTree().getItems());
          }
          catch (Exception ex)
          {
            ex.printStackTrace();
          }
        }
      });
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  protected abstract void processCDOObject(TreeViewer viewer, CDOObject cdoObject);

  private void processItems(TreeViewer viewer, Set<CDOID> ids, TreeItem[] items)
  {
    for (TreeItem item : items)
    {
      Object object = item.getData();
      if (object instanceof CDOObject)
      {
        CDOObject cdoObject = (CDOObject)object;
        if (ids == null)
        {
          processCDOObject(viewer, cdoObject);
        }
        else if (ids.contains(cdoObject.cdoID()))
        {
          processCDOObject(viewer, cdoObject);
        }
      }

      if (item.getItemCount() != 0)
      {
        processItems(viewer, ids, item.getItems());
      }
    }
  }
}
