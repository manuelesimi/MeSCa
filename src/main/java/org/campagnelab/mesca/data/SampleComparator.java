package org.campagnelab.mesca.data;

import org.campagnelab.mesca.input.Sample;

import java.util.Comparator;

/**
 * Comparator interface for {@link org.campagnelab.mesca.input.Sample}s.
 *
 * @author manuele
 */
public abstract class SampleComparator extends BaseComparator<Sample> {

    protected abstract int compareElement(Sample sample1, Sample sample2);
}
