package org.campagnelab.mesca.algorithm;

import org.campagnelab.mesca.input.Sample;

import java.util.*;

/**
 * A cluster of samples in a region matching a set of {@link org.campagnelab.mesca.algorithm.StopCondition}s.
 *
 * @author manuele
 */
public class Cluster {


    protected static enum DIRECTION {
        LEFT,
        RIGHT
    }
    /**
     * Number of unique patients in the cluster.
     */
    private Set<String> uniquePatients = new HashSet<String>();

    /**
     * Lowest priority score in the cluster.
     */
    private float minPriorityScore = 0;

    /**
     * Highest priority score in the cluster.
     */
    private float maxPriorityScore = 0;

    /**
     * The position around which the cluster is built.
     */
    private final long startPosition;

    private final List<StopCondition> stopConditions;

    private long rightEnd;

    private long leftEnd;


    protected Cluster(long startPosition, final List<StopCondition> stopConditions) {
        this.startPosition = startPosition;
        this.stopConditions = stopConditions;
    }

    /**
     * Gets the position at the left end of the cluster.
     * @return
     */
    public long leftEnd() {
        return leftEnd;
    }

    /**
     * Gets the position at the right end of the cluster.
     * @return
     */
    public long rightEnd() {
        return rightEnd;
    }

    /**
     * Extends the cluster to the next left positions.
     * @param sampleListIterator
     */
    protected void goLeft(ListIterator<Sample> sampleListIterator) {
        leftLoop: while (sampleListIterator.hasPrevious()) {
            Sample previous = sampleListIterator.previous();
            for (StopCondition condition : this.stopConditions) {
                if (condition.apply(this, previous, DIRECTION.LEFT)) {
                    //skip the sample
                    break leftLoop;
                }
            }
            this.addSample(previous, DIRECTION.LEFT);
        }
    }

    public Set<String> getSamples() {
        return Collections.unmodifiableSet(this.uniquePatients);
    }

    /**
     * Adds the sample to the cluster.
     * @param sample
     * @param direction
     */
    private void addSample(Sample sample, DIRECTION direction) {

        //calculate if there is a new unique patient
        uniquePatients.add(sample.getName());

        //extend the cluster according to the position
        switch (direction) {
            case LEFT:
                    this.leftEnd = sample.getPosition();
                break;
            case RIGHT:
                    this.rightEnd = sample.getPosition();
                break;
        }

        //record if the sample has the lowest or highest priority
        if (sample.getPriorityScore() > this.maxPriorityScore)
            this.maxPriorityScore = sample.getPriorityScore();
        if (sample.getPriorityScore() < this.minPriorityScore)
            this.minPriorityScore = sample.getPriorityScore();
    }

    /**
     * Extends the cluster to the next right positions.
     * @param sampleListIterator
     */
    protected void goRight(ListIterator<Sample> sampleListIterator) {
        rightLoop: while (sampleListIterator.hasNext()) {
            Sample next = sampleListIterator.next();
            for (StopCondition condition : this.stopConditions) {
                if (condition.apply(this,next, DIRECTION.RIGHT)) {
                  //skip the sample
                  break rightLoop;
                }
            }
            this.addSample(next, DIRECTION.RIGHT);
        }
    }

    protected void close() {

    }


    public String getName() {
        return "C" + startPosition;
    }

    public int getUniquePatients() {
        return uniquePatients.size();
    }

    public float getMaxPriorityScore() {
        return maxPriorityScore;
    }

    public float getMinPriorityScore() {
        return minPriorityScore;
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
            return cluster1.getUniquePatients() < cluster2.getUniquePatients() ? 1
                    : cluster1.getUniquePatients() > cluster2.getUniquePatients() ? -1
                    : 0;
        }

    }
}
