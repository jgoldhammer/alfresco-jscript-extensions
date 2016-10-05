package de.jgoldhammer.alfresco.jscript.policy;

import com.tradeshift.test.remote.Remote;
import com.tradeshift.test.remote.RemoteTestRunner;
import de.jgoldhammer.alfresco.jscript.BaseAlfrescoTest;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.model.Repository;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.util.test.junitrules.ApplicationContextInit;
import org.alfresco.util.test.junitrules.TemporaryNodes;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by jgoldhammer on 16.09.16.
 */
public class ScriptPoliciesTest extends BaseAlfrescoTest {

	@Autowired
	Repository repo;

	@Autowired
	ScriptPolicies scriptPolicies;

	@Autowired
	NodeService nodeService;

	@Test
	public void enableForNode() throws Exception {
		NodeRef nodeRef = repo.getCompanyHome();
		Assert.assertNotNull(nodeRef);

		ScriptNode node = new ScriptNode(nodeRef, getServiceRegistry());
		scriptPolicies.disableForNode(node);
		node.setName("Tralala");
		node.save();

	}

	@Test
	public void enableForTypeOrAspect() throws Exception {

	}

	@Test
	public void disableForTypeOrAspect() throws Exception {

	}

	@Test
	public void enableAll() throws Exception {

	}

	@Test
	public void disableForNode() throws Exception {

	}

}