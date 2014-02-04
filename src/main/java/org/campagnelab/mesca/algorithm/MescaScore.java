package org.campagnelab.mesca.algorithm;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import org.campagnelab.mesca.input.Site;

/**
 *
 * The MeSCa score is a measure of the quality of a cluster.
 * The score is calculated weighting different aspects of the cluster
 * such as: the number of unique patients, the priority score of each position,
 * the size of the cluster and others.
 *
 * @author manuele
 */
public class MescaScore {

    private final Operands operands;

    private final Cluster cluster;

    protected MescaScore(Cluster cluster) {
        this.operands = analyzeCluster(cluster);
        this.cluster = cluster;
    }

    /**
     * Calculates the new score as it would be if the sites are added to the cluster.
     * @param sites
     * @return
     */
    protected float calculateWithSites(Site[] sites) {
        for (Site site : sites) {
            if (site == null)
                continue;
            operands.totalPriorityScores += site.getPriorityScore();
            if (operands.leftEnd > site.getPosition())
                operands.leftEnd = site.getPosition();
            if (operands.rightEnd < site.getPosition())
                operands.rightEnd = site.getPosition();
            if (!cluster.hasPatient(site.getName())) {
                operands.uniquePatients++;
            }
        }
        return this.calculate();
    }


    /**
     * Applies the score formula.
     * @return
     */
    protected float calculate() {
        //calculate the score
        return ((operands.totalPriorityScores / (operands.rightEnd - operands.leftEnd))
                * operands.uniquePatients);
    }

    private Operands analyzeCluster(Cluster cluster) {
        Operands operands = new Operands();
        operands.totalPriorityScores = cluster.totalPriorityScores;
        operands.rightEnd = cluster.rightEnd();
        operands.leftEnd = cluster.leftEnd();
        operands.uniquePatients = cluster.getUniquePatients();
        return operands;
    }

    class Operands {
        float totalPriorityScores = 0F;
        float leftEnd = 0F;
        float rightEnd = 0F;
        int uniquePatients = 0;
    }
}
