package domain.items;

import domain.Item;
import domain.SlotMachine;

/**
 * If this item is in the player's inventory then it allows the left
 * column to be checked for valid patterns (three-of-a-kind).
 */
public class LeftColumnPatternUnlock extends Item {
    
    private static final int ITEM_ID = 0;
    private static final String NAME = "Left-handed Leech";
    private static final String DESCRIPTION =
              "This left-handed leech is surprisingly intelligent, and even "
            + "more surpisingly friendly! It will check whether the reels in "
            + "the left-hand column match and trigger the slot machine to "
            + "payout for that pattern.";
     private static final int COST = 10;
    
    public LeftColumnPatternUnlock() {
        super(ITEM_ID, NAME, DESCRIPTION, COST);
    }
    
    /**
     * Evaluate the left column for matching patterns.
     * 
     * @param grid of symbols to evaluate.
     * @return 0 if no matching patterns were found, 1 if they were.
     */
    public int evaluateLeftColumn(SlotMachine.Symbols[][] grid) {
        int localMultiplier = 0;
        if (grid[0][0] == grid[1][0] && grid[1][0] == grid[2][0]) {
            localMultiplier++;
        }
        
        return localMultiplier;
    }
}