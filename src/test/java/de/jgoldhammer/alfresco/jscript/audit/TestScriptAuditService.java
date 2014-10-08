/**
 * 
 */
package de.jgoldhammer.alfresco.jscript.audit;

import java.util.Map;

import junit.framework.Assert;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tradeshift.test.remote.Remote;
import com.tradeshift.test.remote.RemoteTestRunner;

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
    
    @SuppressWarnings("deprecation")
	@Test
    public void testIsAuditEnabled() {
    	AuthenticationUtil.setFullyAuthenticatedUser("admin");
    	boolean allEnabled = scriptAuditService.isAllEnabled();
    	Assert.assertTrue(allEnabled);
    }
    
 
    @SuppressWarnings("deprecation")
	@Test
    public void testQueryWithAlfNode() {
    	AuthenticationUtil.setFullyAuthenticatedUser("admin");
    	Map<String, ScriptAuditValue> query2 = scriptAuditService.query(null, null, null, null, null, null, null, null);
    	System.out.println(query2);
    	Assert.assertNotNull(query2);
    }
    
}