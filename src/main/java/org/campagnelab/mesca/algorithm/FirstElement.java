package org.campagnelab.mesca.algorithm;

import org.campagnelab.mesca.input.Sample;
import org.campagnelab.mesca.list.DoublyLinkedList;

/**
 * First element of the sample list is reached.
 *
 * @author manuele
 */
public class FirstElement extends BaseStopCondition {

    public FirstElement(final DoublyLinkedList<Sample> sampleList) {
        super(sampleList);
    }

    public FirstElement(final DoublyLinkedList<Sample> sampleList, int order) {
        super(sampleList,order);
    }

    @Override
    public boolean apply(Cluster cluster, Sample sample, Cluster.DIRECTION direction) {
        return false;
    }
}
