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

@ScriptClass(types=ScriptClassType.JavaScriptRootObject, code= "de/jgoldhammer/alfresco/jscript/policy", help="the root object for the de.jgoldhammer.alfresco.jscript.policy/behaviourFilter")
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
    		code="de.jgoldhammer.alfresco.jscript.policy.enableFor(node);",
    		type=ScriptMethodType.READ)
    public void enableForNode(ScriptNode node){
    	behaviourFilter.enableBehaviour(node.getNodeRef());
    }

    @ScriptMethod(
    		help="eanbles the behaviour for the given scriptnode",
    		output="void",
    		code="de.jgoldhammer.alfresco.jscript.policy.enableFor(node);",
    		type=ScriptMethodType.READ)
    public void enableForTypeOrAspect(String shortQName){
    	behaviourFilter.enableBehaviour(QName.resolveToQName(namespaceService, shortQName));

    }

    @ScriptMethod(
    		help="eanbles the behaviour for the given scriptnode",
    		output="void",
    		code="de.jgoldhammer.alfresco.jscript.policy.enableFor(node);",
    		type=ScriptMethodType.READ)

    public void disableForTypeOrAspect(String shortQName){
    	behaviourFilter.disableBehaviour(QName.resolveToQName(namespaceService, shortQName));
    }

	@ScriptMethod(
			help="eanbles all behaviours for the current transaction ",
			output="void",
			code="policy.enableAll;",
			type=ScriptMethodType.READ)

	public void enableAll(){
		behaviourFilter.enableBehaviour();
	}


    @ScriptMethod(
    		help="disables all behaviour for the given scriptnode",
    		output="void",
    		code="de.jgoldhammer.alfresco.jscript.policy.disableFor(node);",
    		type=ScriptMethodType.READ)
    public void disableForNode(ScriptNode node){
		behaviourFilter.disableBehaviour(node.getNodeRef());
	}
	@ScriptMethod(
			help="disables all behaviour for the given scriptnode",
			output="void",
			code="de.jgoldhammer.alfresco.jscript.policy.disableFor(node);",
			type=ScriptMethodType.READ)
	public void isAltered(){
		behaviourFilter.isActivated();
	}

}
