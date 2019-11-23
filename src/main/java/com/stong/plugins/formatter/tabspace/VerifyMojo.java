package com.stong.plugins.formatter.tabspace;
/**
 * @author Steven Tong
 * 
 */
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

/**
 * @goal verify
 * @phase validate
 */
public class VerifyMojo	extends BaseMojo {
	public void execute() throws MojoExecutionException, MojoFailureException {
		boolean verify = true;
		Log mavenLog = getLog();
		mavenLog.info("projectBasedir: "+projectBasedir);
        for (String e:this.fileExtensions) {
            mavenLog.info("extension: "+e);
        }
		//TabWalker.walk(verify, fileExtensions, projectBasedir, mavenLog);
	}
}
