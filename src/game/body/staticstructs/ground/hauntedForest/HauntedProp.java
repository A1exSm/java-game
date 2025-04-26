package game.body.staticstructs.ground.hauntedForest;
// Imports
import game.body.staticstructs.ground.Prop;
import game.core.GameWorld;
import game.utils.GameBodyImage;
// Class
/**
 * Represents a haunted prop in the game world.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 08-04-2025
 */
public class HauntedProp extends Prop {
    // Fields
    /**
     * An image of a pillar face<br>
     * <img src="doc-files/pillar_face.png" alt="A pillar face">
     */
    public static final GameBodyImage PILLAR_FACE_IMG = new GameBodyImage("data/HauntedForest/Props/pillar_face.png", 4f);
    /**
     * An image of a pillar<br>
     * <img src="doc-files/pillarA.png" alt="A pillar A">
     */
    public static final GameBodyImage PILLAR_1_IMG = new GameBodyImage("data/HauntedForest/Props/pillarA.png", 4f);
    /**
     * An image of a pillar<br>
     * <img src="doc-files/pillarB.png" alt="A pillar B">
     */
    public static final GameBodyImage PILLAR_2_IMG = new GameBodyImage("data/HauntedForest/Props/pillarB.png", 4f);
    /**
     * An image of a tree (type A)<br>
     * <img src="doc-files/tree.png" alt="A tree (type A)">
     */
    public static final GameBodyImage TREE_1_IMG = new GameBodyImage("data/HauntedForest/Props/tree.png", 12f);
    /**
     * An image of a tree (type B)<br>
     * <img src="doc-files/tree-2.png" alt="A tree (type B)">
     */
    public static final GameBodyImage TREE_2_IMG = new GameBodyImage("data/HauntedForest/Props/tree-2.png", 12f);
    // Constructor
    /**
     * Constructor for HauntedProp.<br>
     * Creates a new instance of HauntedProp with the given image.
     *
     * @param gameWorld the game world
     * @param img the image of the prop
     */
    public HauntedProp(GameWorld gameWorld, GameBodyImage img) {
        super(gameWorld, img);
    }
}
