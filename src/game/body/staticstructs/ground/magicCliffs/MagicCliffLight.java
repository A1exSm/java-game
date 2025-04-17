package game.body.staticstructs.ground.magicCliffs;
// Imports

import game.core.GameWorld;
import game.utils.GameBodyImage;

/**
 *
 */
// Class
public class MagicCliffLight extends MagicPlatformCliff {
    // Fields
    public static final GameBodyImage IMG = new GameBodyImage("data/MagicCliffs/cliff/light_left.png", 11f);
    // Constructor
    public MagicCliffLight(GameWorld gameWorld, float x, float y, int lengthScale) {
        super(gameWorld, x, y, lengthScale, IMG);
    }

    // Methods
}
