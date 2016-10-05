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

	@Before
	public void init() throws SystemException, NotSupportedException {
		AuthenticationUtil.setRunAsUserSystem();
	}


	@Test
	public void canShareContent() throws Exception {

//		retryingTransactionHelper.doInTransaction(new RetryingTransactionHelper.RetryingTransactionCallback<Void>() {
//
//			@Override
//			public Void execute() throws Throwable {
//				NodeRef testDocument = generateTempDocument();
//				ScriptNode testNode = new ScriptNode(testDocument, getServiceRegistry());
//				String sharedId = scriptQuickshareService.shareContent(testNode);
//				Map<String, Object> metadata = scriptQuickshareService.getMetadata(sharedId);
//				Assert.assertNotNull(metadata.get("nodeRef"));
//				Assert.assertNotNull(metadata.get("sharedId"));
//				Assert.assertNull(metadata.get("sharable"));
//				return null;
//			}
//		});

	}

	@Test
	public void canUnShareContent() throws Exception {

		NodeRef testDocument = retryingTransactionHelper.doInTransaction(new RetryingTransactionHelper.RetryingTransactionCallback<NodeRef>() {

			@Override
			public NodeRef execute() throws Throwable {
				return generateTempDocument();
			}
		});

//		retryingTransactionHelper.doInTransaction(new RetryingTransactionHelper.RetryingTransactionCallback<Void>() {
//
//			  @Override
//			  public Void execute() throws Throwable {
//				  ScriptNode scriptNode = new ScriptNode(testDocument, getServiceRegistry());
//
//				  String shareId = scriptQuickshareService.shareContent(scriptNode);
//				  scriptQuickshareService.unshareContent(shareId);
//
//				  Map<String, Object> metadata = scriptQuickshareService.getMetadata(shareId);
//				  Assert.assertNull(metadata);
//
//				  metadata = scriptQuickshareService.getMetadata(scriptNode);
//				  Assert.assertNotNull(metadata.get("nodeRef"));
//				  Assert.assertNull(metadata.get("sharedId"));
//				  Assert.assertNotNull(metadata.get("sharable"));
//				  return null;
//			  }
//		});


	}



}