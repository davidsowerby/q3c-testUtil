/*
 *
 *  * Copyright (c) 2016. David Sowerby
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 *  * the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 *  * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *  * specific language governing permissions and limitations under the License.
 *
 */

package uk.q3c.util.testutil;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A couple of helper methods to ensure that the correct directory is looked up for file related test operations. This
 * is useful mainly because of the difference between IDEA and Eclipse in their treatment of 'master' and 'sub'
 * projects
 * ... IDEA uses a different project path to Eclipse (IDEA runs from the root project) see
 * https://github.com/davidsowerby/krail/issues/253
 * <p>
 * Things changed a bit when a flat structure was introduced (master on the same folder level as sub-projects), and
 * IDEA
 * could be in either 'master' or 'krail' when the test is run.  The current solution has not been tested with Eclipse
 * https://github.com/davidsowerby/krail/issues/327
 * <p>
 * The only other way to deal with this problem is to set a path to the project root parent folder through environment
 * variables.
 *
 * @author dsowerby
 */
public class TestResource {

    private static List<String> moduleNames;

    static {
        final String[] modules = new String[]{

                "master", "krail", "krail-jpa", "krail-demo", "krail-testApp", "krail-bench", "krail-testUtil",
                "krail-quartz", "q3c-testUtil"};
        moduleNames = Lists.newArrayList(modules);
    }

    /**
     * Returns a File object representing the 'src/test/java' folder within the current module (IDEA) or sub-project
     * (Eclipse)
     *
     * @param moduleName
     *         the IDEA module (Eclipse sub-project) for which the test root is required
     *
     * @return File object for the test root directory
     */
    public static File testJavaRootDir(String moduleName) {
        return new File(projectRoot(), moduleName + "/src/test/java");
    }

    //Not considered worth fixing in test code
    @SuppressFBWarnings({"DLC_DUBIOUS_LIST_COLLECTION", "PATH_TRAVERSAL_IN"})
    private static File projectRoot() {
        File f = new File(".");
        Iterable<String> segments = Splitter.on("/")
                                            .split(f.getAbsolutePath());

        ArrayList<String> strings = Lists.newArrayList(segments);
        //removes the trailing "."
        strings.remove(strings.size() - 1);
        //removes the current directory, if it is a module name,
        // Randomly seems to be either 'master' or 'krail' in IDEA
        //and may not be there in Eclipse
        String lastSegment = strings.get(strings.size() - 1);
        if (moduleNames.contains(lastSegment)) {
            strings.remove(strings.size() - 1);
        }


        Joiner joiner = Joiner.on("/")
                              .skipNulls();
        String rootPathName = joiner.join(strings);
        return new File(rootPathName);
    }

    /**
     * Returns a File object representing the 'src/test/resources' folder within the current module (IDEA) or
     * sub-project (Eclipse)
     *
     * @param moduleName
     *         the IDEA module (Eclipse sub-project) for which the test root is required
     *
     * @return File object for the test root directory
     */
    public static File testResourceRootDir(String moduleName) {
        return new File(projectRoot(), moduleName + "/src/test/resources");
    }
}
