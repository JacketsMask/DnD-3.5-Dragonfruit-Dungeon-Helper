/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package character.races;

import abstracts.Ability;
import abstracts.Size;
import enumerations.AbilityScore;
import character.proficiencies.ArmorProficiency;
import enumerations.Language;
import character.proficiencies.WeaponProficiency;
import java.util.HashMap;

/**
 * A race of a character. A race can affect the ability score of a character, it
 * can grant abilities, weakness, and permanent effects.
 *
 * @author Japhez
 */
public abstract class Race
{

    //Race name
    String name;
    //Ability Score modifiers
    protected HashMap<AbilityScore, Integer> abilityScoreModifiers;
    //Skill point modifiers
    protected int startingModifiedSkillPoints;
    protected int modifiedSkillPointsEachLevel;
    //Languages, racial abilities, and proficiencies
    protected Language[] languages;
    protected Ability[] racialAbilities;
    protected ArmorProficiency[] racialArmorProficiencies;
    protected WeaponProficiency[] racialWeaponProficiencies;
    //Size and speed
    protected Size size;
    protected int speed;
    //Saving throw modifiers
    protected SavingThrowModifier[] savingThrowModifiers;
    //Level adjustment
    protected int levelAdjustment;
    protected int racialHitDie;
    protected String racialHitDieName;

    public Race()
    {
        initializeAbilityScoreModifiers();
    }


    /**
     * Initializes all of the class ability score modifiers to 0.
     */
    private void initializeAbilityScoreModifiers()
    {
        abilityScoreModifiers = new HashMap<>();
        AbilityScore[] abilityScores = AbilityScore.getAbilityScores();
        for (AbilityScore as : abilityScores)
        {
            abilityScoreModifiers.put(as, 0);
        }
    }

    /**
     * Returns the class modifier for the passed Ability Score.
     *
     * @param abilityScore
     * @return the class modifier for the passed Ability Score
     */
    public int getAbilityScoreModifier(AbilityScore abilityScore)
    {
        return abilityScoreModifiers.get(abilityScore);
    }
    public Language[] getLanguages()
    {
        return languages;
    }

    public int getLevelAdjustment()
    {
        return levelAdjustment;
    }

    public int getModifiedSkillPointsEachLevel()
    {
        return modifiedSkillPointsEachLevel;
    }

    public Ability[] getRacialAbilities()
    {
        return racialAbilities;
    }

    public ArmorProficiency[] getRacialArmorProficiencies()
    {
        return racialArmorProficiencies;
    }

    public WeaponProficiency[] getRacialWeaponProficiencies()
    {
        return racialWeaponProficiencies;
    }

    public SavingThrowModifier[] getSavingThrowModifiers()
    {
        return savingThrowModifiers;
    }

    public Size getSize()
    {
        return size;
    }

    public int getSpeed()
    {
        return speed;
    }

    public int getStartingModifiedSkillPoints()
    {
        return startingModifiedSkillPoints;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return name;
    }

    /**
     * Humans are extraordinarily plain.
     *
     * @author Jacob Dorman
     */
    public static class Human extends Race
    {

        public Human()
        {
            name = "Human";
            languages = new Language[]
            {
                Language.COMMON
            };
            size = new Size.Medium();
            speed = 30;
            modifiedSkillPointsEachLevel = 1;
            startingModifiedSkillPoints = 4;
        }
    }
}