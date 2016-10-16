package de.jgoldhammer.alfresco.jscript.repository;

import com.google.common.util.concurrent.Service;
import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.model.Repository;
import org.alfresco.repo.site.DocLibNodeLocator;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.cmr.site.SiteService;
import org.jbpm.graph.action.Script;

/**
 * wraps the repository bean to have quick methods to access certain nodes in the repository.
 */
public class ScriptRepository extends BaseScopableProcessorExtension{

	Repository repository;
	ServiceRegistry serviceRegistry;
	SiteService siteService;
	DocLibNodeLocator docLibNodeLocator;
	PersonService personService;

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	public void setDocLibNodeLocator(DocLibNodeLocator docLibNodeLocator) {
		this.docLibNodeLocator = docLibNodeLocator;
	}

	public ScriptNode getCompanyHome(){
		return new ScriptNode(repository.getCompanyHome(),serviceRegistry, getScope());
	}

	public ScriptNode getRootHome(){
		return new ScriptNode(repository.getRootHome(), serviceRegistry, getScope());
	}

	public ScriptNode getUserHome(){
		return new ScriptNode(repository.getUserHome(repository.getPerson()), serviceRegistry,getScope());
	}

	public ScriptNode getPerson(){
		return new ScriptNode(repository.getPerson(), serviceRegistry,getScope());
	}

	public ScriptNode getPeopleContainer(){
		return new ScriptNode(personService.getPeopleContainer(), serviceRegistry, getScope());
	}

	public ScriptNode getSitesRoot(){
		return new ScriptNode(siteService.getSiteRoot(), serviceRegistry,getScope());
	}

	public ScriptNode getForDocLibForNode(ScriptNode source){
		return new ScriptNode(docLibNodeLocator.getNode(source.getNodeRef(),null), serviceRegistry,getScope());
	}


}
