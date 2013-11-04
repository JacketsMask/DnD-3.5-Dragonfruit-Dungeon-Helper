package character;

import enumerations.AbilityScore;
import enumerations.Skill;
import interfaces.SaveStateTracker;
import java.util.HashMap;

/**
 * Stores information related to character skills, such as their base skill
 * values, bonus/penalty modifiers, and conditional modifiers that only are
 * active under certain circumstances.
 *
 * @author Jacob Dorman
 */
public class CharacterSkills extends SaveStateTracker {

    private Player player;
    private HashMap<Skill, Integer> skillRanks; //Base skills
    private HashMap<Skill, Integer> skillMiscModifier; //Temporary or names bonuses/penalties
    //Mapping of which ability scores affect which skills
    private static HashMap<Skill, AbilityScore> keyAbilityModifiers;

    public CharacterSkills(Player player) {
        this.player = player;
        skillRanks = new HashMap<>();
        skillMiscModifier = new HashMap<>();
        keyAbilityModifiers = new HashMap<>();
        initializeSkills();
        linkKeyAbilityModifiers();
        super.stateChanged = true;
    }

    private void linkKeyAbilityModifiers() {
        //Link strength skills
        Skill[] strSkills = Skill.getStrSkills();
        for (Skill s : strSkills) {
            keyAbilityModifiers.put(s, AbilityScore.STRENGTH);
        }
        //Link dexterity skills
        Skill[] dexSkills = Skill.getDexSkills();
        for (Skill s : dexSkills) {
            keyAbilityModifiers.put(s, AbilityScore.DEXTERITY);
        }
        //Link constitution skills
        Skill[] conSkills = Skill.getConSkills();
        for (Skill s : conSkills) {
            keyAbilityModifiers.put(s, AbilityScore.CONSTITUTION);
        }
        //Link intelligence skills
        Skill[] intSkills = Skill.getIntSkills();
        for (Skill s : intSkills) {
            keyAbilityModifiers.put(s, AbilityScore.INTELLIGENCE);
        }
        //Link wisdom skills
        Skill[] wisSkills = Skill.getWisSkills();
        for (Skill s : wisSkills) {
            keyAbilityModifiers.put(s, AbilityScore.WISDOM);
        }
        //Link charisma skills
        Skill[] charSkills = Skill.getChaSkills();
        for (Skill s : charSkills) {
            keyAbilityModifiers.put(s, AbilityScore.CHARISMA);
        }
    }

    private void initializeSkills() {
        Skill[] allSkills = Skill.getAllSkills();
        for (Skill s : allSkills) {
            skillRanks.put(s, 0);
            skillMiscModifier.put(s, 0);
        }
    }

    /**
     * Returns the int value of the ability score modifier for this skill if 
     * one exists.
     *
     * @param skill
     * @return the modifier value
     */
    public int getKeyModifierValue(Skill skill) {
        AbilityScore as = keyAbilityModifiers.get(skill);
        //If there is an ability score associated with the skill
        if (as != null) {
            return (player.getAbilityScore().getAbilityScoreModifier(as));
            //If there isn't an ability score associated with the skill
        } else {
            return 0;
        }
    }

    public int getSkillRank(Skill skill) {
        return (skillRanks.get(skill) + skillMiscModifier.get(skill));
    }

    /**
     * Sets this character's base skills to the passed HashMap<Skill, Integer>
     *
     * @param skillsBase the new base skills for this character
     */
    public void setSkillsBase(HashMap<Skill, Integer> skillsBase) {
        this.skillRanks = skillsBase;
        super.stateChanged = true;
    }

    /**
     * Modifies the given skill by the given amount.
     *
     * @param skill
     * @param value
     */
    public void modifyMiscSkillModifier(Skill skill, int value) {
        int origValue = skillMiscModifier.get(skill);
        skillMiscModifier.put(skill, origValue + value);
        super.stateChanged = true;
    }

    /**
     * Returns the misc modifier value for the given skill.
     * @param skill
     * @return 
     */
    public int getMiscModifierSkillValue(Skill skill) {
        return skillMiscModifier.get(skill);
    }

    /**
     * Sets the value of the passed skill rank to the passed value.
     * @param skill
     * @param rank 
     */
    public void setSkillRank(Skill skill, int rank) {
        skillRanks.put(skill, rank);
    }

    /**
     * Sets the value of the passed skill misc modifier to the passed value.
     * @param skill
     * @param value 
     */
    public void setSkillModifier(Skill skill, int value) {
        skillMiscModifier.put(skill, value);
    }
}
