package domain;

/**
 * Base class from which all purchasable items inherit.
 * 
 * Each item has:
 * ITEM_ID: A constant integer ID identifying the item.
 * NAME: A string containing the item's "lore" name to display to the player.
 * DESCRIPTION: A string that describes what the item does to the player.
 * COST: A base cost the player will pay for the item.
 */
public abstract class Item {
    
    protected final int ITEM_ID;         // int ID identifying the item.
    protected final String NAME;        // Name of the item (display to user).
    protected final String DESCRIPTION; // What the item is (display to user).
    protected final int COST;        // The base cost of the item.
	
    public Item(int itemID, String name, String description, int cost) {
        this.ITEM_ID = itemID;
        this.NAME = name;
        this.DESCRIPTION = description;
        this.COST = cost;
    }
    
    // -- ACCESSORS --
    public int getID()              { return ITEM_ID; }
    public String getName()         { return NAME; }
    public String getDescription()  { return DESCRIPTION; }
    public int getCost()         { return COST; }
}