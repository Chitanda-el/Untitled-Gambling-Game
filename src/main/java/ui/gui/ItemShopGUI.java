// ItemShopGUI.java
package ui.gui;

import domain.Item;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Item Shop screen for Untitled Gambling Game.
 * 
 * Features:
 * - Grid display of purchasable items with icons and prices
 * - Click items to purchase
 * - Button to return to Slot Machine
 * - Displays player's current money
 */
public class ItemShopGUI extends JPanel {
    
    private MainWindow parent;
    
    private JPanel shopGridPanel;
    private JLabel moneyLabel;
    private JButton backButton;
    private JButton rerollButton;
    private JScrollPane scrollPane;
    
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
        initComponents();
        layoutComponents();
    }
    
    private void initComponents() {
        setBackground(new Color(40, 20, 60));
        setLayout(null);
        
        moneyLabel = new JLabel("Money: $0");
        moneyLabel.setFont(new Font("Arial", Font.BOLD, 28));
        moneyLabel.setForeground(Color.GREEN);
        
        backButton = new JButton("BACK TO SLOT MACHINE");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setBackground(new Color(150, 100, 0));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> parent.switchTo(MainWindow.Screen.SLOT_MACHINE));
        
        rerollButton = new JButton("REROLL SHOP ($50)");
        rerollButton.setFont(new Font("Arial", Font.BOLD, 16));
        rerollButton.setBackground(new Color(100, 100, 200));
        rerollButton.setForeground(Color.WHITE);
        rerollButton.addActionListener(e -> {
            if (parent.getGameDirector() != null) {
                parent.getGameDirector().onRerollShop();
                refreshDisplay();
                //updateShopStock(parent.getGameDirector().getShopItems()); REMOVED FOR TESTING REMOVED FOR TESTING REMOVED FOR TESTING
            }
        });
        
        shopGridPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        shopGridPanel.setBackground(new Color(40, 20, 60));
        shopGridPanel.setOpaque(true);
        
        scrollPane = new JScrollPane(shopGridPanel);
        scrollPane.setBackground(new Color(40, 20, 60));
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(40, 20, 60));
    }
    
    private void layoutComponents() {
        moneyLabel.setBounds(1280 - 250, 20, 230, 40);
        add(moneyLabel);
        
        backButton.setBounds(30, 620, 250, 50);
        add(backButton);
        
        rerollButton.setBounds(1280 - 200, 620, 170, 50);
        add(rerollButton);
        
        scrollPane.setBounds(100, 80, 1080, 500);
        add(scrollPane);
    }
    
    private ImageIcon loadItemImage(Item item, int width, int height) {
        // Try to load based on item name or ID
        String fileName = item.getName().toLowerCase().replace(" ", "_").replace("!", "").replace("x", "");
        String path = ASSETS_PATH + "items/" + fileName + ".png";
        
        try {
            File imgFile = new File(path);
            if (imgFile.exists()) {
                ImageIcon original = new ImageIcon(imgFile.getAbsolutePath());
                Image scaledImage = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            }
        } catch (Exception e) {
            // Try alternate path
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
     * @param items 
     */
    public void updateShopStock(List<Item> items) {
        currentStock.clear();
        buyButtons.clear();
        itemCards.clear();
        shopGridPanel.removeAll();
        
        if (items != null) {
            currentStock.addAll(items);
            
            for (int i = 0; i < currentStock.size(); i++) {
                Item item = currentStock.get(i);
                JPanel card = createItemCard(item, i);
                itemCards.add(card);
                shopGridPanel.add(card);
            }
        }
        
        shopGridPanel.revalidate();
        shopGridPanel.repaint();
    }
    
    private JPanel createItemCard(Item item, int index) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(80, 60, 100));
        card.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        card.setPreferredSize(new Dimension(200, 250));
        card.setMaximumSize(new Dimension(200, 250));
        
        // Item image
        ImageIcon itemIcon = loadItemImage(item, ITEM_IMAGE_SIZE, ITEM_IMAGE_SIZE);
        JLabel iconLabel = new JLabel(itemIcon, SwingConstants.CENTER);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Item name
        JLabel nameLabel = new JLabel(item.getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(Color.WHITE);
        
        // Item price
        JLabel priceLabel = new JLabel("$" + (int) item.getCost(), SwingConstants.CENTER);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        priceLabel.setForeground(Color.YELLOW);
        
        // Item description
        String desc = item.getDescription();
        if (desc.length() > 40) {
            desc = desc.substring(0, 37) + "...";
        }
        JLabel descLabel = new JLabel(desc, SwingConstants.CENTER);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        descLabel.setForeground(Color.LIGHT_GRAY);
        
        // Buy button
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
     * Updates the money display.
     */
    public void refreshDisplay() {
        if (parent.getGameDirector() != null) {
            moneyLabel.setText("Money: $" + parent.getGameDirector().getPlayerMoney());
        }
    }
    
    /**
     * Disables a specific buy button (called after purchase).
     * 
     * @param index 
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