package org.campagnelab.mesca.input;

import java.util.*;

/**
 *
 * Map patient sample names to indexes.
 *
 * @author manuele
 */
public class PatientInfoIndexer {


    private static Map<Integer, PatientInfo> patients = new HashMap<Integer, PatientInfo>();

    /**
     * Indexes a new sample name.
     * @param index
     * @param sampleName
     */
    protected static void add(int index, String sampleName) {
        patients.put(index, new PatientInfo(index, sampleName));
    }

    public static int size() {
        return patients.size();
    }

    public static Collection<PatientInfo> getPatients() {
        return patients.values();
    }

    public static String getSampleName(int index) {
        return (patients.containsKey(index))? patients.get(index).sampleName : "";
    }

    static class PatientInfo {

        protected String sampleName;
        protected int index;
        protected String infoFieldName;

        protected PatientInfo(int index, String sampleName) {
            this.sampleName = sampleName;
            this.index = index;
            this.infoFieldName = String.format("INFO[priority[%s]]",sampleName);
        }
    }
}
