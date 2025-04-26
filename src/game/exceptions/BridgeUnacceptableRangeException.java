package game.exceptions;
import game.body.staticstructs.ground.magicCliffs.MagicBridge;
import game.body.staticstructs.ground.GroundFrame;
import game.core.console.Console;
/**
 * This class is used to check if the distance between two points is within an acceptable range.
 * If the distance is not within the range, an exception is thrown.
 */
public class BridgeUnacceptableRangeException extends InvalidBridgeException {
    /**
     * An InvalidBridgeException with a message.
     * @param message The message to be displayed when the exception is thrown.
     */
    public BridgeUnacceptableRangeException(String message) {
        super(Console.exceptionMessage(message));
    }
    /**
     * Checks if the distance between two points is within an acceptable range.
     * Acceptable range is defined by constants {@link MagicBridge#MAX_DISTANCE} and {@link MagicBridge#MIN_DISTANCE}.
     * @param startGround The starting ground frame of the bridge.
     * @param endGround The ending ground frame of the bridge.
     * @throws BridgeUnacceptableRangeException if the distance is not within the acceptable range.
     */
    public static void CheckBridgeDistance(GroundFrame startGround, GroundFrame endGround) {
        float dist = Math.abs(startGround.getOriginPos().x - endGround.getOriginPos().x);
        if (dist > MagicBridge.MAX_DISTANCE) {
            throw new BridgeUnacceptableRangeException("Bridge exception BRIDGE_DISTANCE: Bridge distance exceeds maximum distance; " + dist + " > " + MagicBridge.MAX_DISTANCE);
        } else if (dist < MagicBridge.MIN_DISTANCE) {
            throw new BridgeUnacceptableRangeException("Bridge exception BRIDGE_DISTANCE: Bridge distance is less than minimum distance; " + dist + " < " + MagicBridge.MIN_DISTANCE);
        }
    }
}
