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
           "--input-file", new File("test-data/vcf/MLWQHLM-GIR-KAN-BWA-3-15stats-first-500.vcf").getAbsolutePath(),
           "--min-priority-score", "0",
           "--degree-of-proximity", "5",
           "--max-cluster-size", "1000",
           "--min-relevant-somatic-frequency", "0",
           "--output-file", new File("test-results/MLWQHLM-clusters.tsv").getAbsolutePath()
        });
    }


    @Test
    public void testCompleteProcess() throws Exception {
        Mesca.process(new String[] {
                //"--input-file", new File("/Users/mas2182/temp/MFWSYOQ-stats.vcf").getAbsolutePath(),
                "--input-file", new File("test-data/vcf/MFWSYOQ-stats-sliced.vcf").getAbsolutePath(),
                "--min-priority-score", "10", "--degree-of-proximity", "5", "--max-cluster-size", "100000", "--min-relevant-somatic-frequency", "5",
                "--min-unique-patients", "1",
                "--output-file", new File("test-results/MFWSYOQ.tsv").getAbsolutePath()
        });
    }
}
