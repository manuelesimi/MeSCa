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

    public Rank(final DoublyLinkedList<Sample> sampleList) {
        super(sampleList);
    }

    public Rank(final DoublyLinkedList<Sample> sampleList, int order) {
        super(sampleList, order);
    }

    @Override
    public boolean apply(Cluster cluster) {
        return false;
    }

}
