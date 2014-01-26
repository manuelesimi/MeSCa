package org.campagnelab.mesca.algorithm;
;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import org.campagnelab.mesca.input.Site;
import org.campagnelab.mesca.list.DoublyLinkedList;

/**
 *
 * The cluster rank is a measure of the quality of a cluster.
 * The rank is calculated weighting different aspects of the cluster
 * such as: the number of unique patients, the priority score of each position,
 * the size of the cluster and others.
 * A decreasing rank represents a stop condition for the cluster.
 *
 * @author manuele
 */
public class Rank extends BaseStopCondition {

    private static final float MIN_RELEVANT_RANK = 1F;

    public Rank(final DoublyLinkedList<Site> siteList) {
        super(siteList);
    }

    public Rank(final DoublyLinkedList<Site> siteList, int order) {
        super(siteList, order);
    }

    @Override
    public boolean apply(Cluster cluster, Site[] sites, Cluster.DIRECTION direction) {
        Operands operands = analyzeCluster(cluster);
        float newRank = calculateNewRank(operands,cluster, sites);
        if (cluster.rank < newRank || cluster.getNumOfSites()==0) {
            cluster.rank = newRank;
            return false;
        }
        return true;
    }

    @Override
    public boolean isRelevant(Cluster cluster) {
        return true;//this.calculateRank(this.analyzeCluster(cluster)) > MIN_RELEVANT_RANK;
    }

    private float calculateNewRank(Operands operands, Cluster cluster, Site[] sites) {
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
       return this.calculateRank(operands);
    }

    /**
     * Provides a string representation of the condition to include in the output report.
     *
     * @return
     */
    @Override
    public String asString() {
        return "Rank: ";
    }

    /**
     * Applies the rank formula.
     * @param operands
     * @return
     */
    private float calculateRank(final Operands operands) {
        //calculate the rank
        return ((operands.totalPriorityScores / (operands.rightEnd - operands.leftEnd))
                * operands.uniquePatients);
    }

    private Operands analyzeCluster(Cluster cluster) {
        Operands operands = new Operands();
        //sum all the priority scores in the cluster
        FloatCollection collection = cluster.getAllPriorityScores();
        FloatIterator iterator = collection.iterator();
        while (iterator.hasNext())
            operands.totalPriorityScores += iterator.nextFloat();

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
