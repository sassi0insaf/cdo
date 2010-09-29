package org.eclipse.emf.cdo.dawn.transaction;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalCommandStack;
import org.eclipse.emf.transaction.impl.TransactionChangeRecorder;
import org.eclipse.emf.transaction.impl.TransactionalEditingDomainImpl;

public class DawnTransactionalEditingDomainImpl extends TransactionalEditingDomainImpl
{

  public DawnTransactionalEditingDomainImpl(AdapterFactory adapterFactory, ResourceSet resourceSet)
  {
    super(adapterFactory, resourceSet);
  }

  public DawnTransactionalEditingDomainImpl(AdapterFactory adapterFactory)
  {
    super(adapterFactory);
  }

  public DawnTransactionalEditingDomainImpl(AdapterFactory adapterFactory, TransactionalCommandStack stack,
      ResourceSet resourceSet)
  {
    super(adapterFactory, stack, resourceSet);
  }

  public DawnTransactionalEditingDomainImpl(AdapterFactory adapterFactory, TransactionalCommandStack stack)
  {
    super(adapterFactory, stack);
  }

  @Override
  protected TransactionChangeRecorder createChangeRecorder(ResourceSet rset)
  {
    return new DawnTransactionChangeRecorder(this, rset);
  }
}