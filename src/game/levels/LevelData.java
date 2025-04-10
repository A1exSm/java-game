package game.levels;
// Imports

import game.core.console.Console;
import game.enums.Environments;

import java.util.HashMap;

/**
 *
 */
// Class
public class LevelData{
    // Fields
    private final HashMap<Integer, Boolean> levelData = new HashMap<>();

    // Constructor
    public LevelData(Environments environment) { // we are going to place a load save from file place in the future options panel, which opens a JFileChooser etc...
        if (StatusGetter.isFilePresent(StatusSaver.PATH_1)) {
            levelData.putAll(StatusGetter.getLevelData(environment));
            if (levelData.isEmpty()) {
                Console.warning("No level data found for " + environment.name() + ", setting all levels to locked.");
                freshMap(environment);
            }
        } else {
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
                if (!levelData.get(i + 1)) {
                    return i + 1;
                }
            }
        }

        return -1;
    }

    protected void unlockLevel() {
        if (getNextLevel() == -1) {
            Console.warning("No more levels to unlock.");
            return;
        }
        levelData.put(getNextLevel(), true);
        StatusSaver.saveGame(StatusSaver.PATH_1);
    }

    protected HashMap<Integer, Boolean> getLevelData() {
        return levelData;
    }
}
