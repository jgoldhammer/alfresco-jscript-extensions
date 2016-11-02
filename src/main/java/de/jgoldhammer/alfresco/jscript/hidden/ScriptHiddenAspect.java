package de.jgoldhammer.alfresco.jscript.hidden;

import com.google.common.base.Preconditions;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.model.filefolder.HiddenAspect;
import org.alfresco.repo.model.filefolder.HiddenFileInfo;
import org.alfresco.service.cmr.repository.NodeRef;

/**
 * wraps the api to hide nodes for certain clients or completely...
 */
public class ScriptHiddenAspect {

	HiddenAspect hiddenAspect;

	public void setHiddenAspect(HiddenAspect hiddenAspect) {
		this.hiddenAspect = hiddenAspect;
	}

	public void hideNodeExplicit(ScriptNode scriptNode){
		Preconditions.checkNotNull(scriptNode);
		hiddenAspect.hideNodeExplicit(scriptNode.getNodeRef());
	}

	public void unhideNode(ScriptNode scriptNode){
		Preconditions.checkNotNull(scriptNode);
		hiddenAspect.unhideExplicit(scriptNode.getNodeRef());
	}

	public boolean hasHiddenAspect(ScriptNode scriptNode){
		Preconditions.checkNotNull(scriptNode);
		return hiddenAspect.hasHiddenAspect(scriptNode.getNodeRef());
	}

	public void removeHiddenAspect(ScriptNode scriptNode){
		Preconditions.checkNotNull(scriptNode);
		hiddenAspect.removeHiddenAspect(scriptNode.getNodeRef());
	}

	public HiddenFileInfo onHiddenPath(ScriptNode scriptNode){
		Preconditions.checkNotNull(scriptNode);
		return hiddenAspect.onHiddenPath(scriptNode.getNodeRef());
	}





}
