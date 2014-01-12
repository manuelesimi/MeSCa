package org.campagnelab.mesca.algorithm;

import it.unimi.dsi.fastutil.objects.ObjectArrayPriorityQueue;

import java.util.Comparator;

/**
 * The clusters detected by {@link org.campagnelab.mesca.algorithm.ClusterDetector}.
 * Cluster are queued and dequeued according to the given comparator.
 *
 * @author manuele
 */
public class ClusterQueue {

    private ObjectArrayPriorityQueue<Cluster> outputQueue;

    /**
     *
     * @param comparator the comparator used in this queue
     */
    protected ClusterQueue(Comparator<? super Cluster> comparator) {
        outputQueue = new ObjectArrayPriorityQueue<Cluster>(comparator);
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
