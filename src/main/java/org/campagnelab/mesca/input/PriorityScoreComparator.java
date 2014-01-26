package org.campagnelab.mesca.input;

/**
 * Compare samples according to their priority score.
 *
 * @author manuele
 */
public class PriorityScoreComparator extends SiteComparator {

    @Override
    protected int compareElement(Site site1, Site site2) {
        return site1.getPriorityScore() < site2.getPriorityScore() ? -1
                : site1.getPriorityScore() > site2.getPriorityScore() ? 1
                : 0;
    }
}
