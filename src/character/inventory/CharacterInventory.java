package character.inventory;

import interfaces.SaveStateTracker;
import javax.swing.DefaultListModel;

/**
 * An inventory holds a wallet, as well as items, whether they be miscellaneous,
 * weapons, armor, wands, scrolls, or something else entirely.
 *
 * @author Jacob Dorman
 */
public class CharacterInventory extends SaveStateTracker {

    private Wallet wallet;
    private DefaultListModel<Item> itemList;

    public CharacterInventory() {
        wallet = new Wallet();
        itemList = new DefaultListModel<>();
        super.stateChanged = true;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
        super.stateChanged = true;
    }

    public DefaultListModel<Item> getItemList() {
        return itemList;
    }

    /**
     * @return the weight of all the items currently in the inventory
     */
    public double getInventoryWeight() {
        double weight = 0;
        for (int i = 0; i < itemList.size(); i++) {
            weight += itemList.get(i).getWeight();
        }
        return weight;
    }
}
