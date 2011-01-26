/**
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Pascal Lehmann - initial API and implementation
 *    Eike Stepper - maintenance
 */
package org.eclipse.emf.cdo.tests.bugzilla;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.tests.AbstractCDOTest;
import org.eclipse.emf.cdo.tests.model4.MultiContainedElement;
import org.eclipse.emf.cdo.tests.model4.RefMultiContained;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CDOUtil;
import org.eclipse.emf.cdo.view.CDOAdapterPolicy;

import org.eclipse.emf.spi.cdo.DefaultCDOMerger;

/**
 * "Attempt to modify historical revision"-Exception after branch merge.
 * <p>
 * See bug 324635.
 * 
 * @author Pascal Lehmann
 */
public class Bugzilla_324635_Test extends AbstractCDOTest
{
  public void testTargetGoalDeltaVersion() throws Exception
  {
    skipUnlessBranching();

    // setup 2 transactions.
    final CDOSession session1 = openSession();
    final CDOTransaction s1Tr1 = session1.openTransaction();
    s1Tr1.options().addChangeSubscriptionPolicy(CDOAdapterPolicy.ALL);
    final CDOTransaction s1Tr2 = session1.openTransaction();
    s1Tr2.options().addChangeSubscriptionPolicy(CDOAdapterPolicy.ALL);

    // create resource, container and 2 elements using transaction 1.
    final CDOResource resource = s1Tr1.createResource("/test1");
    RefMultiContained container = getModel4Factory().createRefMultiContained();
    resource.getContents().add(container);
    MultiContainedElement element1 = getModel4Factory().createMultiContainedElement();
    container.getElements().add(element1);
    MultiContainedElement element2 = getModel4Factory().createMultiContainedElement();
    container.getElements().add(element2);

    s1Tr1.commit();

    // access container on transaction 2 to have it updated with a RevisionDelta.
    RefMultiContained container2 = s1Tr2.getObject(container);

    // setup another branch.
    final CDOBranch otherBranch = s1Tr1.getBranch().createBranch("other");
    final CDOTransaction s1Tr3 = session1.openTransaction(otherBranch);

    RefMultiContained otherContainer = s1Tr3.getObject(container);
    assertNotSame(null, otherContainer);
    assertEquals(true, otherContainer.getElements().size() > 0);

    // remove an element on the other branch.
    otherContainer.getElements().remove(0);

    s1Tr3.commit();

    // merge the other branch to main (this creates the targetGoalDelta for the RevisionDelta).
    s1Tr1.merge(s1Tr3.getBranch().getHead(), new DefaultCDOMerger.PerFeature.ManyValued());

    s1Tr1.commit();
    assertEquals(false, s1Tr1.isDirty());

    // check the change on tr2 and do another change.
    s1Tr2.waitForUpdate(s1Tr1.getLastCommitTime());
    container2.getElements().remove(0);

    s1Tr2.commit(); // <--- this commit will throw the following exception:
    // java.util.ConcurrentModificationException:
    // Attempt by Transaction[2:2] to modify historical revision: RefMultiContained@OID4:0v1
    assertEquals(false, s1Tr1.isDirty());

    // check revision versions.
    s1Tr1.waitForUpdate(s1Tr2.getLastCommitTime());
    assertEquals(CDOUtil.getCDOObject(container).cdoRevision().getVersion(), CDOUtil.getCDOObject(container2)
        .cdoRevision().getVersion());
  }
}