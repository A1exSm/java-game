package game.levels;
// Imports

import game.Game;
import game.body.staticstructs.ground.hauntedForest.HauntedBackdrop;
import game.body.staticstructs.ground.GroundFrame;
import game.body.staticstructs.ground.hauntedForest.HauntedFlatPlatform;
import game.body.staticstructs.ground.hauntedForest.HauntedPillar;
import game.body.staticstructs.ground.hauntedForest.HauntedProp;
import game.body.staticstructs.ground.hauntedForest.HauntedSlopePlatform;
import game.core.GameWorld;
import game.core.console.Console;
import game.enums.Direction;
import game.enums.WalkerType;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
public class HauntedForest extends LevelFrame {
    // Fields
//    public static final int NUM_MOBS = 0;
    public static final MobStore NUM_MOBS = new MobStore(2, new int[]{10, 0});
    // Constructor
    public HauntedForest(GameWorld gameWorld, int levelNumber) {
        super(gameWorld, levelNumber);
        setBoundaries(new Vec2(0, 0), 200, -30, -300, 300);
        setPlayerSpawn(new Vec2(0, 0));
        initLevel(levelNumber);
        resetPlayerPos();
        checkForNoPosInitMobs();
    }
    // Methods
    @Override
    protected void initLevel(int levelNumber) {
        if (levelNumber == 1) {
            levelOneMobs();
            levelOneStructures();
            levelOnePositioning();
        } else if (levelNumber == 2) {
            levelTwoMobs();
            levelTwoStructures();
        }
    }
    // Level 1
    private void levelOneStructures() {
        GroundFrame platformA = add("PlatformA", new HauntedFlatPlatform(gameWorld, -0.8f, -1, 3));
        Vec2 aPos = getPos("PlatformA");
        add("SlopeA", new HauntedSlopePlatform(gameWorld, aPos.x - platformA.getHalfDimensions().x - HauntedSlopePlatform.IMG.getDimensions().x*6, aPos.y + HauntedFlatPlatform.IMG_A.getHalfDimensions().x - HauntedSlopePlatform.IMG.getHalfDimensions().y*6, 6, Direction.UP));
        add("BackdropA", new HauntedBackdrop(gameWorld, getPos("SlopeA").x - get("SlopeA").getHalfDimensions().x - HauntedBackdrop.IMG.getHalfDimensions().x*3, getPos("SlopeA").y + HauntedBackdrop.IMG.getHalfDimensions().y - HauntedBackdrop.PLATFORM_HEIGHT*2 + 0.1f, 3));
        GroundFrame platformB = add("PlatformB", new HauntedFlatPlatform(gameWorld, 0,0, 10));
        GroundFrame pillarA = add("PillarA", new HauntedPillar(gameWorld, 0,0, false, this));
        GroundFrame pillarB = add("PillarB", new HauntedPillar(gameWorld, 0,0, false, this));
        GroundFrame pillarC = add("PillarC", new HauntedPillar(gameWorld, 0,0, false, this));

        // PosChanges
        platformB.setPosition(new Vec2(platformA.getOriginPos().x + platformA.getHalfDimensions().x + platformB.getHalfDimensions().x,-1));
        pillarA.setPosition(new Vec2(platformB.getPosition().x + platformB.getHalfDimensions().x + pillarA.getHalfDimensions().x, -1));
        pillarB.setPosition(new Vec2(pillarA.getPosition().x + pillarA.getHalfDimensions().x*2, pillarA.getPosition().y + pillarA.getHalfDimensions().y + pillarB.getHalfDimensions().y));
        pillarC.setPosition(new Vec2(pillarA.getPosition().x + pillarA.getHalfDimensions().x*2, -1));
        // Props
        platformB.addProp(new HauntedProp(gameWorld, HauntedProp.TREE_2_IMG), GroundFrame.randRangeInt(0, 10));
        platformB.addProp(new HauntedProp(gameWorld, HauntedProp.TREE_1_IMG), 0);
        platformB.addProp(new HauntedProp(gameWorld, HauntedProp.TREE_2_IMG), 2);
    }
    private void levelOneMobs() {
        add(WalkerType.WIZARD, ++count);
        add(WalkerType.WIZARD, ++count);
        add(WalkerType.WORM, ++count);
        add(WalkerType.WIZARD, ++count);
        add(WalkerType.WIZARD, ++count);
        add(WalkerType.WORM, ++count);
        add(WalkerType.WIZARD, ++count);
        add(WalkerType.WIZARD, ++count);
        add(WalkerType.WORM, ++count);
        add(WalkerType.WORM, ++count);
    }

    private void levelOnePositioning() {
        setPos("1", get("PlatformB"));
        setPos("2", get("PlatformB"));
        setPos("3", get("PlatformB"));
        setPos("4", get("PlatformB"));
        setPos("5", get("PlatformA"));
        setPos("6", get("PlatformA"));
        setPos("7", get("PlatformA"));
        setPos("8", get("BackdropA"));
        setPos("9", get("BackdropA"));
        setPos("10", get("BackdropA"));
        setPlayerSpawnNoCentre(new Vec2(get("PillarA").getPosition().x, get("PillarA").getYTop()));
    }
    // Level 2
    private void levelTwoStructures() {}
    private void levelTwoMobs() {}
    // Overrides
    @Override
    protected void objectiveComplete() {
        Game.hauntedData.unlockLevel(getLevelNum());
    }
}
