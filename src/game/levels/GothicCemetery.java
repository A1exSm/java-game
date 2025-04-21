package game.levels;
// Imports

import game.Game;
import game.body.staticstructs.ground.gothicCemetery.*;
import game.core.GameWorld;
import game.enums.Direction;
import game.enums.WalkerType;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
public class GothicCemetery extends LevelFrame{
    // Fields
    public static final MobStore NUM_MOBS = new MobStore(2, new int[]{10, 12});
    // Constructor
    public GothicCemetery(GameWorld gameWorld, int levelNumber) {
        super(gameWorld, levelNumber);
        setBoundaries(new Vec2(0, 0), 300, -300, -300, 300);
        initLevel(levelNumber);
        resetPlayerPos();
    }
    // Methods
    @Override
    protected void initLevel(int levelNumber) {
        if (levelNumber == 1) {
            levelOneMobs();
            levelOneStructures();
            levelOnePositioning();
            setPlayerSpawn(new Vec2(39.104317f,-10.645f));
            gameWorld.getPlayer().setDirection(Direction.LEFT);
        } else if (levelNumber == 2) {
            levelTwoMobs();
            levelTwoStructures();
            levelTwoPositioning();
            setPlayerSpawn(new Vec2(-250, 2));
        }
    }
    // Level 1
    private void levelOneStructures() {
        add("PillarA", new GothicPillar(gameWorld, -GothicFlatDefault.IMG.getHalfDimensions().x - GothicPillar.IMG.getHalfDimensions().x, -1));
        add("FlatA", new GothicFlatDefault(gameWorld, 0, -1, 1));
        add("FlatB", new GothicFlatDefault(gameWorld, getPos("PillarA").x - get("PillarA").getHalfDimensions().x - GothicFlatDefault.IMG.getHalfDimensions().x*5, -1, 5));
        add("SpikesA", new GothicSpikes(gameWorld, GothicSlopeDefault.IMG.getHalfDimensions().x + GothicSpikes.IMG.getHalfDimensions().x*2, -1, 2));
        add("SlopeA", new GothicSlopeDefault(gameWorld, getPos("SpikesA").x + GothicSpikes.IMG.getHalfDimensions().x*2 + GothicSlopeDefault.IMG.getDimensions().x*3, -1, 3, Direction.DOWN));
        add("SpawnPlatform", new GothicFlatDefault(gameWorld, getPos("SlopeA").x + GothicSlopeDefault.IMG.getDimensions().x*3 + GothicFlatDefault.IMG.getHalfDimensions().x*2, get("SlopeA").getPos().y - get("SlopeA").getHalfDimensions().y + 0.5f, 2));
        add("OutOfBoundsSpikes", new GothicSpikes(gameWorld, getPos("SpawnPlatform").x + GothicFlatDefault.IMG.getHalfDimensions().x*2 + GothicSpikes.IMG.getHalfDimensions().x*80, get("SpawnPlatform").getPos().y - get("SpawnPlatform").getHalfDimensions().y + 0.5f, 80));
        add("SpikeC", new GothicSpikes(gameWorld, getEdge("FlatB", Direction.LEFT) - GothicSpikes.IMG.getHalfDimensions().x*3, -1, 3));
        add("FlatD", new GothicFlatSkull(gameWorld, getEdge("SpikeC", Direction.LEFT) - GothicFlatSkull.IMG.getHalfDimensions().x*1, -1, 1));
        add("SpikeD", new GothicSpikes(gameWorld, getEdge("FlatD", Direction.LEFT) - GothicSpikes.IMG.getHalfDimensions().x*3, -1, 3));
        add("FlatE", new GothicFlatDefault(gameWorld, getEdge("SpikeD", Direction.LEFT) - GothicFlatDefault.IMG.getHalfDimensions().x*8, -1, 8));
        add("SpikeE", new GothicSpikes(gameWorld, getEdge("FlatE", Direction.LEFT) - GothicSpikes.IMG.getHalfDimensions().x*3, -1, 3));
        add("FlatF", new GothicFlatDefault(gameWorld, getEdge("SpikeE", Direction.LEFT) - GothicFlatDefault.IMG.getHalfDimensions().x*20, -1, 20));

        get("FlatB").addProp(new GothicProp(gameWorld, GothicProp.STATUE_IMG), 1);
        get("FlatB").addProp(new GothicProp(gameWorld, GothicProp.STONE_1_IMG), 4).flip();
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
        setPos("1", get("FlatB"));
        setPos("2", get("FlatB"));
        setPos("3", get("FlatB"));
        setPos("4", get("FlatF"));
        setPos("5", get("FlatF"));
        setPos("6", get("FlatE"));
        setPos("7", get("FlatE"));
        setPos("8", get("FlatF"));
        setPos("9", get("FlatF"));
        setPos("10", get("FlatF"));
    };
    // Level 2
    private void levelTwoStructures() {
        add("FlatA", new GothicFlatSkull(gameWorld, -250, -6, 2));
        add("SpikesA", new GothicSpikes(gameWorld, getEdge("FlatA", Direction.LEFT) - GothicSpikes.IMG.getHalfDimensions().x*20, -6, 20));
        add("PillarA", new GothicPillar(gameWorld, getEdge("FlatA", Direction.RIGHT) + GothicPillar.IMG.getHalfDimensions().x, -6));
        add("FlatB", new GothicFlatSkull(gameWorld, getEdge("PillarA", Direction.RIGHT) + GothicFlatSkull.IMG.getHalfDimensions().x, -6, 1));
        add("PillarB", new GothicPillar(gameWorld, getEdge("FlatB", Direction.RIGHT) + GothicPillar.IMG.getHalfDimensions().x, -6));
        add("FlatC", new GothicFlatSkull(gameWorld, getEdge("PillarB", Direction.RIGHT) + GothicFlatSkull.IMG.getHalfDimensions().x, -6, 1));
        add("PillarC", new GothicPillar(gameWorld, getEdge("FlatC", Direction.RIGHT) + GothicPillar.IMG.getHalfDimensions().x, -6));
        add("SlopeA", new GothicSlopeSkull(gameWorld, getEdge("PillarC", Direction.RIGHT) + GothicSlopeSkull.IMG.getDimensions().x*3, -5.2f, 3, Direction.DOWN));
        add("FlatD", new GothicFlatDefault(gameWorld, getEdge("SlopeA", Direction.RIGHT) + GothicFlatDefault.IMG.getHalfDimensions().x*4, get("SlopeA").getPos().y - get("SlopeA").getHalfDimensions().y + 0.8f, 4));
        add("FlatE", new GothicFlatDefault(gameWorld, getEdge("FlatD", Direction.RIGHT) + GothicFlatDefault.IMG.getHalfDimensions().x*3, -8, 2));
        add("FlatF", new GothicFlatDefault(gameWorld, getEdge("FlatE", Direction.RIGHT) + GothicFlatDefault.IMG.getHalfDimensions().x*11, -6, 10));
        add("SpikesB", new GothicSpikes(gameWorld, getEdge("FlatF", Direction.RIGHT) + GothicSpikes.IMG.getHalfDimensions().x*10, -6, 10));
        add("FlatG", new GothicFlatDefault(gameWorld, getEdge("FlatF", Direction.RIGHT) + GothicFlatDefault.IMG.getHalfDimensions().x*2, -2, 1));
        add("FlatH", new GothicFlatDefault(gameWorld, getEdge("FlatG", Direction.RIGHT) + GothicFlatDefault.IMG.getHalfDimensions().x*4, -0.5f, 3));
        add("FlatI", new GothicFlatDefault(gameWorld, getEdge("FlatH", Direction.RIGHT) + GothicFlatDefault.IMG.getHalfDimensions().x*3, -2, 1));
        add("FlatJ", new GothicFlatDefault(gameWorld, getEdge("SpikesB", Direction.RIGHT) + GothicFlatDefault.IMG.getHalfDimensions().x*15, -6, 15));
//        add("SlopeB", new GothicSlopeDefault(gameWorld, getEdge("FlatD", Direction.RIGHT) + GothicSlopeDefault.IMG.getDimensions().x*2, get("FlatD").getPos().y - get("FlatD").getHalfDimensions().y + 0.8f, 2, Direction.UP));
//        add("FlatE", new GothicFlatDefault(gameWorld, getEdge("SlopeB", Direction.RIGHT) + GothicFlatDefault.IMG.getHalfDimensions().x*20, get("SlopeB").getPos().y + get("SlopeB").getHalfDimensions().y - 0.9f, 20));
    }

    private void levelTwoMobs() {
        add(WalkerType.WIZARD, ++count);
        add(WalkerType.WIZARD, ++count);
        add(WalkerType.WORM, ++count);
        add(WalkerType.WORM, ++count);
        add(WalkerType.WIZARD, ++count);
        add(WalkerType.WORM, ++count);
        add(WalkerType.WIZARD, ++count);
        add(WalkerType.WIZARD, ++count);
        add(WalkerType.WIZARD, ++count);
        add(WalkerType.WORM, ++count);
        add(WalkerType.WORM, ++count);
        add(WalkerType.WORM, ++count);
    }

    private void levelTwoPositioning() {
        setPos("1", get("FlatD"));
        setPos("2", get("FlatD"));
        setPos("3", get("FlatD"));
        setPos("4", get("FlatF"));
        setPos("5", get("FlatF"));
        setPos("6", get("FlatF"));
        setPos("7", get("FlatJ"));
        setPos("8", get("FlatJ"));
        setPos("9", get("FlatJ"));
        setPos("10", get("FlatJ"));
        setPos("11", get("FlatJ"));
        setPos("12", get("FlatJ"));

    };
    // Methods
    @Override
    protected void objectiveComplete() {
        Game.gothicData.unlockLevel(getLevelNum());
    }
}
