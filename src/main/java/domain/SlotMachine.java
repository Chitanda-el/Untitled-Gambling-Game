package domain;

import util.RandomNumGenerator;

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
    
    private final RandomNumGenerator rng;
    
    // SLOT MACHINE DISPLAY CHARACTERISTICS
    public static final int GRID_ROWS = 3;
    public static final int GRID_COLUMNS = 3;
    
    // SLOT MACHINE GRID
    private Symbols[][] grid = new Symbols[GRID_ROWS][GRID_COLUMNS];
    
    // Possible symbols on the slot machine's grid.
    public enum Symbols {
        DUCK, HEART, CHERRY, SEVEN, CLOVER
    }
    
    /**
     * SlotMachine constructor. Creates the slot machine that we'll be playing
     * with.
     * 
     * @param rng takes the RandomNumGenerator object created in GameDirector
     */
    public SlotMachine(RandomNumGenerator rng) {
        this.rng = rng;
    }
    
    /**
     * Randomizes 3x3 grid of elements from Symbols using the
     * RandomNumGenerator class.
     * 
     * @return a 3x3 grid of randomized symbols.
     */
    public Symbols[][] spin() {
        Symbols[] symbolOptions = Symbols.values();
        
        for (int row = 0; row < GRID_ROWS; ++row) {
            for (int column = 0; column < GRID_COLUMNS; ++column) {
                int randomInt = rng.nextInt(symbolOptions.length);
                grid[row][column] = symbolOptions[randomInt];
            }
        }
        
        return grid;
    }
    
    /**
     * Evaluates the grid for winning patterns.
     * 
     * Checks for three-of-a-kind in the middle row, and other rows/columns as
     * items permit. It returns 1 if no winning pattern is detected and
     * increments for each winning pattern detected.
     * 
     * @param grid to be evaluated for patterns.
     * @return returns -1 if no winning pattern is detected and increments by
     *         1 for each winning pattern. 
     * 
     * TODO: Check player inventory for unlocked patterns and check those too.
     * Potentially collaborate with the inventory to check for
     * weighted patterns or symbols.
     */
    private int evaluatePatterns(Symbols[][] grid) {
        int multiplier = 1;
        
        multiplier += standardPattern(grid);
        
        if (multiplier == 1) {
            return -1;
        } else {
            return multiplier;
        }
    }
    
    // ----- PATTERNS TO EVALUATE ----- PATTERNS TO EVALUATE ----- 
    
    /**
     * Evaluate the center row for matching patterns.
     * 
     * @param grid of symbols to evaluate.
     * @return 0 if no matching patterns were found, 1 if they were.
     */
    private int standardPattern(Symbols[][] grid) {
        int localMultiplier = 0;
        if (grid[1][0] == grid[1][1] && grid[1][1] == grid[1][2]) {
            localMultiplier++;
        }
        
        return localMultiplier;
    }
    
     /**
     * Evaluate the top row for matching patterns.
     * 
     * @param grid of symbols to evaluate.
     * @return 0 if no matching patterns were found, 1 if they were.
     */
    private int topRowPattern(Symbols[][] grid) {
        int localMultiplier = 0;
        if (grid[0][0] == grid[0][1] && grid[0][1] == grid[0][2]) {
            localMultiplier++;
        }
        
        return localMultiplier;
    }
    
    /**
     * Evaluate the bottom row for matching patterns.
     * 
     * @param grid of symbols to evaluate.
     * @return 0 if no matching patterns were found, 1 if they were.
     */
    private int bottomRowPattern(Symbols[][] grid) {
        int localMultiplier = 0;
        if (grid[2][0] == grid[2][1] && grid[2][1] == grid[2][2]) {
            localMultiplier++;
        }
        
        return localMultiplier;
    }
    
    /**
     * Returns the amount of money the player has won.
     * 
     * 
     * @param bet integer bet placed by the player
     * @return the amount of money the player won
     */
    public int calculatePayout(Symbols[][] grid, int bet) {
        // integer multiplier on original bet calculated by evaluatePatterns()
        int multiplier = evaluatePatterns(grid);
        return bet*multiplier;
    }    
}
