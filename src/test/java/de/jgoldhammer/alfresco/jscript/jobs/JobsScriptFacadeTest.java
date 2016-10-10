package de.jgoldhammer.alfresco.jscript.jobs;

import com.tradeshift.test.remote.Remote;
import com.tradeshift.test.remote.RemoteTestRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mozilla.javascript.Scriptable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jgoldhammer on 25.11.15.
 */
@RunWith(RemoteTestRunner.class)
@Remote(runnerClass=SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:alfresco/application-context.xml")
@Ignore("Not working in community version")
public class JobsScriptFacadeTest {

    @Autowired
    protected ScriptJobService scriptJobService;

    @Test
    public void testListJobs(){
        final Scriptable allJobNames = scriptJobService.getAllJobNames();

    }



}
