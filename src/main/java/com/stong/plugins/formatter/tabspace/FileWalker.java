package com.stong.plugins.formatter.tabspace;
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

public class FileWalker {
    String encoding = "UTF-8";
    // Default is 4 spaces
    String spaces = "    ";
    Log log;
    File baseDirectory;
    List<String> extensions;
    
    int filesChanged = 0;
    int linesChanged = 0;
    
    /**
     * Override default number of spaces to replace tabs with
     * @param n
     */
    void overrideDefaultNumberSpaces(int n) {
        spaces = "";
        for (int i = 0; i < n; i++) {
            spaces += " ";
        }
    }

    /**
     * Constructor
     * @param baseDirectory
     * @param extensions
     * @param log
     */
    FileWalker(String baseDirectory, List<String> extensions, Log log) {
		this.baseDirectory = new File(baseDirectory);
		this.log = log;
	}
	/**
	 * @param f
	 * @return
	 * @throws MojoExecutionException
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
	 * @return
	 */
	Collection<File> getFiles() {
	    return FileUtils.listFiles(baseDirectory, extensions.toArray(new String[extensions.size() - 1]), true);
	}
	/**
	 * @param verify
	 * @throws MojoExecutionException
	 * @throws MojoFailureException
	 */
	public void walk(boolean update) throws MojoExecutionException, MojoFailureException {

		if (!baseDirectory.isDirectory()) {
			log.debug("Directory doesn't exist: " + baseDirectory.getAbsolutePath());
			return;
		}
		
		Collection<File> files = getFiles();

        List<File> modifiedFiles = new ArrayList<File>();
		for (File file : files) {
			log.debug("Reading file: " + file.getAbsolutePath());
			List<String> lines = getLines(file);
			Boolean isFileModified = false;
			List<String> processedLines = new ArrayList<String>(lines.size());
			int lineNumber = 0;
			for (String line : lines) {
				lineNumber++;
				String modifiedLine = modifyLine(line);
				// INFO Put additional line modifications after this line
				Boolean isLineModified = (!modifiedLine.equals(line));
				if(isLineModified){
				    linesChanged++;
					log.debug("Tabs found on line " + lineNumber);
				}
				processedLines.add(modifiedLine);
				isFileModified = (isFileModified || isLineModified);
			}
			if (isFileModified) {
			    filesChanged++;
			    modifiedFiles.add(file);
				if (update) {
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
			this.printStats(modifiedFiles);
		}
	}
	String modifyLine(String line) {
	    return TabHelper.replaceLeadingTabs(line);
	}
	void printStats(List<File> files) {
        log.info("Lines changed: "+linesChanged);
        log.info("Files changed: "+filesChanged);
        for (File f:files) {
            log.info("File:        : "+f.getAbsolutePath());
        }
	    
	}
}
