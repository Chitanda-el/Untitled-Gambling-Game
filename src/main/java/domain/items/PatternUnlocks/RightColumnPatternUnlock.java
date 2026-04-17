package domain.items.PatternUnlocks;

import domain.SlotMachine;
import domain.items.PatternUnlock;

/**
 * If this item is in the player's inventory then it allows the right
 * column to be checked for valid patterns (three-of-a-kind).
 */
public class RightColumnPatternUnlock extends PatternUnlock {
    
    private static final int ITEM_ID = 2;
    private static final String NAME = "Righty Tighty";
    private static final String DESCRIPTION =
              "A screw allowing the right column to contain a winning pattern "
            + "came loose and got lost on the machine due to the spinning of "
            + "the reels. Fortunately the hardware store has more, "
            + "albeit at a steep price.";
    private static final int COST = 10;
    
    public RightColumnPatternUnlock() {
        final int ROWS[] = {0, 1, 2};
        final int COLUMNS[] = {2, 2, 2};
        
        super(ITEM_ID, NAME, DESCRIPTION, COST, ROWS, COLUMNS);
    }
}