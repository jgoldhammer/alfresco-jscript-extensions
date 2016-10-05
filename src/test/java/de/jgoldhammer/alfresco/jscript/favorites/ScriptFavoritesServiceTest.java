package de.jgoldhammer.alfresco.jscript.favorites;

import de.jgoldhammer.alfresco.jscript.BaseAlfrescoTest;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.favourites.FavouritesService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ScriptFavoritesService
 */
public class ScriptFavoritesServiceTest extends BaseAlfrescoTest {

	@Autowired
	ScriptFavoritesService scriptFavoritesService;

	@Autowired
	FavouritesService favouritesService;

	NodeRef testNode;

	@Before
	public void init() {
		AuthenticationUtil.setRunAsUserSystem();
		testNode= generateTempDocument();

	}

	@Test
	public void add() throws Exception {

//		transactionHelper.doInTransaction(() -> {
//			Assert.assertFalse(favouritesService.isFavourite(AuthenticationUtil.getSystemUserName(), testNode));
//			scriptFavoritesService.add(new ScriptNode(testNode, getServiceRegistry()));
//			Assert.assertTrue(favouritesService.isFavourite(AuthenticationUtil.getSystemUserName(),testNode));
//			return null;
//		});

	}

	@Test
	public void remove() throws Exception {
//		transactionHelper.doInTransaction(() -> {
//			scriptFavoritesService.add(new ScriptNode(testNode, getServiceRegistry()));
//			Assert.assertTrue(favouritesService.isFavourite(AuthenticationUtil.getSystemUserName(), testNode));
//			scriptFavoritesService.remove(new ScriptNode(testNode, getServiceRegistry()));
//			Assert.assertFalse(favouritesService.isFavourite(AuthenticationUtil.getSystemUserName(), testNode));
//			return null;
//
//		});

	}


}