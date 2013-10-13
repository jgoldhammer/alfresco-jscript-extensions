package de.jgoldhammer.alfresco.jscript.solr;

import java.util.Map;

import org.alfresco.enterprise.repo.admin.indexcheck.SOLRNodeInfo;
import org.alfresco.repo.jscript.Scopeable;
import org.mozilla.javascript.Scriptable;

/**
 * @author jgoldhammer
 *
 */
public class ScriptNodeIndexState {

	private SOLRNodeInfo nodeInfo;
	public Long nodeDbId;
	public Long dbTx;
	public String dbTxStatus;

	public ScriptNodeIndexState(SOLRNodeInfo nodeInfo) {
		Map<String, Object> values = nodeInfo.getValues();
		nodeDbId = (Long) values.get("Node DBID");
		dbTx = (Long) values.get("DB TX");
		dbTxStatus = (String) values.get("Node DBID");
	}


}
