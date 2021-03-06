/*
 * Copyright (c) 2010-2013 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.internal.efs;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.session.CDOSessionConfiguration;
import org.eclipse.emf.cdo.util.CDOURIData;
import org.eclipse.emf.cdo.view.CDOView;

import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.util.collection.Pair;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.container.IPluginContainer;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileSystem;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Eike Stepper
 */
public abstract class CDOFileSystem extends FileSystem
{
  private Map<Pair<String, String>, CDOSession> sessions = new HashMap<Pair<String, String>, CDOSession>();

  private Map<URI, CDOView> views = new HashMap<URI, CDOView>();

  protected CDOFileSystem()
  {
  }

  @Override
  public int attributes()
  {
    return /* EFS.ATTRIBUTE_READ_ONLY | */EFS.ATTRIBUTE_OTHER_READ | EFS.ATTRIBUTE_OTHER_WRITE;
  }

  @Override
  public boolean isCaseSensitive()
  {
    return true;
  }

  @Override
  public IFileStore getStore(URI uri)
  {
    CDOURIData data = new CDOURIData(uri.toString());
    // String authority = uri.getAuthority();
    // IPath path = new Path(uri.getPath());
    // String repositoryName = path.segment(0);
    // path = path.removeFirstSegments(1);
    //
    // IPath branchPath = Path.EMPTY;
    // long timeStamp = CDOBranchPoint.UNSPECIFIED_DATE;
    //
    // while (path.segmentCount() != 0)
    // {
    // String segment = path.segment(0);
    // path = path.removeFirstSegments(1);
    //
    // if (segment.startsWith("@"))
    // {
    // if (segment.length() != 1)
    // {
    // if (!segment.equals("@HEAD"))
    // {
    // timeStamp = Long.parseLong(segment.substring(1));
    // }
    // }
    //
    // break;
    // }
    //
    // branchPath = branchPath.append(segment);
    // }
    //
    // int segments = branchPath.segmentCount();
    // if (segments == 0 || segments == 1 && !branchPath.segment(0).equals(CDOBranch.MAIN_BRANCH_NAME))
    // {
    // branchPath = new Path(CDOBranch.MAIN_BRANCH_NAME).append(branchPath);
    // }

    String authority = data.getAuthority();
    String repositoryName = data.getRepositoryName();
    IPath path = data.getResourcePath();
    IPath branchPath = data.getBranchPath();
    long timeStamp = data.getTimeStamp();
    CDOFileRoot root = new CDOFileRoot(this, authority, repositoryName, branchPath, timeStamp);
    if (path.isEmpty())
    {
      return root;
    }

    return root.getFileStore(path);
  }

  public CDOView getView(CDOFileRoot root, IProgressMonitor monitor)
  {
    URI uri = root.toURI();
    CDOView view = views.get(uri);
    if (view == null)
    {
      view = openView(root, monitor);
      views.put(uri, view);
    }

    return view;
  }

  protected CDOView openView(CDOFileRoot root, IProgressMonitor monitor)
  {
    String authority = root.getAuthority();
    String repositoryName = root.getRepositoryName();
    String branchPath = root.getBranchPath().toPortableString();
    final long timeStamp = root.getTimeStamp();

    final CDOSession session = getSession(authority, repositoryName, monitor);
    final CDOBranch branch = session.getBranchManager().getBranch(branchPath);

    return InfiniteProgress.call("Open view", new Callable<CDOView>()
    {
      public CDOView call() throws Exception
      {
        return session.openView(branch, timeStamp);
      }
    });
  }

  protected CDOSession getSession(String authority, String repositoryName, IProgressMonitor monitor)
  {
    Pair<String, String> sessionKey = Pair.create(authority, repositoryName);
    CDOSession session = sessions.get(sessionKey);
    if (session == null)
    {
      final CDOSessionConfiguration configuration = createSessionConfiguration(authority, repositoryName, monitor);
      session = InfiniteProgress.call("Open session", new Callable<CDOSession>()
      {
        public CDOSession call() throws Exception
        {
          return configuration.openSession();
        }
      });

      sessions.put(sessionKey, session);
    }

    return session;
  }

  protected IManagedContainer getContainer()
  {
    return IPluginContainer.INSTANCE;
  }

  protected abstract CDOSessionConfiguration createSessionConfiguration(String authority, String repositoryName,
      IProgressMonitor monitor);

  /**
   * @author Eike Stepper
   */
  public static abstract class Net4j extends CDOFileSystem
  {
    private String connectorType;

    protected Net4j(String connectorType)
    {
      this.connectorType = connectorType;
    }

    protected IConnector getConnector(final String authority, IProgressMonitor monitor)
    {
      return InfiniteProgress.call("Open connection", new Callable<IConnector>()
      {
        public IConnector call() throws Exception
        {
          return Net4jUtil.getConnector(getContainer(), connectorType, authority);
        }
      });
    }

    @Override
    protected CDOSessionConfiguration createSessionConfiguration(String authority, String repositoryName,
        IProgressMonitor monitor)
    {
      CDONet4jSessionConfiguration configuration = CDONet4jUtil.createNet4jSessionConfiguration();
      configuration.setConnector(getConnector(authority, monitor));
      configuration.setRepositoryName(repositoryName);
      return configuration;
    }

    /**
     * @author Eike Stepper
     */
    public static class TCP extends Net4j
    {
      /*
       * Must be public to be instantiatable by the extension registry.
       */
      public TCP()
      {
        super("tcp");
      }
    }
  }
}
