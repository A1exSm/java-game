package game.levels;
// Imports

import game.Game;
import game.core.console.Console;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 */
// Class
class StatusSaver {
    // Fields
    public static final String PATH_1 = "data/Saves/save1";
    private static HashMap<Integer, Boolean> magicLevelData;
    private static HashMap<Integer, Boolean> hauntedLevelData;
    private static HashMap<Integer, Boolean> gothicLevelData;
    // Constructor
    // Methods
    public static void saveGame(String path){
        updateLevelData();
        writeData(path);

    }
    private static void writeData(String path) {
        Console.info("Starting save process...");
        try (FileWriter fw = new FileWriter(path, false)) {
            fw.write("Level Data\n"); // creating a new file or overwriting an existing file
        } catch (IOException e) {
            Console.errorTrace("Error writing save file: " + e.getMessage());
        }
        writeLevel(magicLevelData);
        writeLevel(hauntedLevelData);
        writeLevel(gothicLevelData);
        Console.info("Finished save process!");
    }

    private static void writeLevel(HashMap<Integer, Boolean> levelData) {
        try (FileWriter fw = new FileWriter(PATH_1, true)) { // Try-with-resource statement, handles resources that need to be closed. Used instead of finally{...};
            for (int i = 1; i <= levelData.size(); i++) {
                if (i == levelData.size()) {
                    fw.write(i + ":" + levelData.get(i) + "\n");
                } else {
                    fw.write(i + ":" + levelData.get(i) + ",");
                }
            }
        } catch (IOException e) {
            Console.errorTrace("Error writing "+ levelData.toString() +"to file: " + e.getMessage());
        }
    }

    private static void updateLevelData() {
        magicLevelData = Game.magicData.getLevelData();
        hauntedLevelData = Game.hauntedData.getLevelData();
        gothicLevelData = Game.gothicData.getLevelData();
    }
}
