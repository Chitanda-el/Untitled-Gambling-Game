package game;

import domain.*;
import util.RnadomNumGenerator;

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
    
    private MainWondow mainWindow;
    
    private RandomNumGenerator rng;
    private Player             player;
    private SlotMachine        slotMachine;
    private ItemShop           itemShop;
    private GameEventManager   eventManager;
}
