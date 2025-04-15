package game.levels;
// Imports

import game.Game;
import game.body.staticstructs.ground.magicCliffs.Bridge;
import game.body.staticstructs.ground.magicCliffs.FloatingPlatform;
import game.body.staticstructs.ground.magicCliffs.MagicPlatform;
import game.core.GameWorld;
import game.enums.PlatformType;
import game.enums.Walkers;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
public class MagicCliff extends LevelFrame {
    // Fields
    public static final MobStore NUM_MOBS = new MobStore(2, new int[]{4, 0});

    // Constructor
    public MagicCliff(GameWorld gameWorld, int levelNumber) {
        super(gameWorld, levelNumber);
        setBoundaries(new Vec2(0,0), 20, -10, -300, 300);
        initLevel(levelNumber);
        setPlayerSpawn(new Vec2(0, 2));
        resetPlayerPos();
    }
    // Methods | Private | setup
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
    private void levelOneMobs() {
        addMob(Walkers.WIZARD, new Vec2(10, 4));
        addMob(Walkers.WIZARD, new Vec2(50,2));
        addMob(Walkers.WIZARD, new Vec2(110, 4));
        addMob(Walkers.WORM,new Vec2(190 + 10, 12));
    }
    private void levelTwoStructures() {}
    private void levelTwoMobs() {}
    @Override // not explicitly required but good practice apparently
    protected void objectiveComplete() {
        Game.magicData.unlockLevel(getLevelNum());
    }
}
