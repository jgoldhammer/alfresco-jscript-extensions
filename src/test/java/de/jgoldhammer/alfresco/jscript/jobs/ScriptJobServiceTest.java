package de.jgoldhammer.alfresco.jscript.jobs;

import de.jgoldhammer.alfresco.jscript.BaseAlfrescoTest;
import org.alfresco.repo.dictionary.types.period.Cron;
import org.junit.Assert;
import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.ScriptableUtils;

/**
 * tests for the ScriptJobService
 */
public class ScriptJobServiceTest extends BaseAlfrescoTest{

    @Autowired
    protected ScriptJobService scriptJobService;

    private ScriptableObject getScope() {
        Context ctx = Context.getCurrentContext();
        boolean closeContext = false;
        if(ctx == null) {
            ctx = Context.enter();
            closeContext = true;
        }

        ScriptableObject scope = ctx.initStandardObjects();
        scope.setParentScope((Scriptable)null);
        if(closeContext) {
            Context.exit();
        }

        return scope;
    }

    @Test
    public void shouldListJobs(){

        try {
            Context cx = Context.enter();
            Scriptable scope = cx.initStandardObjects();
            scriptJobService.setScope(scope);
            final NativeArray allJobs = (NativeArray) scriptJobService.getAllJobs();
            Assert.assertTrue(allJobs.getIds().length > 0);
            ScriptJob job = (ScriptJob) allJobs.get(0);
            Assert.assertNotNull(job.jobName);
            Assert.assertNotNull(job.nextFireTime);

        }finally{
            Context.exit();
        }

    }


    @Test
    public void shouldReturnJobByName(){
        ScriptJob job = scriptJobService.getJob("feedCleanerJobDetail");
        Assert.assertNotNull(job);
        Assert.assertNotNull(job.jobName);
        Assert.assertFalse(job.isRunning());
    }

    @Test
    public void shouldStartJobByRequest() throws InterruptedException {
        ScriptJob job = scriptJobService.getJob("feedCleanerJobDetail");
        Assert.assertNotNull(job);
        Assert.assertNotNull(job.jobName);
        job.runNow();
        Thread.sleep(5000);
        Assert.assertTrue(job.isRunning());
    }

    @Test
    public void testCreateJob() throws InterruptedException {

    // using script service and inline script to test the js

        String script =
                "jobs.scheduleTemporaryJob({"+
                    "jobName: 'SimpleJobTest',"+
                    "runAs: 'system',"+
                    "cronExpression: '0/10 * * * * *',"+
                    "script: function(){"+
                        "batchExecuter.processFolderRecursively({'root': companyhome,"+
                            "onNode: function() {"+
                                "if (node.isDocument) {"+
                                    "   node.properties['cm:author'] = 'Ciber NL';"+
                                    "	node.save();"+
                                "}"+
                            "}"+
                        "});"+
                    "}"+
                "})";

        scriptService.executeScriptString(script,null);
        Thread.sleep(1000 * 20);

    }


    @Test
    public void testCreateJobSimple() throws InterruptedException {
        scriptJobService.scheduleTemporaryJob("Blu","var test='hello'","system","0/10 * * * * *");

        Thread.sleep(1000*20);
    }



}
