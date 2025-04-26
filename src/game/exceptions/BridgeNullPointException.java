package game.exceptions;
import game.body.staticstructs.ground.GroundFrame;
import game.core.console.Console;
/**
 * Ensures neither of the bridge points are null or destroyed.
 */
public class BridgeNullPointException extends InvalidBridgeException {
    // fields
    /**
     * An InvalidBridgeException with a message.
     * @param message The message to be displayed when the exception is thrown.
     */
    public BridgeNullPointException(String message) {
        super(Console.exceptionMessage(message));
    }
    // Static Methods
    /**
     * Checks if either the bridge start and end points are null or destroyed.
     * @param bridgeStart The starting ground frame of the bridge.
     * @param bridgeEnd The ending ground frame of the bridge.
     * @throws BridgeNullPointException if the bridge-start or end points are null or destroyed.
     */
    public static void CheckBridgeNullPoint(GroundFrame bridgeStart, GroundFrame bridgeEnd) {
        // null checks
        if (bridgeStart == null && bridgeEnd == null) {
            throw new BridgeNullPointException("Bridge exception BRIDGE_STOP_START: Bridge start and end are null");
        } else if (bridgeStart == null) {
            throw new BridgeNullPointException("Bridge exception BRIDGE_STOP_START: Bridge start is null");
        } else if (bridgeEnd == null) {
            throw new BridgeNullPointException("Bridge exception BRIDGE_STOP_START: Bridge end is null");
        }
        // destroyed checks
        if (bridgeStart.isDestroyed() && bridgeEnd.isDestroyed()) {
            throw new BridgeNullPointException("Bridge exception BRIDGE_STOP_START: Bridge start and end are destroyed");
        } else if (bridgeStart.isDestroyed()) {
            throw new BridgeNullPointException("Bridge exception BRIDGE_STOP_START: Bridge start is destroyed");
        } else if (bridgeEnd.isDestroyed()) {
            throw new BridgeNullPointException("Bridge exception BRIDGE_STOP_START: Bridge end is destroyed");
        }
    }
}
