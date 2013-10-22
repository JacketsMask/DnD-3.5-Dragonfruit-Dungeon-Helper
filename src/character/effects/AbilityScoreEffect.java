package character.effects;

import enumerations.AbilityScore;

/**
 * An effect that affects a character's ability score either positively or
 * negatively.
 *
 * @author Japhez
 */
public class AbilityScoreEffect extends Effect {

    private AbilityScore statAffected;

    public AbilityScoreEffect(AbilityScore statAffected, String description, EffectTarget target, int modifier, boolean permanent, int roundsLeft, boolean conditional, boolean repeatingEffect) {
        super(description, target, modifier, permanent, roundsLeft, conditional, repeatingEffect);
        this.statAffected = statAffected;
    }

    @Override
    public EffectTarget getTarget() {
        return EffectTarget.ABILITY_SCORE;
    }

    public AbilityScore getTargetAbilityScore() {
        return statAffected;
    }

    @Override
    public String toString() {
        String result = statAffected.toString() + ": ";
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
