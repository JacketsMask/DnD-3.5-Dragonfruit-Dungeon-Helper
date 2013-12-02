package character.effects;

import character.CharacterAttack;
import character.CharacterDefense;
import character.Player;
import enumerations.AbilityScore;
import main.SaveStateSender;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 * Manages effect activation and deactivation to a character.
 *
 * @author Jacob Dorman
 */
public class EffectManager extends SaveStateSender implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<Effect> permanentEffects;
    private ArrayList<Effect> transientEffects;
    private transient Player player;

    public EffectManager(Player character) {
        permanentEffects = new ArrayList<>();
        transientEffects = new ArrayList<>();
        this.player = character;
        super.stateChanged();

    }

    /**
     * Activates the effects of the passed Affectable.
     *
     * @param effect
     */
    public void activate(Effect effect) {
        System.out.println("new effect activated");
        EffectTarget target = effect.getTarget();
        //Add the effect to its appropriate list
        if (effect.isPermanent()) {
            permanentEffects.add(effect);
        } else {
            transientEffects.add(effect);
        }
        //The effect affects ability score
        if (target == EffectTarget.ABILITY_SCORE) {
            AbilityScoreEffect abilityEffect = (AbilityScoreEffect) effect;
            //Activate the effect
            AbilityScore targetAbilityScore = abilityEffect.getTargetAbilityScore();
            int modifier = abilityEffect.getModifier();
            player.getAbilityScore().modifyAbilityScoreBonuses(targetAbilityScore, modifier);
            return;
        }
        //The effect affects attack roll
        if (target == EffectTarget.ATTACK_ROLL) {
            //Activate the effect
            player.getAttack();
            return;
        }
        //The effect affects damage roll
        if (target == EffectTarget.DAMAGE_ROLL) {
            CharacterAttack attack = player.getAttack();
            return;
        }
        //The effect affects AC
        if (target == EffectTarget.ARMOR_CLASS) {
            CharacterDefense defense = player.getDefense();
            return;
        }
        //The effect affects speed
        if (target == EffectTarget.SPEED) {
            return;
        }
        //The effect affects hit points
        if (target == EffectTarget.HITPOINTS_CURRENT || target == EffectTarget.HITPOINTS_MAX) {
            return;
        }
        //The effect affects saving throws
        if (target == EffectTarget.FORTITUDE_SAVE || target == EffectTarget.REFLEX_SAVE || target == EffectTarget.WILL_SAVE) {
            return;
        }
        //The effect affects conditions
        if (target == EffectTarget.CHARACTER_CONDITION) {
            return;
        }
        super.stateChanged();
    }

    public void deactivate(Effect effect) {
        EffectTarget target = effect.getTarget();
        //Add the effect to its appropriate list
        if (effect.isPermanent()) {
            permanentEffects.remove(effect);
        } else {
            transientEffects.remove(effect);
        }
        //The effect affects ability score
        if (target == EffectTarget.ABILITY_SCORE) {
            AbilityScoreEffect abilityEffect = (AbilityScoreEffect) effect;
            //Deactivate the effect
            AbilityScore targetAbilityScore = abilityEffect.getTargetAbilityScore();
            //Reverse the effect and apply it
            int modifier = -abilityEffect.getModifier();
            player.getAbilityScore().modifyAbilityScoreBonuses(targetAbilityScore, modifier);
            return;
        }
        //The effect affects attack roll
        if (target == EffectTarget.ATTACK_ROLL) {
            //Activate the effect
            player.getAttack();
            return;
        }
        //The effect affects damage roll
        if (target == EffectTarget.DAMAGE_ROLL) {
            player.getAttack();
            return;
        }
        //The effect affects AC
        if (target == EffectTarget.ARMOR_CLASS) {
            CharacterDefense defense = player.getDefense();
            return;
        }
        //The effect affects speed
        if (target == EffectTarget.SPEED) {
            return;
        }
        //The effect affects hit points
        if (target == EffectTarget.HITPOINTS_CURRENT || target == EffectTarget.HITPOINTS_MAX) {
            return;
        }
        //The effect affects saving throws
        if (target == EffectTarget.FORTITUDE_SAVE || target == EffectTarget.REFLEX_SAVE || target == EffectTarget.WILL_SAVE) {
            return;
        }
        //The effect affects conditions
        if (target == EffectTarget.CHARACTER_CONDITION) {
            return;
        }
        super.stateChanged();
    }

    public void roundsPassed(int rounds) {
        //Update each transient effect for the passed rounds
        super.stateChanged();
    }

    /**
     * Returns a new DefaultListModel containing all of the effects of the
     * passed EffectTarget type.
     *
     * @param effectTarget the target effect to make a list for
     * @return a new DefaultListModel containing all of the Effects that affect
     * the passed target
     */
    public DefaultListModel getDefaultListModel(EffectTarget effectTarget) {
        DefaultListModel model = new DefaultListModel();
        //Add each relevant permanent effect to the list
        for (Effect e : permanentEffects) {
            //If the target is the same, add it to the model
            if (e.getTarget().equals(effectTarget)) {
                model.addElement(e);
                System.out.println("effect");
            }
        }
        //Add each relevant transient 
        for (Effect e : transientEffects) {
            //If the target is the same, add it to the model
            if (e.getTarget().equals(effectTarget)) {
                model.addElement(e);
            }
        }
        return model;
    }

    /**
     * Oh man I wish this wasn't necessary.  But it's required to get object
     * associations right after de-serialization.
     * 
     * Basically this should only be called to assign the "new" player that 
     * isn't de-serialized, but rather created at program launch.
     * @param player 
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
