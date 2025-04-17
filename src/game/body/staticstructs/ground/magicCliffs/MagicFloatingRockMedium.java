package game.body.staticstructs.ground.magicCliffs;
// Imports

import game.core.GameWorld;
import game.utils.GameBodyImage;

/**
 *
 */
// Class
public class MagicFloatingRockMedium extends MagicFloatingRock {
    // Fields
    public static final GameBodyImage IMG = new GameBodyImage("data/MagicCliffs/rock/cliff_medium.png", 3f);
    // Constructor
    public MagicFloatingRockMedium(GameWorld gameWorld, float x, float y) {
        super(gameWorld, x, y, IMG);
    }
}
