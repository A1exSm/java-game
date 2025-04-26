package game.body.staticstructs.ground.gothicCemetery;
// Imports
import game.core.GameWorld;
import game.enums.Direction;
import game.utils.GameBodyImage;
// Class
/**
 * A platform that takes user params and
 * uses its own default image to create a slope.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 06-04-2025
 */
public final class GothicSlopeDefault extends GothicSlope {
    // Fields
    /**
     * The default image for the slope.<br>
     * <img src="doc-files/slope.png" alt="GothicSlopeDefault">
     */
    public static final GameBodyImage IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-Tiles/slope.png", 4f);
    // Constructor
    /**
     * Creates a slope with the given parameters.
     * @param gameWorld The game world to which this slope belongs.
     * @param x The x coordinate of the slope.
     * @param y The y coordinate of the slope.
     * @param lengthScale The length scale of the slope.
     * @param direction The direction of the slope.
     * @throws game.exceptions.IllegalLengthScaleException if the lengthScale is less than 1.
     * @throws IllegalArgumentException if the direction is null or is not UP or DOWN.
     */
    public GothicSlopeDefault(GameWorld gameWorld, float x, float y, int lengthScale, Direction direction) {
        super(gameWorld, x, y, lengthScale, direction, IMG, 1);
    }
}
