package game.body.staticstructs.ground;
// Imports

import game.core.GameWorld;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
public class Platform extends GroundFrame {
    // Fields

    // Constructor
    public Platform(GameWorld gameWorld, Vec2 originPos) {
        super(gameWorld, new Vec2(5, 0.5f), originPos);
        setName("Platform"+getCount());
    }
}
