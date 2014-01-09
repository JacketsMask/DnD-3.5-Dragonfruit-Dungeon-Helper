package character.feats;

/**
 * A feat is a special attribute for a character that improves a character in
 * some way or makes them special.
 * 
 * Feats are usually picked at character creation or at certain level-ups.
 *
 * @author Japhez
 */
public class Feat {

    private String name;
    private String description;
    private String[] prerequisites;

    public Feat(String name, String description, String[] prerequisites) {
        this.name = name;
        this.description = description;
        this.prerequisites = prerequisites;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String[] prerequisites) {
        this.prerequisites = prerequisites;
    }
}
