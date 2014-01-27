package org.campagnelab.mesca.output;

import org.campagnelab.mesca.algorithm.ClusterQueue;
import org.campagnelab.mesca.algorithm.DetectorWatcher;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by mas2182 on 1/8/14.
 */
public interface Formatter {

    public void format(DetectorWatcher watcher, ClusterQueue clusters, PrintStream stream);

    public void format(DetectorWatcher watcher, ClusterQueue clusters, File file) throws IOException;
}
