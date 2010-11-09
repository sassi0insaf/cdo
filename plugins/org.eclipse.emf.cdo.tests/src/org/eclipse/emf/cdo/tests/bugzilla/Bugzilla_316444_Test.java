/**
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Martin Fluegge - initial API and implementation
 */
package org.eclipse.emf.cdo.tests.bugzilla;

import org.eclipse.emf.cdo.internal.server.Repository;
import org.eclipse.emf.cdo.internal.server.TransactionCommitContext;
import org.eclipse.emf.cdo.net4j.CDOSession;
import org.eclipse.emf.cdo.server.ContainmentCycleDetectedException;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.spi.server.InternalCommitContext;
import org.eclipse.emf.cdo.spi.server.InternalRepository;
import org.eclipse.emf.cdo.spi.server.InternalTransaction;
import org.eclipse.emf.cdo.tests.AbstractCDOTest;
import org.eclipse.emf.cdo.tests.config.impl.RepositoryConfig;
import org.eclipse.emf.cdo.tests.model3.NodeA;
import org.eclipse.emf.cdo.tests.model3.NodeB;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;

import org.eclipse.net4j.util.WrappedException;

import org.eclipse.emf.ecore.resource.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Martin Fluegge
 * @since 4.0
 */
public class Bugzilla_316444_Test extends AbstractCDOTest
{
  private static final String REPOSITORY_NAME = "repo1";

  private static final String RESOURCE_PATH = "/my/resource";

  public int idSessionA;

  public int idSessionB;

  private int idInitSession;

  private Object monitor = new Object();

  private CountDownLatch latch = new CountDownLatch(2);

  private boolean finishedSessionA = false;

  private List<Exception> exceptions = new ArrayList<Exception>();

  @Override
  protected void doSetUp() throws Exception
  {
    createRepository();
    super.doSetUp();
  }

  private void createRepository()
  {
    Repository repository = new Repository.Default()
    {
      @Override
      public InternalCommitContext createCommitContext(InternalTransaction transaction)
      {
        return new TransactionCommitContext(transaction)
        {
          @Override
          protected void lockObjects() throws InterruptedException
          {
            int sessionID = getTransaction().getSession().getSessionID();
            if (sessionID == idSessionB)
            {
              synchronized (monitor)
              {
                // Only wait if Session A has not passed the lockObjects
                if (!finishedSessionA)
                {
                  msg("Session B is waiting for Session A");
                  monitor.wait(DEFAULT_TIMEOUT);
                  msg("Session B stopped waiting");
                }
                else
                {
                  msg("Session B - no need to wait. A has already passed lockObjects()");
                }
              }
            }

            msg("Passing lockObjects() " + getTransaction().getSession());

            try
            {
              super.lockObjects();
            }
            catch (Exception e)
            {
              latch.countDown();
              throw new RuntimeException(e);
            }

            msg("Passed lockObjects() " + getTransaction().getSession());

            if (sessionID == idSessionA)
            {
              synchronized (monitor)
              {
                finishedSessionA = true;
                monitor.notifyAll();
                msg("Session A notified others not to wait anymore.");
              }
            }

            // Do nothing for inital session. Otherwise the test will block too early
            if (sessionID != idInitSession)
            {
              latch.countDown();

              try
              {
                assertEquals(true, latch.await(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS));
              }
              catch (InterruptedException ex)
              {
                throw WrappedException.wrap(ex);
              }
            }
          }
        };
      }
    };

    Map<String, String> props = getRepositoryProperties();
    ((InternalRepository)repository).setProperties(props);

    repository.setName(REPOSITORY_NAME);

    Map<String, Object> map = getTestProperties();
    map.put(RepositoryConfig.PROP_TEST_REPOSITORY, repository);
  }

  @Override
  public synchronized Map<String, Object> getTestProperties()
  {
    Map<String, Object> map = super.getTestProperties();
    map.put(IRepository.Props.ENSURE_REFERENTIAL_INTEGRITY, "true");
    return map;
  }

  public void testLockParentWithEAttributeChange() throws Exception
  {
    String resourcePath = RESOURCE_PATH + "1";
    {
      CDOSession session = (CDOSession)openSession(REPOSITORY_NAME);
      idInitSession = session.getSessionID();
      CDOTransaction transaction = session.openTransaction();

      Resource resource = transaction.createResource(resourcePath);

      // -------------- create graph begin ------------------------
      NodeB root = createSimpleNode("root");

      NodeB A = createSimpleNode("A");
      NodeB B = createSimpleNode("B");
      NodeB C = createSimpleNode("C");
      NodeB D = createSimpleNode("D");
      NodeB E = createSimpleNode("E");

      root.getChildren().add(A);
      root.getChildren().add(D);

      A.getChildren().add(B);
      B.getChildren().add(C);

      D.getChildren().add(E);

      resource.getContents().add(root);
      transaction.commit();

      // -------- check for consistency -----------

      checkInitialGraph(root, A, B, C, D, E);

      transaction.close();
      session.close();
    }

    // restartRepository();

    {
      CDOSession session = (CDOSession)openSession(REPOSITORY_NAME);
      idInitSession = session.getSessionID();

      // ----- start threads -----
      ThreadX threadX = new ThreadX(resourcePath);
      ThreadA threadA = new ThreadA(resourcePath);

      threadA.start();
      sleepIfNeeded();
      threadX.start();

      threadX.join(DEFAULT_TIMEOUT);
      threadA.join(DEFAULT_TIMEOUT);

      if (exceptions.size() > 0)
      {
        throw exceptions.get(0);
      }
    }
  }

  public void testMovingSubtree() throws Exception
  {
    exceptions.clear();

    {
      CDOSession session = (CDOSession)openSession(REPOSITORY_NAME);
      idInitSession = session.getSessionID();
      CDOTransaction transaction = session.openTransaction();

      Resource resource = transaction.createResource(RESOURCE_PATH);

      // -------------- create graph begin ------------------------
      NodeB root = createSimpleNode("root");

      NodeB A = createSimpleNode("A");
      NodeB B = createSimpleNode("B");
      NodeB C = createSimpleNode("C");
      NodeB D = createSimpleNode("D");
      NodeB E = createSimpleNode("E");

      root.getChildren().add(A);
      root.getChildren().add(D);

      A.getChildren().add(B);
      B.getChildren().add(C);

      D.getChildren().add(E);

      resource.getContents().add(root);
      transaction.commit();

      // -------- check for consistency -----------

      checkInitialGraph(root, A, B, C, D, E);

      transaction.close();
      session.close();
    }

    // if (!(isConfig(MEM) || isConfig(MEM_AUDITS) || isConfig(MEM_BRANCHES) || isConfig(MEM_OFFLINE)))
    // {
    // // do not restart the repository on MEM store
    // restartRepository();
    // }

    {
      // Just an additional check to make sure that the graph is stored correctly even after repository restart
      CDOSession session = (CDOSession)openSession(REPOSITORY_NAME);
      idInitSession = session.getSessionID();
      CDOTransaction transaction = session.openTransaction();
      Resource resource = transaction.getResource(RESOURCE_PATH, true);

      NodeB root = (NodeB)resource.getContents().get(0);
      assertEquals("root", root.getName());

      NodeB A = getElementFromGraphNodeB(root, "A");
      NodeB B = getElementFromGraphNodeB(root, "B");
      NodeB C = getElementFromGraphNodeB(root, "C");
      NodeB D = getElementFromGraphNodeB(root, "D");
      NodeB E = getElementFromGraphNodeB(root, "E");

      assertNotNull(A);
      assertNotNull(B);
      assertNotNull(C);
      assertNotNull(D);
      assertNotNull(E);

      checkInitialGraph(root, A, B, C, D, E);
    }

    {
      CDOSession session = (CDOSession)openSession(REPOSITORY_NAME);
      idInitSession = session.getSessionID();

      // ----- start threads -----
      ThreadA threadA = new ThreadA(RESOURCE_PATH);
      ThreadB threadB = new ThreadB(RESOURCE_PATH);

      threadA.start();
      sleepIfNeeded();
      threadB.start();

      threadA.join(DEFAULT_TIMEOUT);
      threadB.join(DEFAULT_TIMEOUT);

      if (exceptions.size() > 0)
      {
        Exception exception = exceptions.get(0);
        if (exception instanceof ThreadBShouldHaveThrownAnExceptionException)
        {
          fail(exception.getMessage());
        }
        else
        {
          throw exception;
        }
      }

      session.close();
      msg("finished");
    }
  }

  private void sleepIfNeeded()
  {
    if (isConfig(LEGACY))
    {
      // sleep in legacy while Bug 318816 is not solved
      sleep(1000);
    }
  }

  private void checkInitialGraph(NodeB root, NodeB A, NodeB B, NodeB C, NodeB D, NodeB E)
  {
    assertEquals("A", A.getName());
    assertEquals("B", B.getName());
    assertEquals("C", C.getName());
    assertEquals("D", D.getName());
    assertEquals("E", E.getName());

    assertEquals(root, A.getParent());
    assertEquals(root, A.eContainer());

    assertEquals(root, D.getParent());
    assertEquals(root, D.eContainer());

    assertEquals(A, B.getParent());
    assertEquals(A, B.eContainer());

    assertEquals(B, C.getParent());
    assertEquals(B, C.eContainer());

    assertEquals(D, E.getParent());
    assertEquals(D, E.eContainer());
  }

  private abstract class AbstactTestThread extends Thread
  {
    protected final String resourcePath;

    public AbstactTestThread(String resourcePath)
    {
      this.resourcePath = resourcePath;
    }
  }

  /**
   * @author Martin Fluegge
   */
  private class ThreadA extends AbstactTestThread
  {
    private CDOSession session;

    public ThreadA(String resourcePath)
    {
      super(resourcePath);
      msg("Starting Thread A");
      session = (CDOSession)openSession(REPOSITORY_NAME);
      idSessionA = session.getSessionID();
    }

    @Override
    public void run()
    {
      try
      {
        msg("Started Thread A " + session);
        CDOTransaction transaction = session.openTransaction();
        Resource resource = transaction.getResource(resourcePath, true);

        NodeB root = (NodeB)resource.getContents().get(0);
        assertEquals("root", root.getName());

        NodeB B = getElementFromGraphNodeB(root, "B");
        NodeB E = getElementFromGraphNodeB(root, "E");

        assertEquals("B", B.getName());
        assertEquals("E", E.getName());

        E.getChildren().add(B);

        try
        {
          transaction.commit();
        }
        catch (CommitException ex)
        {
          exceptions.add(ex);
        }

        session.close();
        msg("Finished Thread A " + session);
      }
      catch (Exception e)
      {
        exceptions.add(e);
      }
    }
  }

  /**
   * @author Martin Fluegge
   */
  private class ThreadB extends AbstactTestThread
  {
    private CDOSession session;

    public ThreadB(String resourcePath)
    {
      super(resourcePath);
      msg("Starting Thread B");
      session = (CDOSession)openSession(REPOSITORY_NAME);
      idSessionB = session.getSessionID();
    }

    @Override
    public void run()
    {
      try
      {
        msg("Started Thread B " + session);
        CDOTransaction transaction = session.openTransaction();

        Resource resource = transaction.getResource(resourcePath, true);

        NodeB root = (NodeB)resource.getContents().get(0);
        assertEquals("root", root.getName());
        NodeB C = getElementFromGraphNodeB(root, "C");
        NodeB D = getElementFromGraphNodeB(root, "D");

        assertEquals("C", C.getName());
        assertEquals("D", D.getName());

        C.getChildren().add(D);

        try
        {
          transaction.commit();
        }
        catch (CommitException ex)
        {
          try
          {
            Exception ex1 = (Exception)ex.getCause();
            Exception ex2 = (Exception)ex1.getCause();
            Exception ex3 = (Exception)ex2.getCause();

            if (ex3 == null || !(ex3 instanceof ContainmentCycleDetectedException))
            {
              throw new RuntimeException(ex1);
            }

            msg("Finished (Passed) Thread B " + session);
            return;
          }
          catch (Exception exx)
          {
            throw new RuntimeException(exx);
          }
        }

        exceptions.add(new ThreadBShouldHaveThrownAnExceptionException("Thread B should have thrown an exception"));
        session.close();
      }
      catch (Exception e)
      {
        exceptions.add(e);
        msg("Finished Thread B " + session);
      }
    }
  }

  /**
   * @author Martin Fluegge
   */
  private class ThreadX extends AbstactTestThread
  {
    private CDOSession session;

    public ThreadX(String resourcePath)
    {
      super(resourcePath);
      msg("Starting Thread X");
      session = (CDOSession)openSession(REPOSITORY_NAME);
      idSessionB = session.getSessionID();
    }

    @Override
    public void run()
    {
      try
      {
        msg("Started Thread X" + session);
        CDOTransaction transaction = session.openTransaction();
        Resource resource = transaction.getResource(resourcePath, true);

        NodeB root = (NodeB)resource.getContents().get(0);
        assertEquals("root", root.getName());

        NodeB D = getElementFromGraphNodeB(root, "D");

        assertEquals("D", D.getName());
        D.setName("DD");

        try
        {
          transaction.commit();
        }
        catch (CommitException ex)
        {
          exceptions.add(ex);
        }

        session.close();
        msg("Finished Thread X");
      }
      catch (Exception e)
      {
        exceptions.add(e);
      }
    }
  }

  private NodeB createSimpleNode(String name)
  {
    NodeB y = getModel3Factory().createNodeB();
    y.setName(name);
    return y;
  }

  private NodeB getElementFromGraphNodeB(NodeB node, String name)
  {
    if (node.getName().equals(name))
    {
      return node;
    }

    for (NodeB child : node.getChildren())
    {
      NodeB elementFromGraph = getElementFromGraphNodeB(child, name);
      if (elementFromGraph != null)
      {
        return elementFromGraph;
      }
    }

    return null;
  }

  @SuppressWarnings("unused")
  private NodeA getElementFromGraphNodeA(NodeA node, String name)
  {
    if (node.getName().equals(name))
    {
      return node;
    }

    for (NodeA child : node.getChildren())
    {
      NodeA elementFromGraph = getElementFromGraphNodeA(child, name);
      if (elementFromGraph != null)
      {
        return elementFromGraph;
      }
    }

    return null;
  }

  /*
   * @SuppressWarnings("unused") private void restartRepository() { LifecycleUtil.deactivate(getRepository());
   * createRepository(); getRepository(); }
   */

  /**
   * @author Martin Fluegge
   */
  private static class ThreadBShouldHaveThrownAnExceptionException extends Exception
  {
    private static final long serialVersionUID = 1L;

    public ThreadBShouldHaveThrownAnExceptionException(String s)
    {
      super(s);
    }
  }
}