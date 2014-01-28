package org.campagnelab.mesca.output;

import org.campagnelab.mesca.algorithm.Cluster;
import org.campagnelab.mesca.algorithm.ClusterQueue;
import org.campagnelab.mesca.algorithm.DetectorWatcher;

import java.io.*;
import java.util.Arrays;

/**
 * Formatter for TSV output.
 */
public class TSVFormatter implements Formatter {

    java.util.Formatter formatter;
    final String separator = "\t";

    public TSVFormatter() {}

    @Override
    public void format(DetectorWatcher watcher, ClusterQueue clusters, PrintStream stream) {
        for (String line : getStatistics(watcher))
            stream.println(line);
        for (String desc : watcher.getDescriptionForStopConditions())
            stream.println("#" + desc);
        stream.println(getHeader());
        Cluster cluster;
        while (clusters.size() > 0) {
            cluster = clusters.getCluster();
            stream.println(buildLine(cluster));
        }
    }

    @Override
    public void format(DetectorWatcher watcher, ClusterQueue clusters, File file) throws IOException{
        PrintWriter writer = new PrintWriter(file, "UTF-8");
        for (String line : getStatistics(watcher))
            writer.println(line);
        for (String desc : watcher.getDescriptionForStopConditions())
            writer.println("#" + desc);
        writer.println(getHeader());
        Cluster cluster;
        while (clusters.size() > 0) {
            cluster = clusters.getCluster();
            writer.println(buildLine(cluster));
        }
        writer.close();
    }

    private String buildLine(Cluster cluster) {

        return String.format("%s%s%s:%d-%d%s%d%s%s%s[%f:%f]%s%f",
                cluster.getName(),
                separator,
                cluster.getChromosome(),
                cluster.leftEnd(),
                cluster.rightEnd(),
                separator,
                cluster.getUniquePatients(),
                separator,
                Arrays.toString(cluster.getSortedUniquePatientNames()),
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
               "#Time elapsed to parse the input file: " + watcher.parserElapsedTime() + " sec",
               "#Time elapsed for cluster detection: " + watcher.detectorElapsedTime() + " sec",
               "#Sites analyzed: " + watcher.getTotalSitesAnalyzed(),
               "#Relevant Sites: " + watcher.getNumOfSites(),
               "#Degree of proximity: " + watcher.getDegreeOfProximity() };
    }


    private String getHeader() {
        return String.format("cluster-name%s[start_position:end_position]%snum_of_patients%ssample(s)%s[min_priority_score:max_priority_score]%srank",
                separator, separator,separator, separator, separator);
    }
}
