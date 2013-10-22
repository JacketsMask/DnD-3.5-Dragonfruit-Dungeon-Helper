package character.effects;

/**
 * A generic description effect that allows for an integer value to be stored
 * for a specific object type (like AbilityScore). This descriptive modifier can
 * have a permanent effect or have a limited duration. It is up to the calling
 * environment to decide what to do with these values, and to revert the effects
 * if the duration expires.
 *
 * @author Jacob Dorman
 */
public class SimpleEffect extends Effect {

    public SimpleEffect(String description, EffectTarget target, int modifier, boolean permanent, int roundsLeft, boolean conditional, boolean repeatingEffect) {
        super(description, target, modifier, permanent, roundsLeft, conditional, repeatingEffect);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getModifier() {
        return modifier;
    }

    @Override
    public int getRoundsLeft() {
        return roundsLeft;
    }

    @Override
    public String toString() {
        String result = target.toString() + ": ";
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

    @Override
    public EffectTarget getTarget() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}