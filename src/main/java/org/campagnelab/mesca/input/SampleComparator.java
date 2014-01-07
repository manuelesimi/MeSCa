package org.campagnelab.mesca.input;

import org.campagnelab.mesca.list.BaseComparator;

/**
 * Comparator interface for {@link org.campagnelab.mesca.input.Sample}s.
 *
 * @author manuele
 */
public abstract class SampleComparator extends BaseComparator<Sample> {

    protected abstract int compareElement(Sample sample1, Sample sample2);
}
