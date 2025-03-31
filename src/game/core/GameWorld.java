package game.core;
// Imports
import game.Game;
import game.body.items.Inventory;
import game.body.staticstructs.ground.*;
import game.body.staticstructs.Trampoline;
import game.body.walkers.PlayerWalker;
import game.body.walkers.mobs.HuntressWalker;
import game.body.walkers.mobs.MobWalker;
import game.body.walkers.mobs.WizardWalker;
import city.cs.engine.*;
import game.body.walkers.mobs.WormWalker;
import game.enums.State;
import game.enums.WalkerBehaviour;
import game.enums.Walkers;
import game.levels.MagicCliff;
import org.jbox2d.common.Vec2;
import game.animation.*;
import java.awt.*;
import java.util.ArrayList;

// Class
/**
 * The `GameWorld` class extends `World` and represents the game world.
 * It handles the initialisation of the player, mobs, bodies, and various game elements.
 */
public class GameWorld extends World {
    // Fields
    private static Game game;
    private final PlayerWalker player;
    private static final ArrayList<MobWalker> mobs = new ArrayList<>();
    public static final Inventory playerInventory = new Inventory(4);
    private boolean toggleMobsPassive = false;

    // Constructor
    /**
     * Constructs a `GameWorld` with the specified game.
     *
     * @param game the game instance
     */
    public GameWorld(Game game, String level) {
        super();
        GameWorld.game = game;
        player = new PlayerWalker(this);
        new WalkerAnimationFrames(State.RUN, Walkers.PLAYER);
        switch (level) {
            case "MagicCliff" -> {
                MagicCliff magicCliff = new MagicCliff(this);
                magicCliff.start();
            }
        }
//        populate();
        // end of constructor start of a new world :)
        start();
    }

    // Population methods
    /**
     * Creates instances of {@link TempGround} below the typical ground level.<br><br>
     * Each instance has an image of the ground that has an offset
     * which causes the image to be placed inline with ground level.<br><br>
     * this method is typically only called by {@link #populate()}.
     *
     * @param start the starting x-coordinate
     * @param end the ending x-coordinate
     */
    private void groundImg2(int start, int end) {
        for (float i = start+6.6f; i < end; i+=12) {
            TempGround temp = new TempGround(this, new Vec2(6, 2.5f), new Vec2(i, -7.5f));
            temp.addImage(new BodyImage("data/ground_tiles/tile_0x2.png",5f)).setOffset(new Vec2(0, 5f));
        }
    }
    /**
     * Populates the game world with various bodies.
     */
    private void populate() {
        groundImg2(-500, 500);
        new TempGround(this, new Vec2(500, 2f), new Vec2(0, -2f)); // changed from 2.5 to 2 so that fillColor is not visible
        playGround();
        areaOne();
    }
    /**
     * Populates the playground area with ground and platforms, generally used to test new features.
     */
    private void playGround() {
        float offset = 100f;
        new TempGround(this, new Vec2(2, 2f), new Vec2(-5+offset, 2f));
//        new Platform(this, new Vec2(20+offset, 4));
//        new Platform(this, new Vec2(27+offset, 7));
        new Trampoline(this, new Vec2(-20+offset, 1));
//        initMobs();
//        toggleMobsPassive();

    }

    /**
     * Populates the area one with various bodies.<br><br>
     * AreaOne is another testing area, like {@link #playGround()}, albeit more complex.<br>
     * For example, sheer walls cannot be scaled, as they have low friction values.
     *
     */
    private void areaOne() {
        ArrayList<Body> areaOne= new ArrayList<>();
        TempGround temp1 = new TempGround(this, new Vec2(20,2.0f), new Vec2(-25.0f, 200f));
        StaticBody temp2 = new StaticBody(this, new PolygonShape(0,0, 0,-4, -10,-4, -10,10));
        temp2.setPosition(new Vec2(-45.0f, 202f));
        TempGround temp3 = sheerWall(new Vec2(-3.9f, 213f), 15.0f);
        TempGround temp6 = sheerWall(new Vec2(-56.1f, 218f), 20.0f);
        TempGround temp4 = new TempGround(this, new Vec2(20, 1.0f), new Vec2(-25.0f, 210f));
        DynamicBody box = new DynamicBody(this, new BoxShape(0.5f,0.5f));
        SolidFixture boxFixture = new SolidFixture(box, new BoxShape(2f, 2f));
        box.setPosition(new Vec2(-25.0f, 212f));
        TempGround temp5 = new TempGround(this, new Vec2(20, 1.0f), new Vec2(-35.0f, 219f));
        areaOne.add(temp1);
        areaOne.add(temp2);
        areaOne.add(temp3);
        areaOne.add(temp4);
        areaOne.add(temp5);
        areaOne.add(temp6);
        for (Body body : areaOne) {
            Color col =  new Color(120, 110, 100);
            body.setFillColor(col);
            body.setLineColor(col);
        }
        box.setFillColor(new Color(85, 72, 63));
        box.setName("box");
        temp2.setName("Stairs");
        player.setPosition(new Vec2(-28.0f, 212.0f));
        player.setPosition(new Vec2(0, 2));
    }
    /**
     * Creates a sheer wall at the specified position with the given height.<br><br>
     * Sheer walls have protrusions ({@link SolidFixture SolidFixtures}) with a low friction value;
     * which prevents scaling.
     *
     * @param pos the position of the sheer wall
     * @param halfHeight half the height of the sheer wall
     * @return the created {@link TempGround} object
     */
    private TempGround sheerWall(Vec2 pos, float halfHeight) {
        TempGround temp3 = new TempGround(this, new Vec2(1,4.0f), pos);
        SolidFixture temp3Fixture = new SolidFixture(temp3, new BoxShape(1.1f,  halfHeight));
        temp3Fixture.setFriction(0.00005f);
        return temp3;
    }

    // Store
    /**
     * Initializes the mobs in the game world.
     */
    private void initMobs() {
        new WizardWalker(this, new Vec2(-25, 202));
        new WizardWalker(this, new Vec2(70,2));
        new WizardWalker(this, new Vec2(127, 8.9f));
        new WizardWalker(this, new Vec2(-30,2));
        new WizardWalker(this, new Vec2(-80,2));
        new WizardWalker(this, new Vec2(-110,2));
        new WormWalker(this, new Vec2(20,2));
//        new WormWalker(this, new Vec2(-25,222));
        new HuntressWalker(this, new Vec2(-25, 222));
    }


    // External Getters & Setters
    /**
     * Toggles the player's attack state.
     */
    public void togglePlayerAttack() {
        player.toggleOffAttack();
    }
    /**
     * Gets the player walker instance.
     *
     * @return the player walker
     */
    public PlayerWalker getPlayer() {
        return player;
    }
    /**
     * Toggles the game's pause state.<br><br>
     * Since {@link Game#togglePause()} is not static, it can be accessed through 'Game.gameWorld.togglePause()'.
     */
    public void togglePause() {
        game.togglePause();
    }
    /**
     * Uses an item from the player's inventory at the specified index.<br>
     * index corresponds to the item's visual position in the inventory. starting from the left.
     *
     * @param index the index of the item to use
     */
    public static void useInventoryItem(int index) {
        playerInventory.use(index);
    }
    /**
     * Drops an {@link game.body.items.InventoryItem InventoryItem} from the player's inventory at the specified index.
     *
     * @param index the index of the item to drop
     */
    public static void dropInventoryItem(int index) {
        playerInventory.drop(index);
    }

    //Mobology
    /**
     * Gets the list of alive {@link MobWalker MobWalkers}.
     *
     * @return the list of alive Mobs
     */
    public static ArrayList<MobWalker> getMobs() {
        ArrayList<MobWalker> aliveMobs = new ArrayList<>();
        for (MobWalker mob : mobs) {
            if(!mob.isDead()) aliveMobs.add(mob);
        }
        return aliveMobs;
    }

    public static void addMob(MobWalker mob) {
        mobs.add(mob);
    }
    /**
     * Removes a mob from the game world.<br>
     * Possibly deprecated, as mobs are ignored by {@link #getMobs()} when they die.
     *
     * @param mob the mob to remove
     */
    public static void removeMob(MobWalker mob) {
        mobs.remove(mob);
    }
    /**
     * Finds a {@link WormWalker} by name.
     *
     * @param name the name of the {@link WormWalker}
     * @return the {@link WormWalker} with the specified name, or null if not found
     */
    public static WormWalker nameToWorm(String name) {
        for (MobWalker mob : mobs) {
            if (mob.getWalkerType().equals(Walkers.WORM) && mob.getName().equals(name)) { // ensures correct type & name
                return (WormWalker) mob; // casts the mob to a WormWalker
            }
        }
        System.err.println("WormWalker with name: "+ name + " not found! Returning null.");
        return null;
    }
    /**
     * Finds a {@link WizardWalker} by name.
     *
     * @param name the name of the {@link WizardWalker}
     * @return the {@link WizardWalker} with the specified name, or null if not found
     */
    public static WizardWalker nameToWizard(String name) {
        for (MobWalker mob : mobs) {
            if (mob.getWalkerType().equals(Walkers.WIZARD) && mob.getName().equals(name)) { // ensures correct type & name
                return (WizardWalker) mob; // casts the mob to a WormWalker
            }
        }
        System.err.println("WizardWalker with name: "+ name + " not found! Returning null.");
        return null;
    }
    /**
     * Toggles the state of all mobs to passive.
     */
    public void toggleMobsPassive() { // only meant to be called during setup
        toggleMobsPassive = !toggleMobsPassive;
        for (MobWalker mob : mobs) {
            mob.setBehaviour(WalkerBehaviour.PASSIVE);
        }
    }

}
