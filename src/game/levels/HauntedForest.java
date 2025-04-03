package game.levels;
// Imports

import game.body.staticstructs.HauntedBackdrop;
import game.body.staticstructs.ground.hauntedForest.HauntedFlatPlatform;
import game.body.staticstructs.ground.hauntedForest.HauntedSlopePlatform;
import game.core.GameWorld;
import game.enums.Direction;
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
        addGroundFrame("SlopeA", new HauntedSlopePlatform(gameWorld, 20, 0, 3, Direction.RIGHT, Direction.UP));
        addGroundFrame("PlatformA", new HauntedFlatPlatform(gameWorld, -0.8f, -1, 3, Direction.RIGHT));
        addGroundFrame("SlopeB", new HauntedSlopePlatform(gameWorld, -31.3f, -12f, 6, Direction.LEFT, Direction.DOWN));
        addGroundFrame("BackdropA", new HauntedBackdrop(gameWorld, getGroundFramePosition("SlopeB").x - groundFrames.get("SlopeB").getHalfDimensions().x - HauntedBackdrop.WIDTH/2, -5.8f));
        addGroundFrame("BackdropB", new HauntedBackdrop(gameWorld, getGroundFramePosition("BackdropA").x - HauntedBackdrop.WIDTH, -5.8f));
    }
    @Override
    protected void initMobs() {
        GameWorld gameWorld = getGameWorld();
        float xCentre = getCentre().x;
        float yCentre = getCentre().y;
        // Add mobs here
    }
}
