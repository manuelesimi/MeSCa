package org.campagnelab.mesca.input;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

/**
 *
 * Map chromosomes to indexes.
 *
 * @author manuele
 */
public class ChromosomeIndexer {

    static Int2ObjectMap<String> customMappings = new Int2ObjectArrayMap<String>();

    static Object2IntMap<String> customReverseMappings = new Object2IntArrayMap<String>();
    static int index = 25;

    public static String decode(int index) {
        if (index <= 22)
            return String.format("%s",index);
        else if (index == 23)
            return "X";
        else if (index == 24)
            return "Y";
        else if (index == 25)
            return "MT";
        else if (customMappings.containsKey(index))
            return String.format("%s",customMappings.get(index));
        else
            throw new IllegalArgumentException("Invalid chromosome index: " + index);
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
                if (customReverseMappings.containsKey(chromosome))
                    return customReverseMappings.getInt(chromosome);
                else {
                    ++index;
                    customMappings.put(index,chromosome);
                    customReverseMappings.put(chromosome,index);
                    return index;
                }
            }

        }

    }
}
