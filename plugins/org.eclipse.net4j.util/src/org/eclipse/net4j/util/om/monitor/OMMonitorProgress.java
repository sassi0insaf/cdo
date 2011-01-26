/**
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.util.om.monitor;

/**
 * @author Eike Stepper
 * @since 3.1
 */
public interface OMMonitorProgress
{
  public static final double ZERO = 0;

  public static final double ONE = 1;

  public static final double TEN = 10;

  public static final double HUNDRED = 100;

  public double getTotalWork();

  public double getWork();

  public double getWorkPercent();
}