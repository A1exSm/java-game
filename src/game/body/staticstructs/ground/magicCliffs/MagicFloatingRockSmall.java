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
public class MagicFloatingRockSmall extends MagicFloatingRock {
    // Fields
    /**
     * The image of a small magic floating rock.<br>
     * <img src="doc-files/cliff_small.png" alt="cliff_small.png">
     */
    public static final GameBodyImage IMG = new GameBodyImage("data/MagicCliffs/rock/cliff_small.png", 2f);
    // Constructor
    /**
     * A Small floating rock.
     * @param gameWorld The game world.
     * @param x The x position of the rock.
     * @param y The y position of the rock.
     */
    public MagicFloatingRockSmall(GameWorld gameWorld, float x, float y) {
        super(gameWorld, x, y, IMG);
    }
}
