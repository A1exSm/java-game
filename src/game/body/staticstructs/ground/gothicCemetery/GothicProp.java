package game.body.staticstructs.ground.gothicCemetery;
// Imports
import game.body.staticstructs.ground.Prop;
import game.core.GameWorld;
import game.utils.GameBodyImage;
// Class
/**
 * A class extending Prop, representing various props in the Gothic Cemetery.
 * Has a sizeable number of static images for different props.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 08-04-2025
 */
public class GothicProp extends Prop {
    // Fields
    /**
     * An image of a large bush<br>
     * <img src="doc-files/bush-large.png" alt="a large bush">
     */
    public static final GameBodyImage BUSH_LARGE_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-objects/bush-large.png", 4f);
    /**
     * An image of a small bush<br>
     * <img src="doc-files/bush-small.png" alt="a small bush">
     */
    public static final GameBodyImage BUSH_SMALL_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-objects/bush-small.png", 4f);
    /**
     * An image of a statue<br>
     * <img src="doc-files/statue.png" alt="a statue">
     */
    public static final GameBodyImage STATUE_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-objects/statue.png", 8f);
    /**
     * An image of a stone (type 1) <br>
     * <img src="doc-files/stone-1.png" alt="stone (type 1)">
     */
    public static final GameBodyImage STONE_1_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-objects/stone-1.png", 4f);
    /**
     * An image of a stone (type 2) <br>
     * <img src="doc-files/stone-2.png" alt="stone (type 2)">
     */
    public static final GameBodyImage STONE_2_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-objects/stone-2.png", 4f);
    /**
     * An image of a stone (type 3) <br>
     * <img src="doc-files/stone-3.png" alt="stone (type 3)">
     */
    public static final GameBodyImage STONE_3_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-objects/stone-3.png", 4f);
    /**
     * An image of a stone (type 4) <br>
     * <img src="doc-files/stone-4.png" alt="stone (type 4)">
     */
    public static final GameBodyImage STONE_4_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-objects/stone-4.png", 4f);
    /**
     * An image of a tree (type 1) <br>
     * <img src="doc-files/tree-1.png" alt="tree (type 1)">
     */
    public static final GameBodyImage TREE_1_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-objects/tree-1.png", 8f);
    /**
     * An image of a tree (type 2) <br>
     * <img src="doc-files/tree-2.png" alt="tree (type 2)">
     */
    public static final GameBodyImage TREE_2_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-objects/tree-2.png", 8f);
    /**
     * An image of a tree (type 3) <br>
     * <img src="doc-files/tree-3.png" alt="tree (type 3)">
     */
    public static final GameBodyImage TREE_3_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-objects/tree-3.png", 8f);
    // Constructor
    /**
     * Creates a new GothicProp object with the given image.
     * @param gameWorld the game world
     * @param img the image of the prop
     */
    public GothicProp(GameWorld gameWorld, GameBodyImage img) {
        super(gameWorld, img);
    }
}
