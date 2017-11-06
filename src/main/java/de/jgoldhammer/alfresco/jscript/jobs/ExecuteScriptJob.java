package de.jgoldhammer.alfresco.jscript.jobs;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.repo.security.authentication.AuthenticationComponent;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.schedule.AbstractScheduledLockedJob;
import org.alfresco.service.cmr.repository.ScriptLocation;
import org.alfresco.service.cmr.repository.ScriptService;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Quartz job that executes a scheduled inline js script.
 * The job execution is cluster aware and using the joblockservice.
 *
 * 
 * @author Jens Goldhammer
 */
public class ExecuteScriptJob extends AbstractScheduledLockedJob
{
	public static final String PARAM_SCRIPT = "script";
    public static final String PARAM_RUN_AS = "runAs";
    public static final String PARAM_SCRIPT_SERVICE = "scriptService";

    /**
     * Executes the scheduled script
     * 
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    public void executeJob(JobExecutionContext context) throws JobExecutionException
    {
        JobDataMap jobData = context.getJobDetail().getJobDataMap();
        
        // Get the script service from the job map
        Object scriptServiceObj = jobData.get(PARAM_SCRIPT_SERVICE);
        if (scriptServiceObj == null || !(scriptServiceObj instanceof ScriptService))
        {
            throw new AlfrescoRuntimeException(
                    "ExecuteScriptJob data must contain valid script service");
        }
        
        // Get the script location from the job map
        String script = (String) jobData.get(PARAM_SCRIPT);
        if (script == null || !(script instanceof String))
        {
            throw new AlfrescoRuntimeException(
                    "ExecuteScriptJob data must contain valid script as String");
        }

        String runAs=null;
        // Get the runAs =user  from the job map
        Object runAsParam = jobData.get(PARAM_RUN_AS);
        if (runAsParam != null && (runAsParam instanceof String))
        {
           runAs= (String) runAsParam;
        }

        try {

            if(runAs == null || runAs.equalsIgnoreCase("system")){
                AuthenticationUtil.setRunAsUserSystem();
            }
            else{
                AuthenticationUtil.setRunAsUser(runAs);
            }

            // Execute the script
            ((ScriptService)scriptServiceObj).executeScriptString((String)script, null);
        } finally
        {
            AuthenticationUtil.clearCurrentSecurityContext();
        }
    }
}
