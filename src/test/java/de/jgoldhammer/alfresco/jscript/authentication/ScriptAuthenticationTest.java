package de.jgoldhammer.alfresco.jscript.authentication;

import de.jgoldhammer.alfresco.jscript.BaseAlfrescoTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * tests the ScriptAuthentication
 */
public class ScriptAuthenticationTest extends BaseAlfrescoTest {

	@Autowired
	ScriptAuthentication scriptAuthentication;

	@Test
	public void shouldAuthenticate(){
		Assert.assertTrue(scriptAuthentication.getAdminUserName().equals("admin"));
	}





}
