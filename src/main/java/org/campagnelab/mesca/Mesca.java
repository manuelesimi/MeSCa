package org.campagnelab.mesca;

import com.martiansoftware.jsap.JSAPResult;
import org.apache.log4j.Logger;
import org.campagnelab.mesca.algorithm.*;
import org.campagnelab.mesca.input.Site;
import org.campagnelab.mesca.list.DoublyLinkedList;
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
        VCFReader vcfReader = new VCFReader(config.getFile("input-file"));
        DoublyLinkedList<Site> siteList = new DoublyLinkedList<Site>();
        while (vcfReader.hasNextPosition()) {
            try {
                Site[] sites = vcfReader.readNextPosition();
                for (Site site : sites)
                    if (validateSite(site))
                        siteList.add(site);
            } catch (VCFReader.InvalidDataLine idl) {
                idl.printStackTrace();
            }
        }

        logger.info(String.format("%d site(s) have been loaded from the input file.", siteList.size()));

        vcfReader.close();

        ClusterDetector detector = new ClusterDetector(siteList);

        //create stop conditions
        Size size = new Size(siteList);
        size.setMaxClusterSize(10000000);
        detector.addStopCondition(size);
        detector.addStopCondition(new Rank(siteList));

        DetectorWatcher watcher = new DetectorWatcher();
        watcher.recordVCFInputFile(config.getFile("input-file"));
        watcher.addStopCondition(size);
        //invoke ClusterDetector
        ClusterQueue clusters = detector.run();
        logger.info(String.format("%d cluster(s) have been detected.", clusters.size()));

        //print the output
        TSVFormatter formatter = new TSVFormatter();
        //formatter.format(watcher, clusters, config.getFile("output-file"));
        formatter.format(watcher, clusters, System.out);

        logger.info(String.format("Detected cluster(s) are available in %s.", config.getFile("output-file").getAbsolutePath()));

    }

    /**
     * Validates the site.
     * @param site
     * @return
     */
    private static boolean validateSite(Site site) {
        return (site.getPriorityScore() >= 0F);
    }
}
