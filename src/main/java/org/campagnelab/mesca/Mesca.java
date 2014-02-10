package org.campagnelab.mesca;

import com.martiansoftware.jsap.JSAPResult;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.apache.log4j.Logger;
import org.campagnelab.mesca.algorithm.*;
import org.campagnelab.mesca.input.LinkedSiteList;
import org.campagnelab.mesca.input.Site;
import org.campagnelab.mesca.input.SiteChromosomeMap;
import org.campagnelab.mesca.input.VCFReader;
import org.campagnelab.mesca.output.TSVFormatter;

import java.util.List;

/**
 * Entry point for the command line tool.
 *
 * @author manuele
 */
public class Mesca {

    protected static final org.apache.log4j.Logger logger = Logger.getLogger(Mesca.class);

    private static CommandLineHelper jsapHelper = new CommandLineHelper(Mesca.class) {
        @Override
        protected boolean hasError(JSAPResult config, List<String> errors) {
            return false;
        }
    };


    /**
     * Entry point from the command line.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        try {
            process(args);
            System.exit(0);
        } catch (Exception e) {
            logger.error("Failed to process the request. " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Processes the caller requests.
     *
     * @param args the arguments passed on the command line
     * @throws Exception if the request fails
     */
    public static void process(String[] args) throws Exception {
        JSAPResult config = jsapHelper.configure(args);
        if (config == null)
            System.exit(1);
        DetectorWatcher watcher = new DetectorWatcher();
        watcher.startRecordParser();
        VCFReader vcfReader = new VCFReader(config.getFile("input-file"));
        SiteChromosomeMap siteChromosomeMap = new SiteChromosomeMap();
        int totalSites = 0;
        while (vcfReader.hasNextPosition()) {
            try {
                Site[] sites = vcfReader.readNextPosition();
                for (Site site : sites) {
                    if (site.isRelevant())
                        siteChromosomeMap.add(site);
                }
                totalSites += sites.length;
            } catch (VCFReader.InvalidDataLine idl) {
                idl.printStackTrace();
            }
        }

        //logger.info(String.format("%d site(s) have been loaded from the input file.", siteList.size()));

        vcfReader.close();
        watcher.stopRecordParser();
        watcher.setTotalSitesAnalyzed(totalSites);
        ClusterQueue qclusters = new ClusterQueue(100);
        //create stop conditions
        Size size = new Size(config.getInt("max-cluster-size"));
        //DecreasingScore decreasingScore = new DecreasingScore(1F);
        watcher.recordVCFInputFile(config.getFile("input-file"));
        watcher.addStopCondition(size);
       // watcher.addStopCondition(decreasingScore);
        watcher.setDegreeOfProximity(Cluster.DEGREE_OF_PROXIMITY);
        watcher.setMinPriorityScore(Site.MIN_RELEVANT_PRIORITY_SCORE);
        watcher.setMinSomaticFrequency(config.getFloat("min-relevant-somatic-frequency"));
        watcher.setMaxReturnedClusters(100);

        watcher.startRecordDetector();
        for (int chromosome : siteChromosomeMap.keySet()) {
            LinkedSiteList siteList = siteChromosomeMap.getSites(chromosome);
            ClusterDetector detector = new ClusterDetector(siteList);
            detector.addStopCondition(size);
            detector.setMinSomaticFrequency(config.getFloat("min-relevant-somatic-frequency"));
            //detector.addStopCondition(decreasingScore);
            watcher.setRelevantSites(siteList.size());
            //invoke ClusterDetector
            ObjectArrayList<Cluster> clusters = detector.run();
            logger.info(String.format("%d cluster(s) have been detected for chromosome %s.", clusters.size(), siteList.get(0).getChromosome()));
            qclusters.addClusters(clusters);
        }
        watcher.stopRecordDetector();
        //print the output
        TSVFormatter formatter = new TSVFormatter();
        formatter.format(watcher, qclusters, config.getFile("output-file"));
        //formatter.format(watcher, clusters, System.out);

        logger.info(String.format("Detected cluster(s) are available in %s.", config.getFile("output-file").getAbsolutePath()));

    }

}
