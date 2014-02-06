package org.campagnelab.mesca.input;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;

/**
 *
 * Map genes to indexes.
 *
 * @author manuele
 */
public class GeneIndexer {

    static Int2ObjectMap<String> customMappings = new Int2ObjectArrayMap<String>();

    static Object2IntMap<String> customReverseMappings = new Object2IntArrayMap<String>();

    static int index = 0;

    public static String decode(int index) {
        if (customMappings.containsKey(index))
            return String.format("%s",customMappings.get(index));
        else
            throw new IllegalArgumentException("Invalid gene index: " + index);
    }

    public static int encode(String gene) {
        if (customReverseMappings.containsKey(gene))
            return customReverseMappings.getInt(gene);
        else {
            ++index;
            customMappings.put(index,gene);
            customReverseMappings.put(gene,index);
            return index;
        }
    }
}