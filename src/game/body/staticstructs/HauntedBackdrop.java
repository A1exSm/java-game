package game.body.staticstructs;
// Imports

import city.cs.engine.*;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import org.jbox2d.common.Vec2;
/**
 *
 */
// Class
public class HauntedBackdrop extends GroundFrame {
    // Fields
    public static final float HEIGHT = 16.05f; // Height of the image (16.05f @ 16f scale)
    public static final float WIDTH = 15.45f; // Width of the image (15.45f @ 16f scale)

    // Constructor
    public HauntedBackdrop(GameWorld gameWorld, float x, float y) {
        super(gameWorld);
        setPosition(new Vec2(x, y));
        new GhostlyFixture(this, new BoxShape(0.1f, 0.1f));
        // Height of the image (0.9f @ 16f scale)
        float PLATFORM_HEIGHT = 0.9f;
        // Width of the image (7.125f @ 16f scale)
        float PLATFORM_Y = 7.125f;
        new SolidFixture(this, new BoxShape(WIDTH/2, PLATFORM_HEIGHT, new Vec2(0, PLATFORM_Y)));
        new SolidFixture(this, new BoxShape(WIDTH/2, PLATFORM_HEIGHT, new Vec2(0, -PLATFORM_Y)));
        paint();
    }
    // Methods
    @Override
    public void paint() {
        removeAllImages();
        new AttachedImage(this, new BodyImage("data/HauntedForest/tiles/back_drop_platform.png"), 16f, 0, new Vec2(0, 0));
    }
}
