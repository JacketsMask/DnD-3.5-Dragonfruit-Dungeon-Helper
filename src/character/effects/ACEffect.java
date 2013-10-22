package character.effects;

/**
 * Represents a effect for armor class.
 *
 * @author Japhez
 */
public class ACEffect extends Effect {

    public ACEffect(String description, EffectTarget target, int modifier, boolean permanent, int roundsLeft, boolean conditional, boolean repeatingEffect) {
        super(description, target, modifier, permanent, roundsLeft, conditional, repeatingEffect);
    }

    @Override
    public EffectTarget getTarget() {
        return EffectTarget.ARMOR_CLASS;
    }

    public String getTargetArmorModifier() {
        return null;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
