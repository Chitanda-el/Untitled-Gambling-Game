package util;
import domain.items.*;
import java.io.Serializable;
import java.util.List;

/**
 * Contains the actual data associated with a save.
 */
public class SaveData implements Serializable {

    // ----- PLAYER STATE -----
    public int money;
    public int debt;
    public List<Item> inv;

    // Probably a good idea to put a round counter in somewhere.
    // public int currentRound;

    // The current stock of the shop.
    public List<Integer> shopItemIDs;

    // The seed associated with the RNG.
    public long rngSeed;

    // TODO: Implement number of rerolls/refreshes and cost of reroll.
}