package gui.classes;

/**
 *
 * @author Japhez
 */
public class CharacterLevelData {

    private int level;
    private int[] baseAttackBonus;
    private int fortSave;
    private int refSave;
    private int willSave;
    private String levelNotes;

    public CharacterLevelData(int level, int[] baseAttackBonus, int fortSave, int refSave, int willSave, String levelNotes) {
        this.level = level;
        this.baseAttackBonus = baseAttackBonus;
        this.fortSave = fortSave;
        this.refSave = refSave;
        this.willSave = willSave;
        this.levelNotes = levelNotes;
    }

    public CharacterLevelData(int level, String baseAttackBonus, int fortSave, int refSave, int willSave, String levelNotes) {
        this.level = level;
        String[] split = baseAttackBonus.split("/");
        int[] bab = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            bab[i] = Integer.parseInt(split[i]);
        }
        this.baseAttackBonus = bab;
        this.fortSave = fortSave;
        this.refSave = refSave;
        this.willSave = willSave;
        this.levelNotes = levelNotes;
    }

    public String getLevelNotes() {
        return levelNotes;
    }

    public int[] getBaseAttackBonus() {
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
