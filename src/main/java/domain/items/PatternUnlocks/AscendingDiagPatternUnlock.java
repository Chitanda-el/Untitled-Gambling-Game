package domain.items.PatternUnlocks;

import domain.SlotMachine;
import domain.items.PatternUnlock;

/**
 * If this item is in the player's inventory then it allows the ascending
 * diagonal (bottom-left, middle, top-right elements) to be checked for
 * valid patterns (three-of-a-kind).
 */
public class AscendingDiagPatternUnlock extends PatternUnlock {
    
    private static final int ITEM_ID = 4;
    private static final String NAME = "Ladder";
    private static final String DESCRIPTION =
              "This is a ladder. It can be used to climb things. It also "
            + "allows the ascending diagonal to be a winning pattern which, "
            + "in this game, is probably its most useful attribute.";
    private static final int COST = 10;
    
    public AscendingDiagPatternUnlock() {
        final int ROWS[] = {2, 1, 0};
        final int COLUMNS[] = {0, 1, 2};
        
        super(ITEM_ID, NAME, DESCRIPTION, COST, ROWS, COLUMNS);
    }
}