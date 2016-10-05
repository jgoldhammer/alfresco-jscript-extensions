/**
 * 
 */
package de.jgoldhammer.alfresco.jscript.audit;

import com.tradeshift.test.remote.Remote;
import com.tradeshift.test.remote.RemoteTestRunner;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.audit.AuditService;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * @author jgoldhammer
 *
 */
@RunWith(RemoteTestRunner.class)
@Remote(runnerClass=SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:alfresco/application-context.xml")
public class TestScriptAuditService {
    
    static Logger log = Logger.getLogger(TestScriptAuditService.class);

    @Autowired
    protected ScriptAuditService scriptAuditService;
    
	@Test
    public void testIsAuditEnabled() {
    	AuthenticationUtil.setFullyAuthenticatedUser("admin");
    	boolean allEnabled = scriptAuditService.isAllEnabled();
    	Assert.assertTrue(allEnabled);
    }
    
 
	@Test
    public void testQueryWithAlfNode() {
    	AuthenticationUtil.setFullyAuthenticatedUser("admin");
    	Map<String, ScriptAuditValue> results = scriptAuditService.query(null, null, null, null, null, null, null, null);
    	System.out.println(results);
    	Assert.assertNotNull(results);
    }

	@Test
	public void testDisableAndEnableAll(){
		AuthenticationUtil.setFullyAuthenticatedUser("admin");
		scriptAuditService.disableAll();
		boolean allEnabled = scriptAuditService.isAllEnabled();
		Assert.assertFalse(allEnabled);

		scriptAuditService.enableAll();
		 allEnabled = scriptAuditService.isAllEnabled();
		Assert.assertTrue(allEnabled);
	}

	@Test
	public void testGetApplications(){
		AuthenticationUtil.setAdminUserAsFullyAuthenticatedUser();
		AuditService.AuditApplication auditApp = scriptAuditService.getApplications().entrySet().iterator().next().getValue();

		Assert.assertTrue(auditApp.getKey().contains("/tagging"));
		Assert.assertTrue(auditApp.getName().contains("Alfresco Tagging Service"));
		Assert.assertTrue(auditApp.isEnabled()==true);

	}
    
}