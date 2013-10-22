package character.inventory;

/**
 * TODO: Enumerate the different types of armor that can be equipped at the same
 * time
 *
 * @author Japhez
 */
public class Armor extends Item {

    private int acBonus;
    private int maxDexBonus;

    public Armor(String name, String description, int weight, int acBonus, int maxDexBonus) {
        super(name, description, weight);
        this.acBonus = acBonus;
        this.maxDexBonus = maxDexBonus;
    }

    public int getAcBonus() {
        return acBonus;
    }

    public int getMaxDexBonus() {
        return maxDexBonus;
    }
}
