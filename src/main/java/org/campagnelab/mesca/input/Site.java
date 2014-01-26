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


    protected Site(int id) {
        this.ID = id;
    }

    public int getChromosome() {
        return this.chromosome;
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
                ", position=" + position +
                ", chromosome=" + chromosome +
                ", name=" + this.getName() +
                '}';
    }
}