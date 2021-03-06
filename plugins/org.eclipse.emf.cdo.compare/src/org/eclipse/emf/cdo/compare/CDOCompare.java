/*
 * Copyright (c) 2013 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.compare;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.common.model.EMFUtil;
import org.eclipse.emf.cdo.util.CDOUtil;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.EMFCompare.Builder;
import org.eclipse.emf.compare.conflict.IConflictDetector;
import org.eclipse.emf.compare.diff.IDiffEngine;
import org.eclipse.emf.compare.equi.IEquiEngine;
import org.eclipse.emf.compare.match.DefaultComparisonFactory;
import org.eclipse.emf.compare.match.DefaultEqualityHelperFactory;
import org.eclipse.emf.compare.match.DefaultMatchEngine;
import org.eclipse.emf.compare.match.IComparisonFactory;
import org.eclipse.emf.compare.match.IEqualityHelperFactory;
import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.match.eobject.IEObjectMatcher;
import org.eclipse.emf.compare.match.eobject.IdentifierEObjectMatcher;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryRegistryImpl;
import org.eclipse.emf.compare.postprocessor.IPostProcessor;
import org.eclipse.emf.compare.req.IReqEngine;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.google.common.base.Function;

/**
 * A class with many overridable factory methods that help to create EMF {@link Comparison comparisons}.
 *
 * @author Eike Stepper
 */
public class CDOCompare
{
  public Comparison compare(IComparisonScope scope)
  {
    Function<EObject, String> idFunction = createIDFunction();
    IEObjectMatcher matcher = createMatcher(idFunction);
    IEqualityHelperFactory equalityHelperFactory = createEqualityHelperFactory();
    IComparisonFactory comparisonFactory = createComparisonFactory(equalityHelperFactory);
    EMFCompare comparator = createComparator(matcher, comparisonFactory);

    Comparison comparison = comparator.compare(scope);
    comparison.eAdapters().add(new ComparisonScopeAdapter(scope));
    return comparison;
  }

  protected CDOIDFunction createIDFunction()
  {
    return new CDOIDFunction();
  }

  protected IdentifierEObjectMatcher createMatcher(Function<EObject, String> idFunction)
  {
    return new IdentifierEObjectMatcher(idFunction);
  }

  protected IEqualityHelperFactory createEqualityHelperFactory()
  {
    return new DefaultEqualityHelperFactory();
  }

  protected IComparisonFactory createComparisonFactory(IEqualityHelperFactory equalityHelperFactory)
  {
    return new DefaultComparisonFactory(equalityHelperFactory);
  }

  protected EMFCompare createComparator(IEObjectMatcher matcher, IComparisonFactory comparisonFactory)
  {
    Builder builder = EMFCompare.builder();

    IMatchEngine.Factory.Registry matchEngineFactoryRegistry = createMatchEngineFactoryRegistry(matcher,
        comparisonFactory);
    if (matchEngineFactoryRegistry != null)
    {
      builder.setMatchEngineFactoryRegistry(matchEngineFactoryRegistry);
    }

    IDiffEngine diffEngine = createDiffEngine();
    if (diffEngine != null)
    {
      builder.setDiffEngine(diffEngine);
    }

    IReqEngine reqEngine = createRequirementEngine();
    if (reqEngine != null)
    {
      builder.setRequirementEngine(reqEngine);
    }

    IEquiEngine equiEngine = createEquivalenceEngine();
    if (equiEngine != null)
    {
      builder.setEquivalenceEngine(equiEngine);
    }

    IPostProcessor.Descriptor.Registry<?> registry = createPostProcessorRegistry();
    if (registry != null)
    {
      builder.setPostProcessorRegistry(registry);
    }

    IConflictDetector conflictDetector = createConflictDetector();
    if (conflictDetector != null)
    {
      builder.setConflictDetector(conflictDetector);
    }

    return builder.build();
  }

  protected IMatchEngine.Factory.Registry createMatchEngineFactoryRegistry(IEObjectMatcher matcher,
      IComparisonFactory comparisonFactory)
  {
    IMatchEngine.Factory.Registry registry = new MatchEngineFactoryRegistryImpl();
    registry.add(new CDOMatchEngine.Factory(matcher, comparisonFactory));
    return registry;
  }

  protected IDiffEngine createDiffEngine()
  {
    return null;
  }

  protected IReqEngine createRequirementEngine()
  {
    return null;
  }

  protected IEquiEngine createEquivalenceEngine()
  {
    return null;
  }

  protected IPostProcessor.Descriptor.Registry<?> createPostProcessorRegistry()
  {
    return null;
  }

  protected IConflictDetector createConflictDetector()
  {
    return null;
  }

  public static IComparisonScope getScope(Comparison comparison)
  {
    ComparisonScopeAdapter adapter = EMFUtil.getAdapter(comparison, ComparisonScopeAdapter.class);
    if (adapter == null)
    {
      return null;
    }

    return adapter.getScope();
  }

  /**
   * An {@link CDOIDFunction ID function} that considers the {@link CDOID}s of {@link CDOObject objects}.
   *
   * @author Eike Stepper
   */
  public static class CDOIDFunction implements Function<EObject, String>
  {
    public String apply(EObject o)
    {
      CDOObject object = CDOUtil.getCDOObject(o);
      CDOID id = object.cdoID();

      StringBuilder builder = new StringBuilder();
      CDOIDUtil.write(builder, id);
      return builder.toString();
    }
  }

  /**
   * A {@link DefaultMatchEngine match engine} that treats {@link Resource resources} as {@link EObject EObjects}.
   *
   * @author Eike Stepper
   */
  public static class CDOMatchEngine extends DefaultMatchEngine
  {
    CDOMatchEngine(IEObjectMatcher matcher, IComparisonFactory comparisonFactory)
    {
      super(matcher, comparisonFactory);
    }

    @Override
    protected void match(Comparison comparison, IComparisonScope scope, final Notifier left, final Notifier right,
        final Notifier origin, Monitor monitor)
    {
      match(comparison, scope, (EObject)left, (EObject)right, (EObject)origin, monitor);
    }

    /**
     * Creates {@link CDOMatchEngine match engine} instances.
     *
     * @author Eike Stepper
     */
    public static class Factory implements IMatchEngine.Factory
    {
      private final IMatchEngine matchEngine;

      private int ranking;

      public Factory(IEObjectMatcher matcher, IComparisonFactory comparisonFactory)
      {
        matchEngine = createMatchEngine(matcher, comparisonFactory);
      }

      protected Factory(IMatchEngine matchEngine)
      {
        this.matchEngine = matchEngine;
      }

      protected CDOMatchEngine createMatchEngine(IEObjectMatcher matcher, IComparisonFactory comparisonFactory)
      {
        return new CDOMatchEngine(matcher, comparisonFactory);
      }

      public IMatchEngine getMatchEngine()
      {
        return matchEngine;
      }

      public int getRanking()
      {
        return ranking;
      }

      public void setRanking(int ranking)
      {
        this.ranking = ranking;
      }

      public boolean isMatchEngineFactoryFor(IComparisonScope scope)
      {
        return scope instanceof CDOComparisonScope;
      }
    }
  }

  /**
   * @author Eike Stepper
   */
  private static final class ComparisonScopeAdapter extends AdapterImpl
  {
    private IComparisonScope scope;

    public ComparisonScopeAdapter(IComparisonScope scope)
    {
      this.scope = scope;
    }

    public final IComparisonScope getScope()
    {
      return scope;
    }

    @Override
    public boolean isAdapterForType(Object type)
    {
      return type == ComparisonScopeAdapter.class;
    }
  }
}
