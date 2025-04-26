package game.levels;
// Imports
import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import game.Game;
import game.body.staticstructs.ground.hauntedForest.*;
import game.body.staticstructs.ground.GroundFrame;
import game.body.walkers.PlayerWalker;
import game.core.GameWorld;
import game.enums.Direction;
import game.enums.WalkerType;
import org.jbox2d.common.Vec2;
import java.awt.*;
/**
 * * The HauntedForest Environment class.
 */
// Class
public class HauntedForest extends LevelFrame {
    // Fields
    /**
     * The number of mobs in the level.
     * This is used to display the number of mobs in the level.
     */
    public static final MobStore NUM_MOBS = new MobStore(2, new int[]{10, 15});
    // Constructor
    /**
     * This is used to create the HauntedForest environment
     * of a given level.
     * @param gameWorld The game world to create the environment in.
     * @param levelNumber The level number to create the environment for.
     * @throws IllegalArgumentException if the level number is invalid.
     */
    public HauntedForest(GameWorld gameWorld, int levelNumber) {
        super(gameWorld, levelNumber);
        initLevel(levelNumber);
        resetPlayerPos();
        checkForNoPosInitMobs();
    }
    // Methods
    /**
     * {@inheritDoc}
     */
    @Override
    protected void initLevel(int levelNumber) {
        if (levelNumber == 1) {
            setBoundaries(new Vec2(0, 0), 200, -30, -300, 300);
            levelOneMobs();
            levelOneStructures();
            levelOnePositioning();
        } else if (levelNumber == 2) {
            setBoundaries(new Vec2(0, 0), 200, -800, -300, 300);
            setPlayerSpawn(new Vec2(0, 0));
            levelTwoMobs();
            levelTwoStructures();
            levelTwoPositioning();
        }
    }
    // Level 1
    private void levelOneStructures() {
        GroundFrame platformA = add("PlatformA", new HauntedFlatPlatform(gameWorld, -0.8f, -1, 3));
        Vec2 aPos = getPos("PlatformA");
        add("SlopeA", new HauntedSlopePlatform(gameWorld, aPos.x - platformA.getHalfDimensions().x - HauntedSlopePlatform.IMG.getDimensions().x*6, aPos.y + HauntedFlatPlatform.IMG_A.getHalfDimensions().x - HauntedSlopePlatform.IMG.getHalfDimensions().y*6, 6, Direction.UP));
        add("BackdropA", new HauntedBackdrop(gameWorld, getPos("SlopeA").x - get("SlopeA").getHalfDimensions().x - HauntedBackdrop.IMG.getHalfDimensions().x*3, getPos("SlopeA").y + HauntedBackdrop.IMG.getHalfDimensions().y - HauntedBackdrop.getPlatformHeight()*2 + 0.1f, 3));
        GroundFrame platformB = add("PlatformB", new HauntedFlatPlatform(gameWorld, 0,0, 10));
        GroundFrame pillarA = add("PillarA", new HauntedPillar(this, 0,0));
        GroundFrame pillarB = add("PillarB", new HauntedPillar(this, 0,0));
        GroundFrame pillarC = add("PillarC", new HauntedPillar(this, 0,0));
        // PosChanges
        platformB.setPosition(new Vec2(platformA.getOriginPos().x + platformA.getHalfDimensions().x + platformB.getHalfDimensions().x,-1));
        pillarA.setPosition(new Vec2(platformB.getPosition().x + platformB.getHalfDimensions().x + pillarA.getHalfDimensions().x, -1));
        pillarB.setPosition(new Vec2(pillarA.getPosition().x + pillarA.getHalfDimensions().x*2, pillarA.getPosition().y + pillarA.getHalfDimensions().y + pillarB.getHalfDimensions().y));
        pillarC.setPosition(new Vec2(pillarA.getPosition().x + pillarA.getHalfDimensions().x*2, -1));
        // Props
        platformB.addProp(new HauntedProp(gameWorld, HauntedProp.TREE_2_IMG), GroundFrame.randRangeInt(0, 9));
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
    private void levelTwoStructures() {
        float lastI = -1;
        for (int i = 1; i<= 50; i+=2) {
            add("PillarRight" + i, new HauntedPillar(this, -HauntedPillar.PILLAR_FACE_IMAGE.getDimensions().x*2,-HauntedPillar.PILLAR_FACE_IMAGE.getDimensions().y*i - PlayerWalker.HALF_Y));
            add("PillarLeft" + i, new HauntedPillar(this, 0,-HauntedPillar.PILLAR_FACE_IMAGE.getDimensions().y*i - PlayerWalker.HALF_Y));
            lastI = i+1;
        }
        lastI = -HauntedPillar.PILLAR_FACE_IMAGE.getDimensions().y*lastI - PlayerWalker.HALF_Y - HauntedPillar.PILLAR_FACE_IMAGE.getDimensions().y + HauntedPillar.PILLAR_FACE_IMAGE.getDimensions().y;
        add("HauntedPool", new HauntedPool(this, -HauntedPillar.PILLAR_FACE_IMAGE.getDimensions().x, lastI, HauntedPillar.PILLAR_FACE_IMAGE.getHalfDimensions().x*3, HauntedPillar.PILLAR_FACE_IMAGE.getDimensions().y));
        add("BackdropA", new HauntedBackdrop(gameWorld, HauntedPillar.PILLAR_FACE_IMAGE.getHalfDimensions().x + HauntedBackdrop.IMG.getHalfDimensions().x, lastI + HauntedBackdrop.getPlatformHeight() + 0.32f, 1));
        GroundFrame backdropB = add("BackdropB", new HauntedBackdrop(gameWorld, 0, lastI + HauntedBackdrop.getPlatformHeight() + 0.32f, 8));
        backdropB.setPosition(new Vec2(get("BackdropA").getPosition().x + get("BackdropA").getHalfDimensions().x + backdropB.getHalfDimensions().x, backdropB.getPosition().y));
        GroundFrame backdropC = add("BackdropC", new HauntedBackdrop(gameWorld, 0, 0 - HauntedBackdrop.IMG.getHalfDimensions().y, 8));
        backdropC.setPosition(new Vec2(get("BackdropA").getPosition().x + get("BackdropA").getHalfDimensions().x + backdropB.getHalfDimensions().x, backdropC.getPosition().y));
        add("HauntedElevatorA", new HauntedPillar(this, backdropB.getPosition().x + backdropB.getHalfDimensions().x + HauntedPillar.PILLAR_FACE_IMAGE.getHalfDimensions().x, backdropB.getPosition().y - backdropB.getHalfDimensions().y - HauntedPillar.PILLAR_FACE_IMAGE.getDimensions().y,  -HauntedPillar.PILLAR_FACE_IMAGE.getDimensions().y*3));
        // invisible boundaries Init
        StaticBody boundaryA = new StaticBody(gameWorld, new BoxShape(0.5f, HauntedBackdrop.IMG.getHalfDimensions().y*2));
        StaticBody boundaryB = new StaticBody(gameWorld, new BoxShape(0.5f, HauntedBackdrop.IMG.getHalfDimensions().y));
        StaticBody boundaryC = new StaticBody(gameWorld, new BoxShape(0.5f, HauntedBackdrop.IMG.getHalfDimensions().y));
        // PosChanges
        boundaryA.setPosition(new Vec2(backdropC.getPosition().x - backdropC.getHalfDimensions().x - 0.5f, backdropC.getPosition().y));
        boundaryB.setPosition(new Vec2(get("PillarRight1").getPosition().x - get("PillarRight1").getHalfDimensions().x - 0.5f, get("PillarRight1").getPosition().y + get("PillarRight1").getHalfDimensions().y*2));
        boundaryC.setPosition(new Vec2(get("PillarLeft1").getPosition().x + get("PillarLeft1").getHalfDimensions().x + 0.5f, get("PillarLeft1").getPosition().y + get("PillarLeft1").getHalfDimensions().y*2));
        // transparency
        boundaryA.setFillColor(new Color(0, 0, 0, 0));
        boundaryA.setLineColor(new Color(0, 0, 0, 0));
        boundaryB.setFillColor(new Color(0, 0, 0, 0));
        boundaryB.setLineColor(new Color(0, 0, 0, 0));
        boundaryC.setFillColor(new Color(0, 0, 0, 0));
        boundaryC.setLineColor(new Color(0, 0, 0, 0));
    }
    private void levelTwoMobs() {
        for (int i = 1; i <= 15; i++) {
            if ((int) (Math.random() * 101) % 2 == 0) {
                add(WalkerType.WIZARD, ++count);
            } else {
                add(WalkerType.WORM, ++count);
            }
        }
    }
    private void levelTwoPositioning() {
        for (int i = 1; i <= 5; i++) {
            setPos(i + "", get("BackdropC"));
        }
        for (int i = 6; i <= 15; i++) {
            setPos(i + "", get("BackdropB"));
        }
    }
    // Overrides
    /**
     * {@inheritDoc}
     */
    @Override
    protected void objectiveComplete() {
        Game.hauntedData.unlockLevel(getLevelNum());
    }
}
