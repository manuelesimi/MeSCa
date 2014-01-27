package org.campagnelab.mesca.algorithm;

import org.campagnelab.mesca.input.Site;

/**
 * A stop condition is control to apply to an expanding {@link org.campagnelab.mesca.algorithm.Cluster}.
 * When the condition is verified, the cluster stops to grow in the direction.
 *
 * @author manuele
 */
public interface StopCondition extends Comparable<StopCondition>{

    /**
     * Applies the stop condition to the cluster. When the cluster matches the condition, the
     * expansion in the given direction is halted.
     * @param cluster
     * @param sites the sites that would be added to the cluster if the stop condition does not apply
     * @param direction the direction in which the site would be added
     * @return true if the cluster match the condition, false otherwise.
     */
    public boolean apply(Cluster cluster, Site[] sites, Cluster.DIRECTION direction);

    /**
     * Checks if the cluster is relevant according to the condition.
     * @param cluster
     * @return  true if the cluster is relevant, false otherwise
     */
    public boolean isRelevant(Cluster cluster);

    /**
     * Gets the message from the last {@link org.campagnelab.mesca.algorithm.StopCondition#apply(Cluster, org.campagnelab.mesca.input.Site[], Cluster.DIRECTION)} invocation.
     * @return the message
     */
    public String getMessage();

    /**
     * The order in which the condition is applied, if more than one condition is specified.
     * @return
     */
    public int getOrder();

    /**
     * Provides a string representation of the condition to include in the output report.
     * @return
     */
    public String asString();
}
