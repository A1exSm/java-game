package game.body.staticstructs.ground.gothicCemetery;
// Imports
import game.core.GameWorld;
import game.utils.GameBodyImage;
// Class
/**
 * A flat platform using the image {@link #IMG} with skulls on it.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 06-04-2025
 */
public final class GothicFlatSkull extends GothicFlat{
    // Fields
    /**
     * The image of the flat platform with skull.<br>
     * <img src="doc-files/ground_skulls.png" alt="Flat platform with skulls">
     */
    public static final GameBodyImage IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-Tiles/ground_skulls.png", 4f);
    // Constructor
    /**
     * Creates a flat platform with skulls on it.
     * @param gameWorld The game world to add this body to.
     * @param x The x position of the body.
     * @param y The y position of the body.
     * @param lengthScale The length scale of the body.
     * @throws IllegalArgumentException if the length scale is less than 1.
     */
    public GothicFlatSkull(GameWorld gameWorld, float x, float y, int lengthScale) {
        super(gameWorld, x, y, lengthScale, IMG);
    }
}
