package game.body.staticstructs.ground.hauntedForest;
// Imports

import city.cs.engine.*;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import game.enums.Direction;
import org.jbox2d.common.Vec2;

import java.util.ArrayList;

/**
 *
 */
// Class
public class HauntedSlopePlatform extends GroundFrame {
    // Fields
    public static final float WIDTH = 3.28f; // Width of the image (3.28f @ 8f scale)
    // Constructor
    /**
     * Constructor for HauntedSlopePlatform.<br>
     * Creates a new instance of HauntedSlopePlatform with the specified parameters.
     *
     * @param gameWorld the game world
     * @param x the x-coordinate of the platform
     * @param y the y-coordinate of the platform
     * @param lengthScale the length scale of the platform (must be >= 1), the lengthScale determines how many times {@link #WIDTH} is multiplied to get half of the length of the platform.
     * @param horizontalDirection the horizontal direction of the platform: {@link Direction#LEFT} or {@link Direction#RIGHT}.
     * @param verticalDirection the vertical direction of the platform: {@link Direction#UP} or {@link Direction#DOWN}.
     *
     * @Note: {@code verticalDirection} and {@code horizontalDirection} throw {@link IllegalArgumentException} if null.
     */
    public HauntedSlopePlatform(GameWorld gameWorld, float x, float y, int lengthScale, Direction horizontalDirection, Direction verticalDirection) {
        super(gameWorld);
        setPosition(new Vec2(x, y));
        if (lengthScale < 1) {
            throw new IllegalArgumentException("Invalid lengthScale: " + lengthScale + ". Req: lengthScale >=  1.");
        } else if (horizontalDirection == null) {
            throw new IllegalArgumentException("Null horizontalDirection!");
        } else if (verticalDirection == null) {
            throw new IllegalArgumentException("Null verticalDirection!");
        }else {
            float[] vertices;
            switch (verticalDirection) {
                case UP -> {
                    if (horizontalDirection == Direction.RIGHT) {
                        vertices = new float[]{-(WIDTH*lengthScale), -0.1f, WIDTH*lengthScale,(lengthScale*2), WIDTH*lengthScale, -0.1f};
                    } else {
                        verticalDirection = Direction.DOWN;
                        horizontalDirection = Direction.RIGHT;
                        vertices = new float[]{-(WIDTH*lengthScale), -0.1f, -(WIDTH*lengthScale), -(lengthScale*2), WIDTH*lengthScale, -(lengthScale*2)};
                    }
                }
                case DOWN -> {
                    if (horizontalDirection == Direction.RIGHT) {
                        vertices = new float[]{-(WIDTH*lengthScale), -0.1f, -(WIDTH*lengthScale), -(lengthScale*2), WIDTH*lengthScale, -(lengthScale*2)};
                    } else {
                        verticalDirection = Direction.UP;
                        horizontalDirection = Direction.RIGHT;
                        vertices = new float[]{-(WIDTH*lengthScale), -0.1f, WIDTH*lengthScale,(lengthScale*2), WIDTH*lengthScale, -0.1f};
                    }
                }
                default -> {throw new IllegalArgumentException("Invalid verticalDirection: " + verticalDirection + ". Req: UP or DOWN.");}
            }
            halfDimensions.x = lengthScale * WIDTH;
            halfDimensions.y = lengthScale * 2;
            new SolidFixture(this, new PolygonShape(vertices[0], vertices[1], vertices[2], vertices[3], vertices[4], vertices[5]));
            String path;
            int incline;
            for (int i = 0; i < lengthScale * 2; i++) {
                path = ((int) (Math.random() * 101) % 6 != 0) ? "" : "_skull";
                incline = (verticalDirection == Direction.UP) ? i : -i;
                AttachedImage image = new AttachedImage(this, new BodyImage("data/HauntedForest/tiles/sloped_" + verticalDirection.name() + path + ".png"), 8f, 0, new Vec2((-WIDTH * lengthScale + WIDTH / 2) + i * WIDTH, -2 + incline));
                if (horizontalDirection != Direction.RIGHT) {
                    image.flipHorizontal();
                }
            }
        }
    }
    // Methods
}
