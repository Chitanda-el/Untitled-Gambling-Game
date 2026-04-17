package domain.items;

/**
 * Base class from which all purchasable items inherit.
 * 
 * Each item has:
 * ITEM_ID: A constant integer ID identifying the item.
 * NAME: A string containing the item's "lore" name to display to the player.
 * DESCRIPTION: A string that describes what the item does to the player.
 */
public abstract class Item {
    
    protected final int ITEM_ID;         // int ID identifying the item.
    protected final String NAME;        // Name of the item (display to user).
    protected final String DESCRIPTION; // What the item is (display to user).
	
    public Item(int itemID, String name, String description) {
        this.ITEM_ID = itemID;
        this.NAME = name;
        this.DESCRIPTION = description;
    }
    
    // -- ACCESSORS --
    public int getID()              { return ITEM_ID; }
    public String getName()         { return NAME; }
    public String getDescription()  { return DESCRIPTION; }
}