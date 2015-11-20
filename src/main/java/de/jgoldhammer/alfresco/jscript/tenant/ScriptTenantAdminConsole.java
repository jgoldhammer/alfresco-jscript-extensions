/**
 *
 */
package de.jgoldhammer.alfresco.jscript.tenant;

import java.io.IOException;

import org.alfresco.repo.processor.BaseProcessorExtension;
import org.alfresco.repo.tenant.TenantInterpreter;
import org.springframework.extensions.webscripts.annotation.ScriptClass;
import org.springframework.extensions.webscripts.annotation.ScriptClassType;
import org.springframework.extensions.webscripts.annotation.ScriptMethod;
import org.springframework.extensions.webscripts.annotation.ScriptMethodType;

/**
 * script object for using the tenant interpreter
 *
 * @author Jens Goldhammer (fme AG)
 */

@ScriptClass(types=ScriptClassType.JavaScriptRootObject, code="tenantAdmin", help="the root object for the tenant interpreter used in the tenantadmin console. Allows to run commands to show tenants, create new tenants ")
public class ScriptTenantAdminConsole extends BaseProcessorExtension {
	
	TenantInterpreter tenantInterpreter;

	public void setTenantInterpreter(TenantInterpreter tenantInterpreter) {
		this.tenantInterpreter = tenantInterpreter;
	}

	@ScriptMethod(
    		help="using the tenantinterpreter to run commands similar to the tenant console",
    		output="String",
    		code="tenants.exec('help')",
    		type=ScriptMethodType.WRITE)
    public String exec(String command) throws IOException{
		return tenantInterpreter.interpretCommand(command);
    }


}
