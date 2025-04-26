package game.body.staticstructs.ground.magicCliffs;
// Imports
import game.body.staticstructs.ground.Prop;
import game.core.GameWorld;
import game.utils.GameBodyImage;
// Class
/**
 * MagicProp class represents a prop in the game world.
 * It extends the Prop class and is used to create props with specific images,
 * which are set as static constants.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 08-04-2025
 */
public class MagicProp extends Prop {
    // Fields
    /**
     * An image of a tree<br>
     * <img src="doc-files/tree.png" alt="image of a tree">
     */
    public static final GameBodyImage TREE_IMG = new GameBodyImage("data/MagicCliffs/misc/tree.png", 11f);
    // Constructor
    /**
     * Constructor for the MagicProp class.
     * @param gameWorld The game world in which the prop exists.
     * @param img The image representing the prop.
     */
    public MagicProp(GameWorld gameWorld, GameBodyImage img) {
        super(gameWorld, img);
    }
}
