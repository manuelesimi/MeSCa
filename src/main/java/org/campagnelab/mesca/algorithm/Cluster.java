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

    /**
     * Min number of patients in the cluster that make it relevant.
     */
    private static final int MIN_RELEVANT_PATIENTS = 2;

    private long rightEnd;

    private long leftEnd;

    private ListIterator<Sample> rightListIterator;

    private ListIterator<Sample> leftListIterator;

    private boolean relevant = true;

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
     * Iterator to extend the cluster in the right direction.
     * @param sampleListIterator
     */
    protected void addRightIterator(ListIterator<Sample> sampleListIterator) {
        this.rightListIterator = sampleListIterator;
    }

    /**
     * Iterator to extend the cluster in the left direction.
     * @param sampleListIterator
     */
    protected void addLeftIterator(ListIterator<Sample> sampleListIterator) {
        this.leftListIterator = sampleListIterator;
    }

    protected void detect() {
       boolean haltDetection = false;
       boolean haltRightExpansion = false;
       boolean haltLeftExpansion = false;
       while (! haltDetection) {
           if (!haltRightExpansion)
            haltRightExpansion = addNextRightPosition();
           if(! haltLeftExpansion)
            haltLeftExpansion =  addNextLeftPosition();
           haltDetection = (haltRightExpansion && haltLeftExpansion);
       }

    }

    private boolean addNextLeftPosition() {
        if (!leftListIterator.hasPrevious())
            return true;
        Sample previous = leftListIterator.previous();
        for (StopCondition condition : this.stopConditions) {
            if (condition.apply(this,previous, DIRECTION.LEFT)) {
                //skip the sample
                return true;
            }
        }
        this.addSample(previous, DIRECTION.LEFT);
        return false;
    }

    private boolean addNextRightPosition() {
        if (!rightListIterator.hasNext())
            return true;
        Sample next = rightListIterator.next();
        for (StopCondition condition : this.stopConditions) {
            if (condition.apply(this,next, DIRECTION.RIGHT)) {
                //skip the sample
                return true;
            }
        }
        this.addSample(next, DIRECTION.RIGHT);
        return false;
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
     * Gets whether this cluster is relevant or not.
     * A relevant cluster is included in the output queue.
     * @return
     */
    protected boolean isRelevant() {
        return (this.relevant && this.uniquePatients.size() >= MIN_RELEVANT_PATIENTS);
    }

    protected void markAsNotRelevant() {
      this.relevant = false;
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
