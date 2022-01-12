package com.test.project;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;

public class ShutdownHookConfiguration {

    public void destroy() {
      LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
      loggerContext.stop();
    }
}
