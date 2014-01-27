package org.campagnelab.mesca.input;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

/**
 *
 * Map chromosomes to indexes.
 *
 * @author manuele
 */
public class ChromosomeIndexer {

    static Int2ObjectMap<String> customMappings = new Int2ObjectArrayMap<String>();
    static int index = 25;

    public static String decode(int chromosome) {
        if (chromosome <= 22)
            return String.format("%s",chromosome);
        else if (chromosome == 23)
            return "X";
        else if (chromosome == 24)
            return "Y";
        else if (chromosome == 25)
            return "MT";
        else if (customMappings.containsKey(chromosome))
            return String.format("%s",customMappings.get(chromosome));
        else
            throw new IllegalArgumentException("Invalid chromosome code: " + chromosome);
    }

    public static int encode(String chromosome) {
        try {
            return Integer.valueOf(chromosome);
        }catch (NumberFormatException nfe) {
            if (chromosome.equalsIgnoreCase("X"))
                return 23;
            else if (chromosome.equalsIgnoreCase("Y"))
                return 24;
            else if (chromosome.equalsIgnoreCase("MT"))
                return 25;
            else {
                customMappings.put(++index,chromosome);
                return index;
            }

        }

    }
}
