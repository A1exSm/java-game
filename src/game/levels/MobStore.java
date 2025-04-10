package game.levels;
// Imports

import game.core.console.Console;

/**
 *
 */
// Class
public class MobStore {
    // Fields
    private final int[] levelData;

    // Constructor
    public MobStore(int numOfLevels, int[] mobData) {
        levelData = new int[numOfLevels];
        if (mobData.length != numOfLevels) {
            Console.errorTrace("Mob data length does not match number of levels.");
            if (mobData.length > numOfLevels) {
                Console.errorTraceCustom("Mob data length ("+ mobData.length +") is greater than number of levels (" + numOfLevels +").", 3);
            } else {
                Console.errorTraceCustom("Mob data length ("+ mobData.length +") is less than number of levels ("+ numOfLevels +").", 3);
            }
        }
        System.arraycopy(mobData, 0, levelData, 0, numOfLevels); // regardless, we copy in the data which is present :).

    }
    // Methods | Public | Getters
    public int getMobData(int level) {
        if (level < 0 || level >= levelData.length) {
            Console.errorTraceCustom("Level " + level + " does not exist.", 3);
            return -1;
        }
        return levelData[level];
    }

    protected int getNumOfLevels() {
        return levelData.length;
    }
}
