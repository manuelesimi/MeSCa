package org.campagnelab.mesca.algorithm;

import org.campagnelab.mesca.input.Sample;

import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

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

    private final long startPosition;

    private final List<StopCondition> stopConditions;

    private long lastPosition;

    private long firstPosition;


    protected Cluster(long startPosition, final List<StopCondition> stopConditions) {
      this.startPosition = startPosition;
        this.stopConditions = stopConditions;
    }

    public long firstPosition() {
        return firstPosition;
    }

    public long lastPosition() {
        return lastPosition;
    }

    protected void goLeft(ListIterator<Sample> sampleListIterator) {
        while (sampleListIterator.hasPrevious()) {

        }
    }

    protected void goRight(ListIterator<Sample> sampleListIterator) {
        while (sampleListIterator.hasNext()) {

        }
    }

    protected void close() {

    }

    public int getUniquePatients() {
        return uniquePatients;
    }

    /**
     * Decides whether this cluster is relevant or not.
     * A relevant cluster is included in the output queue.
     * @return
     */
    public boolean isRelevant() {
        return true;
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
