/**
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.tests.db;

import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.server.db.CDODBUtil;
import org.eclipse.emf.cdo.server.db.CDODBBrowser;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.emf.cdo.tests.config.impl.RepositoryConfig;

import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBAdapter;

import javax.sql.DataSource;

/**
 * @author Eike Stepper
 */
public abstract class DBStoreRepositoryConfig extends RepositoryConfig
{
  private static final long serialVersionUID = 1L;

  private transient CDODBBrowser dbBrowser;

  public DBStoreRepositoryConfig(String name)
  {
    super(name);
  }

  @Override
  public IStore createStore(String repoName)
  {
    IMappingStrategy mappingStrategy = createMappingStrategy();
    IDBAdapter dbAdapter = createDBAdapter();
    DataSource dataSource = createDataSource(repoName);
    return CDODBUtil.createStore(mappingStrategy, dbAdapter, DBUtil.createConnectionProvider(dataSource));
  }

  @Override
  public void setUp() throws Exception
  {
    super.setUp();
    dbBrowser = new CDODBBrowser(repositories);
    dbBrowser.activate();
  }

  @Override
  public void tearDown() throws Exception
  {
    dbBrowser.deactivate();
    super.tearDown();
  }

  protected abstract IMappingStrategy createMappingStrategy();

  protected abstract IDBAdapter createDBAdapter();

  protected abstract DataSource createDataSource(String repoName);
}
