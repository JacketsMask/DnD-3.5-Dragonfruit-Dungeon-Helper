package character.inventory;

import character.Player;
import enumerations.AbilityScore;
import main.SaveStateTracker;
import java.io.Serializable;
import javax.swing.DefaultListModel;

/**
 * An inventory holds a wallet, as well as items, whether they be miscellaneous,
 * weapons, armor, wands, scrolls, or something else entirely.
 *
 * @author Jacob Dorman
 */
public class CharacterInventory extends SaveStateTracker implements Serializable {

    private static final long serialVersionUID = 1L;
    private transient Player player;
    private Wallet wallet;
    private DefaultListModel<Item> itemListModel;

    public CharacterInventory(Player player) {
        this.player = player;
        wallet = new Wallet();
        itemListModel = new DefaultListModel<>();
        super.stateChanged = true;
    }

    public CarryingCapacity.Load getCurrentLoad() {
        int abilityScoreBaseValue = player.getAbilityScore().getAbilityScoreBaseValue(AbilityScore.STRENGTH);
        int abilityScoreBonusValue = player.getAbilityScore().getAbilityScoreBonusValue(AbilityScore.STRENGTH);
        int strength = abilityScoreBaseValue + abilityScoreBonusValue;
        double totalWeight = getInventoryWeight() + getWallet().getWeight();
        return CarryingCapacity.getLoad(strength, player.getBasicInfo().getSize(), false, Math.round((float) (totalWeight)));
    }

    /**
     * @return an object holding current load thresholds for this player.
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
        super.stateChanged = true;
    }

    public DefaultListModel<Item> getItemListModel() {
        return itemListModel;
    }

    /**
     * @return the weight of all the items currently in the inventory
     */
    public double getInventoryWeight() {
        double weight = 0;
        for (int i = 0; i < itemListModel.size(); i++) {
            weight += itemListModel.get(i).getWeight();
        }
        return weight;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
