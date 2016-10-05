/**
 *
 */
package de.jgoldhammer.alfresco.jscript.workflow;

import org.alfresco.repo.processor.BaseProcessorExtension;
import org.alfresco.repo.workflow.WorkflowInterpreter;
import org.springframework.extensions.webscripts.annotation.ScriptClass;
import org.springframework.extensions.webscripts.annotation.ScriptClassType;
import org.springframework.extensions.webscripts.annotation.ScriptMethod;
import org.springframework.extensions.webscripts.annotation.ScriptMethodType;

import java.io.IOException;

/**
 * script object for handling the workflowadmin console interpreter.
 *
 * @author Jens Goldhammer (fme AG)
 */

@ScriptClass(types=ScriptClassType.JavaScriptRootObject, code="workflowAdmin", help="the root object for the de.jgoldhammer.alfresco.jscript.workflow interpreter used in the workflowconsole. Allows to run commands.")
public class ScriptWorkflowConsole extends BaseProcessorExtension {
	
	WorkflowInterpreter workflowInterpreter;
	
	public void setWorkflowInterpreter(WorkflowInterpreter workflowInterpreter) {
		this.workflowInterpreter = workflowInterpreter;
	}

	@ScriptMethod(
    		help="using the workflowinterpreter to run commands similar to the de.jgoldhammer.alfresco.jscript.workflow console",
    		output="String",
    		code="workflowAdmin.exec('help')",
    		type=ScriptMethodType.WRITE)
    public String exec(String command) throws IOException{
		return workflowInterpreter.interpretCommand(command);
    }


}
