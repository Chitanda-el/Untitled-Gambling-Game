package util;
import java.io.*;

/**
 * Manages the creation and deletion of save data to and from a file.
 */
public class SaveManager {
    private static final String SAVE_FILE = "savegame.dat";
    
    /**
     * Saves the data to a file.
     * @param data The SaveData object it saves.
     */
    public void saveGame(SaveData data) {
        try (ObjectOutputStream out =
                 new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            out.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the data from a file.
     * @return the SaveData object contained in the file.
     */
    public SaveData loadGame() {
        try (ObjectInputStream in =
                 new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            return (SaveData) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Returns whether or not a save file exists.
     * @return Whether or not the save file exists.
     */
    public boolean hasSave() {
        return new File(SAVE_FILE).exists();
    }

    /**
     * Deletes the save file.
     */
    public void deleteSave() {
        new File(SAVE_FILE).delete();
    }
}