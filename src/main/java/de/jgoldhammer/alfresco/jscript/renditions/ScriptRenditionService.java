package de.jgoldhammer.alfresco.jscript.renditions;

import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.repo.jscript.Scopeable;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.rendition.RenditionService;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.springframework.extensions.webscripts.annotation.ScriptClass;
import org.springframework.extensions.webscripts.annotation.ScriptClassType;

import java.util.List;

/**
 * wraps some methods of the renditionservice.
 *
 */
@ScriptClass(types= ScriptClassType.JavaScriptRootObject,
		code="renditionService",
		help="the root object renditionService")

public class ScriptRenditionService  extends BaseScopableProcessorExtension {

	RenditionService renditionService;

	ServiceRegistry serviceRegistry;

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public void setRenditionService(RenditionService renditionService) {
		this.renditionService = renditionService;
	}

	/**
	 * checks if the given node is a renditions
	 * @param node the node to check
	 * @return true if the node is a renditions, false if not...
	 */
	public boolean isRendition(ScriptNode node){
		return renditionService.isRendition(node.getNodeRef());
	}

	/**
	 * returns the source node if the renditions
	 * @param rendition the renditions of a document
	 * @return the source node or null...
	 */
	public ScriptNode getSourceNode(ScriptNode rendition){
		return new ScriptNode(renditionService.getSourceNode(rendition.getNodeRef()).getParentRef(), serviceRegistry, getScope());
	}




}
