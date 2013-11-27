package character.classes;

import character.proficiencies.WeaponProficiency;
import character.proficiencies.ArmorProficiency;
import enumerations.*;
import gui.classes.CharacterClassLevelData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A character class containing most of the information pertaining to a class.
 *
 * Information here should only be changed within a class editor, and never
 * within the main program.
 *
 * @author Jacob Dorman
 */
public class CharacterClass implements Serializable {

    private static final long serialVersionUID = 1L;
    //The name of this class
    protected String name;
    //Hit die
    protected int hitDie;
    //The class skills of that this class provides
    protected Skill[] classSkills;
    //Armor proficiencies
    protected ArmorProficiency[] armorProficiencies;
    //Weapon proficiencies
    protected WeaponProficiency[] weaponProficiencies;
    //Class related alignment restrictions
    protected ArrayList<Alignment> restrictedAlignments;
    //Starting gold
    protected StartingGold startingGold;
    //Caster type
    protected CasterType casterType;
    //Whether or not this class uses abilities
    protected boolean usesAbilities;
    //Starting skill ranks
    protected int initialSkillRankModifier;
    protected int skillRankModifier;
    //Class data for each level
    protected HashMap<Integer, CharacterClassLevelData> levelDataMap;
    //Class data for spells
    protected ClassSpellList spellList;

    public CharacterClass(String name) {
        this.name = name;
        restrictedAlignments = new ArrayList<>();
        levelDataMap = new HashMap<>();
        spellList = new ClassSpellList();
    }

    public ClassSpellList getSpellList() {
        return spellList;
    }

    public int getFortSaveModifier(int level) {
        return levelDataMap.get(level).getFortSave();
    }

    public int getRefSaveModifier(int level) {
        return levelDataMap.get(level).getRefSave();
    }

    public int getWillSaveModifier(int level) {
        return levelDataMap.get(level).getWillSave();
    }

    public int[] getBaseAttackBonus(int level) {
        return levelDataMap.get(level).getBaseAttackBonus();
    }

    public HashMap<Integer, CharacterClassLevelData> getLevelDataMap() {
        return levelDataMap;
    }

    public boolean isAbilityUser() {
        return usesAbilities;
    }

    public ArrayList<Alignment> getRestrictedAlignments() {
        return restrictedAlignments;
    }

    public Skill[] getClassSkills() {
        return classSkills;
    }

    public int getInitialSkillRankModifier() {
        return initialSkillRankModifier;
    }

    public int getSkillRankModifier() {
        return skillRankModifier;
    }

    public CasterType getCasterType() {
        return casterType;
    }

    public int getHitDie() {
        return hitDie;
    }

    public StartingGold getStartingGold() {
        return startingGold;
    }

    public String getName() {
        return name;
    }

    /**
     * @return this class's skills
     */
    public Skill[] getSkills() {
        return classSkills;
    }

    /**
     * @return this class's armor proficiencies
     */
    public ArmorProficiency[] getArmorProficiencies() {
        return armorProficiencies;
    }

    /**
     * @return this class's weapon proficiencies
     */
    public WeaponProficiency[] getWeaponProficiencies() {
        return weaponProficiencies;
    }

    public ArrayList<Alignment> getAlignmentLimitations() {
        return restrictedAlignments;
    }

    @Override
    public String toString() {
        return name;
    }
}
