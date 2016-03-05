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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Originally a very poor attempt to differentiate between IDEA and Eclipse project structures, but was unreliable.  Replaced by using Class.getResource
 * correctly.
 *
 * All other deprecated methods will be removed by v0.9
 *
 * @author David Sowerby
 */
public class TestResource {

    /**
     * Static utility only
     */
    private TestResource() {

    }

    /**
     * Returns a File object representing the 'src/test/java' folder within the {@code moduleName}
     *
     * @param moduleName the IDEA module (Eclipse sub-project) for which the test root is required
     * @return File object for the test root directory
     * @deprecated use {@link #resource(Object, String)}
     */
    @Deprecated
    public static File testJavaRootDir(@Nonnull String moduleName) {
        return testFolder(moduleName, "java");
    }

    /**
     * Returns a File object representing the 'src/test/java' folder within the current module(IDEA) sub-project(Eclipse)
     *
     * @return File object for the test root directory
     * @deprecated use {@link #resource(Object, String)}
     */
    @Deprecated
    public static File testJavaRootDir() {
        return testFolder("java");
    }


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
        strings.remove(strings.size() - 1);


        Joiner joiner = Joiner.on("/")
                              .skipNulls();
        String rootPathName = joiner.join(strings);
        return new File(rootPathName);
    }

    private static File moduleRoot() {
        File f = new File(".");
        Iterable<String> segments = Splitter.on("/")
                                            .split(f.getAbsolutePath());

        ArrayList<String> strings = Lists.newArrayList(segments);
        //removes the trailing "."
        strings.remove(strings.size() - 1);
        Joiner joiner = Joiner.on("/")
                              .skipNulls();
        String rootPathName = joiner.join(strings);
        return new File(rootPathName);
    }

    /**
     * Returns a File object representing the 'src/test/resources' folder from {@code moduleName} (module in IDEA, sub-project in Eclipse)
     *
     * @param moduleName the IDEA module (Eclipse sub-project) for which the test root is required
     * @return File object for the test root directory
     * @deprecated use {@link #resource(Object, String)}
     */
    @Deprecated
    public static File testResourceRootDir(@Nonnull String moduleName) {
        return testFolder(moduleName, "resources");
    }

    /**
     * Returns a File object representing the 'src/test/resources' folder within the current module (IDEA) or
     * sub-project (Eclipse)
     *
     * @return File object for the test root directory
     * @deprecated use {@link #resource(Object, String)}
     */
    @Deprecated
    public static File testResourceRootDir() {
        return testFolder("resources");
    }

    private static File testFolder(@Nonnull String moduleName, @Nonnull String folder) {
        checkNotNull(moduleName);
        checkNotNull(folder);
        File file = new File(projectRoot(), moduleName + "/src/test/" + folder);
        if (!file.exists()) {
            throw new InvalidProjectPathException(file.getAbsolutePath());
        }
        return file;
    }

    private static File testFolder(@Nonnull String folder) {
        checkNotNull(folder);
        File file = new File(moduleRoot(), "/src/test/" + folder);
        return file;
    }

    /**
     * Returns a file from the src/test/resources folder of the current module (IDEA) / sub-project (Eclipse).  No check is made for the existence of the file.
     *
     * @param filename the file name to use
     * @return File object representing a file from the src/test/resources folder of the current module (IDEA) / sub-project (Eclipse).
     * @deprecated use {@link #resource(Object, String)}
     */
    @Deprecated
    public static File resource(@Nonnull String filename) {
        checkNotNull(filename);
        return new File(testResourceRootDir(), filename);
    }

    /**
     * Returns a resource from the test resources folder.  This method is really just a wrapper to a couple of standard calls to avoid having to remember
     * those calls ..
     *
     * @param reference    usually an instance of a test class - this is used to call {@link Class#getResource(String)}
     * @param resourceName the name of the resource to find - see {@link Class#getResource(String)} for rules on absolute / relative references
     * @return File object representing the resource or null if the file does not exist
     */
    @Nullable
    public static File resource(@Nonnull Object reference, @Nonnull String resourceName) throws URISyntaxException {
        checkNotNull(reference);
        checkNotNull(resourceName);
        URL url = reference.getClass()
                           .getResource(resourceName);
        return Paths.get(url.toURI())
                    .toFile();

    }
}
