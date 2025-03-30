package game.exceptions;

public class BridgeNullPointException extends InvalidBridgeException {
    // fields
    public BridgeNullPointException(String message) {
        super(message);
    }
    // Static Methods
    public static void CheckBridgeNullPoint(Object bridgeStart, Object bridgeEnd) throws BridgeNullPointException {
        if (bridgeStart == null && bridgeEnd == null) {
            throw new BridgeNullPointException("Bridge exception BRIDGE_STOP_START: Bridge start and end are null");
        } else if (bridgeStart == null) {
            throw new BridgeNullPointException("Bridge exception BRIDGE_STOP_START: Bridge start is null");
        } else if (bridgeEnd == null) {
            throw new BridgeNullPointException("Bridge exception BRIDGE_STOP_START: Bridge end is null");
        }
    }
}
