package ui.gui;

import javax.swing.*;

/**
 * UGG's single top-level window.
 * 
 * All three GUIs (Main Menu, Slot Machine, Item Shop) must live inside
 * this one and are swapped in/out. Do not open additional windows or dialogs
 * for navigation.
 * 
 * TODO:
 *  1. Construct each panel,
 *  2. Set a fixed window size that matches the hand-drawn art
 *     dimensions once those are finished.
 * 
 */
public class MainWindow extends JFrame {
    
    /** Named constants for each screen; used as {@code CardLayout} keys. */
    public enum Screen {
        MAIN_MENU,
        SLOT_MACHINE,
        ITEM_SHOP
    }
}
