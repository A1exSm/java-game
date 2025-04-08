package game.exceptions;

import game.core.console.Console;

public class IllegalLengthScaleException extends IllegalArgumentException {
    public static int checkLengthScale(int lengthScale) {
        if (lengthScale < 1) {
            throw new IllegalLengthScaleException();
        }
        return lengthScale;
    }
    public IllegalLengthScaleException() {
        super(Console.exceptionMessage("Length scale must be greater than 0!"));
    }
}
