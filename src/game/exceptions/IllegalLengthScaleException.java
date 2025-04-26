package game.exceptions;
import game.core.console.Console;
/**
 * Throws when the length scale is less than 1.
 */
public class IllegalLengthScaleException extends IllegalArgumentException {
    /**
     * Checks if the length scale is less than 1.
     * @param lengthScale The length scale to check.
     * @throws IllegalLengthScaleException if the length scale is less than 1.
     * @return The length scale if it is greater than or equal to 1.
     */
    public static int checkLengthScale(int lengthScale) {
        if (lengthScale < 1) {
            throw new IllegalLengthScaleException();
        }
        return lengthScale;
    }
    /**
     * A runtime exception with a defined message.
     */
    public IllegalLengthScaleException() {
        super(Console.exceptionMessage("Length scale must be greater than 0!"));
    }
}
