package org.campagnelab.mesca.algorithm;

import org.campagnelab.mesca.input.Sample;
import org.campagnelab.mesca.list.DoublyLinkedList;

/**
 * @author manuele
 */
public class Size extends BaseStopCondition {

    public Size(final DoublyLinkedList<Sample> sampleList) {
        super(sampleList);
    }

    public Size(final DoublyLinkedList<Sample> sampleList, int order) {
        super(sampleList, order);
    }

    @Override
    public boolean apply(Cluster cluster) {
        return false;
    }
}
