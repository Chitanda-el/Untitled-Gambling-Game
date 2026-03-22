package domain;

import util.RandomNumGenerator;
import domain.Player;

/**
 * Represents the 3x3 slot machine which is the core of the game.
 * 
 * SlotMachine Responsibilities:
 *  1. Spin Generation     (randomize symbols on the grid)
 *  2. Pattern Recognition (tally up winning patterns)
 *  3. Payout Calculation  (how much did the player win?)
 *  4. Reference the player inventory to modify the above as necessary
 * 
 * 
 * SlotMachine Collaborators:
 *  1. Player: SlotMachine doesn't hold a direct reference to Player (that will
 *     be managed by the GameController) but it does utilize the information
 *     in Player.inventory to determine which item's are currently active.
 *  2. Item: reads relevant item definitions from the Item class.
 *  3. RandomNumGenerator: utilizes to randomize the spin generation.
 */
public class SlotMachine {
    public static final int GRID_ROWS = 3;
    public static final int GRID_COLUMNS = 3;
    
    // SUBJECT TO CHANGE. SUBJECT TO CHANGE.
    // Possible symbols on the reel.
    public enum Symbols {
        DUCK, SKULL, CHERRY, SEVEN, CLOVER
    }
    
    /**
     * Evaluates the grid for winning patterns.
     * 
     * Checks for three-of-a-kind. it returns 1 if no winning pattern is
     * detected and increments for each winning pattern detected. If an item
     * modifies the pattern weight it can increment by a double for that pattern
     * and likewise for symbol weight.
     * 
     * @param grid to be evaluated for patterns.
     * @param player reference the player inventory for additional patterns,
     *               pattern weights, and symbol weights.
     * @return returns 1 if no winning pattern is detected and increments by
     *         1 for each winning pattern. 
     */
    public double evaluatePatterns(Symbol[][] grid,
                                   Player player) {
        // DEFINE
    }
}
