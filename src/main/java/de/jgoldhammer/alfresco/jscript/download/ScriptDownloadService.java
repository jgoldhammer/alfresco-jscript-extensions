package de.jgoldhammer.alfresco.jscript.download;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Service;
import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.download.DownloadService;
import org.alfresco.service.cmr.download.DownloadStatus;
import org.alfresco.service.cmr.repository.NodeRef;
import org.jbpm.graph.action.Script;

import java.util.Arrays;

/**
 * ScriptDownloadService wraps the downloadservice.
 *
 */
public class ScriptDownloadService extends BaseScopableProcessorExtension {

	DownloadService downloadService;
	ServiceRegistry serviceRegistry;

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public void setDownloadService(DownloadService downloadService) {
		this.downloadService = downloadService;
	}

	public ScriptNode createDownloadByNodeRefs(String[] nodeRefs, boolean recursive){
		Preconditions.checkNotNull(nodeRefs);

		NodeRef[] nodeRefsParam = new NodeRef[nodeRefs.length];
		for (int i=0;i<nodeRefs.length;i++){
			nodeRefsParam[i] = new NodeRef(nodeRefs[i]);
		}

		return new ScriptNode(downloadService.createDownload(nodeRefsParam, recursive), serviceRegistry);
	}

	public ScriptNode createDownloadByNodeRef(String nodeRef, boolean recursive){
		Preconditions.checkNotNull(nodeRef);
		NodeRef[] nodeRefsParam = {new NodeRef(nodeRef)};

		return new ScriptNode(downloadService.createDownload(nodeRefsParam, recursive), serviceRegistry);
	}

	public ScriptNode createDownload(ScriptNode[] nodes, boolean recursive){
		Preconditions.checkNotNull(nodes);

		NodeRef[] nodeRefsParam = new NodeRef[nodes.length];
		for (int i=0;i<nodes.length;i++){
			nodeRefsParam[i] = nodes[i].getNodeRef();
		}

		return new ScriptNode(downloadService.createDownload(nodeRefsParam, recursive), serviceRegistry);
	}

	public ScriptNode createDownload(ScriptNode node, boolean recursive){
		Preconditions.checkNotNull(node);
		NodeRef[] nodeRefsParam = {node.getNodeRef()};
		return new ScriptNode(downloadService.createDownload(nodeRefsParam, recursive), serviceRegistry);
	}

	public void cancelDownload(ScriptNode downloadRequest){
		Preconditions.checkNotNull(downloadRequest,"downloadRequest cannot be null here");
		downloadService.cancelDownload(downloadRequest.getNodeRef());
	}

	public void cancelDownload(String downloadRequestNodeRef){
		Preconditions.checkNotNull(downloadRequestNodeRef,"downloadRequest cannot be null here");
		downloadService.cancelDownload(new NodeRef(downloadRequestNodeRef));
	}

	public DownloadStatus getDownloadStatus(ScriptNode download){
		return downloadService.getDownloadStatus(download.getNodeRef());
	}

	public DownloadStatus getDownloadStatus(String downloadNodeRef){
		return downloadService.getDownloadStatus(new NodeRef(downloadNodeRef));
	}
}
