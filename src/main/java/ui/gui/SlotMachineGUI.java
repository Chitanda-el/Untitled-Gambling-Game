// SlotMachineGUI.java
package ui.gui;

import domain.SlotMachine.Symbols;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Slot Machine screen for Untitled Gambling Game.
 * 
 * This is the main gameplay screen. Features:
 * - 3x3 grid displaying slot symbols (images from assets/symbols/)
 * - SPIN button: Triggers a new spin via GameDirector.onSpin()
 * - Money display: Shows player's current currency
 * - Debt display: Shows outstanding debt that must be repaid
 * - ITEMS tab (pullout on left): Shows purchased items in a grid
 * - OPEN SHOP button: Navigates to Item Shop screen
 * 
 * The slot machine does NOT handle game logic directly. Instead, it:
 * 1. Calls GameDirector.onSpin() when spin button is clicked
 * 2. Receives updated symbol grid via updateSlotGrid()
 * 3. Calls GameDirector methods to get money/debt values
 * 
 * This separation keeps the GUI purely visual while GameDirector
 * manages domain objects (Player, SlotMachine, ItemShop).
 */
public class SlotMachineGUI extends JPanel {
    
    private MainWindow parent;
    
    private JPanel slotGridPanel;
    private JLabel[][] slotLabels;
    private JLabel moneyLabel;
    private JLabel debtLabel;
    private JButton pullTabButton;
    private JButton openShopButton;
    private JButton spinButton;
    private JPanel pulloutPanel;
    private JLabel backgroundImage;
    private boolean pulloutVisible = false;
    
    private List<String> inventoryItems;
    private Map<Symbols, ImageIcon> symbolImages;
    private ImageIcon defaultSymbolIcon;
    
    // Path to assets folder using system-independent file separator
    private static final String ASSETS_PATH = System.getProperty("user.dir") + "/assets/";
    private static final int SYMBOL_IMAGE_SIZE = 100;
    
    public SlotMachineGUI(MainWindow parent) {
        this.parent = parent;
        this.inventoryItems = new ArrayList<>();
        this.symbolImages = new HashMap<>();
        loadBackgroundImage();
        loadSymbolImages();
        initComponents();
        layoutComponents();
        setupPulloutTab();
    }
    
    /**
     * Loads the background image for the slot machine screen.
     * Looks for assets/ui/slot_bg.png or slot_bg.jpg
     */
    private void loadBackgroundImage() {
        ImageIcon bgImage = loadImage(ASSETS_PATH + "ui/slot_bg.png");
        if (bgImage == null) {
            bgImage = loadImage(ASSETS_PATH + "ui/slot_bg.jpg");
        }
        
        if (bgImage != null) {
            Image scaledBg = bgImage.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
            backgroundImage = new JLabel(new ImageIcon(scaledBg));
            backgroundImage.setLayout(null);
            setLayout(null);
            add(backgroundImage);
            backgroundImage.setBounds(0, 0, 1280, 720);
        } else {
            setBackground(new Color(30, 30, 50));
            setLayout(null);
        }
    }
    
    /**
     * Loads all symbol images from assets/symbols/ folder.
     * Maps each domain.SlotMachine.Symbols enum value to its corresponding image.
     * If an image is missing, a colored placeholder is used instead.
     */
    private void loadSymbolImages() {
        defaultSymbolIcon = loadAndScaleImage(ASSETS_PATH + "symbols/default.png", SYMBOL_IMAGE_SIZE, SYMBOL_IMAGE_SIZE);
        
        
        symbolImages.put(Symbols.DUCK, loadAndScaleImage(ASSETS_PATH + "symbols/duck.png", SYMBOL_IMAGE_SIZE, SYMBOL_IMAGE_SIZE));
        symbolImages.put(Symbols.HEART, loadAndScaleImage(ASSETS_PATH + "symbols/heart.png", SYMBOL_IMAGE_SIZE, SYMBOL_IMAGE_SIZE));
        symbolImages.put(Symbols.CHERRY, loadAndScaleImage(ASSETS_PATH + "symbols/cherry.png", SYMBOL_IMAGE_SIZE, SYMBOL_IMAGE_SIZE));
        symbolImages.put(Symbols.SEVEN, loadAndScaleImage(ASSETS_PATH + "symbols/seven.png", SYMBOL_IMAGE_SIZE, SYMBOL_IMAGE_SIZE));
        symbolImages.put(Symbols.CLOVER, loadAndScaleImage(ASSETS_PATH + "symbols/clover.png", SYMBOL_IMAGE_SIZE, SYMBOL_IMAGE_SIZE));
    }
    
    private ImageIcon loadAndScaleImage(String path, int width, int height) {
        try {
            File imgFile = new File(path);
            if (imgFile.exists()) {
                ImageIcon original = new ImageIcon(imgFile.getAbsolutePath());
                Image scaledImage = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            }
        } catch (Exception e) {
            System.err.println("Failed to load image: " + path);
        }
        return createPlaceholderIcon(width, height);
    }
    
    private ImageIcon loadImage(String path) {
        try {
            File imgFile = new File(path);
            if (imgFile.exists()) {
                return new ImageIcon(imgFile.getAbsolutePath());
            }
        } catch (Exception e) {
            // Silently fail
        }
        return null;
    }
    
    private ImageIcon createPlaceholderIcon(int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, width - 1, height - 1);
        g2d.dispose();
        return new ImageIcon(img);
    }
    
    private void initComponents() {
        // Determine which component to add child components to
        // If we have a background image, add to it; otherwise add directly to this panel
        java.awt.Container contentPanel = (backgroundImage != null) ? backgroundImage : this;
        
        slotLabels = new JLabel[3][3];
        slotGridPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        slotGridPanel.setBackground(new Color(50, 50, 70, 200));  // Semi-transparent
        slotGridPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        slotGridPanel.setOpaque(true);
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                slotLabels[i][j] = new JLabel(defaultSymbolIcon, SwingConstants.CENTER);
                slotLabels[i][j].setOpaque(true);
                slotLabels[i][j].setBackground(Color.WHITE);
                slotLabels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                slotGridPanel.add(slotLabels[i][j]);
            }
        }
        
        moneyLabel = new JLabel("Money: $0");
        moneyLabel.setFont(new Font("Arial", Font.BOLD, 24));
        moneyLabel.setForeground(Color.GREEN);
        
        debtLabel = new JLabel("Debt Owed: $0");
        debtLabel.setFont(new Font("Arial", Font.BOLD, 24));
        debtLabel.setForeground(Color.RED);
        
        pullTabButton = new JButton("ITEMS >>");
        pullTabButton.setFont(new Font("Arial", Font.BOLD, 16));
        pullTabButton.setBackground(new Color(100, 100, 150));
        
        // OPEN SHOP BUTTON: Navigates to the Item Shop screen
        // The shop will request current stock from GameDirector when shown
        openShopButton = new JButton("OPEN SHOP");
        openShopButton.setFont(new Font("Arial", Font.BOLD, 18));
        openShopButton.setBackground(new Color(0, 150, 200));
        openShopButton.addActionListener(e -> parent.switchTo(MainWindow.Screen.ITEM_SHOP));
        
        // SPIN BUTTON: Core gameplay action
        // Calls GameDirector.onSpin() which:
        //   1. Checks if player has enough money
        //   2. Generates random symbols via SlotMachine.spin()
        //   3. Evaluates winning patterns
        //   4. Updates player's money
        //   5. Returns the new symbol grid to display
        spinButton = new JButton("SPIN");
        spinButton.setFont(new Font("Arial", Font.BOLD, 20));
        spinButton.setBackground(new Color(200, 150, 0));
        spinButton.setForeground(Color.WHITE);
        spinButton.addActionListener(e -> {
            if (parent.getGameDirector() != null) {
                parent.getGameDirector().onSpin();
            }
        });
        
        // ITEMS TAB BUTTON: Toggles the pullout panel showing purchased items
        pullTabButton.addActionListener(e -> togglePullout());
        
        // Add components to content panel (either background image or this panel)
        contentPanel.add(slotGridPanel);
        contentPanel.add(spinButton);
        contentPanel.add(moneyLabel);
        contentPanel.add(debtLabel);
        contentPanel.add(openShopButton);
        contentPanel.add(pullTabButton);
    }
    
    private void layoutComponents() {
        int centerX = (1280 - 400) / 2;
        slotGridPanel.setBounds(centerX, 100, 400, 400);
        spinButton.setBounds(centerX + 150, 520, 100, 50);
        moneyLabel.setBounds(1280 - 220, 20, 200, 40);
        debtLabel.setBounds(1280 - 220, 70, 200, 40);
        openShopButton.setBounds(1280 - 160, 620, 140, 50);
        pullTabButton.setBounds(10, 300, 80, 40);
    }
    
    private void setupPulloutTab() {
        // Determine which component to add the pullout panel to
        java.awt.Container contentPanel = (backgroundImage != null) ? backgroundImage : this;
        
        pulloutPanel = new JPanel();
        pulloutPanel.setBackground(new Color(60, 60, 80));
        pulloutPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        pulloutPanel.setLayout(new GridLayout(0, 4, 5, 5));
        pulloutPanel.setBounds(-300, 100, 300, 520);
        contentPanel.add(pulloutPanel);
    }
    
    /**
     * Animates the pullout tab sliding in/out from the left edge.
     */
    private void togglePullout() {
        pulloutVisible = !pulloutVisible;
        int targetX = pulloutVisible ? 0 : -300;
        Timer timer = new Timer(10, null);
        timer.addActionListener(e -> {
            int currentX = pulloutPanel.getX();
            int step = pulloutVisible ? 15 : -15;
            int newX = currentX + step;
            if ((pulloutVisible && newX >= targetX) || (!pulloutVisible && newX <= targetX)) {
                pulloutPanel.setBounds(targetX, 100, 300, 520);
                timer.stop();
            } else {
                pulloutPanel.setBounds(newX, 100, 300, 520);
            }
            pulloutPanel.repaint();
        });
        timer.start();
        refreshPulloutDisplay();
    }
    
    /**
     * Adds an item to the inventory tab.
     * Called by GameDirector after successful purchase in ItemShop.
     * 
     * @param itemName the name of the item to display
     */
    public void addInventoryItem(String itemName) {
        inventoryItems.add(itemName);
        refreshPulloutDisplay();
    }
    
    public void addInventoryItems(List<String> items) {
        inventoryItems.addAll(items);
        refreshPulloutDisplay();
    }
    
    public void removeInventoryItem(int index) {
        if (index >= 0 && index < inventoryItems.size()) {
            inventoryItems.remove(index);
            refreshPulloutDisplay();
        }
    }
    
    /**
     * Clears all inventory items.
     * Called by GameDirector.onAbandonSave() when resetting player progress.
     */
    public void clearInventory() {
        inventoryItems.clear();
        refreshPulloutDisplay();
    }
    
    public List<String> getInventoryItems() {
        return inventoryItems;
    }
    
    private void refreshPulloutDisplay() {
        pulloutPanel.removeAll();
        for (String item : inventoryItems) {
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBackground(new Color(80, 80, 100));
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            
            String fileName = item.toLowerCase().replace(" ", "_").replace("!", "").replace("x", "");
            ImageIcon itemIcon = loadAndScaleImage(ASSETS_PATH + "items/" + fileName + ".png", 60, 60);
            if (itemIcon == null || itemIcon.getIconWidth() <= 0) {
                itemIcon = loadAndScaleImage(ASSETS_PATH + "items/default_item.png", 60, 60);
            }
            
            JLabel itemLabel = new JLabel(itemIcon);
            itemLabel.setHorizontalAlignment(SwingConstants.CENTER);
            itemLabel.setToolTipText(item);
            itemPanel.add(itemLabel, BorderLayout.CENTER);
            
            pulloutPanel.add(itemPanel);
        }
        // Fill empty slots to maintain grid layout
        for (int i = inventoryItems.size(); i < 20; i++) {
            JLabel emptyLabel = new JLabel("", SwingConstants.CENTER);
            emptyLabel.setBackground(new Color(50, 50, 70));
            emptyLabel.setOpaque(true);
            pulloutPanel.add(emptyLabel);
        }
        pulloutPanel.revalidate();
        pulloutPanel.repaint();
    }
    
    /**
     * Updates the slot grid display with new symbols from GameDirector.
     * Called after GameDirector.onSpin() completes.
     * 
     * @param grid 3x3 array of Symbols from domain.SlotMachine.spin()
     */
    public void updateSlotGrid(Symbols[][] grid) {
        if (grid == null) return;
        
        for (int i = 0; i < 3 && i < grid.length; i++) {
            for (int j = 0; j < 3 && j < grid[i].length; j++) {
                ImageIcon icon = symbolImages.getOrDefault(grid[i][j], defaultSymbolIcon);
                slotLabels[i][j].setIcon(icon);
            }
        }
    }
    
    public void resetSlots() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                slotLabels[i][j].setIcon(defaultSymbolIcon);
            }
        }
    }
    
    /**
     * Updates the money and debt displays.
     * Called when switching to this screen and after each spin.
     * Values are retrieved from GameDirector.getPlayerMoney() and getPlayerDebt().
     */
    public void refreshDisplay() {
        if (parent.getGameDirector() != null) {
            moneyLabel.setText("Money: $" + parent.getGameDirector().getPlayerMoney());
            debtLabel.setText("Debt Owed: $" + parent.getGameDirector().getPlayerDebt());
        }
    }
    
    public void showWinNotification(int amount) {
        JOptionPane.showMessageDialog(this, "You won $" + amount + "!");
    }
    
    public void showLoseNotification() {
        JOptionPane.showMessageDialog(this, "No win this spin. Better luck next time!");
    }
    
    public void showInsufficientFundsMessage() {
        JOptionPane.showMessageDialog(this, 
            "Not enough money to spin!\nVisit the shop to see if you can purchase helpful items.",
            "Insufficient Funds", JOptionPane.WARNING_MESSAGE);
    }
}