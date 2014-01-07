package org.campagnelab.mesca.input;

import org.campagnelab.mesca.data.SampleComparator;
import org.campagnelab.mesca.input.Sample;

/**
 * Comparator for comparing samples according to their priority score.
 *
 * @author manuele
 */
public class PriorityScoreComparator extends SampleComparator {

    @Override
    protected int compareElement(Sample sample1, Sample sample2) {
        return 0;
    }
}
