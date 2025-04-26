package game.exceptions;
import city.cs.engine.StaticBody;
import game.body.staticstructs.ground.GroundFrame;
import game.core.console.Console;
import game.enums.Direction;
import org.jbox2d.common.Vec2;
/**
 * The BridgeCollisionException class is used to handle exceptions related to bridge collisions.
 * It extends the InvalidBridgeException class and provides a method to check for collisions between bridges and other objects.
 */
public class BridgeCollisionException extends InvalidBridgeException {

    /**
     * An BridgeCollisionException with a message.
     * @param message The message to be displayed when the exception is thrown.
     */
    public BridgeCollisionException(String message) {
        super(Console.exceptionMessage(message));
    }
    /**
     * Checks for collisions between the bridge and other instances of {@link GroundFrame}.
     * @param halfDimensions The half-dimensions of the bridge.
     * @param startGround The starting ground frame of the bridge.
     * @param endGround The ending ground frame of the bridge.
     * @throws BridgeCollisionException if a collision is detected.
     * @see game.body.staticstructs.ground.GroundFrame
     */
    public static void CheckBridgeCollision(Vec2 halfDimensions, GroundFrame startGround, GroundFrame endGround) {
        Direction direction = startGround.getOriginPos().x < endGround.getOriginPos().x ? Direction.RIGHT : Direction.LEFT;
        float left = direction.equals(Direction.RIGHT) ? startGround.getOriginPos().x + startGround.getHalfDimensions().x : endGround.getOriginPos().x + endGround.getHalfDimensions().x;
        float right = direction.equals(Direction.RIGHT) ? endGround.getOriginPos().x - endGround.getHalfDimensions().x : startGround.getOriginPos().x - startGround.getHalfDimensions().x;
        float originY = (startGround.getOriginPos().y + startGround.getHalfDimensions().y) - 1.5f;
        // Checking for intersections with other ground frames
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
