package game.body.staticstructs.ground.gothicCemetery;
// Imports

import game.body.staticstructs.ground.Prop;
import game.core.GameWorld;
import game.utils.GameBodyImage;

/**
 *
 */
// Class
public class GothicProp extends Prop {
    // Fields
    public static final GameBodyImage BUSH_LARGE_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-objects/bush-large.png", 4f);
    public static final GameBodyImage BUSH_SMALL_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-objects/bush-small.png", 4f);
    public static final GameBodyImage STATUE_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-objects/statue.png", 4f);
    public static final GameBodyImage STONE_1_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-objects/stone-1.png", 4f);
    public static final GameBodyImage STONE_2_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-objects/stone-2.png", 4f);
    public static final GameBodyImage STONE_3_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-objects/stone-3.png", 4f);
    public static final GameBodyImage STONE_4_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-objects/stone-4.png", 4f);
    public static final GameBodyImage TREE_1_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-objects/tree-1.png", 8f);
    public static final GameBodyImage TREE_2_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-objects/tree-2.png", 8f);
    public static final GameBodyImage TREE_3_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-objects/tree-3.png", 8f);
    // Constructor
    public GothicProp(GameWorld gameWorld, GameBodyImage img) {
        super(gameWorld, img);
    }
    // Methods
}
