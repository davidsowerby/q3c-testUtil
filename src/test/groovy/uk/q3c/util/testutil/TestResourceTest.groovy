package uk.q3c.util.testutil

import spock.lang.Specification
/**
 * Created by David Sowerby on 04 Feb 2016
 */
class TestResourceTest extends Specification {



    def "resource using Class.getResource() with absolute reference"() {

        when:
        File f = TestResource.resource(this, "/dummy.txt")

        then:
        f.exists()
    }

    def "resource using Class.getResource() with root"() {

        when:
        File f = TestResource.resource(this, "/")

        then:
        f.exists()
    }

    def "resource using Class.getResource() with relative reference"() {

        when:
        File f = TestResource.resource(this, "ref.txt")

        then:
        f.exists()
    }
}
