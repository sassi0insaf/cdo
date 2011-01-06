/**
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Simon McDuff - maintenance
 */
package org.eclipse.emf.internal.cdo.view;

import org.eclipse.emf.cdo.CDOState;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.branch.CDOBranchVersion;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDTemp;
import org.eclipse.emf.cdo.common.model.EMFUtil;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.CDORevisionFactory;
import org.eclipse.emf.cdo.common.revision.CDORevisionKey;
import org.eclipse.emf.cdo.common.revision.delta.CDOFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDORevisionDelta;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevision;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionManager;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOInvalidationPolicy;
import org.eclipse.emf.cdo.view.CDOView;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;
import org.eclipse.emf.internal.cdo.bundle.OM;

import org.eclipse.net4j.util.collection.Pair;
import org.eclipse.net4j.util.fsm.FiniteStateMachine;
import org.eclipse.net4j.util.fsm.ITransition;
import org.eclipse.net4j.util.om.trace.ContextTracer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EStoreEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.spi.cdo.CDOSessionProtocol.CommitTransactionResult;
import org.eclipse.emf.spi.cdo.FSMUtil;
import org.eclipse.emf.spi.cdo.InternalCDOObject;
import org.eclipse.emf.spi.cdo.InternalCDOTransaction;
import org.eclipse.emf.spi.cdo.InternalCDOView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Eike Stepper
 */
public final class CDOStateMachine extends FiniteStateMachine<CDOState, CDOEvent, InternalCDOObject>
{
  // @Singleton
  public static final CDOStateMachine INSTANCE = new CDOStateMachine();

  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG_STATEMACHINE, CDOStateMachine.class);

  private InternalCDOObject lastTracedObject;

  private CDOState lastTracedState;

  private CDOEvent lastTracedEvent;

  @SuppressWarnings("unchecked")
  private CDOStateMachine()
  {
    super(CDOState.class, CDOEvent.class);

    init(CDOState.TRANSIENT, CDOEvent.PREPARE, new PrepareTransition());
    init(CDOState.TRANSIENT, CDOEvent.ATTACH, FAIL);
    init(CDOState.TRANSIENT, CDOEvent.DETACH, IGNORE);
    init(CDOState.TRANSIENT, CDOEvent.REATTACH, new ReattachTransition());
    init(CDOState.TRANSIENT, CDOEvent.READ, IGNORE);
    init(CDOState.TRANSIENT, CDOEvent.WRITE, IGNORE);
    init(CDOState.TRANSIENT, CDOEvent.INVALIDATE, IGNORE);
    init(CDOState.TRANSIENT, CDOEvent.DETACH_REMOTE, IGNORE);
    init(CDOState.TRANSIENT, CDOEvent.COMMIT, FAIL);
    init(CDOState.TRANSIENT, CDOEvent.ROLLBACK, FAIL);

    init(CDOState.PREPARED, CDOEvent.PREPARE, FAIL);
    init(CDOState.PREPARED, CDOEvent.ATTACH, new AttachTransition());
    init(CDOState.PREPARED, CDOEvent.DETACH, FAIL);
    init(CDOState.PREPARED, CDOEvent.REATTACH, FAIL);
    init(CDOState.PREPARED, CDOEvent.READ, IGNORE);
    init(CDOState.PREPARED, CDOEvent.WRITE, FAIL);
    init(CDOState.PREPARED, CDOEvent.INVALIDATE, FAIL);
    init(CDOState.PREPARED, CDOEvent.DETACH_REMOTE, FAIL);
    init(CDOState.PREPARED, CDOEvent.COMMIT, FAIL);
    init(CDOState.PREPARED, CDOEvent.ROLLBACK, FAIL);

    init(CDOState.NEW, CDOEvent.PREPARE, FAIL);
    init(CDOState.NEW, CDOEvent.ATTACH, FAIL);
    init(CDOState.NEW, CDOEvent.DETACH, new DetachTransition());
    init(CDOState.NEW, CDOEvent.REATTACH, FAIL);
    init(CDOState.NEW, CDOEvent.READ, IGNORE);
    init(CDOState.NEW, CDOEvent.WRITE, new WriteNewTransition());
    init(CDOState.NEW, CDOEvent.INVALIDATE, FAIL);
    init(CDOState.NEW, CDOEvent.DETACH_REMOTE, FAIL);
    init(CDOState.NEW, CDOEvent.COMMIT, new CommitTransition(false));
    init(CDOState.NEW, CDOEvent.ROLLBACK, FAIL);

    init(CDOState.CLEAN, CDOEvent.PREPARE, FAIL);
    init(CDOState.CLEAN, CDOEvent.ATTACH, FAIL);
    init(CDOState.CLEAN, CDOEvent.DETACH, new DetachTransition());
    init(CDOState.CLEAN, CDOEvent.REATTACH, FAIL);
    init(CDOState.CLEAN, CDOEvent.READ, IGNORE);
    init(CDOState.CLEAN, CDOEvent.WRITE, new WriteTransition());
    init(CDOState.CLEAN, CDOEvent.INVALIDATE, new InvalidateTransition());
    init(CDOState.CLEAN, CDOEvent.DETACH_REMOTE, DetachRemoteTransition.INSTANCE);
    init(CDOState.CLEAN, CDOEvent.COMMIT, FAIL);
    init(CDOState.CLEAN, CDOEvent.ROLLBACK, FAIL);

    init(CDOState.DIRTY, CDOEvent.PREPARE, FAIL);
    init(CDOState.DIRTY, CDOEvent.ATTACH, FAIL);
    init(CDOState.DIRTY, CDOEvent.DETACH, new DetachTransition());
    init(CDOState.DIRTY, CDOEvent.REATTACH, FAIL);
    init(CDOState.DIRTY, CDOEvent.READ, IGNORE);
    init(CDOState.DIRTY, CDOEvent.WRITE, new RewriteTransition());
    init(CDOState.DIRTY, CDOEvent.INVALIDATE, new ConflictTransition());
    init(CDOState.DIRTY, CDOEvent.DETACH_REMOTE, new InvalidConflictTransition());
    init(CDOState.DIRTY, CDOEvent.COMMIT, new CommitTransition(true));
    init(CDOState.DIRTY, CDOEvent.ROLLBACK, new RollbackTransition());

    init(CDOState.PROXY, CDOEvent.PREPARE, FAIL);
    init(CDOState.PROXY, CDOEvent.ATTACH, FAIL);
    init(CDOState.PROXY, CDOEvent.DETACH, new DetachTransition());
    init(CDOState.PROXY, CDOEvent.REATTACH, FAIL);
    init(CDOState.PROXY, CDOEvent.READ, new LoadTransition(false));
    init(CDOState.PROXY, CDOEvent.WRITE, new LoadTransition(true));
    init(CDOState.PROXY, CDOEvent.INVALIDATE, IGNORE);
    init(CDOState.PROXY, CDOEvent.DETACH_REMOTE, DetachRemoteTransition.INSTANCE);
    init(CDOState.PROXY, CDOEvent.COMMIT, FAIL);
    init(CDOState.PROXY, CDOEvent.ROLLBACK, FAIL);

    init(CDOState.CONFLICT, CDOEvent.PREPARE, FAIL);
    init(CDOState.CONFLICT, CDOEvent.ATTACH, IGNORE);
    init(CDOState.CONFLICT, CDOEvent.DETACH, new DetachTransition());
    init(CDOState.CONFLICT, CDOEvent.REATTACH, FAIL);
    init(CDOState.CONFLICT, CDOEvent.READ, IGNORE);
    init(CDOState.CONFLICT, CDOEvent.WRITE, new RewriteTransition());
    init(CDOState.CONFLICT, CDOEvent.INVALIDATE, IGNORE);
    init(CDOState.CONFLICT, CDOEvent.DETACH_REMOTE, IGNORE);
    init(CDOState.CONFLICT, CDOEvent.COMMIT, IGNORE);
    init(CDOState.CONFLICT, CDOEvent.ROLLBACK, new RollbackTransition());

    init(CDOState.INVALID, CDOEvent.PREPARE, InvalidTransition.INSTANCE);
    init(CDOState.INVALID, CDOEvent.ATTACH, InvalidTransition.INSTANCE);
    init(CDOState.INVALID, CDOEvent.DETACH, InvalidTransition.INSTANCE);
    init(CDOState.INVALID, CDOEvent.REATTACH, FAIL);
    init(CDOState.INVALID, CDOEvent.READ, InvalidTransition.INSTANCE);
    init(CDOState.INVALID, CDOEvent.WRITE, InvalidTransition.INSTANCE);
    init(CDOState.INVALID, CDOEvent.INVALIDATE, IGNORE); // TODO Handle changeViewTarget!!!
    init(CDOState.INVALID, CDOEvent.DETACH_REMOTE, IGNORE);
    init(CDOState.INVALID, CDOEvent.COMMIT, InvalidTransition.INSTANCE);
    init(CDOState.INVALID, CDOEvent.ROLLBACK, InvalidTransition.INSTANCE);

    init(CDOState.INVALID_CONFLICT, CDOEvent.PREPARE, InvalidTransition.INSTANCE);
    init(CDOState.INVALID_CONFLICT, CDOEvent.ATTACH, InvalidTransition.INSTANCE);
    init(CDOState.INVALID_CONFLICT, CDOEvent.DETACH, InvalidTransition.INSTANCE);
    init(CDOState.INVALID_CONFLICT, CDOEvent.REATTACH, FAIL);
    init(CDOState.INVALID_CONFLICT, CDOEvent.READ, IGNORE);
    init(CDOState.INVALID_CONFLICT, CDOEvent.WRITE, IGNORE);
    init(CDOState.INVALID_CONFLICT, CDOEvent.INVALIDATE, IGNORE);
    init(CDOState.INVALID_CONFLICT, CDOEvent.DETACH_REMOTE, IGNORE);
    init(CDOState.INVALID_CONFLICT, CDOEvent.COMMIT, InvalidTransition.INSTANCE);
    init(CDOState.INVALID_CONFLICT, CDOEvent.ROLLBACK, DetachRemoteTransition.INSTANCE);
  }

  /**
   * The object is already attached in EMF world. It contains all the information needed to know where it will be
   * connected.
   * 
   * @since 2.0
   */
  public void attach(InternalCDOObject object, InternalCDOTransaction transaction)
  {
    ReentrantLock lock = lockView(transaction);

    try
    {
      List<InternalCDOObject> contents = new ArrayList<InternalCDOObject>();
      prepare(object, new Pair<InternalCDOTransaction, List<InternalCDOObject>>(transaction, contents));

      attachOrReattach(object, transaction);
      for (InternalCDOObject content : contents)
      {
        attachOrReattach(content, transaction);
      }
    }
    finally
    {
      unlockView(lock);
    }
  }

  private void attachOrReattach(InternalCDOObject object, InternalCDOTransaction transaction)
  {
    // Bug 283985 (Re-attachment)
    if (transaction.getFormerRevisionKeys().containsKey(object))
    {
      reattachObject(object, transaction);
    }
    else
    {
      attachObject(object);
    }
  }

  /**
   * Phase 1: TRANSIENT --> PREPARED
   */
  private void prepare(InternalCDOObject object,
      Pair<InternalCDOTransaction, List<InternalCDOObject>> transactionAndContents)
  {
    if (TRACER.isEnabled())
    {
      TRACER.format("PREPARE: {0} --> {1}", object, transactionAndContents.getElement1()); //$NON-NLS-1$
    }

    process(object, CDOEvent.PREPARE, transactionAndContents);
  }

  /**
   * Phase 2: PREPARED --> NEW
   */
  private void attachObject(InternalCDOObject object)
  {
    if (TRACER.isEnabled())
    {
      TRACER.format("ATTACH: {0}", object); //$NON-NLS-1$
    }

    process(object, CDOEvent.ATTACH, null);
  }

  private void reattachObject(InternalCDOObject object, InternalCDOTransaction transaction)
  {
    if (TRACER.isEnabled())
    {
      TRACER.format("REATTACH: {0}", object);
    }

    process(object, CDOEvent.REATTACH, transaction);
  }

  /**
   * @since 2.0
   */
  public void detach(InternalCDOObject object)
  {
    ReentrantLock lock = lockView(object.cdoView());

    try
    {
      if (TRACER.isEnabled())
      {
        trace(object, CDOEvent.DETACH);
      }

      List<InternalCDOObject> objectsToDetach = new ArrayList<InternalCDOObject>();
      InternalCDOTransaction transaction = (InternalCDOTransaction)object.cdoView();

      // Accumulate objects that needs to be detached
      // If we have an error, we will keep the graph exactly like it was before.
      process(object, CDOEvent.DETACH, objectsToDetach);

      // postDetach requires the object to be TRANSIENT
      for (InternalCDOObject content : objectsToDetach)
      {
        CDOState oldState = content.cdoInternalSetState(CDOState.TRANSIENT);
        content.cdoInternalPostDetach(false);
        content.cdoInternalSetState(oldState);
      }

      // detachObject needs to know the state before we change the object to TRANSIENT
      for (InternalCDOObject content : objectsToDetach)
      {
        transaction.detachObject(content);
        content.cdoInternalSetState(CDOState.TRANSIENT);

        content.cdoInternalSetView(null);
        content.cdoInternalSetID(null);
        content.cdoInternalSetRevision(null);
      }
    }
    finally
    {
      unlockView(lock);
    }
  }

  /**
   * @since 2.0
   */
  public InternalCDORevision read(InternalCDOObject object)
  {
    ReentrantLock lock = lockView(object.cdoView());

    try
    {
      if (TRACER.isEnabled())
      {
        trace(object, CDOEvent.READ);
      }

      process(object, CDOEvent.READ, null);

      return object.cdoRevision();
    }
    finally
    {
      unlockView(lock);
    }
  }

  /**
   * @since 2.0
   */
  public InternalCDORevision readNoLoad(InternalCDOObject object)
  {
    ReentrantLock lock = lockView(object.cdoView());

    try
    {
      switch (object.cdoState())
      {
      case TRANSIENT:
      case PREPARED:
      case NEW:
      case CONFLICT:
      case INVALID_CONFLICT:
      case INVALID:
      case PROXY:
        return null;
      }

      return object.cdoRevision();
    }
    finally
    {
      unlockView(lock);
    }
  }

  /**
   * @since 2.0
   */
  public void write(InternalCDOObject object)
  {
    write(object, null);
  }

  /**
   * @since 2.0
   */
  public void write(InternalCDOObject object, CDOFeatureDelta featureDelta)
  {
    ReentrantLock lock = lockView(object.cdoView());

    try
    {
      writeWithoutViewLock(object, featureDelta);
    }
    finally
    {
      unlockView(lock);
    }
  }

  private void writeWithoutViewLock(InternalCDOObject object, CDOFeatureDelta featureDelta)
  {
    if (TRACER.isEnabled())
    {
      trace(object, CDOEvent.WRITE);
    }

    process(object, CDOEvent.WRITE, featureDelta);
  }

  /**
   * @since 2.0
   */
  public void reload(InternalCDOObject... objects)
  {
    for (InternalCDOObject object : objects)
    {
      CDOState state = object.cdoState();
      if (state == CDOState.CLEAN || state == CDOState.PROXY)
      {
        changeState(object, CDOState.PROXY);
        object.cdoInternalSetRevision(null);
        read(object);
      }
    }
  }

  /**
   * @since 3.0
   */
  public void invalidate(InternalCDOObject object, CDORevisionKey key, long lastUpdateTime)
  {
    ReentrantLock lock = lockView(object.cdoView());

    try
    {
      if (TRACER.isEnabled())
      {
        trace(object, CDOEvent.INVALIDATE);
      }

      process(object, CDOEvent.INVALIDATE, new Pair<CDORevisionKey, Long>(key, lastUpdateTime));
    }
    finally
    {
      unlockView(lock);
    }
  }

  /**
   * @since 2.0
   */
  public void detachRemote(InternalCDOObject object)
  {
    ReentrantLock lock = lockView(object.cdoView());

    try
    {
      if (TRACER.isEnabled())
      {
        trace(object, CDOEvent.DETACH_REMOTE);
      }

      process(object, CDOEvent.DETACH_REMOTE, null);
    }
    finally
    {
      unlockView(lock);
    }
  }

  /**
   * @since 2.0
   */
  public void commit(InternalCDOObject object, CommitTransactionResult result)
  {
    ReentrantLock lock = lockView(object.cdoView());

    try
    {
      if (TRACER.isEnabled())
      {
        trace(object, CDOEvent.COMMIT);
      }

      process(object, CDOEvent.COMMIT, result);
    }
    finally
    {
      unlockView(lock);
    }
  }

  /**
   * @since 2.0
   */
  public void rollback(InternalCDOObject object)
  {
    ReentrantLock lock = lockView(object.cdoView());

    try
    {
      if (TRACER.isEnabled())
      {
        trace(object, CDOEvent.ROLLBACK);
      }

      process(object, CDOEvent.ROLLBACK, null);
      object.cdoInternalPostRollback();
    }
    finally
    {
      unlockView(lock);
    }
  }

  @Override
  protected CDOState getState(InternalCDOObject object)
  {
    return object.cdoState();
  }

  @Override
  protected void setState(InternalCDOObject object, CDOState state)
  {
    object.cdoInternalSetState(state);
  }

  private ReentrantLock lockView(InternalCDOView view)
  {
    if (view == null)
    {
      return null;
    }

    ReentrantLock stateLock = view.getStateLock();
    stateLock.lock();
    return stateLock;
  }

  private void unlockView(ReentrantLock stateLock)
  {
    if (stateLock != null)
    {
      stateLock.unlock();
    }
  }

  /**
   * Removes clutter from the trace log
   */
  private void trace(InternalCDOObject object, CDOEvent event)
  {
    CDOState state = object.cdoState();
    if (lastTracedObject != object || lastTracedState != state || lastTracedEvent != event)
    {
      TRACER.format("{0}: {1}", event, object.getClass().getName()); //$NON-NLS-1$
      lastTracedObject = object;
      lastTracedState = state;
      lastTracedEvent = event;
    }
  }

  @SuppressWarnings("unused")
  private void testAttach(InternalCDOObject object)
  {
    process(object, CDOEvent.ATTACH, null);
  }

  /**
   * Prepares a tree of transient objects to be subsequently {@link AttachTransition attached} to a CDOView.
   * <p>
   * Execution is recursive and includes:
   * <ol>
   * <li>Assignment of a new {@link CDOIDTemp}
   * <li>Assignment of a new {@link CDORevision}
   * <li>Bidirectional association with the {@link CDOView}
   * <li>Registration with the {@link CDOTransaction}
   * <li>Changing state to {@link CDOState#PREPARED PREPARED}
   * </ol>
   * 
   * @see AttachTransition
   * @author Eike Stepper
   */
  private final class PrepareTransition implements
      ITransition<CDOState, CDOEvent, InternalCDOObject, Pair<InternalCDOTransaction, List<InternalCDOObject>>>
  {
    public void execute(InternalCDOObject object, CDOState state, CDOEvent event,
        Pair<InternalCDOTransaction, List<InternalCDOObject>> transactionAndContents)
    {
      InternalCDOTransaction transaction = transactionAndContents.getElement1();
      List<InternalCDOObject> contents = transactionAndContents.getElement2();

      boolean reattaching = transaction.getFormerRevisionKeys().containsKey(object);

      if (!reattaching)
      {
        // Prepare object
        CDOID id = transaction.getNextTemporaryID();
        object.cdoInternalSetID(id);
        object.cdoInternalSetView(transaction);
        changeState(object, CDOState.PREPARED);

        // Create new revision
        EClass eClass = object.eClass();
        CDORevisionFactory factory = transaction.getSession().getRevisionManager().getFactory();
        InternalCDORevision revision = (InternalCDORevision)factory.createRevision(eClass);
        revision.setID(id);
        revision.setBranchPoint(transaction.getBranch().getHead());

        object.cdoInternalSetRevision(revision);

        // Register object
        transaction.registerObject(object);
        transaction.registerNew(object);
      }

      // Prepare content tree
      for (Iterator<InternalCDOObject> it = getProperContents(object, transaction); it.hasNext();)
      {
        InternalCDOObject content = it.next();
        contents.add(content);
        INSTANCE.process(content, CDOEvent.PREPARE, transactionAndContents);
      }
    }

    private Iterator<InternalCDOObject> getProperContents(final InternalCDOObject object,
        final CDOTransaction transaction)
    {
      final boolean isResource = object instanceof Resource;
      final Iterator<EObject> delegate = object.eContents().iterator();

      return new Iterator<InternalCDOObject>()
      {
        private Object next;

        public boolean hasNext()
        {
          while (delegate.hasNext())
          {
            InternalEObject eObject = (InternalEObject)delegate.next();
            EStructuralFeature eContainingFeature = eObject.eContainingFeature();
            if (isResource || eObject.eDirectResource() == null
                && (eContainingFeature == null || EMFUtil.isPersistent(eContainingFeature)))
            {
              next = FSMUtil.adapt(eObject, transaction);
              return true;
            }
          }

          return false;
        }

        public InternalCDOObject next()
        {
          return (InternalCDOObject)next;
        }

        public void remove()
        {
          throw new UnsupportedOperationException();
        }
      };
    }
  }

  /**
   * Attaches a tree of {@link PrepareTransition prepared} objects to a CDOView.
   * <p>
   * Execution is recursive and includes:
   * <ol>
   * <li>Calling {@link InternalCDOObject#cdoInternalPostAttach()},<br>
   * which includes for {@link CDOObjectImpl}:
   * <ol>
   * <li>Population of the CDORevision with the current values in
   * {@link EStoreEObjectImpl#eSetting(org.eclipse.emf.ecore.EStructuralFeature) eSettings}
   * <li>Unsetting {@link EStoreEObjectImpl#eSetting(org.eclipse.emf.ecore.EStructuralFeature) eSettings}
   * </ol>
   * <li>Changing state to {@link CDOState#NEW NEW}
   * </ol>
   * 
   * @see PrepareTransition
   * @author Eike Stepper
   */
  private final class AttachTransition implements ITransition<CDOState, CDOEvent, InternalCDOObject, Object>
  {
    public void execute(InternalCDOObject object, CDOState state, CDOEvent event, Object NULL)
    {
      object.cdoInternalPostAttach();
      changeState(object, CDOState.NEW);
    }
  }

  /**
   * Bug 283985 (Re-attachment)
   * 
   * @author Caspar De Groot
   */
  private final class ReattachTransition implements
      ITransition<CDOState, CDOEvent, InternalCDOObject, InternalCDOTransaction>
  {
    public void execute(InternalCDOObject object, CDOState state, CDOEvent event, InternalCDOTransaction transaction)
    {
      InternalCDORevisionManager revisionManager = transaction.getSession().getRevisionManager();
      CDORevisionKey revKey = transaction.getFormerRevisionKeys().get(object);

      CDOID id = revKey.getID();
      object.cdoInternalSetID(id);
      object.cdoInternalSetView(transaction);

      // Construct a new revision
      CDORevisionFactory factory = revisionManager.getFactory();
      InternalCDORevision revision = (InternalCDORevision)factory.createRevision(object.eClass());
      revision.setID(id);
      revision.setBranchPoint(revKey.getBranch().getHead());
      revision.setVersion(revKey.getVersion());

      // Populate the revision based on the values in the CDOObject
      object.cdoInternalSetRevision(revision);
      object.cdoInternalPostAttach();

      // Compute a revision delta and register it with the tx
      CDORevision cleanRevision = revisionManager.getRevisionByVersion(id, revKey, -1, true);
      CDORevisionDelta revisionDelta = revision.compare(cleanRevision);
      transaction.registerRevisionDelta(revisionDelta);
      transaction.registerDirty(object, null);
      changeState(object, CDOState.DIRTY);

      // Add the object to the set of reattached objects
      transaction.getLastSavepoint().getReattachedObjects().put(id, object);
    }
  }

  /**
   * @author Eike Stepper
   */
  private static final class DetachTransition implements
      ITransition<CDOState, CDOEvent, InternalCDOObject, List<InternalCDOObject>>
  {
    public void execute(InternalCDOObject object, CDOState state, CDOEvent event,
        List<InternalCDOObject> objectsToDetach)
    {
      InternalCDOTransaction transaction = (InternalCDOTransaction)object.cdoView();
      objectsToDetach.add(object);
      boolean isResource = object instanceof Resource;

      // Prepare content tree
      for (Iterator<EObject> it = object.eContents().iterator(); it.hasNext();)
      {
        InternalEObject eObject = (InternalEObject)it.next();
        boolean isDirectlyConnected = isResource && eObject.eDirectResource() == object;
        if (isDirectlyConnected || eObject.eDirectResource() == null)
        {
          InternalCDOObject content = FSMUtil.adapt(eObject, transaction);
          if (content != null)
          {
            INSTANCE.process(content, CDOEvent.DETACH, objectsToDetach);
          }
        }
      }
    }
  }

  /**
   * @author Eike Stepper
   */
  final private class CommitTransition implements
      ITransition<CDOState, CDOEvent, InternalCDOObject, CommitTransactionResult>
  {
    public CommitTransition(boolean useDeltas)
    {
    }

    public void execute(InternalCDOObject object, CDOState state, CDOEvent event, CommitTransactionResult data)
    {
      InternalCDOTransaction transaction = object.cdoView().toTransaction();
      InternalCDORevision revision = object.cdoRevision();
      Map<CDOID, CDOID> idMappings = data.getIDMappings();

      // Adjust object
      CDOID oldID = object.cdoID();
      CDOID newID = idMappings.get(oldID);
      if (newID != null)
      {
        object.cdoInternalSetID(newID);
        transaction.remapObject(oldID);
        revision.setID(newID);
      }

      // Adjust revision
      revision.adjustForCommit(transaction.getBranch(), data.getTimeStamp());
      revision.adjustReferences(data.getReferenceAdjuster());

      InternalCDORevisionManager revisionManager = transaction.getSession().getRevisionManager();
      revisionManager.addRevision(revision);

      changeState(object, CDOState.CLEAN);
    }
  }

  /**
   * @author Eike Stepper
   */
  private final class RollbackTransition implements ITransition<CDOState, CDOEvent, InternalCDOObject, Object>
  {
    public void execute(InternalCDOObject object, CDOState state, CDOEvent event, Object NULL)
    {
      changeState(object, CDOState.PROXY);
    }
  }

  /**
   * @author Eike Stepper
   */
  private final class WriteTransition implements ITransition<CDOState, CDOEvent, InternalCDOObject, Object>
  {
    public void execute(InternalCDOObject object, CDOState state, CDOEvent event, Object featureDelta)
    {
      InternalCDOTransaction transaction = object.cdoView().toTransaction();
      InternalCDORevision cleanRevision = object.cdoRevision();
      transaction.getCleanRevisions().put(object, cleanRevision);

      // Copy revision
      InternalCDORevision revision = object.cdoRevision().copy();
      object.cdoInternalSetRevision(revision);

      transaction.registerDirty(object, (CDOFeatureDelta)featureDelta);
      changeState(object, CDOState.DIRTY);
    }
  }

  /**
   * @author Simon McDuff
   */
  private static final class WriteNewTransition implements ITransition<CDOState, CDOEvent, InternalCDOObject, Object>
  {
    public void execute(InternalCDOObject object, CDOState state, CDOEvent event, Object featureDelta)
    {
      InternalCDOTransaction transaction = object.cdoView().toTransaction();
      transaction.registerFeatureDelta(object, (CDOFeatureDelta)featureDelta);
    }
  }

  /**
   * @author Simon McDuff
   */
  private static final class RewriteTransition implements ITransition<CDOState, CDOEvent, InternalCDOObject, Object>
  {
    public void execute(InternalCDOObject object, CDOState state, CDOEvent event, Object featureDelta)
    {
      InternalCDOTransaction transaction = object.cdoView().toTransaction();
      transaction.registerFeatureDelta(object, (CDOFeatureDelta)featureDelta);
    }
  }

  /**
   * @author Simon McDuff
   */
  private static class DetachRemoteTransition implements ITransition<CDOState, CDOEvent, InternalCDOObject, Object>
  {
    static final DetachRemoteTransition INSTANCE = new DetachRemoteTransition();

    public void execute(InternalCDOObject object, CDOState state, CDOEvent event, Object NULL)
    {
      CDOStateMachine.INSTANCE.changeState(object, CDOState.INVALID);

      InternalCDOView view = object.cdoView();
      view.deregisterObject(object);
      object.cdoInternalPostDetach(true);
    }
  }

  /**
   * @author Eike Stepper
   */
  private class InvalidateTransition implements
      ITransition<CDOState, CDOEvent, InternalCDOObject, Pair<CDORevisionKey, Long>>
  {
    public void execute(InternalCDOObject object, CDOState state, CDOEvent event, Pair<CDORevisionKey, Long> keyAndTime)
    {
      CDORevisionKey key = keyAndTime.getElement1();
      long lastUpdateTime = keyAndTime.getElement2();
      InternalCDORevision oldRevision = object.cdoRevision();
      if (key == null || key.getVersion() >= oldRevision.getVersion())
      {
        InternalCDOView view = object.cdoView();
        if (key instanceof CDORevisionDelta)
        {
          CDORevisionDelta delta = (CDORevisionDelta)key;
          InternalCDORevision newRevision = oldRevision.copy();

          CDOBranchVersion target = delta.getTarget();
          if (target != null)
          {
            CDOBranchPoint branchPoint = target.getBranch().getPoint(lastUpdateTime);
            int version = target.getVersion();
            if (version == key.getVersion())
            {
              ++version;
            }

            newRevision.setBranchPoint(branchPoint);
            newRevision.setVersion(version);
            newRevision.setRevised(CDOBranchPoint.UNSPECIFIED_DATE);
          }
          else
          {
            newRevision.adjustForCommit(view.getBranch(), lastUpdateTime);
          }

          delta.apply(newRevision);

          object.cdoInternalSetRevision(newRevision);
          changeState(object, CDOState.CLEAN);
          object.cdoInternalPostLoad();
        }
        else
        {
          changeState(object, CDOState.PROXY);

          CDOInvalidationPolicy policy = view.options().getInvalidationPolicy();
          policy.handleInvalidation(object, key);
          object.cdoInternalPostInvalidate();
        }
      }
    }
  }

  /**
   * @author Eike Stepper
   * @since 2.0
   */
  private class ConflictTransition extends InvalidateTransition
  {
    @Override
    public void execute(InternalCDOObject object, CDOState state, CDOEvent event, Pair<CDORevisionKey, Long> keyAndTime)
    {
      CDORevisionKey key = keyAndTime.getElement1();
      InternalCDORevision oldRevision = object.cdoRevision();
      if (key == null || key.getVersion() >= oldRevision.getVersion() - 1)
      {
        changeState(object, CDOState.CONFLICT);
        InternalCDOTransaction transaction = object.cdoView().toTransaction();
        transaction.setConflict(object);
      }
    }
  }

  /**
   * @author Simon McDuff
   */
  private final class InvalidConflictTransition extends ConflictTransition
  {
    @Override
    public void execute(InternalCDOObject object, CDOState state, CDOEvent event, Pair<CDORevisionKey, Long> UNUSED)
    {
      changeState(object, CDOState.INVALID_CONFLICT);

      InternalCDOTransaction transaction = object.cdoView().toTransaction();
      transaction.setConflict(object);
    }
  }

  /**
   * @author Eike Stepper
   */
  private final class LoadTransition implements ITransition<CDOState, CDOEvent, InternalCDOObject, Object>
  {
    private boolean forWrite;

    public LoadTransition(boolean forWrite)
    {
      this.forWrite = forWrite;
    }

    public void execute(InternalCDOObject object, CDOState state, CDOEvent event, Object delta)
    {
      object.cdoInternalPreLoad();

      InternalCDOView view = object.cdoView();
      InternalCDORevision revision = view.getRevision(object.cdoID(), true);
      if (revision == null)
      {
        INSTANCE.detachRemote(object);
        CDOInvalidationPolicy policy = view.options().getInvalidationPolicy();
        policy.handleInvalidObject(object);
      }

      changeState(object, CDOState.CLEAN);
      object.cdoInternalSetRevision(revision);
      object.cdoInternalPostLoad();

      if (forWrite)
      {
        INSTANCE.writeWithoutViewLock(object, (CDOFeatureDelta)delta);
      }
    }
  }

  /**
   * @author Simon McDuff
   */
  private static final class InvalidTransition implements ITransition<CDOState, CDOEvent, InternalCDOObject, Object>
  {
    public static final InvalidTransition INSTANCE = new InvalidTransition();

    public void execute(InternalCDOObject object, CDOState state, CDOEvent event, Object NULL)
    {
      InternalCDOView view = object.cdoView();
      CDOInvalidationPolicy policy = view.options().getInvalidationPolicy();
      policy.handleInvalidObject(object);
    }
  }
}

/**
 * @author Eike Stepper
 */
enum CDOEvent
{
  PREPARE, ATTACH, DETACH, REATTACH, READ, WRITE, INVALIDATE, DETACH_REMOTE, COMMIT, ROLLBACK
}