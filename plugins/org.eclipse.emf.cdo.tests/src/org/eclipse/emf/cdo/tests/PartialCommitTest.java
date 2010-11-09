package org.eclipse.emf.cdo.tests;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.tests.legacy.model1.Model1Package;
import org.eclipse.emf.cdo.tests.legacy.model4.model4Package;
import org.eclipse.emf.cdo.tests.model1.Category;
import org.eclipse.emf.cdo.tests.model1.Company;
import org.eclipse.emf.cdo.tests.model1.Model1Factory;
import org.eclipse.emf.cdo.tests.model1.Product1;
import org.eclipse.emf.cdo.tests.model1.PurchaseOrder;
import org.eclipse.emf.cdo.tests.model1.Supplier;
import org.eclipse.emf.cdo.tests.model4.ContainedElementNoOpposite;
import org.eclipse.emf.cdo.tests.model4.MultiNonContainedElement;
import org.eclipse.emf.cdo.tests.model4.RefMultiNonContained;
import org.eclipse.emf.cdo.tests.model4.RefSingleContainedNPL;
import org.eclipse.emf.cdo.tests.model4.RefSingleNonContained;
import org.eclipse.emf.cdo.tests.model4.SingleNonContainedElement;
import org.eclipse.emf.cdo.tests.model4.model4Factory;
import org.eclipse.emf.cdo.util.CDOUtil;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.util.CommitIntegrityCheck;
import org.eclipse.emf.cdo.util.CommitIntegrityCheck.Style;
import org.eclipse.emf.cdo.util.CommitIntegrityException;
import org.eclipse.emf.cdo.view.CDOView;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.spi.cdo.InternalCDOTransaction;
import org.eclipse.emf.spi.cdo.InternalCDOTransaction.InternalCDOCommitContext;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PartialCommitTest extends AbstractCDOTest
{
  private static String RESOURCENAME = "/r1";

  private CDOSession session;

  private InternalCDOTransaction tx;

  private CDOResource resource1;

  /* ---- Model 1 stuff ---- */

  private Company company1, company2, company3, company99;

  private PurchaseOrder purchaseOrder;

  private Supplier supplier1;

  /* ---- Model 4 stuff ---- */

  private RefSingleContainedNPL refSingleContained1, refSingleContained2;

  private ContainedElementNoOpposite singleContainedElement1;

  private RefSingleNonContained refSingleNonContained1, refSingleNonContained2;

  private SingleNonContainedElement singleNonContainedElement1, singleNonContainedElement2;

  private RefMultiNonContained refMultiNonContained1, refMultiNonContained2;

  private MultiNonContainedElement multiNonContainedElement1, multiNonContainedElement2;

  @Override
  public void setUp() throws Exception
  {
    super.setUp();
    session = openSession();
    session.options().setPassiveUpdateEnabled(false);
    tx = (InternalCDOTransaction)session.openTransaction();
  }

  @Override
  public void tearDown() throws Exception
  {
    tx.close();
    session.close();
    super.tearDown();
  }

  public void testNewTopLevelResource() throws CommitException
  {
    CDOResource topResource1 = tx.createResource("/top1");
    tx.commit();

    topResource1.setName("top1_newname"); // Make dirty but don't include; this causes partial commit
    CDOResource topResource2 = tx.createResource("/top2");
    tx.setCommittables(createSet(topResource2, tx.getRootResource()));
    goodAll();
  }

  public void testNewTopLevelResource_rootResourceNotIncluded() throws CommitException
  {
    CDOResource topResource1 = tx.createResource("/top1");
    tx.commit();

    topResource1.setName("top1_newname"); // Make dirty but don't include; this causes partial commit
    CDOResource topResource2 = tx.createResource("/top2");
    tx.setCommittables(createSet(topResource2));
    badAll(createSet(tx.getRootResource()));
  }

  public void testNewNestedResource() throws CommitException
  {
    CDOResource topResource1 = tx.createResource("/top1");
    tx.commit();

    topResource1.setName("top1_newname"); // Make dirty but don't include; this causes partial commit
    CDOResource nestedResource = tx.createResource("/folder/nested");
    tx.setCommittables(createSet(nestedResource, nestedResource.getFolder(), tx.getRootResource()));
    goodAll();
  }

  public void testNewNestedResource_rootResourceNotIncluded() throws CommitException
  {
    CDOResource topResource1 = tx.createResource("/top1");
    tx.commit();

    topResource1.setName("top1_newname"); // Make dirty but don't include; this causes partial commit
    CDOResource nestedResource = tx.createResource("/folder/nested");
    tx.setCommittables(createSet(nestedResource, nestedResource.getFolder()));
    badAll(createSet(tx.getRootResource()));
  }

  public void testNewNestedResource_resourceFolderNotIncluded() throws CommitException
  {
    CDOResource topResource1 = tx.createResource("/top1");
    tx.commit();

    topResource1.setName("top1_newname"); // Make dirty but don't include; this causes partial commit
    CDOResource nestedResource = tx.createResource("/folder/nested");
    tx.setCommittables(createSet(nestedResource, tx.getRootResource()));
    badAll(createSet(nestedResource.getFolder()));
  }

  public void testPartialCleanUp_dirtyObjects() throws CommitException
  {
    simpleModel1Setup();

    company1.setName("Company1");
    company2.setName("Company2");
    company3.setName("Company3");

    tx.setCommittables(createSet(company1));
    tx.commit();

    assertClean(company1, tx);
    assertDirty(company2, tx);
    assertDirty(company3, tx);
    assertTrue(tx.isDirty());

    tx.setCommittables(createSet(company2));
    tx.commit();

    assertClean(company1, tx);
    assertClean(company2, tx);
    assertDirty(company3, tx);
    assertTrue(tx.isDirty());

    tx.setCommittables(createSet(company3));
    tx.commit();

    assertClean(company1, tx);
    assertClean(company2, tx);
    assertClean(company3, tx);
    assertFalse(tx.isDirty());
  }

  public void testPartialCleanUp_newObjects() throws CommitException
  {
    simpleModel1Setup();
    Category cat = Model1Factory.eINSTANCE.createCategory();
    resource1.getContents().add(cat);
    tx.commit();

    company1.setName("Zzz"); // Make dirty but don't include; so as to force partial commit

    // Make some new objects; but with different containers
    Company company4 = Model1Factory.eINSTANCE.createCompany();
    resource1.getContents().add(company4);
    PurchaseOrder po = Model1Factory.eINSTANCE.createPurchaseOrder();
    company2.getPurchaseOrders().add(po);
    Product1 product = Model1Factory.eINSTANCE.createProduct1();
    cat.getProducts().add(product);

    tx.setCommittables(createSet(company4, resource1));
    tx.commit();

    assertClean(company4, tx);
    assertNew(po, tx);
    assertNew(product, tx);
    assertTrue(tx.isDirty());

    tx.setCommittables(createSet(po, company2));
    tx.commit();

    assertClean(company4, tx);
    assertClean(po, tx);
    assertNew(product, tx);
    assertTrue(tx.isDirty());

    tx.setCommittables(createSet(product, cat));
    tx.commit();

    assertClean(company4, tx);
    assertClean(po, tx);
    assertClean(product, tx);
    assertTrue(tx.isDirty());

    tx.setCommittables(createSet(company1));
    tx.commit();
    assertFalse(tx.isDirty());
  }

  public void testPartialCleanUp_detachedObjects() throws CommitException
  {
    simpleModel1Setup();
    Category cat = Model1Factory.eINSTANCE.createCategory();
    resource1.getContents().add(cat);

    // Make some new objects; but with different containers
    Company company4 = Model1Factory.eINSTANCE.createCompany();
    resource1.getContents().add(company4);
    PurchaseOrder po = Model1Factory.eINSTANCE.createPurchaseOrder();
    company2.getPurchaseOrders().add(po);
    Product1 product = Model1Factory.eINSTANCE.createProduct1();
    cat.getProducts().add(product);
    tx.commit();

    company1.setName("Zzz"); // Make dirty but don't include; so as to force partial commit

    resource1.getContents().remove(company4);
    company2.getPurchaseOrders().remove(po);
    cat.getProducts().remove(product);

    assertTrue(tx.getDetachedObjects().containsValue(company4));
    assertTrue(tx.getFormerRevisionKeys().containsKey(company4));
    assertTrue(tx.getDetachedObjects().containsValue(po));
    assertTrue(tx.getFormerRevisionKeys().containsKey(company4));
    assertTrue(tx.getDetachedObjects().containsValue(product));
    assertTrue(tx.getFormerRevisionKeys().containsKey(company4));
    assertTrue(tx.isDirty());

    tx.setCommittables(createSet(company4, resource1));
    tx.commit();

    assertFalse(tx.getDetachedObjects().containsValue(company4));
    assertFalse(tx.getFormerRevisionKeys().containsKey(company4));
    assertTrue(tx.getDetachedObjects().containsValue(po));
    assertTrue(tx.getFormerRevisionKeys().containsKey(po));
    assertTrue(tx.getDetachedObjects().containsValue(product));
    assertTrue(tx.getFormerRevisionKeys().containsKey(product));
    assertTrue(tx.isDirty());

    tx.setCommittables(createSet(po, company2));
    tx.commit();

    assertFalse(tx.getDetachedObjects().containsValue(company4));
    assertFalse(tx.getFormerRevisionKeys().containsKey(company4));
    assertFalse(tx.getDetachedObjects().containsValue(po));
    assertFalse(tx.getFormerRevisionKeys().containsKey(po));
    assertTrue(tx.getDetachedObjects().containsValue(product));
    assertTrue(tx.getFormerRevisionKeys().containsKey(product));
    assertTrue(tx.isDirty());

    tx.setCommittables(createSet(product, cat));
    tx.commit();

    assertFalse(tx.getDetachedObjects().containsValue(company4));
    assertFalse(tx.getFormerRevisionKeys().containsKey(company4));
    assertFalse(tx.getDetachedObjects().containsValue(po));
    assertFalse(tx.getFormerRevisionKeys().containsKey(po));
    assertFalse(tx.getDetachedObjects().containsValue(product));
    assertFalse(tx.getFormerRevisionKeys().containsKey(product));
    assertTrue(tx.isDirty());

    tx.setCommittables(createSet(company1));
    tx.commit();
    assertFalse(tx.isDirty());
  }

  public void testDirty() throws CommitException
  {
    simpleModel1Setup();
    supplier1.setName("Supplier");
    company1.setName("Company");

    tx.setCommittables(createSet(supplier1));
    tx.commit();

    assertDirty(company1, tx);
    assertEquals(company1.getName(), "Company");

    assertClean(supplier1, tx);
    assertEquals(supplier1.getName(), "Supplier");
  }

  public void testNew() throws CommitException
  {
    simpleModel1Setup();
    PurchaseOrder po = Model1Factory.eINSTANCE.createPurchaseOrder();
    company2.getPurchaseOrders().add(po);

    // Include both the new object and its container
    tx.setCommittables(createSet(company2, po));
    goodAll();
  }

  public void testNew_containerOfNewObjectNotIncluded() throws CommitException
  {
    simpleModel1Setup();
    PurchaseOrder po = Model1Factory.eINSTANCE.createPurchaseOrder();
    company2.getPurchaseOrders().add(po);

    // Include only the new object
    tx.setCommittables(createSet(po));
    badAll(createSet(company2));
  }

  public void testNew_newObjectNotIncluded() throws CommitException
  {
    simpleModel1Setup();
    PurchaseOrder po = Model1Factory.eINSTANCE.createPurchaseOrder();
    company2.getPurchaseOrders().add(po);

    // Include only the new object's container
    tx.setCommittables(createSet(company2));
    badAll(createSet(po));
  }

  public void testDetach() throws CommitException
  {
    simpleModel1Setup();
    EcoreUtil.delete(purchaseOrder);

    // Include the detached object and its old container
    tx.setCommittables(createSet(company1, purchaseOrder));
    goodAll();
  }

  public void testDetach_containerOfDetachedObjectNotIncluded() throws CommitException
  {
    simpleModel1Setup();
    EcoreUtil.delete(purchaseOrder);

    // Include only the detached object
    tx.setCommittables(createSet(purchaseOrder));
    badAll(createSet(company1));
  }

  public void testDetach_detachedObjectNotIncluded() throws CommitException
  {
    simpleModel1Setup();
    EcoreUtil.delete(purchaseOrder);

    // Include only the detached object's old container
    tx.setCommittables(createSet(company1));
    badAll(createSet(purchaseOrder));
  }

  public void testMove() throws CommitException
  {
    simpleModel1Setup();
    company2.getPurchaseOrders().add(purchaseOrder);
    supplier1.setName("Supplier");

    // Include the old and new containers as well as the object that was moved
    tx.setCommittables(createSet(purchaseOrder, company1, company2));
    goodAll();

    assertClean(company1, tx);
    assertClean(company2, tx);
    assertClean(purchaseOrder, tx);
    assertDirty(supplier1, tx);

    assertFalse(company1.getPurchaseOrders().contains(purchaseOrder));
    assertTrue(company2.getPurchaseOrders().contains(purchaseOrder));
    assertEquals("Supplier", supplier1.getName());
    assertSame(company2, purchaseOrder.eContainer());
  }

  public void testMove_newContainerNotIncluded() throws CommitException
  {
    simpleModel1Setup();
    company2.getPurchaseOrders().add(purchaseOrder);

    // Include only the object that was moved and its old container
    tx.setCommittables(createSet(purchaseOrder, company1));
    badAll(createSet(company2));
  }

  public void testMove_oldContainerNotIncluded() throws CommitException
  {
    simpleModel1Setup();
    company2.getPurchaseOrders().add(purchaseOrder);

    // Include only the object that was moved and its new container
    tx.setCommittables(createSet(purchaseOrder, company2));
    badAll(createSet(company1));
  }

  public void testMove_movedObjectNotIncluded() throws CommitException
  {
    simpleModel1Setup();
    company2.getPurchaseOrders().add(purchaseOrder);

    // Include only the old and new containers
    tx.setCommittables(createSet(company1, company2));
    badAll(createSet(purchaseOrder));
  }

  public void testMove_onlyOldContainerIncluded() throws CommitException
  {
    simpleModel1Setup();
    company2.getPurchaseOrders().add(purchaseOrder);

    // Include only the old container
    tx.setCommittables(createSet(company1));
    badAll(createSet(company2, purchaseOrder));
  }

  public void testMove_onlyNewContainerIncluded() throws CommitException
  {
    simpleModel1Setup();
    company2.getPurchaseOrders().add(purchaseOrder);

    // Include only the new container
    tx.setCommittables(createSet(company2));
    badAll(createSet(company1, purchaseOrder));
  }

  public void testMove_onlyMovedObjectIncluded() throws CommitException
  {
    simpleModel1Setup();
    company2.getPurchaseOrders().add(purchaseOrder);

    // Include only the moved object
    tx.setCommittables(createSet(purchaseOrder));
    badAll(createSet(company1, company2));
  }

  public void testDoubleMove() throws CommitException
  {
    simpleModel1Setup();
    company2.getPurchaseOrders().add(purchaseOrder);
    company3.getPurchaseOrders().add(purchaseOrder);

    // Include the old and new containers as well as the object that was moved
    // (The point here is that company2 does NOT have to be included.)
    tx.setCommittables(createSet(purchaseOrder, company1, company3));
    System.out.printf("---> purchaseOrder=%s company1=%s company2=%s company3=%s\n", purchaseOrder, company1, company2,
        company3);
    goodAll();
  }

  public void test_noCommittablesAfterCommit() throws CommitException
  {
    simpleModel1Setup();
    company1.setName("zzz");
    tx.setCommittables(createSet(company1));
    tx.commit();

    assertNull(tx.getCommittables());
  }

  public void testNewSingle() throws CommitException
  {
    simpleModel4ContainmentSetup();
    ContainedElementNoOpposite singleContainedElement = model4Factory.eINSTANCE.createContainedElementNoOpposite();
    refSingleContained2.setElement(singleContainedElement);

    // Include both the new object and its container
    tx.setCommittables(createSet(refSingleContained2, singleContainedElement));
    goodAll();
  }

  public void testNewSingle_containerOfNewObjectNotIncluded() throws CommitException
  {
    simpleModel4ContainmentSetup();
    ContainedElementNoOpposite singleContainedElement = model4Factory.eINSTANCE.createContainedElementNoOpposite();
    refSingleContained2.setElement(singleContainedElement);

    // Include only the new object
    tx.setCommittables(createSet(singleContainedElement));
    badAll(createSet(refSingleContained2));
  }

  public void testNewSingle_newObjectNotIncluded() throws CommitException
  {
    simpleModel4ContainmentSetup();
    ContainedElementNoOpposite singleContainedElement = model4Factory.eINSTANCE.createContainedElementNoOpposite();
    refSingleContained2.setElement(singleContainedElement);

    // Include only the new object's container
    tx.setCommittables(createSet(refSingleContained2));
    badAll(createSet(singleContainedElement));
  }

  public void testDetachSingleRef() throws CommitException
  {
    simpleModel4ContainmentSetup();
    refSingleContained1.setElement(null);

    // Include the detached object and its old container
    tx.setCommittables(createSet(refSingleContained1, singleContainedElement1));
    goodAll();
  }

  public void testDetachSingleRef_containerOfDetachedObjectNotIncluded() throws CommitException
  {
    simpleModel4ContainmentSetup();
    refSingleContained1.setElement(null);

    // Include only the detached object
    tx.setCommittables(createSet(singleContainedElement1));
    badAll(createSet(refSingleContained1));
  }

  public void testDetachSingleRef_detachedObjectNotIncluded() throws CommitException
  {
    simpleModel4ContainmentSetup();
    refSingleContained1.setElement(null);

    // Include only the detached object's old container
    tx.setCommittables(createSet(refSingleContained1));
    badAll(createSet(singleContainedElement1));
  }

  public void testMoveSingleRef() throws CommitException
  {
    simpleModel4ContainmentSetup();
    refSingleContained2.setElement(singleContainedElement1);

    // Include the old and new containers as well as the object that was moved
    tx.setCommittables(createSet(refSingleContained1, refSingleContained2, singleContainedElement1));
    goodAll();
  }

  public void testMoveSingleRef_newContainerNotIncluded() throws CommitException
  {
    simpleModel4ContainmentSetup();
    refSingleContained2.setElement(singleContainedElement1);

    // Include only the object that was moved and its old container
    tx.setCommittables(createSet(refSingleContained1, singleContainedElement1));
    badAll(createSet(refSingleContained2));
  }

  public void testMoveSingleRef_oldContainerNotIncluded() throws CommitException
  {
    simpleModel4ContainmentSetup();
    refSingleContained2.setElement(singleContainedElement1);

    // Include only the object that was moved and its new container
    tx.setCommittables(createSet(refSingleContained2, singleContainedElement1));
    badAll(createSet(refSingleContained1));
  }

  public void testMoveSingleRef_movedObjectNotIncluded() throws CommitException
  {
    simpleModel4ContainmentSetup();
    refSingleContained2.setElement(singleContainedElement1);

    // Include only the old and new containers
    tx.setCommittables(createSet(refSingleContained1, refSingleContained2));
    badAll(createSet(singleContainedElement1));
  }

  public void testMoveSingleRef_onlyOldContainerIncluded() throws CommitException
  {
    simpleModel4ContainmentSetup();
    refSingleContained2.setElement(singleContainedElement1);

    // Include only the old container
    tx.setCommittables(createSet(refSingleContained1));
    badAll(createSet(singleContainedElement1, refSingleContained2));
  }

  public void testMoveSingleRef_onlyNewContainerIncluded() throws CommitException
  {
    simpleModel4ContainmentSetup();
    refSingleContained2.setElement(singleContainedElement1);

    // Include only the new container
    tx.setCommittables(createSet(refSingleContained2));
    badAll(createSet(singleContainedElement1, refSingleContained1));
  }

  public void testMoveSingleRef_onlyMovedObjectIncluded() throws CommitException
  {
    simpleModel4ContainmentSetup();
    refSingleContained2.setElement(singleContainedElement1);

    // Include only the moved object
    tx.setCommittables(createSet(singleContainedElement1));
    badAll(createSet(refSingleContained1, refSingleContained2));
  }

  public void testNewTopLevel() throws CommitException
  {
    simpleModel1Setup();
    Company company = Model1Factory.eINSTANCE.createCompany();
    resource1.getContents().add(company);

    // Include both the resource and the new object
    tx.setCommittables(createSet(resource1, company));
    goodAll();
  }

  public void testNewTopLevel_newObjectNotIncluded() throws CommitException
  {
    simpleModel1Setup();
    Company company = Model1Factory.eINSTANCE.createCompany();
    resource1.getContents().add(company);

    // Include only the resource
    tx.setCommittables(createSet(resource1));
    badAll(createSet(company));
  }

  public void testNewTopLevel_resourceNotIncluded() throws CommitException
  {
    simpleModel1Setup();
    Company company = Model1Factory.eINSTANCE.createCompany();
    resource1.getContents().add(company);

    // Include only the new object
    tx.setCommittables(createSet(company));
    badAll(createSet(resource1));
  }

  public void _testNewTopLevel_resourceNotIncluded() throws CommitException
  {
    simpleModel1Setup();

    CDOID companyID = null;
    {
      Company company = Model1Factory.eINSTANCE.createCompany();
      resource1.getContents().add(company);

      // Include only the new object
      tx.setCommittables(createSet(company));
      tx.commit();

      companyID = CDOUtil.getCDOObject(company).cdoID();
    }

    System.out.println("---> companyID = " + companyID);
    System.out.println("---> " + CDOUtil.getCDOObject(resource1).cdoState());

    {
      CDOSession session2 = openSession();
      CDOView view = session2.openView();
      CDOResource resource = view.getResource(resource1.getPath());

      // We want to know if the new company that was committed, is an element
      // in the getContents() collection of the Resource. We cannot just call
      // getContents().contains(), because of the odd implementation of
      // CDOResourceImpl.contains: it actually asks the element, rather than
      // checking its own collection. So, we have to do this the hard way:
      //
      boolean found = false;
      Iterator<EObject> iter = resource.getContents().iterator();
      while (!found && iter.hasNext())
      {
        CDOObject o = CDOUtil.getCDOObject(iter.next());
        if (o.cdoID().equals(companyID))
        {
          found = true;
        }
      }
      assertTrue(found);

      view.close();
      session2.close();
    }
  }

  // -------- Tests concerning bi-di references ----------
  //
  // Cases to test:
  // Bi-di refs are analogous to containment, the only difference being that
  // bi-di refs are symmetrical, whereas containment/container is not.
  //
  // So:
  //
  // For DIRTY objects, the cases are:
  // 1. Setting a bidi ref to null where it was previously non-null
  // Must check that object owning opposite feature is included
  // 2. Setting a bidi ref to non-null where it was previously null
  // Must check that object owning opposite feature is included
  // 3. Changing a bidi ref from one non-null value to another
  // Must check that both the object owning the NEW opposite feature,
  // as well as the OLD one, are included
  //
  // For NEW objects, the
  // If the detached object had any non-null bidi refs, we must check
  // whether the bidi target is included.
  //
  // For DETACHED objects:
  // If the detached object had any non-null bidi refs, we must check
  // whether the bidi target is included.

  public void testDirtySingleBidiNew() throws CommitException
  {
    simpleModel4SingleBidiSetup();
    singleNonContainedElement2.setParent(refSingleNonContained2);

    tx.setCommittables(createSet(singleNonContainedElement2, refSingleNonContained2));
    goodAll();
  }

  public void testDirtySingleBidiNew_newtargetNotIncluded() throws CommitException
  {
    simpleModel4SingleBidiSetup();
    singleNonContainedElement2.setParent(refSingleNonContained2);

    tx.setCommittables(createSet(singleNonContainedElement2));
    badAll(createSet(refSingleNonContained2));
  }

  public void testDirtySingleBidiChanged() throws CommitException
  {
    simpleModel4SingleBidiSetup();
    // We "reparent" the singleNonContainedElement1
    singleNonContainedElement1.setParent(refSingleNonContained2);

    tx.setCommittables(createSet(singleNonContainedElement1, refSingleNonContained1, refSingleNonContained2));
    goodAll();
  }

  public void testDirtySingleBidiChanged_newTargetNotIncluded() throws CommitException
  {
    simpleModel4SingleBidiSetup();
    // We "reparent" the singleNonContainedElement1
    singleNonContainedElement1.setParent(refSingleNonContained2);

    tx.setCommittables(createSet(singleNonContainedElement1, refSingleNonContained1));
    badAll(createSet(refSingleNonContained2));
  }

  public void testDirtySingleBidiChanged_oldTargetNotIncluded() throws CommitException
  {
    simpleModel4SingleBidiSetup();
    // We "reparent" the singleNonContainedElement1
    singleNonContainedElement1.setParent(refSingleNonContained2);

    tx.setCommittables(createSet(singleNonContainedElement1, refSingleNonContained2));
    badAll(createSet(refSingleNonContained1));
  }

  public void testDirtySingleBidiRemoved() throws CommitException
  {
    simpleModel4SingleBidiSetup();
    singleNonContainedElement1.setParent(null);

    tx.setCommittables(createSet(singleNonContainedElement1, refSingleNonContained1));
    goodAll();
  }

  public void testDirtySingleBidiRemoved_oldTargetNotIncluded() throws CommitException
  {
    simpleModel4SingleBidiSetup();
    singleNonContainedElement1.setParent(null);

    tx.setCommittables(createSet(singleNonContainedElement1));
    badAll(createSet(refSingleNonContained1));
  }

  public void testSingleBidiOnNewObject() throws CommitException
  {
    simpleModel4SingleBidiSetup();
    SingleNonContainedElement newNonContainedElement = model4Factory.eINSTANCE.createSingleNonContainedElement();
    resource1.getContents().add(newNonContainedElement);
    newNonContainedElement.setParent(refSingleNonContained2);

    tx.setCommittables(createSet(newNonContainedElement, resource1, refSingleNonContained2));
    goodAll();
  }

  public void testSingleBidiOnNewObject_targetNotIncluded() throws CommitException
  {
    simpleModel4SingleBidiSetup();
    SingleNonContainedElement newNonContainedElement = model4Factory.eINSTANCE.createSingleNonContainedElement();
    resource1.getContents().add(newNonContainedElement);
    newNonContainedElement.setParent(refSingleNonContained2);

    tx.setCommittables(createSet(newNonContainedElement, resource1));
    badAll(createSet(refSingleNonContained2));
  }

  public void testSingleBidiOnRemovedObject() throws CommitException
  {
    simpleModel4SingleBidiSetup();
    EcoreUtil.delete(singleNonContainedElement1);

    tx.setCommittables(createSet(singleNonContainedElement1, resource1, refSingleNonContained1));
    goodAll();
  }

  public void testSingleBidiOnRemovedObject_targetNotIncluded() throws CommitException
  {
    simpleModel4SingleBidiSetup();
    EcoreUtil.delete(singleNonContainedElement1);

    tx.setCommittables(createSet(singleNonContainedElement1, resource1));
    badAll(createSet(refSingleNonContained1));
  }

  public void testDirtyMultiBidiNew() throws CommitException
  {
    simpleModel4MultiBidiSetup();
    multiNonContainedElement2.setParent(refMultiNonContained2);

    tx.setCommittables(createSet(multiNonContainedElement2, refMultiNonContained2));
    goodAll();
  }

  public void testDirtyMultiBidiNew_newtargetNotIncluded() throws CommitException
  {
    simpleModel4MultiBidiSetup();
    multiNonContainedElement2.setParent(refMultiNonContained2);

    tx.setCommittables(createSet(multiNonContainedElement2));
    badAll(createSet(refMultiNonContained2));
  }

  public void testDirtyMultiBidiChanged() throws CommitException
  {
    simpleModel4MultiBidiSetup();
    // We "reparent" the multiNonContainedElement1
    multiNonContainedElement1.setParent(refMultiNonContained2);

    tx.setCommittables(createSet(multiNonContainedElement1, refMultiNonContained1, refMultiNonContained2));
    goodAll();
  }

  public void testDirtyMultiBidiChanged_newTargetNotIncluded() throws CommitException
  {
    simpleModel4MultiBidiSetup();
    // We "reparent" the multiNonContainedElement1
    multiNonContainedElement1.setParent(refMultiNonContained2);

    tx.setCommittables(createSet(multiNonContainedElement1, refMultiNonContained1));
    badAll(createSet(refMultiNonContained2));
  }

  public void testDirtyMultiBidiChanged_oldTargetNotIncluded() throws CommitException
  {
    simpleModel4MultiBidiSetup();
    // We "reparent" the multiNonContainedElement1
    multiNonContainedElement1.setParent(refMultiNonContained2);

    tx.setCommittables(createSet(multiNonContainedElement1, refMultiNonContained2));
    badAll(createSet(refMultiNonContained1));
  }

  public void testDirtyMultiBidiRemoved() throws CommitException
  {
    simpleModel4MultiBidiSetup();
    multiNonContainedElement1.setParent(null);

    tx.setCommittables(createSet(multiNonContainedElement1, refMultiNonContained1));
    goodAll();
  }

  public void testDirtyMultiBidiRemoved_oldTargetNotIncluded() throws CommitException
  {
    simpleModel4MultiBidiSetup();
    multiNonContainedElement1.setParent(null);

    tx.setCommittables(createSet(multiNonContainedElement1));
    badAll(createSet(refMultiNonContained1));
  }

  public void testMultiBidiOnNewObject() throws CommitException
  {
    simpleModel4MultiBidiSetup();
    MultiNonContainedElement newNonContainedElement = model4Factory.eINSTANCE.createMultiNonContainedElement();
    resource1.getContents().add(newNonContainedElement);
    newNonContainedElement.setParent(refMultiNonContained2);

    tx.setCommittables(createSet(newNonContainedElement, resource1, refMultiNonContained2));
    goodAll();
  }

  public void testMultiBidiOnNewObject_targetNotIncluded() throws CommitException
  {
    simpleModel4MultiBidiSetup();
    MultiNonContainedElement newNonContainedElement = model4Factory.eINSTANCE.createMultiNonContainedElement();
    resource1.getContents().add(newNonContainedElement);
    newNonContainedElement.setParent(refMultiNonContained2);

    tx.setCommittables(createSet(newNonContainedElement, resource1));
    badAll(createSet(refMultiNonContained2));
  }

  public void testMultiBidiOnRemovedObject() throws CommitException
  {
    simpleModel4MultiBidiSetup();
    EcoreUtil.delete(multiNonContainedElement1);

    tx.setCommittables(createSet(multiNonContainedElement1, resource1, refMultiNonContained1));
    goodAll();
  }

  public void testMultiBidiOnRemovedObject_targetNotIncluded() throws CommitException
  {
    simpleModel4MultiBidiSetup();
    EcoreUtil.delete(multiNonContainedElement1);

    tx.setCommittables(createSet(multiNonContainedElement1, resource1));
    badAll(createSet(refMultiNonContained1));
  }

  public void testCheckWithoutCommit_exceptionFast() throws CommitException
  {
    simpleModel1Setup();
    PurchaseOrder po = Model1Factory.eINSTANCE.createPurchaseOrder();
    company2.getPurchaseOrders().add(po);

    // Include only the new object
    tx.setCommittables(createSet(po));
    InternalCDOCommitContext ctx = tx.createCommitContext();
    try
    {
      new CommitIntegrityCheck(ctx, CommitIntegrityCheck.Style.EXCEPTION_FAST).check();
    }
    catch (CommitIntegrityException e)
    {
      // Good
    }
  }

  public void testCheckWithoutCommit_exception() throws CommitException
  {
    simpleModel1Setup();
    PurchaseOrder po = Model1Factory.eINSTANCE.createPurchaseOrder();
    company2.getPurchaseOrders().add(po);

    // Include only the new object
    tx.setCommittables(createSet(po));
    InternalCDOCommitContext ctx = tx.createCommitContext();
    try
    {
      new CommitIntegrityCheck(ctx, CommitIntegrityCheck.Style.EXCEPTION_FAST).check();
    }
    catch (CommitIntegrityException e)
    {
      // Good
    }
  }

  public void testCommittablesContainUncommittableObjects()
  {
    // Idea here is to include some objects in the committables, that exist, but
    // are neither dirty nor detached nor new.
    //
    // Actually, one could wonder what the desirable behavior is in this case.
    // Should there be a failure? Or should the "committables" be considered more like
    // a filter; i.e. it's ok for the filter to cover more than what can actually be committed.
    // Hmm... *ponder* *ponder*.
    //
  }

  /**
   * Test the commit integrity, assuming that it is good, using all possible checking styles.
   */
  private void goodAll() throws CommitException
  {
    good(Style.NO_EXCEPTION);
    good(Style.EXCEPTION_FAST);
    good(Style.EXCEPTION);
    good(null);
  }

  /**
   * Test the commit integrity, assuming that it is good.
   * 
   * @param style
   *          - the checking style to be used; if null, just commit. In that case, the commit logic will choose the
   *          checking style.
   */
  private void good(Style style) throws CommitException
  {
    if (style != null)
    {
      InternalCDOCommitContext ctx = tx.createCommitContext();
      CommitIntegrityCheck check = new CommitIntegrityCheck(ctx, style);

      try
      {
        check.check();
        assertTrue("check.getMissingObjects() should have been empty", check.getMissingObjects().isEmpty());
      }
      catch (CommitIntegrityException e)
      {
        fail("Should not have thrown " + CommitIntegrityException.class.getName());
      }
    }
    else
    {
      try
      {
        // We always make company99 dirty if it's present
        // (This is just a control object to verify that some stuff does NOT get
        // committed.)
        if (company99 != null)
        {
          company99.setName("000");
        }

        tx.commit();

        // And we verify that it didn't get included in the commit
        if (company99 != null)
        {
          assertDirty(company99, tx);
          assertTrue("Transaction should still have been dirty", tx.isDirty());
        }
      }
      catch (CommitException e)
      {
        Throwable cause = e.getCause().getCause();
        if (cause instanceof CommitIntegrityException)
        {
          fail("---> Should not have failed with: " + e.getCause().getMessage());
        }
        else
        {
          throw e;
        }
      }
    }
  }

  /**
   * Test the commit integrity, assuming that it is bad, using all possible checking styles.
   */
  private void badAll(Set<EObject> expectedMissingObjects) throws CommitException
  {
    bad(Style.NO_EXCEPTION, expectedMissingObjects);
    bad(Style.EXCEPTION_FAST, expectedMissingObjects);
    bad(Style.EXCEPTION, expectedMissingObjects);
    bad(null, expectedMissingObjects);
  }

  /**
   * Test the commit integrity, assuming that it is bad.
   * 
   * @param style
   *          - the checking style to be used; if null, just commit. In that case, the commit logic will choose the
   *          checking style.
   */
  private void bad(Style style, Set<EObject> expectedMissingObjects) throws CommitException
  {
    CommitIntegrityException commitIntegrityEx = null;
    Set<? extends EObject> missingObjects = null;

    CommitIntegrityCheck check = null;
    if (style != null)
    {
      InternalCDOCommitContext ctx = tx.createCommitContext();
      check = new CommitIntegrityCheck(ctx, style);
    }

    if (style == Style.NO_EXCEPTION)
    {
      try
      {
        check.check();
      }
      catch (CommitIntegrityException e)
      {
        fail("Should not have thrown " + CommitIntegrityException.class.getName());
      }
    }
    else if (style == CommitIntegrityCheck.Style.EXCEPTION || style == CommitIntegrityCheck.Style.EXCEPTION_FAST)
    {
      try
      {
        check.check();
        fail("Should have thrown " + CommitIntegrityException.class.getName());
      }
      catch (CommitIntegrityException e)
      {
        commitIntegrityEx = e;
      }
    }
    else if (style == null)
    {
      try
      {
        tx.commit();
        fail("Should have thrown " + CommitException.class.getName());
      }
      catch (CommitException e)
      {
        Throwable cause = e.getCause().getCause();
        if (cause instanceof CommitIntegrityException)
        {
          // Good
          commitIntegrityEx = (CommitIntegrityException)cause;
          System.out.println("---> Failed properly: " + e.getCause().getMessage());
        }
        else
        {
          throw e;
        }
      }
    }
    else
    {
      fail("Unknown style");
    }

    if (commitIntegrityEx != null)
    {
      missingObjects = commitIntegrityEx.getMissingObjects();
    }
    else
    {
      missingObjects = check.getMissingObjects();
    }

    if (style == Style.EXCEPTION_FAST)
    {
      assertEquals(1, missingObjects.size());
    }
    else
    {
      // We cannot use == here, because it isn't (always) possible for the logic to
      // find all missing objects
      assertTrue(missingObjects.size() <= expectedMissingObjects.size());
    }

    for (EObject missingObject : missingObjects)
    {
      assertTrue(expectedMissingObjects.contains(missingObject));
    }
  }

  private void simpleModel1Setup() throws CommitException
  {
    EReference ref = Model1Package.eINSTANCE.getCompany_PurchaseOrders();
    boolean preconditions = ref.isContainment() && ref.getEOpposite() == null && ref.isMany();
    if (!preconditions)
    {
      throw new RuntimeException("Model1 does not meet prerequirements for this test");
    }

    resource1 = tx.createResource(RESOURCENAME);
    company1 = Model1Factory.eINSTANCE.createCompany();
    company2 = Model1Factory.eINSTANCE.createCompany();
    company3 = Model1Factory.eINSTANCE.createCompany();
    company99 = Model1Factory.eINSTANCE.createCompany();
    supplier1 = Model1Factory.eINSTANCE.createSupplier();
    purchaseOrder = Model1Factory.eINSTANCE.createPurchaseOrder();
    company1.getPurchaseOrders().add(purchaseOrder);
    resource1.getContents().add(company1);
    resource1.getContents().add(company2);
    resource1.getContents().add(company3);
    resource1.getContents().add(company99);
    resource1.getContents().add(supplier1);
    tx.commit();
  }

  private void simpleModel4ContainmentSetup() throws CommitException
  {
    EReference ref = model4Package.eINSTANCE.getRefSingleContainedNPL_Element();
    boolean preconditions = ref.isContainment() && ref.getEOpposite() == null && !ref.isMany();
    if (!preconditions)
    {
      throw new RuntimeException("Model4 does not meet prerequirements for this test");
    }

    resource1 = tx.createResource(RESOURCENAME);

    refSingleContained1 = model4Factory.eINSTANCE.createRefSingleContainedNPL();
    refSingleContained2 = model4Factory.eINSTANCE.createRefSingleContainedNPL();
    singleContainedElement1 = model4Factory.eINSTANCE.createContainedElementNoOpposite();
    refSingleContained1.setElement(singleContainedElement1);
    resource1.getContents().add(refSingleContained1);
    resource1.getContents().add(refSingleContained2);

    tx.commit();
  }

  private void simpleModel4SingleBidiSetup() throws CommitException
  {
    EReference ref = model4Package.eINSTANCE.getRefSingleNonContained_Element();
    boolean preconditions = !ref.isContainment() && ref.getEOpposite() != null && !ref.isMany();
    if (!preconditions)
    {
      throw new RuntimeException("Model4 does not meet prerequirements for this test");
    }

    resource1 = tx.createResource(RESOURCENAME);

    refSingleNonContained1 = model4Factory.eINSTANCE.createRefSingleNonContained();
    refSingleNonContained2 = model4Factory.eINSTANCE.createRefSingleNonContained();
    singleNonContainedElement1 = model4Factory.eINSTANCE.createSingleNonContainedElement();
    singleNonContainedElement2 = model4Factory.eINSTANCE.createSingleNonContainedElement();
    refSingleNonContained1.setElement(singleNonContainedElement1);
    resource1.getContents().add(refSingleNonContained1);
    resource1.getContents().add(refSingleNonContained2);
    resource1.getContents().add(singleNonContainedElement1);
    resource1.getContents().add(singleNonContainedElement2);

    tx.commit();
  }

  private void simpleModel4MultiBidiSetup() throws CommitException
  {
    EReference ref = model4Package.eINSTANCE.getRefMultiNonContained_Elements();
    boolean preconditions = !ref.isContainment() && ref.getEOpposite() != null && ref.isMany();
    if (!preconditions)
    {
      throw new RuntimeException("Model4 does not meet prerequirements for this test");
    }

    resource1 = tx.createResource(RESOURCENAME);

    refMultiNonContained1 = model4Factory.eINSTANCE.createRefMultiNonContained();
    refMultiNonContained2 = model4Factory.eINSTANCE.createRefMultiNonContained();
    multiNonContainedElement1 = model4Factory.eINSTANCE.createMultiNonContainedElement();
    multiNonContainedElement2 = model4Factory.eINSTANCE.createMultiNonContainedElement();
    refMultiNonContained1.getElements().add(multiNonContainedElement1);
    resource1.getContents().add(refMultiNonContained1);
    resource1.getContents().add(refMultiNonContained2);
    resource1.getContents().add(multiNonContainedElement1);
    resource1.getContents().add(multiNonContainedElement2);

    tx.commit();
  }

  private Set<EObject> createSet(EObject... objects)
  {
    Set<EObject> committables = new HashSet<EObject>();
    for (EObject o : objects)
    {
      if (o == null)
      {
        throw new NullPointerException();
      }
      committables.add(o);
    }
    return committables;
  }
}