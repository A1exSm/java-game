package game.body.staticstructs.ground.hauntedForest;
// Imports
import city.cs.engine.*;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import game.exceptions.IllegalLengthScaleException;
import game.utils.GameBodyImage;
import org.jbox2d.common.Vec2;
// Class
/**
 * A class representing a haunted backdrop in the game.
 * This class extends the GroundFrame class and provides functionality for creating a haunted backdrop.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 03-04-2025
 */
public class HauntedBackdrop extends GroundFrame {
    // Fields
    /**
     * An image representing the haunted backdrop.<br>
     * <img src="doc-files/back_drop_platform.png" alt="Haunted Backdrop">
     */
    public static final GameBodyImage IMG = new GameBodyImage("data/HauntedForest/tiles/back_drop_platform.png", 16f);
    private static final float PLATFORM_HEIGHT  = 0.9f; // Height of the platform parts
    private static final float PLATFORM_Y = 7.125f; // Height of the entire image
    private final int lengthScale;
    private final GameWorld gameWorld;
    // Constructor
    /**
     * Constructor for HauntedBackdrop.<br>
     * Creates a new instance of HauntedBackdrop with the specified parameters.
     * @param gameWorld the game world
     * @param x the x-coordinate of the backdrop
     * @param y the y-coordinate of the backdrop
     * @param lengthScale the length scale of the backdrop (must be >= 1)
     * @throws IllegalLengthScaleException if lengthScale is less than 1
     */
    public HauntedBackdrop(GameWorld gameWorld, float x, float y, int lengthScale) {
        super(gameWorld);
        this.gameWorld = gameWorld;
        this.lengthScale = IllegalLengthScaleException.checkLengthScale(lengthScale);
        halfDimensions.x = IMG.getHalfDimensions().x*lengthScale;
        halfDimensions.y = IMG.getHalfDimensions().y;
        setPosition(new Vec2(x, y));
        setYTop(getPosition().y - halfDimensions.y + PLATFORM_HEIGHT);
        new GhostlyFixture(this, new BoxShape(0.1f, 0.1f));
        new SolidFixture(this, new BoxShape(IMG.getHalfDimensions().x*lengthScale, PLATFORM_HEIGHT, new Vec2(0, PLATFORM_Y)));
        new SolidFixture(this, new BoxShape(IMG.getHalfDimensions().x*lengthScale, PLATFORM_HEIGHT, new Vec2(0, -PLATFORM_Y)));
        paint();
    }
    // Methods

    /**
     * {@inheritDoc}
     */
    @Override
    public void paint() {
        removeAllImages();
        if (lengthScale == 1) {
            addImage(IMG);
        } else {
            for (int i = 0; i < lengthScale; i++) {
                addImage(IMG).setOffset(new Vec2((-halfDimensions.x + IMG.getHalfDimensions().x) + i*IMG.getDimensions().x, 0));
            }
        }
    }
    /**
     * Sets the position of the backdrop if it is different from the original position.
     * Does not reset yTop like the super class.
     * @param pos the new position of the backdrop
     */
    @Override
    public void setPosition(Vec2 pos) {
        superSetPosition(pos);
        if (pos != originPos) {
            originPos.x = pos.x;
            originPos.y = pos.y;
        }
    }
    /**
     * returns a duplicated instance of the HauntedBackdrop.
     */
    public void duplicate() {
        new HauntedBackdrop(gameWorld, getPosition().x, getPosition().y, lengthScale);
    }
    /**
     * returns the PLATFORM_HEIGHT constant of the HauntedBackdrop class.
     * @return the height of the platform parts
     */
    public static float getPlatformHeight() {
        return PLATFORM_HEIGHT;
    }
}
