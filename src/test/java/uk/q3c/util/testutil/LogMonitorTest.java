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

import com.google.inject.Inject;
import com.mycila.testing.junit.MycilaJunitRunner;
import com.mycila.testing.plugin.guice.GuiceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MycilaJunitRunner.class)
@GuiceContext({})
public class LogMonitorTest {

    private static Logger log = LoggerFactory.getLogger(LogMonitorTest.class);

    @Inject
    LogMonitor logMonitor;


    @Test
    public void error() {
        //given
        logMonitor.addClassFilter(this.getClass());
        //when
        log.error("error");
        //then
        assertThat(logMonitor.errorCount()).isEqualTo(1);
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
}