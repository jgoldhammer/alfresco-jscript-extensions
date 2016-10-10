package de.jgoldhammer.alfresco.jscript.links;

import de.jgoldhammer.alfresco.jscript.BaseAlfrescoTest;
import org.alfresco.model.ApplicationModel;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.service.cmr.repository.NodeRef;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jgoldhammer on 16.09.16.
 */
@Ignore("Not working on travis- freezing the travis process")
public class ScriptLinkServiceTest extends BaseAlfrescoTest {

	@Autowired
	ScriptLinkService scriptLinkService;

	NodeRef documentId ;


	NodeRef tempFolder;

	@Before
	public void init(){
		AuthenticationUtil.setAdminUserAsFullyAuthenticatedUser();
		documentId = generateTempDocument();
		tempFolder = generateTempFolder();

	}

	@Test
	public void shouldCreateDocumentLink() throws Exception {
		transactionHelper.doInTransaction(new RetryingTransactionHelper.RetryingTransactionCallback<Void>(){
			@Override
			public Void execute() throws Throwable {
				ScriptNode newDocumentLink = scriptLinkService.createLink(new ScriptNode(documentId, getServiceRegistry()),
						new ScriptNode(tempFolder, getServiceRegistry()));
				Assert.assertNotNull(newDocumentLink);
				Assert.assertTrue(getServiceRegistry().getNodeService().getType(newDocumentLink.getNodeRef()).
						equals(ApplicationModel.TYPE_FILELINK));
				return null;
			}
		});
	}

	@Test
	public void deleteLinks() throws Exception {
		// create the link
		transactionHelper.doInTransaction(new RetryingTransactionHelper.RetryingTransactionCallback<Void>(){
			@Override
			public Void execute() throws Throwable {
				ScriptNode newDocumentLink = scriptLinkService.createLink(new ScriptNode(documentId, getServiceRegistry()),
						new ScriptNode(tempFolder, getServiceRegistry()));
				Assert.assertNotNull(newDocumentLink);
				Assert.assertTrue(getServiceRegistry().getNodeService().getType(newDocumentLink.getNodeRef()).
						equals(ApplicationModel.TYPE_FILELINK));

				return null;
			}
		},false,true);

		// delete the new created link
		transactionHelper.doInTransaction(new RetryingTransactionHelper.RetryingTransactionCallback<Void>() {
			@Override
			public Void execute() throws Throwable {
				scriptLinkService.deleteLinks(new ScriptNode(documentId, getServiceRegistry()));
				return null;
			}
		});
	}

}