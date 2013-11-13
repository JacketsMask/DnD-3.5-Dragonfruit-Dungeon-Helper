package character.classes;

import character.proficiencies.WeaponProficiency;
import character.proficiencies.ArmorProficiency;
import enumerations.*;
import abstracts.Ability;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 * A basic character class to be extended and set by child classes.
 *
 * @author Jacob Dorman
 */
public abstract class CharacterClass implements Serializable {
    //The name of this class

    protected String name;
    //The current level of this class
    protected int level;
    //The current base attack bonus of this class
    protected int baseAttackBonus;
    //Hit die
    protected int hitDie;
    //Savings throws
    protected int fortitudeSavingThrow;
    protected int reflexSavingThrow;
    protected int willSavingThrow;
    //The class skills of that this class provides
    protected Skill[] classSkills;
    //The list of abilities
    protected DefaultListModel<Ability> abilityListModel;
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
    //Starting skill ranks
    protected int initialSkillRankModifier;
    protected int skillRankModifier;

    public CharacterClass() {
        name = "Undefined";
        level = 1;
        baseAttackBonus = 0;
        fortitudeSavingThrow = 0;
        reflexSavingThrow = 0;
        willSavingThrow = 0;
        classSkills = new Skill[0];
        abilityListModel = new DefaultListModel<>();
        armorProficiencies = new ArmorProficiency[0];
        weaponProficiencies = new WeaponProficiency[0];
        restrictedAlignments = new ArrayList<>();
        classNotes = "[Enter class notes here]";
        hitDie = 6;
        startingGold = new StartingGold(1, 1, 10);
        casterType = CasterType.NON_CASTER;
        initialSkillRankModifier = 0;
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

    public void setAbilityListModel(DefaultListModel<Ability> abilityListModel) {
        this.abilityListModel = abilityListModel;
    }

    public void setBaseAttackBonus(int baseAttackBonus) {
        this.baseAttackBonus = baseAttackBonus;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getFortitudeSavingThrow() {
        return fortitudeSavingThrow;
    }

    public void setFortitudeSavingThrow(int fortitudeSavingThrow) {
        this.fortitudeSavingThrow = fortitudeSavingThrow;
    }

    public int getReflexSavingThrow() {
        return reflexSavingThrow;
    }

    public void setReflexSavingThrow(int reflexSavingThrow) {
        this.reflexSavingThrow = reflexSavingThrow;
    }

    public int getWillSavingThrow() {
        return willSavingThrow;
    }

    public void setWillSavingThrow(int willSavingThrow) {
        this.willSavingThrow = willSavingThrow;
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

    /**
     * Adds the passed ability to this classes' list of abilities.
     *
     * @param ability
     */
    public void addAbility(Ability ability) {
        abilityListModel.addElement(ability);
    }

    public DefaultListModel<Ability> getAbilityListModel() {
        return abilityListModel;
    }

    public String getName() {
        return name;
    }

    /**
     * @return this class's fortitude save modifier
     */
    public int getFortSaveModifier() {
        return fortitudeSavingThrow;
    }

    /**
     * @return this class's reflex save modifier
     */
    public int getRefSaveModifier() {
        return reflexSavingThrow;
    }

    /**
     * @return this class's will save modifier
     */
    public int getWillSaveModifier() {
        return willSavingThrow;
    }

    /**
     * @return this class's attack bonus
     */
    public int getBaseAttackBonus() {
        return baseAttackBonus;
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
