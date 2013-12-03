package character;

import diceroller.DiceRoller;
import enumerations.AbilityScore;
import savestate.SaveStateSender;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Stores character ability score information, including their base ability
 * score, ability score bonus/penalties, and the resulting ability score
 * modifiers.
 *
 * @author Jacob Dorman
 */
public final class CharacterAbilityScore extends SaveStateSender implements Serializable {

    private static final long serialVersionUID = 1L;
    private HashMap<AbilityScore, Integer> abilityScoreBases;
    private HashMap<AbilityScore, Integer> abilityScoreBonuses;
    private HashMap<AbilityScore, Integer> abilityScoreModifiers;

    public CharacterAbilityScore() {
        super();
        rollAndInitializeAbilityScore();
        abilityScoreBonuses = new HashMap<>();
        abilityScoreBonuses.put(AbilityScore.STRENGTH, 0);
        abilityScoreBonuses.put(AbilityScore.DEXTERITY, 0);
        abilityScoreBonuses.put(AbilityScore.CONSTITUTION, 0);
        abilityScoreBonuses.put(AbilityScore.WISDOM, 0);
        abilityScoreBonuses.put(AbilityScore.INTELLIGENCE, 0);
        abilityScoreBonuses.put(AbilityScore.CHARISMA, 0);
    }

    /**
     * Calculates the ability score modifiers based off the current ability
     * score.
     */
    public void calculateAbilityScoreModifiers() {
        abilityScoreModifiers = new HashMap<>();
        abilityScoreModifiers.put(AbilityScore.STRENGTH, 0);
        abilityScoreModifiers.put(AbilityScore.DEXTERITY, 0);
        abilityScoreModifiers.put(AbilityScore.CONSTITUTION, 0);
        abilityScoreModifiers.put(AbilityScore.WISDOM, 0);
        abilityScoreModifiers.put(AbilityScore.INTELLIGENCE, 0);
        abilityScoreModifiers.put(AbilityScore.CHARISMA, 0);
        super.stateChanged();
    }

    /**
     * Modifies the given ability score bonus value by the given amount.
     *
     * @param as the ability score to modify
     * @param value the value to modify the ability score by
     */
    public void modifyAbilityScoreBonuses(AbilityScore as, int value) {
        abilityScoreBonuses.put(as, (abilityScoreBonuses.get(as) + value));
        super.stateChanged();
    }

    /**
     * Modifies the given ability score base value by the given amount.
     *
     * @param as the ability score to modify
     * @param value the value to modify the ability score by
     */
    public void modifyAbilityScoreBases(AbilityScore as, int value) {
        abilityScoreBases.put(as, (abilityScoreBases.get(as) + value));
        super.stateChanged();
    }

    /**
     * @return the current ability score modifiers
     */
    public HashMap<AbilityScore, Integer> getAbilityScoreModifiers() {
        return abilityScoreModifiers;
    }

    /**
     * @return the current base ability scores in a HashMap.
     */
    public HashMap<AbilityScore, Integer> getAbilityScoresBases() {
        return abilityScoreBases;
    }

    /**
     * @return the current bonus ability scores in a HashMap.
     */
    public HashMap<AbilityScore, Integer> getAbilityScoresBonuses() {
        return abilityScoreBonuses;
    }

    public void setStatModifier(AbilityScore abilityScore, int value) {
        abilityScoreModifiers.put(abilityScore, value);
        super.stateChanged();
    }

    /**
     * Rolls initial ability score values for this character (prior to race
     * modifications), sets them as this character's ability score.
     */
    public void rollAndInitializeAbilityScore() {
        abilityScoreBases = new HashMap();
        abilityScoreBases.put(AbilityScore.STRENGTH, DiceRoller.rollAbilityScore());
        abilityScoreBases.put(AbilityScore.DEXTERITY, DiceRoller.rollAbilityScore());
        abilityScoreBases.put(AbilityScore.CONSTITUTION, DiceRoller.rollAbilityScore());
        abilityScoreBases.put(AbilityScore.WISDOM, DiceRoller.rollAbilityScore());
        abilityScoreBases.put(AbilityScore.INTELLIGENCE, DiceRoller.rollAbilityScore());
        abilityScoreBases.put(AbilityScore.CHARISMA, DiceRoller.rollAbilityScore());
        super.stateChanged();
    }

    /**
     * Sets the passed ability score bonus to the passed value.
     *
     * @param abilityScore
     * @param value
     */
    public void setBonusAbilityScore(AbilityScore abilityScore, int value) {
        abilityScoreBonuses.put(abilityScore, value);
        super.stateChanged();
    }

    /**
     * Sets the passed ability score base to the passed value.
     *
     * @param abilityScore
     * @param value
     */
    public void setBaseAbilityScore(AbilityScore abilityScore, int value) {
        abilityScoreBases.put(abilityScore, value);
        super.stateChanged();
    }

    /**
     * Returns the ability score value of the passed base ability score.
     *
     * @param abilityScore
     * @return the ability score value of the passed base ability score
     */
    public int getAbilityScoreBaseValue(AbilityScore abilityScore) {
        return abilityScoreBases.get(abilityScore);
    }

    /**
     * Returns the ability score value of the passed bonus ability score.
     *
     * @param abilityScore
     * @return the ability score value of the passed bonus ability score
     */
    public int getAbilityScoreBonusValue(AbilityScore abilityScore) {
        return abilityScoreBonuses.get(abilityScore);
    }

    /**
     * Calculates and returns the ability modifier for a given total ability
     * score value.
     *
     * @param value
     * @return return the ability modifier
     */
    public int getAbilityScoreModifier(AbilityScore abilityScore) {
        int base = abilityScoreBases.get(abilityScore);
        int bonus = abilityScoreBonuses.get(abilityScore);
        int value = base + bonus;
        if (value > 8 && value < 12) {
            return 0;
        }
        boolean negative = false;
        if (value < 9) {
            negative = true;
        }
        int result = (int) Math.ceil(Math.abs((value - 10) / 2.0));
        if (negative) {
            return -result;
        }
        return result;
    }
}
