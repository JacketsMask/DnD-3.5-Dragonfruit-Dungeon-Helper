package character;

import abstracts.Size;
import character.inventory.Armor;
import enumerations.AbilityScore;
import interfaces.SaveStateTracker;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents an entity's defensive capabilities. This includes AC and dodge, if
 * applicable.
 *
 * @author Jacob Dorman
 */
public class CharacterDefense extends SaveStateTracker {
    //AC = 10 + armor bonus + shield bonus + AbilityScore modifier + size modifier

    //Core AC stats
    private Player character;
    private ArrayList<Armor> equippedArmor;
    private int armorBonus;
    private int shieldBonus;
    private int maximumDexModifierBonus;
    private HashMap<AbilityScore, Boolean> abilityScoreBonusesUsed;
    //Dodge bonuses
    private int dodgeBonus;
    //Optional AC stats
    private int enhancementBonuses;
    private int deflectionBonuses;
    private int naturalArmorBonuses;

    //Enhancement, natural, deflection, 
    public CharacterDefense(Player character) {
        this.character = character;
        this.equippedArmor = new ArrayList<>();
        initializeAbilityScoreModifierUsage();
        //Set an unrealistic cap initially, update when armor is equipped
        maximumDexModifierBonus = 100;
        super.stateChanged = true;
    }

    /**
     * Initializes the HashMap that is used to track which modifiers are
     * included in the AC calculation. By default only DEX is used, though
     * others may be added through feats and class choices.
     */
    private void initializeAbilityScoreModifierUsage() {
        abilityScoreBonusesUsed = new HashMap<>();
        abilityScoreBonusesUsed.put(AbilityScore.STRENGTH, false);
        abilityScoreBonusesUsed.put(AbilityScore.DEXTERITY, true);
        abilityScoreBonusesUsed.put(AbilityScore.CONSTITUTION, false);
        abilityScoreBonusesUsed.put(AbilityScore.INTELLIGENCE, false);
        abilityScoreBonusesUsed.put(AbilityScore.WISDOM, false);
        abilityScoreBonusesUsed.put(AbilityScore.CHARISMA, false);
        super.stateChanged = true;
    }

    /**
     * Recalculates the standard (vs. normal attack) AC value and returns it.
     *
     * @return the current standard AC value
     */
    public int getAC() {
        Size size = character.getBasicInfo().getSize();
        int result = 10 + armorBonus + shieldBonus + size.getAttackAndACModifier()
                + enhancementBonuses + deflectionBonuses + naturalArmorBonuses;
        //Check to see if each ability score bonus currently applies
        for (AbilityScore as : abilityScoreBonusesUsed.keySet()) {
            //If the modifier is used, apply it
            if (abilityScoreBonusesUsed.get(as)) {
                int abilityScoreModifier = character.getAbilityScore().getAbilityScoreModifier(as);
                //Special case, DEX may not be applied under certain conditions (caught flat footed, etc), and may be capped
                if (as.equals(AbilityScore.DEXTERITY)) {
                    if (abilityScoreModifier < maximumDexModifierBonus) {
                        result += abilityScoreModifier;
                    } else {
                        result += maximumDexModifierBonus;
                    }
                    //If the modifier is used and is not DEX, just apply the modifier
                } else {
                    result += abilityScoreModifier;
                }
            }
        }
        return result;
    }

    /**
     * Set whether this entity currently receives their ability score bonus to
     * AC to true or false.
     *
     * @param abilityScore the ability score to set the usage of
     * @param value true if the ability score modifier should be used in AC
     * calculation
     */
    public void setUsingModifierBonus(AbilityScore abilityScore, boolean value) {
        abilityScoreBonusesUsed.put(abilityScore, value);
        super.stateChanged = true;
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

    public void setDeflectionBonuses(int deflectionBonuses) {
        this.deflectionBonuses = deflectionBonuses;
        super.stateChanged = true;
    }

    public int getEnhancementBonuses() {
        return enhancementBonuses;
    }

    public void modifyEnhancementBonuses(int value) {
        enhancementBonuses += value;
        super.stateChanged = true;
    }

    public void setEnhancementBonuses(int enhancementBonuses) {
        this.enhancementBonuses = enhancementBonuses;
        super.stateChanged = true;
    }

    public int getNaturalArmorBonuses() {
        return naturalArmorBonuses;
    }

    public void setNaturalArmorBonuses(int naturalArmorBonuses) {
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
}
