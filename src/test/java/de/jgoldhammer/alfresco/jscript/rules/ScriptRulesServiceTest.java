package de.jgoldhammer.alfresco.jscript.rules;

import de.jgoldhammer.alfresco.jscript.BaseAlfrescoTest;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.NodeRef;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jgoldhammer on 14.11.16.
 */
public class ScriptRulesServiceTest extends BaseAlfrescoTest{


	@Autowired
	ScriptRulesService scriptRulesService;

	@Test
	public void isEnabled(){
		Assert.assertTrue(scriptRulesService.isEnabled());
		scriptRulesService.disableRules();
		Assert.assertFalse(scriptRulesService.isEnabled());
	}

	@Test
	@Ignore(value="Does not work as expected here... When")
	public void isEnabledForNode(){

		transactionHelper.doInTransaction(new RetryingTransactionHelper.RetryingTransactionCallback<Void>() {

			@Override
			public Void execute() throws Throwable {
				NodeRef documentId = generateTempDocument();
				Assert.assertTrue(scriptRulesService.rulesEnabled(documentId));
				scriptRulesService.disableRules(documentId);
				Assert.assertFalse(scriptRulesService.rulesEnabled(documentId));
				return null;
			}
		});


	}

}
