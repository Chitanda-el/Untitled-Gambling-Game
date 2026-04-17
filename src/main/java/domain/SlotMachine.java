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
 * SlotMachine Collaborators:
 *  1. Player: SlotMachine doesn't hold a direct reference to Player (that will
 *     be managed by the GameController) but it does utilize the information
 *     in Player.inventory to determine which item's are currently active.
 *  2. Item: reads relevant item definitions from the Item class.
 *  3. RandomNumGenerator: utilizes to randomize the spin generation.
 */
public class SlotMachine {
    
    private final RandomNumGenerator rng;
    
    // SLOT MACHINE GRID CHARACTERISTICS
    public static final int GRID_ROWS = 3;
    public static final int GRID_COLUMNS = 3;
    
    // SLOT MACHINE GRID
    private Symbols[][] grid = new Symbols[GRID_ROWS][GRID_COLUMNS];
    
    // Possible symbols on the slot machine's grid.
    public enum Symbols {
        DUCK, HEART, CHERRY, SEVEN, CLOVER
    }
    
    // Bet tracking
    private int currentBet;
    private static final int DEFAULT_BET = 10;
    private static final int MIN_BET = 1;
    
    /**
     * SlotMachine constructor. Creates the slot machine that we'll be playing
     * with.
     * 
     * @param rng takes the RandomNumGenerator object created in GameDirector
     */
    public SlotMachine(RandomNumGenerator rng) {
        this.rng = rng;
        this.currentBet = DEFAULT_BET;
    }
    
    // ----- ---- ----- ----- ---- ----- -----
    // ----- SPIN LOGIC ----- SPIN LOGIC -----
    // ----- ---- ----- ----- ---- ----- -----
    
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
     * Returns the amount of money the player has won.
     * 
     * 
     * @param bet integer bet placed by the player
     * @return the amount of money the player won
     */
    public int calculatePayout(Symbols[][] grid, int bet) {
        int multiplier = evaluatePatterns(grid);
        return bet * multiplier;
    }

    // ----- --- ---------- ----- --- ---------- -----
    // ----- BET MANAGEMENT ----- BET MANAGEMENT -----
    // ----- --- ---------- ----- --- ---------- -----
    
    /**
     * Gets the current bet amount.
     * 
     * @return current bet value
     */
    public int getCurrentBet() {
        return currentBet;
    }
    
    /**
     * Sets the current bet amount.
     * 
     * @param bet the new bet amount (must be at least MIN_BET)
     */
    public void setCurrentBet(int bet) {
        if (bet >= MIN_BET) {
            this.currentBet = bet;
        }
    }
    
    /**
     * Increases bet by 5% of the player's current money.
     * 
     * @param playerMoney the player's current money amount
     * @return the new bet amount
     */
    public int increaseBetByPercent(int playerMoney) {
        int increaseAmount = (int) Math.round(playerMoney * 0.05);
        int newBet = currentBet + increaseAmount;
        
        if (newBet < MIN_BET) {
            newBet = MIN_BET;
        }
        if (newBet > playerMoney && playerMoney > 0) {
            newBet = playerMoney;
        }
        
        this.currentBet = newBet;
        return currentBet;
    }
    
    /**
     * Decreases bet by 5% of the player's current money.
     * 
     * @param playerMoney the player's current money amount
     * @return the new bet amount
     */
    public int decreaseBetByPercent(int playerMoney) {
        int decreaseAmount = (int) Math.round(playerMoney * 0.05);
        int newBet = currentBet - decreaseAmount;
        
        if (newBet < MIN_BET) {
            newBet = MIN_BET;
        }
        
        this.currentBet = newBet;
        return currentBet;
    }
    
    /**
     * Sets bet to all available money (ALL IN).
     * 
     * @param playerMoney the player's current money amount
     * @return the new bet amount
     */
    public int setAllInBet(int playerMoney) {
        this.currentBet = Math.max(playerMoney, MIN_BET);
        return currentBet;
    }
    
    /**
     * Validates and sets a custom bet amount.
     * 
     * @param betAmount the requested bet amount
     * @param playerMoney the player's current money amount
     * @return true if bet was valid and set, false otherwise
     */
    public boolean validateAndSetBet(int betAmount, int playerMoney) {
        if (betAmount < MIN_BET) {
            return false;
        }
        if (betAmount > playerMoney) {
            return false;
        }
        this.currentBet = betAmount;
        return true;
    }
}