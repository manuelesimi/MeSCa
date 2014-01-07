package org.campagnelab.mesca.data;

import org.campagnelab.mesca.input.Sample;

/**
 * Comparator for comparing samples according to their priority score.
 *
 * @author manuele
 */
public class PriorityScoreComparator extends SampleComparator{

    @Override
    protected int compare(Sample sample1, Sample sample2) {
        return 0;
    }
}
