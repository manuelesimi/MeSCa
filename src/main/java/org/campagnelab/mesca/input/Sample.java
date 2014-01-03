package org.campagnelab.mesca.input;

/**
 * Information about a position in the genome for a patient.
 *
 * @author manuele
 */
public class Sample {

    private float priorityScore;

    private final int ID;

    private int positionCode;

    protected Sample(int id) {this.ID = id;}

    public int getChromosome() {
        return PositionCodeCalculator.decodePosition(this.positionCode)[0];
    }

    public int getPosition() {
       return PositionCodeCalculator.decodePosition(this.positionCode)[1];
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


    public void setPositionCode(int positionCode) {
        this.positionCode = positionCode;
    }

    public int getPositionCode() {
        return positionCode;
    }

    @Override
    public String toString() {
            int[] info = PositionCodeCalculator.decodePosition(this.positionCode);
        return "Sample{" +
                "position=" + info[1] +
                ", chromosome=" + info[0] +
                ", priorityScore=" + priorityScore +
                ", ID=" + ID +
                '}';
    }

}
