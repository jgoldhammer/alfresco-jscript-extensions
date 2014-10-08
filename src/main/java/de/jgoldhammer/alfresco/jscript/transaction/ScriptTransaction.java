/**
 *
 */
package de.jgoldhammer.alfresco.jscript.transaction;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * deals with the transaction object.
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

	public int getStatus() throws SystemException{
		return userTransaction.getStatus();
	}
	
	
}
