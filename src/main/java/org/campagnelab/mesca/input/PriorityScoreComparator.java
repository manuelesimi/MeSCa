package org.campagnelab.mesca.input;

/**
 * Compare samples according to their priority score.
 *
 * @author manuele
 */
public class PriorityScoreComparator extends SampleComparator {

    @Override
    protected int compareElement(Sample sample1, Sample sample2) {
        return sample1.getPriorityScore() < sample2.getPriorityScore() ? -1
                : sample1.getPriorityScore() > sample2.getPriorityScore() ? 1
                : 0;
    }
}
