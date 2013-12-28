package org.campagnelab.mesca.algorithm;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * The cluster detection method.
 *
 * Given a list of variations in a genome, it clusters them with an adaptive
 * quality-conscious approach.
 *
 * @author manuele
 */
public final class ClusterDetector {

   SortedSet<StopCondition> stopConditions = new TreeSet<StopCondition>();

   public void addStopCondition(StopCondition stopCondition) {
       this.stopConditions.add(stopCondition);
   }

}
