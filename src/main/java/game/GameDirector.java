package game;

// The 'rng' object created in the GameDirector is to be used everywhere;
// no random number should ever be generated any other way than through 'rng'.
import util.RandomNumGenerator;
import java.util.Date;  // For if the user doesn't provide their own seed.

import domain.Item;
import java.util.List;

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
    
    public RandomNumGenerator rng = new RandomNumGenerator(new Date().getTime());
    //private Player             player;
    //private SlotMachine        slotMachine;
    //private ItemShop           itemShop;
    //private GameEventManager   eventManager;
    
    // ----- ------------ ----- ------------ ----- ------------ -----
    // ----- CONSTRUCTORS ----- CONSTRUCTORS ----- CONSTRUCTORS -----
    // ----- ------------ ----- ------------ ----- ------------ -----
    
    public GameDirector(){
        createUI(this);
    }
    
    private static void createUI(GameDirector director) {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow(director); // no-arg constructor, no GameDirector needed
            window.setVisible(true);
        });
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
        
    }
    
    /**
     * This function will handle what occurs when the user abandons (deletes)
     * their save.
     */
    public void onAbandonSave() {
        
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
    
    public void onSpin() {
        
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
    public List<Item> getShopItems() {
        return List<0>;
    }
    */
    
    /**
     * 
     * @param itemIndex
     * @return 
     */
    public boolean onPurchaseItem(int itemIndex) {
        return false;
    }
}
