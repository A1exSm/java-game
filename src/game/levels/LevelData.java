package game.levels;
// Imports

import game.Game;
import game.core.console.Console;
import game.enums.Environments;
import java.util.HashMap;
/**
 *
 */
// Class
public class LevelData {
    // Fields
    private final HashMap<Integer, Boolean> levelData = new HashMap<>();
    private final Environments environment;


    // Constructor
    public LevelData(Environments environment) {
        this(environment, StatusSaver.PATH_1);
    }

    public LevelData(Environments environment, String path) { // we are going to place a load save from file place in the future options panel, which opens a JFileChooser etc...
        this.environment = environment;
        if (StatusGetter.isFilePresent(path)) {
            levelData.putAll(StatusGetter.getLevelData(environment, path, false));
            if (levelData.isEmpty()) {
                Console.warning("No level data found for " + environment.name() + ", setting all levels to locked.");
                freshMap(environment);
            }
        } else {
            Console.warning("No level data found for " + environment.name() + ", resetting to default.");
           freshMap(environment);
        }
    }
    // Methods
    private void freshMap(Environments environment) {
        if (environment == Environments.MAGIC_CLIFF) {
            levelData.put(1, true);
            initLockedMap(2, MagicCliff.NUM_MOBS.getNumOfLevels());
        } else if (environment == Environments.HAUNTED_FOREST) {
            initLockedMap(1, HauntedForest.NUM_MOBS.getNumOfLevels());
        } else if (environment == Environments.GOTHIC_CEMETERY) {
            initLockedMap(1, GothicCemetery.NUM_MOBS.getNumOfLevels());
        } else {
            Console.error("Unsupported environment " + environment + ", setting all levels to locked.");
        }
    }

    private void initLockedMap(int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            levelData.put(i, false);
        }
    }

    public boolean isLocked(int level) {
        if (levelData.containsKey(level)) {
            return levelData.get(level);
        } else {
            Console.error("Level " + level + " does not exist.");
            return false;
        }
    }

    private int getNextLevel() {
        for (int i = 1; i <= levelData.size(); i++) {
            if (levelData.get(i)) {
                if (i == levelData.size()) {
                    return -2;
                } else if (!levelData.get(i + 1)) {
                    return i + 1;
                }
            }
        }
        return -1;
    }

    private void unlockFirst(LevelData levelData) {
        if (levelData == null) {
            Console.error("No next environment found.");
            return;
        }
        levelData.levelData.put(1, true);
        StatusSaver.saveGame(StatusSaver.PATH_1);
    }

    protected void unlockLevel(int levelNumber) {
        if (levelNumber == 1 && levelData.get(levelNumber+1)) {
            return;
        }
        if (levelNumber < getHighestUnlocked()) {return;} // we ignore if this is not the current highest level
        if (getNextLevel() == -1) {
            Console.warning("No more levels to unlock.");
            return;
        } else if (getNextLevel() == -2) {
            unlockFirst(environment.getNextEnvironment());
            return;
        }
        levelData.put(getNextLevel(), true);
        StatusSaver.saveGame(StatusSaver.PATH_1);
    }
    protected HashMap<Integer, Boolean> getLevelData() {
        return levelData;
    }

    public int getHighestUnlocked() {
        int highest = 0;
        for (int i = 1; i <= levelData.size(); i++) {
            if (levelData.get(i)) {
                highest = i;
            }
        }
        if (highest == levelData.size()) {
            LevelData nextEnvironment = environment.getNextEnvironment();
            if (nextEnvironment == null) {
                Console.errorTrace("No environment found.");
                return -1;
            } else if (nextEnvironment == Game.magicData) {
                Console.info("Game completed, do something here...");
            } else if (nextEnvironment.levelData.get(1)) { // The first level of next environment is unlocked
                return 10000; // ensures that comparison is always true
            }
            Console.warning("No levels unlocked.");
            return -1;
        }
        return highest;
    }

    /**
     * CAUTION! this will reset environment level data.
     */
    private void reset() {
        levelData.clear();
        freshMap(environment);
    }


    // static methods

    /**
     * Resets all level data to default values.
     * This stores the previous data in save2 and resets the data in save1.
     * Save2 will be overridden the next time resetLevelData is called.
     */
    public static void resetLevelData() {
        StatusSaver.saveGame(StatusSaver.PATH_2);
        Game.magicData.reset();
        Game.hauntedData.reset();
        Game.gothicData.reset();
        StatusSaver.saveGame(StatusSaver.PATH_1);
    }

    /**
     * Saves the level data to the specified path.
     * @param path The path to save the level data to.
     */
    public static void saveLevelDataWithPath(String path) {
        if (Game.magicData == null || Game.hauntedData == null || Game.gothicData == null) {
            return;
        }
        StatusSaver.saveGame(path);
    }

    /**
     * Saves the level data to the default path.
     */
    public static void saveLevelData() {
        saveLevelDataWithPath(StatusSaver.PATH_1);
    }
}
