package org.campagnelab.mesca.input;

import static junit.framework.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by manuelesimi on 1/1/14.
 */
public class VCFReaderTest {

    VCFReader vcf;

    @Before
    public void setUp() throws Exception {
        vcf = new VCFReader(
                new File("test-data/vcf/MLWQHLM-GIR-KAN-BWA-3-15stats-first-500.vcf"));
    }

    @Test
    public void testReadNextPosition() throws Exception {
          Sample[] samples = vcf.readNextPosition();
    }

    @Test
    public void testGetNumOfPatients() throws Exception {
       assertEquals("Unexpected number of patients found", 2, vcf.getNumOfPatients());
    }
}
