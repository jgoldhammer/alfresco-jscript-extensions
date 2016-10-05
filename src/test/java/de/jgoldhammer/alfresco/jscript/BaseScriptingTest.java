package de.jgoldhammer.alfresco.jscript;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.util.ApplicationContextHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * Base class for Alfresco tests using JavaScript execution.
 *
 * @author Bulat Yaminov
 */
public class BaseScriptingTest {

    private static final Log logger = LogFactory.getLog(BaseScriptingTest.class);

    protected static ServiceRegistry sr;
    protected static NodeService ns;
    protected static NodeRef testHome;
    protected static NodeRef companyHome;
    protected static ApplicationContext ctx;

    @BeforeClass
    public static void initAppContext() {
        ApplicationContextHelper.setUseLazyLoading(false);
        ApplicationContextHelper.setNoAutoStart(true);
        ctx = ApplicationContextHelper.getApplicationContext(
                new String[]{"classpath:alfresco/application-context.xml"});
        ns = (NodeService) ctx.getBean("NodeService");
        sr = (ServiceRegistry) ctx.getBean("ServiceRegistry");
        AuthenticationUtil.setFullyAuthenticatedUser("admin");
        companyHome = sr.getNodeLocatorService().getNode("companyhome", null, null);
        testHome = ns.getChildByName(companyHome, ContentModel.ASSOC_CONTAINS, "Tests");
        if (testHome == null) {
            logger.info("Creating folder /Company Home/Tests/");
            testHome = sr.getFileFolderService().create(companyHome, "Tests", ContentModel.TYPE_FOLDER).getNodeRef();
        }
        logger.info("Application Context loaded");
    }

    @After
    public void clearTestFolder() {
        if (ns.exists(testHome)) {
            List<FileInfo> children = sr.getFileFolderService().list(testHome);
            for (FileInfo child : children) {
                ns.deleteNode(child.getNodeRef());
            }
        }
    }

    protected NodeRef createTestDocument(String name) {
        return createTestDocument(name, testHome);
    }

    protected NodeRef createTestDocument(String name, NodeRef parent) {
        return sr.getFileFolderService().create(parent, name, ContentModel.TYPE_CONTENT).getNodeRef();
    }

    protected Object execute(String script) {
        return sr.getScriptService().executeScriptString(script, null);
    }

    protected Object executeWithModel(String script) {
        String userName = sr.getAuthenticationService().getCurrentUserName();
        NodeRef person = sr.getPersonService().getPerson(userName);
        NodeRef userHome = (NodeRef) sr.getNodeService().getProperty(person, ContentModel.PROP_HOMEFOLDER);

        Map<String, Object> model = sr.getScriptService().buildDefaultModel(person, companyHome,
                userHome, null, null, null);

        return sr.getScriptService().executeScriptString(script, model);
    }

    protected void executeWithModelNonBlocking(final String script) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AuthenticationUtil.setFullyAuthenticatedUser("admin");
                executeWithModel(script);
            }
        }).start();
    }
}
