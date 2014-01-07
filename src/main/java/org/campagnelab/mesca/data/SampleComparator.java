package org.campagnelab.mesca.data;

import org.campagnelab.mesca.input.Sample;

import java.util.Comparator;

/**
 * Comparator interface for {@link org.campagnelab.mesca.input.Sample}s.
 *
 * @author manuele
 */
public abstract class SampleComparator implements Comparator<Node<Sample>> {

    @Override
    public int compare(Node<Sample> sampleNode, Node<Sample> sampleNode2) {
        return this.compare(sampleNode.element, sampleNode2.element);
    }

    protected abstract int compare(Sample sample1, Sample sample2);
}
