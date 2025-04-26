package game.body.staticstructs;
// Imports
import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import city.cs.engine.StaticBody;
import game.core.GameWorld;
import org.jbox2d.common.Vec2;
// Class
/**
 * Stairs class.
 * This class creates a stair object in the game world.
 * It consists of a series of steps, each represented by a box shape.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 30-01-2025
 * @deprecated replaced by a series of slopes
 * which extend {@link game.body.staticstructs.ground.GroundFrame GroundFrame}
 * @see game.body.staticstructs.ground.GroundFrame GroundFrame
 */
@Deprecated
class Stairs extends StaticBody {
    // Constructor
    /**
     * Creates a stair object in the game world.
     * @param gameWorld The game world where the stairs are created.
     * @param halfStepDimensions The dimensions of each step in the stairs.
     * @param originPos The position of the stairs in the game world.
     * @param numSteps The number of steps in the stairs.
     * @param vertical The direction of the stairs (UP or DOWN).
     * @param horizontal The direction of the stairs (LEFT or RIGHT).
     */
    public Stairs(GameWorld gameWorld, Vec2 halfStepDimensions, Vec2 originPos, int numSteps, String vertical, String horizontal) {
        super(gameWorld, new BoxShape(halfStepDimensions.x, halfStepDimensions.y));
        this.setPosition(originPos);
        if (vertical.equals("DOWN")) halfStepDimensions.y = -halfStepDimensions.y;
        if (horizontal.equals("LEFT")) halfStepDimensions.x = -halfStepDimensions.x;
        for (int i = 1; i < numSteps; i++) {
            new SolidFixture(this, new BoxShape(halfStepDimensions.x, halfStepDimensions.y, new Vec2(((halfStepDimensions.x*2) * i), (halfStepDimensions.y*2) *i )));
        }
    }
}
