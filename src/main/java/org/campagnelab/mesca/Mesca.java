package org.campagnelab.mesca;

import com.martiansoftware.jsap.JSAPResult;
import org.apache.log4j.Logger;
import org.campagnelab.mesca.input.PriorityScoreComparator;
import org.campagnelab.mesca.list.DoublyLinkedList;
import org.campagnelab.mesca.input.Sample;
import org.campagnelab.mesca.input.VCFReader;

import java.util.List;
import java.util.ListIterator;

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
        DoublyLinkedList<Sample> sampleList = new DoublyLinkedList<Sample>();
        while (vcfReader.hasNextPosition()) {
            try {
                Sample[] samples = vcfReader.readNextPosition();
                for (Sample sample : samples)
                    sampleList.add(sample);
            } catch (VCFReader.InvalidDataLine idl) {
                idl.printStackTrace();
            }
        }

        logger.info(String.format("%d sample(s) have been loaded from the input file.", sampleList.size()));

        vcfReader.close();

        sampleList.shuffle();

        //sample iterators testing, TODO to remove
        ListIterator<Sample> backwardIterator = sampleList.backwardIterator(sampleList.size()-1);
        while (backwardIterator.hasPrevious()) {
            logger.info("Going backward: " + backwardIterator.previous().toString());
        }

        ListIterator<Sample> forwardIterator = sampleList.forwardIterator(0);
        while (forwardIterator.hasNext()) {
            logger.info("Going forward: " + forwardIterator.next().toString());
        }
        sampleList.sort(new PriorityScoreComparator());

        ListIterator<Sample> forwardIterator2 = sampleList.iterator();
        while (forwardIterator2.hasNext()) {
            logger.info("Going forward2: " + forwardIterator2.next().toString());
        }

    }
}
