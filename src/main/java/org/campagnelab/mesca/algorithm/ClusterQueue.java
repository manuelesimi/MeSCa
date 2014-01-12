package org.campagnelab.mesca.algorithm;

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

    /**
     *
     */
    protected ClusterQueue() {
        outputQueue = new ObjectArrayPriorityQueue<Cluster>(new Cluster.ClusterComparator());
    }

    /**
     * Enqueues a new cluster.
     *
     * @param cluster
     */
    protected void addCluster(Cluster cluster) {
       outputQueue.enqueue(cluster);
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
