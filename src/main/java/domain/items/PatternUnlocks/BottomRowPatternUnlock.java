package domain.items.PatternUnlocks;

import domain.SlotMachine;
import domain.items.PatternUnlock;

/**
 * If this item is in the player's inventory then it allows the bottom
 * row to be checked for valid patterns (three-of-a-kind).
 */
public class BottomRowPatternUnlock extends PatternUnlock {
    
    private static final int ITEM_ID = 0;
    private static final String NAME = "Bottom Bell";
    private static final String DESCRIPTION =
              "This bottom bell's mysterious chime sparks an epiphany in the "
            + "slot machine, allowing it to verify whether a winning pattern "
            + " has appeared in the bottom row.";
    private static final int COST = 10;
    
    public BottomRowPatternUnlock() {
        final int ROWS[] = {2, 2, 2};
        final int COLUMNS[] = {0, 1, 2};
        
        super(ITEM_ID, NAME, DESCRIPTION, COST, ROWS, COLUMNS);
    }
}