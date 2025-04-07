package game.exceptions;

public class IllegalLengthScaleException extends IllegalArgumentException {
    public static int checkLengthScale(int lengthScale) {
        if (lengthScale < 1) {
            throw new IllegalLengthScaleException();
        }
        return lengthScale;
    }
    public IllegalLengthScaleException() {
        super("Length scale must be greater than 0!");
    }
}
