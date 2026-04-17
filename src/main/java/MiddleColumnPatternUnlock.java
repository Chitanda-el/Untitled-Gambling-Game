package domain.items;

import domain.Item;
import domain.SlotMachine;

/**
 * If this item is in the player's inventory then it allows the middle
 * column to be checked for valid patterns (three-of-a-kind).
 */
public class MiddleColumnPatternUnlock extends Item {
    
    private static final int ITEM_ID = 0;
    private static final String NAME = "Middle Column";
    private static final String DESCRIPTION =
              "The middle column can now contain a winning pattern.";
    private static final int COST = 10;
     
    public MiddleColumnPatternUnlock() {
        super(ITEM_ID, NAME, DESCRIPTION, COST);
    }
    
    /**
     * Evaluate the middle column for matching patterns.
     * 
     * @param grid of symbols to evaluate.
     * @return 0 if no matching patterns were found, 1 if they were.
     */
    public int evaluateMiddleColumn(SlotMachine.Symbols[][] grid) {
        int localMultiplier = 0;
        if (grid[0][1] == grid[1][1] && grid[1][1] == grid[2][1]) {
            localMultiplier++;
        }
        
        return localMultiplier;
    }
}