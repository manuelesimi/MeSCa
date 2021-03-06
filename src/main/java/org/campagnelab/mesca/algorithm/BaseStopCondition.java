package org.campagnelab.mesca.algorithm;

import org.campagnelab.mesca.input.LinkedSiteList;


/**
 * Base implementation for stop conditions.
 *
 * @author manuele
 */
public abstract class BaseStopCondition implements StopCondition {

    /**
     * The order of the stop condition. The order indicates the execution priority of a
     * condition. Conditions with a lower order are checked before conditions with a higher
     * order. If the order is not set, it is assumed that it is not relevant and it's not
     * guaranteed the order in a condition is checked.
     */
    protected Integer order = 0;

    protected String message = "";

    public BaseStopCondition() {}

    public BaseStopCondition(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public int compareTo(StopCondition stopCondition) {
        //ascending order
        return this.getOrder()- stopCondition.getOrder();
    }
}
