package org.campagnelab.mesca.output;

import org.campagnelab.mesca.algorithm.Cluster;
import org.campagnelab.mesca.algorithm.ClusterQueue;
import org.campagnelab.mesca.algorithm.DetectorWatcher;

import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;

/**
 * Created by mas2182 on 1/8/14.
 */
public class TSVFormatter implements Formatter {

    java.util.Formatter formatter;
    final String separator = "\t";

    public TSVFormatter() {}

    @Override
    public void format(DetectorWatcher watcher, ClusterQueue clusters, PrintStream stream) {
        for (String line : getStatistics(watcher))
            stream.println(line);
        stream.println(getHeader());
        Cluster cluster;
        while (clusters.size() > 0) {
            cluster = clusters.getCluster();
            stream.println(buildLine(cluster));
        }

    }

    @Override
    public void format(DetectorWatcher watcher, ClusterQueue clusters, File file) {
    }

    private String buildLine(Cluster cluster) {
        return String.format("%s%s[%d:%d]%s%d%s%s%s[%f:%f]%s%f",
                cluster.getName(),
                separator,
                cluster.leftEnd(),
                cluster.rightEnd(),
                separator,
                cluster.getUniquePatients(),
                separator,
                Arrays.toString(cluster.getUniquePatientNames().toArray(new String[0])),
                separator,
                cluster.getMinPriorityScore(),
                cluster.getMaxPriorityScore(),
                separator,
                cluster.getRank()
        );
    }

    private String[] getStatistics(DetectorWatcher watcher) {
        return new String[] {
               "#Input file: " + watcher.getInputFileName(),
               "#Time elapsed: " + watcher.elapsedTime() + " sec"
        };
    }


    private String getHeader() {
        return String.format("cluster-name%s[start_position:end_position]%snum_of_patients%ssample(s)%s[min_priority_score:max_priority_score]%srank",
                separator, separator,separator, separator, separator);
    }
}
