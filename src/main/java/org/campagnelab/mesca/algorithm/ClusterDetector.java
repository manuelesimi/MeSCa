package org.campagnelab.mesca.algorithm;

import org.apache.log4j.Logger;
import org.campagnelab.mesca.input.Site;
import org.campagnelab.mesca.list.DoublyLinkedList;

import java.util.*;

/**
 * The cluster detection method.
 * <p/>
 * Given a list of variations in a genome, it clusters them with an adaptive
 * quality-conscious approach according to stop conditions injected in the method.
 *
 * @author manuele
 */
public final class ClusterDetector {

    private static final org.apache.log4j.Logger logger = Logger.getLogger(ClusterDetector.class);

    private List<StopCondition> stopConditions = new ArrayList<StopCondition>();

    private final DoublyLinkedList<Site> siteList;

    /**
     * @param siteList the list of sites to clusterize.
     */
    public ClusterDetector(DoublyLinkedList<Site> siteList) {
        this.siteList = siteList;
    }

    public void addStopCondition(StopCondition stopCondition) {
        this.stopConditions.add(stopCondition);
    }

    /**
     * Detects the clusters in the siteList.
     * @return
     */
    public ClusterQueue run() {
        Collections.sort(stopConditions);
        ClusterQueue clusters = new ClusterQueue();
        for (int index = 0; index < siteList.size(); index++) {
            Site site = siteList.get(index);
            //try to build a cluster around the site
            logger.info("Trying to cluster around position " + site.getPosition());
            try {
                Cluster cluster = new Cluster(site, this.stopConditions);
                cluster.addLeftIterator(siteList.backwardIterator(index));
                cluster.addRightIterator(siteList.forwardIterator(index));
                cluster.detect();
                if (cluster.isRelevant())
                    clusters.addCluster(cluster);
            } catch (Exception e) {
                logger.error("Failed to create cluster around position " + site.getPosition(),e);
            }
        }
        return clusters;
    }
}
