package game.body.staticstructs.ground;
// Imports
import game.core.GameWorld;
import org.jbox2d.common.Vec2;
// Class
/**
 * A temporary ground class.
 * Used by the deprecated class
 * {@link game.body.staticstructs.Trampoline Trampoline} while
 * developing alternative game mechanics.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 25-03-2025
 * @deprecated replaced by more robust ground classes.
 */
@Deprecated
public class TempGround extends GroundFrame {
    // Constructor
    /**
     * Creates a temporary ground object in the game world.
     * @param gameWorld The game world where the ground is created.
     * @param halfDimensions The half-dimensions of the ground.
     * @param originPos The position of the ground in the game world.
     */
    public TempGround(GameWorld gameWorld, Vec2 halfDimensions, Vec2 originPos) {
        super(gameWorld, halfDimensions, originPos);
    }
    // Methods
    @Override
    public void paint() {}
}
