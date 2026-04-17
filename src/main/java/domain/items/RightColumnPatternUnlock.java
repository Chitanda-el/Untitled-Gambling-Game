package domain.items;

import domain.Item;
import domain.SlotMachine;

/**
 * If this item is in the player's inventory then it allows the right
 * column to be checked for valid patterns (three-of-a-kind).
 */
public class RightColumnPatternUnlock extends Item {
    
    private static final int ITEM_ID = 2;
    private static final String NAME = "Righty Tighty";
    private static final String DESCRIPTION =
              "A screw allowing the right column to contain a winning pattern "
            + "came loose and got lost on the machine due to the spinning of "
            + "the reels. Fortunately the hardware store has more, "
            + "albeit at a steep price.";
    private static final int COST = 10;
    
    public RightColumnPatternUnlock() {
        super(ITEM_ID, NAME, DESCRIPTION, COST);
    }
    
    /**
     * Evaluate the right column for matching patterns.
     * 
     * @param grid of symbols to evaluate.
     * @return 0 if no matching patterns were found, 1 if they were.
     */
    public int evaluateRightColumn(SlotMachine.Symbols[][] grid) {
        int localMultiplier = 0;
        if (grid[0][2] == grid[1][2] && grid[1][2] == grid[2][2]) {
            localMultiplier++;
        }
        
        return localMultiplier;
    }
}