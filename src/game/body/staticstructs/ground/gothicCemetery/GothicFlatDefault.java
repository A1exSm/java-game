package game.body.staticstructs.ground.gothicCemetery;
// Imports
import game.core.GameWorld;
import game.utils.GameBodyImage;
// Class
/**
 * A flat platform using the img {@link GothicFlatDefault#IMG} from the GothicvaniaCemetery.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 06-04-2025
 */
public final class GothicFlatDefault extends GothicFlat{
    // Fields
    /**
     * The image used for the flat object.<br>
     * <img src="doc-files/ground.png" alt="ground.png"/>
     */
    public static final GameBodyImage IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-Tiles/ground.png", 4f);
    // Constructor
    /**
     * Provides an image to its parent class {@link GothicFlat}.
     * @param gameWorld   The game world instance.
     * @param x          The x-coordinate of the flat object.
     * @param y          The y-coordinate of the flat object.
     * @param lengthScale The length scale for the flat object.
     * @throws IllegalArgumentException if the lengthScale is less than 1.
     */
    public GothicFlatDefault(GameWorld gameWorld, float x, float y, int lengthScale) {
        super(gameWorld, x, y, lengthScale, IMG);
    }
}
