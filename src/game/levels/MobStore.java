package game.levels;
// Imports
import game.core.console.Console;
/**
 * * The MobStore class is used to store the number of mobs
 */
// Class
public class MobStore {
    // Fields
    private final int[] levelData;
    // Constructor
    /**
     * Constructor for MobStore,
     * provides detailed error messages,
     * which are printed to the {@link Console}.
     * @param numOfLevels The number of levels to create the MobStore for.
     * @param mobData The number of mobs in each level.
     */
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
        System.arraycopy(mobData, 0, levelData, 0, numOfLevels); // regardless, we copy in the data which is present
    }
    // Methods | Public | Getters

    /**
     * Gets the number of mobs in a given level.
     * @param level The level to get the number of mobs for.
     * @return The number of mobs in the level.
     */
    public int getMobData(int level) {
        if (level < 0 || level >= levelData.length) {
            Console.errorTraceCustom("Level " + level + " does not exist.", 3);
            return -1;
        }
        return levelData[level];
    }
    /**
     * Gets the number of levels in the MobStore.
     * @return The number of levels in the MobStore.
     */
    protected int getNumOfLevels() {
        return levelData.length;
    }
}
