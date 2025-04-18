package game.levels;
// Imports

import game.Game;
import game.body.staticstructs.ground.gothicCemetery.*;
import game.core.GameWorld;
import game.enums.Direction;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
public class GothicCemetery extends LevelFrame{
    // Fields
    public static final MobStore NUM_MOBS = new MobStore(2, new int[]{0, 0});
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
        add("FlatC", new GothicFlatDefault(gameWorld, getPos("SlopeA").x + GothicSlopeDefault.IMG.getDimensions().x*3 + GothicFlatDefault.IMG.getHalfDimensions().x*2, get("SlopeA").getPos().y - get("SlopeA").getHalfDimensions().y + 0.5f, 2));
        add("FlatD", new GothicFlatSkull(gameWorld, getEdge("FlatA", Direction.LEFT) - GothicFlatSkull.IMG.getHalfDimensions().x*30, -1, 30));
        get("FlatB").addProp(new GothicProp(gameWorld, GothicProp.STATUE_IMG), 1);
        get("FlatB").addProp(new GothicProp(gameWorld, GothicProp.STONE_1_IMG), 4).flip();
    }
    private void levelOneMobs() {}

    private void levelOnePositioning() {

    };
    // Level 2
    private void levelTwoStructures() {}

    private void levelTwoMobs() {}

    private void levelTwoPositioning() {
        add("FlatA", new GothicFlatSkull(gameWorld, -250, -6, 2));
        add("SpikesA", new GothicSpikes(gameWorld, getEdge("FlatA", Direction.LEFT) - GothicSpikes.IMG.getHalfDimensions().x*20, -6, 20));
        add("PillarA", new GothicPillar(gameWorld, getEdge("FlatA", Direction.RIGHT) + GothicPillar.IMG.getHalfDimensions().x, -6));
        add("FlatB", new GothicFlatDefault(gameWorld, getEdge("PillarA", Direction.RIGHT) + GothicFlatDefault.IMG.getHalfDimensions().x, -6, 1));
        add("PillarB", new GothicPillar(gameWorld, getEdge("FlatB", Direction.RIGHT) + GothicPillar.IMG.getHalfDimensions().x, -6));
        add("FlatC", new GothicFlatDefault(gameWorld, getEdge("PillarB", Direction.RIGHT) + GothicFlatDefault.IMG.getHalfDimensions().x, -6, 1));
        add("PillarC", new GothicPillar(gameWorld, getEdge("FlatC", Direction.RIGHT) + GothicPillar.IMG.getHalfDimensions().x, -6));
        add("SlopeA", new GothicSlopeSkull(gameWorld, getEdge("PillarC", Direction.RIGHT) + GothicSlopeSkull.IMG.getDimensions().x*3, -5.2f, 3, Direction.DOWN));
        add("FlatD", new GothicFlatDefault(gameWorld, getEdge("SlopeA", Direction.RIGHT) + GothicFlatDefault.IMG.getHalfDimensions().x*4, get("SlopeA").getPos().y - get("SlopeA").getHalfDimensions().y + 0.8f, 4));
        add("SlopeB", new GothicSlopeDefault(gameWorld, getEdge("FlatD", Direction.RIGHT) + GothicSlopeDefault.IMG.getDimensions().x*2, get("FlatD").getPos().y - get("FlatD").getHalfDimensions().y + 0.8f, 2, Direction.UP));
        add("FlatE", new GothicFlatDefault(gameWorld, getEdge("SlopeB", Direction.RIGHT) + GothicFlatDefault.IMG.getHalfDimensions().x*20, get("SlopeB").getPos().y + get("SlopeB").getHalfDimensions().y - 0.9f, 20));
    };
    // Methods
    @Override
    protected void objectiveComplete() {
        Game.gothicData.unlockLevel(getLevelNum());
    }
}
