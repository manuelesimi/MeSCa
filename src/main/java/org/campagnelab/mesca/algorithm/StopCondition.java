package org.campagnelab.mesca.algorithm;

/**
 * @author manuele
 */
public interface StopCondition extends Comparable<StopCondition>{

    /**
     * Applies the stop condition to the cluster.
     * @param cluster
     * @return true if the cluster match the condition, false otherwise
     */
    public boolean apply(Cluster cluster);

    public String getMessage();

    public void setOrder(int order);

    public int getOrder();
}
