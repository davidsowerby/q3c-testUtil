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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static com.google.common.base.Preconditions.*;

/**
 * Originally a very poor attempt to differentiate between IDEA and Eclipse project structures, but was unreliable.  Replaced by using Class.getResource
 * correctly.
 * <p>
 * All other deprecated methods will be removed by v0.9
 *
 * @author David Sowerby
 */
public class TestResource {

    /**
     * Static utility only
     */
    private TestResource() {
// do nothing
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
