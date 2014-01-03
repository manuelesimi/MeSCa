package org.campagnelab.mesca.input;

import java.util.ArrayList;
import java.util.List;

/**
 * Encode/decode a pair (chromosome, position) to/from a single value.
 *
 * @author manuele
 */
public class PositionCodeCalculator {

    static final List<Integer> chromosomePositionOffsets = new ArrayList<Integer>();

    static final List<Integer> chromosomeStartPositions = new ArrayList<Integer>();

    static final List<Integer> chromosomes = new ArrayList<Integer>();

    protected static void closeChromosome(final int endPosition) {
       if (chromosomes.size() == 0)
           return;
       int length = endPosition - chromosomeStartPositions.get(chromosomeStartPositions.size()-1);
       if (length <= 0)
            throw new IllegalArgumentException("Invalid end position: it is smaller than the recorded start position.");
       //record the offset for the next chromosome, if any
       chromosomePositionOffsets.add(
         length + chromosomePositionOffsets.get(chromosomePositionOffsets.size()-1));

    }

    protected static void openChromosome(final int chromosome, final int startPosition) {
        chromosomeStartPositions.add(startPosition);
        chromosomes.add(chromosome);
        if (chromosomeStartPositions.size() ==1) {
            chromosomePositionOffsets.add(0);
        }
    }

    /**
     * Calculates the coding offset for the position.
     * @param position
     * @return the position code
     */
    public static int encodePosition(final int position) {
        return (position - chromosomeStartPositions.get(chromosomeStartPositions.size()-1))
                + chromosomePositionOffsets.get(chromosomePositionOffsets.size()-1);
    }


    /**
     * Calculates the chromosome and the absolute position of a position code.
     * @param positionCode
     * @return an array of two elements: the first one is the chromosome and the second one is the absolute position.
     */
    public static int[] decodePosition(final int positionCode) {
        for (int index = chromosomePositionOffsets.size()-1; index >=0; index--) {
           if (positionCode >= chromosomePositionOffsets.get(index)) {
               return new int[] {
                       chromosomes.get(index), // chromosome
                       chromosomeStartPositions.get(index) + (positionCode - chromosomePositionOffsets.get(index))}; //abs position
           }
        }

        throw new IllegalArgumentException("Invalid position code.");
    }

}
