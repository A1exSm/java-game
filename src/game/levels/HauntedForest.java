package game.levels;
// Imports

import game.body.staticstructs.ground.hauntedForest.HauntedPlatform;
import game.body.staticstructs.ground.magicCliffs.MagicPlatform;
import game.core.GameWorld;
import game.enums.Direction;
import game.enums.PlatformType;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
public class HauntedForest extends LevelFrame {
    // Fields
    public static final int NUM_MOBS = 0;
    // Constructor
    public HauntedForest(GameWorld gameWorld) {
        super(gameWorld);
        setBoundaries(new Vec2(0, 0), 20, -20, -300, 300);
        initMobs();
        initFrames();
        setPlayerSpawn(new Vec2(0, 2));
        resetPlayerPos();
    }
    // Methods
    @Override
    protected void initFrames() {
        GameWorld gameWorld = getGameWorld();
        addGroundFrame("B", new HauntedPlatform(gameWorld,  PlatformType.SLOPED.getSideWidth() - 0.2f, 0, 1.825f, PlatformType.FLAT, Direction.RIGHT));
        addGroundFrame("C", new HauntedPlatform(gameWorld, -29.2f, -1, 29.2f, PlatformType.FLAT, Direction.RIGHT));
        addGroundFrame("A", new HauntedPlatform(gameWorld, 0, -1, 0, PlatformType.SKULL_SLOPED, Direction.RIGHT));
        addGroundFrame("D", new HauntedPlatform(gameWorld, (PlatformType.SLOPED.getSideWidth() - 0.4f) + 3.65f, 0, 0, PlatformType.SLOPED, Direction.LEFT));
        addGroundFrame("E", new HauntedPlatform(gameWorld, (PlatformType.SLOPED.getSideWidth() - 0.4f) + (3.65f*2), -1, 0, PlatformType.SLOPED, Direction.LEFT));
        addGroundFrame("F", new HauntedPlatform(gameWorld, (PlatformType.SLOPED.getSideWidth() - 0.4f) + (3.65f*3), -2, 0, PlatformType.SLOPED, Direction.LEFT));
        addGroundFrame("G", new HauntedPlatform(gameWorld, (PlatformType.SLOPED.getSideWidth() - 0.4f) + (3.65f*4), -3, 0, PlatformType.SLOPED, Direction.LEFT));
        addGroundFrame("H", new HauntedPlatform(gameWorld, (PlatformType.SLOPED.getSideWidth() - 0.4f) + (3.65f*5), -4, 0, PlatformType.SLOPED, Direction.LEFT));
        addGroundFrame("I", new HauntedPlatform(gameWorld, (PlatformType.SLOPED.getSideWidth() - 0.4f) + (3.65f*6), -5, 0, PlatformType.SLOPED, Direction.LEFT));
        addGroundFrame("J", new HauntedPlatform(gameWorld, (PlatformType.SLOPED.getSideWidth() - 0.4f) + (3.65f*7), -6, 0, PlatformType.SLOPED, Direction.LEFT));

    }
    @Override
    protected void initMobs() {
        GameWorld gameWorld = getGameWorld();
        float xCentre = getCentre().x;
        float yCentre = getCentre().y;
        // Add mobs here
    }
}
