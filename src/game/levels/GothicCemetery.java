package game.levels;
// Imports

import game.body.staticstructs.HauntedBackdrop;
import game.body.staticstructs.ground.GroundFrame;
import game.body.staticstructs.ground.hauntedForest.HauntedFlatPlatform;
import game.body.staticstructs.ground.hauntedForest.HauntedPillar;
import game.body.staticstructs.ground.hauntedForest.HauntedSlopePlatform;
import game.core.GameWorld;
import game.enums.Direction;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
public class GothicCemetery extends LevelFrame{
    // Fields
    public static final int NUM_MOBS = 0;
    // Constructor
    public GothicCemetery(GameWorld gameWorld) {
        super(gameWorld);
        setBoundaries(new Vec2(0, 0), 300, -300, -300, 300);
        initMobs();
        initFrames();
        setPlayerSpawn(new Vec2(0, 2));
        resetPlayerPos();
    }
    // Methods
    @Override
    protected void initFrames() {
        GameWorld gameWorld = getGameWorld();
        addGroundFrame("test", new HauntedFlatPlatform(gameWorld, 0, -1, 3, Direction.RIGHT));
    }
    @Override
    protected void initMobs() {
        GameWorld gameWorld = getGameWorld();
        float xCentre = getCentre().x;
        float yCentre = getCentre().y;
        // Add mobs here
    }
}
