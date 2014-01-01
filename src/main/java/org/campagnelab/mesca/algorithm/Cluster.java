package org.campagnelab.mesca.algorithm;

/**
 * A cluster of ....
 *
 * @author manuele
 */
class Cluster {

    /**
     * Number of unique patients in the cluster.
     */
    private int uniquePatients = 0;

    /**
     * Lowest priority score in the cluster.
     */
    private int lowestPS;

    /**
     * Highest priority score in the cluster.
     */
    private int highestPS;


    private long startPosition;

    private long lowestPosition;

    private long highestPosition;


    protected Cluster(long startPosition) {
      this.startPosition = startPosition;
    }

    protected boolean goLeft() {
        return false;
    }

    protected boolean goRight() {
        return false;
    }

    protected void close() {

    }
}
