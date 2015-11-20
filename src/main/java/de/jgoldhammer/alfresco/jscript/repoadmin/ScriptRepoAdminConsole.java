/**
 *
 */
package de.jgoldhammer.alfresco.jscript.repoadmin;

import java.io.IOException;

import org.alfresco.repo.admin.RepoAdminInterpreter;
import org.alfresco.repo.processor.BaseProcessorExtension;
import org.springframework.extensions.webscripts.annotation.ScriptClass;
import org.springframework.extensions.webscripts.annotation.ScriptClassType;
import org.springframework.extensions.webscripts.annotation.ScriptMethod;
import org.springframework.extensions.webscripts.annotation.ScriptMethodType;

/**
 * script object for using the repoadmin interpreter
 *
 * @author Jens Goldhammer (fme AG)
 */

@ScriptClass(types=ScriptClassType.JavaScriptRootObject, code="repoAdmin", help="the root object for the repo admin interpreter used in the repoadmin console. Allows to run commands to deploy messages, models in the backend.")
public class ScriptRepoAdminConsole extends BaseProcessorExtension {
	
	RepoAdminInterpreter repoAdminInterpreter;

	public void setRepoAdminInterpreter(RepoAdminInterpreter repoAdminInterpreter) {
		this.repoAdminInterpreter = repoAdminInterpreter;
	}

	@ScriptMethod(
    		help="using the repoAdminInterpreter to run commands similar to the repo admin console",
    		output="String",
    		code="repoAdmin.exec('help')",
    		type=ScriptMethodType.WRITE)
    public String exec(String command) throws IOException{
		return repoAdminInterpreter.interpretCommand(command);
    }


}
