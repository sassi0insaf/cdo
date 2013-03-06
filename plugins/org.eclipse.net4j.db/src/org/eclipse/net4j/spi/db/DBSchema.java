/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.spi.db;

import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBAdapter;
import org.eclipse.net4j.db.IDBConnectionProvider;
import org.eclipse.net4j.db.IDBRowHandler;
import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.db.ddl.IDBIndex;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.db.ddl.delta.IDBSchemaDelta;
import org.eclipse.net4j.internal.db.ddl.DBField;
import org.eclipse.net4j.internal.db.ddl.DBIndex;
import org.eclipse.net4j.internal.db.ddl.DBTable;
import org.eclipse.net4j.internal.db.ddl.delta.DBSchemaDelta;
import org.eclipse.net4j.util.StringUtil;

import javax.sql.DataSource;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A useful base class for implementing custom {@link IDBSchema DB schemas}.
 *
 * @author Eike Stepper
 */
public class DBSchema extends DBSchemaElement implements IDBSchema
{
  /**
   * @since 4.2
   */
  public static final IDBTable[] NO_TABLES = {};

  private static final long serialVersionUID = 1L;

  private Map<String, DBTable> tables = new HashMap<String, DBTable>();

  private transient boolean locked;

  private transient int indexCounter;

  public DBSchema(String name)
  {
    super(name);
  }

  /**
   * @since 4.2
   */
  public DBSchema(IDBSchema source)
  {
    super(source.getName());

    for (IDBTable sourceTable : source.getTables())
    {
      DBTable table = (DBTable)addTable(sourceTable.getName());

      for (IDBField sourceField : sourceTable.getFields())
      {
        table.addField(sourceField.getName(), sourceField.getType(), sourceField.getPrecision(),
            sourceField.getScale(), sourceField.isNotNull());
      }

      for (IDBIndex sourceIndex : sourceTable.getIndices())
      {
        DBIndex index = table.addIndex(sourceIndex.getName(), sourceIndex.getType());
        for (IDBField sourceField : sourceIndex.getFields())
        {
          DBField field = table.getField(sourceField.getPosition());
          index.addIndexField(field);
        }
      }
    }
  }

  /**
   * Constructor for deserialization.
   *
   * @since 4.2
   */
  protected DBSchema()
  {
  }

  public String getFullName()
  {
    return getName();
  }

  public IDBSchema getSchema()
  {
    return this;
  }

  /**
   * @since 2.0
   */
  public IDBTable addTable(String name) throws DBException
  {
    assertUnlocked();
    if (tables.containsKey(name))
    {
      throw new DBException("DBTable exists: " + name); //$NON-NLS-1$
    }

    DBTable table = new DBTable(this, name);
    tables.put(table.getName(), table);
    return table;
  }

  /**
   * @since 4.0
   */
  public IDBTable removeTable(String name)
  {
    assertUnlocked();
    name = name(name);
    return tables.remove(name);
  }

  /**
   * @since 2.0
   */
  public IDBTable getTable(String name)
  {
    name = name(name);
    return tables.get(name);
  }

  /**
   * @since 2.0
   */
  public IDBTable[] getTables()
  {
    return tables.values().toArray(new DBTable[tables.size()]);
  }

  /**
   * @since 4.2
   */
  public boolean isEmpty()
  {
    return tables.isEmpty();
  }

  /**
   * @since 4.2
   */
  public IDBTable[] getElements()
  {
    return getTables();
  }

  /**
   * @since 4.2
   */
  public void remove()
  {
    assertUnlocked();
    tables.clear();
  }

  public boolean isLocked()
  {
    return locked;
  }

  public boolean lock()
  {
    return locked = true;
  }

  /**
   * @since 4.2
   */
  public boolean unlock()
  {
    return locked = false;
  }

  public void assertUnlocked() throws DBException
  {
    if (locked)
    {
      throw new DBException("Schema locked: " + this); //$NON-NLS-1$
    }
  }

  public Set<IDBTable> create(IDBAdapter dbAdapter, Connection connection) throws DBException
  {
    return dbAdapter.createTables(tables.values(), connection);
  }

  public Set<IDBTable> create(IDBAdapter dbAdapter, DataSource dataSource) throws DBException
  {
    return create(dbAdapter, DBUtil.createConnectionProvider(dataSource));
  }

  public Set<IDBTable> create(IDBAdapter dbAdapter, IDBConnectionProvider connectionProvider) throws DBException
  {
    Connection connection = null;

    try
    {
      connection = connectionProvider.getConnection();
      if (connection == null)
      {
        throw new DBException("No connection available from " + connectionProvider); //$NON-NLS-1$
      }

      return create(dbAdapter, connection);
    }
    finally
    {
      DBUtil.close(connection);
    }
  }

  public void drop(IDBAdapter dbAdapter, Connection connection) throws DBException
  {
    dbAdapter.dropTables(tables.values(), connection);
  }

  public void drop(IDBAdapter dbAdapter, DataSource dataSource) throws DBException
  {
    drop(dbAdapter, DBUtil.createConnectionProvider(dataSource));
  }

  public void drop(IDBAdapter dbAdapter, IDBConnectionProvider connectionProvider) throws DBException
  {
    Connection connection = null;

    try
    {
      connection = connectionProvider.getConnection();
      drop(dbAdapter, connection);
    }
    finally
    {
      DBUtil.close(connection);
    }
  }

  public void export(Connection connection, PrintStream out) throws DBException
  {
    for (IDBTable table : getTables())
    {
      export(table, connection, out);
    }
  }

  private void export(final IDBTable table, Connection connection, final PrintStream out)
  {
    if (DBUtil.select(connection, new IDBRowHandler()
    {
      public boolean handle(int row, Object... values)
      {
        if (row == 0)
        {
          String tableName = table.getName();
          out.println(tableName);
          for (int i = 0; i < tableName.length(); i++)
          {
            out.print("="); //$NON-NLS-1$
          }

          out.println();
        }

        out.println(Arrays.asList(values));
        return true;
      }
    }, table.getFields()) > 0)

    {
      out.println();
    }
  }

  public void export(DataSource dataSource, PrintStream out) throws DBException
  {
    export(DBUtil.createConnectionProvider(dataSource), out);
  }

  public void export(IDBConnectionProvider connectionProvider, PrintStream out) throws DBException
  {
    Connection connection = null;

    try
    {
      connection = connectionProvider.getConnection();
      export(connection, out);
    }
    finally
    {
      DBUtil.close(connection);
    }
  }

  /**
   * @since 4.2
   */
  public IDBSchemaDelta compare(IDBSchema oldSchema)
  {
    return new DBSchemaDelta(this, oldSchema);
  }

  /**
   * @since 4.2
   */
  public String createIndexName(IDBTable table, IDBIndex.Type type, IDBField[] fields, int position)
  {
    return "I" + System.currentTimeMillis() + "_" + ++indexCounter;
  }

  @Override
  public void dump(Writer writer) throws IOException
  {
    writer.append("SCHEMA ");
    writer.append(getName());
    writer.append(StringUtil.NL);
    for (DBTable table : tables.values())
    {
      table.dump(writer);
    }
  }
}
