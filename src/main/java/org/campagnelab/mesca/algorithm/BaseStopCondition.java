package org.campagnelab.mesca.algorithm;

/**
 * Created by manuelesimi on 12/27/13.
 */
public abstract class BaseStopCondition implements StopCondition {

    /**
     * The order of the stop condition. The order indicates the execution priority of a
     * condition. Conditions with a lower order are checked before conditions with a higher
     * order. If the order is not set, it is assumed that it is not relevant and it's not
     * guaranteed the order in a condition is checked.
     */
    protected Integer order = 0;

    @Override
    public void setOrder(int order) {
       this.order = order;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public int compare(Integer order1, Integer order2) {
        return order1.compareTo(order2);
    }
}
