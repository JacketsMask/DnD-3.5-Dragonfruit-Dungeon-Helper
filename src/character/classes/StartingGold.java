package character.classes;

/**
 * Holds starting gold statistics for a class.
 * @author Jacob Dorman
 */
public class StartingGold {

    private int numDice;
    private int numSides;
    private int multiplier;

    public StartingGold(int numDice, int numSides, int multiplier) {
        this.numDice = numDice;
        this.numSides = numSides;
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public int getNumDice() {
        return numDice;
    }

    public int getNumSides() {
        return numSides;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public void setNumDice(int numDice) {
        this.numDice = numDice;
    }

    public void setNumSides(int numSides) {
        this.numSides = numSides;
    }
}
