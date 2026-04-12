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
 * Features:
 * - 3x3 grid for slot symbols
 * - Money and debt displays
 * - Pullout tab on the left showing inventory items
 * - Shop button to navigate to Item Shop
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
    private boolean pulloutVisible = false;
    
    private List<String> inventoryItems;
    private Map<Symbols, ImageIcon> symbolImages;
    private ImageIcon defaultSymbolIcon;
    
    private static final String ASSETS_PATH = System.getProperty("user.dir") + "/assets/";
    private static final int SYMBOL_IMAGE_SIZE = 100;
    
    public SlotMachineGUI(MainWindow parent) {
        this.parent = parent;
        this.inventoryItems = new ArrayList<>();
        this.symbolImages = new HashMap<>();
        loadSymbolImages();
        initComponents();
        layoutComponents();
        setupPulloutTab();
    }
    
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
        // Return a colored placeholder if image not found
        return createPlaceholderIcon(width, height);
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
        setBackground(new Color(30, 30, 50));
        setLayout(null);
        
        slotLabels = new JLabel[3][3];
        slotGridPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        slotGridPanel.setBackground(new Color(50, 50, 70));
        slotGridPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        
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
        
        openShopButton = new JButton("OPEN SHOP");
        openShopButton.setFont(new Font("Arial", Font.BOLD, 18));
        openShopButton.setBackground(new Color(0, 150, 200));
        openShopButton.addActionListener(e -> parent.switchTo(MainWindow.Screen.ITEM_SHOP));
        
        spinButton = new JButton("SPIN");
        spinButton.setFont(new Font("Arial", Font.BOLD, 20));
        spinButton.setBackground(new Color(200, 150, 0));
        spinButton.setForeground(Color.WHITE);
        spinButton.addActionListener(e -> {
            if (parent.getGameDirector() != null) {
                parent.getGameDirector().onSpin();
            }
        });
        
        pullTabButton.addActionListener(e -> togglePullout());
    }
    
    private void layoutComponents() {
        int centerX = (1280 - 400) / 2;
        slotGridPanel.setBounds(centerX, 100, 400, 400);
        add(slotGridPanel);
        
        spinButton.setBounds(centerX + 150, 520, 100, 50);
        add(spinButton);
        
        moneyLabel.setBounds(1280 - 220, 20, 200, 40);
        add(moneyLabel);
        
        debtLabel.setBounds(1280 - 220, 70, 200, 40);
        add(debtLabel);
        
        openShopButton.setBounds(1280 - 160, 620, 140, 50);
        add(openShopButton);
        
        pullTabButton.setBounds(10, 300, 80, 40);
        add(pullTabButton);
    }
    
    private void setupPulloutTab() {
        pulloutPanel = new JPanel();
        pulloutPanel.setBackground(new Color(60, 60, 80));
        pulloutPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        pulloutPanel.setLayout(new GridLayout(0, 4, 5, 5));
        pulloutPanel.setBounds(-300, 100, 300, 520);
        add(pulloutPanel);
    }
    
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
     * Called by GameDirector when player purchases items.
     */
    public void addInventoryItem(String itemName) {
        inventoryItems.add(itemName);
        refreshPulloutDisplay();
    }
    
    /**
     * Adds multiple items to the inventory tab.
     */
    public void addInventoryItems(List<String> items) {
        inventoryItems.addAll(items);
        refreshPulloutDisplay();
    }
    
    /**
     * Removes an item from the inventory by index.
     */
    public void removeInventoryItem(int index) {
        if (index >= 0 && index < inventoryItems.size()) {
            inventoryItems.remove(index);
            refreshPulloutDisplay();
        }
    }
    
    /**
     * Clears all inventory items.
     */
    public void clearInventory() {
        inventoryItems.clear();
        refreshPulloutDisplay();
    }
    
    /**
     * Returns the current inventory list.
     */
    public List<String> getInventoryItems() {
        return inventoryItems;
    }
    
    private void refreshPulloutDisplay() {
        pulloutPanel.removeAll();
        for (String item : inventoryItems) {
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBackground(new Color(80, 80, 100));
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            
            // Try to load item image
            ImageIcon itemIcon = loadAndScaleImage(ASSETS_PATH + "items/" + item.toLowerCase().replace(" ", "_") + ".png", 60, 60);
            if (itemIcon == null) {
                itemIcon = loadAndScaleImage(ASSETS_PATH + "items/default_item.png", 60, 60);
            }
            
            JLabel itemLabel = new JLabel(itemIcon);
            itemLabel.setHorizontalAlignment(SwingConstants.CENTER);
            itemLabel.setToolTipText(item);
            itemPanel.add(itemLabel, BorderLayout.CENTER);
            
            pulloutPanel.add(itemPanel);
        }
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
     * Updates the slot grid display with new symbols from the domain.
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
    
    /**
     * Resets all slots to a default symbol.
     */
    public void resetSlots() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                slotLabels[i][j].setIcon(defaultSymbolIcon);
            }
        }
    }
    
    /**
     * Updates the money and debt displays.
     */
    public void refreshDisplay() {
        if (parent.getGameDirector() != null) {
            moneyLabel.setText("Money: $" + parent.getGameDirector().getPlayerMoney());
            debtLabel.setText("Debt Owed: $" + parent.getGameDirector().getPlayerDebt());
        }
    }
    
    /**
     * Shows a win notification.
     */
    public void showWinNotification(int amount) {
        JOptionPane.showMessageDialog(this, "You won $" + amount + "!");
    }
    
    /**
     * Shows a lose notification.
     */
    public void showLoseNotification() {
        JOptionPane.showMessageDialog(this, "No win this spin. Better luck next time!");
    }
    
    /**
     * Shows insufficient funds message.
     */
    public void showInsufficientFundsMessage() {
        JOptionPane.showMessageDialog(this, 
            "Not enough money to spin!\nVisit the shop to see if you can purchase helpful items.",
            "Insufficient Funds", JOptionPane.WARNING_MESSAGE);
    }
}