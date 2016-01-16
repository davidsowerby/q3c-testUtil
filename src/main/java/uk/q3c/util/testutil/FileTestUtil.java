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

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by David Sowerby on 16/11/14.
 */
public class FileTestUtil {

    /**
     * Make a line by line comparison, of all lines, of the text files provided at {@code file1} and {@code file2}.  If they are not
     * the same, the comparison stops at the first mis-match and returns an Optional<String> containing a description
     * of the failure.  Lines given in {@code ignore} are not compared - this can be useful, for example, where a
     * line contains a timestamp. Even when a line is ignored for comparison, it must exist in both files for match
     * to succeed.
     *
     * @param ignore
     *         optional lines to ignore
     * @param file1
     *         the first file used in comparison
     * @param file2
     *         the second file used in comparison
     *
     * @return an empty Optional if match successful, otherwise a description of the failure
     *
     * @throws IOException
     */
    public static Optional<String> compare(File file1, File file2, Integer... ignore) throws IOException {
        return compareFirst(-1, file1, file2, ignore);
    }

    /**
     * Make a line by line comparison, up to and including {@code linesToCompare}, of the text files provided at {@code file1} and {@code file2}.
     * <p>
     * If they are not the same, the comparison stops at the first mis-match and returns an Optional<String> containing a description of the failure.  Lines
     * given in {@code ignore} are not compared - this can be useful, for example, where a line contains a timestamp. Even when a line is ignored for
     * comparison, it must exist in both files for match to succeed.
     *
     * @param ignore
     *         optional lines to ignore
     * @param file1
     *         the first file used in comparison
     * @param file2
     *         the second file used in comparison
     *
     * @return an empty Optional if match successful, otherwise a description of the failure
     *
     * @throws IOException
     */
    public static Optional<String> compareFirst(int linesToCompare, File file1, File file2, Integer... ignore) throws IOException {
        List<String> list1 = FileUtils.readLines(file1);
        List<String> list2 = FileUtils.readLines(file2);

        if (linesToCompare > 0) {
            list1 = Lists.partition(list1, linesToCompare)
                         .get(0);
            list2 = Lists.partition(list2, linesToCompare)
                         .get(0);
        }
        List<Integer> ignores = Arrays.asList(ignore);
        boolean pass = true;

        int max = list1.size() < list2.size() ? list1.size() : list2.size();
        //
        for (int i = 0; i < max; i++) {
            if (!ignores.contains(i)) {
                String item1 = list1.get(i);
                String item2 = list2.get(i);

                if (!item1.equals(item2)) {
                    pass = false;
                    String comment = "line " + i + " is not the same. '" + item1 + "' ... '" + item2 + '\'';
                    return Optional.of(comment);
                }
            }
        }

        if (list1.size() != list2.size()) {
            String comment = "Compared successfully up to line " + max + "but lists are of different size, lists 1 " +
                    "and 2 have " + list1.size() + " and " + list2.size() + " lines respectively";
            return Optional.of(comment);
        }
        return Optional.empty();

    }


}
