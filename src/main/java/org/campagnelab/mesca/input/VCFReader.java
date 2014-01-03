package org.campagnelab.mesca.input;

import edu.cornell.med.icb.goby.readers.vcf.VCFParser;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author manuele
 */
public class VCFReader {

    protected static Logger logger = Logger.getLogger(VCFReader.class);

    private VCFParser parser;

    private List<PatientInfo> patients = new ArrayList<PatientInfo>();

    private static final String chromosomeFieldName = "CHROM";

    private static final String positionFieldName = "POS";

    private int currentChromosome = -1;

    private int currentEndPosition = -1;


    public VCFReader(final File vcfFile) throws IOException {
        try {
            this.createParser(new FileReader(vcfFile));
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }

    }

    private void createParser(FileReader reader) throws IOException, VCFParser.SyntaxException {
        parser = new VCFParser(reader);
        parser.readHeader();
        parser.readTsvColumnTypes();
        int assignedIndex = 0;
        for (int i = 0; i<  parser.getNumberOfColumns(); i++) {
           String name = parser.getColumnName(i);
            if (name.endsWith("blood-patient"))
                this.patients.add(new PatientInfo(assignedIndex++,name));
        }

        /*StringBuilder builder = new StringBuilder();
        for (String patient : this.patients) {
            builder.append("priority\\["+ patient + "\\]=(\\-{0,1}\\d+\\.\\d+);.*");
        }
        System.out.println(builder.toString());
        //builder.append("$");
        this.infoColumnPattern = Pattern.compile(builder.toString());*/
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
     * @return the samples found in the position or null if there is no position (e.g. EOF is reached).
     */
    public Sample[] readNextPosition() throws InvalidDataLine {
        if (parser.hasNextDataLine()) {
            try {
                Sample[] samples = new Sample[this.patients.size()];
                for (PatientInfo patientInfo : this.patients)
                    samples[patientInfo.index] = new Sample(patientInfo.index);
                int chromosome = 0;
                int position = 0;
                for (int i = 0; i < parser.countAllFields(); i++) {
                    final String name = parser.getFieldName(i);
                    if (name.equals(chromosomeFieldName)) {
                        chromosome = Integer.valueOf(parser.getFieldValue(i).toString());
                    } else  if (name.equals(positionFieldName)) {
                        position = Integer.valueOf(parser.getFieldValue(i).toString());
                    } else if (name.startsWith("INFO[priority")) {
                        for (PatientInfo patientInfo : this.patients) {
                            if (name.equals(patientInfo.infoFieldName)) {
                               samples[patientInfo.index].setPriorityScore(Float.valueOf(parser.getFieldValue(i).toString()));
                            }
                        }
                    }
                }
                if (chromosome != currentChromosome) {
                    PositionCodeCalculator.closeChromosome(currentEndPosition);
                    PositionCodeCalculator.openChromosome(chromosome, position);
                    currentChromosome = chromosome;
                }
                for (int s = 0; s < samples.length;s++)
                    samples[s].setPositionCode(PositionCodeCalculator.encodePosition(position));
                currentEndPosition = position;
                return samples;
            } catch (Exception e) {
                logger.error("Invalid data line found. The data line was skipped.", e);
                throw new InvalidDataLine();
            }  finally {
                parser.next();
            }
        }
        return null;
    }

    class PatientInfo {
        protected String sampleName;
        protected int index;
        protected String infoFieldName;

        protected PatientInfo(int index, String sampleName) {
            this.sampleName = sampleName;
            this.index = index;
            this.infoFieldName = String.format("INFO[priority[%s]]",sampleName);
        }
    }


        /**
     * Invalid data line detected.
     */
    public static class InvalidDataLine extends Exception {

        public InvalidDataLine() {
            super();
        }

        public InvalidDataLine(String s) {
            super(s);
        }

        public InvalidDataLine(String s, Throwable throwable) {
            super(s, throwable);
        }

        public InvalidDataLine(Throwable throwable) {
            super(throwable);
        }
    }
}
