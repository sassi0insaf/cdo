/*
 * Copyright (c) 2008, 2011, 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.util.concurrent;

/**
 * @author Eike Stepper
 * @since 2.0
 */
public class QueueRunner extends QueueWorker<Runnable>
{
  public QueueRunner()
  {
  }

  @Override
  protected void work(WorkContext context, Runnable runnable)
  {
    runnable.run();
  }
}
