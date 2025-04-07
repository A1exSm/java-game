package game.levels;
// Imports

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
        addGroundFrame("PillarA", new GothicPillar(gameWorld, -GothicFlatDefault.IMG.getHalfDimensions().x - GothicPillar.IMG.getHalfDimensions().x, -1));
        addGroundFrame("FlatA", new GothicFlatDefault(gameWorld, 0, -1, 1));
        addGroundFrame("FlatB", new GothicFlatDefault(gameWorld, getGroundFramePosition("PillarA").x - getGroundFrame("PillarA").getHalfDimensions().x - GothicFlatDefault.IMG.getHalfDimensions().x*5, -1, 5));
        addGroundFrame("SpikesA", new GothicSpikes(gameWorld, GothicSlopeDefault.IMG.getHalfDimensions().x + GothicSpikes.IMG.getHalfDimensions().x*2, -1, 2));
        addGroundFrame("SlopeA", new GothicSlopeDefault(gameWorld, getGroundFramePosition("SpikesA").x + GothicSpikes.IMG.getHalfDimensions().x*2 + GothicSlopeDefault.IMG.getDimensions().x*3, -1, 3, Direction.DOWN));
//        addGroundFrame("FlatB", new GothicFlatSkull(gameWorld, getGroundFramePosition("FlatA").x + getGroundFrame("FlatA").getHalfDimensions().x + GothicFlatSkull.IMG.getHalfDimensions().x*5, -1, 5));
//        addGroundFrame("PillarA", new GothicPillar(gameWorld, getGroundFramePosition("FlatA").x - getGroundFrame("FlatA").getHalfDimensions().x - GothicPillar.IMG.getHalfDimensions().x, -1));
        addGroundFrame("propA", new Prop(gameWorld, getGroundFrame("FlatB"), Prop.TREE_2_IMG));
//        addGroundFrame("Slope", new GothicSlopeDefault(gameWorld, 15,-1 , 3, Direction.DOWN));
    }
    @Override
    protected void initMobs() {
        GameWorld gameWorld = getGameWorld();
        float xCentre = getCentre().x;
        float yCentre = getCentre().y;
        // Add mobs here
    }
}
