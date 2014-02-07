package org.campagnelab.mesca.algorithm;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.ints.Int2FloatArrayMap;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import org.apache.log4j.Logger;
import org.campagnelab.mesca.input.ChromosomeIndexer;
import org.campagnelab.mesca.input.GeneIndexer;
import org.campagnelab.mesca.input.Site;

import java.util.*;

/**
 * A cluster of sites in a region matching a set of {@link org.campagnelab.mesca.algorithm.StopCondition}s.
 *
 * @author manuele
 */
public class Cluster {

    protected static final org.apache.log4j.Logger logger = Logger.getLogger(Cluster.class);

    private final String name;

    private int chromosome;

    protected static enum DIRECTION {
        LEFT,
        RIGHT
    }
    /**
     * Number of unique patients in the cluster.
     */
    private Map<String, PatientScore> uniquePatients = new HashMap<String, PatientScore>();

    /**
     * Lowest priority score in the cluster.
     */
    private float minPriorityScore = 0;

    /**
     * Highest priority score in the cluster.
     */
    private float maxPriorityScore = 0;

    /**
     * Record the priority score at each site in the cluster.
     */
    private Int2FloatMap priorityScoreAtSite = new Int2FloatArrayMap();

    protected float totalPriorityScores = 0F;

    /**
     * Stop conditions to apply to each extension's iteration.
     */
    private final List<StopCondition> stopConditions;

    /**
     * Min number of patients in the cluster that make it relevant.
     */
    private static final int MIN_RELEVANT_PATIENTS = 2;   //TODO: will be a parameter in the command line


    /**
     * Max somatic frequency in the cluster that make it relevant.
     */
    public static final float MIN_RELEVANT_SOMATIC_FREQUENCY = 10F;

    /**
     * How many neighboring positions are considered in a direction for each iteration.
     */
    public static final int DEGREE_OF_PROXIMITY = 5;

    /**
     * The most right position in the cluster.
     */
    private long rightEnd;

    /**
     * The most left position in the cluster.
     */
    private long leftEnd;

    private ListIterator<Site> rightListIterator;

    private ListIterator<Site> leftListIterator;

    protected float score = 0F;


    protected Cluster(Site startSite, final List<StopCondition> stopConditions) {
        this.name = "C" + startSite.getID() + startSite.getPosition();
        this.chromosome = startSite.getChromosomeAsInt();
        this.stopConditions = stopConditions;
        if (!uniquePatients.containsKey(startSite.getName()))   {
            uniquePatients.put(startSite.getName(), new PatientScore(startSite.getName(),
                    startSite.getPriorityScore(), startSite.getPosition(),startSite.getSomaticFrequency(),
                    startSite.getGeneAsInt()));
        } else {
            if (uniquePatients.get(startSite.getName()).priorityScore < startSite.getPriorityScore())
                uniquePatients.get(startSite.getName()).priorityScore = startSite.getPriorityScore();
        }
        this.leftEnd = startSite.getPosition();
        this.rightEnd = startSite.getPosition();
        this.maxPriorityScore = startSite.getPriorityScore();
        this.minPriorityScore = startSite.getPriorityScore();
        priorityScoreAtSite.put(startSite.getPosition(), startSite.getPriorityScore());

    }

    /**
     * Gets the position at the left end of the cluster.
     * @return
     */
    public long leftEnd() {
        return leftEnd;
    }

    /**
     * Gets the position at the right end of the cluster.
     * @return
     */
    public long rightEnd() {
        return rightEnd;
    }

    public Set<String> getUniquePatientNames() {
        return Collections.unmodifiableSet(this.uniquePatients.keySet());
    }

    public String[] getSortedUniquePatientNames() {
        List<PatientScore> list = new ArrayList<PatientScore>(this.uniquePatients.values());
        Collections.sort(list);
        String[] sortedList = new String[list.size()];
        int i =0;
        for (PatientScore patientScore : list)
            sortedList[i++] = String.format("%s(%s:%d|%s|%f)",patientScore.name,
                    this.getChromosome(),patientScore.position, GeneIndexer.decode(patientScore.gene),patientScore.somaticFrequency);
        return sortedList;
    }

    public String[] getUniqueGenes() {
        Set<String> genes = new HashSet<String>();
        for (PatientScore patientScore : this.uniquePatients.values()) {
            genes.add(GeneIndexer.decode(patientScore.gene));
        }
        return genes.toArray(new String[0]);
    }


    /**
     * Gets all the priority scores of the samples in the cluster,
     * @return
     */
    protected FloatCollection getAllPriorityScores() {
        return priorityScoreAtSite.values();
    }

    protected int getNumOfSites() {
        return priorityScoreAtSite.values().size();
    }
    /**
     * Adds the sites to the cluster.
     * @param sites
     * @param direction
     */
    private void addSites(Site[] sites, DIRECTION direction) {
        for (Site site : sites) {
            if (site == null)
                continue;
            //calculate if there is a new unique patient
            logger.info(this.name + ": adding site " + site.toString());
            if (!uniquePatients.containsKey(site.getName()))   {
                uniquePatients.put(site.getName(),
                        new PatientScore(site.getName(),
                            site.getPriorityScore(),site.getPosition(),
                                site.getSomaticFrequency(), site.getGeneAsInt()));
            } else {
                PatientScore patientScore = uniquePatients.get(site.getName());
                if (patientScore.priorityScore <= site.getPriorityScore())  {
                    patientScore.priorityScore = site.getPriorityScore();
                    patientScore.position = site.getPosition();
                    patientScore.somaticFrequency = site.getSomaticFrequency();
                    patientScore.gene = site.getGeneAsInt();

                }
            }
            //extend the cluster according to the position
            switch (direction) {
                case LEFT:
                        this.leftEnd = site.getPosition();
                    break;
                case RIGHT:
                        this.rightEnd = site.getPosition();
                    break;
            }

            //record if the site has the lowest or highest priority
            if (site.getPriorityScore() > this.maxPriorityScore)
                this.maxPriorityScore = site.getPriorityScore();
            if (site.getPriorityScore() < this.minPriorityScore)
                this.minPriorityScore = site.getPriorityScore();
            priorityScoreAtSite.put(site.getPosition(), site.getPriorityScore());
            this.totalPriorityScores +=  site.getPriorityScore();
        }
        this.score = new MescaScore(this).calculate();
    }

    /**
     * Iterator to extend the cluster in the right direction.
     * @param siteListIterator
     */
    protected void addRightIterator(ListIterator<Site> siteListIterator) {
        this.rightListIterator = siteListIterator;
    }

    /**
     * Iterator to extend the cluster in the left direction.
     * @param siteListIterator
     */
    protected void addLeftIterator(ListIterator<Site> siteListIterator) {
        this.leftListIterator = siteListIterator;
    }

    protected void detect() {
       boolean haltDetection = false;
       boolean haltRightExpansion = false;
       boolean haltLeftExpansion = false;
       while (! haltDetection) {
           if (!haltRightExpansion)
            haltRightExpansion = addRightNeighboringSites();
           if(! haltLeftExpansion)
            haltLeftExpansion =  addLeftNeighboringSites();
           haltDetection = (haltRightExpansion && haltLeftExpansion);
       }
    }

    /**
     * Tries to add sites at left according to DEGREE_OF_PROXIMITY
     * @return true if the extension in the left direction is completed, false otherwise
     */
    private boolean addLeftNeighboringSites() {
        if (!leftListIterator.hasPrevious())
            return true;
        int sitesInSteps = 0;
        Site[] sites = new Site[DEGREE_OF_PROXIMITY];
        while (leftListIterator.hasPrevious() && sitesInSteps < DEGREE_OF_PROXIMITY)
            sites[sitesInSteps++] = leftListIterator.previous();
        boolean closed = false;
        for (StopCondition condition : this.stopConditions) {
            if (condition.apply(this, sites, DIRECTION.LEFT)) {
                //skip the sites
                closed = true;
                break;
            }
        }
        if (!closed)
            this.addSites(sites, DIRECTION.LEFT);
        return closed;
    }

    /**
     * Tries to add sites at right according to DEGREE_OF_PROXIMITY
     * @return true if the extension in the right direction is closed, false otherwise
     */
    private boolean addRightNeighboringSites() {
        if (!rightListIterator.hasNext())
            return true;
        int sitesInSteps = 0;
        Site[] sites = new Site[DEGREE_OF_PROXIMITY];
        while (rightListIterator.hasNext() && sitesInSteps < DEGREE_OF_PROXIMITY)
            sites[sitesInSteps++] = rightListIterator.next();
        boolean closed = false;
        for (StopCondition condition : this.stopConditions) {
            if (condition.apply(this, sites, DIRECTION.RIGHT)) {
                //skip the sites
                closed = true;
                break;
            }
        }
        if (!closed)
            this.addSites(sites, DIRECTION.RIGHT);
        return closed;
    }

    public boolean hasPatient(String name) {
        return this.uniquePatients.containsKey(name);
    }

    public String getName() {
        return name;
    }

    public int getUniquePatients() {
        return uniquePatients.size();
    }

    public float getMaxPriorityScore() {
        return maxPriorityScore;
    }

    public float getMinPriorityScore() {
        return minPriorityScore;
    }

    public float getScore() {
        return this.score;
    }

    public String getChromosome() {
        return ChromosomeIndexer.decode(chromosome);
    }

    /**
     * Gets the higher somatic frequency among the top sites in the cluster.
     * @return
     */
    public float getTopSomaticFrequency() {
        float topSomaticFrequency = 0F;
        for (PatientScore patientScore : this.uniquePatients.values()) {
            if (patientScore.somaticFrequency > topSomaticFrequency)
                topSomaticFrequency = patientScore.somaticFrequency;
        }

        return topSomaticFrequency;
    }

    /**
     * Gets whether this cluster is relevant or not.
     * A relevant cluster is included in the output queue.
     *
     * @return
     */
    protected boolean isRelevant() {
        //check if the detected cluster is relevant according to the defined stop conditions
        boolean relevant = true;
        for (StopCondition condition : this.stopConditions)
            if (!condition.isRelevant(this)) relevant = false;
        return (relevant
                && this.uniquePatients.size() >= MIN_RELEVANT_PATIENTS
                && this.getTopSomaticFrequency() >= MIN_RELEVANT_SOMATIC_FREQUENCY
                && this.getNumOfSites() > this.uniquePatients.size()); //this makes sure that more than one position is in the cluster
    }

    /**
     * Primary criteria: unique patients
     * Secondary criteria: score
     */
    public static class ClusterComparator implements Comparator<Cluster> {

        @Override
        public int compare(Cluster cluster1, Cluster cluster2) {
            if (cluster1.getUniquePatients() == cluster2.getUniquePatients()) {
                return cluster1.getScore() < cluster2.getScore() ? 1
                        : cluster1.getScore() > cluster2.getScore() ? -1
                        : 0;
            } else
                return cluster1.getUniquePatients() < cluster2.getUniquePatients() ? 1
                    : cluster1.getUniquePatients() > cluster2.getUniquePatients() ? -1
                    : 0;
        }

    }

    class PatientScore implements Comparable<PatientScore> {

        String name;

        float priorityScore;

        long position;

        float somaticFrequency;

        int gene;

        public PatientScore(String name, float priorityScore, int position, float somaticFrequency, int gene) {
            this.name = name;
            this.priorityScore = priorityScore;
            this.position = position;
            this.gene = gene;
            this.somaticFrequency = somaticFrequency;
        }

        @Override
        public int compareTo(PatientScore patient) {
            return this.priorityScore < patient.priorityScore ? 1
                    : this.priorityScore > patient.priorityScore ? -1
                    : 0;        }
    }
}
