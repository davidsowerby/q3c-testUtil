/*
 * Copyright (c) 2015. David Sowerby
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package uk.q3c.util.testutil;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

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

    private static Logger log = Logger.getRootLogger();
    private final MemoryAppender appender;

    public LogMonitor() {
        appender = new MemoryAppender();
        log.addAppender(appender);
    }

    public List<String> debugLogs() {
        return appender.logs.get(Level.DEBUG);
    }

    public List<String> traceLogs() {
        return appender.logs.get(Level.TRACE);
    }

    public void close() {
        log.removeAppender(appender);
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

    class MemoryAppender implements Appender {

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

        @Override
        public Filter getFilter() {
            throw new RuntimeException("not yet implemented");
        }

        @Override
        public void clearFilters() {
            filters.clear();
        }

        @Override
        public void close() {
            logs.clear();
            filters.clear();
        }

        @Override
        public void doAppend(LoggingEvent event) {
            List<String> list = logs.get(event.getLevel());
            if (acceptedByAFilter(event)) {
                list.add(event.getRenderedMessage());
            }
        }

        private boolean acceptedByAFilter(LoggingEvent event) {
            for (Filter filter : filters) {
                if (filter.decide(event) == Filter.ACCEPT) {
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

        @Override
        public ErrorHandler getErrorHandler() {
            throw new RuntimeException("not yet implemented");
        }

        @Override
        public void setErrorHandler(ErrorHandler errorHandler) {
            throw new RuntimeException("not yet implemented");
        }

        @Override
        public Layout getLayout() {
            throw new RuntimeException("not yet implemented");
        }

        @Override
        public void setLayout(Layout layout) {
            throw new RuntimeException("not yet implemented");
        }



        @Override
        public boolean requiresLayout() {
            return false;
        }

    }

}
