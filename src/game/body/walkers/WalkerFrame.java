package game.body.walkers;
// Imports
import city.cs.engine.*;
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
 * @see game.body.walkers.mobs.MobWalker, game.body.walkers.PlayerWalker
*/
// Class
public abstract class WalkerFrame extends Walker {
    // Fields
    public final float ORIGIN_X;
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
    public final SoundFX soundFX;

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
    }
    // Methods

    public void constructSolidFixture(Shape shape) {
        if (!solidFixtures.contains(shape)) {
            solidFixtures.add(shape);
        }
        new SolidFixture(this, shape);
    }

    public void constructGhostlyFixture(Shape shape) {
        if (!ghostlyFixtures.contains(shape)) {
            ghostlyFixtures.add(shape);
        }
        new GhostlyFixture(this, shape);
    }

    // Toggle Setter Methods
    public void toggleOnAttack() {
        attacking = true;
        state = State.ATTACK1;
    }

    public void toggleOffAttack() {
        soundFX.stopFX(State.ATTACK1);
        attacking = false;
        if (state.equals(State.ATTACK1)) {
            state = State.IDLE;
        }
    }

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

    public void toggleOffHit() {
        hit = false;
        if (!dead) {
            state = State.IDLE;
        }
    }

    public void toggleActionCoolDown() {
        if (!isCooldown) {
            javax.swing.Timer coolDowntimer = new javax.swing.Timer(1500, e -> {
                isCooldown = false;
            });
            coolDowntimer.setRepeats(false);
            coolDowntimer.start();
            isCooldown = true;
        }
    }

    // Destruction Functions
    public void die() {
        for (State state : State.values()) {
            soundFX.stopFX(state);
        }
        destroy();
    }

    public void makeGhostly() {
        if (!isGhostly) {
            getFixtureList().forEach(Fixture::destroy);
            solidFixtures.forEach(this::constructGhostlyFixture); // since they must be ghostly in Ghostly form
            ghostlyFixtures.forEach(this::constructGhostlyFixture);
            new Sensor(this, SHAPE); // sensor to use for detect stuff in the future (since collisions don't occur)
            setGravityScale(0);
            setLinearVelocity(new Vec2(0, 0));
            isGhostly = true;
            return;
        }
        Console.warning("WalkerFrame.makeGhostly() called on a Ghostly WalkerFrame " + getName() + ", Ignoring method call.");
    }

    public void makeSolid() {
        if (isGhostly) {
            getFixtureList().forEach(Fixture::destroy);
            solidFixtures.forEach(this::constructSolidFixture);
            ghostlyFixtures.forEach(this::constructGhostlyFixture);
            // this means I will need a function to re-add the fixture if I choose to have fixture which make up the hit box.
            setGravityScale(1);
            isGhostly = false;
            return;
        }
        Console.warning("WalkerFrame.makeSolid() called on a solid WalkerFrame " + getName() + ", Ignoring method call.");
    }

    public void beginDeath() {
        if (!isGhostly) {
            makeGhostly();
        }
        dead = true;
        state = State.DEATH;
    }

    // Setters
    public void setState(State state) {
        this.state = state;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    // Getters
    public boolean isAttacking() {
        return attacking;
    }

    public boolean isHit() {
        return hit;
    }

    public State getState() {
        return state;
    }

    public Direction getDirection() {
        return direction;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public boolean isDead() {
        return dead;
    }

    public WalkerType getWalkerType() {
        return WALKER_TYPE;
    }

    public boolean getCooldown() {
        return isCooldown;
    }

    public boolean isSolid() {
        return !isGhostly;
    }

}
