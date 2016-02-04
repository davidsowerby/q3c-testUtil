package uk.q3c.util.testutil

import spock.lang.Specification

/**
 * Created by David Sowerby on 04 Feb 2016
 */
class FileTestUtilTest extends Specification {

    def "compare equal files with no lines ignored"() {
        given:
        File ref = TestResource.resource('ref.txt')
        File refEqual = TestResource.resource('refEqual.txt')

        when:
        Optional<String> result = FileTestUtil.compare(ref, refEqual)

        then:
        !result.isPresent()
    }

    def "compare files with 1 line different"() {
        given:
        File ref = TestResource.resource('ref.txt')
        File oneLineDifferent = TestResource.resource('oneLineDifferent.txt')

        when:
        Optional<String> result = FileTestUtil.compare(ref, oneLineDifferent)

        then:
        result.isPresent()
    }

    def "compare files with 1 line different, but that line ignored"() {
        given:
        File ref = TestResource.resource('ref.txt')
        File oneLineDifferent = TestResource.resource('oneLineDifferent.txt')

        when:
        Optional<String> result = FileTestUtil.compare(ref, oneLineDifferent, 3)

        then:
        !result.isPresent()
    }

    def "compare first, limited to before different line"() {
        given:
        File ref = TestResource.resource('ref.txt')
        File oneLineDifferent = TestResource.resource('oneLineDifferent.txt')

        when:
        Optional<String> result = FileTestUtil.compareFirst(3, ref, oneLineDifferent, 1)

        then:
        !result.isPresent()
    }

    def "compare first, different line ignored"() {
        given:
        File ref = TestResource.resource('ref.txt')
        File oneLineDifferent = TestResource.resource('oneLineDifferent.txt')

        when:
        Optional<String> result = FileTestUtil.compareFirst(4, ref, oneLineDifferent, 3)

        then:
        !result.isPresent()
    }

    def "compare first, different line not ignored"() {
        given:
        File ref = TestResource.resource('ref.txt')
        File oneLineDifferent = TestResource.resource('oneLineDifferent.txt')

        when:
        Optional<String> result = FileTestUtil.compareFirst(4, ref, oneLineDifferent)

        then:
        result.isPresent()
    }

    def "compare is successful for available lines, but one file longer"() {
        given:
        File ref = TestResource.resource('ref.txt')
        File oneLineLonger = TestResource.resource('oneLineLonger.txt')

        when:
        Optional<String> result = FileTestUtil.compare(ref, oneLineLonger)

        then:
        result.isPresent()
    }
}
