package org.campagnelab.mesca.algorithm;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import org.campagnelab.mesca.input.Site;

/**
 *
 * A decreasing score represents a stop condition for the cluster.
 *
 * @author manuele
 */
public class DecreasingScore extends BaseStopCondition {


    private static final float MAX_PERCENTAGE_DECREASE = 30;

    private final float min_relevant_score;

    public DecreasingScore(float min_relevant_score) {
        super();
        this.min_relevant_score = min_relevant_score;
    }

    public DecreasingScore(float min_relevant_score, int order) {
        super(order);
        this.min_relevant_score = min_relevant_score;
    }

    @Override
    public boolean apply(Cluster cluster, Site[] sites, Cluster.DIRECTION direction) {
        MescaScore mescaScore = new MescaScore(cluster);
        float newScore = mescaScore.calculateWithSites(sites);
        if (cluster.score < newScore || (cluster.getNumOfSites()==0)) {
            return false;
        } else {
            //calculate how big is the decrease in the score and if it is acceptable
            float decrease = (((cluster.score - newScore) * 100)/cluster.score);
            if (decrease > MAX_PERCENTAGE_DECREASE)
                return true;
            else
                return false;
        }
    }

    @Override
    public boolean isRelevant(Cluster cluster) {
        return cluster.score > min_relevant_score;
    }

    /**
     * Provides a string representation of the condition to include in the output report.
     *
     * @return
     */
    @Override
    public String asString() {
        return "Minimum relevant score: " + min_relevant_score;
    }

}
