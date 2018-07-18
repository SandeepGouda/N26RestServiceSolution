package com.log;

import java.util.Arrays;
import org.apache.log4j.MDC;

/**
 * Log handler.
 */
public class Log {
	
	 private static final String PROJECT = "N26RestfulSolution";
	
	/**
     * Logger.
     */
    private org.apache.log4j.Logger logger;

    public Log(Class clazz) {
        logger = org.apache.log4j.Logger.getLogger(clazz);
    }
    
    /*************************************************************
     * Log without LogCode.
     *************************************************************/

    public void error(Object... messages) {
        logger.error(logMessage(messages));
    }

    public void warn(Object... messages) {
        logger.warn(logMessage(messages));
    }

    public void info(Object... messages) {
        if (logger.isInfoEnabled()) {
            logger.info(logMessage(messages));
        }
    }

    public void debug(Object... messages) {
        if (logger.isDebugEnabled()) {
            logger.debug(logMessage(messages));
        }
    }

    public void trace(Object... messages) {
        if (logger.isTraceEnabled()) {
            logger.trace(logMessage(messages));
        }
    }
    
    private String logMessage(Object... messages) {
        return PROJECT + ": " + Arrays.toString(messages);
    }


}
