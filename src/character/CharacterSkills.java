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
    private HashMap<Skill, Integer> skillsBase; //Base skills
    private HashMap<Skill, Integer> skillsBonus; //Temporary or names bonuses/penalties
    //Mapping of which ability scores affect which skills
    private static HashMap<Skill, AbilityScore> keyAbilityModifiers;

    public CharacterSkills(Player player) {
        this.player = player;
        skillsBase = new HashMap<>();
        skillsBonus = new HashMap<>();
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
            skillsBase.put(s, 0);
            skillsBonus.put(s, 0);
        }
    }

    /**
     * Returns the int value of the passed skill by adding the base,
     * bonus/penalty, and ability modifier values.
     *
     * @param skill
     * @return the skill value
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
        return (skillsBase.get(skill) + skillsBonus.get(skill));
    }

    /**
     * Sets this character's base skills to the passed HashMap<Skill, Integer>
     *
     * @param skillsBase the new base skills for this character
     */
    public void setSkillsBase(HashMap<Skill, Integer> skillsBase) {
        this.skillsBase = skillsBase;
        super.stateChanged = true;
    }

    /**
     * Modifies the given skill by the given amount.
     *
     * @param skill
     * @param value
     */
    public void modifySkillBonus(Skill skill, int value) {
        int origValue = skillsBonus.get(skill);
        skillsBonus.put(skill, origValue + value);
        super.stateChanged = true;
    }
}
