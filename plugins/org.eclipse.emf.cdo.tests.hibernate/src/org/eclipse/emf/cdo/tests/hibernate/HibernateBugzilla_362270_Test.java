/*
 * Copyright (c) 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Martin Taal
 */
package org.eclipse.emf.cdo.tests.hibernate;

import org.eclipse.emf.cdo.server.internal.hibernate.HibernateStore;
import org.eclipse.emf.cdo.tests.ExternalReferenceTest;
import org.eclipse.emf.cdo.tests.config.IRepositoryConfig;

/**
 * Read external reference annotation.
 * 
 * @author Martin Taal
 */
public class HibernateBugzilla_362270_Test extends ExternalReferenceTest
{
  @Override
  public void testOneXMIResourceManyViewsOnOneResourceSet() throws Exception
  {
  }

  @Override
  public void testUsingObjectsBetweenSameTransaction() throws Exception
  {
  }

  @Override
  public void testManyViewsOnOneResourceSet() throws Exception
  {
  }

  @Override
  public void testXRefExternalObject() throws Exception
  {
  }

  @Override
  protected void doSetUp() throws Exception
  {
    final IRepositoryConfig repConfig = getRepositoryConfig();
    final HibernateConfig hbConfig = (HibernateConfig)repConfig;
    final String persistenceXML = "org/eclipse/emf/cdo/tests/hibernate/external_model1_4.persistence.xml";
    hbConfig.getAdditionalProperties().put(HibernateStore.PERSISTENCE_XML, persistenceXML);

    super.doSetUp();
  }

  @Override
  protected void doTearDown() throws Exception
  {
    final IRepositoryConfig repConfig = getRepositoryConfig();
    final HibernateConfig hbConfig = (HibernateConfig)repConfig;
    hbConfig.getAdditionalProperties().clear();
    super.doTearDown();
  }
}
