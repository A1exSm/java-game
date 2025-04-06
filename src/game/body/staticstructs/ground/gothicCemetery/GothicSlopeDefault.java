package game.body.staticstructs.ground.gothicCemetery;
// Imports

import game.core.GameWorld;
import game.enums.Direction;
import game.utils.GameBodyImage;

/**
 *
 */
// Class
public final class GothicSlopeDefault extends GothicSlope {
    // Fields
    public static final GameBodyImage IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-Tiles/slope.png", 4f);
    // Constructor
    public GothicSlopeDefault(GameWorld gameWorld, float x, float y, int lengthScale, Direction direction) {
        super(gameWorld, x, y, lengthScale, direction, IMG, 1);
    }

    // Methods
}
