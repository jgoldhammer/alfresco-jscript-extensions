package de.jgoldhammer.alfresco.jscript.jobs;

import org.alfresco.repo.jscript.BaseScopableProcessorExtension;

/**
 * Created by jgoldhammer on 15.10.16.
 */
public class CronExpressions extends BaseScopableProcessorExtension {

	public String EVERY_TEN_SECONDS="0/10 * * * * *";
	public String EVERY_TWENTY_SECONDS="0/20 * * * * *";

	public String EVERY_MINUTE="0 0/1 * 1/1 * ? *";
	public String EVERY_TWO_MINUTES="0 0/2 * 1/1 * ? *";
	public String EVERY_FIVE_MINUTES="0 0/5 * 1/1 * ? *";

	public String EVERY_HOUR="0 0 0/1 1/1 * ? *";
	public String EVERY_TWO_HOURS="0 0 0/2 1/1 * ? *";
	public String EVERY_THREE_HOURS="0 0 0/3 1/1 * ? *";


}
