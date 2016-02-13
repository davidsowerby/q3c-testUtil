

import spock.lang.Specification
import uk.q3c.util.testutil.InvalidProjectPathException
import uk.q3c.util.testutil.TestResource

/**
 * Created by David Sowerby on 04 Feb 2016
 */
class TestResourceTest extends Specification {

    def "testJavaRootDir"() {
        expect:
        TestResource.testJavaRootDir('q3c-testUtil').equals(new File('/home/david/git/q3c-testUtil/src/test/java'))
        TestResource.testJavaRootDir().equals(new File('/home/david/git/q3c-testUtil/src/test/java'))
    }

    def "test resource"() {
        expect:
        TestResource.testResourceRootDir('q3c-testUtil').equals(new File('/home/david/git/q3c-testUtil/src/test/resources'))
        TestResource.testResourceRootDir().equals(new File('/home/david/git/q3c-testUtil/src/test/resources'))
    }

    def "testJavaRootDir with invalid module name"() {
        when:
        TestResource.testJavaRootDir('rubbish')

        then:
        thrown(InvalidProjectPathException)
    }

    def "test root with invalid module name"() {
        when:
        TestResource.testResourceRootDir('rubbish')

        then:
        thrown(InvalidProjectPathException)
    }

    def "test resource, current project assumed"() {
        when:
        File file = TestResource.resource('ref.txt')
        println file
        then:
        file.exists()
    }
}
