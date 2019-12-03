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
import java.util.List;

/**
 * @author Steven Tong
 * 
 */
public class WhitespaceHelper {
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
    static String replaceTrailingTabs(String line) {
        String modified = line;
        while (true) {
            if (modified.endsWith("\t")) {
                modified = modified.substring(0, modified.length() - 1);
            }
            else {
                break;
            }
        }
        return modified;
    }
    static String replaceTrailingTabsAndSpaces(String line) {
        String modified = line;
        while (true) {
            if (modified.endsWith("\t") || modified.endsWith(" ")) {
                modified = modified.substring(0, modified.length() - 1);
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
