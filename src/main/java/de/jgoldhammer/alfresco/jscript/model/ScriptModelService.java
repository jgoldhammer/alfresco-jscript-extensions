package de.jgoldhammer.alfresco.jscript.model;

import org.alfresco.query.PagingRequest;
import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.processor.BaseProcessorExtension;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
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
public class ScriptModelService extends BaseScopableProcessorExtension {

	CustomModelService customModelService;
	ServiceRegistry serviceRegistry;

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public void setCustomModelService(CustomModelService customModelService) {
		this.customModelService = customModelService;
	}

	/**
	 * checks whether the current user is a model admin...
	 * @return true if the user is a model admin or super admin, false if not
	 */
	public boolean isModelAdmin(){
		return customModelService.isModelAdmin(AuthenticationUtil.getRunAsUser());
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
		return Context.getCurrentContext().newArray(getScope(),customModels.toArray(new Object[customModels.size()]));
	}


}
