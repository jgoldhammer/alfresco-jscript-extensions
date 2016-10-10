package de.jgoldhammer.alfresco.jscript.quickshare;

import com.google.common.base.Preconditions;
import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.service.cmr.quickshare.QuickShareService;
import org.mozilla.javascript.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * utilizes the quickshareservice to share and unshare content.
 * it is possible to get the metadata of a shared document.
 */
public class ScriptQuickshareService extends BaseScopableProcessorExtension {

	QuickShareService quickShareService;

	public void setQuickShareService(QuickShareService quickShareService) {
		this.quickShareService = quickShareService;
	}

	/**
	 * share a content, so that others can
	 *
	 * @param node the document to share (no folders supported)
	 * @return the quick share id
	 */
	public String shareContent(ScriptNode node){
		Preconditions.checkNotNull(node);
		return quickShareService.shareContent(node.getNodeRef()).getId();
	}

	/**
	 * unshare a content, so that a previously shared content cannot be accessed anymore from anonymous users.
	 *
	 * @param shareId the shareid of the shared document
	 *
	 */
	public void unshareContent(String shareId){
		Preconditions.checkNotNull(shareId);
		quickShareService.unshareContent(shareId);
	}

	/**
	 * get metadata given by shareId
	 *
	 * @param shareId the shareid of the shared document
	 * @return a map of metadata of the shared content
	 */
	public Map<String, Object> getMetadata(String shareId){
		Preconditions.checkNotNull(shareId);
		return (Map<String,Object>) quickShareService.getMetaData(shareId).get("item");
	}

	/**
	 * get metadata given by node
	 *
	 * @param node (not necessary to be shared!)
	 * @return a map of metadata of the shared content or null
	 */
	public Map<String, Object> getMetadata(ScriptNode node){
		Preconditions.checkNotNull(node);
		return (Map<String,Object>) quickShareService.getMetaData(node.getNodeRef()).get("item");
	}
}
