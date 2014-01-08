package org.campagnelab.mesca.algorithm;

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
     * Detects the cluster in the sampleList.
     * @return
     */
    public ClusterQueue detect() {
        Collections.sort(stopConditions);
        ClusterQueue clusters = new ClusterQueue();
        for (Sample sample : sampleList) {
            //try to build a cluster around the sample

        }
        return clusters;
    }
}
