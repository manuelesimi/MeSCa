package org.campagnelab.mesca.output;

import org.campagnelab.mesca.algorithm.ClusterQueue;

import java.io.File;
import java.io.PrintStream;

/**
 * Created by mas2182 on 1/8/14.
 */
public interface Formatter {

    public void format(ClusterQueue clusters, PrintStream stream);

    public void format(ClusterQueue clusters, File file);
}
