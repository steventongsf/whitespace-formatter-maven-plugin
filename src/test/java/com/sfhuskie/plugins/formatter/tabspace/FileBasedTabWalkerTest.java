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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.codehaus.plexus.util.FileUtils;
import org.junit.Test;

import com.sfhuskie.plugins.formatter.tabspace.FileWalker;
import com.sfhuskie.plugins.formatter.tabspace.WhitespaceHelper;

public class FileBasedTabWalkerTest {

    @Test
    public void testReplaceLeadingTabs() throws Exception {
        Map<String,Object> results = buildExpectedActualData("src/test/resources/leadingtabs.txt");
        // Test file was changed
        assertTrue((Boolean)results.get("changed"));
        // Test symbols unchanged
        assertListsEqual((List<String>)results.get("lines"), (List<String>)results.get("moddedlines"));
    }
    @Test
    public void testNotReplacingMiddleTabs() throws Exception {
        Map<String,Object> results = buildExpectedActualData("src/test/resources/middletabs.txt");
        // Test file was changed
        assertFalse((Boolean)results.get("changed"));
        // Test symbols unchanged
        assertListsEqual((List<String>)results.get("lines"), (List<String>)results.get("moddedlines"));
    }
    @Test
    public void testReplacingTrailingSpaces() throws Exception {
        Map<String,Object> results = buildExpectedActualData("src/test/resources/trailingspaces.txt");
        // Test file was changed
        assertTrue((Boolean)results.get("changed"));
        // Test symbols unchanged
        assertListsEqual((List<String>)results.get("lines"), (List<String>)results.get("moddedlines"));
    }
    @Test
    public void testNotReplacingLeadingSpaces() throws Exception {
        Map<String,Object> results = buildExpectedActualData("src/test/resources/leadingspaces.txt");
        // Test file was changed
        assertFalse((Boolean)results.get("changed"));
        // Test symbols unchanged
        assertListsEqual((List<String>)results.get("lines"), (List<String>)results.get("moddedlines"));
    }
    @Test
    public void testTrailingTabs() throws Exception {
        Map<String,Object> results = buildExpectedActualData("src/test/resources/trailingtabs.txt");
        // Test file was changed
        assertTrue((Boolean)results.get("changed"));
        // Test symbols unchanged
        assertListsEqual((List<String>)results.get("lines"), (List<String>)results.get("moddedlines"));
    }
    @Test
    public void testRun() throws Exception {
        // Setup test case
        String baseDir = System.getProperty("user.dir");
        File targetDir = new File(baseDir+"/target/tmp");
        if(!targetDir.exists()){
            targetDir.mkdirs(); 
        }
        Collection<File> files = FileUtils.getFiles(new File(baseDir+"/src/test/resources"),null,null);
        for (File f:files) {
            String newFileName = f.getName().replace(".txt", ".java");
            newFileName = newFileName.substring(0,1).toUpperCase()+newFileName.substring(1);
            File targetFile =  new File(targetDir.getAbsolutePath()+"/"+newFileName);
            FileUtils.copyFile(f,targetFile);
        }
        // Run logic
        List<String> exts = new ArrayList<String>();
        exts.add("java");
        FileWalker fw = new FileWalker(targetDir, exts, null );
        fw.walk(true);
        // Validate results
        fw.walk(false);
        Collection<File> moddedFiles = FileUtils.getFiles(targetDir,null,null);
        for (File moddedFile:files) {
            // TODO Compare java files with txt files
            
        }
    }
    static void assertListsEqual(List<String> expected, List<String> actual) {
        assertEquals(
                WhitespaceHelper.removeAllTabsAndSpaces(expected),
                WhitespaceHelper.removeAllTabsAndSpaces(actual));
    }
    static Map<String,Object> buildExpectedActualData(String fileName) throws Exception {
        
        Map<String,Object> map = new HashMap<String,Object>();
        File file = new File(System.getProperty("user.dir"));
        FileWalker fw = new FileWalker(file,null,null);
        List<String> lines = fw.getLines(new File(fileName));
        List<String> moddedlines = new ArrayList<String>();
        boolean changed = false;
        for (String line: lines) {
            //String moddedline = WhitespaceHelper.replaceLeadingTabs(line);
            String moddedline = fw.modifyLine(line);
            if (!line.equals(moddedline)) {
                changed = true;
            }
            moddedlines.add(moddedline);
        }
        map.put("changed", changed);
        map.put("lines", lines);
        map.put("moddedlines", moddedlines);
        return map;
    }
}
