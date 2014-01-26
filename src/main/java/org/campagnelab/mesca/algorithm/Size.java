package org.campagnelab.mesca.algorithm;

import org.campagnelab.mesca.input.Site;
import org.campagnelab.mesca.list.DoublyLinkedList;

/**
 * @author manuele
 */
public class Size extends BaseStopCondition {

    private long maxClusterSize;

    public Size(final DoublyLinkedList<Site> siteList) {
        super(siteList);
    }

    public Size(final DoublyLinkedList<Site> siteList, int order) {
        super(siteList, order);
    }

    public void setMaxClusterSize(long maxClusterSize) {
        this.maxClusterSize = maxClusterSize;
    }

    @Override
    public boolean apply(Cluster cluster, Site[] sites, Cluster.DIRECTION direction) {
        //TODO: this is not correct!!
        return (cluster.rightEnd() - cluster.leftEnd()) > this.maxClusterSize;
    }

    @Override
    public boolean isRelevant(Cluster cluster) {
        return this.apply(cluster,null,null);
    }

    /**
     * Provides a string representation of the condition to include in the output report.
     *
     * @return
     */
    @Override
    public String asString() {
        return "Max Cluster Size: " + this.maxClusterSize;
    }
}
