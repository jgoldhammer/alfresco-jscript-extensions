/**
 *
 */
package de.jgoldhammer.alfresco.jscript.transaction;

import javax.transaction.*;

/**
 * deals with the de.jgoldhammer.alfresco.jscript.transaction object.
 *
 * @author jgoldhammer
 *
 */
public class ScriptTransaction {

	private UserTransaction userTransaction;

	public ScriptTransaction(UserTransaction userTransaction) {
		this.userTransaction = userTransaction;
	}

	/**
	 * begin a new user transaction
	 *
	 * @throws NotSupportedException
	 * @throws SystemException
	 */
	public void begin() throws NotSupportedException, SystemException {
		userTransaction.begin();
	}

	/**
	 * commit a usertransaction
	 *
	 * @throws SecurityException if transaction commit fails.
	 * @throws IllegalStateException if transaction commit fails.
	 * @throws RollbackException if transaction commit fails.
	 * @throws HeuristicMixedException if transaction commit fails.
	 * @throws HeuristicRollbackException if transaction commit fails.
	 * @throws SystemException if transaction commit fails.
	 */
	public void commit() throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException, SystemException {
			userTransaction.commit();
	}

	/**
	 * rollback of an transaction
	 *
	 * @throws SecurityException if transaction commit fails.
	 * @throws IllegalStateException if transaction commit fails.
	 * @throws RollbackException if transaction commit fails.
	 * @throws HeuristicMixedException if transaction commit fails.
	 * @throws HeuristicRollbackException if transaction commit fails.
	 * @throws SystemException if transaction commit fails.
	 */
	public void rollback() throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException {
			userTransaction.rollback();
	}

	/**
	 * @return status value (see javax/transaction/Status.class)
	 * @throws SystemException if getting the status failed.
	 */
	public int getStatus() throws SystemException{
		return userTransaction.getStatus();
	}
	
	
}
