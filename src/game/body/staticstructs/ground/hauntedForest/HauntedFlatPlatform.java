package game.body.staticstructs.ground.hauntedForest;
// Imports

import city.cs.engine.*;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import game.enums.Direction;
import org.jbox2d.common.Vec2;

import java.awt.*;

/**
 * A platform which uses the HauntedForest tile set.
 */
// Class
public class HauntedFlatPlatform extends GroundFrame {
    // Fields
    /**
     * One unit in relation to the lengthScale.
     */
    public static final float WIDTH = 3.65f; // Width of the image (5.26f @ 11f scale, 3.7f @ 8f scale)
    // Constructor
    /**
     * Constructor for HauntedFlatPlatform.<br>
     * Creates a new instance of HauntedFlatPlatform with the specified parameters.
     *
     * @param gameWorld the game world
     * @param x the x-coordinate of the platform
     * @param y the y-coordinate of the platform
     * @param lengthScale the length scale of the platform (must be >= 1), the lengthScale determines how many times {@link #WIDTH} is multiplied to get half of the length of the platform.
     * @param direction the direction of the platform: {@link Direction#LEFT} or {@link Direction#RIGHT}.
     * @Note: if {@code direction} is null or {@code lengthScale} is less than 1, they throw {@link IllegalArgumentException} if null.
     */
    public HauntedFlatPlatform(GameWorld gameWorld, float x, float y, int lengthScale, Direction direction) {
        super(gameWorld);
        setPosition(new Vec2(x, y));
        if (lengthScale < 1) {
            throw new IllegalArgumentException("Invalid lengthScale: " + lengthScale + ". Req: lengthScale >=  1.");
        } else if (direction == null) {
            throw new IllegalArgumentException("Null direction!");
        } else {
            new SolidFixture(this, new BoxShape(WIDTH*lengthScale, 1, new Vec2(0, 0)));
            String path;
            for (int i = 0; i < lengthScale*2; i++) {
                path = ((int)(Math.random() * 101) % 12 != 0) ? "A" : "B";
                AttachedImage image = new AttachedImage(this, new BodyImage("data/HauntedForest/tiles/flat_"+path+".png"), 8f, 0, new Vec2((-WIDTH*lengthScale + WIDTH/2) + i * WIDTH, -2));
                if (direction != Direction.RIGHT) {
                    image.flipHorizontal();
                }
            }
        }
    }
    // Methods
}
