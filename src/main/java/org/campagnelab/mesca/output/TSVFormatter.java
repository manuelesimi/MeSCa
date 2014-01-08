package org.campagnelab.mesca.output;

import org.campagnelab.mesca.algorithm.ClusterQueue;

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
    public void format(ClusterQueue clusters, PrintStream stream) {

    }

    @Override
    public void format(ClusterQueue clusters, File file) {

    }
}
