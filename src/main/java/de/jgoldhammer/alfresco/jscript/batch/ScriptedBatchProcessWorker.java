package de.jgoldhammer.alfresco.jscript.batch;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.alfresco.repo.batch.BatchProcessor.BatchProcessWorker;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.ScriptService;
import org.apache.commons.lang.StringUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/**
 * a batch process worker which uses the scriptservice to execute the provided script for a set of nodes.
 *
 * @author jgoldhammer
 *
 */
public final class ScriptedBatchProcessWorker implements BatchProcessWorker<Collection<NodeRef>> {
	private final boolean runAsSystem;
	private final Scriptable batchScope;
	private final String processorFunction;
	private Context context;
	private static final String PROCESSING_SCRIPT = "\n for (var index = 0; index < processingNodes.length; index++) {process(processingNodes[index]);}";
	private static final String BEFORE_PROCESSING_SCRIPT = "\n beforeProcess(processingNodes)";
	private static final String AFTER_PROCESSING_SCRIPT = "\n afterProcess(processingNodes)";
	private ScriptService scriptService;
	private ServiceRegistry serviceRegistry;
	private String beforeProcessFunction;
	private String afterProcessFuntion;
	private List<NodeRef> nodeRefs;

	ScriptedBatchProcessWorker(boolean runAsSystem, Scriptable batchScope, String processorFunction, List<NodeRef> nodeRefs, String beforeProcessFunction, String afterProcessFuntion, Context context,
			ServiceRegistry serviceRegistry, ScriptService scriptService) {
		this.runAsSystem = runAsSystem;
		this.batchScope = batchScope;
		this.processorFunction = processorFunction;
		this.nodeRefs = nodeRefs;
		this.beforeProcessFunction = beforeProcessFunction;
		this.afterProcessFuntion = afterProcessFuntion;
		this.context = context;
		this.serviceRegistry = serviceRegistry;
		this.scriptService = scriptService;
	}

	@Override
	public void beforeProcess() throws Throwable {
		if (runAsSystem) {
			AuthenticationUtil.setRunAsUserSystem();
		}

		if(StringUtils.isNotBlank(beforeProcessFunction)){
			Object[] scriptNodes = createScriptNodes(this.nodeRefs);
			scriptService.executeScriptString("javascript", beforeProcessFunction+BEFORE_PROCESSING_SCRIPT, createNodesModel(scriptNodes));
		}

	}

	@Override
	public void afterProcess() throws Throwable {
		if(StringUtils.isNotBlank(afterProcessFuntion)){
			Object[] scriptNodes = createScriptNodes(this.nodeRefs);
			scriptService.executeScriptString("javascript", afterProcessFuntion+AFTER_PROCESSING_SCRIPT, createNodesModel(scriptNodes));
		}
	}

	@Override
	public String getIdentifier(Collection<NodeRef> entries) {
		return entries.toString();
	}

	@Override
	public void process(Collection<NodeRef> entries) throws Throwable {
		Object[] scriptNodes = createScriptNodes(entries);

		String javascriptCode = processorFunction + PROCESSING_SCRIPT;
		scriptService.executeScriptString("javascript", javascriptCode, createNodesModel(scriptNodes));

	}

	/**
	 * @param entries
	 * @return
	 */
	private Object[] createScriptNodes(Collection<NodeRef> entries) {
		Object[] scriptNodes = new Object[entries.size()];
		int counter = 0;
		for (NodeRef nodeRef : entries) {
			scriptNodes[counter] = new ScriptNode(nodeRef, serviceRegistry);
			counter++;
		}
		return scriptNodes;
	}

	/**
	 * creates
	 *
	 * @param scriptNodes
	 * @return
	 */
	private HashMap<String, Object> createNodesModel(Object[] scriptNodes) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put("processingNodes", context.newArray(batchScope, scriptNodes));
		return model;
	}
}