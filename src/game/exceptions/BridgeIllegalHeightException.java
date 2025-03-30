package game.exceptions;

import game.body.staticstructs.ground.GroundFrame;

public class BridgeIllegalHeightException extends InvalidBridgeException {

    public BridgeIllegalHeightException(String message) {
        super(message);
    }

    public static void CheckBridgeHeightMismatch(GroundFrame bridgeStart, GroundFrame bridgeEnd) throws BridgeIllegalHeightException {
        if (bridgeStart.getYTop() != bridgeEnd.getYTop()) {
            throw new BridgeIllegalHeightException("Bridge exception BRIDGE_HEIGHT_MISMATCH: Bridge yTop values do not match; (" + bridgeStart.getName() + ") " + bridgeStart.getYTop() + " != (" + bridgeEnd.getName() + ") " + bridgeEnd.getYTop());
        }
    }
}
