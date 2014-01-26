package org.campagnelab.mesca.algorithm;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.ints.Int2FloatArrayMap;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
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

    private Int2FloatMap priorityScoreAtPosition = new Int2FloatArrayMap();

    /**
     * Stop conditions to apply to each extension's iteration.
     */
    private final List<StopCondition> stopConditions;

    /**
     * Min number of patients in the cluster that make it relevant.
     */
    private static final int MIN_RELEVANT_PATIENTS = 2;   //TODO: will be a parameter in the command line

    /**
     * How many neighboring positions are considered in a direction for each iteration.
     */
    private static final int DEGREE_OF_PROXIMITY = 5;

    /**
     * The most right position in the cluster.
     */
    private long rightEnd;

    /**
     * The most left position in the cluster.
     */
    private long leftEnd;

    private ListIterator<Sample> rightListIterator;

    private ListIterator<Sample> leftListIterator;

    protected float rank = 0F;

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

    public Set<String> getUniquePatientNames() {
        return Collections.unmodifiableSet(this.uniquePatients);
    }

    /**
     * Gets all the priority scores of the samples in the cluster,
     * @return
     */
    protected FloatCollection getAllPriorityScores() {
        return priorityScoreAtPosition.values();
    }

    protected int getNumOfSamples() {
        return priorityScoreAtPosition.values().size();
    }
    /**
     * Adds the samples to the cluster.
     * @param samples
     * @param direction
     */
    private void addSamples(Sample[] samples, DIRECTION direction) {
        for (Sample sample : samples) {
            if (sample == null)
                continue;
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

            priorityScoreAtPosition.put(sample.getPosition(),sample.getPriorityScore());
        }
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
            haltRightExpansion = addRightNeighboringPositions();
           if(! haltLeftExpansion)
            haltLeftExpansion =  addLeftNeighboringPositions();
           haltDetection = (haltRightExpansion && haltLeftExpansion);
       }
    }

    /**
     * Tries to add position at left according to DEGREE_OF_PROXIMITY
     * @return true if the extension in the left direction is completed, false otherwise
     */
    private boolean addLeftNeighboringPositions() {
        if (!leftListIterator.hasPrevious())
            return true;
        int positionInSteps = 0;
        Sample[] samples = new Sample[DEGREE_OF_PROXIMITY];
        while (leftListIterator.hasPrevious() && positionInSteps < DEGREE_OF_PROXIMITY)
            samples[positionInSteps++] = leftListIterator.previous();
        for (StopCondition condition : this.stopConditions) {
            if (condition.apply(this, samples, DIRECTION.LEFT)) {
                //skip the sample
                return true;
            }
        }
        this.addSamples(samples, DIRECTION.LEFT);
        return false;
    }

    /**
     * Tries to add position at right according to DEGREE_OF_PROXIMITY
     * @return true if the extension in the right direction is completed, false otherwise
     */
    private boolean addRightNeighboringPositions() {
        if (!rightListIterator.hasNext())
            return true;
        int positionInSteps = 0;
        Sample[] samples = new Sample[DEGREE_OF_PROXIMITY];
        while (rightListIterator.hasNext() && positionInSteps < DEGREE_OF_PROXIMITY) {
            samples[positionInSteps++] = rightListIterator.next();

        }
        for (StopCondition condition : this.stopConditions) {
            if (condition.apply(this,samples, DIRECTION.RIGHT)) {
                //skip the sample
                return true;
            }
        }
        this.addSamples(samples, DIRECTION.RIGHT);
        return false;
    }

    public boolean hasPatient(String name) {
        return this.uniquePatients.contains(name);
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

    public float getRank() {
        return this.rank;
    }

    /**
     * Gets whether this cluster is relevant or not.
     * A relevant cluster is included in the output queue.
     * @return
     */
    protected boolean isRelevant() {
        //check if the detected cluster is relevant according to the defined stop conditions
        boolean relevant = true;
        for (StopCondition condition : this.stopConditions)
            if (!condition.isRelevant(this)) relevant = false;
        return (relevant && this.uniquePatients.size() >= MIN_RELEVANT_PATIENTS);
    }

    public static class ClusterComparator implements Comparator<Cluster> {

        @Override
        public int compare(Cluster cluster1, Cluster cluster2) {
            //TODO: refine the comparator to consider also the rank of the cluster?
            return cluster1.getUniquePatients() < cluster2.getUniquePatients() ? 1
                    : cluster1.getUniquePatients() > cluster2.getUniquePatients() ? -1
                    : 0;
        }

    }
}
