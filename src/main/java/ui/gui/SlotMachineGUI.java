// SlotMachineGUI.java
package ui.gui;

import domain.SlotMachine.Symbols;
import domain.items.Item;
import domain.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Slot Machine screen for Untitled Gambling Game.
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
    
    private Map<Symbols, ImageIcon> symbolImages;
    private ImageIcon defaultSymbolIcon;
    
    private int currentBet;
    private static final int DEFAULT_BET = 10;
    private static final int MIN_BET = 1;
    
    private static final String ASSETS_PATH = System.getProperty("user.dir") + "/assets/";
    private static final int SYMBOL_IMAGE_SIZE = 100;
    
    private static final int PULLOUT_COLUMNS = 2;
    private static final int PULLOUT_ROWS = 4;
    private static final int ITEM_IMAGE_SIZE = 100;
    private static final int PULLOUT_CELL_SIZE = 110;
    private static final int PULLOUT_WIDTH = PULLOUT_COLUMNS * PULLOUT_CELL_SIZE + 20;
    private static final int PULLOUT_HEIGHT = PULLOUT_ROWS * PULLOUT_CELL_SIZE + 20;
    
    public SlotMachineGUI(MainWindow parent) {
        this.parent = parent;
        this.symbolImages = new HashMap<>();
        this.currentBet = DEFAULT_BET;
        loadBackgroundImage();
        loadSymbolImages();
        initComponents();
        layoutComponents();
        setupPulloutTab();
    }
    
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
        
        moneyLabel = new JLabel("Money: $0");
        moneyLabel.setFont(new Font("Arial", Font.BOLD, 24));
        moneyLabel.setForeground(Color.GREEN);
        
        debtLabel = new JLabel("Debt Owed: $0");
        debtLabel.setFont(new Font("Arial", Font.BOLD, 24));
        debtLabel.setForeground(Color.RED);
        
        betLabel = new JLabel("Bet: $" + currentBet, SwingConstants.CENTER);
        betLabel.setFont(new Font("Arial", Font.BOLD, 20));
        betLabel.setForeground(Color.CYAN);
        betLabel.setOpaque(true);
        betLabel.setBackground(Color.BLACK);
        betLabel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        betLabel.setPreferredSize(new Dimension(120, 40));
        
        pullTabButton = new JButton("Items");
        pullTabButton.setFont(new Font("Arial", Font.BOLD, 16));
        pullTabButton.setBackground(new Color(100, 100, 150));
        pullTabButton.setPreferredSize(new Dimension(100, 45));
        pullTabButton.addActionListener(e -> togglePullout());
        
        openShopButton = new JButton("OPEN SHOP");
        openShopButton.setFont(new Font("Arial", Font.BOLD, 18));
        openShopButton.setBackground(new Color(0, 150, 200));
        openShopButton.setPreferredSize(new Dimension(180, 50));
        openShopButton.addActionListener(e -> parent.switchTo(MainWindow.Screen.ITEM_SHOP));
        
        betUpButton = new JButton("+5%");
        betUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        betUpButton.setBackground(new Color(0, 100, 0));
        betUpButton.setForeground(Color.WHITE);
        betUpButton.addActionListener(e -> {
            if (parent.getGameDirector() != null && parent.getGameDirector().getSlotMachine() != null) {
                int playerMoney = parent.getGameDirector().getPlayerMoney();
                int newBet = parent.getGameDirector().getSlotMachine().increaseBetByPercent(playerMoney);
                currentBet = newBet;
                updateBetDisplay();
            }
        });
        
        betDownButton = new JButton("-5%");
        betDownButton.setFont(new Font("Arial", Font.BOLD, 14));
        betDownButton.setBackground(new Color(100, 0, 0));
        betDownButton.setForeground(Color.WHITE);
        betDownButton.addActionListener(e -> {
            if (parent.getGameDirector() != null && parent.getGameDirector().getSlotMachine() != null) {
                int playerMoney = parent.getGameDirector().getPlayerMoney();
                int newBet = parent.getGameDirector().getSlotMachine().decreaseBetByPercent(playerMoney);
                currentBet = newBet;
                updateBetDisplay();
            }
        });
        
        allInButton = new JButton("ALL IN");
        allInButton.setFont(new Font("Arial", Font.BOLD, 14));
        allInButton.setBackground(new Color(150, 150, 0));
        allInButton.setForeground(Color.WHITE);
        allInButton.addActionListener(e -> {
            if (parent.getGameDirector() != null && parent.getGameDirector().getSlotMachine() != null) {
                int playerMoney = parent.getGameDirector().getPlayerMoney();
                int newBet = parent.getGameDirector().getSlotMachine().setAllInBet(playerMoney);
                currentBet = newBet;
                updateBetDisplay();
            }
        });
        
        inputBetButton = new JButton("Set");
        inputBetButton.setFont(new Font("Arial", Font.BOLD, 14));
        inputBetButton.setBackground(new Color(0, 100, 150));
        inputBetButton.setForeground(Color.WHITE);
        inputBetButton.addActionListener(e -> promptForBetAmount());
        
        spinButton = new JButton("SPIN");
        spinButton.setFont(new Font("Arial", Font.BOLD, 20));
        spinButton.setBackground(new Color(200, 150, 0));
        spinButton.setForeground(Color.WHITE);
        spinButton.addActionListener(e -> {
            if (parent.getGameDirector() != null) {
                parent.getGameDirector().onSpin(currentBet);
            }
        });
        
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
        int centerX = (1280 - 400) / 2;
        
        slotGridPanel.setBounds(centerX, 100, 400, 400);
        moneyLabel.setBounds(1280 - 220, 20, 200, 40);
        debtLabel.setBounds(1280 - 220, 70, 200, 40);
        betLabel.setBounds(centerX + 140, 520, 120, 40);
        betDownButton.setBounds(centerX + 40, 520, 80, 40);
        betUpButton.setBounds(centerX + 290, 520, 80, 40);
        allInButton.setBounds(centerX + 40, 575, 80, 40);
        inputBetButton.setBounds(centerX + 290, 575, 80, 40);
        spinButton.setBounds(centerX + 150, 570, 100, 50);
        openShopButton.setBounds(1280 - 200, 620, 180, 50);
        pullTabButton.setBounds(10, 40, 100, 45);
    }
    
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
                if (parent.getGameDirector() != null && parent.getGameDirector().getSlotMachine() != null) {
                    parent.getGameDirector().getSlotMachine().setCurrentBet(currentBet);
                }
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
    
    private void refreshPulloutDisplay() {
        pulloutPanel.removeAll();
        
        // Get inventory from Player via GameDirector
        java.util.List<Item> playerInventory = null;
        if (parent.getGameDirector() != null && parent.getGameDirector().getPlayer() != null) {
            playerInventory = parent.getGameDirector().getPlayer().getInventory();
        }
        
        int maxItems = PULLOUT_COLUMNS * PULLOUT_ROWS;
        
        if (playerInventory != null) {
            for (int i = 0; i < maxItems; i++) {
                if (i < playerInventory.size()) {
                    Item item = playerInventory.get(i);
                    JPanel itemPanel = createItemPanel(item);
                    pulloutPanel.add(itemPanel);
                } else {
                    JPanel emptyPanel = createEmptyPanel();
                    pulloutPanel.add(emptyPanel);
                }
            }
        } else {
            for (int i = 0; i < maxItems; i++) {
                JPanel emptyPanel = createEmptyPanel();
                pulloutPanel.add(emptyPanel);
            }
        }
        
        pulloutPanel.revalidate();
        pulloutPanel.repaint();
    }
    
    private JPanel createItemPanel(Item item) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setBackground(new Color(80, 80, 100));
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        String fileName = getItemAssetFileName(item);
        ImageIcon itemIcon = loadAndScaleImage(ASSETS_PATH + "items/" + fileName + ".png", ITEM_IMAGE_SIZE, ITEM_IMAGE_SIZE);
        if (itemIcon == null || itemIcon.getIconWidth() <= 0) {
            itemIcon = loadAndScaleImage(ASSETS_PATH + "items/default_item.png", ITEM_IMAGE_SIZE, ITEM_IMAGE_SIZE);
        }
        
        JLabel imageLabel = new JLabel(itemIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setToolTipText(item.getName());
        itemPanel.add(imageLabel, BorderLayout.CENTER);
        
        JLabel nameLabel = new JLabel(item.getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        nameLabel.setForeground(Color.WHITE);
        itemPanel.add(nameLabel, BorderLayout.SOUTH);
        
        return itemPanel;
    }
    
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
        } else if (name.contains("ascend") || name.contains("ladder")) {
            return "ascend_ladder";
        } else if (name.contains("hell") || name.contains("stair")) {
            return "hell_stair";
        } else {
            return "default_item";
        }
    }
    
    private JPanel createEmptyPanel() {
        JPanel emptyPanel = new JPanel(new BorderLayout());
        emptyPanel.setBackground(new Color(50, 50, 70));
        emptyPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        JLabel emptyLabel = new JLabel("Empty", SwingConstants.CENTER);
        emptyLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        emptyLabel.setForeground(Color.LIGHT_GRAY);
        emptyPanel.add(emptyLabel, BorderLayout.CENTER);
        
        return emptyPanel;
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
            
            refreshPulloutDisplay();
            
            int playerMoney = parent.getGameDirector().getPlayerMoney();
            if (currentBet > playerMoney && playerMoney > 0) {
                currentBet = playerMoney;
                if (parent.getGameDirector().getSlotMachine() != null) {
                    parent.getGameDirector().getSlotMachine().setCurrentBet(currentBet);
                }
                updateBetDisplay();
            } else if (playerMoney <= 0 && currentBet > MIN_BET) {
                currentBet = MIN_BET;
                if (parent.getGameDirector().getSlotMachine() != null) {
                    parent.getGameDirector().getSlotMachine().setCurrentBet(currentBet);
                }
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