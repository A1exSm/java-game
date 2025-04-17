package game.body.staticstructs.ground.magicCliffs;
// Imports

import game.body.staticstructs.ground.Prop;
import game.core.GameWorld;
import game.utils.GameBodyImage;

/**
 *
 */
// Class
public class MagicProp extends Prop {
    // Fields
    public static final GameBodyImage TREE_IMG = new GameBodyImage("data/MagicCliffs/misc/tree.png", 11f);
    // Constructor
    public MagicProp(GameWorld gameWorld, GameBodyImage img) {
        super(gameWorld, img);
    }
    // Methods
}
