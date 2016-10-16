/**
 *
 */
package de.jgoldhammer.alfresco.jscript.jobs;

import org.alfresco.error.AlfrescoRuntimeException;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import javax.management.*;
import java.util.Date;
import java.util.List;

/**
 * class representing a job class which can be used to trigger a new run, check if the job is running and
 * cancel a running job (when the job class is supporting it)
 *
 * @author jgoldhammer
 *
 */
public class ScriptJob {

	public final String jobName;
	public final String groupName;
	public final Scheduler scheduler;
	public final Date previousFireTime;
	public final Date nextFireTime;
	public final String calendarName;
	public final String triggerName;
	public final String triggerGroup;
	public String cronExpression;


	public ScriptJob(String jobName, String jobGroupName, Scheduler scheduler, Date previousFireTime,
					 Date nextFireTime, String calendarName, String triggerName, String triggerGroup) {
		this.jobName = jobName;
		groupName = jobGroupName;
		this.scheduler = scheduler;
		this.previousFireTime = previousFireTime;
		this.nextFireTime = nextFireTime;
		this.calendarName = calendarName;
		this.triggerName = triggerName;
		this.triggerGroup = triggerGroup;
	}

	/**
	 * starts to trigger the job via quartz runtime which will start the job.
	 */
	public void runNow() {
		try {
			scheduler.triggerJob(this.jobName, this.groupName);
		} catch (SchedulerException e) {
			throw new AlfrescoRuntimeException("Cannot start job "+this,e);		}
	}

	/**
	 * checks if the current job is running
	 * @return true if running, false if not.
	 */
	public boolean isRunning(){
		boolean isRunning=false;
		try {
			List<JobExecutionContext> currentlyExecutingJobs = scheduler.getCurrentlyExecutingJobs();
			for (JobExecutionContext job: currentlyExecutingJobs){
				if(job.getJobDetail().getName().equals(this.jobName) &&
					job.getJobDetail().getGroup().equals(this.groupName)){
					isRunning=true;
				}
			}

		} catch (SchedulerException e) {
			throw new AlfrescoRuntimeException("Cannot check if the current job "+this+" is running",e);
		}

		return isRunning;
	}

	@Override
	public String toString() {
		return "ScriptJob{" +
				"jobName='" + jobName + '\'' +
				", groupName='" + groupName + '\'' +
				", scheduler=" + scheduler +
				", previousFireTime=" + previousFireTime +
				", nextFireTime=" + nextFireTime +
				", calendarName='" + calendarName + '\'' +
				", triggerName='" + triggerName + '\'' +
				", triggerGroup='" + triggerGroup + '\'' +
				", cronExpression='" + cronExpression + '\'' +
				'}';
	}

	public void cancelRun(){
		try {
			scheduler.unscheduleJob(this.triggerName, this.triggerGroup);
		} catch (SchedulerException e) {
			throw new AlfrescoRuntimeException("Unable to cancel the job "+this,e);
		}
	}

	public void pauseJob(){
		try {
			scheduler.pauseJob(this.jobName, this.groupName);
		} catch (SchedulerException e) {
			throw new AlfrescoRuntimeException("Unable to pause the job "+this,e);
		}
	}

	/**
	 * resume a paused job.
	 */
	public void resumeJob(){
		try {
			scheduler.resumeJob(this.jobName, this.groupName);
		} catch (SchedulerException e) {
			throw new AlfrescoRuntimeException("Unable to resume the job "+this,e);
		}
	}

	/**
	 * to delete the job from the scheduler.
	 *
	 */
	public void deleteJob(){
		try {
			scheduler.deleteJob(this.jobName, this.groupName);
		} catch (SchedulerException e) {
			throw new AlfrescoRuntimeException("Cannot delete the job with name "+jobName+" and group "+groupName,e);
		}
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}


}
