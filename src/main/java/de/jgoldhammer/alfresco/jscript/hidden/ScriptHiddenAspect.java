package de.jgoldhammer.alfresco.jscript.hidden;

import com.google.common.base.Preconditions;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.model.filefolder.HiddenAspect;

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


}
