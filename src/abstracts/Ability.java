package abstracts;

/**
 * Represents an ability that belongs to a character. It can be passive, active,
 * have a number of limited uses.
 *
 * @author Japhez
 */
public abstract class Ability {

    protected String name;
    protected String description;

    public Ability(String name, String description) {
        this.name = name;
        this.description = description;
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
