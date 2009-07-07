/**
 * Copyright (c) 2004 - 2009 Martin Taal and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Martin Taal 
 */
package org.eclipse.emf.cdo.server.internal.hibernate.tuplizer;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDExternal;
import org.eclipse.emf.cdo.common.id.CDOIDMeta;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.common.revision.CDORevision;

import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

/**
 * A user type which can handle both {@link CDOIDMeta} as well as {@link CDOIDExternal}. Both are stored in a single
 * varchar field.
 * 
 * @author <a href="mailto:mtaal@elver.org">Martin Taal</a>
 */
public class CDOIDExternalUserType implements UserType, ParameterizedType
{

  private static final String META_PREFIX = "MID";

  private static final int[] SQL_TYPES = { Types.VARCHAR };

  public int[] sqlTypes()
  {
    return SQL_TYPES;
  }

  public Class<?> returnedClass()
  {
    return CDOID.class;
  }

  public boolean isMutable()
  {
    return false;
  }

  public Object deepCopy(Object value)
  {
    return value;
  }

  public boolean equals(Object x, Object y)
  {
    if (x == y)
    {
      return true;
    }
    if (x == null || y == null)
    {
      return false;
    }
    return x.equals(y);
  }

  public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws SQLException
  {
    final String data = resultSet.getString(names[0]);
    if (data == null)
    {
      return null;
    }
    if (data.startsWith(META_PREFIX))
    {
      return CDOIDUtil.createMeta(Long.parseLong(data.substring(META_PREFIX.length())));
    }
    return CDOIDUtil.createExternal(data);
  }

  public void nullSafeSet(PreparedStatement statement, Object value, int index) throws SQLException
  {
    if (value == null)
    {
      statement.setNull(index, Types.VARCHAR);
      return;
    }
    final Object localValue;
    if (value instanceof CDORevision)
    {
      localValue = ((CDORevision)value).getID();
    }
    else
    {
      localValue = value;
    }

    if (localValue instanceof CDOIDMeta)
    {
      statement.setString(index, META_PREFIX + ((CDOIDMeta)localValue).getLongValue());
    }
    else if (localValue instanceof CDOIDExternal)
    {
      statement.setString(index, ((CDOIDExternal)localValue).getURI());
    }
    else
    {
      throw new IllegalArgumentException("CDOID type " + localValue.getClass().getName() + " not supported here");
    }
  }

  public Serializable disassemble(Object value)
  {
    return (Serializable)value;
  }

  public Object assemble(Serializable cachedValue, Object owner)
  {
    return cachedValue;
  }

  public Object replace(Object original, Object target, Object owner)
  {
    return original;
  }

  public int hashCode(Object x)
  {
    return x.hashCode();
  }

  public void setParameterValues(Properties parameters)
  {
  }
}
