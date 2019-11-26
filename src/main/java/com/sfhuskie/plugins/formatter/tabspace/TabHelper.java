package com.sfhuskie.plugins.formatter.tabspace;
/**
 * @author Steven Tong
 * 
 */
import java.util.List;

/**
 * @author Steven Tong
 * 
 */
public class TabHelper {
    static String spaces = "    ";
    static String replaceLeadingTabs(String line) {
        String modified = line;
        while (true) {
            if (modified.startsWith("\t")) {
                modified = modified.replace("\t", spaces);
            }
            else {
                break;
            }
        }
        return modified;
    }
    static String removeAllTabsAndSpaces(List<String> lines) {
        String result = "";
        for (String line: lines) {
            result += line.replaceAll("\t", "").replaceAll(" ", "");
        }
        return result;
    }

}
