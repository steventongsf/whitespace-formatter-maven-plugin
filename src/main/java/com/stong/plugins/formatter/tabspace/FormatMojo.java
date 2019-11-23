package com.stong.plugins.formatter.tabspace;
/**
 * @author Steven Tong
 * 
 */

import org.apache.maven.plugin.AbstractMojo;
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

		boolean verify = false;

		Log mavenLog = getLog();

		//FileWalker.walk(verify, fileExtensions, projectBasedir, mavenLog);

	}

}
