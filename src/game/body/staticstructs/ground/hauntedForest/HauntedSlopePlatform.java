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

import java.util.ArrayList;

/**
 *
 */
// Class
public class HauntedSlopePlatform extends GroundFrame {
    // Fields
    private final int lengthScale;
    public static final GameBodyImage IMG = new GameBodyImage("data/HauntedForest/tiles/slope.png", 8f);
    public static final GameBodyImage SKULL_IMG = new GameBodyImage("data/HauntedForest/tiles/slope_skull.png", 8f);
    private final Direction direction;
    // Constructor
    /**
     * Constructor for HauntedSlopePlatform.<br>
     * Creates a new instance of HauntedSlopePlatform with the specified parameters.
     *
     * @param gameWorld the game world
     * @param x the x-coordinate of the platform
     * @param y the y-coordinate of the platform
     * @param lengthScale the length scale of the platform (must be >= 1)
     * @param direction the vertical direction of the platform: {@link Direction#UP} or {@link Direction#DOWN}.
     *
     * @Note: {@code verticalDirection} and {@code horizontalDirection} throw {@link IllegalArgumentException} if null.
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
    private float[] getFloats() {
        float[] vertices;
        switch (direction) {
            case DOWN -> {
                vertices = new float[]{-halfDimensions.x, IMG.getHalfDimensions().y*lengthScale - 1, -halfDimensions.x, IMG.getHalfDimensions().y*lengthScale - IMG.getDimensions().y, halfDimensions.x, -IMG.getDimensions().y,halfDimensions.x, -1};
                new GhostlyFixture(this, new BoxShape(0.1f, 0.1f));
            }
            case UP -> {
                vertices = new float[]{-halfDimensions.x, 0, halfDimensions.x, 0, halfDimensions.x, IMG.getHalfDimensions().y*lengthScale -  IMG.getHalfDimensions().y/4};
            }
            default -> {throw new IllegalArgumentException(Console.exceptionMessage("Invalid direction: " + direction + ". Req: UP or DOWN."));}
        }
        return vertices;
    }
    // Methods | public
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
