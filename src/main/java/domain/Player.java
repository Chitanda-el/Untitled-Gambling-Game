package domain;

import domain.items.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Manages the player's state for a given run, including their
 * current money, outstanding debt, and item inventory.
 */
public class Player {
    private int shopMoney;        // Money that buys items.
    private int debt;             // Debt requirement
    private final List<Item> inventory;  // Player's inventory of items
    
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
        this.inventory = new ArrayList<>();
    }

    // ----- -------- --- --------- ----- -------- --- --------- -----
    // ----- MUTATORS AND ACCESSORS ----- MUTATORS AND ACCESSORS ----- 
    // ----- -------- --- --------- ----- -------- --- --------- -----

    // ----- ----- ----- ----- -----
    // ----- MONEY ----- MONEY -----
    // ----- ----- ----- ----- -----
    
    /**
     * Adds the amount of money passed to the player's money.
     * 
     * @param amount of money (positive or negative) to add to shopMoney.
     */
    public void addMoney(int amount) {
        this.shopMoney += amount;
    }
    
    /**
     * Sets the amount of money that the player has.
     * Server a function for save/load.
     * 
     * @param amount of money to set shopMoney to.
     */
    public void setMoney(int amount) {
        this.shopMoney = amount;
    }
    
    /**
     * @return the amount of money that the player currently has.
     */
    public int getMoney() {
        return shopMoney;
    }
      
    // ----- ---- ----- ---- ----- 
    // ----- DEBT ----- DEBT -----
    // ----- ---- ----- ---- -----
    
    /**
     * Sets the player's new debt requirement.
     * 
     * @param newDebtRequirement is the amount to set the debt to.
     */
    public void setDebt(int newDebtRequirement) {
        this.debt = newDebtRequirement;
    }
    
    /**
     * @return the player's current outstanding debt
     */
    public int getDebt() {
        return debt;
    }
    
    // ----- --------- ----- --------- -----
    // ----- INVENTORY ----- INVENTORY -----
    // ----- --------- ----- --------- -----
    
    /**
     * Adds an item to the player's inventory.
     * 
     * @param item to add to the player inventory.
     */
    public void addItem(Item item) {
        inventory.add(item);
    }
    
    /**
     * Clear the player's inventory; useful for save/load
     */
    public void clearInventory() {
        inventory.clear();
    }
    
    // Sets the inventory to a whole new one. Serves a purpose for save/load.
    public void setInventory(List<Item> newInv) {
        inventory.clear();
        inventory.addAll(newInv);
    }
    
    /**
     * @return a list of the player's current item inventory.
     */
    public List<Item> getInventory() {
        return inventory;
    }
    /**
     * Takes a subclass which extends Item as its argument and checks whether
     * any item which is an instance of the passed subclass is in the player's
     * inventory. If it is, then it returns a reference to that item or null
     * otherwise.
     * 
     * @param <T> any type which extends Item.
     * @param itemQuery a class which extends Item to search for.
     * @return null if no object which is an instance of itemQuery is found or,
     *         if an appropriate item is found, return a reference to it.
     */
    public <T extends Item> T checkInventory(Class<T> itemQuery) {
        for (Item itemInInventory : inventory) {
            if (itemQuery.isInstance(itemInInventory)) {
                return itemQuery.cast(itemInInventory);
            }
        }
        return null;
    }
}