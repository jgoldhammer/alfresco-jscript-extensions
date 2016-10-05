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

	public void begin() throws NotSupportedException, SystemException {
		userTransaction.begin();
	}

	public void commit() throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException, SystemException {
			userTransaction.commit();
	}

	public void rollback() throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException {
			userTransaction.rollback();
	}

	/**
	 * @return status value (see javax/transaction/Status.class)
	 * @throws SystemException
	 */
	public int getStatus() throws SystemException{
		return userTransaction.getStatus();
	}
	
	
}
