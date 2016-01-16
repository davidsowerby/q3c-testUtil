/*
 *
 *  * Copyright (c) 2016. David Sowerby
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 *  * the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 *  * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *  * specific language governing permissions and limitations under the License.
 *
 */
package uk.q3c.util.testutil;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Uses an in-memory appender to to capture log output during testing. Be sure to call {@link #close()} in your
 * teardown method, or it will eat up your memory.
 *
 * @author David Sowerby
 * @date 12 May 2014
 */
public class LogMonitor {
    private final MemoryAppender<ILoggingEvent> appender;
    private Logger log;

    public LogMonitor() {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        log = lc.getLogger(Logger.ROOT_LOGGER_NAME);
        appender = new MemoryAppender();
        appender.start();
        log.addAppender(appender);
    }

    public List<String> debugLogs() {
        return appender.logs.get(Level.DEBUG);
    }

    public List<String> traceLogs() {
        return appender.logs.get(Level.TRACE);
    }

    public void close() {
        appender.logs.clear();
        log.detachAppender(appender);
    }

    public void addClassFilter(Class<?> classToAccept) {
        ClassAcceptFilter filter = new ClassAcceptFilter(classToAccept);
        appender.addFilter(filter);

    }

    public int errorCount() {
        return errorLogs().size();
    }

    public List<String> errorLogs() {
        return appender.logs.get(Level.ERROR);
    }

    public int warnCount() {
        return warnLogs().size();
    }

    public List<String> warnLogs() {
        return appender.logs.get(Level.WARN);
    }

    public int infoCount() {
        return infoLogs().size();
    }

    public List<String> infoLogs() {
        return appender.logs.get(Level.INFO);
    }

    static class MemoryAppender<E extends ILoggingEvent> extends AppenderBase<E> {

        LinkedList<Filter> filters;
        Map<Level, List<String>> logs;

        protected MemoryAppender() {
            super();
            filters = new LinkedList<>();
            logs = new HashMap<>();
            logs.put(Level.TRACE, new LinkedList<String>());
            logs.put(Level.DEBUG, new LinkedList<String>());
            logs.put(Level.INFO, new LinkedList<String>());
            logs.put(Level.WARN, new LinkedList<String>());
            logs.put(Level.ERROR, new LinkedList<String>());
        }

        @Override
        public void addFilter(Filter newFilter) {
            filters.add(newFilter);
        }



        public void close() {
            logs.clear();
            filters.clear();
        }

        @Override
        public void append(E event) {
            List<String> list = logs.get(event.getLevel());
            if (acceptedByAFilter(event)) {
                list.add(event.getFormattedMessage());
            }
        }

        private boolean acceptedByAFilter(E event) {
            for (Filter filter : filters) {
                if (filter.decide(event) == FilterReply.ACCEPT) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getName() {
            return "In memory appender";
        }


        /**
         * Not used, getName() is fixed
         *
         * @param name
         */
        @Override
        public void setName(String name) {

        }



    }

}
