package game.levels;
// Imports
import game.Game;
import game.core.console.Console;
import game.enums.Environments;
import java.util.HashMap;
/**
 * This class Handles the saving, storing, deleting
 * and loading of level data.
 */
// Class
public class LevelData {
    // Fields
    /**
     * The default path to save level data.
     * This is used for the default save file.
     */
    public static final String PATH_1 = "data/Saves/save1.txt";
    /**
     * The path to save level data to.
     * This is used for the backup save file.
     */
    public static final String PATH_2 = "data/Saves/save2.txt";
    private final HashMap<Integer, Boolean> levelData = new HashMap<>();
    private final Environments environment;
    // Constructor
    /**
     * Constructor for LevelData.
     * Used to load level data from the default path.
     * @param environment The environment to load the level data for.
     */
    public LevelData(Environments environment) {
        this(environment, PATH_1);
    }
    /**
     * Constructor for LevelData.
     * Used to load level data from a given file path.
     * @param environment The environment to load the level data for.
     * @param path The path to load the level data from.
     */
    public LevelData(Environments environment, String path) {
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

    /**
     * Checks if the level is locked.
     * @param level The level to check.
     * @return {@code true} if the level is locked, {@code false} otherwise.
     */
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
        StatusSaver.saveGame(PATH_1);
    }
    /**
     * Unlocks the next level.
     * This will unlock the next level in the current environment.
     * If the next level is the last level, it will unlock the first level of the next environment.
     * Saves the game after unlocking the level.
     */
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
        StatusSaver.saveGame(PATH_1);
    }
    protected HashMap<Integer, Boolean> getLevelData() {
        return levelData;
    }
    /**
     * Gets the highest unlocked level.
     * @return The highest unlocked level.
     */
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
            } else if (nextEnvironment.levelData.get(1)) { // The first level of next environment is unlocked
                return 10000; // ensures that comparison is always true
            }
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
        StatusSaver.saveGame(PATH_2);
        Game.magicData.reset();
        Game.hauntedData.reset();
        Game.gothicData.reset();
        StatusSaver.saveGame(PATH_1);
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
        saveLevelDataWithPath(PATH_1);
    }
}
