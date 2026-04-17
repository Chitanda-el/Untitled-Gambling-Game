package domain.items;

import domain.SlotMachine;

/**
 * Parent class of all pattern unlocks.
 * 
 * This class inherits from the Item class but checks the new ROWS and COLUMNS
 * attributes in the new evaluatePattern method too see if there if there is a
 * three-of-a-kind in the specified positions on the grid.
 */
public class PatternUnlock extends Item {
    
    private final int ROWS[] = new int[3];
    private final int COLUMNS[] = new int[3];
    
    protected PatternUnlock(int ITEM_ID, String ITEM_NAME, String ITEM_DESCRIPTION, int ITEM_COST, int[] rowsToCheck, int[] columnsToCheck) {
        super(ITEM_ID,
              ITEM_NAME,
              ITEM_DESCRIPTION,
              ITEM_COST);
       
        this.ROWS[0] = rowsToCheck[0];
        this.ROWS[1] = rowsToCheck[1];
        this.ROWS[2] = rowsToCheck[2];
        this.COLUMNS[0] = columnsToCheck[0];
        this.COLUMNS[1] = columnsToCheck[1];
        this.COLUMNS[2] = columnsToCheck[2];
    }
    
    /**
     * Evaluate the specified rows and columns for a matching three-of-a-kind
     * pattern.
     * 
     * @param grid of symbols to evaluate.
     * @return 0 if no matching patterns were found, 1 if they were.
     */
    public int evaluatePattern(SlotMachine.Symbols[][] grid) {
        int localMultiplier = 0;
        if (grid[ROWS[0]][COLUMNS[0]] == grid[ROWS[1]][COLUMNS[1]] &&
            grid[ROWS[1]][COLUMNS[1]] == grid[ROWS[2]][COLUMNS[2]]) {
            localMultiplier++;
            return localMultiplier;
        }
        
        return localMultiplier;
    }
}