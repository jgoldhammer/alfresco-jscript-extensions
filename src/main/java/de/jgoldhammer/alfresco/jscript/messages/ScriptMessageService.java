package de.jgoldhammer.alfresco.jscript.messages;

import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;
import org.alfresco.repo.i18n.MessageService;
import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.repo.jscript.ValueConverter;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/**
 *
 *
 * copied from https://github.com/bluedolmen/App-blue-courrier/blob/ba0fa1e8119419fdb3bb3ccf844e60ad9df9a736/alfresco-extensions/src/main/java/org/bluedolmen/repo/jscript/MessageScript.java
 */
public class ScriptMessageService extends BaseScopableProcessorExtension {
	
	private final ValueConverter valueConverter = new ValueConverter();

	public String get(String messageKey) {
		return getMessage(messageKey);
	}
	
	public String getMessage(String messageKey) {
		return messageService.getMessage(messageKey);
	}
	
	public String get(String messageKey, Scriptable params) {
		return getMessage(messageKey, params);
	}
	
	public String getMessage(String messageKey, Scriptable params) {
		
		final Object convertedValue = valueConverter.convertValueForJava(params);
		Preconditions.checkArgument(convertedValue instanceof List<?>);

		final List<?> paramsList = (List<?>) convertedValue;
		final Object[] paramsArray = paramsList.toArray();
		return messageService.getMessage(messageKey, paramsArray);
		
	}

	public Scriptable getRegisteredBundles(){
		Set<String> registeredBundles = messageService.getRegisteredBundles();
		return Context.getCurrentContext().newArray(getScope(),registeredBundles.toArray(new Object[registeredBundles.size()]));

	}
	
	// IoC
	
	private MessageService messageService;
	
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	
}