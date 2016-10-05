/**
 *
 */
package de.jgoldhammer.alfresco.jscript.jobs;

import org.apache.commons.lang.StringUtils;
import org.quartz.SchedulerException;

import javax.management.*;

/**
 * class representing a java class
 *
 * @author jgoldhammer
 *
 */
public class ScriptJob {

	private ObjectName objectName;
	private MBeanServer alfrescoMBeanServer;

	public ScriptJob(ObjectName objectName, MBeanServer alfrescoMBeanServer) {
		this.objectName = objectName;
		this.alfrescoMBeanServer = alfrescoMBeanServer;
	}

	public String getName(){
		return StringUtils.substringAfterLast(objectName.getCanonicalName(), "=");
	}

	public void run() throws InstanceNotFoundException, ReflectionException, MBeanException, SchedulerException{
		alfrescoMBeanServer.invoke(objectName, "executeNow", null, null);
	}

	public Object getState() throws InstanceNotFoundException, ReflectionException, MBeanException, SchedulerException{
		return alfrescoMBeanServer.invoke(objectName, "getState", null, null);
	}



}
