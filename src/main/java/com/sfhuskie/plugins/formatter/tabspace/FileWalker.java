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
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileWalker implements FileAction {
    String encoding = "UTF-8";
    // Default is 4 spaces
    String spaces = "    ";
    Log log;
    File baseDirectory;
    List<String> extensions = new ArrayList<String> ();
    
    Collection<File> files;
    List<File> modifiedFiles;
    
    FileAction action = this;
    
    int totalFiles = 0;
    int filesChanged = 0;
    int linesChanged = 0;
    
    /**
     * Constructor
     * @param baseDir       Directory to scan
     * @param extensions    Valid file extensions to scan for
     * @param log           Instance of logger
     */
    FileWalker(File baseDir, List<String> extensions, Log log) {
		this.baseDirectory = baseDir;
		this.log = log;
		if (extensions == null) {
		    this.extensions.add("java");
		}
		else {
		      this.extensions = extensions;
		}
	}
    /**
     * 
     * @param n Override number of spaces to substitute leading tabs with
     */
    void overrideDefaultNumberSpaces(int n) {
        spaces = "";
        for (int i = 0; i < n; i++) {
            spaces += " ";
        }
    }

    /**
	 * @param f    File instance to get lines from
	 * @return List    List of strings
	 * @throws MojoExecutionException  Failure to read file
	 */
	List<String> getLines(File f) throws MojoExecutionException {
        try {
            return FileUtils.readLines(f, encoding);
        } 
        catch (IOException e) {
            throw new MojoExecutionException("Failed to read lines from " + f.getAbsolutePath(), e);
        }
	    
	}
	/**
	 * @return Collection return list of File instances
	 */
	Collection<File> getFiles() {
	    return FileUtils.listFiles(baseDirectory, extensions.toArray(new String[extensions.size() - 1]), true);
	}
	/**
	 * @param updateFiles  if true, update files. if false, verify files
	 * @throws MojoExecutionException  Fail to write file
	 * @throws MojoFailureException    Tabs found in a file
	 */
	public void walk(boolean updateFiles) throws MojoExecutionException, MojoFailureException {

		if (!baseDirectory.isDirectory()) {
		    if (log != null)
		        log.debug("Directory doesn't exist: " + baseDirectory.getAbsolutePath());
			return;
		}
		files = getFiles();
        modifiedFiles = new ArrayList<File>();

        for (File file : files) {
            if (log != null)
                log.debug("Reading file: " + file.getAbsolutePath());
			List<String> lines = getLines(file);
			Boolean isFileModified = false;
			List<String> processedLines = new ArrayList<String>(lines.size());
			int lineNumber = 0;
			for (String line : lines) {
				lineNumber++;
				String modifiedLine = this.action.modifyLine(line);
				Boolean isLineModified = (!modifiedLine.equals(line));
				if(isLineModified){
				    linesChanged++;
				    if (log != null)
				        log.debug("Tabs found on line " + lineNumber);
				}
				processedLines.add(modifiedLine);
				isFileModified = (isFileModified || isLineModified);
			}
			if (isFileModified) {
			    filesChanged++;
			    modifiedFiles.add(file);
				if (updateFiles) {
				    // Only write file if update is true
    				try {
    					FileUtils.writeLines(file, encoding, processedLines);
    				} 
    				catch (IOException e) {
    					throw new MojoExecutionException("Failed to write file " + file.getAbsolutePath(), e);
    				}
				}
				else {
				    // verify mode so throw error when 1st tab is encountered in any file
                    throw new MojoFailureException("Tabs found in " + file.getAbsolutePath());
				}
			}
		}
        totalFiles = files.size();
        this.printStats(modifiedFiles);
	}
	public String modifyLine(String line) {
	    return WhitespaceHelper.replaceLeadingTabs(line);
	}
	/**    Override default action
	 * @param action FileAction instance.  Default is this instance
	 */
	public void overrideFileAction(FileAction action) {
	    this.action = action;
	}
	void printStats(List<File> files) {
	    if (log != null) {
    	    log.info("Total files  : "+totalFiles);
            log.info("Files changed: "+filesChanged);
            log.info("Lines changed: "+linesChanged);
            for (File f:files) {
                log.info("File:        : "+f.getAbsolutePath());
            }
	    }
	    
	}
}
