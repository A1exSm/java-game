package game.core.console;
// Imports


import game.enums.LogTypes;

/**
 *
 */
// Class
public class Console {
    // Fields
    private static final ConsoleInterface CONSOLE_INTERFACE = new ConsoleInterface();
    // Methods
    /**
     * Toggles the visibility of the console.
     */
    public static void toggleConsole() {
        CONSOLE_INTERFACE.toggleVisibility();
    }
    /**
     * Prints text to the console and appends it to the console interface in the Error style.
     * @param text The text to append.
     */
    public static void error(String text) {
        System.err.println(text);
        CONSOLE_INTERFACE.appendText(text, LogTypes.ERROR);
    }
    /**
     * Prints text to the console and appends it to the console interface in the Info style.
     * @param text The text to append.
     */
    public static void info(String text) {
        System.out.println(text);
        CONSOLE_INTERFACE.appendText(text, LogTypes.INFO);
    }
    /**
     * Prints text to the console and appends it to the console interface in the Warning style.
     * @param text The text to append.
     */
    public static void warning(String text) {
        System.err.println(text);
        CONSOLE_INTERFACE.appendText(text, LogTypes.WARNING);
    }
    /**
     * Prints text to the console and appends it to the console interface in the Debug style.
     * Used to print messages for development/debugging purposes.
     * @param text The text to append.
     */
    public static void debug(String text) {
        System.out.println(text);
        CONSOLE_INTERFACE.appendText(text, LogTypes.DEBUG);
    }
    /**
     * Prints text to the console and appends it to the console interface in the Log style.
     * @param text The text to append.
     */
    public static void log(String text) {
        System.out.println(text);
        CONSOLE_INTERFACE.appendText(text, LogTypes.LOG);
    }
    /**
     * To be used when printing an error message, returns the message for the ExceptionHandler to use.
     * @param text The text to append.
     * @return The text appended.
     */
    public static String exceptionMessage(String text) {
        CONSOLE_INTERFACE.appendText(text, LogTypes.ERROR);
        return text;
    }
}
