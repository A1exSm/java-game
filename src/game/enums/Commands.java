package game.enums;
/**
 * Enum representing various commands in the Console.
 * @see game.core.console.Console
 */
public enum Commands {
    /**
     * Command to display the help information.
     */
    HELP,
    /**
     * Command to toggle {@link game.Game#debugOn()}.
     */
    DEBUG,
    /**
     * Command to see whether the {@link game.core.GameWorld GameWorld's}
     * {@link game.levels.LevelFrame level} instance is set.
     */
    LEVEL_STATUS;
}
