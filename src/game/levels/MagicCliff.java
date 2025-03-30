package game.levels;
// Imports

import game.body.staticstructs.ground.Bridge;
import game.body.staticstructs.ground.FloatingPlatform;
import game.body.staticstructs.ground.MediumPlatform;
import game.core.GameWorld;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
public class MagicCliff extends LevelFrame {
    // Fields
    private float xCentre;
    private float yCentre;
    // Constructor
    public MagicCliff(GameWorld gameWorld) {
        super(gameWorld);
        initBoundaries();
        initFrames();
        setPlayerSpawn(new Vec2(0, 2));
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
        addGroundFrame("A", new MediumPlatform(gameWorld, xCentre + 0, yCentre + -3));
        if (groundFrames.get("A") instanceof MediumPlatform A) {A.addTree();}
        addGroundFrame("B", new MediumPlatform(gameWorld, xCentre + 48, yCentre + -3));
        addGroundFrame("C", new MediumPlatform(gameWorld, xCentre + -200, yCentre + -3));
        addGroundFrame("D", new MediumPlatform(gameWorld, xCentre + 76, yCentre + -3));
        addGroundFrame("E", new MediumPlatform(gameWorld, xCentre + 116, yCentre + -3));
        addGroundFrame("platform", new FloatingPlatform(gameWorld, xCentre + 1420, yCentre + 5, "LARGE"));
        addGroundFrame("A_bridge_B", new Bridge(gameWorld, groundFrames.get("A"), groundFrames.get("B")));
        addGroundFrame("A_bridge_C", new Bridge(gameWorld, groundFrames.get("A"), groundFrames.get("C")));
        addGroundFrame("E_bridge_D", new Bridge(gameWorld, groundFrames.get("E"), groundFrames.get("D")));
    }
}
