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
public class Item {
    
    // ----- ---- --- ----- ---- --- ----- ---- --- ----- ---- --- -----
    // ----- ITEM IDS ----- ITEM IDS ----- ITEM IDS ----- ITEM IDS -----
    // ----- ---- --- ----- ---- --- ----- ---- --- ----- ---- --- -----
    
    public static final int LEFT_COL_PATT_ID = 1;
    public static final int MID_COL_PATT_ID = 2;
    public static final int RIGHT_COL_PATT_ID = 3;
    public static final int TOP_ROW_PATT_ID = 4;
    public static final int BOT_ROW_PATT_ID = 5;
    public static final int DSCDNG_DIAG_PATT_ID = 6;
    public static final int ASCNDG_DIAG_PATT_ID = 7;
    public static final int COOL_DUCK_ID = 8;
    
    // ----- ------ ----- ----- ------ ----- ----- ------ ----- ----- 
    // ----- EFFECT TYPES ----- EFFECT TYPES ----- EFFECT TYPES ----- 
    // ----- ------ ----- ----- ------ ----- ----- ------ ----- ----- 
   
    public enum EffectType {
        // Changes the value of certain symbols
        SYMBOL_WEIGHT,
        // Unlocks additional winning patterns or modifies their value
        PATTERN_MODIFIER,
        // Miscellaneous/Special Effects
        SPECIAL
    }
    
    private final int        itemID;      // int ID identifying the item.
    private final String     name;        // Name of the item (display to user).
    private final String     description; // What the item is (display to user).
    private final int        cost;        // How much the item costs in the shop.
    private final EffectType effectType;  // What the item does.
    
    private final double effectValue;
    
    public Item(int itemID, String name, String description, int cost,
                EffectType effectType, double effectValue) {
        this.itemID = itemID;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.effectType = effectType;
        this.effectValue = effectValue;
    }
    
    
    
    // ----- --------- ----- --------- ----- ----- --------- ----- ---------
    // ----- ACCESSORS ----- ACCESSORS ----- ACCESSORS ----- ACCESSORS ----- 
    // ----- --------- ----- --------- ----- ----- --------- ----- ---------
    
    public int getID()                 { return itemID;      }
    public String getName()            { return name;        }
    public String getDescription()     { return description; }
    public double getCost()            { return cost;        }
    public EffectType getEffectType()  { return effectType;  }
    public double getEffectValue()     { return effectValue; }
    
    
}
