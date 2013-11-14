package character.inventory;

import java.io.Serializable;

/**
 * Stores a player's wallet information.
 *
 * @author Jacob Dorman
 * @version Dec 3, 2012
 */
public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int COPPER_PER_PLATINUM = 1000;
    private static final int COPPER_PER_GOLD = 100;
    private static final int COPPER_PER_SILVER = 10;
    private static final int COINS_PER_POUND = 50;
    private int platinum;
    private int copper;
    private int silver;
    private int gold;
    private boolean saved;

    /**
     * Creates a new wallet that contains no coins.
     */
    public Wallet() {
        gold = 0;
        silver = 0;
        copper = 0;
    }

    /**
     * @return the current number of platinum coins
     */
    public int getPlatinum() {
        return platinum;
    }

    /**
     * @return the current number of copper coins
     */
    public int getCopper() {
        return copper;
    }

    /**
     * @return the current number of gold coins
     */
    public int getGold() {
        return gold;
    }

    /**
     * @return the current number of silver coins
     */
    public int getSilver() {
        return silver;
    }

    /**
     * Set the current platinum to the passed value.
     *
     * @param platinum
     */
    public void setPlatinum(int platinum) {
        this.platinum = platinum;
    }

    /**
     * Set the current copper to the passed value.
     *
     * @param copper
     */
    public void setCopper(int copper) {
        this.copper = copper;
    }

    /**
     * Set the current gold to the passed value.
     *
     * @param gold
     */
    public void setGold(int gold) {
        this.gold = gold;
    }

    /**
     * Set the current silver to the passed value.
     *
     * @param silver
     */
    public void setSilver(int silver) {
        this.silver = silver;
    }

    /**
     * @return true if this wallet has enough money to afford the passed cost
     */
    public boolean canAfford(int platinum, int gold, int silver, int copper) {
        double totalCostInCopper = copper + (silver * COPPER_PER_SILVER) + (gold * COPPER_PER_GOLD) + (platinum * COPPER_PER_PLATINUM);
        return (totalCostInCopper <= this.copper + (this.silver * COPPER_PER_SILVER) + (this.gold * COPPER_PER_GOLD) + (this.platinum * COPPER_PER_PLATINUM));
    }

    /**
     * Subtracts the purchase cost from this wallet, attempting to use the least
     * valuable currency and going up from there. Also retrieves change for the
     * purchase.
     *
     * @param platinum cost in platinum
     * @param gold cost in gold
     * @param silver cost in silver
     * @param copper cost in copper
     */
    public void subtractPurchaseCost(int platinum, int gold, int silver, int copper) {
        //Verify that this character can afford the purchase
        if (!canAfford(platinum, gold, silver, copper)) {
            return;
        }
        //Get the total amount of money subtracted
        int totalCostInCopper = (platinum * COPPER_PER_PLATINUM) + (gold * COPPER_PER_GOLD) + (silver * COPPER_PER_SILVER) + (copper);
        //Start by deducting the cost in copper
        totalCostInCopper = deductCopper(totalCostInCopper);
        if (totalCostInCopper == 0) {
            return;
        }
        //If necessary, deduct the cost in silver as well
        totalCostInCopper = deductSilver(totalCostInCopper);
        if (totalCostInCopper == 0) {
            return;
        }
        //If necessary, deduct the cost in gold as well
        totalCostInCopper = deductGold(totalCostInCopper);
        if (totalCostInCopper == 0) {
            return;
        }
        //If necessary, deduct the cost in platinum as well
        deductPlatinum(totalCostInCopper);

    }

    public int deductCopper(int totalValueToDeductInCopper) {
        int totalValue = totalValueToDeductInCopper;
        //First see if the item can be bought outright in copper
        if (totalValue <= this.copper) {
            this.copper -= totalValue;
            //Item is paid for, return
            //Item is fully paid off
            return 0;
        }//If it can't, deduct the current copper from the cost
        else {
            totalValue -= this.copper;
            this.copper = 0;
            //Return remaining amount to be paid off
            return totalValue;
        }
    }

    public int deductSilver(int totalValueToDeductInCopper) {
        int totalValue = totalValueToDeductInCopper;
        //See if the item can be paid off in silver
        if (totalValue <= (this.silver * COPPER_PER_SILVER)) {
            int change;
            //Find out if the item can be paid evenly with silver
            if (totalValue % COPPER_PER_SILVER == 0) {
                this.silver -= (totalValue / COPPER_PER_SILVER);
                //Item is fully paid off
                return 0;
            } //The item can be paid off, but there will be change, deduct the extra
            //silver coin to break for change
            else {
                this.silver -= (totalValue / COPPER_PER_SILVER) + 1;
                change = COPPER_PER_SILVER - (totalValue % COPPER_PER_SILVER);
                this.copper += change;
                //Item is paid for, return
                return 0;
            }

        } //If it can't be paid off, deduct the current silver from the cost
        else {
            totalValue -= (this.silver * COPPER_PER_SILVER);
            this.silver = 0;
            return totalValue;
        }
    }

    public int deductGold(int totalValueToDeductInCopper) {
        int totalValue = totalValueToDeductInCopper;
        //See if the item can be paid off in gold
        if (totalValue <= (this.gold * COPPER_PER_GOLD)) {
            int change;
            //Find out if the item can be paid evenly with gold
            if (totalValue % COPPER_PER_GOLD == 0) {
                this.gold -= (totalValue / COPPER_PER_GOLD);
                //Item is fully paid off
                return 0;
            } //The item can be paid off, but there will be change, deduct the extra
            //gold coin to break for change
            else {
                this.gold -= (totalValue / COPPER_PER_GOLD) + 1;
                //Get remaining change value in copper
                change = COPPER_PER_GOLD - (totalValue % COPPER_PER_GOLD);
                //Find silver change value
                int silverChange = (change / COPPER_PER_SILVER);
                this.silver += silverChange;
                change -= (silverChange * COPPER_PER_SILVER);
                //Find copper change value
                this.copper += change;
                //Item is paid for, return
                return 0;
            }

        } //If it can't be paid off, deduct the current gold from the cost
        else {
            totalValue -= (this.gold * COPPER_PER_GOLD);
            this.gold = 0;
            return totalValue;
        }
    }

    /**
     * Attempts to pay off the passed value with platinum.
     *
     * @param totalValueToDeductInCopper the total value to pay off
     * @return any remaining cost to be paid
     */
    public int deductPlatinum(int totalValueToDeductInCopper) {
        int totalValue = totalValueToDeductInCopper;
        //See if the item can be paid off in platinum
        if (totalValue <= (this.platinum * COPPER_PER_PLATINUM)) {
            int change;
            //Find out if the item can be paid evenly with platinum
            if (totalValue % COPPER_PER_PLATINUM == 0) {
                this.platinum -= (totalValue / COPPER_PER_PLATINUM);
                //Item is fully paid off
                return 0;
            } //The item can be paid off, but there will be change, deduct the extra
            //platinum coin to break for change
            else {
                this.platinum -= (totalValue / COPPER_PER_PLATINUM) + 1;
                //Get remaining change value in copper
                change = COPPER_PER_PLATINUM - (totalValue % COPPER_PER_PLATINUM);
                //Find gold change value
                int goldChange = (change / COPPER_PER_GOLD);
                this.gold += goldChange;
                change -= (goldChange * COPPER_PER_GOLD);
                //Find silver change value
                int silverChange = (change / COPPER_PER_SILVER);
                this.silver += silverChange;
                change -= (silverChange * COPPER_PER_SILVER);
                //Find copper change value
                this.copper += change;
                //Item is paid for, return
                return 0;
            }

        } //If it can't be paid off, deduct the current platinum from the cost
        else {
            totalValue -= (this.platinum * COPPER_PER_PLATINUM);
            this.platinum = 0;
            return totalValue;
        }
    }

    /**
     * Adds the passed money to the wallet.
     *
     * @param platinum
     * @param gold
     * @param silver
     * @param copper
     */
    public void addMoney(int platinum, int gold, int silver, int copper) {
        this.platinum += platinum;
        this.gold += gold;
        this.silver += silver;
        this.copper += copper;
    }

    /**
     * @return a string composed of lines, where each line is a type of currency
     * and the value
     */
    @Override
    public String toString() {
        return ("Platinum:" + platinum + "\nGold: " + gold + "\nSilver: " + silver + "\nCopper: " + copper);
    }

    /**
     * @return the weight of all of the coins inside this wallet
     */
    public double getWeight() {
        return (double) (copper + silver + gold + platinum) / COINS_PER_POUND;
    }

    /**
     * Sets the saved state of the wallet. This should be set to false if the
     * data is changed so that it can be re-serialized at next save.
     *
     * @param saved
     */
    public void setSaved(boolean saved) {
        this.saved = false;
    }

    /**
     * @return true if the serialized version of this wallet is currently up to
     * date
     */
    public boolean isSaved() {
        return saved;
    }
}
