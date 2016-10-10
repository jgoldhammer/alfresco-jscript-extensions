package de.jgoldhammer.alfresco.jscript.quickshare;

import de.jgoldhammer.alfresco.jscript.BaseAlfrescoTest;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.transaction.TransactionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import java.util.Map;

/**
 * test for quickshare service
 */
public class ScriptQuickshareServiceTest extends BaseAlfrescoTest {

	@Autowired
	ScriptQuickshareService scriptQuickshareService;

	@Autowired
	TransactionService transactionService;

	@Autowired
	RetryingTransactionHelper retryingTransactionHelper;
	private NodeRef testDocument;

	@Before
	public void init() throws SystemException, NotSupportedException {
		AuthenticationUtil.setRunAsUserSystem();
		testDocument = generateTempDocument();

	}


	@Test
	public void canShareContent() throws Exception {

		retryingTransactionHelper.doInTransaction(new RetryingTransactionHelper.RetryingTransactionCallback<Void>() {
			@Override
			public Void execute() throws Throwable {
				ScriptNode testNode = new ScriptNode(testDocument, getServiceRegistry());
				String sharedId = scriptQuickshareService.shareContent(testNode);
				Map<String, Object> metadata = scriptQuickshareService.getMetadata(sharedId);
				Assert.assertNotNull(metadata.get("nodeRef"));
				Assert.assertNotNull(metadata.get("sharedId"));
				Assert.assertNull(metadata.get("sharable"));
				return null;
			}
		});

	}

	@Test
	public void canUnShareContent() throws Exception {

		retryingTransactionHelper.doInTransaction(new RetryingTransactionHelper.RetryingTransactionCallback<Void>() {

			  @Override
			  public Void execute() throws Throwable {
				  ScriptNode scriptNode = new ScriptNode(testDocument, getServiceRegistry());

				  String shareId = scriptQuickshareService.shareContent(scriptNode);
				  scriptQuickshareService.unshareContent(shareId);

				  Map<String, Object> metadata = scriptQuickshareService.getMetadata(scriptNode);
				  Assert.assertNotNull(metadata);
				  Assert.assertNull(metadata.get("sharedId"));
				  return null;
			  }
		});


	}



}