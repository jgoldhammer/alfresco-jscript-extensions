/**
 *
 */
package de.jgoldhammer.alfresco.jscript.transaction;

import org.alfresco.repo.processor.BaseProcessorExtension;
import org.alfresco.service.transaction.TransactionService;
import org.springframework.extensions.webscripts.annotation.ScriptClass;
import org.springframework.extensions.webscripts.annotation.ScriptClassType;
import org.springframework.extensions.webscripts.annotation.ScriptMethod;
import org.springframework.extensions.webscripts.annotation.ScriptMethodType;

/**
 * script object for handling the behaviourFilter
 *
 * @author Jens Goldhammer (fme AG)
 */

@ScriptClass(types=ScriptClassType.JavaScriptRootObject, code="transaction", help="the root object for the policy/behaviourFilter")
public class ScriptTransactions extends BaseProcessorExtension {
    private TransactionService transactionService;
    public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

    @ScriptMethod(
    		help="get a transaction object- the transaction is not started yet.",
    		output="void",
    		code="transaction.startUserTransaction()",
    		type=ScriptMethodType.READ)
    public ScriptTransaction getTransaction(){
    	return new ScriptTransaction(transactionService.getUserTransaction());
    }

}
