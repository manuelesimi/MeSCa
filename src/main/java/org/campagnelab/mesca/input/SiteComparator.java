package org.campagnelab.mesca.input;

import org.campagnelab.mesca.list.BaseComparator;

/**
 * Base comparator for {@link Site}s.
 *
 * @author manuele
 */
public abstract class SiteComparator extends BaseComparator<Site> {

    protected abstract int compareElement(Site site1, Site site2);
}
