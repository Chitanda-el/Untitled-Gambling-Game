// ItemShop.java
package domain;

import domain.items.Item;
import util.RandomNumGenerator;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the items available for purchase and processes transactions.
 * 
 * The shop refreshes its inventory when:
 *   1. Initially created
 *   2. Player pays for a reroll
 *   3. (Optional) After debt is repaid
 * 
 * ItemShop Responsibilities:
 *  1. Maintain a pool of all possible items (from domain.items package)
 *  2. Randomly select items for current stock (all items equally rare)
 *  3. Apply price scaling based on number of purchases made
 *  4. Process purchase transactions with the player
 *  5. Remove purchased items from current stock
 * 
 * ItemShop Collaborators:
 *  1. RandomNumGenerator: for deterministic random selection
 *  2. Player: to check funds and add purchased items
 *  3. Item subclasses: the actual items being sold
 */
public class ItemShop {
    
    // ----- CONSTANTS -----
    
    /** Number of items for sale at any given time */
    private static final int SHOP_STOCK_SIZE = 3;
    
    /** Base price multiplier for items (can be adjusted by purchase count) */
    private static final double BASE_PRICE_MULTIPLIER = 1.0;
    
    /** How much the price multiplier increases per purchase (e.g., 0.1 = +10%) */
    private static final double PRICE_SCALE_INCREMENT = 0.1;
    
    /** Maximum price multiplier cap (e.g., 3.0 = 300% of base price) */
    private static final double MAX_PRICE_MULTIPLIER = 3.0;
    
    /** Cost to reroll the shop inventory */
    private static final int REROLL_COST = 50;
    
    // ----- FIELDS -----
    
    private final RandomNumGenerator rng;
    private final List<Item> allItems;      // Complete pool of purchasable items
    private List<Item> currentStock;        // Items currently for sale
    private int totalPurchases;              // Count of items purchased (affects prices)
    private double currentPriceMultiplier;   // Current multiplier applied to all item prices
    
    // ----- CONSTRUCTORS -----
    
    /**
     * Creates a new ItemShop with the given RNG and item pool.
     * 
     * @param rng the seeded random number generator for deterministic selection
     * @param allItems list of all items that can appear in the shop
     */
    public ItemShop(RandomNumGenerator rng, List<Item> allItems) {
        this.rng = rng;
        this.allItems = new ArrayList<>(allItems);
        this.currentStock = new ArrayList<>();
        this.totalPurchases = 0;
        this.currentPriceMultiplier = BASE_PRICE_MULTIPLIER;
        
        // Initial shop refresh
        refreshShop();
    }
    
    // ----- SHOP MANAGEMENT -----
    
    /**
     * Refreshes the shop's inventory with new random items.
     * Called on initial creation and when player pays for a reroll.
     * 
     * All items have equal chance of being selected (no weighting).
     */
    public void refreshShop() {
    if (allItems == null || allItems.isEmpty()) {
        System.out.println("No items available in master pool!");
        currentStock.clear();
        return;
    }
    
    System.out.println("Refreshing shop. Master pool size: " + allItems.size());
    
    List<Item> availableItems = new ArrayList<>(allItems);
    List<Item> newStock = new ArrayList<>();
    
    for (int i = 0; i < SHOP_STOCK_SIZE && !availableItems.isEmpty(); i++) {
        int randomIndex = rng.nextInt(availableItems.size());
        Item selected = availableItems.get(randomIndex);
        System.out.println("Selected item: " + selected.getName());
        newStock.add(selected);
        availableItems.remove(randomIndex);
    }
    
    currentStock = newStock;
    System.out.println("Shop stock size after refresh: " + currentStock.size());
}
    
    /**
     * Attempts to reroll the shop inventory at a cost.
     * 
     * @param player the player attempting to reroll
     * @return true if reroll was successful (player had enough money)
     */
    public boolean rerollShop(Player player) {
        if (player.getMoney() >= REROLL_COST) {
            player.addMoney(-REROLL_COST);  // Deduct cost
            refreshShop();
            return true;
        }
        return false;
    }
    
    /**
     * Gets the cost to reroll the shop.
     * 
     * @return the reroll cost in currency
     */
    public int getRerollCost() {
        return REROLL_COST;
    }
    
    // ----- PURCHASE LOGIC -----
    
    /**
     * Processes a purchase attempt for an item in the shop.
     * 
     * Steps:
     *   1. Validate item index
     *   2. Calculate actual price with current multiplier
     *   3. Check if player can afford it
     *   4. Deduct money from player
     *   5. Add item to player's inventory
     *   6. Remove item from shop stock
     *   7. Increment purchase counter (increases future prices)
     * 
     * @param player the player making the purchase
     * @param itemIndex index of the item in currentStock
     * @return true if purchase was successful
     */
    public boolean purchaseItem(Player player, int itemIndex) {
        // Validate index
        if (itemIndex < 0 || itemIndex >= currentStock.size()) {
            return false;
        }
        
        Item item = currentStock.get(itemIndex);
        int actualPrice = calculateItemPrice(item);
        
        // Check if player can afford
        if (player.getMoney() < actualPrice) {
            return false;
        }
        
        // Process transaction
        player.addMoney(-actualPrice);
        player.addItem(item);
        
        // Remove from shop stock
        currentStock.remove(itemIndex);
        
        // Increment purchase counter (prices will increase for next purchases)
        incrementPurchaseCount();
        
        return true;
    }
    
    /**
     * Calculates the actual price of an item based on base price and
     * the current price multiplier.
     * 
     * @param item the item to price
     * @return the actual price in currency
     */
    private int calculateItemPrice(Item item) {
        int basePrice = getItemBasePrice(item);
        
        // Apply multiplier and cap at maximum
        double multipliedPrice = basePrice * currentPriceMultiplier;
        int finalPrice = (int) Math.round(multipliedPrice);
        
        // Ensure minimum price of 1
        return Math.max(1, finalPrice);
    }
    
    /**
     * Gets the base price for an item.
     * Different items can have different base prices based on their type.
     * 
     * @param item the item to price
     * @return base price in currency
     */
    private int getItemBasePrice(Item item) {
        String itemName = item.getName().toLowerCase();
        
        // Price based on item type/name
        if (itemName.contains("bottom") || itemName.contains("top")) {
            return 40;  // Row-specific unlocks
        } else if (itemName.contains("left") || itemName.contains("right") || itemName.contains("middle")) {
            return 45;  // Column-specific unlocks
        } else if (itemName.contains("analyzer")) {
            return 50;  // More expensive items
        } else {
            return 35;  // Default price for other items
        }
    }
    
    /**
     * Increments the purchase counter and updates the price multiplier.
     * Each purchase makes future items slightly more expensive.
     */
    private void incrementPurchaseCount() {
        totalPurchases++;
        
        // Calculate new multiplier: base + (purchases * increment)
        double newMultiplier = BASE_PRICE_MULTIPLIER + (totalPurchases * PRICE_SCALE_INCREMENT);
        
        // Cap at maximum multiplier
        currentPriceMultiplier = Math.min(newMultiplier, MAX_PRICE_MULTIPLIER);
    }
    
    /**
     * Resets the purchase counter and price multiplier.
     * Useful when abandoning a save file or starting a new game.
     */
    public void resetPurchaseMultiplier() {
        totalPurchases = 0;
        currentPriceMultiplier = BASE_PRICE_MULTIPLIER;
    }
    
    // ----- ACCESSORS -----
    
    /**
     * Returns the current shop inventory.
     * 
     * @return list of items currently available for purchase
     */
    public List<Item> getCurrentStock() {
        return new ArrayList<>(currentStock);
    }
    
    /**
     * Returns the number of items purchased from this shop.
     * 
     * @return total purchase count
     */
    public int getTotalPurchases() {
        return totalPurchases;
    }
    
    /**
     * Returns the current price multiplier.
     * 
     * @return current multiplier (1.0 = base price)
     */
    public double getCurrentPriceMultiplier() {
        return currentPriceMultiplier;
    }
    
    /**
     * Gets the actual price for an item at the current multiplier.
     * Useful for displaying prices in the GUI.
     * 
     * @param item the item to check
     * @return the actual price in currency
     */
    public int getItemActualPrice(Item item) {
        return calculateItemPrice(item);
    }
    
    /**
     * Checks if the shop has any items left.
     * 
     * @return true if shop is empty
     */
    public boolean isEmpty() {
        return currentStock.isEmpty();
    }
    
    // ----- DEBUG/TESTING -----
    
    /**
     * Returns the number of items in the master pool.
     * Useful for debugging.
     * 
     * @return total available item types
     */
    public int getMasterPoolSize() {
        return allItems.size();
    }
}