package org.aulides.maven.plugin.xmlmerge;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.atteo.xmlcombiner.XmlCombiner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Goal which merge XML file
 *
 * @goal mergexml
 * @phase prepare-package
 */
public class MergeXmlMojo extends AbstractMojo {
    /**
     * @parameter
     * @required
     */
    private File fromFile ;

    /**
     * The input directory in which the XML Merge Data can be found.
     *
     * @parameter
     * @required
     */
    private File toFile ;

    /**
     * The output directory into which to copy the resources.
     *
     * @parameter
     * @required
     */
    private File outputFile;

    public void execute() throws MojoExecutionException {
        getLog().info("merge '" + fromFile + " to file " + toFile);

        try {
            // see https://github.com/atteo/xml-combiner
            XmlCombiner combiner = new XmlCombiner("id");

            // combine files
            combiner.combine(new FileInputStream(toFile));
            combiner.combine(new FileInputStream(fromFile));

            String parentDir = outputFile.getParent() ;
            getLog().info("Store " + outputFile.getName() + " to " + parentDir);
            File pDir = new File(parentDir) ;
            if (!pDir.exists())
                pDir.mkdirs() ;

           // store the result
            combiner.buildDocument(new FileOutputStream(outputFile));
        } catch (Exception e) {
            throw new MojoExecutionException("Unable to merge xml", e);
        }

    }
}
