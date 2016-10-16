package de.jgoldhammer.alfresco.jscript.jobs;

import com.google.common.base.Preconditions;
import de.jgoldhammer.alfresco.jscript.RhinoUtils;
import de.jgoldhammer.alfresco.jscript.batchexecuter.BatchJobParameters;
import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.repo.lock.JobLockService;
import org.alfresco.service.cmr.repository.ScriptService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;


import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;



import java.text.ParseException;
import java.util.HashMap;

import java.util.Map;



/**
 * the script job service can read the configured quartz jobs within a alfresco repository.
 * So you can iterate over all jobs, get a job by name or print the details.
 * Running, state checking and cancel a job run is part of the ScriptJob class.
 * 
 * @author jgoldhammer
 * 
 */
public class ScriptJobService extends BaseScopableProcessorExtension {

	ScriptService scriptService;

	public void setScriptService(ScriptService scriptService) {
		this.scriptService = scriptService;
	}

	Scheduler scheduler;

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	JobLockService jobLockService;

	public void setJobLockService(JobLockService jobLockService) {
		this.jobLockService = jobLockService;
	}

	private Map<String, ScriptJob> getJobs() {
		Map<String, ScriptJob> jobs = new HashMap<>();

		try {
			String[] jobGroupNames = scheduler.getJobGroupNames();
			for (String jobGroupName : jobGroupNames) {
				String[] jobNames = scheduler.getJobNames(jobGroupName);
				for (String jobName : jobNames) {
					Trigger[] jobTriggers = scheduler.getTriggersOfJob(jobName, jobGroupName);
					Trigger jobTrigger = jobTriggers[0];
					ScriptJob job = new ScriptJob(jobName, jobGroupName, scheduler,
							jobTrigger.getPreviousFireTime(),
							jobTrigger.getNextFireTime(),
							jobTrigger.getCalendarName(),
							jobTrigger.getName(),
							jobTrigger.getGroup());

					if (jobTrigger instanceof CronTrigger) {
						job.setCronExpression(((CronTrigger) jobTrigger).getCronExpression());
					}
					jobs.put(jobName, job);

				}
			}
		}catch (SchedulerException e) {
			throw new AlfrescoRuntimeException("Cannot determine the configured alfresco jobs via quartz ",e);
		}
		return jobs;
	}

	public Scriptable getAllJobs() {
		Map<String, ScriptJob> jobs = getJobs();

		Scriptable scope = getScope();
		Object[] jobsArray = jobs.values().toArray(new Object[jobs.size()]);
		return Context.getCurrentContext().newArray(scope, jobsArray);
	}

	public ScriptJob getJob(String name) {
		Map<String, ScriptJob> jobs = getJobs();
		return jobs.get(name);
	}

	public void pauseJobs(){
		try {
			scheduler.pauseAll();
		} catch (SchedulerException e) {
			throw new AlfrescoRuntimeException("Unable to pause all jobs",e);
		}
	}

	public void resumeJobs(){
		try {
			scheduler.resumeAll();
		} catch (SchedulerException e) {
			throw new AlfrescoRuntimeException("Unable to resume all jobs",e);
		}
	}

	public void standbyScheduler(){
		try {
			scheduler.standby();
		} catch (SchedulerException e) {
			throw new AlfrescoRuntimeException("Unable to standby the scheduler",e);
		}
	}


	public void startScheduler(){
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			throw new AlfrescoRuntimeException("Unable to standby the scheduler",e);
		}
	}

	/**
	 * schedules a temporary (e.g. the job is not living longer than alfresco) job
	 * with an inline script which is executed in each job run.
	 *
	 *
	 * @param jobName the job name - will be enhanced with the runasuser
	 * @param script
	 * @param runAsUser system or null for the system user, any other string for an valid alfresco user
	 * @param cronexpression the expression for quartz to run the job, e.g. you can use http://www.cronmaker.com for generating a cron expression
	 */
	public void scheduleTemporaryJob(String jobName, String script, String runAsUser, String cronexpression){

		// extract params



		// define the job and tie it to our ExecuteScriptJob class
		JobDetail job = new JobDetail(jobName +" (run as "+(runAsUser!=null ? runAsUser : "system")+")", "scriptJobGroup", ExecuteScriptJob.class);
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(ExecuteScriptJob.PARAM_RUN_AS, runAsUser);
		jobDataMap.put(ExecuteScriptJob.PARAM_SCRIPT,script);
		jobDataMap.put(ExecuteScriptJob.PARAM_SCRIPT_SERVICE, scriptService);
		jobDataMap.put("jobLockService", jobLockService);
		job.setJobDataMap(jobDataMap);

		try {
			// create a crontrigger
			CronTrigger trigger =
					new CronTrigger("scriptJobTrigger ("+System.nanoTime()+")", "scriptJobTriggerGroup", cronexpression);

			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job, trigger);

		} catch (SchedulerException e) {
			throw new AlfrescoRuntimeException("Cannot schedule a new executeScriptJob with script="+script+" and runAs="+runAsUser,e);
		} catch (ParseException e) {
			throw new AlfrescoRuntimeException("Cannot schedule a new executeScriptJob with script="+script+", cronexpression="+cronexpression+" and runAs="+runAsUser,e);
		}

	}

	/**
	 * schedules a temporary (e.g. the job is not living longer than alfresco) job
	 * with an inline script which is executed in each job run.
	 *
	 * The job can be canceled by calling jobs.getJobByName('','scriptJobGroup').cancel();
	 *
	 * @param jobParameter the job parameter object from javascript - a js object with following properties
	 *                     jobName
	 *                     runAs
	 *                     cronExpression
	 *
	 *                     and the function
	 *                     script
	 *
	 *   jobs.scheduleTemporaryJob({
	 *      jobName: '',
	 *      runAs: '',
	 *      cronExpression: '',
	 *      script: function(){
	 *          batchExecuter.processFolderRecursively({
	 *				root: companyhome,
	 *				onNode: function() {
	 *					if (node.isDocument) {
	 *						node.properties['cm:author'] = "Ciber NL";
	 *						node.save();
	 *					}
	 *				}
	 *			});
	 *
	 *      }
	 *   });
	 *
	 *
	 */
	public String scheduleTemporaryJob(Object jobParameter){

		// extract params
		Map<String, Object> paramsMap = BatchJobParameters.getParametersMap(jobParameter);
		String jobName = RhinoUtils.getString(paramsMap,"jobName", "Inline Script Job ("+System.nanoTime()+")");
		String runAsUser = RhinoUtils.getString(paramsMap,ExecuteScriptJob.PARAM_RUN_AS,"system");

		String cronExpression = Preconditions.checkNotNull(
				RhinoUtils.getString(paramsMap,"cronExpression",null));
		Function scriptFunction = Preconditions.checkNotNull(RhinoUtils.getFunction(paramsMap,ExecuteScriptJob.PARAM_SCRIPT));

		Context cx = Context.enter();
		String script = cx.decompileFunctionBody(scriptFunction,3).trim();

		// define the job and tie it to our ExecuteScriptJob class
		String newJobName = jobName + " (run as " + (runAsUser != null ? runAsUser : "system") + ")";
		JobDetail job = new JobDetail(newJobName, "scriptJobGroup", ExecuteScriptJob.class);
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(ExecuteScriptJob.PARAM_RUN_AS, runAsUser);
		jobDataMap.put(ExecuteScriptJob.PARAM_SCRIPT,script);
		jobDataMap.put(ExecuteScriptJob.PARAM_SCRIPT_SERVICE, scriptService);
		jobDataMap.put("jobLockService", jobLockService);
		job.setJobDataMap(jobDataMap);

		try {
			// create a crontrigger
			CronTrigger trigger =
					new CronTrigger("scriptJobTrigger ("+cronExpression+" " +System.nanoTime()+")", "scriptJobTriggerGroup", cronExpression);

			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job, trigger);

		} catch (SchedulerException e) {
			throw new AlfrescoRuntimeException("Cannot schedule a new executeScriptJob with script="+script+" and runAs="+runAsUser,e);
		} catch (ParseException e) {
			throw new AlfrescoRuntimeException("Cannot schedule a new executeScriptJob with script="+script+", cronexpression="+cronExpression+" and runAs="+runAsUser,e);
		} finally {
			Context.exit();
		}

		return newJobName;
	}


	public String printJobDetails() {
		return StringUtils.join(getJobs().values(),"\n");
	}

}
