package game.body.staticstructs.ground.hauntedForest;
// Imports

import city.cs.engine.*;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import game.enums.Direction;
import game.enums.PlatformType;
import org.jbox2d.common.Vec2;
import java.util.Objects;

/**
 *
 */
// Class
public class HauntedPlatform extends GroundFrame {
    // Fields
    private final Direction direction;
    // Constructor
    /**
     * Constructor for Ground.<br>
     * Creates a new instance of Ground with the specified parameters.
     * @Note: When adding a {@link PlatformType#FLAT} after a {@link PlatformType#SLOPED} or {@link PlatformType#SKULL_SLOPED} platform,
     * the {@link PlatformType#FLAT} must overlap the {@link PlatformType#SLOPED} or {@link PlatformType#SKULL_SLOPED} platform by 0.2f.
     *
     * @param gameWorld the game world
     * @param x the x-coordinate of the ground
     * @param y the y-coordinate of the ground
     * @param halfLength the half-length of the platform
     * @param type the type of platform: {@code "FLAT"} or {@code "SLOPED"}
     * @param direction the direction of the platform: {@link Direction#LEFT} or {@link Direction#RIGHT}, defaults to {@link Direction#RIGHT} if null.
     */
    public HauntedPlatform(GameWorld gameWorld, float x, float y, float halfLength, PlatformType type, Direction direction) {
        super(gameWorld);
        setPosition(new Vec2(x,y));
        this.direction = Objects.requireNonNullElse(direction, Direction.RIGHT); // if the object is Null, then set it to the default value
        switch (type) {
            case FLAT -> {
                if (halfLength % PlatformType.FLAT.getSideWidth() != 0 && halfLength != 1.825f) {
                    throw new IllegalArgumentException("Invalid halfLength: " + halfLength + ". Must be a multiple of " + PlatformType.FLAT.getSideWidth());
                } else {
                    drawFlat(halfLength);
                }
            }
            case SLOPED -> {
                drawSloped(PlatformType.SLOPED);
            }
            case SKULL_SLOPED -> {
                drawSloped(PlatformType.SKULL_SLOPED);
            }
            default -> {throw new IllegalStateException("Unsupported platform type: " + type + ". Accepted types:\n1.FLAT\n2.SLOPED\n3.SKULL_SLOPED");}
        }

    }
    // Methods
    /**
     * Draws a flat platform with the specified half-length.
     * @param halfLength the half-length of the platform
     */
    private void drawFlat(float halfLength) {
        new SolidFixture(this, new BoxShape(halfLength, 1));
        BodyImage right;
        BodyImage left;
        if (direction.equals(Direction.RIGHT)) {
            right = PlatformType.FLAT.getSideBody();
            left = PlatformType.FLAT.getMiddleBody();
        } else {
            right = PlatformType.FLAT.getMiddleBody();
            left = PlatformType.FLAT.getSideBody();
        }
        if (halfLength == 1.825f) {
            addImage(PlatformType.FLAT.getSideBody()).setOffset(new Vec2(getOriginPos().x - 2.8f, -2f));
        } else {
            for (float xPos = -halfLength + 2.7f; xPos < halfLength; xPos+= PlatformType.FLAT.getSideWidth()) {
                if ((int)(Math.random() * 101) % 3 == 0) { // randomly adds a middle image
                    addImage(left).setOffset(new Vec2(xPos,-2f));
                } else {
                    addImage(right).setOffset(new Vec2(xPos, -2f));
                }
            }
        }

    }
    /**
     * Draws a sloped platform with the specified type.
     * @param type the type of platform: {@code "SLOPED"} or {@code "SKULL_SLOPED"}
     */
    private void drawSloped(PlatformType type) {
        float halfW = type.getSideWidth()/2;
        if (direction.equals(Direction.RIGHT)) {
            addImage(type.getSideBody()).setOffset(new Vec2(halfW, -2f));
            new SolidFixture(this, new PolygonShape(-halfW, 1, halfW-0.2f, 1, halfW-0.2f, 2)).setFriction(5f); // friction prevents player from sliding down the slope
        } else {
            addImage(type.getMiddleBody()).setOffset(new Vec2(halfW - 1f, -3f));
            new SolidFixture(this, new PolygonShape(-halfW, 0, -halfW, 1, halfW, 0)).setFriction(5f);
        }


    }
}
