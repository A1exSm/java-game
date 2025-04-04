package game.levels;
// Imports

import game.body.staticstructs.ground.magicCliffs.Bridge;
import game.body.staticstructs.ground.magicCliffs.FloatingPlatform;
import game.body.staticstructs.ground.magicCliffs.MagicPlatform;
import game.body.walkers.mobs.WizardWalker;
import game.body.walkers.mobs.WormWalker;
import game.core.GameWorld;
import game.enums.PlatformType;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
public class MagicCliff extends LevelFrame {
    // Fields
    public static final int NUM_MOBS = 4;
    // Constructor
    public MagicCliff(GameWorld gameWorld) {
        super(gameWorld);
        setBoundaries(new Vec2(0,0), 20, -20, -300, 300);
        initMobs();
        initFrames();
        setPlayerSpawn(new Vec2(0, 2));
        resetPlayerPos();
    }
    // Methods | Private | setup
    @Override
    protected void initFrames() {
        GameWorld gameWorld = getGameWorld();
        addGroundFrame("A", new MagicPlatform(gameWorld, 0, -3, PlatformType.GROUND));
        addGroundFrame("B", new MagicPlatform(gameWorld, 48, -3, PlatformType.GROUND));
        addGroundFrame("C", new MagicPlatform(gameWorld, 76, -3, PlatformType.GROUND));
        addGroundFrame("D", new MagicPlatform(gameWorld, 116, -3, PlatformType.GROUND));
        addGroundFrame("platform", new FloatingPlatform(gameWorld, 1420, 5, "LARGE"));
        addGroundFrame("A_bridge_B", new Bridge(gameWorld, getGroundFrame("A"), getGroundFrame("B")));
        addGroundFrame("E_bridge_D", new Bridge(gameWorld, getGroundFrame("C"), getGroundFrame("D")));
        addGroundFrame("FloatingPlatformA", new FloatingPlatform(gameWorld, 140, 3, "LARGE"));
        addGroundFrame("FloatingPlatformB", new FloatingPlatform(gameWorld, 160, 5, "MEDIUM"));
        addGroundFrame("FloatingPlatformC", new FloatingPlatform(gameWorld, 175, 7, "MEDIUM"));
        addGroundFrame("FloatingPlatformD", new FloatingPlatform(gameWorld, 190, 9, "MEDIUM"));
        addGroundFrame("CliffPlatformA", new MagicPlatform(gameWorld, 210, 6, PlatformType.CLIFF_LIGHT));
        if (getGroundFrame("A") instanceof MagicPlatform a) {a.addTree();}
        if (getGroundFrame("D") instanceof MagicPlatform d) {d.addTree();}
    }
    @Override
    protected void initMobs() {
        GameWorld gameWorld = getGameWorld();
        float xCentre = getCentre().x;
        float yCentre = getCentre().y;
        new WizardWalker(gameWorld, new Vec2(xCentre + 10, yCentre + 4));
        new WizardWalker(gameWorld, new Vec2(xCentre + 50, yCentre + 2));
        new WizardWalker(gameWorld, new Vec2(xCentre + 110, yCentre + 4));
        new WormWalker(gameWorld, new Vec2(xCentre + 190 + 10, yCentre + 12));
    }
}
