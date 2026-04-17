package domain.items.PatternUnlocks;

import domain.SlotMachine;
import domain.items.PatternUnlock;

/**
 * If this item is in the player's inventory then it allows the top row to be
 * checked for valid patterns (three-of-a-kind).
 */
public class TopRowPatternUnlock extends PatternUnlock {
    
    private static final int ITEM_ID = 3;
    private static final String NAME = "Top Analyzer";
    private static final String DESCRIPTION =
              "This top row analyzer updates the slot machine's computer "
            + "allowing it to scan the top row for winning patterns.";
     private static final int COST = 10;
    
    public TopRowPatternUnlock() {
        final int ROWS[] = {0, 0, 0};
        final int COLUMNS[] = {0, 1, 2};
        
        super(ITEM_ID, NAME, DESCRIPTION, COST, ROWS, COLUMNS);
    }
}