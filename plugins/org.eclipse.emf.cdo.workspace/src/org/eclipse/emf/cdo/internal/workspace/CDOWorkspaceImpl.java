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
package org.eclipse.emf.cdo.internal.workspace;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.CDORevisionHandler;
import org.eclipse.emf.cdo.internal.server.Repository;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.server.CDOServerUtil;
import org.eclipse.emf.cdo.server.IRepository.Props;
import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.server.IStoreAccessor;
import org.eclipse.emf.cdo.server.StoreThreadLocal;
import org.eclipse.emf.cdo.server.net4j.CDONet4jServerUtil;
import org.eclipse.emf.cdo.session.CDOSessionConfiguration;
import org.eclipse.emf.cdo.session.CDOSessionConfigurationFactory;
import org.eclipse.emf.cdo.spi.common.branch.InternalCDOBranchManager;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageRegistry;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageUnit;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;
import org.eclipse.emf.cdo.spi.server.InternalRepository;
import org.eclipse.emf.cdo.spi.server.InternalStore;
import org.eclipse.emf.cdo.transaction.CDOCommitContext;
import org.eclipse.emf.cdo.transaction.CDODefaultTransactionHandler;
import org.eclipse.emf.cdo.transaction.CDOMerger;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.cdo.workspace.CDOWorkspace;
import org.eclipse.emf.cdo.workspace.CDOWorkspaceBaseline;

import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.jvm.IJVMAcceptor;
import org.eclipse.net4j.jvm.IJVMConnector;
import org.eclipse.net4j.jvm.JVMUtil;
import org.eclipse.net4j.util.container.ContainerUtil;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.monitor.Monitor;
import org.eclipse.net4j.util.om.monitor.OMMonitor;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.spi.cdo.InternalCDOSession;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eike Stepper
 */
public class CDOWorkspaceImpl implements CDOWorkspace
{
  private static final String PROP_BRANCH_PATH = "org.eclipse.emf.cdo.workspace.branchPath"; //$NON-NLS-1$

  private static final String PROP_TIME_STAMP = "org.eclipse.emf.cdo.workspace.timeSTamp"; //$NON-NLS-1$

  private IManagedContainer container;

  private InternalRepository localRepository;

  private IJVMAcceptor localAcceptor;

  private InternalCDOSession localSession;

  private CDOSessionConfigurationFactory remoteSessionConfigurationFactory;

  private CDOWorkspaceBaseline baseline;

  private String baselineBranch;

  private long baselineTime;

  private CDOWorkspaceImpl(IStore local, CDOWorkspaceBaseline baseline)
  {
    container = createContainer(local);
    localRepository = createLocalRepository(local);
    localAcceptor = getLocalAcceptor();
    localSession = openLocalSession();
    this.baseline = baseline;
  }

  public CDOWorkspaceImpl(IStore local, CDOWorkspaceBaseline baseline, CDOSessionConfigurationFactory remote,
      String branchPath, long timeStamp)
  {
    this(local, baseline);
    remoteSessionConfigurationFactory = remote;
    baselineBranch = branchPath;
    baselineTime = timeStamp;

    baseline.init(localSession.getPackageRegistry(), branchPath, timeStamp);
    checkout();
  }

  public CDOWorkspaceImpl(IStore local, CDOWorkspaceBaseline baseline, CDOSessionConfigurationFactory remote)
  {
    this(local, baseline);
    remoteSessionConfigurationFactory = remote;
    open();
  }

  protected void checkout()
  {
    final OMMonitor monitor = new Monitor();
    final Object[] context = { null };

    final IStoreAccessor accessor = localRepository.getStore().getWriter(null);
    StoreThreadLocal.setAccessor(accessor);

    try
    {
      InternalCDOSession session = openRemoteSession();

      try
      {
        InternalCDOPackageUnit[] packageUnits = session.getPackageRegistry().getPackageUnits();
        context[0] = accessor.rawStore(packageUnits, context[0], monitor);

        InternalCDOPackageRegistry repositoryPackageRegistry = localRepository.getPackageRegistry(false);
        InternalCDOPackageRegistry sessionPackageRegistry = localSession.getPackageRegistry();
        for (InternalCDOPackageUnit packageUnit : packageUnits)
        {
          repositoryPackageRegistry.putPackageUnit(packageUnit);
          sessionPackageRegistry.putPackageUnit(packageUnit);
        }

        CDORevisionHandler handler = new CDORevisionHandler()
        {
          public boolean handleRevision(CDORevision revision)
          {
            context[0] = accessor.rawStore((InternalCDORevision)revision, context[0], monitor);
            return true;
          }
        };

        CDOBranch branch = session.getBranchManager().getBranch(baselineBranch);
        session.getSessionProtocol().handleRevisions(null, branch, false, baselineTime, false, handler);
      }
      finally
      {
        LifecycleUtil.deactivate(session);
      }

      accessor.rawCommit(context[0], monitor);
      storeBranchPoint(baselineBranch, baselineTime);
    }
    finally
    {
      StoreThreadLocal.release();
      monitor.done();
    }
  }

  protected void open()
  {
  }

  public InternalRepository getLocalRepository()
  {
    return localRepository;
  }

  public CDOView openView()
  {
    CDOView view = localSession.openView();
    initView(view);
    return view;
  }

  public CDOView openView(ResourceSet resourceSet)
  {
    CDOView view = localSession.openView(resourceSet);
    initView(view);
    return view;
  }

  public CDOTransaction openTransaction()
  {
    CDOTransaction transaction = localSession.openTransaction();
    initView(transaction);
    return transaction;
  }

  public CDOTransaction openTransaction(ResourceSet resourceSet)
  {
    CDOTransaction transaction = localSession.openTransaction(resourceSet);
    initView(transaction);
    return transaction;
  }

  public CDOTransaction update(CDOMerger merger)
  {
    return update(merger, CDOBranch.MAIN_BRANCH_NAME);
  }

  public CDOTransaction update(CDOMerger merger, String branchPath)
  {
    return update(merger, branchPath, CDOBranchPoint.UNSPECIFIED_DATE);
  }

  public CDOTransaction update(CDOMerger merger, String branchPath, long timeStamp)
  {
    // TODO: implement CDOWorkspaceImpl.update(merger, branchPath, timeStamp)
    throw new UnsupportedOperationException();
  }

  public void revert()
  {
    // TODO: implement CDOWorkspaceImpl.revert()
    throw new UnsupportedOperationException();
  }

  public void replace(String branchPath, long timeStamp)
  {
    // TODO: implement CDOWorkspaceImpl.replace(branchPath, timeStamp)
    throw new UnsupportedOperationException();
  }

  public void commit(String comment)
  {
    // TODO: implement CDOWorkspaceImpl.commit(comment)
    throw new UnsupportedOperationException();
  }

  public synchronized void close()
  {
    LifecycleUtil.deactivate(localSession);
    localSession = null;

    LifecycleUtil.deactivate(localAcceptor);
    localAcceptor = null;

    LifecycleUtil.deactivate(localRepository);
    localRepository = null;
  }

  public synchronized boolean isClosed()
  {
    return localRepository == null;
  }

  protected IManagedContainer getContainer()
  {
    return container;
  }

  protected String getLocalAcceptorName()
  {
    return "acceptor-for-" + localRepository.getUUID();
  }

  protected IJVMAcceptor getLocalAcceptor()
  {
    String localAcceptorName = getLocalAcceptorName();
    IJVMAcceptor acceptor = JVMUtil.getAcceptor(container, localAcceptorName);
    return acceptor;
  }

  protected IJVMConnector getLocalConnector()
  {
    String localAcceptorName = getLocalAcceptorName();
    return JVMUtil.getConnector(container, localAcceptorName);
  }

  protected InternalCDOSession getLocalSession()
  {
    return localSession;
  }

  protected IManagedContainer createContainer(IStore local)
  {
    IManagedContainer container = ContainerUtil.createContainer();
    Net4jUtil.prepareContainer(container);
    JVMUtil.prepareContainer(container);
    CDONet4jServerUtil.prepareContainer(container);
    container.activate();
    return container;
  }

  protected InternalRepository createLocalRepository(IStore store)
  {
    Map<String, String> props = new HashMap<String, String>();
    props.put(Props.OVERRIDE_UUID, ""); // UUID := name !!!
    props.put(Props.SUPPORTING_AUDITS, "false");
    props.put(Props.SUPPORTING_BRANCHES, "false");

    Repository repository = new Repository.Default()
    {
      @Override
      protected void initMainBranch(InternalCDOBranchManager branchManager, long lastCommitTimeStamp)
      {
        // Mark the main branch local so that new objects get local IDs
        branchManager.initMainBranch(true, lastCommitTimeStamp);
      }

      @Override
      protected void initRootResource()
      {
        // Don't create the root resource as it will be checked out
        setState(State.INITIAL);
      }
    };

    repository.setName("local");
    repository.setStore((InternalStore)store);
    repository.setProperties(props);

    CDOServerUtil.addRepository(container, repository);
    return repository;
  }

  protected InternalCDOSession openLocalSession()
  {
    IJVMConnector connector = getLocalConnector();
    String repositoryName = localRepository.getName();

    org.eclipse.emf.cdo.net4j.CDOSessionConfiguration configuration = CDONet4jUtil.createSessionConfiguration();
    configuration.setConnector(connector);
    configuration.setRepositoryName(repositoryName);
    return (InternalCDOSession)configuration.openSession();
  }

  protected void initView(CDOView view)
  {
    if (view instanceof CDOTransaction)
    {
      CDOTransaction transaction = (CDOTransaction)view;
      transaction.addTransactionHandler(new CDODefaultTransactionHandler()
      {
        @Override
        public void committedTransaction(CDOTransaction transaction, CDOCommitContext commitContext)
        {
          baseline.updateAfterCommit(transaction);
        }
      });
    }
  }

  protected CDOSessionConfigurationFactory getRemoteSessionConfigurationFactory()
  {
    return remoteSessionConfigurationFactory;
  }

  protected InternalCDOSession openRemoteSession()
  {
    CDOSessionConfiguration configuration = remoteSessionConfigurationFactory.createSessionConfiguration();
    return (InternalCDOSession)configuration.openSession();
  }

  protected void storeBranchPoint(String branchPath, long timeStamp)
  {
    Map<String, String> props = new HashMap<String, String>();
    props.put(PROP_BRANCH_PATH, branchPath);
    props.put(PROP_TIME_STAMP, String.valueOf(timeStamp));
    localRepository.setProperties(props);
  }
}