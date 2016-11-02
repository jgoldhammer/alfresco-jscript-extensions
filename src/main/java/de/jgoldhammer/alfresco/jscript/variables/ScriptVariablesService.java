package de.jgoldhammer.alfresco.jscript.variables;

import java.util.Properties;
import org.alfresco.repo.jscript.BaseScopableProcessorExtension;

/**
 * Expose global properties to js scripts
 *
 */
public class ScriptVariablesService extends BaseScopableProcessorExtension {

    private Properties properties;
    
    public void setProperties(final Properties globalProperties) {
        properties = globalProperties;
    }

    public Properties getProperties() {
        return properties;
    }

    public String get(String key) {
        return properties.getProperty(key);
    }
    
    public String get(String key,String otherwise) {
        return properties.getProperty(key,otherwise);
    }
}