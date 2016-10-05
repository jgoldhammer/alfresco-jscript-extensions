package de.jgoldhammer.alfresco.jscript.jobs;

import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.apache.commons.lang.StringUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * fascade to the solr index check service
 * 
 * ENTERPRISE ONLY
 * 
 * @author jgoldhammer
 * 
 */
public class ScriptJobService extends BaseScopableProcessorExtension {
	Map<String, ScriptJob> jobs = new HashMap();

	private MBeanServer alfrescoMBeanServer;

	public void setAlfrescoMBeanServer(MBeanServer alfrescoMBeanServer) {
		this.alfrescoMBeanServer = alfrescoMBeanServer;
	}

	public void init() throws MalformedObjectNameException, NullPointerException {
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
		return Context.getCurrentContext().newArray(getScope(), this.jobs.keySet().toArray(new String[jobs.size()]));
	}

	public ScriptJob getJob(String name) {
		return jobs.get(name);
	}

	public String listJobs() {
		return jobs.keySet().toString();
	}

}
