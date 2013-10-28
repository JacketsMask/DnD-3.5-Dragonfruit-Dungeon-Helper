package character.classes;

import character.proficiencies.WeaponProficiency;
import character.proficiencies.ArmorProficiency;
import enumerations.*;
import abstracts.Ability;
import javax.swing.DefaultListModel;

/**
 * A basic character class to be extended and set by child classes.
 *
 * @author Jacob Dorman
 */
public abstract class CharacterClass {
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
    //Class related order limitations
    protected Order[] orderLimitations;
    //Class related alignment limitations
    protected Alignment[] alignmentLimitations;
    //Class notes
    protected String classNotes;

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
        orderLimitations = new Order[0];
        alignmentLimitations = new Alignment[0];
        classNotes = "[Enter class notes here]";
        hitDie = 6;
    }

    public int getHitDie() {
        return hitDie;
    }

    public void setHitDie(int hitDie) {
        this.hitDie = hitDie;
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
    public int getFortSaveModifiers() {
        return fortitudeSavingThrow;
    }

    /**
     * @return this class's reflex save modifier
     */
    public int getRefSaveModifiers() {
        return reflexSavingThrow;
    }

    /**
     * @return this class's will save modifier
     */
    public int getWillSaveModifiers() {
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

    public Order[] getOrderLimitations() {
        return orderLimitations;
    }

    public Alignment[] getAlignmentLimitations() {
        return alignmentLimitations;
    }

    @Override
    public String toString() {
        return name;
    }
}
