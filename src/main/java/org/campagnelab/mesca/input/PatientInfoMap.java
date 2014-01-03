package org.campagnelab.mesca.input;

import java.util.*;

/**
 * Created by mas2182 on 1/3/14.
 */
public class PatientInfoMap {


    private static Map<Integer, PatientInfo> patients = new HashMap<Integer, PatientInfo>();

    protected static void add (int index, String sampleName) {
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
