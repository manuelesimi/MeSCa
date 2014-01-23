package org.campagnelab.mesca.algorithm;

import java.io.File;

/**
 * Measure an execution of {@link org.campagnelab.mesca.algorithm.ClusterDetector}
 *
 * @author manuele
 */
public class DetectorWatcher {

    private final long start;

    private String filename;

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
}
