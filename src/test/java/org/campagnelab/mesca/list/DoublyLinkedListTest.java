package org.campagnelab.mesca.list;

import org.campagnelab.mesca.input.PriorityScoreComparator;
import org.campagnelab.mesca.input.Sample;
import org.campagnelab.mesca.input.VCFReader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

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
        samplesList.sort(new PriorityScoreComparator());
        samplesList.reverse();

    }

    @Test
    public void testSequentialForwardIterator() {
        Iterator<Sample> forwardIterator2 = samplesList.iterator();
        assertEquals(0.20200586F, forwardIterator2.next().getPriorityScore());
        assertEquals(0.050501466F, forwardIterator2.next().getPriorityScore());
        assertEquals(66001418, forwardIterator2.next().getPosition());
        assertEquals(883568, forwardIterator2.next().getPosition());
        assertEquals(881627, forwardIterator2.next().getPosition());
    }

    @Test
    public void testForwardIterator() {
        ListIterator<Sample> forwardIterator = samplesList.forwardIterator(0);
        assertEquals(-0.4496079F,forwardIterator.next().getPriorityScore());
        assertEquals(-3.4028235E38F,forwardIterator.next().getPriorityScore());
        assertEquals(0.050501466F,forwardIterator.next().getPriorityScore());
        assertEquals(65868310,forwardIterator.next().getPosition());
        assertEquals(65868310,forwardIterator.next().getPosition());
        assertEquals(66001418,forwardIterator.next().getPosition());
        assertEquals(66001418,forwardIterator.next().getPosition());


    }

    @Test(expected=NoSuchElementException.class)
    public void testBackwardIterator() throws Exception {
        ListIterator<Sample> backwardIterator = samplesList.backwardIterator(samplesList.size()-1);
        assertEquals(-0.4496079F,backwardIterator.previous().getPriorityScore());
        assertEquals(0.20200586F,backwardIterator.previous().getPriorityScore());
        backwardIterator.previous();
    }

    @Test
    public void testSize() throws Exception {
       assertEquals(12,samplesList.size());
    }
}
