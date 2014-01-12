package org.campagnelab.mesca.algorithm;

import java.util.Comparator;

/**
 * A cluster of ....
 *
 * @author manuele
 */
class Cluster {

    /**
     * Number of unique patients in the cluster.
     */
    private int uniquePatients = 0;

    /**
     * Lowest priority score in the cluster.
     */
    private int lowestPS;

    /**
     * Highest priority score in the cluster.
     */
    private int highestPS;

    private long startPosition;

    private long lowestPosition;

    private long highestPosition;


    protected Cluster(long startPosition) {
      this.startPosition = startPosition;
    }

    protected boolean goLeft() {
        return false;
    }

    protected boolean goRight() {
        return false;
    }

    protected void close() {

    }

    public int getUniquePatients() {
        return uniquePatients;
    }

    public static class ClusterComparator implements Comparator<Cluster> {

        @Override
        public int compare(Cluster cluster1, Cluster cluster2) {
            //TODO: refine the comparator to consider also the size and score of the cluster?
            return cluster1.getUniquePatients() < cluster2.getUniquePatients() ? -1
                    : cluster1.getUniquePatients() > cluster2.getUniquePatients() ? 1
                    : 0;
        }

    }
}
