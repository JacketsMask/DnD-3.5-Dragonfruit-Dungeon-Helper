/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package character.effects;

/**
 * An abstract class to be extended by all effects governed by the Effect
 * Manager. This abstract class insures that the Effect Manager can correctly
 * apply and revert effects under the correct circumstances.
 *
 * @author Jacob Dorman
 */
public abstract class Effect {

    protected String description;
    protected EffectTarget target;
    protected int modifier;
    protected boolean permanent;
    protected int roundsLeft;
    protected boolean conditional;
    protected boolean repeatingEffect;

    public Effect(String description, EffectTarget target, int modifier, boolean permanent, int roundsLeft, boolean conditional, boolean repeatingEffect) {
        this.description = description;
        this.target = target;
        this.modifier = modifier;
        this.permanent = permanent;
        this.roundsLeft = roundsLeft;
        this.conditional = conditional;
        this.repeatingEffect = repeatingEffect;
    }

    /**
     * @return true if this effect is only present under certain conditions
     * (player/DM tracked) 
     */
    public boolean isConditional() {
        return conditional;
    }

    /**
     * @return true if this effect repeats every round
     */
    public boolean isRepeatingEffect() {
        return repeatingEffect;
    }

    /**
     * @return the object affected by this effect. Ex. AbilityScore.CHARISMA, or
     * a String if the modifier isn't supported as a enumeration.
     */
    public abstract EffectTarget getTarget();

    /**
     * @return the description for this effect, or null if none is given
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the modifier of this effect for the affected statistic
     */
    public int getModifier() {
        return modifier;
    }

    /**
     * Simulates the passage of time with the passed rounds. Each round
     * represents 6 seconds.
     *
     * @param rounds the rounds that have passed
     */
    public void roundsPassed(int rounds) {
        if (!permanent) {
            roundsLeft -= rounds;
        }
    }

    public int getRoundsLeft() {
        return roundsLeft;
    }

    public boolean isPermanent() {
        return permanent;
    }

    @Override
    public abstract String toString();
}
