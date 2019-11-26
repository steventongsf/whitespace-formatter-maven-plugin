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

abstract class BaseMojo extends AbstractMojo {
    Log mavenLog = getLog();
    /**
    *
    * @parameter project.basedir=""
    * @required
    */
   protected File projectBasedir;
   /**
   *
   * @parameter file.extensions=""
   * @required
   */
   protected List<String> fileExtensions;   
   
   public void execute() throws MojoExecutionException, MojoFailureException {

       mavenLog.info("projectBasedir: "+projectBasedir);
       for (String e:this.fileExtensions) {
           mavenLog.info("extension: "+e);
       }
       FileWalker fw = new FileWalker(projectBasedir, this.fileExtensions, mavenLog);
       fw.walk(true);  
       
   }
}
