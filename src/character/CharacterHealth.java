package character;

import diceroller.DiceRoller;
import savestate.StateSender;
import java.io.Serializable;

/**
 * Health management for a character.
 *
 * @author Japhez
 */
public class CharacterHealth extends StateSender implements Serializable {

    private static final long serialVersionUID = 1L;
    private transient Player player;
    private int maxHitPoints;
    private int wounds;
    private int nonlethalDamage;

    public CharacterHealth(Player player) {
        this.player = player;
        wounds = 0;
        nonlethalDamage = 0;
        super.stateChanged();
    }

    //Roll initial hitpoints based off of character class
    public void rollInitialHitPoints() {
        maxHitPoints = DiceRoller.rollDice(1, player.getClassInfo().getInitialClass().getHitDie()).getTotalRoll();
        super.stateChanged();
    }

    public int getMaxHitPoints() {
        super.stateChanged();
        return maxHitPoints;
    }

    public void takeDamage(int damage) {
        wounds += damage;
        super.stateChanged();
    }

    public void takeNonlethalDamage(int damage) {
        nonlethalDamage += damage;
        super.stateChanged();
    }

    public int getCurrentHealth() {
        super.stateChanged();
        return maxHitPoints - wounds;
    }

    public int getNonlethalDamage() {
        super.stateChanged();
        return nonlethalDamage;
    }

    public void heal(int hitpoints) {
        if (hitpoints > wounds) {
            wounds = 0;
        } else {
            wounds -= hitpoints;
        }
        super.stateChanged();
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
