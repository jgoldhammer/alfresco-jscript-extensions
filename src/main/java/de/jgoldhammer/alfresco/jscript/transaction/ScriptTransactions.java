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
 * script object for handling the de.jgoldhammer.alfresco.jscript.transaction service.
 *
 * @author Jens Goldhammer (fme AG)
 */

@ScriptClass(types=ScriptClassType.JavaScriptRootObject, code= "transaction", help="the root object for the transactionservice")
public class ScriptTransactions extends BaseProcessorExtension {

	private TransactionService transactionService;

    public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

    @ScriptMethod(
    		help="get a new user transaction object- the transaction is not started yet. Please execute begin, commit, rollback and getStatus on the transaction.",
    		output="void",
    		code="de.jgoldhammer.alfresco.jscript.transaction.getUserTransaction()",
    		type=ScriptMethodType.WRITE)
    public ScriptTransaction getUserTransaction(){
    	return new ScriptTransaction(transactionService.getUserTransaction());
    }
    
    public boolean isReadOnly(){
    	return !transactionService.getAllowWrite();
    }

    
    
}
