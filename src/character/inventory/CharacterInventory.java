package character.inventory;

import character.Player;
import enumerations.AbilityScore;
import main.SaveStateSender;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * An inventory holds a wallet, as well as items, whether they be miscellaneous,
 * weapons, armor, wands, scrolls, or something else entirely.
 *
 * @author Jacob Dorman
 */
public class CharacterInventory extends SaveStateSender implements Serializable {

    private static final long serialVersionUID = 1L;
    private transient Player player;
    private Wallet wallet;
    private ArrayList<Item> items;

    public CharacterInventory(Player player) {
        this.player = player;
        wallet = new Wallet();
        items = new ArrayList<>();
        super.stateChanged();
    }

    /**
     * @return the current load for this player
     */
    public CarryingCapacity.Load getCurrentLoad() {
        int abilityScoreBaseValue = player.getAbilityScore().getAbilityScoreBaseValue(AbilityScore.STRENGTH);
        int abilityScoreBonusValue = player.getAbilityScore().getAbilityScoreBonusValue(AbilityScore.STRENGTH);
        int strength = abilityScoreBaseValue + abilityScoreBonusValue;
        double totalWeight = getInventoryWeight() + getWallet().getWeight();
        return CarryingCapacity.getLoad(strength, player.getBasicInfo().getSize(), false, Math.round((float) (totalWeight)));
    }

    /**
     * @return an object holding current load thresholds for this player
     */
    public CarryingCapacity.LoadThresholds getCurrentLoadThresholds() {
        int abilityScoreBaseValue = player.getAbilityScore().getAbilityScoreBaseValue(AbilityScore.STRENGTH);
        int abilityScoreBonusValue = player.getAbilityScore().getAbilityScoreBonusValue(AbilityScore.STRENGTH);
        int strength = abilityScoreBaseValue + abilityScoreBonusValue;
        return CarryingCapacity.getLoadThresholds(strength, player.getBasicInfo().getSize(), false);
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
        super.stateChanged();
    }

    /**
     * This returns as an array to discourage direct modification of the ArrayList.
     * @return an array of the current items in the inventory
     */
    public Item[] getItems() {
        return (items.toArray(new Item[items.size()]));
    }

    /**
     * @return the weight of all the items currently in the inventory
     */
    public double getInventoryWeight() {
        double weight = 0;
        for (int i = 0; i < items.size(); i++) {
            weight += items.get(i).getWeight();
        }
        return weight;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addItem(Item item) {
        items.add(item);
        super.stateChanged();
    }

    public void removeItem(Item item) {
        items.remove(item);
        super.stateChanged();
    }

    @Override
    public void stateSaved() {
        super.stateSaved();
        wallet.stateSaved();
    }

    @Override
    public boolean isStateChanged() {
        return (super.isStateChanged() || wallet.isStateChanged());
    }
}
