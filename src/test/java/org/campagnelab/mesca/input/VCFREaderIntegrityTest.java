package org.campagnelab.mesca.input;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Checks the integrity and correctness of the data read from the input VCF.
 *
 * @author manuele
 */
@RunWith(JUnit4.class)
public class VCFReaderIntegrityTest {

    static VCFReader vcf;
    static List<Sample> samplesList;

    @BeforeClass
    public static void setUp() throws Exception {
        samplesList = new ArrayList<Sample>();
        vcf = new VCFReader(new File("test-data/vcf/VCFReaderIntegrityInput.vcf"));
        while (vcf.hasNextPosition()) {
            try {
                Sample[] samples = vcf.readNextPosition();
                for (Sample sample : samples)
                    samplesList.add(sample);
            } catch (VCFReader.InvalidDataLine idl) {
                idl.printStackTrace();
            }
        }
        assertEquals("Invalid number of samples found", 12, samplesList.size());
    }

    @Test
    public void testDecodePosition() throws Exception {
       assertEquals(881627, samplesList.get(0).getPosition());
       assertEquals(881627, samplesList.get(1).getPosition());
       assertEquals(883568, samplesList.get(2).getPosition());
       assertEquals(883568, samplesList.get(3).getPosition());
       assertEquals(65868310, samplesList.get(4).getPosition());
       assertEquals(65868310, samplesList.get(5).getPosition());
    }

    @Test
    public void testDecodeChromosome() throws Exception {
        assertEquals(1, samplesList.get(0).getChromosome());
        assertEquals(1, samplesList.get(1).getChromosome());
        assertEquals(1, samplesList.get(2).getChromosome());
        assertEquals(1, samplesList.get(3).getChromosome());
        assertEquals(10, samplesList.get(4).getChromosome());
        assertEquals(10, samplesList.get(5).getChromosome());
    }

    @Test
    public void testDecodeSampleID() throws Exception {
        assertEquals("OCNQNMJ-Qtt1-LM1-29-F-HPN-blood-patient", samplesList.get(0).getName());
        assertEquals("ADSRXBV-Q4c-LM6-33-M-HPN-blood-patient", samplesList.get(1).getName());
        assertEquals("OCNQNMJ-Qtt1-LM1-29-F-HPN-blood-patient", samplesList.get(2).getName());
        assertEquals("ADSRXBV-Q4c-LM6-33-M-HPN-blood-patient", samplesList.get(3).getName());
        assertEquals("OCNQNMJ-Qtt1-LM1-29-F-HPN-blood-patient", samplesList.get(4).getName());
        assertEquals("ADSRXBV-Q4c-LM6-33-M-HPN-blood-patient", samplesList.get(5).getName());
    }

    @Test
    public void testGetNumOfPatients() throws Exception {
        assertEquals("Unexpected number of patients found", 2, vcf.getNumOfPatients());
    }
}
