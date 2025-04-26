package game.body.staticstructs.ground.hauntedForest;
// Imports
import city.cs.engine.*;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import game.core.console.Console;
import game.enums.Direction;
import game.exceptions.IllegalLengthScaleException;
import game.utils.GameBodyImage;
import org.jbox2d.common.Vec2;

// Class
/**
 * Represents a slope platform in the haunted forest level of the game.
 * The slope can be either upward or downward, and its length is determined by the lengthScale parameter.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 03-04-2025
 */
public class HauntedSlopePlatform extends GroundFrame {
    // Fields
    private final int lengthScale;
    /**
     * The image of the slope platform.<br>
     * <img src="doc-files/slope.png" alt="Sloped Platform">
     */
    public static final GameBodyImage IMG = new GameBodyImage("data/HauntedForest/tiles/slope.png", 8f);
    /**
     * The image of the slope platform with a skull.<br>
     * <img src="doc-files/slope_skull.png" alt="Sloped Platform with skulls">
     */
    public static final GameBodyImage SKULL_IMG = new GameBodyImage("data/HauntedForest/tiles/slope_skull.png", 8f);
    private final Direction direction;
    // Constructor
    /**
     * Constructor for HauntedSlopePlatform.<br>
     * Creates a new instance of HauntedSlopePlatform with the specified parameters.
     * @param gameWorld the game world
     * @param x the x-coordinate of the platform
     * @param y the y-coordinate of the platform
     * @param lengthScale the length scale of the platform (must be >= 1)
     * @param direction the vertical direction of the platform: {@link Direction#UP} or {@link Direction#DOWN}.
     * @throws IllegalLengthScaleException if lengthScale is less than 1.
     * @throws IllegalArgumentException if the direction is not UP or DOWN.
     */
    public HauntedSlopePlatform(GameWorld gameWorld, float x, float y, int lengthScale, Direction direction) {
        super(gameWorld);
        this.lengthScale = IllegalLengthScaleException.checkLengthScale(lengthScale);
        halfDimensions.x = lengthScale * IMG.getHalfDimensions().x;
        this.direction = direction;
        setPosition(new Vec2(x, y));
        halfDimensions.x = lengthScale * IMG.getDimensions().x;
        halfDimensions.y = lengthScale * 2;
        new SolidFixture(this, new PolygonShape(getFloats()));
        paint();
    }
    // Methods | private
    /**
     * Returns the vertices of the slope platform based on its direction.
     * @return an array of floats representing the vertices of the slope platform.
     */
    private float[] getFloats() {
        float[] vertices;
        switch (direction) {
            case DOWN -> {
                vertices = new float[]{-halfDimensions.x, IMG.getHalfDimensions().y*lengthScale - 1, -halfDimensions.x, IMG.getHalfDimensions().y*lengthScale - IMG.getDimensions().y, halfDimensions.x, -IMG.getDimensions().y,halfDimensions.x, -1};
                new GhostlyFixture(this, new BoxShape(0.1f, 0.1f));
            }
            case UP -> vertices = new float[]{-halfDimensions.x, 0, halfDimensions.x, 0, halfDimensions.x, IMG.getHalfDimensions().y*lengthScale -  IMG.getHalfDimensions().y/4};
            default -> throw new IllegalArgumentException(Console.exceptionMessage("Invalid direction: " + direction + ". Req: UP or DOWN."));
        }
        return vertices;
    }
    // Methods | public
    /**
     * {@inheritDoc}
     */
    @Override
    public void paint() {
        if (lengthScale == 1) {
            AttachedImage attachedImage = new AttachedImage(this, IMG, 1f, 0, new Vec2(0, -halfDimensions.y));
            if (direction.equals(Direction.DOWN)) {attachedImage.flipHorizontal();}

        } else {
            for (int i = 0; i < lengthScale * 2; i++) {
                AttachedImage attachedImage = new AttachedImage(this, IMG, 1f, 0, new Vec2((-halfDimensions.x + IMG.getHalfDimensions().x) + i * IMG.getDimensions().x, -IMG.getHalfDimensions().y/2 + (i * IMG.getHalfDimensions().y/2)));
                if (direction.equals(Direction.DOWN)) {attachedImage.flipHorizontal();}
            }
        }
    }
}
