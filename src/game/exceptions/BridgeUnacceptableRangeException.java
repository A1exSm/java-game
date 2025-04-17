package game.exceptions;

import game.body.staticstructs.ground.magicCliffs.MagicBridge;
import game.body.staticstructs.ground.GroundFrame;
import game.core.console.Console;

public class BridgeUnacceptableRangeException extends InvalidBridgeException {

    public BridgeUnacceptableRangeException(String message) {
        super(Console.exceptionMessage(message));
    }

    public static void CheckBridgeDistance(GroundFrame startGround, GroundFrame endGround) throws BridgeUnacceptableRangeException {
        float dist = Math.abs(startGround.getOriginPos().x - endGround.getOriginPos().x);
        if (dist > MagicBridge.MAX_DISTANCE) {
            throw new BridgeUnacceptableRangeException("Bridge exception BRIDGE_DISTANCE: Bridge distance exceeds maximum distance; " + dist + " > " + MagicBridge.MAX_DISTANCE);
        } else if (dist < MagicBridge.MIN_DISTANCE) {
            throw new BridgeUnacceptableRangeException("Bridge exception BRIDGE_DISTANCE: Bridge distance is less than minimum distance; " + dist + " < " + MagicBridge.MIN_DISTANCE);
        }
    }
}
