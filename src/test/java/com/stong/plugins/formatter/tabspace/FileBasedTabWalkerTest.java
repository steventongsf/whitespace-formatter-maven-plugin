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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.codehaus.plexus.util.FileUtils;
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
                TabHelper.removeAllTabsAndSpaces((List<String>)results.get("lines")), 
                TabHelper.removeAllTabsAndSpaces((List<String>)results.get("moddedlines")));
    }
    @Test
    public void testNotReplacingMiddleTabs() throws Exception {
        Map<String,Object> results = buildData("src/test/resources/middletabs.txt");
        // Test file was changed
        assertFalse((Boolean)results.get("changed"));
        // Test symbols unchanged
        assertEquals(
                TabHelper.removeAllTabsAndSpaces((List<String>)results.get("lines")), 
                TabHelper.removeAllTabsAndSpaces((List<String>)results.get("moddedlines")));
    }
    @Test
    public void testNotReplacingTrailingSpaces() throws Exception {
        Map<String,Object> results = buildData("src/test/resources/trailingspaces.txt");
        // Test file was changed
        assertFalse((Boolean)results.get("changed"));
        // Test symbols unchanged
        assertEquals(
                TabHelper.removeAllTabsAndSpaces((List<String>)results.get("lines")), 
                TabHelper.removeAllTabsAndSpaces((List<String>)results.get("moddedlines")));    
    }
    @Test
    public void testNotReplacingLeadingSpaces() throws Exception {
        Map<String,Object> results = buildData("src/test/resources/leadingspaces.txt");
        // Test file was changed
        assertFalse((Boolean)results.get("changed"));
        // Test symbols unchanged
        assertEquals(
                TabHelper.removeAllTabsAndSpaces((List<String>)results.get("lines")), 
                TabHelper.removeAllTabsAndSpaces((List<String>)results.get("moddedlines")));
    }
    @Test
    public void testTrailingTabs() throws Exception {
        Map<String,Object> results = buildData("src/test/resources/trailingtabs.txt");
        // Test file was changed
        assertFalse((Boolean)results.get("changed"));
        // Test symbols unchanged
        assertEquals(
                TabHelper.removeAllTabsAndSpaces((List<String>)results.get("lines")), 
                TabHelper.removeAllTabsAndSpaces((List<String>)results.get("moddedlines")));
    }
    @Test
    public void testRun() throws Exception {
        String baseDir = System.getProperty("user.dir");
        File targetDir = new File(baseDir+"/target/tmp");
        if(!targetDir.exists()){
            targetDir.mkdirs(); 
        }
        Collection<File> files = FileUtils.getFiles(new File(baseDir+"/src/test/resources"),null,null);
        for (File f:files) {
            System.out.println(f.getAbsolutePath());
            String newFileName = f.getName().replace(".txt", ".java");
            newFileName = newFileName.substring(0,1).toUpperCase()+newFileName.substring(1);
            System.out.println(targetDir.getAbsolutePath()+"/"+newFileName);
            FileUtils.copyFile(f, new File(newFileName));
        }
        
    }

    static Map<String,Object> buildData(String fileName) throws Exception {
        
        Map<String,Object> map = new HashMap<String,Object>();
        File file = new File(System.getProperty("user.dir"));
        FileWalker fw = new FileWalker(file,null,null);
        List<String> lines = fw.getLines(new File(fileName));
        List<String> moddedlines = new ArrayList<String>();
        boolean changed = false;
        for (String line: lines) {
            String moddedline = TabHelper.replaceLeadingTabs(line);
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
