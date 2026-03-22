package domain;

import util.RandomNumGenerator;
import java.util.List;

/**
 * Manages the items available for purchase and processes transactions.
 * 
 * The shop is refreshed each time the debt requirement is satisfied.
 * 
 * ItemShop Responsibilities:
 *  1. Pass items currently for sale to the GUI
 *  2. Process transactions with the player
 *  3. Refresh the items in the shop each time the debt requirement is met
 * 
 * ItemShop Collaborators:
 *  1. RandomNumGenerator: to randomize which items are available in the shop.
 *  2. Player
 */
public class ItemShop {
    
    // Number of items for sale every inventory refresh.
    private static final int INVENTORY_CAP = 3;
    
    private final RandomNumGenerator rng;
    private final List<Item> allItems;  // Reference for refreshing the shop
    private List<Item> currentStock;    // Items for sale
    
    public ItemShop(RandomNumGenerator rng) {
        
    }
    
    
    
    /**
     * Refreshes the shop's inventory.
     * 
     * Call every time the debt requirement is met.
     */
    public void refreshShop() {
        // DEFINE
    }
    
    /**
     * Checks whether 
     */
    public void purchaseItem() {
        // DEFINE
    }
}
