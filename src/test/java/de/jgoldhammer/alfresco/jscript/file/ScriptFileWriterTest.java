package de.jgoldhammer.alfresco.jscript.file;

import de.jgoldhammer.alfresco.jscript.BaseAlfrescoTest;
import de.jgoldhammer.alfresco.jscript.repository.ScriptRepository;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.service.cmr.repository.ContentReader;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

/**
 * test on ScriptFileWriter.
 */
public class ScriptFileWriterTest extends BaseAlfrescoTest{

	@Autowired
	ScriptFileWriter fileWriter;

	@Autowired
	ScriptRepository scriptRepository;


	@Before
	public void init() throws SystemException, NotSupportedException {
		AuthenticationUtil.setAdminUserAsFullyAuthenticatedUser();
	}

	@Test
	public void shouldWriteContentToRepository(){
		transactionHelper.doInTransaction(new RetryingTransactionHelper.RetryingTransactionCallback<Void>() {

			@Override
			public Void execute() throws Throwable {
				fileWriter.createFile();
				fileWriter.appendLine("Hello");

				ScriptNode testReport = fileWriter.persist("Test"+System.nanoTime(), scriptRepository.getCompanyHome().getNodeRef().toString(),
						"text/plain", "cm:content", false);
				ContentReader reader = fileFolderService.getReader(testReport.getNodeRef());
				Assert.assertTrue(reader.getContentString().equals("Hello"+IOUtils.LINE_SEPARATOR));
				Assert.assertTrue(reader.getMimetype().equals("text/plain"));
				Assert.assertTrue(nodeService.getType(testReport.getNodeRef()).toPrefixString().equals("cm:content"));
				return null;
			}
		});

	}
}
