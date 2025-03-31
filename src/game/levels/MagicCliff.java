package game.levels;
// Imports

import game.body.staticstructs.ground.*;
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
    private float xCentre;
    private float yCentre;
    // Constructor
    public MagicCliff(GameWorld gameWorld) {
        super(gameWorld);
        initBoundaries();
        initMobs();
        initFrames();
        setPlayerSpawn(new Vec2(100, 2));
        resetPlayerPos();
    }
    // Methods | Private | setup
    private void initBoundaries() {
        setCentre(new Vec2(100, 0));
        xCentre = getCentre().x;
        yCentre = getCentre().y;
        setBoundary("Lower", new Vec2(xCentre + 0, yCentre + -20));
        setBoundary("Upper", new Vec2(xCentre + 0, yCentre + 20));
        setBoundary("Left", new Vec2(xCentre + -300, yCentre + 0));
        setBoundary("Right", new Vec2(xCentre + 300, yCentre + 0));
    }
    private void initFrames() {
        GameWorld gameWorld = getGameWorld();
        addGroundFrame("A", new Platform(gameWorld, xCentre + 0, yCentre + -3, PlatformType.GROUND));
        addGroundFrame("B", new Platform(gameWorld, xCentre + 48, yCentre + -3, PlatformType.GROUND));
        addGroundFrame("C", new Platform(gameWorld, xCentre + 76, yCentre + -3, PlatformType.GROUND));
        addGroundFrame("D", new Platform(gameWorld, xCentre + 116, yCentre + -3, PlatformType.GROUND));
        addGroundFrame("platform", new FloatingPlatform(gameWorld, xCentre + 1420, yCentre + 5, "LARGE"));
        addGroundFrame("A_bridge_B", new Bridge(gameWorld, groundFrames.get("A"), groundFrames.get("B")));
        addGroundFrame("E_bridge_D", new Bridge(gameWorld, groundFrames.get("C"), groundFrames.get("D")));
        addGroundFrame("FloatingPlatformA", new FloatingPlatform(gameWorld, xCentre + 140, yCentre + 3, "LARGE"));
        addGroundFrame("FloatingPlatformB", new FloatingPlatform(gameWorld, xCentre + 160, yCentre + 5, "MEDIUM"));
        addGroundFrame("FloatingPlatformC", new FloatingPlatform(gameWorld, xCentre + 175, yCentre + 7, "MEDIUM"));
        addGroundFrame("FloatingPlatformD", new FloatingPlatform(gameWorld, xCentre + 190, yCentre + 9, "MEDIUM"));
        addGroundFrame("CliffPlatformA", new Platform(gameWorld, xCentre + 210, yCentre + 6, PlatformType.CLIFF_LIGHT));
        if (groundFrames.get("A") instanceof Platform a) {a.addTree();}
        if (groundFrames.get("D") instanceof Platform d) {d.addTree();}
    }
    private void initMobs() {
        GameWorld gameWorld = getGameWorld();
        new WizardWalker(gameWorld, new Vec2(xCentre + 0, yCentre + 2));
        new WizardWalker(gameWorld, new Vec2(xCentre + 50, yCentre + 2));
        new WizardWalker(gameWorld, new Vec2(xCentre + 110, yCentre + 4));
        new WormWalker(gameWorld, new Vec2(xCentre + 190 + 10, yCentre + 12));
    }
}
