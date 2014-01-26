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
    static List<Site> siteList;

    @BeforeClass
    public static void setUp() throws Exception {
        siteList = new ArrayList<Site>();
        vcf = new VCFReader(new File("test-data/vcf/VCFReaderIntegrityInput.vcf"));
        while (vcf.hasNextPosition()) {
            try {
                Site[] sites = vcf.readNextPosition();
                for (Site site : sites)
                    siteList.add(site);
            } catch (VCFReader.InvalidDataLine idl) {
                idl.printStackTrace();
            }
        }
        assertEquals("Invalid number of samples found", 12, siteList.size());
    }

    @Test
    public void testDecodePosition() throws Exception {
       assertEquals(881627, siteList.get(0).getPosition());
       assertEquals(881627, siteList.get(1).getPosition());
       assertEquals(883568, siteList.get(2).getPosition());
       assertEquals(883568, siteList.get(3).getPosition());
       assertEquals(65868310, siteList.get(4).getPosition());
       assertEquals(65868310, siteList.get(5).getPosition());
    }

    @Test
    public void testDecodeChromosome() throws Exception {
        assertEquals(1, siteList.get(0).getChromosome());
        assertEquals(1, siteList.get(1).getChromosome());
        assertEquals(1, siteList.get(2).getChromosome());
        assertEquals(1, siteList.get(3).getChromosome());
        assertEquals(10, siteList.get(4).getChromosome());
        assertEquals(10, siteList.get(5).getChromosome());
    }

    @Test
    public void testDecodeSiteID() throws Exception {
        assertEquals("OCNQNMJ-Qtt1-LM1-29-F-HPN-blood-patient", siteList.get(0).getName());
        assertEquals("ADSRXBV-Q4c-LM6-33-M-HPN-blood-patient", siteList.get(1).getName());
        assertEquals("OCNQNMJ-Qtt1-LM1-29-F-HPN-blood-patient", siteList.get(2).getName());
        assertEquals("ADSRXBV-Q4c-LM6-33-M-HPN-blood-patient", siteList.get(3).getName());
        assertEquals("OCNQNMJ-Qtt1-LM1-29-F-HPN-blood-patient", siteList.get(4).getName());
        assertEquals("ADSRXBV-Q4c-LM6-33-M-HPN-blood-patient", siteList.get(5).getName());
    }

    @Test
    public void testGetNumOfPatients() throws Exception {
        assertEquals("Unexpected number of patients found", 2, vcf.getNumOfPatients());
    }
}
