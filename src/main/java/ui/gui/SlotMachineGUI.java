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
 * - Bet display: Shows current bet amount with controls to adjust
 * - Bet adjustment buttons: +5%, -5%, ALL IN, and manual bet input
 * - ITEMS tab (pullout on left): Shows purchased items in a 2x4 grid
 * - OPEN SHOP button: Navigates to Item Shop screen
 */
public class SlotMachineGUI extends JPanel {
    
    private MainWindow parent;
    
    private JPanel slotGridPanel;
    private JLabel[][] slotLabels;
    private JLabel moneyLabel;
    private JLabel debtLabel;
    private JLabel betLabel;
    private JButton pullTabButton;
    private JButton openShopButton;
    private JButton spinButton;
    private JButton betUpButton;
    private JButton betDownButton;
    private JButton allInButton;
    private JButton inputBetButton;
    private JPanel pulloutPanel;
    private JLabel backgroundImage;
    private boolean pulloutVisible = false;
    
    private List<String> inventoryItems;
    private Map<Symbols, ImageIcon> symbolImages;
    private ImageIcon defaultSymbolIcon;
    
    private int currentBet;
    private static final int DEFAULT_BET = 10;
    private static final int MIN_BET = 1;
    
    // Path to assets folder
    private static final String ASSETS_PATH = System.getProperty("user.dir") + "/assets/";
    private static final int SYMBOL_IMAGE_SIZE = 100;
    
    // Pullout tab constants - 2 columns, 4 rows (8 items max visible)
    private static final int PULLOUT_COLUMNS = 2;
    private static final int PULLOUT_ROWS = 4;
    private static final int ITEM_IMAGE_SIZE = 100;
    private static final int PULLOUT_CELL_SIZE = 110; // 100px image + 10px padding
    private static final int PULLOUT_WIDTH = PULLOUT_COLUMNS * PULLOUT_CELL_SIZE + 20; // ~240px
    private static final int PULLOUT_HEIGHT = PULLOUT_ROWS * PULLOUT_CELL_SIZE + 20; // ~460px
    
    public SlotMachineGUI(MainWindow parent) {
        this.parent = parent;
        this.inventoryItems = new ArrayList<>();
        this.symbolImages = new HashMap<>();
        this.currentBet = DEFAULT_BET;
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
        java.awt.Container contentPanel = (backgroundImage != null) ? backgroundImage : this;
        
        slotLabels = new JLabel[3][3];
        slotGridPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        slotGridPanel.setBackground(new Color(50, 50, 70, 200));
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
        
        // Money and Debt Displays
        moneyLabel = new JLabel("Money: $0");
        moneyLabel.setFont(new Font("Arial", Font.BOLD, 24));
        moneyLabel.setForeground(Color.GREEN);
        
        debtLabel = new JLabel("Debt Owed: $0");
        debtLabel.setFont(new Font("Arial", Font.BOLD, 24));
        debtLabel.setForeground(Color.RED);
        
        // Bet Display
        betLabel = new JLabel("Bet: $" + currentBet, SwingConstants.CENTER);
        betLabel.setFont(new Font("Arial", Font.BOLD, 20));
        betLabel.setForeground(Color.CYAN);
        betLabel.setOpaque(true);
        betLabel.setBackground(Color.BLACK);
        betLabel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        betLabel.setPreferredSize(new Dimension(120, 40));
        
        // ITEMS TAB BUTTON
        pullTabButton = new JButton("Items");
        pullTabButton.setFont(new Font("Arial", Font.BOLD, 16));
        pullTabButton.setBackground(new Color(100, 100, 150));
        pullTabButton.setPreferredSize(new Dimension(100, 45));
        pullTabButton.addActionListener(e -> togglePullout());
        
        // OPEN SHOP BUTTON
        openShopButton = new JButton("OPEN SHOP");
        openShopButton.setFont(new Font("Arial", Font.BOLD, 18));
        openShopButton.setBackground(new Color(0, 150, 200));
        openShopButton.setPreferredSize(new Dimension(180, 50));
        openShopButton.addActionListener(e -> parent.switchTo(MainWindow.Screen.ITEM_SHOP));
        
        // Bet control buttons
        betUpButton = new JButton("+5%");
        betUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        betUpButton.setBackground(new Color(0, 100, 0));
        betUpButton.setForeground(Color.WHITE);
        betUpButton.addActionListener(e -> adjustBet(true));
        
        betDownButton = new JButton("-5%");
        betDownButton.setFont(new Font("Arial", Font.BOLD, 14));
        betDownButton.setBackground(new Color(100, 0, 0));
        betDownButton.setForeground(Color.WHITE);
        betDownButton.addActionListener(e -> adjustBet(false));
        
        allInButton = new JButton("ALL IN");
        allInButton.setFont(new Font("Arial", Font.BOLD, 14));
        allInButton.setBackground(new Color(150, 150, 0));
        allInButton.setForeground(Color.WHITE);
        allInButton.addActionListener(e -> setAllInBet());
        
        inputBetButton = new JButton("Set");
        inputBetButton.setFont(new Font("Arial", Font.BOLD, 14));
        inputBetButton.setBackground(new Color(0, 100, 150));
        inputBetButton.setForeground(Color.WHITE);
        inputBetButton.addActionListener(e -> promptForBetAmount());
        
        // SPIN BUTTON
        spinButton = new JButton("SPIN");
        spinButton.setFont(new Font("Arial", Font.BOLD, 20));
        spinButton.setBackground(new Color(200, 150, 0));
        spinButton.setForeground(Color.WHITE);
        spinButton.addActionListener(e -> {
            if (parent.getGameDirector() != null) {
                parent.getGameDirector().onSpin(currentBet);
            }
        });
        
        // Add all components to content panel
        contentPanel.add(slotGridPanel);
        contentPanel.add(moneyLabel);
        contentPanel.add(debtLabel);
        contentPanel.add(betLabel);
        contentPanel.add(pullTabButton);
        contentPanel.add(openShopButton);
        contentPanel.add(betUpButton);
        contentPanel.add(betDownButton);
        contentPanel.add(allInButton);
        contentPanel.add(inputBetButton);
        contentPanel.add(spinButton);
    }
    
    private void layoutComponents() {
        int centerX = (1280 - 400) / 2;  // Center of slot grid (440 to 840)
        
        // Slot grid - stays centered
        slotGridPanel.setBounds(centerX, 100, 400, 400);
        
        // Money and Debt displays - top right
        moneyLabel.setBounds(1280 - 220, 20, 200, 40);
        debtLabel.setBounds(1280 - 220, 70, 200, 40);
        
        // Bet display - below slot grid, centered
        betLabel.setBounds(centerX + 140, 520, 120, 40);
        
        // Bet control buttons
        betDownButton.setBounds(centerX + 40, 520, 80, 40);
        betUpButton.setBounds(centerX + 290, 520, 80, 40);
        allInButton.setBounds(centerX + 40, 575, 80, 40);
        inputBetButton.setBounds(centerX + 290, 575, 80, 40);
        
        // Spin button
        spinButton.setBounds(centerX + 150, 570, 100, 50);
        
        // Open Shop button - bottom right
        openShopButton.setBounds(1280 - 200, 620, 180, 50);
        
        // Items tab button - moved up 30 pixels (Y=40)
        pullTabButton.setBounds(10, 40, 100, 45);
    }
    
    /**
     * Adjusts the current bet by 5% of playerMoney
     */
    private void adjustBet(boolean add) {
        int playerMoney = parent.getGameDirector().getPlayerMoney();
        int newBet = (int) Math.round(playerMoney * 0.05);
        
        
        if (add) {
            if (currentBet + newBet > playerMoney) {
                currentBet = playerMoney;
            }
            else {
                currentBet += newBet;
            }
        }
        else {
            if (currentBet - newBet < MIN_BET) {
                currentBet = MIN_BET;
            }
            else {
                currentBet -= newBet;
            }
        }
        updateBetDisplay();
    }
    
    /**
     * Sets the bet to the player's entire available money
     */
    private void setAllInBet() {
        if (parent.getGameDirector() != null) {
            int playerMoney = parent.getGameDirector().getPlayerMoney();
            currentBet = Math.max(playerMoney, MIN_BET);
            updateBetDisplay();
        } else {
            String moneyText = moneyLabel.getText().replace("Money: $", "");
            try {
                int playerMoney = Integer.parseInt(moneyText);
                currentBet = Math.max(playerMoney, MIN_BET);
                updateBetDisplay();
            } catch (NumberFormatException e) {
                currentBet = DEFAULT_BET;
                updateBetDisplay();
            }
        }
    }
    
    /**
     * Prompts the user to enter a custom bet amount
     */
    private void promptForBetAmount() {
        String input = JOptionPane.showInputDialog(this, 
            "Enter bet amount (min: $" + MIN_BET + "):", 
            "Set Bet", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (input != null && !input.trim().isEmpty()) {
            try {
                int newBet = Integer.parseInt(input.trim());
                
                if (newBet < MIN_BET) {
                    JOptionPane.showMessageDialog(this, 
                        "Bet cannot be less than $" + MIN_BET + "!",
                        "Invalid Bet", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                if (parent.getGameDirector() != null) {
                    int playerMoney = parent.getGameDirector().getPlayerMoney();
                    if (newBet > playerMoney) {
                        JOptionPane.showMessageDialog(this, 
                            "Bet cannot exceed your current money ($" + playerMoney + ")!",
                            "Invalid Bet", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                
                currentBet = newBet;
                updateBetDisplay();
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a valid number!",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void updateBetDisplay() {
        betLabel.setText("Bet: $" + currentBet);
    }
    
    public int getCurrentBet() {
        return currentBet;
    }
    
    public void setCurrentBet(int bet) {
        if (bet >= MIN_BET) {
            this.currentBet = bet;
            updateBetDisplay();
        }
    }
    
    private void setupPulloutTab() {
        java.awt.Container contentPanel = (backgroundImage != null) ? backgroundImage : this;
        
        pulloutPanel = new JPanel();
        pulloutPanel.setBackground(new Color(60, 60, 80));
        pulloutPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        
        // GridLayout: 4 rows, 2 columns (rows first, then columns)
        pulloutPanel.setLayout(new GridLayout(PULLOUT_ROWS, PULLOUT_COLUMNS, 10, 10));
        pulloutPanel.setBounds(-PULLOUT_WIDTH, 100, PULLOUT_WIDTH, PULLOUT_HEIGHT);
        
        contentPanel.add(pulloutPanel);
    }
    
    private void togglePullout() {
        pulloutVisible = !pulloutVisible;
        int targetX = pulloutVisible ? 0 : -PULLOUT_WIDTH;
        Timer timer = new Timer(10, null);
        timer.addActionListener(e -> {
            int currentX = pulloutPanel.getX();
            int step = pulloutVisible ? 15 : -15;
            int newX = currentX + step;
            if ((pulloutVisible && newX >= targetX) || (!pulloutVisible && newX <= targetX)) {
                pulloutPanel.setBounds(targetX, 100, PULLOUT_WIDTH, PULLOUT_HEIGHT);
                timer.stop();
            } else {
                pulloutPanel.setBounds(newX, 100, PULLOUT_WIDTH, PULLOUT_HEIGHT);
            }
            pulloutPanel.repaint();
        });
        timer.start();
        refreshPulloutDisplay();
    }
    
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
    
    public void clearInventory() {
        inventoryItems.clear();
        refreshPulloutDisplay();
    }
    
    public List<String> getInventoryItems() {
        return inventoryItems;
    }
    
    private void refreshPulloutDisplay() {
        pulloutPanel.removeAll();
        
        // Display up to 8 items (2 columns x 4 rows)
        int maxItems = PULLOUT_COLUMNS * PULLOUT_ROWS;
        
        for (int i = 0; i < maxItems; i++) {
            if (i < inventoryItems.size()) {
                // Show actual item
                String item = inventoryItems.get(i);
                JPanel itemPanel = createItemPanel(item);
                pulloutPanel.add(itemPanel);
            } else {
                // Show empty slot
                JPanel emptyPanel = createEmptyPanel();
                pulloutPanel.add(emptyPanel);
            }
        }
        
        pulloutPanel.revalidate();
        pulloutPanel.repaint();
    }
    
    private JPanel createItemPanel(String itemName) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setBackground(new Color(80, 80, 100));
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        // Load and display 100x100 image
        String fileName = itemName.toLowerCase().replace(" ", "_").replace("!", "").replace("x", "");
        ImageIcon itemIcon = loadAndScaleImage(ASSETS_PATH + "items/" + fileName + ".png", ITEM_IMAGE_SIZE, ITEM_IMAGE_SIZE);
        if (itemIcon == null || itemIcon.getIconWidth() <= 0) {
            itemIcon = loadAndScaleImage(ASSETS_PATH + "items/default_item.png", ITEM_IMAGE_SIZE, ITEM_IMAGE_SIZE);
        }
        
        JLabel imageLabel = new JLabel(itemIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setToolTipText(itemName);
        itemPanel.add(imageLabel, BorderLayout.CENTER);
        
        // Add item name label below the image
        JLabel nameLabel = new JLabel(truncateItemName(itemName, 15), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        nameLabel.setForeground(Color.WHITE);
        itemPanel.add(nameLabel, BorderLayout.SOUTH);
        
        return itemPanel;
    }
    
    private JPanel createEmptyPanel() {
        JPanel emptyPanel = new JPanel(new BorderLayout());
        emptyPanel.setBackground(new Color(50, 50, 70));
        emptyPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        // Add placeholder text
        JLabel emptyLabel = new JLabel("Empty", SwingConstants.CENTER);
        emptyLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        emptyLabel.setForeground(Color.LIGHT_GRAY);
        emptyPanel.add(emptyLabel, BorderLayout.CENTER);
        
        return emptyPanel;
    }
    
    private String truncateItemName(String name, int maxLength) {
        if (name.length() <= maxLength) {
            return name;
        }
        return name.substring(0, maxLength - 3) + "...";
    }
    
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
    
public void refreshDisplay() {
    if (parent.getGameDirector() != null) {
        moneyLabel.setText("Money: $" + parent.getGameDirector().getPlayerMoney());
        debtLabel.setText("Debt Owed: $" + parent.getGameDirector().getPlayerDebt());
        
        // ONLY adjust bet if current bet exceeds player's money
        int playerMoney = parent.getGameDirector().getPlayerMoney();
        if (currentBet > playerMoney && playerMoney > 0) {
            currentBet = playerMoney;
            parent.getGameDirector().getSlotMachine().setCurrentBet(currentBet);
            updateBetDisplay();
        } else if (playerMoney <= 0 && currentBet > MIN_BET) {
            currentBet = MIN_BET;
            parent.getGameDirector().getSlotMachine().setCurrentBet(currentBet);
            updateBetDisplay();
        }
        
        
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