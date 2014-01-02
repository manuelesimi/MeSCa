package org.campagnelab.mesca.input;

/**
 * Information about a position in the genome for a patient.
 *
 * @author manuele
 */
public class Sample {

    private int position;
    private int chromosome;
    private float priorityScore;
    private final int ID;

    protected Sample(int id) {this.ID = id;}

    public int getChromosome() {
        return this.chromosome;
    }

    public int getPosition() {
       return this.position;
    }

    protected void setPosition(int position) {
        this.position = position;
    }

    protected void setChromosome(int chromosome) {
        this.chromosome = chromosome;
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

    @Override
    public String toString() {
        return "Sample{" +
                "position=" + position +
                ", chromosome=" + chromosome +
                ", priorityScore=" + priorityScore +
                ", ID=" + ID +
                '}';
    }
}
