package game.body.walkers;
// Imports
import city.cs.engine.*;
import game.Game;
import game.core.GameWorld;
import game.core.console.Console;
import game.enums.Direction;
import game.enums.State;
import game.enums.WalkerType;
import game.utils.sound.SoundFX;
import org.jbox2d.common.Vec2;
import java.util.ArrayList;
/**
 * An Abstract class used for all walkers in the game.
 * The WalkerFrame class is a class which extends the {@link Walker} class and is used to create the base for all walkers in the game.
 * All Walkers in the game will extend this class and will have the same basic functionality at their core, such as the ability to attack, take damage, die, etc.
 * This class is abstract and cannot be instantiated as it is only used as a base class for all walkers in the game.
 * @see game.body.walkers.mobs.MobWalker
 * @see game.body.walkers.PlayerWalker
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 13-02-2025
*/
// Class
public abstract class WalkerFrame extends Walker {
    // Fields
    /**
     * The origin x position of the walker in the world.
     */
    public final float ORIGIN_X;
    /**
     * The origin y position of the walker in the world.
     */
    public final float ORIGIN_Y;
    private final Shape SHAPE;
    private boolean attacking = false;
    private boolean hit = false;
    private State state = State.IDLE;
    private Direction direction = Direction.RIGHT;
    private final GameWorld gameWorld;
    private boolean dead = false;
    private final WalkerType WALKER_TYPE; // for now, walkers cannot transform into another
    private boolean isCooldown = false;
    private final ArrayList<Shape> solidFixtures = new ArrayList<>();
    private final ArrayList<Shape> ghostlyFixtures = new ArrayList<>();
    private boolean isGhostly = false;
    /**
     * The sound effects instance for the walker.
     */
    public final SoundFX soundFX;
    /**
     * Adds a solid fixture to the walker using the shape provided.
     * @param gameWorld The game world that the walker is in.
     * @param shape The shape of the walker.
     * @param origin The origin of the walker in the world.
     * @param walkerType The type of walker this is.
     */
    public WalkerFrame(GameWorld gameWorld, Shape shape, Vec2 origin, WalkerType walkerType) {
        super(gameWorld);
        // Initialising constants
        this.ORIGIN_X = origin.x;
        this.ORIGIN_Y = origin.y;
        this.SHAPE = shape;
        this.WALKER_TYPE = walkerType;
        this.gameWorld = gameWorld;
        // Method Calls
        setPosition(origin);
        constructSolidFixture(SHAPE);
        soundFX = new SoundFX(walkerType);
        isDebug();
    }
    // Methods
    /**
     * Sets the line colour of the walker to the debug colour.
     * Sets AlwaysOutline to the value of {@link Game#isDebugOn()}
     */
    private void isDebug() {
       boolean debugOn = Game.isDebugOn();
        setLineColor(Game.getColor(debugOn));
        setAlwaysOutline(debugOn);
    }
    /**
     * Constructs a solid fixture for the walker,
     * then adds the shape to the solid fixtures array
     * to be used later if the need for reconstruction arises.
     * @param shape The shape of the walker.
     */
    public void constructSolidFixture(Shape shape) {
        if (!solidFixtures.contains(shape)) {
            solidFixtures.add(shape);
        }
        new SolidFixture(this, shape);
    }
    /**
     * Constructs a ghostly fixture for the walker,
     * then adds the shape to the ghostly fixtures array
     * to be used later if the need for reconstruction arises.
     * @param shape The shape of the walker.
     */
    public void constructGhostlyFixture(Shape shape) {
        if (!ghostlyFixtures.contains(shape)) {
            ghostlyFixtures.add(shape);
        }
        new GhostlyFixture(this, shape);
    }
    // Toggle Setter Methods

    /**
     * sets the walker's state to the attack state.
     * @see State
     */
    public void toggleOnAttack() {
        attacking = true;
        state = State.ATTACK1;
    }
    /**
     * sets the walker's state to the idle state
     * if still in attack state.
     * stops the sound effect of the attack.
     * @see State
     * @see SoundFX
     */
    public void toggleOffAttack() {
        soundFX.stopFX(State.ATTACK1);
        attacking = false;
        if (state.equals(State.ATTACK1)) {
            state = State.IDLE;
        }
    }
    /**
     * sets the walker's state to the hit state.
     * starts a timer to call {@link #toggleOffHit()}
     * and start the sound effect for hit after 300 ms.
     * @see State
     * @see SoundFX
     */
    public void toggleOnHit() {
        if (!dead) {
            hit = true;
            state = State.HIT;
            javax.swing.Timer hitTimer = new javax.swing.Timer(300, e -> {
                soundFX.hit();
                toggleOffHit();
            });
            hitTimer.setRepeats(false);
            hitTimer.start();
        }
    }
    /**
     * sets the walker's state to the idle state
     * if not the dead
     * @see State
     * @see SoundFX
     */
    public void toggleOffHit() {
        hit = false;
        if (!dead) {
            state = State.IDLE;
        }
    }
    /**
     * Puts the walker's actions on cooldown
     * starts a timer to turn the cooldown off after 1500 ms.
     * @see State
     * @see SoundFX
     */
    public void toggleActionCoolDown() {
        if (!isCooldown) {
            javax.swing.Timer coolDowntimer = new javax.swing.Timer(1500, e -> isCooldown = false);
            coolDowntimer.setRepeats(false);
            coolDowntimer.start();
            isCooldown = true;
        }
    }
    // Destruction Functions
    /**
     * Destroys the walker after stoping all sound effects.
     * This method is called when the walker dies.
     * @see SoundFX
     */
    public void die() {
        for (State state : State.values()) {
            soundFX.stopFX(state);
        }
        destroy();
    }
    /**
     * Destroys all fixtures attached to the walker,
     * constructs Ghostly fixtures for all shapes in
     * the solid fixtures array and the ghostly fixtures array.
     * Sets gravity scale to 0 to prevent falling through the floor.
     * Sets linear y velocity to 0 to prevent floating up or down.
     * Outputs a warning if the walker is already ghostly.
     */
    public void makeGhostly() {
        if (!isGhostly) {
            getFixtureList().forEach(Fixture::destroy);
            solidFixtures.forEach(this::constructGhostlyFixture); // since they must be ghostly in Ghostly form
            ghostlyFixtures.forEach(this::constructGhostlyFixture);
            new Sensor(this, SHAPE); // sensor to use for detecting
            setGravityScale(0);
            setLinearVelocity(new Vec2(0, 0));
            isGhostly = true;
            return;
        }
        Console.warning("WalkerFrame.makeGhostly() called on a Ghostly WalkerFrame " + getName() + ", Ignoring method call.");
    }
    /**
     * Destroys all fixtures attached to the walker,
     * constructs solid fixtures for all shapes in
     * the solid fixtures array,
     * constructs ghostly fixtures for all the shapes in
     * the ghostly fixtures array.
     * Sets gravity scale to 1 to resume normal gravity.
     * Outputs a warning if the walker is already solid.
     */
    public void makeSolid() {
        if (isGhostly) {
            getFixtureList().forEach(Fixture::destroy);
            solidFixtures.forEach(this::constructSolidFixture);
            ghostlyFixtures.forEach(this::constructGhostlyFixture);
            setGravityScale(1);
            isGhostly = false;
            return;
        }
        Console.warning("WalkerFrame.makeSolid() called on a solid WalkerFrame " + getName() + ", Ignoring method call.");
    }
    /**
     * Sets the walker to the DEATH state.
     * Calls {@link #makeGhostly()} to make the walker ghostly.
     */
    public void beginDeath() {
        if (!isGhostly) {
            makeGhostly();
        }
        dead = true;
        state = State.DEATH;
    }
    // Setters
    /**
     * Sets the state of the walker to the given state.
     * @param state The state to set the walker to.
     */
    public void setState(State state) {
        this.state = state;
    }
    /**
     * Sets the direction of the walker to the given direction.
     * @param direction The direction to set the walker to.
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    // Getters
    /**
     * gets the boolean value associated with the attacking state.
     * @return the {@code attacking} boolean value.
     */
    public boolean isAttacking() {
        return attacking;
    }
    /**
     * gets the boolean value associated with the hit state.
     * @return the {@code hit} boolean value.
     */
    public boolean isHit() {
        return hit;
    }
    /**
     * gets the current walker state
     * @return the state.
     */
    public State getState() {
        return state;
    }
    /**
     * gets the direction of the walker.
     * @return the {@code direction} value.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * gets the GameWorld of the walker.
     * @return the {@code gameWorld} instance.
     * @see GameWorld
     */
    public GameWorld getGameWorld() {
        return gameWorld;
    }
    /**
     * gets the boolean value associated with the dead state.
     * @return the {@code dead} boolean value.
     */
    public boolean isDead() {
        return dead;
    }
    /**
     * gets the walkerType of the walker.
     * @return the {@link WalkerType} property.
     */
    public WalkerType getWalkerType() {
        return WALKER_TYPE;
    }

    /**
     * gets the boolean value associated with the cooldown state.
     * @return the {@code isCooldown} property.
     */
    public boolean getCooldown() {
        return isCooldown;
    }
    /**
     * gets the boolean value associated with the ghostly state.
     * @return the inverse of {@code isGhostly} property.
     */
    public boolean isSolid() {
        return !isGhostly;
    }
}
