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
    public LevelData(Environments environment) {
        if (environment == Environments.MAGIC_CLIFF) {
            levelData.put(1, true);
            levelData.put(2, false);
        } else if (environment == Environments.HAUNTED_FOREST || environment == Environments.GOTHIC_CEMETERY) {
            levelData.put(1, false);
            levelData.put(2, false);
        } else {
            Console.error("Unsupported environment " + environment + ", setting all levels to locked.");
            levelData.put(1, false);
            levelData.put(2, false);
        }
    }
    // Methods
    public boolean isLocked(int level) {
        if (levelData.containsKey(level)) {
            return levelData.get(level);
        } else {
            Console.error("Level " + level + " does not exist.");
            return false;
        }
    }

}
