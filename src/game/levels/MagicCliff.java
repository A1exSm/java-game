package game.levels;
// Imports

import game.Game;
import game.body.staticstructs.ground.magicCliffs.*;
import game.core.GameWorld;
import game.enums.PlatformType;
import game.enums.WalkerType;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
public class MagicCliff extends LevelFrame {
    // Fields
    public static final MobStore NUM_MOBS = new MobStore(2, new int[]{4, 6});

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
            levelOneMobs();
            levelOneStructures();
        } else if (levelNumber == 2) {
            levelTwoMobs();
            levelTwoStructures();
            levelTwoPositioning();
        }
    }

    private void levelOneStructures() {
        add("A", new MagicPlatform(gameWorld, 0, -3, PlatformType.GROUND));
        add("B", new MagicPlatform(gameWorld, 48, -3, PlatformType.GROUND));
        add("C", new MagicPlatform(gameWorld, 76, -3, PlatformType.GROUND));
        add("D", new MagicPlatform(gameWorld, 116, -3, PlatformType.GROUND));
        add("platform", new MagicFloatingPlatform(gameWorld, 1420, 5, "LARGE"));
        add("A_bridge_B", new MagicBridge(gameWorld, get("A"), get("B")));
        add("E_bridge_D", new MagicBridge(gameWorld, get("C"), get("D")));
        add("FloatingPlatformA", new MagicFloatingPlatform(gameWorld, 140, 3, "LARGE"));
        add("FloatingPlatformB", new MagicFloatingPlatform(gameWorld, 160, 5, "MEDIUM"));
        add("FloatingPlatformC", new MagicFloatingPlatform(gameWorld, 175, 7, "MEDIUM"));
        add("FloatingPlatformD", new MagicFloatingPlatform(gameWorld, 190, 9, "MEDIUM"));
        add("CliffPlatformA", new MagicPlatform(gameWorld, 210, 6, PlatformType.CLIFF_LIGHT));
        get("A").addProp(new MagicProp(gameWorld, MagicProp.TREE_IMG), -1);
        get("D").addProp(new MagicProp(gameWorld, MagicProp.TREE_IMG), -1);
    }
    private void levelOneMobs() {
        add(WalkerType.WIZARD, new Vec2(10, 4), ++count);
        add(WalkerType.WIZARD, new Vec2(50,2), ++ count);
        add(WalkerType.WIZARD, new Vec2(110, 4), ++count);
        add(WalkerType.WORM,new Vec2(190 + 10, 12), ++count);
    }
    private void levelTwoStructures() {
        add("LightA", new MagicCliffLight(gameWorld, 0,0 , 1));
        add("DarkA", new MagicCliffDark(gameWorld, getPos("LightA").x + get("LightA").getHalfDimensions().x + 20,0 , 4));
        add("FloatingA", new MagicFloatingRockMedium(gameWorld, getPos("DarkA").x + get("DarkA").getHalfDimensions().x + MagicFloatingRockMedium.IMG.getDimensions().x*2, MagicPlatformCliff.IMG.getHalfDimensions().y/2));
        add("FloatingB", new MagicFloatingRockMedium(gameWorld, getPos("FloatingA").x + get("FloatingA").getHalfDimensions().x + MagicFloatingRockMedium.IMG.getDimensions().x*2, MagicPlatformCliff.IMG.getHalfDimensions().y));
        add("DarkB", new MagicCliffDark(gameWorld, getPos("FloatingB").x + get("FloatingB").getHalfDimensions().x + get("DarkA").getHalfDimensions().x + MagicFloatingRockMedium.IMG.getDimensions().x*2,MagicPlatformCliff.IMG.getHalfDimensions().y * 1.5f, 8));
//        add("SmallRockA", new MagicFloatingRockSmall(gameWorld, 0,0));

    }
    private void levelTwoMobs() {
        add(WalkerType.WORM, new Vec2(-1000, -1000), ++count);
        add(WalkerType.WIZARD, new Vec2(-1000,-1000), ++count);
        add(WalkerType.WIZARD, new Vec2(-1000, -1000), ++count);
        add(WalkerType.WORM, new Vec2(-1000, -1000), ++count);
        add(WalkerType.WIZARD, new Vec2(-1000, -1000), ++count);
        add(WalkerType.WORM, new Vec2(-1000, -1000), ++count);
    }

    private void levelTwoPositioning() {
        setPos("1", get("DarkA"));
        setPos("2", get ("DarkA"));
        setPos("3", get("DarkA"));
        setPos("4", get("DarkB"));
        setPos("5", get("DarkB"));
        setPos("6", get("DarkB"));
    }
    @Override // not explicitly required but good practice apparently
    protected void objectiveComplete() {
        Game.magicData.unlockLevel(getLevelNum());
    }
}
