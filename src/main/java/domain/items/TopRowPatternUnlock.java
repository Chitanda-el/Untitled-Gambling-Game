package domain.items;

import domain.Item;
import domain.SlotMachine;

/**
 * If this item is in the player's inventory then it allows the top row to be
 * checked for valid patterns (three-of-a-kind).
 */
public class TopRowPatternUnlock extends Item {
    
    private static final int ITEM_ID = 3;
    private static final String NAME = "Top Analyzer";
    private static final String DESCRIPTION =
              "This top row analyzer updates the slot machine's computer "
            + "allowing it to scan the top row for winning patterns.";
     private static final int COST = 10;
    
    public TopRowPatternUnlock() {
        super(ITEM_ID, NAME, DESCRIPTION, COST);
    }
    
    /**
     * Evaluate the top row for matching patterns.
     * 
     * @param grid of symbols to evaluate.
     * @return 0 if no matching patterns were found, 1 if they were.
     */
    public int evaluateTopRow(SlotMachine.Symbols[][] grid) {
        int localMultiplier = 0;
        if (grid[0][0] == grid[0][1] && grid[0][1] == grid[0][2]) {
            localMultiplier++;
        }
        
        return localMultiplier;
    }
}