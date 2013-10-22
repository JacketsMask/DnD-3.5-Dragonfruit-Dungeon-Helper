package character.inventory;

/**
 * A weapon is an Item that has a number dice with a number of sides.
 *
 * @author Jacob Dorman
 */
public class Weapon extends Item {

    private int dice;
    private int sides;

    public Weapon(String name, String description, int weight, int dice, int sides) {
        super(name, description, weight);
        this.dice = dice;
        this.sides = sides;
    }

    public int getDice() {
        return dice;
    }

    public int getSides() {
        return sides;
    }

    /**
     * TODO: Figure out what was going on here.
     */
    public static enum WeaponType {

        UNARMED, LIGHT, MELEE, ONE_HANDED_MELEE, TWO_HANDED_MELEE, RANGED
    }
}
