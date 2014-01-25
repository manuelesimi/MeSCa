package org.campagnelab.mesca.algorithm;

import org.campagnelab.mesca.input.Sample;
import org.campagnelab.mesca.list.DoublyLinkedList;

/**
 *
 * The cluster rank is a measure of the quality of a cluster.
 * The rank is calculated weighting different aspects of the cluster
 * such as: the number of unique patients, the priority score of each position,
 * the size of the cluster and others.
 * A decreasing rank represents a stop condition for the cluster.
 *
 * @author manuele
 */
public class Rank extends BaseStopCondition {

    private static final float MIN_RELEVANT_RANK = 1F;

    public Rank(final DoublyLinkedList<Sample> sampleList) {
        super(sampleList);
    }

    public Rank(final DoublyLinkedList<Sample> sampleList, int order) {
        super(sampleList, order);
    }

    @Override
    public boolean apply(Cluster cluster, Sample sample, Cluster.DIRECTION direction) {
        //todo set relevant cluster or not
        float newRank = newRank(cluster,sample);
        if (actualRank(cluster) > newRank) {
           // if (newRank < MIN_RELEVANT_RANK)
           //     cluster.markAsNotRelevant();
            return false;
        }
        return true;
    }
    /**
     * Provides a string representation of the condition to include in the output report.
     *
     * @return
     */
    @Override
    public String asString() {
        return "Rank: ";
    }

    private float actualRank(Cluster cluster) {
        return 0F;
    }

    private float newRank(Cluster cluster, Sample sample) {
        return 0F;
    }
}
