package game.levels;
// Imports

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.Game;
import game.body.staticstructs.ground.GroundFrame;
import game.body.walkers.PlayerWalker;
import game.body.walkers.WalkerFrame;
import game.body.walkers.mobs.HuntressWalker;
import game.body.walkers.mobs.MobWalker;
import game.body.walkers.mobs.WizardWalker;
import game.body.walkers.mobs.WormWalker;
import game.core.GameWorld;
import game.core.console.Console;
import game.enums.Direction;
import game.enums.WalkerType;
import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The `LevelFrame` class is an abstract class that serves as a base for all levels in the game.
 * It manages the level number, boundaries, and ground frames.
 */
// Class
public abstract class LevelFrame {
    // Fields
    protected final GameWorld gameWorld;
    private HashMap<String, Vec2> boundaries;
    private final HashMap<String, GroundFrame> groundFrames;
    private ArrayList<MobWalker> mobs;
    private ArrayList<MobWalker> noPosInitMobs = new ArrayList<>();
    private Vec2 centre;
    private Vec2 playerSpawn;
    private StepListener stepListener;
    private final int levelNum;
    protected int count = 0;
    /**
     * Constructor for LevelFrame.<br>
     * Initialises the game world and level number.
     *
     * @param gameWorld the game world
     */
    LevelFrame(GameWorld gameWorld, int levelNum) {
        this.gameWorld = gameWorld;
        initBoundaries();
        groundFrames = new HashMap<>();
        mobs = new ArrayList<>();
        this.levelNum = levelNum;

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
     * Initialises {@link game.enums.Environments Environment} level.<br>
     * This method is called in the constructor of the level.
     * @param levelNumber the level number
     */
    protected abstract void initLevel(int levelNumber);

    /**
     * Called when the objective is complete.<br>
     * This method is called in {@link #checkForMobsDead()}.
     */
    protected abstract void objectiveComplete();

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
    public GroundFrame add(String name, GroundFrame groundFrame) {
        if (groundFrames.putIfAbsent(name, groundFrame) != null) {
            Console.warning("Ground frame with name " + name + " already exists. Destroying Duplicate!");
            groundFrame.destroy();
            return groundFrame;
        }
        groundFrame.setName(name);
        updatePosition(groundFrame);
        return groundFrame;
    }
    // Methods | Protected
    /**
     * Returns the origin position in relation {@link #centre} of the added {@link GroundFrame} with the given name.
     *
     * @param name the name of the ground frame
     * @return a {@link Vec2} object representing the position of the {@link GroundFrame}
     */
    protected Vec2 getPos(String name) {
        return new Vec2(groundFrames.get(name).getOriginPos().x + centre.x, groundFrames.get(name).getOriginPos().y + centre.y);
    }
    /**
     * Returns the edge of the given ground frame in the given direction.
     * @see Direction
     * @see GroundFrame
     * @param name the name of the ground frame
     * @param side the direction of the edge
     * @return the edge value
     */
    protected float getEdge(String name, Direction side) {
        if (side == Direction.RIGHT) {
            return get(name).getPosition().x + get(name).getHalfDimensions().x;
        } else if (side == Direction.LEFT) {
            return get(name).getPosition().x - get(name).getHalfDimensions().x;
        } else {
            Console.errorTraceCustom("Invalid direction " + side + " for getEdge() method.", 3);
            return 0;
        }
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
    protected GroundFrame get(String name) {
        if (!groundFrames.containsKey(name)) {
            throw new NullPointerException(Console.exceptionMessage("Ground frame with key " + name + " does not exist in groundFrame HashMap."));
        }
        return groundFrames.get(name);
    }
    /**
     * Returns the {@link MobWalker} with the given identifier.
     *
     * @param identifier the identifier of the mob
     * @return the {@link MobWalker} object
     */
    private MobWalker getMob(String identifier) {
        for (MobWalker mob : mobs) {
            if (mob.getName().equals(mob.getWalkerType().name().toLowerCase() + identifier)) {
                return mob;
            }
        }
        Console.warning("No mob with identifier " + identifier + " found.");
        return null;
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
     * Sets the player spawn position without centering it.
     *
     * @param playerSpawn the player spawn position
     */
    protected void setPlayerSpawnNoCentre(Vec2 playerSpawn) {
        this.playerSpawn.set(playerSpawn);
    }

    /**
     * Returns the {@link GameWorld} instance associated with this level.
     *
     * @return the {@link GameWorld} instance
     */
    public GameWorld getGameWorld() {
        return gameWorld;
    }

    private MobWalker addMob(WalkerType walkerType, Vec2 pos, String identifier) {
        MobWalker mob;
        switch (walkerType) {
            case WORM -> {
                mob = new WormWalker(gameWorld, pos);
                mobs.add(mob);
            }
            case WIZARD -> {
                mob = new WizardWalker(gameWorld, pos);
                mobs.add(mob);
            }
            case WalkerType.HUNTRESS -> {
                mob = new HuntressWalker(gameWorld, pos);
                mobs.add(mob);
            }
            default -> {
                Console.errorTraceCustom("Walker type " + walkerType + " not recognised, returning null!", 3);
                mob = null;
            }
        }
        if (mob != null) {mob.updateName(identifier);}
        return mob;
    }

    /**
     * Adds a mob to the level.<br>
     * The position is set in relation to the {@link #centre} of the level.
     *
     * @param walkerType the type of mob to add
     * @param pos the position of the mob
     * @param identifier the identifier of the mob
     */
    protected MobWalker add(WalkerType walkerType, Vec2 pos, String identifier) {
        return addMob(walkerType, new Vec2(centre.x + pos.x, centre.y + pos.y), identifier);
    }
    /**
     * Sets the position of a {@link MobWalker} with the given identifier to a random place on top of a {@link GroundFrame}.<br>
     * @see GroundFrame#randRangeFloat(float, float)
     *
     * @param identifier the identifier of the mob
     * @param platform the ground frame to set the position on
     */
    protected void setPos(String identifier, GroundFrame platform) {
        MobWalker walker = getMob(identifier);
        if (walker == null) {
            Console.errorTraceCustom("Mob with name " + identifier + " not found!", 3);
            return;
        }
        float pPosX = platform.getPosition().x;
        float x = GroundFrame.randRangeFloat(pPosX - platform.getHalfDimensions().x + walker.getHalfDimensions().x, pPosX + platform.getHalfDimensions().x - walker.getHalfDimensions().x);
        walker.setPosition(new Vec2(x, platform.getYTop() + walker.getHalfDimensions().y));
    }

    /**
     * Adds a child of {@link MobWalker} to the level.<br>
     * The position is set in relation to the {@link #centre} of the level.
     *
     * @param walkerType the type of mob to add
     * @param pos the position of the mob
     * @param identifier the identifier of the mob
     */
    protected MobWalker add(WalkerType walkerType, Vec2 pos, int identifier) {
        return addMob(walkerType, new Vec2(centre.x + pos.x, centre.y + pos.y), String.valueOf(identifier));
    }

    protected void checkForNoPosInitMobs() {
        if (noPosInitMobs.isEmpty()) {
            return;
        }
        for (MobWalker mob : noPosInitMobs) {
            if (mob.getPosition().x <= -1000 && mob.getPosition().y <= -1000) {
                Console.warning("Mob " + mob.getName() + " not initialised, removing from level.");
                mob.destroy();
            }
        }
    }

    protected MobWalker add(WalkerType walkerType, int identifier) {
        MobWalker walker = addMob(walkerType, new Vec2(-1000 - count, -1000 - count), String.valueOf(identifier));
        noPosInitMobs.add(walker);
        return walker;
    }

    // Methods | Private | StepListener
    private void initStepListener() {
        stepListener = new StepListener() {
            @Override
            public void preStep(StepEvent event) {
                if (gameWorld.getLevel() != LevelFrame.this) {
                    stop();
                }
                isOutOfBounds(gameWorld.getPlayer());
                checkForMobsDead();
            }

            @Override
            public void postStep(StepEvent event) {
                // Post-step logic
            }
        };
    }
    // Methods | Public
    /**
     * Checks if the given walker is out of bounds.<br>
     * If the walker is out of bounds, it will fall to death.
     *
     * @param walkerFrame the walker to check
     * @return true if the walker is out of bounds, false otherwise
     */
    public boolean isOutOfBounds(WalkerFrame walkerFrame) {
        Vec2 pos = walkerFrame.getPosition();
        if (pos.y < boundaries.get("Lower").y || pos.y > boundaries.get("Upper").y || pos.x > boundaries.get("Right").x || pos.x < boundaries.get("Left").x) {
            if (walkerFrame instanceof PlayerWalker player) {
                player.fallToDeath();
                return true;
            }
            return true;
        }
        return false;
    }
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
     * Returns the player's spawn position.<br>
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
    /**
     * Checks if all mobs are dead.<br>
     * If all mobs are dead, the game is over.
     */
    public void checkForMobsDead() {
        if (mobs.isEmpty()) {
            return;
        }
        boolean gameOver = true;
        for (MobWalker mob : mobs) {
            if (mob == null || !mob.isDead()) {
                gameOver = false;
                break;
            }
        }
        if (gameOver) {
            Game.gameView.gameWon("LEVEL CLEARED: Eliminated All Enemies!");
            objectiveComplete();
            stop();
        }
    }
    /**
     * Returns the level number.
     *
     * @return the level number
     */
    protected int getLevelNum() {
        return levelNum;
    }
}