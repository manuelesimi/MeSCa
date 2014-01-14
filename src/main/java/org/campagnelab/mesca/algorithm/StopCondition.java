package org.campagnelab.mesca.algorithm;

/**
 * A stop condition is control to apply to an expanding {@link org.campagnelab.mesca.algorithm.Cluster}.
 * When the condition is verified, the cluster stops to grow in the direction.
 *
 * @author manuele
 */
public interface StopCondition extends Comparable<StopCondition>{

    /**
     * Applies the stop condition to the cluster.
     * @param cluster
     * @return true if the cluster match the condition, false otherwise
     */
    public boolean apply(Cluster cluster);

    /**
     * Gets the message from the last {@link org.campagnelab.mesca.algorithm.StopCondition#apply(Cluster)} invocation.
     * @return the message
     */
    public String getMessage();

    /**
     * The order in which the condition is applied, if more than one condition is specified.
     * @return
     */
    public int getOrder();
}
