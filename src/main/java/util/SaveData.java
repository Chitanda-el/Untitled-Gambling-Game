package util;
import domain.items.*;
import java.io.Serializable;
import java.util.List;

/**
 * Contains the actual data associated with a save.
 */
public class SaveData implements Serializable {

    // ----- PLAYER STATE -----
    /**
     * The player's current money.
     */
    public int money;
    /**
     * The player's current debt.
     */
    public int debt;
    /**
     * The player's current inventory.
     */
    public List<Item> inv;

    /**
     * The current stock of the shop.
     */
    public List<Integer> shopItemIDs;

    /**
     * The seed associated with the RNG.
     */
    public long rngSeed;

}