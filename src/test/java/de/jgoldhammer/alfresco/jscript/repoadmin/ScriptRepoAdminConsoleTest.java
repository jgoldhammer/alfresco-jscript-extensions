package de.jgoldhammer.alfresco.jscript.repoadmin;

import de.jgoldhammer.alfresco.jscript.BaseAlfrescoTest;
import de.jgoldhammer.alfresco.jscript.policy.ScriptPolicies;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.model.Repository;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * tests for the ScriptRepoAdminConsole
 *
 */
public class ScriptRepoAdminConsoleTest extends BaseAlfrescoTest {

	@Autowired
	ScriptRepoAdminConsole scriptRepoAdminConsole;


	@Test
	public void testExecution() throws Exception {
		AuthenticationUtil.setAdminUserAsFullyAuthenticatedUser();
		String output = scriptRepoAdminConsole.exec("help");
		Assert.assertNotNull(output);
		Assert.assertThat(output, StringContains.containsString("help"));
	}

}