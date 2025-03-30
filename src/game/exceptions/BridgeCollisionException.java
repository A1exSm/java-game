package game.exceptions;

import city.cs.engine.StaticBody;
import game.body.staticstructs.ground.GroundFrame;
import game.enums.Direction;
import org.jbox2d.common.Vec2;

public class BridgeCollisionException extends InvalidBridgeException {

    public BridgeCollisionException(String message) {
        super(message);
    }

    public static void CheckBridgeCollision(Vec2 halfDimensions, GroundFrame startGround, GroundFrame endGround) throws BridgeCollisionException {
        Direction direction = startGround.getOriginPos().x < endGround.getOriginPos().x ? Direction.RIGHT : Direction.LEFT;
        float left = direction.equals(Direction.RIGHT) ? startGround.getOriginPos().x + startGround.getHalfDimensions().x : endGround.getOriginPos().x + endGround.getHalfDimensions().x;
        float right = direction.equals(Direction.RIGHT) ? endGround.getOriginPos().x - endGround.getHalfDimensions().x : startGround.getOriginPos().x - startGround.getHalfDimensions().x;
        float originY = (startGround.getOriginPos().y + startGround.getHalfDimensions().y) - 1.5f;

        for (StaticBody body : startGround.getWorld().getStaticBodies()) {
            if (body instanceof GroundFrame ground) {
                if (ground == startGround || ground == endGround) {
                    continue;
                }
                if (ground.getPosition().x > left && ground.getPosition().x < right) {
                    if (ground.getPosition().y + ground.getHalfDimensions().y > originY - halfDimensions.y && ground.getPosition().y - ground.getHalfDimensions().y < originY + halfDimensions.y) {
                        throw new BridgeCollisionException("Bridge collision exception: "+ ground.getName() + " is in the way of the bridge.");
                    }
                }
            }
        }
    }
}
