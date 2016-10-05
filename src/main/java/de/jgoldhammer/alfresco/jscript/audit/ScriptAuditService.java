/**
 *
 */
package de.jgoldhammer.alfresco.jscript.audit;

import com.google.common.collect.Maps;
import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.service.cmr.audit.AuditQueryParameters;
import org.alfresco.service.cmr.audit.AuditService;
import org.alfresco.service.cmr.audit.AuditService.AuditQueryCallback;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * ScriptAuditService is a wrapper around the Auditservice of Alfresco. It allows to enable/disable the auditservice,
 * query the auditservice and clearing the audit entries of a given app.
 *
 * @author jgoldhammer
 * 
 */
public class ScriptAuditService extends BaseScopableProcessorExtension {

	AuditService auditService;

	public void setAuditService(AuditService auditService) {
		this.auditService = auditService;
	}

	public boolean isAllEnabled() {
		return auditService.isAuditEnabled();
	}

	
	public boolean isEnabledFor(String appName, String path) {
		return auditService.isAuditEnabled(appName, path);
	}

	public void enableAll() {
		auditService.setAuditEnabled(true);
	}

	public void disableAll() {
		auditService.setAuditEnabled(false);
	}

	public Map<String, AuditService.AuditApplication> getApplications(){
		return auditService.getAuditApplications();
	}

	public void clearAll(String appName) {
		auditService.clearAudit(appName, null, null);
	}

	/**
	 * Remove .audit entries for the given application between the time ranges.
	 * If no start time is given then entries are deleted as far back as they
	 * exist. If no end time is given then entries are deleted up until the
	 * current time.
	 * 
	 * @param appName
	 *            the name of the application for which to remove entries
	 * @param start
	 *            the start time of entries to remove (inclusive and optional)
	 * @param end
	 *            the end time of entries to remove (exclusive and optional)
	 *
	 * @since 3.4
	 **/
	public void clear(String appName, long start, long end) {
		auditService.clearAudit(appName, start, end);
	}

	/**
	 * Issue an audit query using the given parameters and consuming results.
	 * Results are returned in entry order, corresponding to time order.
	 *
	 * @param appName
	 *            if not null, find entries logged against this application
	 * @param user
	 *            if not null, find entries logged against this user
	 * @param path
	 *            if not null, find entries logged against this path
	 * @param fromTime
	 *            the start search time (<tt>null</tt> to start at the
	 *            beginning)
	 * @param toTime
	 *            the end search time (<tt>null</tt> for no limit)
	 * @param forward
	 *            <tt>true</tt> for results to ordered from first to last, or
	 *            <tt>false</tt> to order from last to first
	 * @param limit
	 *            the maximum number of results to retrieve (zero or negative to
	 *            ignore)
	 * @param valuesRequired
	 *            Determines whether the entries will be populated with data
	 * @return an array of maps with key=noderef and values=entryvalues
	 */
	public Map<String, ScriptAuditValue> query(String appName, String user,
			String path, Long fromTime, Long toTime, Boolean forward,
			Integer limit, Boolean valuesRequired) {

		final AuditQueryParameters params = createAuditParameters(appName,
				user, fromTime, toTime, forward);

		if (valuesRequired == null) {
			valuesRequired = Boolean.TRUE;
		}

		if (limit == null) {
			limit = 25;
		}

		final Map<String, ScriptAuditValue> results = Maps.newLinkedHashMap();
		auditService.auditQuery(new AuditQueryCallback() {
			@Override
			public boolean valuesRequired() {
				return true;
			}

			@Override
			public boolean handleAuditEntryError(Long entryId, String errorMsg,
					Throwable error) {
				// LOG.warn(
				// "Error fetching tagging update entry - " + errorMsg,
				// error);
				// Keep trying
				return true;
			}

			@Override
			public boolean handleAuditEntry(Long entryId,
					String applicationName, String user, long time,
					Map<String, Serializable> values) {
				results.put(String.valueOf(entryId), new ScriptAuditValue(
						applicationName, user, time, values));
				return true;

			}
		}, params, limit);
		return results;
	}

	private AuditQueryParameters createAuditParameters(String appName,
			String user, Long fromTime, Long toTime, Boolean forward) {
		final AuditQueryParameters params = new AuditQueryParameters();

		if (forward != null) {
			params.setForward(forward);
		}

		if (StringUtils.isNotBlank(appName)) {
			params.setApplicationName(appName);
		}

		if (StringUtils.isNotBlank(user)) {
			params.setUser(user);
		}

		if (fromTime != null) {
			params.setFromTime(fromTime);
		}

		if (toTime != null) {
			params.setToTime(toTime);
		}
		return params;
	}

}
