package domain.items.PatternUnlocks;

import domain.SlotMachine;
import domain.items.PatternUnlock;

/**
 * If this item is in the player's inventory then it allows the middle
 * column to be checked for valid patterns (three-of-a-kind).
 */
public class MiddleColumnPatternUnlock extends PatternUnlock {
    
    private static final int ITEM_ID = 4;
    private static final String NAME = "collumn Photo";
    private static final String DESCRIPTION =
              "You spot this impressive Roman style column in the distance, "
            + "it inspires you to verify whether the symbols in the center "
            + "column match.";
    private static final int COST = 10;
    
    public MiddleColumnPatternUnlock() {
        final int ROWS[] = {0, 1, 2};
        final int COLUMNS[] = {1, 1, 1};
        
        super(ITEM_ID, NAME, DESCRIPTION, COST, ROWS, COLUMNS);
    }
}