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
     * Indexes a new patient name.
     * @param index
     * @param name
     */
    protected static void add(int index, String name) {
        patients.put(index, new PatientInfo(index, name));
    }

    public static int size() {
        return patients.size();
    }

    public static Collection<PatientInfo> getPatients() {
        return patients.values();
    }

    public static String getName(int index) {
        return (patients.containsKey(index))? patients.get(index).name : "";
    }

    static class PatientInfo {

        protected String name;
        protected int index;
        protected String infoFieldName;

        protected PatientInfo(int index, String name) {
            this.name = name;
            this.index = index;
            this.infoFieldName = String.format("INFO[priority[%s]]",name);
        }
    }
}
