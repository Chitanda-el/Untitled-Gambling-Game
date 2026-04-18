// MainMenuGUI.java
package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Main Menu screen for Untitled Gambling Game.
 * 
 * This is the first screen players see. It contains:
 * - A large title banner
 * - START/CONTINUE button: Begins a new game or continues an existing save
 * - ABANDON SAVE FILE button: Resets all player progress
 * - INPUT CUSTOM SEED button: Allows players to set a deterministic RNG seed
 * - QUIT GAME button: Exits the application
 * 
 * All button actions call back to GameDirector methods, which handle the
 * actual game logic. The GUI only handles visual presentation and user input.
 */
public class MainMenuGUI extends JPanel {
    
    private MainWindow parent;
    
    private JLabel titleLabel;
    private JButton startButton;
    private JButton abandonButton;
    private JButton seedButton;
    private JButton quitButton;
    private JLabel backgroundImage;
    
    // Path to assets folder (project root, above src/)
    private static final String ASSETS_PATH = System.getProperty("user.dir") + "/assets/";
    
    public MainMenuGUI(MainWindow parent) {
        this.parent = parent;
        initComponents();
        layoutComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Load background image from assets/ui/main_bg.png or main_bg.jpg
        ImageIcon bgImage = loadImage(ASSETS_PATH + "ui/main_bg.jpg");
        
        if (bgImage != null) {
            // Scale background to fit 1280x720 window
            Image scaledBg = bgImage.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
            backgroundImage = new JLabel(new ImageIcon(scaledBg));
            backgroundImage.setLayout(new GridBagLayout());
            add(backgroundImage, BorderLayout.CENTER);
        } else {
            // Fallback background color if image not found
            setBackground(new Color(20, 20, 40));
        }
        
        // Create title label with styling
        titleLabel = new JLabel("UNTITLED GAMBLING GAME");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Create styled buttons with different colors for visual distinction
        startButton = createStyledButton("START / CONTINUE", new Color(0, 150, 0));
        abandonButton = createStyledButton("ABANDON SAVE FILE", new Color(150, 0, 0));
        seedButton = createStyledButton("INPUT CUSTOM SEED", new Color(0, 100, 150));
        quitButton = createStyledButton("QUIT GAME", new Color(100, 100, 100));
        
        // --- BUTTON ACTION LISTENERS ---
        // Each button calls the appropriate GameDirector method, then navigates
        // or updates the UI as needed.
        
        // START BUTTON: Tells GameDirector to initialize/continue the game,
        // then switches to the slot machine screen.
        startButton.addActionListener(e -> {
            if (parent.getGameDirector() != null) {
                parent.getGameDirector().onStartGame();
            }
            parent.switchTo(MainWindow.Screen.SLOT_MACHINE);
        });
        
        // ABANDON BUTTON: Shows confirmation dialog, then tells GameDirector
        // to reset all player data if user confirms.
        abandonButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure? This will reset all progress and cannot be undone!",
                "Abandon Save File", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION && parent.getGameDirector() != null) {
                parent.getGameDirector().onAbandonSave();
                JOptionPane.showMessageDialog(this, "Save file abandoned. Progress reset.");
            }
        });
        
        // SEED BUTTON: Opens input dialog for custom RNG seed.
        // The seed is passed to GameDirector which creates a new RandomNumGenerator.
        // This affects all future random outcomes (spins, shop items, etc.)
        seedButton.addActionListener(e -> {
            String currentSeed = parent.getGameDirector() != null ? 
                String.valueOf(parent.getGameDirector().getCurrentSeed()) : "default";
            String newSeed = JOptionPane.showInputDialog(this,
                "Enter a custom seed (number):", currentSeed);
            if (newSeed != null && !newSeed.trim().isEmpty() && parent.getGameDirector() != null) {
                parent.getGameDirector().setCustomSeed(newSeed.trim());
                JOptionPane.showMessageDialog(this, "Seed set to: " + newSeed.trim());
            }
        });
        
        // QUIT BUTTON: Exits the application entirely.
        quitButton.addActionListener(e -> System.exit(0));
    }
    
    /**
     * Creates a consistently styled button with custom colors.
     * 
     * @param text the button label
     * @param bgColor the background color of the button
     * @return a configured JButton
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);  // Removes focus outline for cleaner look
        button.setPreferredSize(new Dimension(300, 60));
        return button;
    }
    
    /**
     * Loads an image from the given file path.
     * Returns null if the file doesn't exist or cannot be loaded.
     * 
     * @param path full file system path to the image
     * @return ImageIcon or null if load fails
     */
    private ImageIcon loadImage(String path) {
        try {
            File imgFile = new File(path);
            if (imgFile.exists()) {
                return new ImageIcon(imgFile.getAbsolutePath());
            }
        } catch (Exception e) {
            // Silently fail - use default styling
        }
        return null;
    }
    
    /**
     * Arranges the UI components on screen.
     * Buttons are centered in a transparent panel that sits over the background.
     */
    private void layoutComponents() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);  // Transparent to show background image
        
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
        
        // If we have a background image, add button panel to it
        // Otherwise, add directly to this panel
        if (backgroundImage != null) {
            backgroundImage.add(buttonPanel, gbc);
        } else {
            setLayout(new GridBagLayout());
            add(buttonPanel, gbc);
        }
    }
    
    public void refreshDisplay() {
        // Main menu typically doesn't need dynamic refreshing
        // But this method exists for interface consistency
    }
}