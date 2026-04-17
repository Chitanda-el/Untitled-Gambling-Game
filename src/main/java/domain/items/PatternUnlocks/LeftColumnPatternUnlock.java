package domain.items.PatternUnlocks;

import domain.SlotMachine;
import domain.items.PatternUnlock;

/**
 * If this item is in the player's inventory then it allows the left
 * column to be checked for valid patterns (three-of-a-kind).
 */
public class LeftColumnPatternUnlock extends PatternUnlock {
    
    private static final int ITEM_ID = 1;
    private static final String NAME = "Left-handed Leech";
    private static final String DESCRIPTION =
              "This left-handed leech is surprisingly intelligent, and even "
            + "more surpisingly friendly! It will check whether the reels in "
            + "the left-hand column match and trigger the slot machine to "
            + "payout for that pattern.";
     private static final int COST = 10;
    
    public LeftColumnPatternUnlock() {
        final int ROWS[] = {0, 1, 2};
        final int COLUMNS[] = {0, 0, 0};
        
        super(ITEM_ID, NAME, DESCRIPTION, COST, ROWS, COLUMNS);
    }
}