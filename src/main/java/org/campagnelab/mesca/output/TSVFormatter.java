package org.campagnelab.mesca.output;

import org.campagnelab.mesca.algorithm.ClusterQueue;
import org.campagnelab.mesca.algorithm.DetectorWatcher;

import java.io.File;
import java.io.PrintStream;

/**
 * Created by mas2182 on 1/8/14.
 */
public class TSVFormatter implements Formatter {

    java.util.Formatter formatter;
    final String separator = "\t";

    public TSVFormatter() {}

    @Override
    public void format(DetectorWatcher watcher, ClusterQueue clusters, PrintStream stream) {
        printStatistics(watcher);
    }

    @Override
    public void format(DetectorWatcher watcher, ClusterQueue clusters, File file) {
        printStatistics(watcher);
    }

    private void printStatistics(DetectorWatcher watcher) {
        System.out.println("Time elapsed: " + watcher.elapsedTime() + " sec");
    }
}
