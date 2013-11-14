package character;

import character.inventory.Weapon;
import diceroller.DiceRoll;
import diceroller.DiceRoller;
import main.SaveStateTracker;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Holds the statistics related to character attacks, such as attack roll,
 * damage roll, and a list of the character's weapons.
 *
 * @author Japhez
 */
public class CharacterAttack extends SaveStateTracker implements Serializable {

    private static final long serialVersionUID = 1L;
    private int attackRoll;
    private int damageRollBonus;
    private ArrayList<Weapon> equippedWeapons;

    public CharacterAttack() {
        super();
        attackRoll = 0;
        damageRollBonus = 0;
        equippedWeapons = new ArrayList<>();
    }

    /**
     * Adds the passed item to the equipped items list if it is not already in
     * the list.
     *
     * @param weapon the weapon to equip
     */
    public void equipWeapon(Weapon weapon) {
        if (!equippedWeapons.contains(weapon)) {
            equippedWeapons.add(weapon);
        }
        super.stateChanged = true;
    }

    /**
     * Removes the passed weapon if it is currently equipped.
     *
     * @param weapon
     */
    public void unequipWeapon(Weapon weapon) {
        if (equippedWeapons.contains(weapon)) {
            equippedWeapons.remove(weapon);
        }
        super.stateChanged = true;
    }

    /**
     * Rolls an attack value using a D20, adds the attack modifier, and then
     * returns the result.
     *
     * @return the result
     */
    public int rollAttack() {
        return (DiceRoller.rollD20() + attackRoll);
    }

    /**
     * Rolls and returns damage from each equipped weapon.
     *
     * @return
     */
    public int rollDamage() {
        int damage = 0;
        for (Weapon w : equippedWeapons) {
            int dice = w.getDice();
            int sides = w.getSides();
            DiceRoll rollDice = DiceRoller.rollDice(dice, sides);
            damage += rollDice.getTotalRoll() + damageRollBonus;
        }
        return damage;
    }
}
