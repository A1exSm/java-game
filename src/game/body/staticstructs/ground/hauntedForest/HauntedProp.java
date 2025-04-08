package game.body.staticstructs.ground.hauntedForest;
// Imports

import game.body.staticstructs.ground.Prop;
import game.core.GameWorld;
import game.utils.GameBodyImage;

/**
 *
 */
// Class
public class HauntedProp extends Prop {
    // Fields
    public static final GameBodyImage PILLAR_FACE_IMG = new GameBodyImage("data/HauntedForest/Props/pillar_face.png", 4f);
    public static final GameBodyImage PILLAR_1_IMG = new GameBodyImage("data/HauntedForest/Props/pillarA.png", 4f);
    public static final GameBodyImage PILLAR_2_IMG = new GameBodyImage("data/HauntedForest/Props/pillarB.png", 4f);
    public static final GameBodyImage TREE_1_IMG = new GameBodyImage("data/HauntedForest/Props/tree.png", 12f);
    public static final GameBodyImage TREE_2_IMG = new GameBodyImage("data/HauntedForest/Props/tree-2.png", 12f);
    // Constructor
    public HauntedProp(GameWorld gameWorld, GameBodyImage img) {
        super(gameWorld, img);
    }
    // Methods
}
