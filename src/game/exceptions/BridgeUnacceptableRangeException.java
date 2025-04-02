package game.exceptions;

import game.body.staticstructs.ground.magicCliffs.Bridge;
import game.body.staticstructs.ground.GroundFrame;

public class BridgeUnacceptableRangeException extends InvalidBridgeException {

    public BridgeUnacceptableRangeException(String message) {
        super(message);
    }

    public static void CheckBridgeDistance(GroundFrame startGround, GroundFrame endGround) throws BridgeUnacceptableRangeException {
        float dist = Math.abs(startGround.getOriginPos().x - endGround.getOriginPos().x);
        if (dist > Bridge.MAX_DISTANCE) {
            throw new BridgeUnacceptableRangeException("Bridge exception BRIDGE_DISTANCE: Bridge distance exceeds maximum distance; " + dist + " > " + Bridge.MAX_DISTANCE);
        } else if (dist < Bridge.MIN_DISTANCE) {
            throw new BridgeUnacceptableRangeException("Bridge exception BRIDGE_DISTANCE: Bridge distance is less than minimum distance; " + dist + " < " + Bridge.MIN_DISTANCE);
        }
    }
}
