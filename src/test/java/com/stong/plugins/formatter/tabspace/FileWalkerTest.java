package com.stong.plugins.formatter.tabspace;
/**
 * @author Steven Tong
 * 
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.Test;

import com.stong.plugins.formatter.tabspace.FileWalker;
import com.stong.plugins.formatter.tabspace.TabHelper;

public class FileWalkerTest {

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
        FileWalker fw = new FileWalker(System.getProperty("user.dir"), null, null);
        for (int i = 0; i < 25; i++) {
            fw.overrideDefaultNumberSpaces(i);
            if (fw.spaces.length() != i) {
                fail("Space override is not correct: "+i+","+fw.spaces.length());
            }
        }
    }

}
