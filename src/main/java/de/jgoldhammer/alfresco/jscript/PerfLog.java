package de.jgoldhammer.alfresco.jscript;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.MessageFormat;

/**
 * class for performance monitoring. create your instance via constructor, start
 * the monitoring via {@link #start()} or {@link #start(String, Object...)} and
 * stop the monitoring with {@link #stop(String, Object...)} or
 * {@link #stop(int, String, Object...)}.
 * 
 * @author jgoldhammer
 */
public class PerfLog {

	private static final int DEFAULT_ASSERT_PERFORMANCE = 2000;
	private Log LOG = LogFactory.getLog(PerfLog.class);
	private long startTime;

	/**
	 * @param logger
	 *            the logger to log the performance
	 */
	public PerfLog(Log logger) {
		if (logger != null) {
			LOG = logger;
		}
	}

	/**
	 * simple timelogger. If you want to log in your own logger instance, use
	 * {@link #PerfLog(Log)}.
	 */
	public PerfLog() {
	}

	/**
	 * start the logging
	 * 
	 * @param message the message or message template to log as start message (optional)
	 * @param params the params of the message
	 * @return a new perflog instance.
	 *
	 */
	public PerfLog start(String message, Object... params) {
		if (LOG.isInfoEnabled() || LOG.isWarnEnabled()) {
			startTime = System.currentTimeMillis();
			if (StringUtils.isNotEmpty(message)) {
				LOG.info(MessageFormat.format(message, params));
			}
		}
		return this;
	}

	/**
	 * start the logging
	 * @return a new perflog instance.
	 *
	 */
	public PerfLog start() {
		return start(null, (Object) null);
	}

	/**
	 * @param assertPerformanceOf if the duration of the function is over this limit, the log message will be logged as warning!
	 * @param message message or message template to log as stop message
	 * @param params params for the message template
	 * @return the performance in milliseconds
	 *
	 */
	public long stop(int assertPerformanceOf, String message, Object... params) {
		long endTime = System.currentTimeMillis();
		long neededTime = endTime - startTime;
		if (LOG.isInfoEnabled() || LOG.isWarnEnabled()) {
			if (neededTime < assertPerformanceOf) {
				LOG.info("(OK) " + neededTime + " ms:" + MessageFormat.format(message, params));
			} else {
				LOG.warn("(WARNING) " + neededTime + " ms: " + MessageFormat.format(message, params));
			}

		}
		return neededTime;

	}

	/**
	 * ends the performance monitoring. Logs a warning if the operation is
	 * finished after {@value #DEFAULT_ASSERT_PERFORMANCE} milliseconds. To
	 * specify your own time limit, use {@link #stop(int, String, Object...)}.
	 * 
	 * @param message
	 *            the message to log in the log statement.
	 * @param params
	 *       params for the message template
	 * @return the performance in milliseconds
	 */
	public long stop(String message, Object... params) {
		return stop(DEFAULT_ASSERT_PERFORMANCE, message, params);
	}
}
