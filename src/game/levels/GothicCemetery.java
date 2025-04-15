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
        addGroundFrame("PillarA", new GothicPillar(gameWorld, -GothicFlatDefault.IMG.getHalfDimensions().x - GothicPillar.IMG.getHalfDimensions().x, -1));
        addGroundFrame("FlatA", new GothicFlatDefault(gameWorld, 0, -1, 1));
        addGroundFrame("FlatB", new GothicFlatDefault(gameWorld, getGroundFramePosition("PillarA").x - getGroundFrame("PillarA").getHalfDimensions().x - GothicFlatDefault.IMG.getHalfDimensions().x*5, -1, 5));
        addGroundFrame("SpikesA", new GothicSpikes(gameWorld, GothicSlopeDefault.IMG.getHalfDimensions().x + GothicSpikes.IMG.getHalfDimensions().x*2, -1, 2));
        addGroundFrame("SlopeA", new GothicSlopeDefault(gameWorld, getGroundFramePosition("SpikesA").x + GothicSpikes.IMG.getHalfDimensions().x*2 + GothicSlopeDefault.IMG.getDimensions().x*3, -1, 3, Direction.DOWN));
        getGroundFrame("FlatB").addProp(new GothicProp(gameWorld, GothicProp.STATUE_IMG), -1);
        getGroundFrame("FlatB").addProp(new GothicProp(gameWorld, GothicProp.TREE_1_IMG), -1);
        getGroundFrame("FlatB").addProp(new GothicProp(gameWorld, GothicProp.TREE_2_IMG), -1);
        getGroundFrame("FlatB").addProp(new GothicProp(gameWorld, GothicProp.TREE_3_IMG), -1);
        getGroundFrame("FlatB").addProp(new GothicProp(gameWorld, GothicProp.STONE_2_IMG), -1).flip();
        getGroundFrame("FlatB").removeProp(2);
        getGroundFrame("FlatB").addProp(new GothicProp(gameWorld, GothicProp.STONE_1_IMG), -1).flip();
    }
    private void levelOneMobs() {}
    private void levelTwoStructures() {}
    private void levelTwoMobs() {}
    @Override
    protected void objectiveComplete() {
        Game.gothicData.unlockLevel(getLevelNum());
    }
}
