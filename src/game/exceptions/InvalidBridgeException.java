package game.exceptions;
import game.core.console.Console;
/**
 * The parent class for all bridge-related exceptions.
 * This class is used to handle exceptions related to bridges in the game.
 */
public class InvalidBridgeException extends IllegalArgumentException {
    /**
     * An InvalidBridgeException with a message.
     * @param message The message to be displayed when the exception is thrown.
     */
    public InvalidBridgeException(String message) {super(Console.exceptionMessage(message));}
}
