// SlotMachine.java - Add these methods to the existing class
package domain;

import util.RandomNumGenerator;

/**
 * Represents the 3x3 slot machine which is the core of the game.
 */
public class SlotMachine {
    
    private final RandomNumGenerator rng;
    
    public static final int GRID_ROWS = 3;
    public static final int GRID_COLUMNS = 3;
    
    private Symbols[][] grid = new Symbols[GRID_ROWS][GRID_COLUMNS];
    
    public enum Symbols {
        DUCK, HEART, CHERRY, SEVEN, CLOVER
    }
    
    // Bet tracking
    private int currentBet;
    private static final int DEFAULT_BET = 10;
    private static final int MIN_BET = 1;
    
    public SlotMachine(RandomNumGenerator rng) {
        this.rng = rng;
        this.currentBet = DEFAULT_BET;
    }
    
    // ----- BET MANAGEMENT -----
    
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
    
    // ----- SPIN LOGIC (existing methods below) -----
    
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
    
    private int evaluatePatterns(Symbols[][] grid) {
        int multiplier = 1;
        multiplier += standardPattern(grid);
        
        if (multiplier == 1) {
            return -1;
        } else {
            return multiplier;
        }
    }
    
    private int standardPattern(Symbols[][] grid) {
        int localMultiplier = 0;
        if (grid[1][0] == grid[1][1] && grid[1][1] == grid[1][2]) {
            localMultiplier++;
        }
        return localMultiplier;
    }
    
    private int topRowPattern(Symbols[][] grid) {
        int localMultiplier = 0;
        if (grid[0][0] == grid[0][1] && grid[0][1] == grid[0][2]) {
            localMultiplier++;
        }
        return localMultiplier;
    }
    
    private int bottomRowPattern(Symbols[][] grid) {
        int localMultiplier = 0;
        if (grid[2][0] == grid[2][1] && grid[2][1] == grid[2][2]) {
            localMultiplier++;
        }
        return localMultiplier;
    }
    
    public int calculatePayout(Symbols[][] grid, int bet) {
        int multiplier = evaluatePatterns(grid);
        return bet * multiplier;
    }
}