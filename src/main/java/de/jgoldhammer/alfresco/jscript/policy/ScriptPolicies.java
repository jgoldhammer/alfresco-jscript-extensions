/**
 *
 */
package de.jgoldhammer.alfresco.jscript.policy;

import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.policy.BehaviourFilter;
import org.alfresco.repo.processor.BaseProcessorExtension;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.springframework.extensions.webscripts.annotation.ScriptClass;
import org.springframework.extensions.webscripts.annotation.ScriptClassType;
import org.springframework.extensions.webscripts.annotation.ScriptMethod;
import org.springframework.extensions.webscripts.annotation.ScriptMethodType;

/**
 * script object for handling the behaviourFilter
 *
 * @author Jens Goldhammer (fme AG)
 */

@ScriptClass(types=ScriptClassType.JavaScriptRootObject, code="policy", help="the root object for the policy/behaviourFilter")
public class ScriptPolicies extends BaseProcessorExtension {
    private BehaviourFilter behaviourFilter;
    private NamespaceService namespaceService;

    public void setBehaviourFilter(BehaviourFilter behaviourFilter) {
		this.behaviourFilter = behaviourFilter;
	}

    public void setNamespaceService(NamespaceService namespaceService) {
		this.namespaceService = namespaceService;
	}

    @ScriptMethod(
    		help="eanbles the behaviour for the given scriptnode",
    		output="void",
    		code="policy.enableFor(node);",
    		type=ScriptMethodType.READ)
    public void enableForNode(ScriptNode node){
    	behaviourFilter.enableBehaviour(node.getNodeRef());
    }

    @ScriptMethod(
    		help="eanbles the behaviour for the given scriptnode",
    		output="void",
    		code="policy.enableFor(node);",
    		type=ScriptMethodType.READ)
    public void enableForTypeOrAspect(String shortQName){
    	behaviourFilter.enableBehaviour(QName.resolveToQName(namespaceService, shortQName));
    }

    @ScriptMethod(
    		help="eanbles the behaviour for the given scriptnode",
    		output="void",
    		code="policy.enableFor(node);",
    		type=ScriptMethodType.READ)

    public void diasbleForTypeOrAspect(String shortQName){
    	behaviourFilter.disableBehaviour(QName.resolveToQName(namespaceService, shortQName));
    }


    @ScriptMethod(
    		help="disables the behaviour for the given scriptnode",
    		output="void",
    		code="policy.disableFor(node);",
    		type=ScriptMethodType.READ)
    public void disableForNode(ScriptNode node){
    	behaviourFilter.disableBehaviour(node.getNodeRef());
    }

}
