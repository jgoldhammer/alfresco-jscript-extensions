package de.jgoldhammer.alfresco.jscript.jobs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.alfresco.repo.jscript.Scopeable;
import org.alfresco.repo.processor.BaseProcessorExtension;
import org.apache.commons.lang.StringUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/**
 * fascade to the solr index check service
 * 
 * ENTERPRISE ONLY
 * 
 * @author jgoldhammer
 * 
 */
public class JobsScriptFacade extends BaseProcessorExtension implements Scopeable {
	Map<String, ScriptJob> jobs = new HashMap<String, ScriptJob>();
	private MBeanServer alfrescoMBeanServer;
	private Scriptable scope;

	public void setAlfrescoMBeanServer(MBeanServer alfrescoMBeanServer) {
		this.alfrescoMBeanServer = alfrescoMBeanServer;
	}

	public void init() throws MalformedObjectNameException, NullPointerException {
		// read jobs from jmx and fill the hashmap
		Set<ObjectName> queryNames = alfrescoMBeanServer.queryNames(null, null);
		for (ObjectName objectName : queryNames) {
			if(objectName.toString().startsWith("Alfresco:Name=Schedule,")){
				String jobName = StringUtils.substringAfter(objectName.toString(), "Trigger=");
				jobs.put(jobName, new ScriptJob(objectName,
						alfrescoMBeanServer));
			}
		}
		register();
	}

	public Scriptable getAllJobNames() {
		return Context.getCurrentContext().newArray(this.scope, this.jobs.keySet().toArray(new String[jobs.size()]));
	}

	public ScriptJob getJob(String name) {
		return jobs.get(name);
	}

	public String listJobs() {
		return jobs.keySet().toString();
	}

	@Override
	public void setScope(Scriptable scope) {
		this.scope = scope;
	}
}
