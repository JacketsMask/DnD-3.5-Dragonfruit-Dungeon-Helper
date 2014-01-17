package character.feats;

import java.io.Serializable;

/**
 * A feat is a special attribute for a character that improves a character in
 * some way or makes them special.
 *
 * Feats are usually picked at character creation or at certain level-ups.
 *
 * @author Japhez
 */
public class Feat implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    //The reference that contains this feat
    private String reference;
    //The page that contains information for this feat
    private int page;
    //Prerequisites that must be met for this feat to be learned
    private String[] prerequisites;

    public Feat(String name, String description, String reference, int page, String[] prerequisites) {
        this.name = name;
        this.description = description;
        this.reference = reference;
        this.page = page;
        this.prerequisites = prerequisites;
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
