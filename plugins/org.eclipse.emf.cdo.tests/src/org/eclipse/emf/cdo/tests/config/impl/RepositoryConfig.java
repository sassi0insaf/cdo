/**
 * Copyright (c) 2004 - 2009 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Stefan Winkler - https://bugs.eclipse.org/bugs/show_bug.cgi?id=259402
 */
package org.eclipse.emf.cdo.tests.config.impl;

import org.eclipse.emf.cdo.internal.server.Repository;
import org.eclipse.emf.cdo.internal.server.RevisionManager;
import org.eclipse.emf.cdo.server.CDOServerUtil;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.IRepositoryProvider;
import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.server.IRepository.Props;
import org.eclipse.emf.cdo.server.db.CDODBUtil;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.emf.cdo.server.mem.MEMStoreUtil;
import org.eclipse.emf.cdo.tests.bundle.OM;
import org.eclipse.emf.cdo.tests.config.IRepositoryConfig;
import org.eclipse.emf.cdo.tests.store.verifier.AbstractDBStoreVerifier;
import org.eclipse.emf.cdo.tests.store.verifier.AuditDBStoreIntegrityVerifier;
import org.eclipse.emf.cdo.tests.store.verifier.NonAuditDBStoreIntegrityVerifier;

import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBAdapter;
import org.eclipse.net4j.db.derby.EmbeddedDerbyAdapter;
import org.eclipse.net4j.db.hsqldb.HSQLDBAdapter;
import org.eclipse.net4j.db.hsqldb.HSQLDBDataSource;
import org.eclipse.net4j.db.mysql.MYSQLAdapter;
import org.eclipse.net4j.util.ObjectUtil;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.io.IOUtil;
import org.eclipse.net4j.util.io.TMPUtil;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import org.apache.derby.jdbc.EmbeddedDataSource;

import javax.sql.DataSource;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Eike Stepper
 */
public abstract class RepositoryConfig extends Config implements IRepositoryConfig
{
  public static final RepositoryConfig[] CONFIGS = { MEM.INSTANCE, //
      DB.Hsqldb.INSTANCE, //
      DB.Derby.INSTANCE, //
      DB.Mysql.INSTANCE };

  public static final String PROP_TEST_REPOSITORY = "test.repository";

  public static final String PROP_TEST_REVISION_MANAGER = "test.repository.revisionmanager";

  public static final String PROP_TEST_STORE = "test.repository.store";

  private static final long serialVersionUID = 1L;

  private transient Map<String, IRepository> repositories;

  public RepositoryConfig(String name)
  {
    super(name);
  }

  public Map<String, String> getRepositoryProperties()
  {
    Map<String, String> repositoryProperties = new HashMap<String, String>();
    initRepositoryProperties(repositoryProperties);

    Map<String, Object> testProperties = getTestProperties();
    if (testProperties != null)
    {
      for (Entry<String, Object> entry : testProperties.entrySet())
      {
        if (entry.getValue() instanceof String)
        {
          repositoryProperties.put(entry.getKey(), (String)entry.getValue());
        }
      }
    }

    return repositoryProperties;
  }

  public synchronized IRepository getRepository(String name)
  {
    IRepository repository = repositories.get(name);
    if (repository == null)
    {
      repository = getTestRepository();
      if (repository != null && !ObjectUtil.equals(repository.getName(), name))
      {
        repository = null;
      }

      if (repository == null)
      {
        repository = createRepository(name);
      }

      repositories.put(name, repository);
      LifecycleUtil.activate(repository);
    }

    return repository;
  }

  protected void initRepositoryProperties(Map<String, String> props)
  {
    props.put(Props.OVERRIDE_UUID, ""); // UUID := name !!!
    props.put(Props.CURRENT_LRU_CAPACITY, "10000");
    props.put(Props.REVISED_LRU_CAPACITY, "10000");
  }

  @Override
  public void setUp() throws Exception
  {
    super.setUp();
    repositories = new HashMap<String, IRepository>();
    IManagedContainer serverContainer = getCurrentTest().getServerContainer();
    CDOServerUtil.prepareContainer(serverContainer, new IRepositoryProvider()
    {
      public IRepository getRepository(String name)
      {
        return repositories.get(name);
      }
    });

    // Start default repository
    getRepository(REPOSITORY_NAME);
  }

  @Override
  public void tearDown() throws Exception
  {
    for (Object repository : repositories.values().toArray())
    {
      LifecycleUtil.deactivate(repository);
    }

    repositories.clear();
    repositories = null;
    super.tearDown();
  }

  protected IRepository createRepository(String name)
  {
    IStore store = getTestStore();
    if (store == null)
    {
      store = createStore();
    }

    Map<String, String> props = getRepositoryProperties();
    Repository repository = (Repository)CDOServerUtil.createRepository(name, store, props);
    RevisionManager revisionManager = getTestRevisionManager();
    if (revisionManager != null)
    {
      repository.setRevisionManager(revisionManager);
    }

    return repository;
  }

  protected abstract IStore createStore();

  protected IRepository getTestRepository()
  {
    return (IRepository)getTestProperty(PROP_TEST_REPOSITORY);
  }

  protected RevisionManager getTestRevisionManager()
  {
    return (RevisionManager)getTestProperty(PROP_TEST_REVISION_MANAGER);
  }

  protected IStore getTestStore()
  {
    return (IStore)getTestProperty(PROP_TEST_STORE);
  }

  /**
   * @author Eike Stepper
   */
  public static class MEM extends RepositoryConfig
  {
    public static final MEM INSTANCE = new MEM();

    private static final long serialVersionUID = 1L;

    public MEM()
    {
      super("MEM");
    }

    @Override
    protected IStore createStore()
    {
      return MEMStoreUtil.createMEMStore();
    }
  }

  /**
   * @author Eike Stepper
   */
  public static abstract class DB extends RepositoryConfig
  {
    private static final long serialVersionUID = 1L;

    public DB(String name)
    {
      super(name);
    }

    @Override
    protected IStore createStore()
    {
      IMappingStrategy mappingStrategy = createMappingStrategy();
      IDBAdapter dbAdapter = createDBAdapter();
      DataSource dataSource = createDataSource();
      return CDODBUtil.createStore(mappingStrategy, dbAdapter, DBUtil.createConnectionProvider(dataSource));
    }

    protected abstract IMappingStrategy createMappingStrategy();

    protected abstract IDBAdapter createDBAdapter();

    protected abstract DataSource createDataSource();

    /**
     * @author Eike Stepper
     */
    public static class Hsqldb extends DB
    {
      private static final long serialVersionUID = 1L;

      public static boolean USE_VERIFIER = false;

      public static final Hsqldb INSTANCE = new Hsqldb("HSQLDB");

      private transient HSQLDBDataSource dataSource;

      public Hsqldb(String name)
      {
        super(name);
      }

      @Override
      protected IMappingStrategy createMappingStrategy()
      {
        return CDODBUtil.createHorizontalMappingStrategy();
      }

      @Override
      protected IDBAdapter createDBAdapter()
      {
        return new HSQLDBAdapter();
      }

      @Override
      protected DataSource createDataSource()
      {
        dataSource = new HSQLDBDataSource();
        dataSource.setDatabase("jdbc:hsqldb:mem:dbtest");
        dataSource.setUser("sa");

        try
        {
          dataSource.setLogWriter(new PrintWriter(System.err));
        }
        catch (SQLException ex)
        {
          OM.LOG.warn(ex.getMessage());
        }

        return dataSource;
      }

      @Override
      public void tearDown() throws Exception
      {
        try
        {
          if (USE_VERIFIER)
          {
            IRepository testRepository = getRepository(REPOSITORY_NAME);
            if (testRepository != null)
            {
              getVerifier(testRepository).verify();
            }
          }
        }
        finally
        {
          try
          {
            super.tearDown();
          }
          finally
          {
            shutDownHsqldb();
          }
        }
      }

      protected AbstractDBStoreVerifier getVerifier(IRepository repository)
      {
        return new AuditDBStoreIntegrityVerifier(repository);
      }

      private void shutDownHsqldb() throws SQLException
      {
        if (dataSource != null)
        {
          Connection connection = null;
          Statement statement = null;

          try
          {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            statement.execute("SHUTDOWN");
          }
          finally
          {
            DBUtil.close(statement);
            DBUtil.close(connection);
            dataSource = null;
          }
        }
      }
    }

    public static class HsqldbNonAudit extends Hsqldb
    {
      private static final long serialVersionUID = 1L;

      public static final HsqldbNonAudit INSTANCE = new HsqldbNonAudit("DBStore: Hsqldb (non audit)");

      public HsqldbNonAudit(String name)
      {
        super(name);
      }

      @Override
      protected void initRepositoryProperties(Map<String, String> props)
      {
        super.initRepositoryProperties(props);
        props.put(IRepository.Props.SUPPORTING_AUDITS, "false");
      }

      @Override
      protected IMappingStrategy createMappingStrategy()
      {
        return CDODBUtil.createHorizontalNonAuditMappingStrategy();
      }

      @Override
      protected AbstractDBStoreVerifier getVerifier(IRepository repository)
      {
        return new NonAuditDBStoreIntegrityVerifier(repository);
      }
    }

    /**
     * @author Eike Stepper
     */
    public static class Derby extends DB
    {
      private static final long serialVersionUID = 1L;

      public static final Derby INSTANCE = new Derby("DBStore: Derby");

      private transient File dbFolder;

      private transient EmbeddedDataSource dataSource;

      public Derby(String name)
      {
        super(name);
      }

      @Override
      protected IMappingStrategy createMappingStrategy()
      {
        return CDODBUtil.createHorizontalMappingStrategy();
      }

      @Override
      protected IDBAdapter createDBAdapter()
      {
        return new EmbeddedDerbyAdapter();
      }

      @Override
      protected DataSource createDataSource()
      {
        dbFolder = TMPUtil.createTempFolder("derby_", null, new File("/temp"));
        deleteDBFolder();

        dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName(dbFolder.getAbsolutePath());
        dataSource.setCreateDatabase("create");
        return dataSource;
      }

      @Override
      public void tearDown() throws Exception
      {
        deleteDBFolder();
        super.tearDown();
      }

      private void deleteDBFolder()
      {
        IOUtil.delete(dbFolder);
      }
    }

    /**
     * @author Simon McDuff
     */
    public static class Mysql extends DB
    {
      private static final long serialVersionUID = 1L;

      public static final Mysql INSTANCE = new Mysql("DBStore: Mysql");

      private transient MysqlDataSource setupDataSource;

      private transient MysqlDataSource dataSource;

      public Mysql(String name)
      {
        super(name);
      }

      @Override
      protected IMappingStrategy createMappingStrategy()
      {
        return CDODBUtil.createHorizontalMappingStrategy();
      }

      @Override
      protected IDBAdapter createDBAdapter()
      {
        return new MYSQLAdapter();
      }

      private MysqlDataSource getSetupDataSource()
      {
        if (setupDataSource == null)
        {
          setupDataSource = new MysqlDataSource();
          setupDataSource.setUrl("jdbc:mysql://localhost");
          setupDataSource.setUser("sa");
        }

        return setupDataSource;
      }

      @Override
      public void setUp() throws Exception
      {
        dropDatabase();
        Connection connection = null;
        try
        {
          connection = getSetupDataSource().getConnection();
          connection.prepareStatement("create database cdodb1").execute();
        }
        catch (SQLException ignore)
        {

        }
        finally
        {
          connection.close();
        }
        super.setUp();
      }

      @Override
      protected DataSource createDataSource()
      {
        dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost/cdodb1");
        dataSource.setUser("sa");
        return dataSource;
      }

      @Override
      public void tearDown() throws Exception
      {
        super.tearDown();
        dropDatabase();
      }

      private void dropDatabase() throws Exception
      {
        Connection connection = null;
        try
        {
          connection = getSetupDataSource().getConnection();
          connection.prepareStatement("DROP database cdodb1").execute();
        }
        catch (SQLException ignore)
        {

        }
        finally
        {
          connection.close();
        }
      }

    }
  }
}
