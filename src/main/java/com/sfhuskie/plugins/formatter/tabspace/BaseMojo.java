package com.sfhuskie.plugins.formatter.tabspace;
/**
 * @author Steven Tong
 * 
 */
import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Parameter;

abstract class BaseMojo extends AbstractMojo {
    Log mavenLog = getLog();

   @Parameter(defaultValue = "${project.build.sourceDirectory}", property = "search.directory", required = true)
   protected File searchDirectory;

   @Parameter(property = "file.extensions", required = true)
   protected List<String> fileExtensions;   
   
   public void execute() throws MojoExecutionException, MojoFailureException {

       mavenLog.info("searchDirectory: "+searchDirectory);
       for (String e:this.fileExtensions) {
           mavenLog.info("extension: "+e);
       }
       FileWalker fw = new FileWalker(searchDirectory, this.fileExtensions, mavenLog);
       fw.walk(true);  
       
   }
}
