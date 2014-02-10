package org.campagnelab.mesca.algorithm;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.apache.log4j.Logger;
import org.campagnelab.mesca.input.LinkedSiteList;
import org.campagnelab.mesca.input.Site;

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

    private final LinkedSiteList siteList;
    private float minSomaticFrequency;
    private int degreeOfProximity;

    /**
     * @param siteList the list of sites to clusterize.
     */
    public ClusterDetector(final LinkedSiteList siteList) {
        this.siteList = siteList;
    }

    public void addStopCondition(StopCondition stopCondition) {
        this.stopConditions.add(stopCondition);
    }

    /**
     * Detects the clusters in the siteList.
     * @return
     */
    public ObjectArrayList<Cluster> run() {
        Collections.sort(stopConditions);
        ObjectArrayList<Cluster> clusters = new ObjectArrayList<Cluster>();
        for (int index = 0; index < siteList.size(); index++) {
            Site site = siteList.get(index);
            //try to build a cluster around the site
            logger.info("Trying to cluster around position " + site.getPosition());
            try {
                Cluster cluster = new Cluster(site, this.stopConditions, this.minSomaticFrequency, this.degreeOfProximity);
                cluster.addLeftIterator(siteList.backwardIterator(index));
                cluster.addRightIterator(siteList.forwardIterator(index));
                cluster.detect();
                if (cluster.isRelevant())
                    clusters.add(cluster);
            } catch (Exception e) {
                logger.error("Failed to create cluster around position " + site.getPosition(),e);
            }
        }
        return clusters;
    }

    /**
     *
     * @param minSomaticFrequency
     */
    public void setMinSomaticFrequency(float minSomaticFrequency) {
        this.minSomaticFrequency = minSomaticFrequency;
    }

    /**
     * How many neighboring sites are considered in a direction for each iteration.
     * @param degreeOfProximity
     */
    public void setDegreeOfProximity(int degreeOfProximity) {
        this.degreeOfProximity = degreeOfProximity;
    }


}
