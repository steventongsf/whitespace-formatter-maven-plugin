package com.sfhuskie.plugins.formatter.tabspace;
/**
 * @author Steven Tong
 * 
 */
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

@Mojo(name = "verify", defaultPhase = LifecyclePhase.VALIDATE, requiresProject = true, threadSafe = true)
public class VerifyMojo	extends BaseMojo {
    public void execute() throws MojoExecutionException, MojoFailureException {
        super.execute();
        FileWalker fw = new FileWalker(projectBasedir, this.fileExtensions, mavenLog);
        fw.walk(true);  
    }

}
