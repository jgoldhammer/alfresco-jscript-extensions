package de.jgoldhammer.alfresco.jscript.links;

import com.google.common.base.Preconditions;
import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.DeleteLinksStatusReport;
import org.alfresco.service.cmr.repository.DocumentLinkService;
import org.alfresco.service.cmr.repository.NodeRef;

/**
 * encapsulates the documentlinkservice which allows to create links for documents into other folders,
 * delete all links for a document and get the original document of a given link.
 */
public class ScriptLinkService extends BaseScopableProcessorExtension{

	DocumentLinkService documentLinkService;
	ServiceRegistry services;

	public void setDocumentLinkService(DocumentLinkService documentLinkService) {
		this.documentLinkService = documentLinkService;
	}

	/**
	 * Sets the service registry
	 *
	 * @param services  the service registry
	 */
	public void setServiceRegistry(ServiceRegistry services)
	{
		this.services = services;
	}

	/**
	 * creates a link from the source to the target
	 * @param source the document to link in another folder
	 * @param targetFolder folder the document should be linked to.
	 * @return the created link as script node
	 */
	public ScriptNode createLink(ScriptNode source, ScriptNode targetFolder){
		Preconditions.checkNotNull(source);
		Preconditions.checkNotNull(targetFolder);
		return new ScriptNode(documentLinkService.createDocumentLink(source.getNodeRef(),
				targetFolder.getNodeRef()), services);
	}

	/**
	 * delete all links of the given document.
	 *
	 * @param source - document or folder to delete all links for.
	 *               @return the deletelinkstatusreport object which holds information about the deletion
	 */
	public DeleteLinksStatusReport deleteLinks(ScriptNode source){
		Preconditions.checkNotNull(source);
		return documentLinkService.deleteLinksToDocument(source.getNodeRef());
	}

	/**
	 * get the original document for a linked node.
	 *
	 * @param link the document to get the source document for.
	 * @return the source document or null (if the given parameter is not a link)
	 */
	public ScriptNode getSource(ScriptNode link){
		Preconditions.checkNotNull(link);
		NodeRef sourceDocument = documentLinkService.getLinkDestination(link.getNodeRef());
		if(sourceDocument!=null){
			return new ScriptNode(sourceDocument, services);
		} else {
			return null;
		}
	}

}
