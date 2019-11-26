package com.sfhuskie.plugins.formatter.tabspace;
/**
 * @author Steven Tong
 * 
 */

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

import java.io.File;

/**
 * @goal format
 * @phase validate
 */
public class FormatMojo	extends BaseMojo {

	public void execute() throws MojoExecutionException, MojoFailureException {
	    super.execute();
        FileWalker fw = new FileWalker(projectBasedir, this.fileExtensions, mavenLog);
        fw.walk(true);	
        
	}

}
