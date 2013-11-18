package character.classes;

import character.proficiencies.WeaponProficiency;
import character.proficiencies.ArmorProficiency;
import enumerations.*;
import gui.classes.CharacterClassLevelData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import main.SaveStateTracker;

/**
 * A character class containing most of the information pertaining to a class.
 *
 * @author Jacob Dorman
 */
public class CharacterClass extends SaveStateTracker implements Serializable {

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
    //Class data for spells
    protected ClassSpellList spellList;

    public CharacterClass(String name) {
        this.name = name;
        restrictedAlignments = new ArrayList<>();
        levelDataMap = new HashMap<>();
    }

    public ClassSpellList getSpellList() {
        return spellList;
    }

    public void setSpellList(ClassSpellList spellList) {
        System.out.println(name + " spell list set.");
        this.spellList = spellList;
        super.stateChanged = true;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
        super.stateChanged = true;
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

    public void setLevelDataMap(HashMap<Integer, CharacterClassLevelData> levelDataMap) {
        this.levelDataMap = levelDataMap;
        super.stateChanged = true;
    }

    public void setAbilityUser(boolean usesAbilities) {
        this.usesAbilities = usesAbilities;
        super.stateChanged = true;
    }

    public boolean isAbilityUser() {
        return usesAbilities;
    }

    public ArrayList<Alignment> getRestrictedAlignments() {
        return restrictedAlignments;
    }

    public void setRestrictedAlignments(ArrayList<Alignment> restrictedAlignments) {
        this.restrictedAlignments = restrictedAlignments;
        super.stateChanged = true;
    }

    public void setArmorProficiencies(ArmorProficiency[] armorProficiencies) {
        this.armorProficiencies = armorProficiencies;
        super.stateChanged = true;
    }

    public void setName(String name) {
        this.name = name;
        super.stateChanged = true;
    }

    public void setWeaponProficiencies(WeaponProficiency[] weaponProficiencies) {
        this.weaponProficiencies = weaponProficiencies;
        super.stateChanged = true;
    }

    public Skill[] getClassSkills() {
        return classSkills;
    }

    public String getClassNotes() {
        return classNotes;
    }

    public void setClassNotes(String classNotes) {
        this.classNotes = classNotes;
        super.stateChanged = true;
    }

    public void setClassSkills(Skill[] classSkills) {
        this.classSkills = classSkills;
        super.stateChanged = true;
    }

    public void setInitialSkillRankModifier(int initialSkillRankModifier) {
        this.initialSkillRankModifier = initialSkillRankModifier;
        super.stateChanged = true;
    }

    public int getInitialSkillRankModifier() {
        return initialSkillRankModifier;
    }

    public void setSkillRankModifier(int skillRankModifier) {
        this.skillRankModifier = skillRankModifier;
        super.stateChanged = true;
    }

    public int getSkillRankModifier() {
        return skillRankModifier;
    }

    public void setCasterType(CasterType casterType) {
        this.casterType = casterType;
        super.stateChanged = true;
    }

    public CasterType getCasterType() {
        return casterType;
    }

    public int getHitDie() {
        return hitDie;
    }

    public void setHitDie(int hitDie) {
        this.hitDie = hitDie;
        super.stateChanged = true;
    }

    public void setStartingGold(StartingGold startingGold) {
        this.startingGold = startingGold;
        super.stateChanged = true;
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
    public boolean stateChanged() {
        return super.stateChanged() || spellList.stateChanged();
    }

    @Override
    public void stateSaved() {
        super.stateSaved();
        spellList.stateSaved();
    }

    @Override
    public String toString() {
        return name;
    }
}
