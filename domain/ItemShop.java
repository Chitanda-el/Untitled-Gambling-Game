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
    
    public ItemShop(RandomNumGenerator rng, List<Item> allItems) {
        this.rng = rng;
		this.allItems = allItems;
		refreshShop(); // initial roll
    }
    
    private Item getWeightedRandomItem(List<Item> pool) {
		int totalWeight = 0;

		for (Item item : pool) {
			totalWeight += item.getWeight();
		}

		int roll = rng.nextInt(totalWeight);

		int cumulative = 0;
		for (Item item : pool) {
			cumulative += item.getWeight();
			if (roll < cumulative) {
				return item;
			}
		}

		throw new IllegalStateException("Should never reach here");
	}
    
    /**
     * Refreshes the shop's inventory.
     * 
     * Call every time the debt requirement is met.
     */
    public void refreshShop() {
        currentStock = new ArrayList<>();
		List<Item> pool = new ArrayList<>(allItems);
		
		for (int i = 0; i < INVENTORY_CAP && !pool.isEmpty(); i++) {
			Item selected = getWeightedRandomItem(pool);
			currentStock.add(selected);
			pool.remove(selected);
		}
    }
    
    /**
     * Checks whether item exists in the shop.
	 * Then checks if the player can afford the item.
	 * Deducts the required amount of money.
	 * Adds the item to the player's inventory.
	 * Removes the item from the shop
     */
    public boolean purchaseItem(Player player, int itemIndex) {
		if (itemIndex < 0 || itemIndex >= currentStock.size()) {
			return false;
		}

		Item item = currentStock.get(itemIndex);

		if (player.getMoney() < item.getCost()) {
			return false;
		}

		player.spendMoney(item.getCost());
		player.addItem(item);

		currentStock.remove(itemIndex);

		return true;
	}
	
	public List<Item> getCurrentStock() {
		return currentStock;
	}
	
	public boolean reroll(Player player, int cost) {
		if (player.getMoney() < cost) return false;

		player.spendMoney(cost);
		refreshShop();
		return true;
	}
	
}
