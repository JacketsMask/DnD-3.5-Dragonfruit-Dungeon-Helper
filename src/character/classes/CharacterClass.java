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
 * @author Jacob Dorman
 */
public class CharacterClass implements Serializable {

    private static final long serialVersionUID = 1L;
    //The name of this class
    protected String name;
    //Current level
    protected int currentLevel;
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
    //Class notes
    protected String classNotes;
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

    public CharacterClass() {
        name = "Undefined";
        restrictedAlignments = new ArrayList<>();
        levelDataMap = new HashMap<>();
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getFortSaveModifier() {
        return levelDataMap.get(currentLevel).getFortSave();
    }

    public int getRefSaveModifier() {
        return levelDataMap.get(currentLevel).getRefSave();
    }

    public int getWillSaveModifier() {
        return levelDataMap.get(currentLevel).getWillSave();
    }
    
    public int[] getBaseAttackBonus() {
        return levelDataMap.get(currentLevel).getBaseAttackBonus();
    }

    public CharacterClass(String name) {
        this.name = name;
        restrictedAlignments = new ArrayList<>();
        levelDataMap = new HashMap<>();
    }

    public void setLevelDataMap(HashMap<Integer, CharacterClassLevelData> levelDataMap) {
        this.levelDataMap = levelDataMap;
    }

    public void setAbilityUser(boolean usesAbilities) {
        this.usesAbilities = usesAbilities;
    }

    public boolean isAbilityUser() {
        return usesAbilities;
    }

    public ArrayList<Alignment> getRestrictedAlignments() {
        return restrictedAlignments;
    }

    public void setRestrictedAlignments(ArrayList<Alignment> restrictedAlignments) {
        this.restrictedAlignments = restrictedAlignments;
    }

    public void setArmorProficiencies(ArmorProficiency[] armorProficiencies) {
        this.armorProficiencies = armorProficiencies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeaponProficiencies(WeaponProficiency[] weaponProficiencies) {
        this.weaponProficiencies = weaponProficiencies;
    }

    public Skill[] getClassSkills() {
        return classSkills;
    }

    public String getClassNotes() {
        return classNotes;
    }

    public void setClassNotes(String classNotes) {
        this.classNotes = classNotes;
    }

    public void setClassSkills(Skill[] classSkills) {
        this.classSkills = classSkills;
    }

    public void setInitialSkillRankModifier(int initialSkillRankModifier) {
        this.initialSkillRankModifier = initialSkillRankModifier;
    }

    public int getInitialSkillRankModifier() {
        return initialSkillRankModifier;
    }

    public void setSkillRankModifier(int skillRankModifier) {
        this.skillRankModifier = skillRankModifier;
    }

    public int getSkillRankModifier() {
        return skillRankModifier;
    }

    public void setCasterType(CasterType casterType) {
        this.casterType = casterType;
    }

    public CasterType getCasterType() {
        return casterType;
    }

    public int getHitDie() {
        return hitDie;
    }

    public void setHitDie(int hitDie) {
        this.hitDie = hitDie;
    }

    public void setStartingGold(StartingGold startingGold) {
        this.startingGold = startingGold;
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
