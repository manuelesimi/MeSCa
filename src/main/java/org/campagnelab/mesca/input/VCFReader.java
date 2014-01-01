package org.campagnelab.mesca.input;

import edu.cornell.med.icb.goby.readers.vcf.ColumnInfo;
import edu.cornell.med.icb.goby.readers.vcf.VCFParser;
import it.unimi.dsi.fastutil.objects.ObjectIterator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author manuele
 */
public class VCFReader {

    private VCFParser parser;

    private List<String> patients = new ArrayList<String>();

    public VCFReader(final File vcfFile) throws IOException {
        try {
            this.createParser(new FileReader(vcfFile));
        } catch (Exception e) {
            throw new IOException();
        }

    }

    private void createParser(FileReader reader) throws IOException, VCFParser.SyntaxException {
        parser = new VCFParser(reader);
        parser.readHeader();
        parser.readTsvColumnTypes();
        for (int i = 0; i<  parser.getNumberOfColumns(); i++) {
           String name = parser.getColumnName(i);
            if (name.endsWith("blood-patient"))
                this.patients.add(name);
        }

    }

    /**
     * Gets the number of patients in the file.
     * @return the number of patients
     */
    public int getNumOfPatients() {
       return this.patients.size();
    }

    /**
     * Reads the patients' samples at the next position.
     * @return the samples or null if there is no position (e.g. EOF is reached).
     */
    public Sample[] readNextPosition() {
        parser.next();
        if (parser.hasNextDataLine()) {
            final Position position = new Position();
            for (int i = 0; i < parser.countAllFields(); i++) {
                final String name = parser.getFieldName(i);
                System.out.println("name: " + name);
                final String stringFieldValue = parser.getStringFieldValue(i);
                System.out.println("val: " +stringFieldValue);
            }
            return position.getSamples();
        }
        return null;
    }

    private void printSomeData() {
        String samples[] = parser.getColumnNamesUsingFormat();

        while (parser.hasNextDataLine()) {
            for (int i = 0; i < parser.countAllFields(); i++) {
                final String name = parser.getFieldName(i);
                final String stringFieldValue = parser.getStringFieldValue(i);
                System.out.printf("field %s gfi:%d value: %s%n", name, i,
                        stringFieldValue);
                String columnName = samples[0];
                String fieldName = "GT";
                int globalFieldIndex = parser.getGlobalFieldIndex(columnName, fieldName);
                String genotypeValue = parser.getStringFieldValue(globalFieldIndex);
                System.out.println("genotype=" + genotypeValue);
            }
            parser.next();

        }
    }



    private void printSomeInfo() {
        System.out.println("Number of columns in the file: " + parser.getNumberOfColumns());
        ObjectIterator<ColumnInfo> iterator = parser.getColumns().iterator();
        while (iterator.hasNext())
            System.out.println("Column name: " + iterator.next().getColumnName());
    }
}
