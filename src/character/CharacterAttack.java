package character;

import character.classes.CharacterClass;
import character.inventory.Weapon;
import diceroller.DiceRoll;
import diceroller.DiceRoller;
import main.SaveStateSender;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Holds the statistics related to character attacks, such as attack roll,
 * damage roll, and a list of the character's weapons.
 *
 * @author Japhez
 */
public class CharacterAttack extends SaveStateSender implements Serializable {

    private static final long serialVersionUID = 1L;
    private transient Player player;
    private int attackRoll;
    private int damageRollBonus;
    private ArrayList<Weapon> equippedWeapons;

    public CharacterAttack(Player player) {
        super();
        this.player = player;
        attackRoll = 0;
        damageRollBonus = 0;
        equippedWeapons = new ArrayList<>();
    }

    /**
     * @return the BAB values as an array of integers
     */
    public int[] getBAB() {
        CharacterClassInfo classInfo = player.getClassInfo();
        ArrayList<CharacterClass> characterClasses = player.getClassInfo().getCharacterClasses();
        int totalBAB = 0;
        for (CharacterClass cc : characterClasses) { //Null pointer
            int classLevel = classInfo.getClassLevel(cc);
            System.out.println("BAB at level: " + classLevel + " = " + cc.getBaseAttackBonus(classLevel));
            totalBAB += cc.getBaseAttackBonus(classLevel);
        }
        int[] bab = new int[3];
        bab[0] = totalBAB;
        if (totalBAB > 5) {
            bab[1] = totalBAB - 5;
        }
        if (totalBAB > 10) {
            bab[2] = totalBAB - 10;
        }
        return bab;
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
        super.stateChanged();
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
        super.stateChanged();
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

    public static void main(String[] args) {
        int totalBAB = 10;
        int[] bab = new int[3];
        bab[0] = totalBAB;
        if (totalBAB > 5) {
            bab[1] = totalBAB - 5;
        }
        if (totalBAB > 10) {
            bab[2] = totalBAB - 10;
        }
        for (Integer i : bab) {
            System.out.println(i + "/");
        }
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
