package character;

import diceroller.DiceRoller;

/**
 * Health management for a character.
 *
 * @author Japhez
 */
public class CharacterHealth {

    private Player player;
    private int maxHitPoints;
    private int wounds;
    private int nonlethalDamage;

    public CharacterHealth(Player player) {
        this.player = player;
        wounds = 0;
        nonlethalDamage = 0;
    }

    //Roll initial hitpoints based off of character class
    public void rollInitialHitPoints() {
        maxHitPoints = DiceRoller.rollDice(1, player.getClassInfo().getInitialClass().getHitDie()).getTotalRoll();
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public void takeDamage(int damage) {
        wounds += damage;
    }

    public void takeNonlethalDamage(int damage) {
        nonlethalDamage += damage;
    }

    public int getCurrentHealth() {
        return maxHitPoints - wounds;
    }

    public int getNonlethalDamage() {
        return nonlethalDamage;
    }

    public void heal(int hitpoints) {
        if (hitpoints > wounds) {
            wounds = 0;
        } else {
            wounds -= hitpoints;
        }
    }
}
