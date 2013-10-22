/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package character.effects;

/**
 * An enumeration listing all of the effects that can be tracked by the
 * EffectManager.
 *
 * @author Japhez
 */
public enum EffectTarget {

    //Ability Score
    ABILITY_SCORE,
    //Skills
    SKILL,
    //Defense
    ARMOR_CLASS, FORTITUDE_SAVE, REFLEX_SAVE, WILL_SAVE,
    //Attack
    ATTACK_ROLL, DAMAGE_ROLL,
    //Speed
    SPEED,
    //Health
    HITPOINTS_MAX, HITPOINTS_CURRENT,
    //Conditions
    CHARACTER_CONDITION
}
