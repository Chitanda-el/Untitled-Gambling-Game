// MainMenuGUI.java
package ui.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Main Menu screen for Untitled Gambling Game.
 * 
 * Contains title and four buttons:
 * - Start/Continue
 * - Abandon Save File
 * - Input Custom Seed
 * - Quit Game
 */
public class MainMenuGUI extends JPanel {
    
    private MainWindow parent;
    
    private JLabel titleLabel;
    private JButton startButton;
    private JButton abandonButton;
    private JButton seedButton;
    private JButton quitButton;
    private JLabel backgroundImage;
    
    public MainMenuGUI(MainWindow parent) {
        this.parent = parent;
        initComponents();
        layoutComponents();
    }
    
    private void initComponents() {
        setLayout(new GridBagLayout());
        
        // Load background image if exists
        ImageIcon bgImage = loadImage("/assets/ui/menu_bg.png");
        if (bgImage != null) {
            backgroundImage = new JLabel(bgImage);
            backgroundImage.setLayout(new GridBagLayout());
            add(backgroundImage);
            backgroundImage.setBounds(0, 0, 1280, 720);
        }
        
        titleLabel = new JLabel("UNTITLED GAMBLING GAME");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        startButton = createStyledButton("START / CONTINUE", new Color(0, 150, 0));
        abandonButton = createStyledButton("ABANDON SAVE FILE", new Color(150, 0, 0));
        seedButton = createStyledButton("INPUT CUSTOM SEED", new Color(0, 100, 150));
        quitButton = createStyledButton("QUIT GAME", new Color(100, 100, 100));
        
        startButton.addActionListener(e -> {
            if (parent.getGameDirector() != null) {
                parent.getGameDirector().onStartGame();
            }
            parent.switchTo(MainWindow.Screen.SLOT_MACHINE);
        });
        
        abandonButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure? This will reset all progress and cannot be undone!",
                "Abandon Save File", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION && parent.getGameDirector() != null) {
                parent.getGameDirector().onAbandonSave();
                JOptionPane.showMessageDialog(this, "Save file abandoned. Progress reset.");
            }
        });
        
        seedButton.addActionListener(e -> {
            String newSeed = JOptionPane.showInputDialog(this,
                "Enter a custom seed (text or number):", 
                parent.getGameDirector() != null ? parent.getGameDirector().getCurrentSeed() : "default");
            if (newSeed != null && !newSeed.trim().isEmpty() && parent.getGameDirector() != null) {
                parent.getGameDirector().setCustomSeed(newSeed.trim());
                JOptionPane.showMessageDialog(this, "Seed set to: " + newSeed.trim());
            }
        });
        
        quitButton.addActionListener(e -> System.exit(0));
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(300, 60));
        return button;
    }
    
    private ImageIcon loadImage(String path) {
        try {
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) {
                return new ImageIcon(imgURL);
            }
            // Try absolute path from project root
            File imgFile = new File(System.getProperty("user.dir") + path);
            if (imgFile.exists()) {
                return new ImageIcon(imgFile.getAbsolutePath());
            }
        } catch (Exception e) {
            // Silently fail - use default styling
        }
        return null;
    }
    
    private void layoutComponents() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        buttonPanel.add(titleLabel, gbc);
        
        gbc.gridy = 1;
        buttonPanel.add(startButton, gbc);
        
        gbc.gridy = 2;
        buttonPanel.add(abandonButton, gbc);
        
        gbc.gridy = 3;
        buttonPanel.add(seedButton, gbc);
        
        gbc.gridy = 4;
        buttonPanel.add(quitButton, gbc);
        
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.CENTER);
    }
    
    public void refreshDisplay() {
        // Main menu typically doesn't need refreshing
    }
}