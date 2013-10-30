package character;

import character.effects.SimpleEffect;
import enumerations.Skill;
import interfaces.SaveStateTracker;
import java.util.HashMap;
import javax.swing.DefaultListModel;

/**
 * Stores information related to character skills, such as their base skill
 * values, bonus/penalty modifiers, and conditional modifiers that only are
 * active under certain circumstances.
 *
 * @author Jacob Dorman
 */
public class CharacterSkills extends SaveStateTracker {

    private HashMap<Skill, Integer> skillsBase; //Base skills
    private HashMap<Skill, Integer> skillsBonus; //Temporary or names bonuses/penalties
    private DefaultListModel<SimpleEffect> conditionalModifierList; //Modifiers to skills under certain circumstances

    public CharacterSkills() {
        skillsBase = new HashMap<>();
        skillsBonus = new HashMap<>();
        conditionalModifierList = new DefaultListModel<>();
        initializeSkills();
        super.stateChanged = true;
    }

    private void initializeSkills() {
        Skill[] allSkills = Skill.getAllSkills();
        for (Skill s : allSkills) {
            skillsBase.put(s, 0);
            skillsBonus.put(s, 0);
        }
    }

    /**
     * Returns the int value of the passed skill by adding both the base and
     * bonus/penalty values.
     *
     * @param skill
     * @return the skill value
     */
    public int getSkillValue(Skill skill) {
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

    /**
     * @return a list of conditional modifier that can be used directly in a GUI
     */
    public DefaultListModel<SimpleEffect> getConditionalModifierList() {
        return conditionalModifierList;
    }
}
