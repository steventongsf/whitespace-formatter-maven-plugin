package com.sfhuskie.plugins.formatter.tabspace;
/**
 * @author Steven Tong
 * 
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.codehaus.plexus.util.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.sfhuskie.plugins.formatter.tabspace.FileAction;
import com.sfhuskie.plugins.formatter.tabspace.FileWalker;
import com.sfhuskie.plugins.formatter.tabspace.TabHelper;

public class FileWalkerTest implements FileAction {

    
    @Test
    public void testReplaceLeadingTabs() {
        String str = "\t\tsteven was here.";
        assertNotEquals(str, TabHelper.replaceLeadingTabs(str));
    }
    @Test
    public void testNoReplacements() {
        String str = "steven was here.";
        assertEquals(str, TabHelper.replaceLeadingTabs(str));
    }
    @Test
    public void testNoReplacementsLeadingSpaces() {
        String str = "    steven was here.";
        assertEquals(str, TabHelper.replaceLeadingTabs(str));
    }
    @Test
    public void testNoReplaceMiddleTabs() {
        String str = "    steven\twas\there.";
        assertEquals(str, TabHelper.replaceLeadingTabs(str));
    }
    @Test
    public void testNoReplaceTrailingTabs() {
        String str = "steven was here.\t";
        assertEquals(str, TabHelper.replaceLeadingTabs(str));
    }
    @Test
    public  void testSpaceOverride() {
        FileWalker fw = new FileWalker(new File(System.getProperty("user.dir")), null, null);
        for (int i = 0; i < 25; i++) {
            fw.overrideDefaultNumberSpaces(i);
            if (fw.spaces.length() != i) {
                fail("Space override is not correct: "+i+","+fw.spaces.length());
            }
        }
    }
    @Test
    public void testGetFiles() {
        List<String> extensions = new ArrayList<String>();
        extensions.add("txt");
        FileWalker fw = new FileWalker(new File(System.getProperty("user.dir")+"/src/test/resources"), extensions, null);
        Collection<File> files = fw.getFiles();
        List<String> testFiles = new ArrayList<String>();
        testFiles.add("leadingspaces.txt");
        testFiles.add("leadingtabs.txt");
        testFiles.add("middletabs.txt");
        testFiles.add("trailingspaces.txt");
        testFiles.add("trailingtabs.txt");
        for (File f:files) {
           String fname = FileUtils.basename(f.getAbsolutePath())+"txt";
           assertTrue(testFiles.contains(fname));
                
        }
    }
    @Test
    public void testWalk() throws Exception {
        List<String> extensions = new ArrayList<String>();
        extensions.add("txt");
        File file = new File(System.getProperty("user.dir")+"/src/test/resources");
        FileWalker fw = new FileWalker(file, extensions, null);
        Collection<File> files = fw.getFiles();
        try {
            fw.walk(false);
        }
        catch (MojoFailureException e) {
            // expected failure
            return;
        }
        fail("FileWalker.walk() should have thrown an exception");
    }
    @Test
    public void testWalkWithNoActions() throws Exception {
        List<String> extensions = new ArrayList<String>();
        extensions.add("txt");
        File file = new File(System.getProperty("user.dir")+"/src/test/resources");
        FileWalker fw = new FileWalker(file, extensions, null);
        fw.overrideFileAction(this);
        Collection<File> files = fw.getFiles();
        try {
            fw.walk(false);
            assertTrue(fw.modifiedFiles.size() == 0);
            assertTrue(fw.linesChanged == 0);
            assertEquals(fw.files.size(), fw.totalFiles);
        }
        catch (MojoFailureException e) {
            fail("FileWalker.walk() should not fail");
        }
    }
    @Test
    public void testDefaultAttributes() {
        
    }
    
    List<String> testFiles = new ArrayList<String>();

    @Before
    public void setUp() {
        testFiles.add("leadingspaces.txt");
        testFiles.add("leadingtabs.txt");
        testFiles.add("middletabs.txt");
        testFiles.add("trailingspaces.txt");
        testFiles.add("trailingtabs.txt");
    }
    @Override
    public String modifyLine(String line) {
        // Do nothing implementation
        return line;
    }
}

