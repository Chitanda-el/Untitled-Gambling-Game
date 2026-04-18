package game;

// The 'rng' object created in the GameDirector is to be used everywhere;
// no random number should ever be generated any other way than through 'rng'.
import util.*;
import java.util.Date;  // For if the user doesn't provide their own seed.
import java.util.ArrayList;
import java.util.List;

// Domain Depndencies
import domain.*;
import domain.items.*;
import domain.items.PatternUnlocks.*;

// GUI Dependencies
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
    
    // Starting shop money
    private static final int STARTING_MONEY = 50;
    
    // Starting debt amount
    private static final int INITIAL_DEBT = 100;
    
    // Standard amount by which to multiply debt each time the requirement is met.
    private static final int STANDARD_DEBT_SCALE = 10;
    
    // ----- ------------ ----- ------------ ----- ------------ -----
    // ----- DEPENDENCIES ----- DEPENDENCIES ----- DEPENDENCIES -----
    // ----- ------------ ----- ------------ ----- ------------ -----
    
    // The RandomNumGenerator object is created using the current time as the
    // seed by default. A new one is created that replaces it if the player
    // enters a new seed.
    public RandomNumGenerator rng = new RandomNumGenerator(new Date().getTime());
    
    private SlotMachine slotMachine; // Reference to the SlotMachine object.
    private MainWindow  window;      // Reference to the MainWindow object.
    private ItemShop itemShop;
    private SaveManager save;
    
    // Reference to the Player object.
    private Player player;
    
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
        
        // Create Player object
        player = new Player(STARTING_MONEY, INITIAL_DEBT);
        
        List<Item> allItems = createAllItemsList();
        itemShop = new ItemShop(rng, allItems);
    }
    
    // ----- --- ------------ ----- --- ------------ ----- --- ------------ -----
    // ----- GUI INTERACTIONS ----- GUI INTERACTIONS ----- GUI INTERACTIONS -----
    // ----- --- ------------ ----- --- ------------ ----- --- ------------ -----
    
    // ----- GENERAL USER INPUTS -----
    
    /**
     * This function handles setting up SlotMachine and Player objects once the
     * once the player selects the play button.
     */
    public void onStartGame() {
        // Initialize game state and display
        window.getSlotMachineGUI().refreshDisplay();
        window.getItemShopGUI().updateShopStock(itemShop.getCurrentStock());
    }
    
    /**
     * This function will handle what occurs when the user abandons (deletes)
     * their save.
     */
    public void onAbandonSave() {
        save.deleteSave();
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
    
    // ----- ---- ------- ----- ------------ -----
    // ----- SLOT MACHINE CLASS INTERACTIONS -----
    // ----- ---- ------- ----- ------------ -----
    
    /**
     * What happens when the spin button is pressed in the GUI?
     * 
     * 1. Subtracts the bet from the player's money.
     * 2. Spins the slot machine.
     * 3. Updates the grid displayed.
     * 4. Updates the players money and Debt if they won.
     */
    public void onSpin(int bet) {
        if (bet <= player.getMoney() && bet >= 0) {
            
            // Subract bet amount from player's money.
            //player.addMoney(bet * -1);
            
            // Generate grid
            grid = slotMachine.spin();
            
            // Display grid
            window.getSlotMachineGUI().updateSlotGrid(grid);
            
            // If the player won, give them their winnings
            int payout = slotMachine.calculatePayout(grid, bet, player);
            player.addMoney(payout);
        }
        
        // If the amount of money the player has exceeds the amount of debt
        // they have, then subtract the current debt from their current money
        // and multiply the amount of debt owed by STANDARD_DEBT_SCALE.
        if (player.getMoney() >= player.getDebt()) {
            player.addMoney((player.getDebt())*(-1));
            int newDebt = player.getDebt()*STANDARD_DEBT_SCALE;
            player.setDebt(newDebt);
        }
        
        window.getSlotMachineGUI().refreshDisplay();
    }
    
    public SlotMachine getSlotMachine() {
        return slotMachine;
    }
    
        
    // ----- ------ ----- ------------ -----
    // ----- PLAYER CLASS INTERACTIONS -----
    // ----- ------ ----- ------------ -----
    
    
    public int getPlayerMoney() {
        return player.getMoney();
    }
    

    public int getPlayerDebt() {
        return player.getDebt();
    }
    
    public Player getPlayer() {
    return player;
    }
    
    // ----- ---- ---- ----- ------------ -----
    // ----- ITEM SHOP CLASS INTERACTIONS -----
    // ----- ---- ---- ----- ------------ -----
    
    public void onRerollShop() {
        if (itemShop.rerollShop(player)) {
            window.getItemShopGUI().updateShopStock(itemShop.getCurrentStock());
            window.getMainMenuGUI().refreshDisplay();
        }
    }
    
    public ItemShop getItemShop() {
        return itemShop;
    }
    
    /**
     * 
     * @param itemIndex
     * @return 
     */
    public boolean onPurchaseItem(int itemIndex) {
        if (itemShop.purchaseItem(player, itemIndex)) {
            window.getItemShopGUI().updateShopStock(itemShop.getCurrentStock());
            window.getMainMenuGUI().refreshDisplay();
            return true;
        }
        return false;
    }
    
    private List<Item> createAllItemsList() {
        List<Item> items = new ArrayList<>();

        items.add(new BottomRowPatternUnlock());
        items.add(new TopRowPatternUnlock());
        items.add(new LeftColumnPatternUnlock());
        items.add(new RightColumnPatternUnlock());
        items.add(new MiddleColumnPatternUnlock());
        items.add(new AscendingDiagPatternUnlock());
        items.add(new DescendingDiagPatternUnlock());

        return items;
    }
    
    // ----- --------- ----- --------- -----
    // ----- SAVE/LOAD ----- SAVE/LOAD ----- 
    // ----- --------- ----- --------- -----
    
    public SaveData createSaveData() {
        SaveData data = new SaveData();

        // Player
        data.money = player.getMoney();
        data.debt = player.getDebt();

        data.inventoryItemIDs = new ArrayList<>();
        
        // Shop
        data.shopItemIDs = new ArrayList<>();
        for (Item item : itemShop.getCurrentStock()) {
            data.shopItemIDs.add(item.getID());
        }

        // RNG
        data.rngSeed = getCurrentSeed();

        return data;
    }
    
    public void restoreFromSave(SaveData data) {
        // Player
        player.setMoney(data.money);
        player.setDebt(data.debt);
        for (int id : data.inventoryItemIDs) {
        //    player.addItem(itemShop.createItem(id));
        }
        // RNG
        this.rng = new RandomNumGenerator(data.rngSeed);
        // Shop
        for (int id : data.shopItemIDs) {
        //    itemShop.addItem(itemShop.createItem(id));
        }
    }
}

