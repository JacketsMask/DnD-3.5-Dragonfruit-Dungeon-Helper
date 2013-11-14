package character;

import abstracts.Size;
import character.classes.CharacterClass;
import character.inventory.Armor;
import enumerations.AbilityScore;
import main.SaveStateTracker;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents an entity's defensive capabilities. This includes AC and dodge, if
 * applicable.
 *
 * @author Jacob Dorman
 */
public class CharacterDefense extends SaveStateTracker implements Serializable {
    //AC = 10 + armor bonus + shield bonus + AbilityScore modifier + size modifier

    //Core AC stats
    private static final long serialVersionUID = 1L;
    private static final int BASE_AC = 10;
    private Player character;
    private ArrayList<Armor> equippedArmor;
    private int armorBonus;
    private int shieldBonus;
    private int maximumDexModifierBonus;
    //Dodge bonuses
    private int dodgeBonus;
    //Optional AC stats
    private int enhancementBonuses;
    private int deflectionBonuses;
    private int naturalArmorBonuses;

    //Enhancement, natural, deflection, 
    public CharacterDefense(Player player) {
        this.character = player;
        this.equippedArmor = new ArrayList<>();
        //Set an unrealistic cap initially, update when armor is equipped
        maximumDexModifierBonus = 100;
        super.stateChanged = true;
    }

    /**
     * @return the base AC for anyone (spoilers, it is 10)
     */
    public static int getBASE_AC() {
        return BASE_AC;
    }

    /**
     * Recalculates the standard (vs. normal attack) AC value and returns it.
     *
     * @return the current standard AC value
     */
    public int getAC() {
        Size size = character.getBasicInfo().getSize();
        int dexMod = character.getAbilityScore().getAbilityScoreModifier(AbilityScore.DEXTERITY);
        int result = 10 + armorBonus + shieldBonus + dexMod + size.getAttackAndACModifier()
                + enhancementBonuses + deflectionBonuses + naturalArmorBonuses;
        return result;
    }

    /**
     * Recalculates the touch (vs. touch attack) AC value and returns it.
     *
     * @return the current touch AC value
     */
    public int getACVSTouch() {
        Size size = character.getBasicInfo().getSize();
        int dexMod = character.getAbilityScore().getAbilityScoreModifier(AbilityScore.DEXTERITY);
        int result = 10 + dexMod + size.getAttackAndACModifier()
                + enhancementBonuses + deflectionBonuses;
        return result;
    }

    /**
     * Recalculates the flat-footed AC value and returns it.  A character uses
     * this value if they haven't yet acted in combat.
     *
     * @return the current flat-footed AC value
     */
    public int getACFlatFooted() {
        Size size = character.getBasicInfo().getSize();
        int result = 10 + armorBonus + shieldBonus + size.getAttackAndACModifier()
                + enhancementBonuses + deflectionBonuses + naturalArmorBonuses;
        return result;
    }

    /**
     * @return the AC bonus granted through armor
     */
    public int getArmorBonus() {
        return armorBonus;
    }

    /**
     * Equips the passed piece of armor if it is not currently equipped.
     *
     * @param armor
     */
    public void equipArmor(Armor armor) {
        //Add the item to the equipped list if it isn't already
        if (!equippedArmor.contains(armor)) {
            equippedArmor.add(armor);
            //Limit max DEX bonus if necessary
            if (maximumDexModifierBonus > armor.getMaxDexBonus()) {
                maximumDexModifierBonus = armor.getMaxDexBonus();
            }
            armorBonus += armor.getAcBonus();
        }
        super.stateChanged = true;
    }

    /**
     * Removes the passed piece of armor if it is currently equipped.
     *
     * @param armor
     */
    public void removeArmor(Armor armor) {
        if (equippedArmor.contains(armor)) {
            equippedArmor.remove(armor);
            armorBonus -= armor.getAcBonus();
            //Recalculate the maximum DEX modifier bonus if necessary
            if (armor.getMaxDexBonus() == maximumDexModifierBonus) {
                recalculateMaxDexBonus();
            }
        }
        super.stateChanged = true;
    }

    public int getDeflectionBonuses() {
        return deflectionBonuses;
    }

    public void modifyDeflectionBonuses(int value) {
        deflectionBonuses += value;
        super.stateChanged = true;
    }

    public void setDeflectionBonus(int deflectionBonuses) {
        this.deflectionBonuses = deflectionBonuses;
        super.stateChanged = true;
    }

    public int getEnhancementBonus() {
        return enhancementBonuses;
    }

    public void modifyEnhancementBonuses(int value) {
        enhancementBonuses += value;
        super.stateChanged = true;
    }

    public void setEnhancementBonus(int enhancementBonuses) {
        this.enhancementBonuses = enhancementBonuses;
        super.stateChanged = true;
    }

    public int getNaturalArmorBonuses() {
        return naturalArmorBonuses;
    }

    public void setNaturalArmorBonus(int naturalArmorBonuses) {
        this.naturalArmorBonuses = naturalArmorBonuses;
        super.stateChanged = true;
    }

    public int getShieldBonus() {
        return shieldBonus;
    }

    public void setShieldBonus(int shieldBonus) {
        this.shieldBonus = shieldBonus;
        super.stateChanged = true;
    }

    public int getDodgeBonus() {
        return dodgeBonus;
    }

    public void modifyDodgeBonus(int value) {
        dodgeBonus += value;
        super.stateChanged = true;
    }

    public void setDodgeBonus(int dodgeBonus) {
        this.dodgeBonus = dodgeBonus;
        super.stateChanged = true;
    }

    /**
     * Recalculates the maximum DEX bonus from the equipped armor.
     */
    private void recalculateMaxDexBonus() {
        maximumDexModifierBonus = 100;
        for (Armor a : equippedArmor) {
            if (a.getMaxDexBonus() < maximumDexModifierBonus) {
                maximumDexModifierBonus = a.getMaxDexBonus();
            }
        }
        super.stateChanged = true;
    }

    /**
     * Calculates and returns a fortitude save bonus from classes and 
     * constitution.
     * @return the fortitude save bonus
     */
    public int getFortitudeSaveBonus() {
        //Fortitude save bonuses from classes
        ArrayList<CharacterClass> characterClasses = character.getClassInfo().getCharacterClasses();
        int fortSaveBonus = 0;
        for (CharacterClass cc : characterClasses) {
            fortSaveBonus += cc.getFortSaveModifier();
        }
        //Constitution modifier
        int conMod = character.getAbilityScore().getAbilityScoreModifier(AbilityScore.CONSTITUTION);
        return fortSaveBonus + conMod;
    }

    /**
     * Calculates and returns a reflex save bonus from classes and dexterity.
     * @return the reflex save bonus
     */
    public int getReflexSaveBonus() {
        //Fortitude save bonuses from classes
        ArrayList<CharacterClass> characterClasses = character.getClassInfo().getCharacterClasses();
        int reflexSaveBonus = 0;
        for (CharacterClass cc : characterClasses) {
            reflexSaveBonus += cc.getRefSaveModifier();
        }
        //Constitution modifier
        int dexMod = character.getAbilityScore().getAbilityScoreModifier(AbilityScore.DEXTERITY);
        return reflexSaveBonus + dexMod;
    }

    /**
     * Calculates and returns a will save bonus from classes and wisdom.
     * @return the will save bonus
     */
    public int getWillSaveBonus() {
        //Fortitude save bonuses from classes
        ArrayList<CharacterClass> characterClasses = character.getClassInfo().getCharacterClasses();
        int willSaveBonus = 0;
        for (CharacterClass cc : characterClasses) {
            willSaveBonus += cc.getWillSaveModifier();
        }
        //Constitution modifier
        int wisMod = character.getAbilityScore().getAbilityScoreModifier(AbilityScore.WISDOM);
        return willSaveBonus + wisMod;
    }

    /**
     * @return the size bonus to AC 
     */
    public int getSizeBonus() {
        return character.getBasicInfo().getRace().getSize().getAttackAndACModifier();
    }

    /**
     * Forcefully sets the armor bonus to the passed value.  This will work 
     * fine early, but may cause problems if it is being used after armor can 
     * be equipped later.
     * @param newArmorBonus the new armor bonus
     */
    public void setArmorBonus(int newArmorBonus) {
        armorBonus = newArmorBonus;
    }

    /**
     * @return the dex modifier of this character
     */
    public int getDexBonus() {
        return character.getAbilityScore().getAbilityScoreModifier(AbilityScore.DEXTERITY);
    }
}
