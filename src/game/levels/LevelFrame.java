package game.levels;
// Imports

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.Game;
import game.body.staticstructs.ground.GroundFrame;
import game.body.staticstructs.ground.gothicCemetery.GothicFlatSkull;
import game.body.walkers.mobs.HuntressWalker;
import game.body.walkers.mobs.MobWalker;
import game.body.walkers.mobs.WizardWalker;
import game.body.walkers.mobs.WormWalker;
import game.core.GameWorld;
import game.core.console.Console;
import game.enums.Direction;
import game.enums.Walkers;
import org.jbox2d.common.Vec2;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * The `LevelFrame` class is an abstract class that serves as a base for all levels in the game.
 * It manages the level number, boundaries, and ground frames.
 */
// Class
public abstract class LevelFrame {
    // Fields
    private final GameWorld gameWorld;
    private HashMap<String, Vec2> boundaries;
    private final HashMap<String, GroundFrame> groundFrames;
    private ArrayList<MobWalker> mobs;
    private Vec2 centre;
    private Vec2 playerSpawn;
    private StepListener stepListener;


    /**
     * Constructor for LevelFrame.<br>
     * Initialises the game world and level number.
     *
     * @param gameWorld the game world
     */
    LevelFrame(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        initBoundaries();
        groundFrames = new HashMap<>();
        mobs = new ArrayList<>();

    }
    // Methods | Private
    /**
     * Initialises the boundaries of the level.<br>
     * Sets the boundaries to null initially.
     */
    private void initBoundaries() {
        boundaries = new HashMap<>();
        boundaries.put("Lower", null);
        boundaries.put("Upper", null);
        boundaries.put("Left", null);
        boundaries.put("Right", null);
    }
    /**
     * Sets the boundary of a given key.<br>
     * If the boundary value already exists, it will not be updated and a warning is given.
     *
     * @param key the key for the boundary ({@link String})
     * @param boundary the boundary value ({@link Vec2})
     */
    private void addBoundary(String key, Vec2 boundary) {
        if (boundaries.putIfAbsent(key, boundary) != null) {
            Console.warning("Boundary " + key + " does not exist.\nPlease set the boundary for the following keys:\n1.Lower\n2.Upper\n3.Left\n4.Right");
        }
    }

    // Methods | Protected
    /**
     * Initialises the {@link GroundFrame GroundFrames} of the level.<br>
     * This method is called in the constructor of the level.
     */
    protected abstract void initFrames();
    /**
     * Initialises the {@link game.body.walkers.mobs.MobWalker MobWalkers} of the level.<br>
     * This method is called in the constructor of the level.
     */
    protected abstract void initMobs();
    /**
     * Sets the values of boundaries and returns the centre of the level.<br>
     * @param centre the centre of the level ({@link Vec2})
     * @param upperY the upper Y boundary
     * @param lowerY the lower Y boundary
     * @param leftX the left X boundary
     * @param rightX the right X boundary
     */
    protected void setBoundaries(Vec2 centre, float upperY, float lowerY, float leftX, float rightX) {
        this.centre = centre;
        addBoundary("Upper", new Vec2(centre.x, centre.y + upperY));
        addBoundary("Lower", new Vec2(centre.x, centre.y + lowerY));
        addBoundary("Left", new Vec2(centre.x + leftX, centre.y));
        addBoundary("Right", new Vec2(centre.x + rightX, centre.y));
    }
    // Methods | Public
    /**
     * Adds a ground frame to the level.<br>
     * If a ground frame with the same name already exists, it will not be added.
     *
     * @param name the name of the ground frame
     * @param groundFrame the ground frame to add
     */
    public void addGroundFrame(String name, GroundFrame groundFrame) {
        if (groundFrames.putIfAbsent(name, groundFrame) != null) {
            Console.warning("Ground frame with name " + name + " already exists. Destroying Duplicate!");
            groundFrame.destroy();
            return;
        }
        groundFrame.setName(name);
        updatePosition(groundFrame);
    }
    // Methods | Protected
    /**
     * Returns the origin position in relation {@link #centre} of the added {@link GroundFrame} with the given name.
     *
     * @param name the name of the ground frame
     * @return a {@link Vec2} object representing the position of the {@link GroundFrame}
     */
    protected Vec2 getGroundFramePosition(String name) {
        return new Vec2(groundFrames.get(name).getOriginPos().x + centre.x, groundFrames.get(name).getOriginPos().y + centre.y);
    }
//    protected void appendGroundFrame(Direction direction, GroundFrame targetFrame, GroundFrame groundFrame) {
//        float half_Dimensions
//        if (groundFrame instanceof GothicFlatSkull) {}
//    }
//        if (direction.equals(Direction.LEFT)) {
//
//        }
//    }
    /**
     * Sets the position of the ground frame with the given name.<br>
     * The position is set in relation to the {@link #centre} of the level.
     *
     * @param name the name of the ground frame
     * @param position the new position of the ground frame
     */
    protected void setGroundPosition(String name, Vec2 position) {
        if (!groundFrames.containsKey(name)) {
            throw new NullPointerException(Console.exceptionMessage("Ground frame with key " + name + " does not exist in groundFrame HashMap."));
        }
        groundFrames.get(name).setPosition(new Vec2(centre.x + position.x, centre.y + position.y));
    }
    /**
     * Updates the position of the given ground frame.<br>
     * The position is set in relation to the {@link #centre} of the level.
     *
     * @param groundFrame the ground frame to update
     */
    protected void updatePosition(GroundFrame groundFrame) {
        groundFrame.setPosition(new Vec2(centre.x + groundFrame.getOriginPos().x, centre.y + groundFrame.getOriginPos().y));
    }

    /**
     * Returns the {@link GroundFrame} with the given name.
     *
     * @param name the name of the ground frame
     * @return the {@link GroundFrame} object
     */
    protected GroundFrame getGroundFrame(String name) {
        if (!groundFrames.containsKey(name)) {
            throw new NullPointerException(Console.exceptionMessage("Ground frame with key " + name + " does not exist in groundFrame HashMap."));
        }
        return groundFrames.get(name);
    }
    // Methods | Public
    /**
     * Removes the ground frame with the given name.<br>
     * If the key doesn't exist, an exception is thrown.
     *
     * @param name the name of the ground frame
     */
    public void removeGroundFrame(String name) {
        if (!groundFrames.containsKey(name)) {
            throw new NullPointerException(Console.exceptionMessage("Ground frame with key " + name + " does not exist in groundFrame HashMap."));
        }
        groundFrames.get(name).destroy();
        groundFrames.remove(name);
    }
    // Methods | Protected
    /**
     * Sets the player spawn position.
     *
     * @param playerSpawn the player spawn position
     */
    protected void setPlayerSpawn(Vec2 playerSpawn) {
        this.playerSpawn = new Vec2(centre.x + playerSpawn.x, centre.y + playerSpawn.y);
    }

    /**
     * Returns the {@link GameWorld} instance associated with this level.
     *
     * @return the {@link GameWorld} instance
     */
    protected GameWorld getGameWorld() {
        return gameWorld;
    }

    /**
     * Adds a mob to the level.<br>
     * The position is set in relation to the {@link #centre} of the level.
     *
     * @param mob the mob to add
     */
    protected void addMob(Walkers walkerType, Vec2 pos) {
        switch (walkerType) {
            case WORM -> {
                mobs.add(new WormWalker(gameWorld, new Vec2(centre.x + pos.x, centre.y + pos.y)));
            }
            case WIZARD -> {
                mobs.add(new WizardWalker(gameWorld, new Vec2(centre.x + pos.x, centre.y + pos.y)));
            }
            case Walkers.HUNTRESS -> {
                mobs.add(new HuntressWalker(gameWorld, new Vec2(centre.x + pos.x, centre.y + pos.y)));
            }
            default -> Console.warning("Walker type " + walkerType + " not recognised.");
        }
    }

    // Methods | Private | StepListener
    private void initStepListener() {
        stepListener = new StepListener() {
            @Override
            public void preStep(StepEvent event) {
                if (gameWorld.level != LevelFrame.this) {
                    stop();
                }
                Vec2 playerPos = gameWorld.getPlayer().getPosition();
                if (playerPos.y < boundaries.get("Lower").y || playerPos.y > boundaries.get("Upper").y || playerPos.x > boundaries.get("Right").x || playerPos.x < boundaries.get("Left").x) {
                    Game.gameView.isOutOfBounds = true;
                    gameWorld.getPlayer().die();
                }
            }

            @Override
            public void postStep(StepEvent event) {
                // Post-step logic
            }
        };
    }
    // Methods | Public
    /**
     * Sets the player's position to the spawn point.<br>
     * If the player spawn point is not set, a warning is given.
     */
    public void resetPlayerPos() {
        if (playerSpawn == null) {
            Console.warning("Player spawn position not set.");
            return;
        }
        gameWorld.getPlayer().setPosition(playerSpawn);
    }

    /**
     * Returns the centre of the level.<br>
     * @return the centre value ({@link Vec2})
     */
    public Vec2 getCentre() {
        return centre;
    }
    /**
     * Returns the player spawn position.<br>
     * @return the player spawn value ({@link Vec2})
     */
    public Vec2 getPlayerSpawn() {
        return playerSpawn;
    }

    /**
     * Returns the ArrayList of mobs in the level.
     * @return the ArrayList of mobs ({@link ArrayList<MobWalker>})
     */
    public ArrayList<MobWalker> getMobs() {
        return mobs;
    }

    /**
     * Returns the boundary vector of the given key
     * @param key can be {@code Lower}, {@code Upper}, {@code Left} or {@code Right}
     * @return the boundary value ({@link Vec2})
     */
    public Vec2 getBoundary(String key) {
        return boundaries.get(key);
    }

    /**
     * Starts the level.<br>
     * <p>Adds the level stepListener to the world.</p>
     */
    public void start() {
        if (stepListener == null) {
            initStepListener();
        }
        gameWorld.addStepListener(stepListener);
    }
    /**
     * Stops the level.<br>
     * <p>Removes the level stepListener from the world.</p>
     */
    public void stop() {
        if (stepListener == null) {
            Console.warning("Step listener not initialised.");
            return;
        }
        gameWorld.removeStepListener(stepListener);
    }
}