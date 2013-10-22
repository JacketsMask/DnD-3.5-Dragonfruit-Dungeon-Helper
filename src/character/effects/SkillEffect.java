package character.effects;

import enumerations.Skill;

/**
 * A specialized extension of the Effect abstract class that holds information
 * regarding the skill and sub-skill affected.
 *
 * @author Jacob Dorman
 */
public class SkillEffect extends Effect {

    private Skill targetSkill;
    private String subSkill;

    public SkillEffect(Skill targetSkill, String subSkill, String description, EffectTarget target, int modifier, boolean permanent, int roundsLeft, boolean conditional, boolean repeatingEffect) {
        super(description, target, modifier, permanent, roundsLeft, conditional, repeatingEffect);
        this.targetSkill = targetSkill;
        this.subSkill = subSkill;
    }

    public Skill getSkillName() {
        return targetSkill;
    }

    public String getSubskillName() {
        return subSkill;
    }

    @Override
    public EffectTarget getTarget() {
        return EffectTarget.SKILL;
    }

    @Override
    public String toString() {
        String result = targetSkill.toString();
        if (subSkill != null && !subSkill.equals("")) {
            result += " (" + subSkill + ") ";
        }
        result += ": ";
        if (modifier > -1) {
            result += "+";
        }
        result += modifier;
        if (description != null && !description.equals("")) {
            result += " [" + description + "]";
        }
        if (!permanent) {
            result += " [" + roundsLeft + " rounds left]";
        }
        return result;
    }
}
