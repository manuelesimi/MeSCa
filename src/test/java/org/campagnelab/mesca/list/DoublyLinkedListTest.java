package org.campagnelab.mesca.list;

import org.campagnelab.mesca.input.PriorityScoreComparator;
import org.campagnelab.mesca.input.Sample;
import org.campagnelab.mesca.input.VCFReader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static junit.framework.Assert.assertEquals;

/**
 * Created by manuelesimi on 1/8/14.
 */
public class DoublyLinkedListTest {

    static VCFReader vcf;
    static DoublyLinkedList<Sample> samplesList;

    @BeforeClass
    public static void setUp() throws Exception {
        samplesList = new DoublyLinkedList<Sample>();
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
    public void testSort() throws Exception {
        samplesList.sort(new PriorityScoreComparator());
    }

    @Test
    public void testReverse() throws Exception {
       samplesList.reverse();
    }

    @Test
    public void testSequentialForwardIterator() throws Exception {
        Iterator<Sample> forwardIterator2 = samplesList.sequentialForwardIterator();
        while (forwardIterator2.hasNext()) {
            System.out.println("Going forward with sequential iterator: "                    + forwardIterator2.next().toString());
        }
    }

    @Test
    public void testForwardIterator() throws Exception {
        ListIterator<Sample> forwardIterator = samplesList.forwardIterator(0);
        while (forwardIterator.hasNext()) {
            System.out.println("Going forward: " + forwardIterator.next().toString());
        }

    }

    @Test
    public void testBackwardIterator() throws Exception {
        ListIterator<Sample> backwardIterator = samplesList.backwardIterator(samplesList.size()-1);
        while (backwardIterator.hasPrevious()) {
            System.out.println("Going backward: " + backwardIterator.previous().toString());
        }
    }

    @Test
    public void testSize() throws Exception {

    }
}
