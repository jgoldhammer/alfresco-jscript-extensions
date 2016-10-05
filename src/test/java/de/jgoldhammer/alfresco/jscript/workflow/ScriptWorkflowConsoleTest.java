package de.jgoldhammer.alfresco.jscript.workflow;

import com.tradeshift.test.remote.Remote;
import com.tradeshift.test.remote.RemoteTestRunner;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.hamcrest.core.StringContains;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by jgoldhammer on 16.09.16.
 */
@RunWith(RemoteTestRunner.class)
@Remote(runnerClass=SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:alfresco/application-context.xml")
public class ScriptWorkflowConsoleTest {

	@Autowired
	protected ScriptWorkflowConsole scriptWorkflowConsole;

	@Test
	public void testExecuteCommand() throws IOException {
		AuthenticationUtil.setAdminUserAsFullyAuthenticatedUser();
		String output = scriptWorkflowConsole.exec("help");
		Assert.assertNotNull(output);
		Assert.assertThat(output, StringContains.containsString("help"));
	}
}
