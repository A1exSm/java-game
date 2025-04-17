package game.body.staticstructs.ground.magicCliffs;
// Imports

import game.core.GameWorld;
import game.utils.GameBodyImage;

/**
 *
 */
// Class
public class MagicFloatingRockSmall extends MagicFloatingRock {
    // Fields
    public static final GameBodyImage IMG = new GameBodyImage("data/MagicCliffs/rock/cliff_small.png", 2f);
    // Constructor
    public MagicFloatingRockSmall(GameWorld gameWorld, float x, float y) {
        super(gameWorld, x, y, IMG);
    }
}
