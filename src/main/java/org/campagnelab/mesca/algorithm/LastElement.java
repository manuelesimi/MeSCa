package org.campagnelab.mesca.algorithm;

import org.campagnelab.mesca.input.Sample;
import org.campagnelab.mesca.list.DoublyLinkedList;


/**
 * Last element of the sample list is reached.
 *
 * @author manuele
 */
public class LastElement extends BaseStopCondition {

    public LastElement(final DoublyLinkedList<Sample> sampleList) {
        super(sampleList);
    }

    public LastElement(final DoublyLinkedList<Sample> sampleList, int order) {
        super(sampleList, order);
    }

    @Override
    public boolean apply(Cluster cluster) {
        return false;
    }

}
