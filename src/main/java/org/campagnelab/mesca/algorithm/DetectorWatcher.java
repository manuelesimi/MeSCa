package org.campagnelab.mesca.algorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Measure an execution of {@link org.campagnelab.mesca.algorithm.ClusterDetector}
 *
 * @author manuele
 */
public class DetectorWatcher {

    private final long start;

    private String filename;

    private List<StopCondition> stopConditions = new ArrayList<StopCondition>();

    private int degree;
    private int siteSize;

    /**
     * Create a stopwatch object.
     */
    public DetectorWatcher() {
        start = System.currentTimeMillis();
    }

    /**
     * Return elapsed time (in seconds) since this object was created.
     */
    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }

    public void recordVCFInputFile(File file) {
        this.filename = file.getAbsolutePath();
    }

    public String getInputFileName() {
        return this.filename;
    }

    public void addStopCondition(StopCondition condition) {
        this.stopConditions.add(condition);
    }

    public void setDegreeOfProximity(int degree) {
        this.degree = degree;
    }

    public String[] getDescriptionForStopConditions(){
        String[] descriptions = new String[stopConditions.size()];
        for (int i=0; i < stopConditions.size(); i++)
            descriptions[i] = stopConditions.get(i).asString();
        return descriptions;
    }

    public int getDegreeOfProximity() {
        return this.degree;
    }

    public void setSiteSize(int siteSize) {
        this.siteSize = siteSize;
    }

    public int getSiteSize() {
        return siteSize;
    }
}
