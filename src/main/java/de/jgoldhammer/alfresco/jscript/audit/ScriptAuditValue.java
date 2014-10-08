/**
 * 
 */
package de.jgoldhammer.alfresco.jscript.audit;

import java.io.Serializable;
import java.util.Map;

/**
 * @author jgoldhammer
 * 
 */
public class ScriptAuditValue {

	private String applicationName;
	private String user;
	private long time;
	private Map<String, Serializable> values;

	public ScriptAuditValue(String applicationName, String user, long time,
			Map<String, Serializable> values) {
		this.applicationName = applicationName;
		this.user = user;
		this.time = time;
		this.values = values;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public String getUser() {
		return user;
	}

	public long getTime() {
		return time;
	}

	public Map<String, Serializable> getValues() {
		return values;
	}

	@Override
	public String toString() {
		return "ScriptAuditValue [applicationName=" + applicationName
				+ ", user=" + user + ", time=" + time + ", values=" + values
				+ "]";
	}

}
