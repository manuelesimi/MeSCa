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
    private final ClusterQueue clusterQueue;


    /**
     * @param sampleList the list of samples to clusterize.
     */
    public ClusterDetector(DoublyLinkedList<Sample> sampleList) {
        this.sampleList = sampleList;
        this.clusterQueue = new ClusterQueue();
    }

    public void addStopCondition(StopCondition stopCondition) {
        this.stopConditions.add(stopCondition);
    }

    /**
     * Detects the cluster in the sampleList.
     * @return
     */
    public ClusterQueue detect() {
        Collections.sort(stopConditions);
        ClusterQueue clusters = new ClusterQueue();
        for (Sample sample : sampleList) {
            //try to build a cluster around the sample
            try {
                Cluster cluster = new Cluster(sample.getPosition());
                cluster.goLeft(sampleList.backwardIterator(sample.getPosition()));
                cluster.goRight(sampleList.forwardIterator(sample.getPosition()));
                cluster.close();
                if (cluster.isRelevant())
                 this.clusterQueue.addCluster(cluster);
            } catch (Exception e) {
                logger.error("Failed to create cluster around position " + sample.getPosition());
            }
        }
        return clusters;
    }
}
