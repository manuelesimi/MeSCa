package org.campagnelab.mesca.algorithm;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.campagnelab.mesca.input.LinkedSiteList;
import org.campagnelab.mesca.input.Site;

/**
 * Map a chromosome with its sites.
 *
 * @author manuele
 */
public class SiteChromosomeMap {

    private Int2ObjectMap<LinkedSiteList> map = new Int2ObjectArrayMap<LinkedSiteList>();

    /**
     * Adds a site to the chromosome.
     * @param site
     */
    public void add(Site site) {
        int chromosome = site.getChromosomeAsInt();
        if (!map.containsKey(chromosome)) {
             map.put(chromosome, new LinkedSiteList());
        }
        map.get(chromosome).add(site);
    }


    /**
     * Gets all the site belonging to the chromosome.
     * @param chromosome
     * @return
     */
    public LinkedSiteList getSites(int chromosome) {
        return map.get(chromosome);
    }

    public int[] keySet() {
        return map.keySet().toArray(new int[0]);
    }
}
