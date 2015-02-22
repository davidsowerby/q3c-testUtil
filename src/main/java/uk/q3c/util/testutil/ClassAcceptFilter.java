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

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Created by David Sowerby on 22/02/15.
 */
public class ClassAcceptFilter extends Filter {

    private String acceptClassName;

    public ClassAcceptFilter(Class<?> classToMonitor) {
        acceptClassName = classToMonitor.getCanonicalName();

    }

    /**
     * <p>If the decision is <code>DENY</code>, then the event will be
     * dropped. If the decision is <code>NEUTRAL</code>, then the next
     * filter, if any, will be invoked. If the decision is ACCEPT then
     * the event will be logged without consulting with other filters in
     * the chain.
     *
     * @param event
     *         The LoggingEvent to decide upon.
     *
     * @return decision The decision of the filter.
     */
    @Override
    public int decide(LoggingEvent event) {
        if (event.getLogger()
                 .getName()
                 .equals(acceptClassName)) {
            return Filter.ACCEPT;
        } else {
            return Filter.NEUTRAL;
        }
    }
}
