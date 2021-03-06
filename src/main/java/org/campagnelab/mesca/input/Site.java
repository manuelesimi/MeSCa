package org.campagnelab.mesca.input;

/**
 * Information about a position in the genome for a patient.
 *
 * @author manuele
 */
public class Site {

    private float priorityScore;

    private final int ID;

    private int position;

    private int chromosome;

    private int gene;

    private float somaticFrequency;

    protected Site(int id) {
        this.ID = id;
    }

    public String getChromosome() {
        return ChromosomeIndexer.decode(this.chromosome);
    }

    public int getChromosomeAsInt() {
        return this.chromosome;
    }

    protected void setChromosome(String chromosome) {
        this.chromosome = ChromosomeIndexer.encode(chromosome);
    }

    protected void setPriorityScore(float priorityScore) {
        this.priorityScore = priorityScore;
    }

    public float getPriorityScore() {
        return priorityScore;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return PatientInfoIndexer.getName(ID);
    }

    protected void setPosition(int position) {


        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }


    @Override
    public String toString() {
        return "Site{" +
                "priorityScore=" + priorityScore +
                ", ID=" + ID +
                ", chromosome=" + this.getChromosome() +
                ", position=" + position +
                ", somatic frequency=" + somaticFrequency +
                ", gene=" + this.getGene() +
                ", name=" + this.getName() +
                '}';
    }

    public void setGene(String gene) {
        this.gene = GeneIndexer.encode(gene);
    }

    public String getGene() {
        return GeneIndexer.decode(this.gene);
    }

    public int getGeneAsInt() {
        return this.gene;
    }

    public void setSomaticFrequency(float somaticFrequency) {
        this.somaticFrequency = somaticFrequency;
    }

    public float getSomaticFrequency() {
        return somaticFrequency;
    }
}
