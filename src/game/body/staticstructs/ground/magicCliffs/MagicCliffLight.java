package game.body.staticstructs.ground.magicCliffs;
// Imports
import game.core.GameWorld;
import game.utils.GameBodyImage;
// Class
/**
 * MagicCliffDark class represents a dark magic cliff in the game.
 * It extends the MagicPlatformCliff class and provides a specific image for the cliff.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 17-04-2025
 */
public class MagicCliffLight extends MagicPlatformCliff {
    // Fields
    /**
     * IMG is the image used for the light magic cliff.<br>
     * <img src="doc-files/light_left.png" alt="Light Magic Cliff Image">
     */
    public static final GameBodyImage IMG = new GameBodyImage("data/MagicCliffs/cliff/light_left.png", 11f);
    // Constructor
    /**
     * Constructor for the MagicCliffLight class.
     * @param gameWorld The game world in which the cliff is added to.
     * @param x The x-coordinate of the cliff.
     * @param y The y-coordinate of the cliff.
     * @param lengthScale The scale of the cliff's length.
     * @throws IllegalArgumentException if the lengthScale is less than 1.
     */
    public MagicCliffLight(GameWorld gameWorld, float x, float y, int lengthScale) {
        super(gameWorld, x, y, lengthScale, IMG);
    }
}
