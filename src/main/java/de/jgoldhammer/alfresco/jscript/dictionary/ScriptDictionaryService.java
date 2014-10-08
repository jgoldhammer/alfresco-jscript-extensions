/**
 *
 */
package de.jgoldhammer.alfresco.jscript.dictionary;

import org.alfresco.repo.processor.BaseProcessorExtension;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.springframework.extensions.webscripts.annotation.ScriptClass;
import org.springframework.extensions.webscripts.annotation.ScriptClassType;

/**
 * script object for handling the behaviourFilter
 *
 * @author Jens Goldhammer (fme AG)
 */

@ScriptClass(types=ScriptClassType.JavaScriptRootObject, code="dictionary", help="the root object for the dictionary service")
public class ScriptDictionaryService extends BaseProcessorExtension {
    private DictionaryService dictionaryService;

    public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

//    @ScriptMethod(
//    		help="eanbles the behaviour for the given scriptnode",
//    		output="void",
//    		code="policy.enableFor(node);",
//    		type=ScriptMethodType.READ)
//    public void enableForNode(ScriptNode node){
//    	dictionaryService.getTy
//    }
//
//    @ScriptMethod(
//    		help="eanbles the behaviour for the given scriptnode",
//    		output="void",
//    		code="policy.enableFor(node);",
//    		type=ScriptMethodType.READ)
//    public void enableForTypeOrAspect(String shortQName){
//    	behaviourFilter.enableBehaviour(QName.resolveToQName(namespaceService, shortQName));
//    }
//
//    @ScriptMethod(
//    		help="eanbles the behaviour for the given scriptnode",
//    		output="void",
//    		code="policy.enableFor(node);",
//    		type=ScriptMethodType.READ)
//
//    public void diasbleForTypeOrAspect(String shortQName){
//    	behaviourFilter.disableBehaviour(QName.resolveToQName(namespaceService, shortQName));
//    }
//
//
//    @ScriptMethod(
//    		help="disables the behaviour for the given scriptnode",
//    		output="void",
//    		code="policy.disableFor(node);",
//    		type=ScriptMethodType.READ)
//    public void disableForNode(ScriptNode node){
//    	behaviourFilter.disableBehaviour(node.getNodeRef());
//    }

}
