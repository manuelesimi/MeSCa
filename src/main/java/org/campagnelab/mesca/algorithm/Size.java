package org.campagnelab.mesca.algorithm;

import org.campagnelab.mesca.input.Sample;
import org.campagnelab.mesca.list.DoublyLinkedList;

/**
 * @author manuele
 */
public class Size extends BaseStopCondition {

    private long maxClusterSize;

    public Size(final DoublyLinkedList<Sample> sampleList) {
        super(sampleList);
    }

    public Size(final DoublyLinkedList<Sample> sampleList, int order) {
        super(sampleList, order);
    }

    public void setMaxClusterSize(long maxClusterSize) {
        this.maxClusterSize = maxClusterSize;
    }

    @Override
    public boolean apply(Cluster cluster, Sample sample, Cluster.DIRECTION direction) {
        return (cluster.rightEnd() - cluster.leftEnd()) > this.maxClusterSize;
    }
}
