/*
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Ronald Krijgsheld - initial API and implementation
 */
package org.eclipse.emf.cdo.tests.bugzilla;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.tests.AbstractCDOTest;
import org.eclipse.emf.cdo.tests.model1.Category;
import org.eclipse.emf.cdo.tests.model1.Company;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.InvalidObjectException;
import org.eclipse.emf.cdo.util.ObjectNotFoundException;

import org.eclipse.emf.internal.cdo.analyzer.CDOFeatureAnalyzerModelBased;
import org.eclipse.emf.internal.cdo.analyzer.CDOFetchRuleManagerThreadLocal;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.spi.cdo.InternalCDOSession;
import org.eclipse.emf.spi.cdo.InternalCDOTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Ronald Krijgsheld
 */
public class Bugzilla_368223_Test extends AbstractCDOTest
{
  public void testRules1() throws Throwable
  {
    CDOSession session = openSession();

    CDOTransaction transaction = session.openTransaction();
    transaction.createResource(getResourcePath("/test1"));
    transaction.commit();

    AtomicReference<Throwable> exception = new AtomicReference<Throwable>();
    long start = System.currentTimeMillis();

    Creator creator = new Creator(exception);
    creator.start();

    Loader loader = new Loader(exception);
    loader.start();

    creator.join();
    loader.join();

    System.out.println(System.currentTimeMillis() - start);

    Throwable ex = exception.get();
    if (ex != null && !(ex instanceof Success))
    {
      throw exception.get();
    }
  }

  @Override
  protected void doSetUp() throws Exception
  {
    disableConsole();
    super.doSetUp();

    skipUnlessMEM();
    skipUnlessBranching();
  }

  @Override
  protected void doTearDown() throws Exception
  {
    disableConsole();
    super.doTearDown();
  }

  /**
   * @author Eike Stepper
   */
  private static final class Success extends Exception
  {
    private static final long serialVersionUID = 1L;
  }

  /**
   * @author Eike Stepper
   */
  private abstract class Actor extends Thread
  {
    private final AtomicReference<Throwable> exception;

    protected Actor(AtomicReference<Throwable> exception)
    {
      this.exception = exception;
    }

    @Override
    public final void run()
    {
      try
      {
        runSafe(exception);
      }
      catch (Throwable ex)
      {
        exception.compareAndSet(null, ex);
      }
    }

    protected abstract void runSafe(AtomicReference<Throwable> exception) throws Exception;
  }

  /**
   * @author Ronald Krijgsheld
   */
  private final class Creator extends Actor
  {
    public Creator(AtomicReference<Throwable> exception)
    {
      super(exception);
    }

    @Override
    protected void runSafe(AtomicReference<Throwable> exception) throws Exception
    {
      CDOSession session = openSession();
      CDOTransaction transaction = session.openTransaction();
      CDOResource resource = transaction.getResource(getResourcePath("/test1"));

      List<Company> listOfCompanies = new ArrayList<Company>();

      int loop = 10;
      while (exception.get() == null && --loop != 0)
      {
        // System.out.println(loop);
        synchronized (transaction)
        {
          for (int i = 0; i < 100; i++)
          {
            Company company = createCompanyWithCategories(resource);
            listOfCompanies.add(company);
          }

          transaction.commit();

          while (!listOfCompanies.isEmpty())
          {
            Company company = listOfCompanies.remove(0);

            EList<Category> categories = company.getCategories();
            while (!categories.isEmpty())
            {
              categories.remove(0);
              transaction.commit();
            }

            resource.getContents().remove(company);
            transaction.commit();
          }
        }
      }

      throw new Success();
    }

    private Company createCompanyWithCategories(CDOResource resource)
    {
      Company company = getModel1Factory().createCompany();
      EList<Category> categories = company.getCategories();

      for (int i = 0; i < 10; i++)
      {
        Category category = getModel1Factory().createCategory();
        categories.add(category);
      }

      resource.getContents().add(company);
      return company;
    }
  }

  /**
   * @author Ronald Krijgsheld
   */
  private final class Loader extends Actor
  {
    public Loader(AtomicReference<Throwable> exception)
    {
      super(exception);
    }

    @Override
    protected void runSafe(AtomicReference<Throwable> exception) throws Exception
    {
      InternalCDOSession session = (InternalCDOSession)openSession();
      session.setFetchRuleManager(new CDOFetchRuleManagerThreadLocal());

      InternalCDOTransaction transaction = (InternalCDOTransaction)session.openTransaction();
      transaction.setFeatureAnalyzer(new CDOFeatureAnalyzerModelBased());

      CDOResource resource = transaction.getResource(getResourcePath("/test1"));

      while (exception.get() == null)
      {
        try
        {
          synchronized (transaction)
          {
            for (EObject object : resource.getContents())
            {
              Company company = (Company)object;
              EList<Category> categories = company.getCategories();
              if (categories.size() > 0)
              {
                Category category = categories.get(0);
                msg(category);
              }
            }
          }
        }
        catch (InvalidObjectException ex)
        {
          System.err.println(ex.getMessage());
          continue;
        }
        catch (ObjectNotFoundException ex)
        {
          System.err.println(ex.getMessage());
          continue;
        }
      }

      throw new Success();
    }
  }
}