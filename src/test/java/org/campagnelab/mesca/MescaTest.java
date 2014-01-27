package org.campagnelab.mesca;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;

/**
 *
 * Tester for the Command line interface.
 *
 * @author manuele
 */
@RunWith(JUnit4.class)
public class MescaTest {

    @BeforeClass
    public static void createResults() {

    }
    @Test
    public void testProcess() throws Exception {
        Mesca.process(new String[] {
           "--input-file", new File("/Users/mas2182/Lab/Projects/FSGS-Laurent/Data/RZAADYE-FSGSR-Somatic-9-3-31exomes-9-Patients-BWAstats.vcf").getAbsolutePath(),
          // "--input-file", new File("test-data/vcf/MLWQHLM-GIR-KAN-BWA-3-15stats-first-500.vcf").getAbsolutePath(),
           "--output-file", new File("test-results/sliced.tsv").getAbsolutePath()
        });
    }
}
