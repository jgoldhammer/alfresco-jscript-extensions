/**
 *
 */
package de.jgoldhammer.alfresco.jscript.favorites;

import com.google.common.base.Preconditions;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.processor.BaseProcessorExtension;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.favourites.FavouritesService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.jbpm.graph.action.Script;
import org.springframework.extensions.webscripts.annotation.ScriptClass;
import org.springframework.extensions.webscripts.annotation.ScriptClassType;
import org.springframework.extensions.webscripts.annotation.ScriptMethod;
import org.springframework.extensions.webscripts.annotation.ScriptMethodType;

import java.util.Collection;

/**
 * script object for handling the FavouritesService.
 * Currently, it allows to add a node (folder, document and) and remove a favorite from a use
 *
 * @author Jens Goldhammer (fme AG)
 */

@ScriptClass(types=ScriptClassType.JavaScriptRootObject, code= "favorites", help="the root object for the favorites service")
public class ScriptFavoritesService extends BaseProcessorExtension {
	private FavouritesService favouritesService;
	private ServiceRegistry serviceRegistry;

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public void setFavouritesService(FavouritesService favouritesService) {
		this.favouritesService = favouritesService;
	}

	@ScriptMethod(
			help = "adds the given script node as favorite for the current authenticated user and returns the favorite as scriptnode",
			output = "ScriptNode",
			code = "favorites.add(<Scriptnode>)",
			type = ScriptMethodType.WRITE)
	public ScriptNode add(ScriptNode node) {

		Preconditions.checkNotNull(node,"Node parameter must be given");
		ScriptNode result=null;
		String username = AuthenticationUtil.getFullyAuthenticatedUser();

		if(!favouritesService.isFavourite(username, node.getNodeRef())) {
			NodeRef nodeRef = favouritesService.addFavourite(username, node.getNodeRef()).getNodeRef();
			result = new ScriptNode(nodeRef, serviceRegistry);
		}

		return result;
	}

	@ScriptMethod(
			help = "adds the given script node as favorite for the given user and returns the favorite as scriptnode",
			output = "ScriptNode",
			code = "favorites.add(<Scriptnode>)",
			type = ScriptMethodType.WRITE)
	public ScriptNode add(ScriptNode node, final String username) {

		Preconditions.checkNotNull(node,"Node parameter must be given");
		Preconditions.checkNotNull(username,"username must be given");

		ScriptNode result = AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<ScriptNode>() {
			@Override
			public ScriptNode doWork() throws Exception {
				ScriptNode result=null;
				if(!favouritesService.isFavourite(username, node.getNodeRef())) {
					NodeRef nodeRef = favouritesService.addFavourite(username, node.getNodeRef()).getNodeRef();
					result = new ScriptNode(nodeRef, serviceRegistry);
				}
				return result;
			}
		}, username);

		return result;
	}

	@ScriptMethod(
			help = "removes the given script node as favorite for the current authenticated user",
			output = "void",
			code = "",
			type = ScriptMethodType.WRITE)
	public void remove(ScriptNode node){
		Preconditions.checkNotNull(node,"Node parameter must be given");
		String username = AuthenticationUtil.getFullyAuthenticatedUser();
		// currently the return value of removeFavourite is always false- so we do not offer a return value here...
		if(favouritesService.isFavourite(username, node.getNodeRef())){
			favouritesService.removeFavourite(username, node.getNodeRef());
		}
	}

	@ScriptMethod(
			help = "checks if the given script node is a favorite for the current authenticated user",
			output = "void",
			code = "",
			type = ScriptMethodType.READ)
	public boolean isFavorite(ScriptNode node){
		Preconditions.checkNotNull(node,"Node parameter must be given");
		return favouritesService.isFavourite(AuthenticationUtil.getFullyAuthenticatedUser(), node.getNodeRef());
	}




}