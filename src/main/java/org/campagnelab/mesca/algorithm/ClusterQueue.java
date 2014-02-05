package org.campagnelab.mesca.algorithm;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArrayPriorityQueue;

import java.util.Comparator;

/**
 * The clusters detected by {@link org.campagnelab.mesca.algorithm.ClusterDetector}.
 * Cluster are queued and dequeued according to Cluster.ClusterComparator().
 *
 * @author manuele
 */
public class ClusterQueue {

    private ObjectArrayPriorityQueue<Cluster> outputQueue;

    private final int TOP_CLUSTERS_RETURNED = 100;

    /**
     *
     */
    public ClusterQueue() {
        outputQueue = new ObjectArrayPriorityQueue<Cluster>(TOP_CLUSTERS_RETURNED,new Cluster.ClusterComparator());
    }

    /**
     * Enqueues a cluster.
     *
     * @param cluster
     */
    public void addCluster(Cluster cluster) {
       outputQueue.enqueue(cluster);
    }

    /**
     * Enqueues a list of clusters.
     *
     * @param clusters
     */
    public void addClusters(ObjectArrayList<Cluster> clusters) {
        for (Cluster cluster : clusters)
            this.addCluster(cluster);
    }

    /**
     * Dequeues the first cluster from the queue.
     * @return
     */
    public Cluster getCluster() {
        return outputQueue.dequeue();
    }

    /**
     * Returns the number of clusters in this queue.
     * @return
     */
    public int size() {
        return outputQueue.size();
    }
}
