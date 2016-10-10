package de.jgoldhammer.alfresco.jscript.dictionary;

import de.jgoldhammer.alfresco.jscript.BaseAlfrescoTest;
import org.alfresco.service.namespace.QName;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * testing the ScriptDictionaryService.
 */
public class ScriptDictionaryServiceTest extends BaseAlfrescoTest{


	@Autowired
	ScriptDictionaryService scriptDictionaryService;

	@Test
	public void getAllTypes() throws Exception {
		Collection<QName> allTypes = scriptDictionaryService.getAllTypes();
	}

	@Test
	public void isSubType() throws Exception {
		Assert.assertTrue(scriptDictionaryService.isSubType("cm:thumbnail","cm:content"));
		Assert.assertFalse(scriptDictionaryService.isSubType("cm:object", "cm:content"));

	}

}