package game.body.staticstructs.ground.gothicCemetery;
// Imports
import game.core.GameWorld;
import game.enums.Direction;
import game.utils.GameBodyImage;
// Class
/**
 * A GothicSlope using the skull image.
 * Scales in length based of the given lengthScale.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 06-04-2025
 */
public final class GothicSlopeSkull extends GothicSlope {
    // Fields
    /**
     * The image of the slope. <br>
     * <img src="doc-files/slope_skull.png" "alt="Slope Skull Image">
     */
    public static final GameBodyImage IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-Tiles/slope_skull.png", 4f);
    // Constructor
    /**
     * Creates a GothicSlopeSkull object with the given direction
     * and a length based on the given lengthScale.
     * @param gameWorld The game world the slope is in.
     * @param x The x position of the slope.
     * @param y The y position of the slope.
     * @param lengthScale The length scale of the slope.
     * @param direction The direction of the slope.
     * @throws game.exceptions.IllegalLengthScaleException if lengthScale is less than 1.
     * @throws IllegalArgumentException if the direction is not UP or DOWN
     */
    public GothicSlopeSkull(GameWorld gameWorld, float x, float y, int lengthScale, Direction direction) {
        super(gameWorld, x, y, lengthScale, direction, IMG, 2);
    }
}
