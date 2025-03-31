package game.levels;
// Imports

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.Game;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import org.jbox2d.common.Vec2;

import java.awt.*;
import java.util.HashMap;

/**
 * The `LevelFrame` class is an abstract class that serves as a base for all levels in the game.
 * It manages the level number, boundaries, and ground frames.
 */
// Class
public abstract class LevelFrame {
    // Fields
    private static int levelCount = -1;
    private final GameWorld gameWorld;
    private final int levelNumber;
    private HashMap<String, Vec2> boundaries;
    protected HashMap<String, GroundFrame> groundFrames;
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
        levelNumber = ++levelCount;
        initBoundaries();
        groundFrames = new HashMap<>();

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

    // Methods | Protected
    /**
     * Adds a ground frame to the level.<br>
     * If a ground frame with the same name already exists, it will not be added.
     *
     * @param name the name of the ground frame
     * @param groundFrame the ground frame to add
     */
    protected void addGroundFrame(String name, GroundFrame groundFrame) {
        if (groundFrames.putIfAbsent(name, groundFrame) != null) {
            System.err.println("Warning: Ground frame with name " + name + " already exists. Destroying Duplicate!");
            groundFrame.destroy();
        }
    }

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
     * Sets the boundary of a given key.<br>
     * If the boundary value already exists, it will not be updated and a warning is given.
     *
     * @param key the key for the boundary ({@link String})
     * @param boundary the boundary value ({@link Vec2})
     */
    protected void setBoundary(String key, Vec2 boundary) {
        if (boundaries.putIfAbsent(key, boundary) != null) {
            System.err.println("Warning: Boundary " + key + " does not exist.\nPlease set the boundary for the following keys:\n1.Lower\n2.Upper\n3.Left\n4.Right");
        }
    }

    /**
     * Sets the centre of the level.<br>
     *
     * @param centre the centre value ({@link Vec2})
     */
    protected void setCentre(Vec2 centre) {
        this.centre = centre;
    }
    // Methods | Private | StepListener
    private void initStepListener() {
        stepListener = new StepListener() {
            @Override
            public void preStep(StepEvent event) {
                if (gameWorld.getPlayer().getCurrentLevel() != LevelFrame.this) {
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
            System.err.println("Warning: Player spawn position not set.");
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
     * Returns the boundary vector of the given key
     * @param key can be {@code Lower}, {@coe Upper}, {@code Left} or {@code Right}
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
        gameWorld.getPlayer().setCurrentLevel(this);
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
            System.err.println("Warning: Step listener not initialised.");
            return;
        }
        gameWorld.removeStepListener(stepListener);
    }
}