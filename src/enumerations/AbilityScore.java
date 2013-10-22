package enumerations;

/**
 * An enumeration of all of the ability scores.
 *
 * @author Japhez
 */
public enum AbilityScore
{

    CHARISMA, CONSTITUTION, DEXTERITY, INTELLIGENCE, STRENGTH, WISDOM;

    /**
     * @return the array of ability scores
     */
    public static AbilityScore[] getAbilityScores()
    {
        AbilityScore[] result = new AbilityScore[6];
        result[0] = STRENGTH;
        result[1] = DEXTERITY;
        result[2] = CONSTITUTION;
        result[3] = INTELLIGENCE;
        result[4] = WISDOM;
        result[5] = CHARISMA;
        return result;
    }

    public static AbilityScore getAbilityScoreFromString(String abilityScore)
    {
        if (abilityScore.equalsIgnoreCase("Strength"))
        {
            return STRENGTH;
        }
        if (abilityScore.equalsIgnoreCase("Dexterity"))
        {
            return DEXTERITY;
        }
        if (abilityScore.equalsIgnoreCase("Constitution"))
        {
            return CONSTITUTION;
        }
        if (abilityScore.equalsIgnoreCase("Intelligence"))
        {
            return INTELLIGENCE;
        }
        if (abilityScore.equalsIgnoreCase("Wisdom"))
        {
            return WISDOM;
        }
        if (abilityScore.equalsIgnoreCase("Charisma"))
        {
            return CHARISMA;
        }
        return null;
    }

    /**
     * Override for toString that returns the enumeration with the first letter
     * capitalized, and the rest lower case.
     * http://javahowto.blogspot.com/2006/10/custom-string-values-for-enum.html?showComment=1240529040000#c6158717620626337359
     *
     * @return the enumeration with the first letter capitalized, and the rest
     * lower case.
     */
    @Override
    public String toString()
    {
        String output = name().toString();
        output = output.charAt(0) + output.substring(1).toLowerCase();
        return output;
    }
}
