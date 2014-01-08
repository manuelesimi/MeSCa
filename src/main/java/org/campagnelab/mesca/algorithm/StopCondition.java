package org.campagnelab.mesca.algorithm;

import java.util.Comparator;

/**
 * @author manuele
 */
public interface StopCondition extends Comparable<StopCondition>{

    public boolean check();

    public String getMessage();

    public void setOrder(int order);

    public int getOrder();
}
