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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;


public class LogMonitorTest {

    private static Logger log = LoggerFactory.getLogger(LogMonitorTest.class);

    LogMonitor logMonitor;

    @Before
    public void setup() {
        logMonitor = new LogMonitor();
    }

    @After
    public void teardown() {
        logMonitor.close();
    }


    @Test
    public void error() {
        //given
        logMonitor.addClassFilter(this.getClass());
        //when
        log.error("error");
        //then
        assertThat(logMonitor.errorCount()).isEqualTo(1);
        assertThat(logMonitor.warnCount()).isEqualTo(0);
        assertThat(logMonitor.getLog()
                             .getAppender("In memory appender")).isNotNull();
    }

    @Test
    public void warn() {
        //given
        logMonitor.addClassFilter(this.getClass());
        //when
        log.warn("warn");
        //then
        assertThat(logMonitor.warnCount()).isEqualTo(1);
    }

    @Test
    public void info() {
        //given
        logMonitor.addClassFilter(this.getClass());
        //when
        log.info("info");
        //then
        assertThat(logMonitor.infoCount()).isEqualTo(1);
    }

    @Test
    public void debug() {
        //given
        logMonitor.addClassFilter(this.getClass());
        //when
        log.debug("debug");
        //then
        assertThat(logMonitor.debugCount()).isEqualTo(1);
    }

    @Test
    public void trace() {
        //given
        logMonitor.addClassFilter(this.getClass());
        //when
        log.trace("trace");
        //then
        assertThat(logMonitor.traceCount()).isEqualTo(1);
    }

    @Test
    public void close() throws Exception {
        // given
        logMonitor.addClassFilter(this.getClass());
        log.debug("debug");
        //when
        logMonitor.close();
        //then
        assertThat(logMonitor.debugCount()).isEqualTo(0);
        assertThat(logMonitor.infoCount()).isEqualTo(0);
        assertThat(logMonitor.warnCount()).isEqualTo(0);
        assertThat(logMonitor.errorCount()).isEqualTo(0);
        assertThat(logMonitor.getClassFilters()).isEmpty();
        assertThat(logMonitor.getLog()
                             .getAppender("In memory appender")).isNull();

    }
}