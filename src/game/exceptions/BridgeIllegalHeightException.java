package game.exceptions;
import game.body.staticstructs.ground.GroundFrame;
import game.core.console.Console;
/**
 * It checks if the height of the bridge is valid and throws an exception if it is not.
 */
public class BridgeIllegalHeightException extends InvalidBridgeException {
    /**
     * An InvalidBridgeException with a message.
     * @param message The message to be displayed when the exception is thrown.
     */
    public BridgeIllegalHeightException(String message) {
        super(Console.exceptionMessage(message));
    }
    /**
     * Checks if the height of the bridge is valid.
     * @param bridgeStart The starting ground frame of the bridge.
     * @param bridgeEnd The ending ground frame of the bridge.
     * @throws BridgeIllegalHeightException if the height of the bridge is not valid.
     */
    public static void CheckBridgeHeightMismatch(GroundFrame bridgeStart, GroundFrame bridgeEnd) {
        if (bridgeStart.getYTop() != bridgeEnd.getYTop()) {
            throw new BridgeIllegalHeightException("Bridge exception BRIDGE_HEIGHT_MISMATCH: Bridge yTop values do not match; (" + bridgeStart.getName() + ") " + bridgeStart.getYTop() + " != (" + bridgeEnd.getName() + ") " + bridgeEnd.getYTop());
        }
    }
}
