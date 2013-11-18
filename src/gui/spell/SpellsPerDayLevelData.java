package gui.classes;

import java.io.Serializable;

/**
 *
 * @author Japhez
 */
public class SpellsPerDayLevelData implements Serializable {

    private static final long serialVersionUID = 1L;
    private int level;
    //An array of level data from 0-9, where each element is the number of
    //spells preparable for that index
    private int[] spellsAtLevel;

    public SpellsPerDayLevelData(int level, int[] levelData) {
        this.level = level;
        spellsAtLevel = levelData;
    }

    public int getLevel() {
        return level;
    }

    public int[] getSpellsAtLevel() {
        return spellsAtLevel;
    }
}
