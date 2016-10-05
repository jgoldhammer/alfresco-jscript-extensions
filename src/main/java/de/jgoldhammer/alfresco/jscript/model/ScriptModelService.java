package de.jgoldhammer.alfresco.jscript.model;

import org.alfresco.query.PagingRequest;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.processor.BaseProcessorExtension;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.dictionary.CustomModelDefinition;
import org.alfresco.service.cmr.dictionary.CustomModelService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.util.List;

/**
 * Created by jgoldhammer on 17.09.16.
 */
public class ScriptModelService extends BaseProcessorExtension {

	CustomModelService customModelService;
	ServiceRegistry serviceRegistry;
	Scriptable scope;

	/**
	 * @see org.alfresco.repo.jscript.Scopeable#setScope(org.mozilla.javascript.Scriptable)
	 */
	public void setScope(Scriptable scope)
	{
		this.scope = scope;
	}

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public void setCustomModelService(CustomModelService customModelService) {
		this.customModelService = customModelService;
	}

	/**
	 * activates a custom model by name
	 *
	 * @param model name of the model
	 */
	public void activateModel(String model){
		 customModelService.activateCustomModel(model);
	}

	public void deactivateModel(String model){
		 customModelService.deactivateCustomModel(model);
	}

	public void deleteModel(String model){
		customModelService.deleteCustomModel(model);
	}

	public ScriptNode getModelNode(String model){
		 NodeRef customModelRef = customModelService.getModelNodeRef(model);
		if(customModelRef!=null){
			return new ScriptNode(customModelRef, serviceRegistry);
		}else{
			return null;
		}
	}

	public Scriptable getCustomModels(int start, int end){
		List<CustomModelDefinition> customModels = customModelService.getCustomModels(new PagingRequest(start, end)).getPage();
		return Context.getCurrentContext().newArray(scope,customModels.toArray(new Object[customModels.size()]));
	}

}
