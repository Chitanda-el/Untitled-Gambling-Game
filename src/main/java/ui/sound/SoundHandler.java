package ui.sound;

/**
 *
 * 
 */
public interface SoundHandler {
 
    /**
     * Played when the player initiates a spin.
     * Suggested character: mechanical reel-spinning sound.
     */
    void playSpinSound();
 
    /**
     * Played when a winning combination is detected after a spin.
     */
    void playWinSound();
 
    /**
     * Played when a spin resolves with no winning combination.
     */
    void playLoseSound();
 
    /**
     * Played when the player successfully purchases an item from the shop.
     */
    void playPurchaseSound();
 
    /**
     * Played when a debt is repaid.
     */
    void playDebtRepaidSound();
    
    /**
     * Played when a debt is reacquired.
     */
    void playDebtAcquiredSound();
}