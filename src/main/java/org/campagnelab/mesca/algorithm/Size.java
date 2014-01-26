package org.campagnelab.mesca.algorithm;

import org.campagnelab.mesca.input.Site;
import org.campagnelab.mesca.list.DoublyLinkedList;

/**
 * @author manuele
 */
public class Size extends BaseStopCondition {

    private final long maxClusterSize;

    public Size(final DoublyLinkedList<Site> siteList,long maxClusterSize) {
        super(siteList);
        this.maxClusterSize = maxClusterSize;

    }

    public Size(final DoublyLinkedList<Site> siteList, long maxClusterSize, int order) {
        super(siteList, order);
        this.maxClusterSize = maxClusterSize;

    }


    @Override
    public boolean apply(Cluster cluster, Site[] sites, Cluster.DIRECTION direction) {
        float leftEnd = cluster.leftEnd();
        float rightEnd = cluster.rightEnd();
        for (Site site : sites) {
            if (site == null)
                continue;
            if (leftEnd > site.getPosition())
                leftEnd = site.getPosition();
            if (rightEnd < site.getPosition())
                rightEnd = site.getPosition();
        }
        return ((rightEnd - leftEnd) > this.maxClusterSize);
    }

    @Override
    public boolean isRelevant(Cluster cluster) {
        return ((cluster.rightEnd() - cluster.leftEnd()) < this.maxClusterSize);
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
