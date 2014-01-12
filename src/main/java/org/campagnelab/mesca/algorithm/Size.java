package org.campagnelab.mesca.algorithm;

/**
 * @author manuele
 */
public class Size extends BaseStopCondition {

    @Override
    public boolean apply(Cluster cluster) {
        return false;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
