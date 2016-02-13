import spock.lang.Specification
import uk.q3c.util.testutil.InvalidProjectPathException
import uk.q3c.util.testutil.TestResource

/**
 * Created by David Sowerby on 04 Feb 2016
 */
class TestResourceTest extends Specification {

    def "testJavaRootDir"() {
        given:
        File f = new File(new File('.'),'src/test/java')
        println f.getCanonicalPath()

        expect:
        TestResource.testJavaRootDir('q3c-testUtil').getCanonicalPath().equals(f.getCanonicalPath())
        TestResource.testJavaRootDir().getCanonicalPath().equals(f.getCanonicalPath())
    }

    def "test resource"() {
        given:

        File f = new File(new File('.'),'src/test/resources')
        println f.getCanonicalPath()

        expect:
        TestResource.testResourceRootDir('q3c-testUtil').getCanonicalPath().equals(f.getCanonicalPath())
        TestResource.testResourceRootDir().getCanonicalPath().equals(f.getCanonicalPath())
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
