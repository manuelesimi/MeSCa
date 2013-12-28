package org.campagnelab.mesca.algorithm;

/**
 *
 * The quality score is a measure of the quality of a cluster.
 * The score is calculated weighting different aspects of the cluster
 * such as: the number of unique patients, the priority score of each position,
 * the size of the cluster and others.
 * When it decreases, a stop condition occurs.
 *
 * @author manuele
 */
public class QualityScore extends BaseStopCondition {

    @Override
    public boolean check() {
        return false;
    }

    @Override
    public String getMessage() {
        return null;
    }

}
