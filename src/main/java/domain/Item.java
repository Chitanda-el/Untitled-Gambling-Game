package domain;

/**
 * Definitions of purchasable items.
 * 
 * Each item is identified by a constant itemID. The player's
 * inventory only stores the IDs. Classes that collaborate with
 * the player's items check for a relevant ID in the player's
 * inventory, then reference here for the exact definition.
 * 
 */
public abstract class Item {
    
    protected final int itemID; // int ID identifying the item.
    protected final String name; // Name of the item (display to user).
    protected final String description; // What the item is (display to user).
    protected final int cost; // How much the item costs in the shop.
	protected final int weight; // How rare the item is
	
    public Item(int itemID, String name, String description, int cost, int weight) {
        this.itemID = itemID;
        this.name = name;
        this.description = description;
        this.cost = cost;
		this.weight = weight;
    }
    
    // -- ACCESSORS --
    public int getID() { return itemID; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getCost() { return cost; }
	public int getWeight() { return weight; }
    
	// An Item will interact with an event, so can implement these -- EVENT HOOKS --
	public void onSpinStart(GameEventManager events) {}
    public void onSpinEnd(GameEventManager events) {}
    public void onPayoutCalculation(GameEventManager events) {}
    public void onSymbolRolled(GameEventManager events, Symbol symbol) {}
	
}
