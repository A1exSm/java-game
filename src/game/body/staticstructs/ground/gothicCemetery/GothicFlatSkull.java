package game.body.staticstructs.ground.gothicCemetery;
// Imports

import game.core.GameWorld;
import game.utils.GameBodyImage;

/**
 *
 */
// Class
public final class GothicFlatSkull extends GothicFlat{
    // Fields
    public static final GameBodyImage IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-Tiles/ground_skulls.png", 4f);
    // Constructor
    public GothicFlatSkull(GameWorld gameWorld, float x, float y, int lengthScale) {
        super(gameWorld, x, y, lengthScale, IMG);
    }
    // Methods
}
