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
     *
     * @param text The text to append.
     */
    public static void error(String text) {
        System.err.println(text);
        CONSOLE_INTERFACE.appendText(text, LogTypes.ERROR);
    }
    /**
     * Prints text to the console and appends it to the console interface in the Error style.
     * This method is used to print error messages with information about the caller,
     * how far up the call is proportional to the stack trace index.
     * @param text The text to append.
     * @param stackTraceIndex The index of the stack trace to use.
     * @Note: The stack trace Index must be greater than or equal to 2.
     */
    public static void errorTraceCustom(String text, int stackTraceIndex) {
        if (stackTraceIndex < 2) {
            error(text);
            return;
        }
        StackTraceElement caller = Thread.currentThread().getStackTrace()[stackTraceIndex];
        text ="["+caller.getFileName()+":"+caller.getLineNumber()+"] "+text;
        error(text);
    }
    /**
     * Prints text to the console and appends it to the console interface in the Error style.
     * This method is used to print error messages with information about the immediate caller.
     * @param text The text to append.
     */
    public static void errorTrace(String text) {
        errorTraceCustom(text, 3); // 3 since 2 is the immediate caller, but since it traverses through errorTrace to errorTraceCustom, we need to add 1 to the index.
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
        Console.errorTraceCustom(text, 3);
        return text;
    }
}
