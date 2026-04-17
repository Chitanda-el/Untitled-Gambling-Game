package domain.items;

import domain.Item;
import domain.SlotMachine;

/**
 * If this item is in the player's inventory then it allows the bottom
 * row to be checked for valid patterns (three-of-a-kind).
 */
public class BottomRowPatternUnlock extends Item {
    
    private static final int ITEM_ID = 0;
    private static final String NAME = "Bottom Bell";
    private static final String DESCRIPTION =
              "This bottom bell's mysterious chime sparks an epiphany in the "
            + "slot machine, allowing it to verify whether a winning pattern "
            + " has appeared in the bottom row.";
    private static final int COST = 10;
    
    public BottomRowPatternUnlock() {
        super(ITEM_ID, NAME, DESCRIPTION, COST);
    }
    
    /**
     * Evaluate the bottom row for matching patterns.
     * 
     * @param grid of symbols to evaluate.
     * @return 0 if no matching patterns were found, 1 if they were.
     */
    public int evaluateBottomRow(SlotMachine.Symbols[][] grid) {
        int localMultiplier = 0;
        if (grid[2][0] == grid[2][1] && grid[2][1] == grid[2][2]) {
            localMultiplier++;
        }
        
        return localMultiplier;
    }
}