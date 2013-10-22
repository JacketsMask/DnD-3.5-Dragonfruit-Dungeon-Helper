//package interfaces;
//
//import character.CharacterDefense;
//import character.EffectDescription;
//import character.Player;
//import diceroller.DiceRoll;
//import diceroller.DiceRoller;
//import enumerations.AbilityScore;
//import enumerations.Skill;
//
///**
// * A condition is a negative effect that applies to a character or creature. The
// * condition is activated by calling the activate() method, which directly
// * applies the conditional effects to the passed entity. (Ex. Modifies speed) At
// * the conclusion of the condition, deactivate() should be called to revert the
// * effects placed upon the entity.
// *
// * @author Jacob Dorman
// */
//public abstract class Condition
//{
//
//    protected Player player;
//    protected String name;
//    protected String description;
//    protected EffectDescription effect;
//    protected int roundsRemaining;
//    protected boolean removedAtRest;
//    protected boolean active;
//    protected boolean permanent;
//
//    /**
//     * Activates this condition and applies its effects to the passed player.
//     *
//     * @param player
//     */
//    public void activate(Player player)
//    {
//        active = true;
//    }
//
//    /**
//     * Deactivate this condition and remove its effects. Deactivated conditions
//     * will be removed from the condition list of a given entity.
//     *
//     * @param player
//     */
//    public void deactivate()
//    {
//        active = false;
//    }
//
//    public String getName()
//    {
//        return name;
//    }
//
//    public boolean isPermanent()
//    {
//        return permanent;
//    }
//
//    public void setPermanent(boolean permanent)
//    {
//        this.permanent = permanent;
//    }
//
//    /**
//     * Adjusts the effects of this condition if healing by restoration affect
//     * it.
//     *
//     * @param points the points healed by restoration
//     */
//    public void restoration(int points)
//    {
//    }
//
//    /**
//     * Adjusts this condition appropriately for a night's rest
//     */
//    public void rest()
//    {
//        if (removedAtRest)
//        {
//            active = false;
//        }
//        dayPassed();
//    }
//
//    /**
//     * Adjusts this condition appropriately for a day's passing
//     */
//    public void dayPassed()
//    {
//    }
//
//    /**
//     * @return true if this condition is removed by a night's rest
//     */
//    public boolean isRemovedAtRest()
//    {
//        return removedAtRest;
//    }
//
//    /**
//     * @return a description of what this has done, and how long it will last
//     */
//    public String getDescription()
//    {
//        return description;
//    }
//
//    /**
//     * @return true if this condition affects the player ability score
//     */
//    public boolean affectsAbilityScore()
//    {
//        return false;
//    }
//
//    /**
//     * @return the number of rounds remaining for this condition, or 99 if the
//     * effect cannot wear off naturally.
//     */
//    public int getRoundsRemaining()
//    {
//        return roundsRemaining;
//    }
//
//    /**
//     * @return true if this condition is still active
//     */
//    public boolean isActive()
//    {
//        return active;
//    }
//
//    /**
//     * To be called when rounds pass. This usually will result in the condition
//     * losing one more remaining round. Subtracts the passed rounds from the
//     * remaining rounds.
//     */
//    public void roundsPassed(int rounds)
//    {
//        roundsRemaining -= rounds;
//        if (roundsRemaining > 0)
//        {
//            roundsRemaining--;
//        }
//    }
//
//    /**
//     * This is a special form of ability damage that cannot be magically or
//     * psionically healed. It is caused by the use of certain psionic feats and
//     * powers. It returns only through natural healing.
//     *
//     * @author Jacob Dorman
//     */
//    public static class AbilityBurned extends AbilityDamaged
//    {
//
//        public AbilityBurned(AbilityScore statAffected, int value)
//        {
//            super(statAffected, value);
//            name = "Ability Burned";
//            effect = new EffectDescription("Ability Burn: " + statAffected, value);
//        }
//
//        /**
//         * Restoration doesn't affect Ability Burn.
//         *
//         * @param points
//         */
//        @Override
//        public void restoration(int points)
//        {
//        }
//    }
//
//    /**
//     * The base form of ability damage that can be healed by restoration.
//     */
//    public static class AbilityDamaged extends Condition
//    {
//
//        private int value;
//        private AbilityScore modifiedStat;
//
//        public AbilityDamaged(AbilityScore statAffected, int value)
//        {
//            name = "Ability Damaged";
//            effect = new EffectDescription("Ability Damage: " + statAffected, value);
//            this.value = value;
//            modifiedStat = statAffected;
//        }
//
//        @Override
//        public void activate(Player player)
//        {
//            active = true;
//            this.player = player;
//            //Apply Ability Score penalty to the player
//            player.getAbilityScore().modifyAbilityScoreBases(modifiedStat, -value);
//        }
//
//        @Override
//        public void deactivate()
//        {
//            active = false;
//        }
//
//        /**
//         * Restoration removes ability damage. If there is no damage remaining,
//         * this condition becomes inactive.
//         *
//         * @param points the points to be restored
//         */
//        @Override
//        public void restoration(int points)
//        {
//            //If the player is being healed all of the damage, restore only
//            //the remaining value taken away
//            if (value <= points)
//            {
//                player.getAbilityScore().modifyAbilityScoreBonuses(modifiedStat, value);
//                value = 0;
//                deactivate();
//            } else
//            {
//                //Add back in the points removed
//                player.getAbilityScore().modifyAbilityScoreBonuses(modifiedStat, points);
//                value -= points;
//            }
//            effect.setValue(value);
//        }
//
//        @Override
//        public int getRoundsRemaining()
//        {
//            return 99;
//        }
//
//        @Override
//        public String getDescription()
//        {
//            return "You've lost " + value + " points of "
//                    + modifiedStat + ". This can only be naturally healed.";
//        }
//
//        @Override
//        public void rest()
//        {
//            //If rest would fully heal the damage
//            if (value - 2 <= 0)
//            {
//                player.getAbilityScore().modifyAbilityScoreBonuses(modifiedStat, value);
//                value = 0;
//                deactivate();
//            } //If rest wouldn't fully heal the damage, just restore 2 points
//            else
//            {
//                player.getAbilityScore().modifyAbilityScoreBonuses(modifiedStat, 2);
//                value -= 2;
//            }
//            effect.setValue(value);
//        }
//
//        @Override
//        public void dayPassed()
//        {
//            //If rest would fully heal the damage
//            if (value - 1 <= 0)
//            {
//                player.getAbilityScore().modifyAbilityScoreBonuses(modifiedStat, value);
//                value = 0;
//                deactivate();
//            } //If rest wouldn't fully heal the damage, just restore 1 point
//            else
//            {
//                player.getAbilityScore().modifyAbilityScoreBonuses(modifiedStat, 1);
//                value -= 1;
//            }
//            effect.setValue(value);
//        }
//    }
//
//    /**
//     * Ability drain is a supernatural attack that drains ability score, and
//     * cannot be naturally healed. It is healed through restoration, however.
//     */
//    public static class AbilityDrained extends AbilityDamaged
//    {
//
//        private int value;
//        private AbilityScore abilityScoreModified;
//
//        public AbilityDrained(AbilityScore abilityScoreModified, int value)
//        {
//            super(abilityScoreModified, value);
//            name = "Ability Drained";
//            effect = new EffectDescription("Ability Drain: " + abilityScoreModified, value);
//            this.value = value;
//            this.abilityScoreModified = abilityScoreModified;
//        }
//
//        @Override
//        public void rest()
//        {
//        }
//
//        @Override
//        public void dayPassed()
//        {
//        }
//
//        @Override
//        public void restoration(int points)
//        {
//            //If the player is being healed all of the damage, restore only
//            //the remaining value taken away
//            if (value <= points)
//            {
//                player.getAbilityScore().modifyAbilityScoreBonuses(abilityScoreModified, value);
//                value = 0;
//                deactivate();
//            } else
//            {
//                //Add back in the points removed
//                player.getAbilityScore().modifyAbilityScoreBonuses(abilityScoreModified, points);
//                value -= points;
//            }
//            effect.setValue(value);
//        }
//
//        @Override
//        public String getDescription()
//        {
//            return "The ability that some creatures have to drain ability "
//                    + "scores is a supernatural one, requiring some sort of "
//                    + "attack. This effect permanently reduces a living "
//                    + "opponent’s ability score when the creature hits with a "
//                    + "melee attack, though restoration can restore even those "
//                    + "lost ability score points.";
//        }
//    }
//
//    /**
//     * The character cannot see. He takes a –2 penalty to Armor Class, loses his
//     * Dexterity bonus to AC (if any), moves at half speed, and takes a –4
//     * penalty on Search checks and on most Strength- and Dexterity-based skill
//     * checks. //TODO: Fill in activate and deactivate once I have AC
//     */
//    public static class Blinded extends Condition
//    {
//
//        public Blinded(int duration)
//        {
//            name = "Blinded";
//            effect = new EffectDescription("Blinded: ", duration);
//            roundsRemaining = duration;
//        }
//
//        @Override
//        public void activate(Player player)
//        {
//            active = true;
//            this.player = player;
//            CharacterDefense defense = player.getDefense();
//            defense.modifyEnhancementBonuses(-2);
//            defense.setUsingDexBonus(false);
//            Skill[] dexSkills = Skill.getDexSkills();
//            Skill[] strSkills = Skill.getStrSkills();
//        }
//
//        @Override
//        public void deactivate()
//        {
//            super.deactivate();
//        }
//
//        @Override
//        public String getDescription()
//        {
//            return "You cannot see. You take a –2 penalty to Armor "
//                    + "Class, lose you Dexterity bonus to AC (if any), move "
//                    + "at half speed, and take a –4 penalty on Search checks "
//                    + "and on most Strength- and Dexterity-based skill checks. "
//                    + "All checks and activities that rely on vision (such as "
//                    + "reading and Spot checks) automatically fail. All "
//                    + "opponents are considered to have total concealment "
//                    + "(50% miss chance) to the blinded character.";
//        }
//
//        @Override
//        public void roundsPassed(int rounds)
//        {
//            super.roundsPassed(rounds);
//            if (roundsRemaining < 1)
//            {
//                active = false;
//            }
//        }
//    }
//
//    /**
//     * Depending on its size, a creature can be blown away by winds of high
//     * velocity. A creature on the ground that is blown away is knocked down and
//     * rolls 1d4 × 10 feet, taking 1d4 points of nonlethal damage per 10 feet. A
//     * flying creature that is blown away is blown back 2d6 × 10 feet and takes
//     * 2d6 points of nonlethal damage due to battering and buffering.
//     */
//    public static class BlownAway extends Condition
//    {
//
//        public BlownAway()
//        {
//            DiceRoll rollDice = DiceRoller.rollDice(1, 4);
//            int totalRoll = rollDice.getTotalRoll();
//            //distance = (totalRoll * 10);
//            //If flying
//            //damage = DiceRoller.rollDice(1, 4).getTotalRoll() * (distance / 10);
//            roundsRemaining = 0;
//        }
//
//        @Override
//        public String getDescription()
//        {
//            return "Depending on its size, a creature can be blown away by winds"
//                    + " of high velocity. A creature on the ground that is blown"
//                    + " away is knocked down and rolls 1d4 × 10 feet, taking 1d4"
//                    + " points of nonlethal damage per 10 feet. A flying "
//                    + "creature that is blown away is blown back 2d6 × 10 feet "
//                    + "and takes 2d6 points of nonlethal damage due to battering"
//                    + " and buffering. ";
//        }
//    }
//
//    /**
//     * Prevented from achieving forward motion by an applied force, such as
//     * wind. Checked creatures on the ground merely stop. Checked flying
//     * creatures move back a distance specified in the description of the
//     * effect.
//     */
//    public static class Checked extends Condition
//    {
//
//        public Checked()
//        {
//        }
//
//        @Override
//        public String getDescription()
//        {
//            return "You are prevented from achieving forward motion by an "
//                    + "applied force, such as wind. If you are on the ground "
//                    + "you cannot move forward. If you're flying, move back a "
//                    + "distance. ";
//
//        }
//    }
//
//    /**
//     * A confused character’s actions are determined by rolling d% at the
//     * beginning of his turn: d% Action 01–10 Attack caster with melee or ranged
//     * weapons (or close with caster if attacking is not possible) 11–20 Act
//     * normally 21–50 Do nothing but babble incoherently 51–70 Flee away from
//     * caster at top possible speed 71–100 Attack nearest creature (for this
//     * purpose, a familiar counts as part of the subject’s self ).
//     *
//     * A confused character who can’t carry out the indicated action does
//     * nothing but babble incoherently. Attackers are not at any special
//     * advantage when attacking a confused character. Any confused character who
//     * is attacked automatically attacks its attackers on its next turn, as long
//     * as it is still confused when its turn comes. A confused character does
//     * not make attacks of opportunity against any creature that it is not
//     * already devoted to attacking (either because of its most recent action or
//     * because it has just been attacked).
//     */
//    public static class Confused extends Condition
//    {
//
//        public Confused(int duration)
//        {
//            roundsRemaining = duration;
//            int totalRoll = DiceRoller.rollDice(1, 100).getTotalRoll();
//            if (totalRoll < 11)
//            {
//                description = "You must attack attack your attacker with"
//                        + " melee or ranged weapons (or close with caster if "
//                        + "attacking is not possible).";
//            } else if (totalRoll < 21)
//            {
//                description = "You may act normally.";
//            } else if (totalRoll < 51)
//            {
//                description = "You can do nothing but babble incoherently.";
//            } else if (totalRoll < 71)
//            {
//                description = "You must flee away from the confusion caster at "
//                        + "top speed!";
//            } else
//            {
//                description = "You must attack the nearest creature (not "
//                        + "including your familiar, if you have one).";
//            }
//        }
//
//        @Override
//        public void roundsPassed(int rounds)
//        {
//            super.roundsPassed(rounds);
//            if (roundsRemaining < 1)
//            {
//                active = false;
//            }
//        }
//
//        @Override
//        public String getDescription()
//        {
//            return description;
//        }
//    }
//
//    public static class Cowering extends Condition
//    {
//    }
//
//    public static class Dazed extends Condition
//    {
//    }
//
//    public static class Dazzled extends Condition
//    {
//    }
//
//    public static class Dead extends Condition
//    {
//    }
//
//    public static class Deafened extends Condition
//    {
//    }
//
//    public class Disabled extends Condition
//    {
//    }
//
//    public class Dying extends Condition
//    {
//    }
//
//    public class EnergyDrained extends Condition
//    {
//    }
//
//    public class Entangled extends Condition
//    {
//    }
//
//    public class Exhausted extends Condition
//    {
//    }
//
//    public class Fascinated extends Condition
//    {
//    }
//
//    public class Fatigued extends Condition
//    {
//    }
//
//    public class FlatFooted extends Condition
//    {
//    }
//
//    public class Frightened extends Condition
//    {
//    }
//
//    public class Grappling extends Condition
//    {
//    }
//
//    public class Helpless extends Condition
//    {
//    }
//
//    public class Incorporeal extends Condition
//    {
//    }
//
//    public class Invisible extends Condition
//    {
//    }
//
//    public class KnockedDown extends Condition
//    {
//    }
//
//    public class Nauseated extends Condition
//    {
//    }
//
//    public class Panicked extends Condition
//    {
//    }
//
//    public class Paralyzed extends Condition
//    {
//    }
//
//    public class Petrified extends Condition
//    {
//    }
//
//    public class Pinned extends Condition
//    {
//    }
//
//    public class Prone extends Condition
//    {
//    }
//
//    public class Shaken extends Condition
//    {
//    }
//
//    public class Sickened extends Condition
//    {
//    }
//
//    public class Stable extends Condition
//    {
//    }
//
//    public class Staggered extends Condition
//    {
//    }
//
//    public class Stunned extends Condition
//    {
//    }
//
//    public class Turned extends Condition
//    {
//    }
//
//    public class Unconscious extends Condition
//    {
//    }
//}