// MainWindow.java
package ui.gui;

import game.GameDirector;
import javax.swing.*;
import java.awt.*;

/**
 * UGG's single top-level window.
 * 
 * All three GUIs (Main Menu, Slot Machine, Item Shop) must live inside
 * this one and are swapped in/out. Do not open additional windows or dialogues
 * for navigation.
 */
public class MainWindow extends JFrame {
    
    /** Named constants for each screen; used as {@code CardLayout} keys. */
    public enum Screen {
        MAIN_MENU,
        SLOT_MACHINE,
        ITEM_SHOP
    }
    
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    private MainMenuGUI mainMenuGUI;
    private SlotMachineGUI slotMachineGUI;
    private ItemShopGUI itemShopGUI;
    
    private GameDirector gameDirector;
    
    public MainWindow() {
        initComponents();
        setupWindow();
    }
    
    public MainWindow(GameDirector gameDirector) {
        this.gameDirector = gameDirector;
        initComponents();
        setupWindow();
    }
    
    private void initComponents() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        mainMenuGUI = new MainMenuGUI(this);
        slotMachineGUI = new SlotMachineGUI(this);
        itemShopGUI = new ItemShopGUI(this);
        
        mainPanel.add(mainMenuGUI, Screen.MAIN_MENU.name());
        mainPanel.add(slotMachineGUI, Screen.SLOT_MACHINE.name());
        mainPanel.add(itemShopGUI, Screen.ITEM_SHOP.name());
        
        add(mainPanel);
    }
    
    private void setupWindow() {
        setTitle("Untitled Gambling Game");
        setSize(1280, 720);
        setMinimumSize(new Dimension(1280, 720));
        setMaximumSize(new Dimension(1280, 720));
        setPreferredSize(new Dimension(1280, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    public void switchTo(Screen screen) {
        cardLayout.show(mainPanel, screen.name());
        
        switch (screen) {
            case SLOT_MACHINE:
                slotMachineGUI.refreshDisplay();
                break;
            case ITEM_SHOP:
                itemShopGUI.refreshDisplay();
                break;
        }
    }
    
    public void setGameDirector(GameDirector gameDirector) {
        this.gameDirector = gameDirector;
    }
    
    public GameDirector getGameDirector() {
        return gameDirector;
    }
    
    public SlotMachineGUI getSlotMachineGUI() {
        return slotMachineGUI;
    }
    
    public ItemShopGUI getItemShopGUI() {
        return itemShopGUI;
    }
    
    public MainMenuGUI getMainMenuGUI() {
        return mainMenuGUI;
    }
}