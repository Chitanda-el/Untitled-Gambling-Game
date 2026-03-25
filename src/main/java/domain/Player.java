package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the player's state for a given run, including their
 * current money, outstanding debt, and item inventory.
 */
public class Player {
    private int shopMoney;  // Money that buys items.
    private int debt;       // Debt requirement
    private final List<Item> inventory;
    
    /**
     * Constructs a Player object.
     * 
     * @param startingMoney is the amount of money the player has to spend in
     *                      the shop initially.
     * @param initialDebt the initial debt requirement for the player.
     */
    public Player(int startingMoney, int initialDebt) {
        this.shopMoney = startingMoney;
        this.debt = initialDebt;
        this.inventory = new ArrayList<>(); // I'm a Java newbie
    }


    // ----- -------- --- --------- ----- -------- --- --------- -----
    // ----- MUTATORS AND ACCESSORS ----- MUTATORS AND ACCESSORS ----- 
    // ----- -------- --- --------- ----- -------- --- --------- -----


    // ----- MONEY ----- MONEY ----- MONEY ----- MONEY ----- MONEY ----- 
    // Mutator: that modifies the player's money
    public void addMoney(int amount) {
        this.shopMoney += amount;
    }
    
    // Accessor: return's the player's current money
    public int getMoney() {
        return shopMoney;
    }
    // ----- END\MONEY ----- END\MONEY ----- END\MONEY ----- END\MONEY -----
    
    // ----- DEBT ----- DEBT ----- DEBT ----- DEBT ----- DEBT ----- DEBT -----
    // Mutator: sets the player's new debt requirement
    public void setDebt(int newDebtRequirement) {
        this.debt = newDebtRequirement;
    }
    
    // Accessor: returns the player's current outstanding debt
    public int getDebt() {
        return debt;
    }
    // ----- END\DEBT ----- END\DEBT ----- END\DEBT ----- END\DEBT -----
    
    // ----- INVENTORY ----- INVENTORY ----- INVENTORY ----- INVENTORY -----
    // Mutator: adds an item to the player's inventory
    public void addItem(Item item) {
        inventory.add(item);
    }
    
    // TODO: getInventory()
    // returns the items in the player inventory. How this is handled
    // varies depending on how Items is handled.
    // ----- END\INVENTORY ----- END\INVENTORY ----- END\INVENTORY ----- 
}
