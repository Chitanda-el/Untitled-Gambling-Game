package domain.items.PatternUnlocks;

import domain.SlotMachine;
import domain.items.PatternUnlock;

/**
 * If this item is in the player's inventory then it allows the descending
 * diagonal (top-left, middle, bottom-right elements) to be checked for
 * valid patterns (three-of-a-kind).
 */
public class DescendingDiagPatternUnlock extends PatternUnlock {
    
    private static final int ITEM_ID = 4;
    private static final String NAME = "Stairs to Hell";
    private static final String DESCRIPTION =
              "These stairs remind both you and the cool ducks inside of the "
            + "machine of your incredible disregard for wise financial "
            + "decisions, and also that the descending diagonal should totally "
            + "be a winning pattern from here on in.";
    private static final int COST = 10;
    
    public DescendingDiagPatternUnlock() {
        final int ROWS[] = {0, 1, 2};
        final int COLUMNS[] = {0, 1, 2};
        
        super(ITEM_ID, NAME, DESCRIPTION, COST, ROWS, COLUMNS);
    }
}