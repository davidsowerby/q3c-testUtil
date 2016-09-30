package uk.q3c.util.testutil

import spock.lang.Specification
/**
 * Created by David Sowerby on 04 Feb 2016
 */
class FileTestUtilTest extends Specification {

    def "compare equal files with no lines ignored"() {
        given:
        File ref = TestResource.resource(this, 'ref.txt')
        File refEqual = TestResource.resource(this, 'refEqual.txt')

        when:
        Optional<String> result = FileTestUtil.compare(ref, refEqual)

        then:
        !result.isPresent()
    }

    def "compare files with 1 line different"() {
        given:
        File ref = TestResource.resource(this, 'ref.txt')
        File oneLineDifferent = TestResource.resource(this, 'oneLineDifferent.txt')

        when:
        Optional<String> result = FileTestUtil.compare(ref, oneLineDifferent)

        then:
        result.isPresent()
    }

    def "compare files with 1 line different, but that line ignored"() {
        given:
        File ref = TestResource.resource(this, 'ref.txt')
        File oneLineDifferent = TestResource.resource(this, 'oneLineDifferent.txt')

        when:
        Optional<String> result = FileTestUtil.compare(ref, oneLineDifferent, 3)

        then:
        !result.isPresent()
    }

    def "compare first, limited to before different line"() {
        given:
        File ref = TestResource.resource(this, 'ref.txt')
        File oneLineDifferent = TestResource.resource(this, 'oneLineDifferent.txt')

        when:
        Optional<String> result = FileTestUtil.compareFirst(3, ref, oneLineDifferent, 1)

        then:
        !result.isPresent()
    }

    def "compare first, different line ignored"() {
        given:
        File ref = TestResource.resource(this, 'ref.txt')
        File oneLineDifferent = TestResource.resource(this, 'oneLineDifferent.txt')

        when:
        Optional<String> result = FileTestUtil.compareFirst(4, ref, oneLineDifferent, 3)

        then:
        !result.isPresent()
    }

    def "compare first, different line not ignored"() {
        given:
        File ref = TestResource.resource(this, 'ref.txt')
        File oneLineDifferent = TestResource.resource(this, 'oneLineDifferent.txt')

        when:
        Optional<String> result = FileTestUtil.compareFirst(4, ref, oneLineDifferent)

        then:
        result.isPresent()
    }

    def "compare is successful for available lines, but one file longer"() {
        given:
        File ref = TestResource.resource(this, 'ref.txt')
        File oneLineLonger = TestResource.resource(this, 'oneLineLonger.txt')

        when:
        Optional<String> result = FileTestUtil.compare(ref, oneLineLonger)

        then:
        result.isPresent()
    }

    def "compare with blank lines removed, only 'valid' lines compared"() {
        given:
        File ref = TestResource.resource(this, 'ref.txt')
        File extraBlankLines = TestResource.resource(this, 'extraBlankLines.txt')

        when:
        Optional<String> result = FileTestUtil.compareIgnoreBlankLines(ref, extraBlankLines)

        then:
        !result.isPresent()

        when:
        result = FileTestUtil.compare(ref, extraBlankLines)

        then:
        result.isPresent()

    }
}
