package uk.q3c.util.testutil

import ch.qos.logback.classic.spi.LoggingEvent
import ch.qos.logback.core.spi.FilterReply
import spock.lang.Specification

/**
 * Created by David Sowerby on 04 Feb 2016
 */
class ClassAcceptFilterTest extends Specification {

    def "accept"() {
        given:
        LoggingEvent event = Mock(LoggingEvent)
        event.getLoggerName() >> LogMonitor.getName()
        LoggingEvent event1 = Mock(LoggingEvent)
        event1.getLoggerName() >> TestResource.getName()
        ClassAcceptFilter filter = new ClassAcceptFilter(LogMonitor)

        expect:
        filter.decide(event).equals(FilterReply.ACCEPT)
        filter.decide(event1).equals(FilterReply.NEUTRAL)
    }
}
