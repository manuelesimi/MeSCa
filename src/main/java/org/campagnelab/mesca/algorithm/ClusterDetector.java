package org.campagnelab.mesca.algorithm;

import org.apache.log4j.Logger;
import org.campagnelab.mesca.input.Sample;
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

    private final DoublyLinkedList<Sample> sampleList;

    /**
     * @param sampleList the list of samples to clusterize.
     */
    public ClusterDetector(DoublyLinkedList<Sample> sampleList) {
        this.sampleList = sampleList;
    }

    public void addStopCondition(StopCondition stopCondition) {
        this.stopConditions.add(stopCondition);
    }

    /**
     * Detects the clusters in the sampleList.
     * @return
     */
    public ClusterQueue run() {
        Collections.sort(stopConditions);
        ClusterQueue clusters = new ClusterQueue();
        for (int index = 0; index < sampleList.size(); index++) {
            Sample sample = sampleList.get(index);
            //try to build a cluster around the sample
            logger.info("Trying to cluster around position " + sample.getPosition());
            try {
                Cluster cluster = new Cluster(sample.getPosition(), this.stopConditions);
                cluster.addLeftIterator(sampleList.backwardIterator(index));
                cluster.addRightIterator(sampleList.forwardIterator(index));
                cluster.detect();
                if (cluster.isRelevant())
                    clusters.addCluster(cluster);
            } catch (Exception e) {
                logger.error("Failed to create cluster around position " + sample.getPosition(),e);
            }
        }
        return clusters;
    }
}
