package org.campagnelab.mesca.input;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

/**
 *
 * Map genes to indexes.
 *
 * @author manuele
 */
public class GeneIndexer {

    static Int2ObjectMap<String> customMappings = new Int2ObjectArrayMap<String>();
    static int index = 0;

    public static String decode(int index) {
        if (customMappings.containsKey(index))
            return String.format("%s",customMappings.get(index));
        else
            throw new IllegalArgumentException("Invalid gene index: " + index);

    }

    public static int encode(String gene) {
        customMappings.put(++index,gene);
        return index;
    }
}