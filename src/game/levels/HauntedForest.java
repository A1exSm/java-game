package game.levels;
// Imports

import game.Game;
import game.body.staticstructs.HauntedBackdrop;
import game.body.staticstructs.ground.GroundFrame;
import game.body.staticstructs.ground.hauntedForest.HauntedFlatPlatform;
import game.body.staticstructs.ground.hauntedForest.HauntedPillar;
import game.body.staticstructs.ground.hauntedForest.HauntedProp;
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
//    public static final int NUM_MOBS = 0;
    public static final MobStore NUM_MOBS = new MobStore(2, new int[]{0, 0});
    // Constructor
    public HauntedForest(GameWorld gameWorld, int levelNumber) {
        super(gameWorld, levelNumber);
        setBoundaries(new Vec2(0, 0), 200, -30, -300, 300);
        initLevel(levelNumber);
        setPlayerSpawn(new Vec2(0, 2));
        resetPlayerPos();
    }
    // Methods
    @Override
    protected void initLevel(int levelNumber) {
        if (levelNumber == 1) {
            levelOneStructures();
            levelOneMobs();
        } else if (levelNumber == 2) {
            levelTwoStructures();
            levelTwoMobs();
        }
    }
    private void levelOneStructures() {
        addGroundFrame("PillarA", new HauntedPillar(gameWorld, 35,0, false, true, this));
        addGroundFrame("SlopeA", new HauntedSlopePlatform(gameWorld, 20, 0, 3, Direction.UP));
        addGroundFrame("PlatformA", new HauntedFlatPlatform(gameWorld, -0.8f, -1, 3));
        Vec2 aPos = getGroundFramePosition("PlatformA");
        GroundFrame platformA = getGroundFrame("PlatformA");
        addGroundFrame("SlopeB", new HauntedSlopePlatform(gameWorld, aPos.x - platformA.getHalfDimensions().x - HauntedSlopePlatform.IMG.getDimensions().x*6, aPos.y + HauntedFlatPlatform.IMG_A.getHalfDimensions().x - HauntedSlopePlatform.IMG.getHalfDimensions().y*6, 6, Direction.UP));
        addGroundFrame("BackdropA", new HauntedBackdrop(gameWorld, getGroundFramePosition("SlopeB").x - getGroundFrame("SlopeB").getHalfDimensions().x - HauntedBackdrop.IMG.getHalfDimensions().x*3, getGroundFramePosition("SlopeB").y + HauntedBackdrop.IMG.getHalfDimensions().y - HauntedBackdrop.PLATFORM_HEIGHT*2 + 0.1f, 3));
        getGroundFrame("PlatformA").addProp(new HauntedProp(gameWorld, HauntedProp.TREE_1_IMG), 0);
        getGroundFrame("PlatformA").addProp(new HauntedProp(gameWorld, HauntedProp.TREE_2_IMG), 2);
    }
    private void levelOneMobs() {}
    private void levelTwoStructures() {}
    private void levelTwoMobs() {}
    @Override
    protected void objectiveComplete() {
        Game.hauntedData.unlockLevel(getLevelNum());
    }
}
