package game.enums;
// Imports
import game.Game;
import game.core.console.Console;
import game.levels.LevelData;
/***
 * Enum representing the different environments in the game.
 * Each environment has an index and a title.
 * The index is used to identify the environment, while the title is used for display.
 * The enum also provides methods to check if a level is unlocked and to get the next environment.
 * @see game.levels.LevelFrame
 */
public enum Environments {
    /**
     * Represents the menu: {@link game.menu.MainMenu}.
     */
    Main_Menu(-2, "Main Menu"),
    /**
     * Represents the menu: {@link game.menu.SelectLevel}.
     */
    LEVEL_SELECT(-0, "Level Select"),
    /**
     * Represents the menu: {@link game.menu.Options}.
     */
    OPTIONS(-1, "Options"),
    /**
     * Represents the level: {@link game.levels.MagicCliff}.
     */
    MAGIC_CLIFF(1, "Magic Cliffs"),
    /**
     * Represents the level: {@link game.levels.HauntedForest}.
     */
    HAUNTED_FOREST(2, "Haunted Forest"),
    /**
     * Represents the level: {@link game.levels.GothicCemetery}.
     */
    GOTHIC_CEMETERY(3, "Gothicvania Cemetery"),;
    // Fields
    /**
     * The index of the environment.
     */
    public final int index;
    /**
     * The title of the environment.
     */
    public final String title;
    // Constructor
    Environments(int index, String title) {
        this.index = index;
        this.title = title;
    }
    // Methods

    /**
     * Gets whether a level is unlocked.
     * @param level the level number to check (not enum index)
     * @return {@code true} if the level is unlocked, {@code false} otherwise.
     */
    public boolean getBool(int level) {
        switch (this) {
            case MAGIC_CLIFF -> {return Game.magicData.isLocked(level);}
            case HAUNTED_FOREST -> {return Game.hauntedData.isLocked(level);}
            case GOTHIC_CEMETERY -> {return Game.gothicData.isLocked(level);}
            default -> {
                Console.error("Level " + level + " does not exist, returning false.");
                return false;
            }
        }
    }
    /**
     * Gets the next environment in the level order sequence.
     * @return the next environment, or {@code null} if there is no next environment.
     */
    public LevelData getNextEnvironment() {
        switch (this) {
            case MAGIC_CLIFF -> {return Game.hauntedData;}
            case HAUNTED_FOREST -> {return Game.gothicData;}
            case GOTHIC_CEMETERY -> {return Game.magicData;}
            default -> {
                Console.error("No next environment found, returning null.");
                return null;
            }
        }
    }
}
