package org.campagnelab.mesca.algorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Watch an execution of the detection algorithm.
 *
 * @author manuele
 */
public class DetectorWatcher {

    private long detectorStart;

    private double detectorElapsed;

    private long parserStart;

    private String filename;

    private List<StopCondition> stopConditions = new ArrayList<StopCondition>();

    private int degree;
    private int totalSites;

    private double parserElapsed;
    private int totalSitesAnalyzed;
    private float minPriorityScore;
    private float minSomaticFrequency;
    private int maxReturnedClusters;

    /**
     * Create a stopwatch object.
     */
    public DetectorWatcher() {
    }

    public void startRecordParser() {
        parserStart = System.currentTimeMillis();
    }

    public void stopRecordParser() {
        long now = System.currentTimeMillis();
        parserElapsed = (now - parserStart) / 1000.0;
    }

    public void startRecordDetector() {
        detectorStart = System.currentTimeMillis();
    }

    public void stopRecordDetector() {
        long now = System.currentTimeMillis();
        detectorElapsed = (now - detectorStart) / 1000.0;
    }
    /**
     * Return elapsed time (in seconds) that took to run the algorithm.
     */
    public double detectorElapsedTime() {
        return this.detectorElapsed;
    }

    /**
     * Return elapsed time (in seconds) that took to parse the input file.
     */
    public double parserElapsedTime() {
        return this.parserElapsed;
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

    public void setRelevantSites(int siteSize) {
        this.totalSites += siteSize;
    }

    public int getNumOfSites() {
        return totalSites;
    }

    public void setTotalSitesAnalyzed(int totalSitesAnalyzed) {
        this.totalSitesAnalyzed = totalSitesAnalyzed;
    }

    public int getTotalSitesAnalyzed() {
        return totalSitesAnalyzed;
    }

    public float getMinPriorityScore() {
        return minPriorityScore;
    }

    public void setMinPriorityScore(float minPriorityScore) {
        this.minPriorityScore = minPriorityScore;
    }

    public void setMinSomaticFrequency(float minSomaticFrequency) {
        this.minSomaticFrequency = minSomaticFrequency;
    }

    public float getMinSomaticFrequency() {
        return minSomaticFrequency;
    }

    public void setMaxReturnedClusters(int maxReturnedClusters) {
        this.maxReturnedClusters = maxReturnedClusters;
    }

    public int getMaxReturnedClusters() {
        return maxReturnedClusters;
    }
}
