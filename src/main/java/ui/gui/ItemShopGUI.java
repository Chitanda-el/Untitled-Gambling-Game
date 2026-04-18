// ItemShopGUI.java
package ui.gui;

import domain.items.Item;
import domain.ItemShop;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Item Shop screen for Untitled Gambling Game.
 * 
 * This screen allows players to spend their money on items that affect gameplay.
 * Features:
 * - Grid display of purchasable items (3 columns)
 * - Each item shows: image, name, price, description
 * - BUY button for each item - calls GameDirector.onPurchaseItem()
 * - REROLL SHOP button - pays to refresh available items (calls GameDirector.onRerollShop())
 * - BACK button - returns to slot machine screen
 * - Money display - shows player's current currency
 * 
 * The shop does NOT manage inventory or purchases directly. Instead:
 * 1. GameDirector provides the current shop stock via getShopItems()
 * 2. GameDirector validates purchases and updates Player/ItemShop domain objects
 * 3. This GUI only displays the data and relays user actions
 */
public class ItemShopGUI extends JPanel {
    
    private MainWindow parent;
    
    private JPanel shopGridPanel;
    private JLabel moneyLabel;
    private JButton backButton;
    private JButton rerollButton;
    private JScrollPane scrollPane;
    private JLabel backgroundImage;
    
    private List<Item> currentStock;
    private List<JButton> buyButtons;
    private List<JPanel> itemCards;
    
    private static final String ASSETS_PATH = System.getProperty("user.dir") + "/assets/";
    private static final int ITEM_IMAGE_SIZE = 100;
    
    public ItemShopGUI(MainWindow parent) {
        this.parent = parent;
        this.currentStock = new ArrayList<>();
        this.buyButtons = new ArrayList<>();
        this.itemCards = new ArrayList<>();
        loadBackgroundImage();
        initComponents();
        layoutComponents();
    }
    
    /**
     * Loads the background image for the shop screen.
     * Looks for assets/ui/shop_bg.png or shop_bg.jpg
     */
    private void loadBackgroundImage() {
        ImageIcon bgImage = loadImage(ASSETS_PATH + "ui/shop_bg.png");
        if (bgImage == null) {
            bgImage = loadImage(ASSETS_PATH + "ui/shop_bg.jpg");
        }
        
        if (bgImage != null) {
            Image scaledBg = bgImage.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
            backgroundImage = new JLabel(new ImageIcon(scaledBg));
            backgroundImage.setLayout(null);
            setLayout(null);
            add(backgroundImage);
            backgroundImage.setBounds(0, 0, 1280, 720);
        } else {
            setBackground(new Color(40, 20, 60));
            setLayout(null);
        }
    }
    
    private void initComponents() {
        // Determine which component to add child components to
        // If we have a background image, add to it; otherwise add directly to this panel
        java.awt.Container contentPanel = (backgroundImage != null) ? backgroundImage : this;
        
        moneyLabel = new JLabel("Money: $0");
        moneyLabel.setFont(new Font("Arial", Font.BOLD, 28));
        moneyLabel.setForeground(Color.GREEN);
        
        // BACK BUTTON: Returns to slot machine screen
        // The slot machine will refresh its display when switched back
        backButton = new JButton("BACK TO SLOT MACHINE");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setBackground(new Color(150, 100, 0));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> parent.switchTo(MainWindow.Screen.SLOT_MACHINE));
        
        // REROLL BUTTON: Pays to refresh available shop items
        // Calls GameDirector.onRerollShop() which:
        //   1. Checks if player has enough money (typically $50)
        //   2. Deducts the reroll cost
        //   3. Calls ItemShop.refreshShop() to generate new random items
        //   4. Returns the new stock via getShopItems()
        rerollButton = new JButton("REROLL SHOP ($50)");
        rerollButton.setFont(new Font("Arial", Font.BOLD, 16));
        rerollButton.setBackground(new Color(100, 100, 200));
        rerollButton.setForeground(Color.WHITE);
        rerollButton.addActionListener(e -> {
    if (parent.getGameDirector() != null && parent.getGameDirector().getItemShop() != null) {
        boolean success = parent.getGameDirector().getItemShop().rerollShop(
            parent.getGameDirector().getPlayer()  // Need to add getPlayer() to GameDirector
        );
        if (success) {
            refreshDisplay();
            List<Item> newStock = parent.getGameDirector().getItemShop().getCurrentStock();
            updateShopStock(newStock);
            resetBuyButtons();
            JOptionPane.showMessageDialog(this, "Shop refreshed!");
        } else {
            JOptionPane.showMessageDialog(this, 
                "Not enough money to reroll shop! ($" + 
                parent.getGameDirector().getItemShop().getRerollCost() + " required)",
                "Insufficient Funds", JOptionPane.WARNING_MESSAGE);
        }
    }
});
        
        
        shopGridPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        shopGridPanel.setBackground(new Color(40, 20, 60, 200));  // Semi-transparent
        shopGridPanel.setOpaque(true);
        
        scrollPane = new JScrollPane(shopGridPanel);
        scrollPane.setBackground(new Color(40, 20, 60));
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(40, 20, 60));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        contentPanel.add(moneyLabel);
        contentPanel.add(backButton);
        contentPanel.add(rerollButton);
        contentPanel.add(scrollPane);
    }
    
    private void layoutComponents() {
        moneyLabel.setBounds(1280 - 250, 10, 230, 40);
        backButton.setBounds(30, 620, 350, 50);
        rerollButton.setBounds(1280 - 250, 620, 200, 50);
        scrollPane.setBounds(100, 80, 1080, 500);
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
    
    private ImageIcon loadItemImage(Item item, int width, int height) {
    // Map item names to correct asset filenames
    String fileName = getItemAssetFileName(item);
    String path = ASSETS_PATH + "items/" + fileName + ".png";
    
    System.out.println("Loading item image: " + path);
    
    try {
        File imgFile = new File(path);
        if (imgFile.exists()) {
            ImageIcon original = new ImageIcon(imgFile.getAbsolutePath());
            Image scaledImage = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            System.out.println("Image not found: " + path);
        }
    } catch (Exception e) {
        System.err.println("Failed to load image for: " + item.getName());
    }
    
    // Try default item image
    try {
        File defaultFile = new File(ASSETS_PATH + "items/default_item.png");
        if (defaultFile.exists()) {
            ImageIcon original = new ImageIcon(defaultFile.getAbsolutePath());
            Image scaledImage = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        }
    } catch (Exception e) {
        // Fall through to placeholder
    }
    
    return createPlaceholderIcon(width, height, item.getName().substring(0, Math.min(2, item.getName().length())));
}
    
    private ImageIcon createPlaceholderIcon(int width, int height, String text) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(new Color(100, 80, 120));
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        g2d.drawString(text, (width - textWidth) / 2, (height + textHeight / 2) / 2);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, width - 1, height - 1);
        g2d.dispose();
        return new ImageIcon(img);
    }
    
    /**
     * Updates the shop display with the current stock from GameDirector.
     * 
     * @param items list of items currently available for purchase
     */
    public void updateShopStock(List<Item> items) {
    System.out.println("Updating shop stock with " + (items != null ? items.size() : 0) + " items");
    
    currentStock.clear();
    buyButtons.clear();
    itemCards.clear();
    shopGridPanel.removeAll();
    
    if (items != null && !items.isEmpty()) {
        currentStock.addAll(items);
        
        for (int i = 0; i < currentStock.size(); i++) {
            Item item = currentStock.get(i);
            System.out.println("Adding item to shop: " + item.getName());
            JPanel card = createItemCard(item, i);
            itemCards.add(card);
            shopGridPanel.add(card);
        }
    } else {
        // Show empty message when no items
        JLabel emptyLabel = new JLabel("No items available! Check back later.", SwingConstants.CENTER);
        emptyLabel.setFont(new Font("Arial", Font.BOLD, 18));
        emptyLabel.setForeground(Color.WHITE);
        shopGridPanel.add(emptyLabel);
    }
    
    shopGridPanel.revalidate();
    shopGridPanel.repaint();
}
    
    private JPanel createItemCard(Item item, int index) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(80, 60, 100));
        card.setPreferredSize(new Dimension(200, 250));
        card.setMaximumSize(new Dimension(200, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.YELLOW, 2),
            BorderFactory.createEmptyBorder(100,10,10,10)
        ));
        
        // Item image
        ImageIcon itemIcon = loadItemImage(item, ITEM_IMAGE_SIZE, ITEM_IMAGE_SIZE);
        JLabel iconLabel = new JLabel(itemIcon, SwingConstants.CENTER);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Item name
        JLabel nameLabel = new JLabel(item.getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(Color.WHITE);
        
        // Item price - get actual price with multiplier from ItemShop
        int actualPrice = item.getCost(); // Default fallback
        if (parent.getGameDirector() != null && parent.getGameDirector().getItemShop() != null) {
            actualPrice = parent.getGameDirector().getItemShop().getItemActualPrice(item);
        }
        JLabel priceLabel = new JLabel("$" + actualPrice, SwingConstants.CENTER);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        priceLabel.setForeground(Color.YELLOW);
        
        // Item description
        String desc = item.getDescription();
        JTextArea descLabel = new JTextArea(desc);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        descLabel.setForeground(Color.LIGHT_GRAY);
        descLabel.setBackground(new Color(80, 60, 100));
        descLabel.setLineWrap(true);
        descLabel.setWrapStyleWord(true);
        descLabel.setEditable(false);
        descLabel.setFocusable(false);
        descLabel.setBorder(null);
        
        // BUY BUTTON: Attempts to purchase the item
        // Calls GameDirector.onPurchaseItem(index) which:
        //   1. Checks if item exists in shop stock
        //   2. Checks if player can afford it
        //   3. Deducts money from player
        //   4. Adds item to player inventory
        //   5. Removes item from shop stock
        //   6. Returns true if successful
        JButton buyButton = new JButton("BUY");
        buyButton.setBackground(new Color(0, 150, 0));
        buyButton.setForeground(Color.WHITE);
        final int itemIndex = index;
        buyButton.addActionListener(e -> {
            if (parent.getGameDirector() != null) {
                boolean success = parent.getGameDirector().onPurchaseItem(itemIndex);
                if (success) {
                    buyButton.setEnabled(false);
                    buyButton.setText("SOLD");
                    buyButton.setBackground(new Color(100, 100, 100));
                    refreshDisplay();
                    JOptionPane.showMessageDialog(this, 
                        "You purchased " + item.getName() + "!\nIt has been added to your items tab.",
                        "Purchase Successful", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Cannot purchase " + item.getName() + "!\nNot enough money or item unavailable.",
                        "Purchase Failed", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        buyButtons.add(buyButton);
        
        JPanel textPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        textPanel.setBackground(new Color(80, 60, 100));
        textPanel.add(nameLabel);
        textPanel.add(priceLabel);
        textPanel.add(descLabel);
        
        card.add(iconLabel, BorderLayout.NORTH);
        card.add(textPanel, BorderLayout.CENTER);
        card.add(buyButton, BorderLayout.SOUTH);
        
        return card;
    }
    
    
    
/**
 * Maps item names to their corresponding asset filenames.
 * 
 * @param item the item to get the filename for
 * @return the asset filename (without .png extension)
 */
    private String getItemAssetFileName(Item item) {
    String name = item.getName().toLowerCase();
    
    if (name.contains("bottom") || name.contains("bell")) {
        return "bottom_bell";
    } else if (name.contains("left") || name.contains("leech")) {
        return "left_leech";
    } else if (name.contains("right") || name.contains("tight")) {
        return "right_tight";
    } else if (name.contains("top") || name.contains("analyzer")) {
        return "top_analyzer";
    } else if (name.contains("ascend") || name.contains("ladder")){
        return "ascend_ladder";
    } else if (name.contains("hell") || name.contains("stair")){
        return "hell_stair";
    } else if (name.contains("collumn") || name.contains("photo")) {
        return "collumn_photo";
    } else {
        // Default: convert name to lowercase with underscores
        return "default_item";
    }
}
    
    /**
     * Updates the money display.
     * Called when switching to this screen and after purchases/rerolls.
     */
    public void refreshDisplay() {
        if (parent.getGameDirector() != null) {
            moneyLabel.setText("Money: $" + parent.getGameDirector().getPlayerMoney());
        }
    }
    
    /**
     * Disables a specific buy button (called after purchase).
     * 
     * @param index index of the button to disable
     */
    public void disableBuyButton(int index) {
        if (index >= 0 && index < buyButtons.size()) {
            buyButtons.get(index).setEnabled(false);
            buyButtons.get(index).setText("SOLD");
            buyButtons.get(index).setBackground(new Color(100, 100, 100));
        }
    }
    
    /**
     * Resets all buy buttons (called when shop refreshes).
     */
    public void resetBuyButtons() {
        for (JButton button : buyButtons) {
            button.setEnabled(true);
            button.setText("BUY");
            button.setBackground(new Color(0, 150, 0));
        }
    }
}