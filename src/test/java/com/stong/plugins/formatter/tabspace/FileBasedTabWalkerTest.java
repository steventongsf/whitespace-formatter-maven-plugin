package com.stong.plugins.formatter.tabspace;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.Test;

import com.stong.plugins.formatter.tabspace.FileWalker;
import com.stong.plugins.formatter.tabspace.TabHelper;

public class FileBasedTabWalkerTest {

    @Test
    public void testReplaceLeadingTabs() throws Exception {
        Map<String,Object> results = buildData("src/test/resources/leadingtabs.txt");
        // Test file was changed
        assertTrue((Boolean)results.get("changed"));
        // Test symbols unchanged
        assertEquals(
                TabHelper.replaceAllTabsAndSpaces((List<String>)results.get("lines")), 
                TabHelper.replaceAllTabsAndSpaces((List<String>)results.get("moddedlines")));
    }
    @Test
    public void testNotReplacingMiddleTabs() throws Exception {
        Map<String,Object> results = buildData("src/test/resources/middletabs.txt");
        // Test file was changed
        assertFalse((Boolean)results.get("changed"));
        // Test symbols unchanged
        assertEquals(
                TabHelper.replaceAllTabsAndSpaces((List<String>)results.get("lines")), 
                TabHelper.replaceAllTabsAndSpaces((List<String>)results.get("moddedlines")));
    }
    @Test
    public void testNotReplacingTrailingSpaces() throws Exception {
        Map<String,Object> results = buildData("src/test/resources/trailingspaces.txt");
        // Test file was changed
        assertFalse((Boolean)results.get("changed"));
        // Test symbols unchanged
        assertEquals(
                TabHelper.replaceAllTabsAndSpaces((List<String>)results.get("lines")), 
                TabHelper.replaceAllTabsAndSpaces((List<String>)results.get("moddedlines")));    
    }
    @Test
    public void testNotReplacingLeadingSpaces() throws Exception {
        Map<String,Object> results = buildData("src/test/resources/leadingspaces.txt");
        // Test file was changed
        assertFalse((Boolean)results.get("changed"));
        // Test symbols unchanged
        assertEquals(
                TabHelper.replaceAllTabsAndSpaces((List<String>)results.get("lines")), 
                TabHelper.replaceAllTabsAndSpaces((List<String>)results.get("moddedlines")));
    }
    @Test
    public void testTrailingTabs() throws Exception {
        Map<String,Object> results = buildData("src/test/resources/trailingtabs.txt");
        // Test file was changed
        assertFalse((Boolean)results.get("changed"));
        // Test symbols unchanged
        assertEquals(
                TabHelper.replaceAllTabsAndSpaces((List<String>)results.get("lines")), 
                TabHelper.replaceAllTabsAndSpaces((List<String>)results.get("moddedlines")));
    }

    static Map<String,Object> buildData(String fileName) throws Exception {
        
        Map<String,Object> map = new HashMap<String,Object>();
        FileWalker fw = new FileWalker(System.getProperty("user.dir"),null,null);
        List<String> lines = fw.getLines(new File(fileName));
        List<String> moddedlines = new ArrayList<String>();
        boolean changed = false;
        for (String line: lines) {
            String moddedline = TabHelper.replaceLeadingTabs(line);
            //System.out.println(line);
            //System.out.println(moddedline);
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
