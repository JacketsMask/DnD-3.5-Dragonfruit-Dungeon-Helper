package character.classes;

import java.io.Serializable;

/**
 * Class data that corresponds to a particular level of that class.
 *
 * @author Japhez
 */
public class CharacterClassLevelData implements Serializable {

    private static final long serialVersionUID = 1L;
    private int level;
    private int baseAttackBonus;
    private int fortSave;
    private int refSave;
    private int willSave;
    private String levelNotes;

    public CharacterClassLevelData(int level, int baseAttackBonus, int fortSave, int refSave, int willSave, String levelNotes) {
        this.level = level;
        this.baseAttackBonus = baseAttackBonus;
        this.fortSave = fortSave;
        this.refSave = refSave;
        this.willSave = willSave;
        this.levelNotes = levelNotes;
    }

    public String getLevelNotes() {
        return levelNotes;
    }

    public int getBaseAttackBonus() {
        return baseAttackBonus;
    }

    public int getFortSave() {
        return fortSave;
    }

    public int getLevel() {
        return level;
    }

    public int getRefSave() {
        return refSave;
    }

    public int getWillSave() {
        return willSave;
    }
}
