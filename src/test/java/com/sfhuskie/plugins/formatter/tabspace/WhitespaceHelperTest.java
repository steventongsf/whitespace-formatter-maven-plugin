package com.sfhuskie.plugins.formatter.tabspace;
/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * @author Steven Tong
 * 
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class WhitespaceHelperTest {
    List<String> testFiles = new ArrayList<String>();

    @Before
    public void setUp() {
        testFiles.add("leadingspaces.txt");
        testFiles.add("leadingtabs.txt");
        testFiles.add("middletabs.txt");
        testFiles.add("trailingspaces.txt");
        testFiles.add("trailingtabs.txt");
    }
    @Test
    public void testReplaceLeadingTabs() {
        String str = "\t\tsteven was here.";
        assertEquals("        steven was here.", WhitespaceHelper.replaceLeadingTabs(str));
    }
    @Test
    public void testNoReplacements() {
        String str = "steven was here.";
        assertEquals(str, WhitespaceHelper.replaceLeadingTabs(str));
    }
    @Test
    public void testNoReplacementsLeadingSpaces() {
        String str = "    steven was here.";
        assertEquals(str, WhitespaceHelper.replaceLeadingTabs(str));
    }
    @Test
    public void testNoReplaceMiddleTabs() {
        String str = "    steven\twas\there.";
        assertEquals(str, WhitespaceHelper.replaceLeadingTabs(str));
    }
    @Test
    public void testNoReplaceTrailingTabs() {
        String str = "steven was here.\t";
        assertEquals(str, WhitespaceHelper.replaceLeadingTabs(str));
    }
    @Test
    public void testReplaceTrailingWS1() {
        String line = "\t\tsteven was here.\t\t\t";
        assertEquals("\t\tsteven was here.", WhitespaceHelper.replaceTrailingTabsAndSpaces(line));
    }
    @Test
    public void testReplaceTrailingWS2() {
        String line = "\t\tsteven was here. \t";
        assertEquals("\t\tsteven was here.", WhitespaceHelper.replaceTrailingTabsAndSpaces(line));
    }
    @Test
    public void testReplaceTrailingWS3() {
        String line = "\t\tsteven was here. \t \t";
        assertEquals("\t\tsteven was here.", WhitespaceHelper.replaceTrailingTabsAndSpaces(line));
    }
    @Test
    public void testReplaceTrailingWS4() {
        String line = "\t\tsteven was here. \t \t";
        assertEquals("\t\tsteven was here.", WhitespaceHelper.replaceTrailingTabsAndSpaces(line));
    }
    @Test
    public void testReplaceTrailingWS5() {
        String line = "    steven was here. \t \t";
        assertEquals("    steven was here.", WhitespaceHelper.replaceTrailingTabsAndSpaces(line));
    }
}
