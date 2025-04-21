package game.enums;

import game.Game;
import game.core.console.Console;
import game.levels.LevelData;

public enum Environments {
    Main_Menu,
    Level_Select,
    MAGIC_CLIFF,
    HAUNTED_FOREST,
    GOTHIC_CEMETERY;
    static {
        MAGIC_CLIFF.index = 1;
        HAUNTED_FOREST.index = 2;
        GOTHIC_CEMETERY.index = 3;
    }
    // Fields
    public int index;
    // Methods

    public boolean getBool(int level) {
        return isLevelUnlocked(level);
    }

    private boolean isLevelUnlocked(int level) {
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
