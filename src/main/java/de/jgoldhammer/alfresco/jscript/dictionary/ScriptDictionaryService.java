/**
 *
 */
package de.jgoldhammer.alfresco.jscript.dictionary;

import com.google.common.base.Preconditions;
import org.alfresco.repo.processor.BaseProcessorExtension;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.dictionary.TypeDefinition;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.springframework.extensions.webscripts.annotation.ScriptClass;
import org.springframework.extensions.webscripts.annotation.ScriptClassType;
import org.springframework.extensions.webscripts.annotation.ScriptMethod;
import org.springframework.extensions.webscripts.annotation.ScriptMethodType;

import java.util.Collection;

/**
 * script object for handling the behaviourFilter
 *
 * @author Jens Goldhammer (fme AG)
 */

@ScriptClass(types=ScriptClassType.JavaScriptRootObject, code= "dictionary", help="the root object for the de.jgoldhammer.alfresco.jscript.dictionary service")
public class ScriptDictionaryService extends BaseProcessorExtension {
	private DictionaryService dictionaryService;

	private NamespaceService namespaceService;

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	public void setNamespaceService(NamespaceService namespaceService) {
		this.namespaceService = namespaceService;
	}


	@ScriptMethod(
			help = "",
			output = "void",
			code = "",
			type = ScriptMethodType.READ)
	public Collection<QName> getAllTypes() {
		return dictionaryService.getAllTypes();
	}

	@ScriptMethod(
			help = "checks if the given ofType is subtype of the type",
			output = "void",
			code = "",
			type = ScriptMethodType.READ)
	public boolean isSubType(String type, String ofType) {
		Preconditions.checkNotNull(type, "type cannot be null here");
		Preconditions.checkNotNull(ofType, "ofType cannot be null here");
		return dictionaryService.isSubClass(QName.resolveToQName(namespaceService, type),
				QName.resolveToQName(namespaceService, ofType));
	}

	@ScriptMethod(
			help = "get type definition by name",
			output = "void",
			code = "",
			type = ScriptMethodType.READ)
	public TypeDefinition getType(String type){
		return dictionaryService.getType(QName.resolveToQName(namespaceService, type));
	}

}