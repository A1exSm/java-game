package game.body.staticstructs.ground.gothicCemetery;
// Imports

import game.core.GameWorld;
import game.enums.Direction;
import game.utils.GameBodyImage;

/**
 *
 */
// Class
public final class GothicSlopeSkull extends GothicSlope {
    // Fields
    public static final GameBodyImage IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-Tiles/slope_skull.png", 4f);
    // Constructor
    public GothicSlopeSkull(GameWorld gameWorld, float x, float y, int lengthScale, Direction direction) {
        super(gameWorld, x, y, lengthScale, direction, IMG, 2);
    }
    // Methods
}
