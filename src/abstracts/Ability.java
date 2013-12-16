package abstracts;

/**
 * Represents an ability that belongs to a character. It can be passive, active,
 * have a number of limited uses.
 *
 * @author Japhez
 */
public class Ability {

    private String name;
    private String description;
    private int level;
    //The reference that contains this ability
    private String reference;
    //The page that contains information for this ability
    private int page;
    //The number of dice this ability throws
    private int diceNumber;
    //The sides of the dice that are thrown
    private int diceSides;

    public Ability(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getDiceNumber() {
        return diceNumber;
    }

    public void setDiceNumber(int diceNumber) {
        this.diceNumber = diceNumber;
    }

    public int getDiceSides() {
        return diceSides;
    }

    public void setDiceSides(int diceSides) {
        this.diceSides = diceSides;
    }

    /**
     * Attempts to activate the ability if it is activatable.
     */
    public void activate() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }
}
