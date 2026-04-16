package game;

// The 'rng' object created in the GameDirector is to be used everywhere;
// no random number should ever be generated any other way than through 'rng'.
import util.RandomNumGenerator;
import java.util.Date;  // For if the user doesn't provide their own seed.
import java.util.List;
import domain.*;
import java.util.ArrayList;

// GUI Elements and Dependencies
import ui.gui.MainWindow;
import javax.swing.SwingUtilities;

/**
 * Directs game logic for one run in UGG.
 * 
 * Lies between the domain and the UI.
 * 
 * The UI calls director methods in response to player input and the
 * director processes domain logic and pushes the updated state back
 * to the appropriate user interface.
 */
public class GameDirector {
    
    // ----- --------- ----- --------- ----- --------- ----- --------- -----
    // ----- CONSTANTS ----- CONSTANTS ----- CONSTANTS ----- CONSTANTS ----- 
    // ----- --------- ----- --------- ----- --------- ----- --------- -----
    
    private static final int STARTING_MONEY = 0;
    private static final int INITIAL_DEBT = 0; // TO BE DETERMINED
    
    // ----- ------------ ----- ------------ ----- ------------ -----
    // ----- DEPENDENCIES ----- DEPENDENCIES ----- DEPENDENCIES -----
    // ----- ------------ ----- ------------ ----- ------------ -----
    
    // The RandomNumGenerator object is created using the current time as the
    // seed by default. A new one is created that replaces it if the player
    // enters a new seed.
    public RandomNumGenerator rng = new RandomNumGenerator(new Date().getTime());
    
    private SlotMachine slotMachine; // Reference to the SlotMachine object.
    private MainWindow  window;      // Reference to the MainWindow object.
    //private ItemShop           itemShop;
    //private GameEventManager   eventManager;
    
    // grid contains the symbols to display on the slot machine in the GUI.
    SlotMachine.Symbols[][] grid;
    
    // ----- ------------ ----- ------------ ----- ------------ -----
    // ----- CONSTRUCTORS ----- CONSTRUCTORS ----- CONSTRUCTORS -----
    // ----- ------------ ----- ------------ ----- ------------ -----
    
    public GameDirector(){
        
        // Create the MainWindow object
        SwingUtilities.invokeLater(() -> {
            this.window = new MainWindow(this); // no-arg constructor, no GameDirector needed
            this.window.setVisible(true);
        });
        
        // Create SlotMachine Object
        slotMachine = new SlotMachine(rng);
    }
    
    // ----- --- ------------ ----- --- ------------ ----- --- ------------ -----
    // ----- GUI INTERACTIONS ----- GUI INTERACTIONS ----- GUI INTERACTIONS -----
    // ----- --- ------------ ----- --- ------------ ----- --- ------------ -----
    
    // ----- GENERAL USER INPUTS -----
    
    /**
     * This function will handle "setting up" the game once the player selects
     * the play button.
     */
    public void onStartGame() {
        onSpin();
    }
    
    /**
     * This function will handle what occurs when the user abandons (deletes)
     * their save.
     */
    public void onAbandonSave() {
        // saveManager.deleteSave();
    }
    
    // ----- RANDOM NUMBER GENERATOR CLASS INTERACTIONS -----
    
    /**
     * GETTER FUNCTION
     *
     * @return the seed used by the random number generator.
     */
    public long getCurrentSeed() {
        return rng.getSeed();
    }
    
    /**
     * If the user enters a new seed convert it to a long and generate a new
     * RandomNumGenerator object in place of the old one.
     * 
     * @param newSeed string or number that is converted to a long for seeding.
     */
    public void setCustomSeed(String newSeed) {
        rng = new RandomNumGenerator(Long.parseLong(newSeed));
    }
    
    // ----- SLOT MACHINE CLASS INTERACTIONS -----
    
    /**
     * What happens when the spin button is pressed in the GUI?
     * 
     * 1. Spins the slot machine
     * 2. Updates the grid displayed.
     */
    public void onSpin() {
        grid = slotMachine.spin();
        window.getSlotMachineGUI().updateSlotGrid(grid);
    }
    
    // ----- PLAYER CLASS INTERACTIONS -----
    
    public int getPlayerMoney() {
        return 0;
    }
    
    public int getPlayerDebt() {
        return 0;
    }
    
    // ----- ITEM SHOP CLASS INTERACTIONS -----
    
    public void onRerollShop() {
        
    }
    
    
    /**
     * 
     * @param itemIndex
     * @return 
     */
    public boolean onPurchaseItem(int itemIndex) {
        return false;
    }
}
