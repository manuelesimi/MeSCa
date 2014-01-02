package org.campagnelab.mesca.input;

/**
 * Data line extracted from the VCF file.
 *
 * @author manuele
 */
class DataLine {

    private static final String chromosomeColumnName = "CHROM";

    private static final String positionColumnName = "POS";


    private static enum COLUMN_NAME {

    }

    protected DataLine() {

    }

    /**
     * Gets all the patients' samples at this position.
     * @return
     */
    protected Sample[] getSamples() {

        return null;
    }
}

