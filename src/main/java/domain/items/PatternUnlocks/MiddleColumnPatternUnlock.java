package domain.items.PatternUnlocks;

import domain.SlotMachine;
import domain.items.PatternUnlock;

/**
 * If this item is in the player's inventory then it allows the middle
 * column to be checked for valid patterns (three-of-a-kind).
 */
public class MiddleColumnPatternUnlock extends PatternUnlock {
    
    private static final int ITEM_ID = 4;
    private static final String NAME = "Middle Column";
    private static final String DESCRIPTION =
              "The middle column can now contain a winning pattern.";
    private static final int COST = 10;
    
    public MiddleColumnPatternUnlock() {
        final int ROWS[] = {0, 1, 2};
        final int COLUMNS[] = {1, 1, 1};
        
        super(ITEM_ID, NAME, DESCRIPTION, COST, ROWS, COLUMNS);
    }
}