package com.stong.plugins.formatter.tabspace;
/**
 * @author Steven Tong
 * 
 */
import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;

abstract class BaseMojo extends AbstractMojo {
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
}
