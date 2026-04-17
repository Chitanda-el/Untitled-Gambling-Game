package util;

/**
 * Manages the creation and deletion of save data.
 * 
 */
import java.io.*;

public class SaveManager {
    private static final String SAVE_FILE = "savegame.dat";

    public void saveGame(SaveData data) {
        try (ObjectOutputStream out =
                 new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            out.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SaveData loadGame() {
        try (ObjectInputStream in =
                 new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            return (SaveData) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public boolean hasSave() {
        return new File(SAVE_FILE).exists();
    }

    public void deleteSave() {
        new File(SAVE_FILE).delete();
    }
}