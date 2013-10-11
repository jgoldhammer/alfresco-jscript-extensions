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

	public void start() throws NotSupportedException, SystemException {
		userTransaction.begin();
	}

	public void commit() throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException, SystemException {
		if (userTransaction.getStatus() == javax.transaction.Status.STATUS_ACTIVE
				|| userTransaction.getStatus() != javax.transaction.Status.STATUS_COMMITTED) {
			userTransaction.commit();
		}
	}

	public void rollBack() throws SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException {
			userTransaction.rollback();
	}

}
