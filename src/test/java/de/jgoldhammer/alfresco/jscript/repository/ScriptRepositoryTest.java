package de.jgoldhammer.alfresco.jscript.repository;

import de.jgoldhammer.alfresco.jscript.BaseAlfrescoTest;
import de.jgoldhammer.alfresco.jscript.quickshare.ScriptQuickshareService;
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
public class ScriptRepositoryTest extends BaseAlfrescoTest {

	@Autowired
	ScriptRepository scriptRepository;

	@Autowired
	RetryingTransactionHelper retryingTransactionHelper;

	@Before
	public void init() throws SystemException, NotSupportedException {
		AuthenticationUtil.setRunAsUserSystem();
	}

	@Test
	public void testNotNull() throws Exception {

		retryingTransactionHelper.doInTransaction(new RetryingTransactionHelper.RetryingTransactionCallback<Void>() {

			  @Override
			  public Void execute() throws Throwable {
				  Assert.assertNotNull(scriptRepository.getCompanyHome());
				  Assert.assertNotNull(scriptRepository.getPerson());
				  Assert.assertNotNull(scriptRepository.getUserHome());
				  Assert.assertNotNull(scriptRepository.getRootHome());
				  Assert.assertNotNull(scriptRepository.getPeopleContainer());
				  Assert.assertNotNull(scriptRepository.getSitesRoot());
				  return null;
			  }
		});


	}



}