package game.body.walkers;
// Imports
import city.cs.engine.*;
import game.core.GameWorld;
import game.enums.Direction;
import game.enums.State;
import game.enums.Walkers;
import org.jbox2d.common.Vec2;

import java.util.ArrayList;

// Class
public class WalkerFrame extends Walker {
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
    private final Walkers WALKER_TYPE; // for now, walkers cannot transform into another
    private boolean isCooldown = false;
    private final ArrayList<Shape> solidFixtures = new ArrayList<>();
    private final ArrayList<Shape> ghostlyFixtures = new ArrayList<>();
    private boolean isGhostly = false;

    public WalkerFrame(GameWorld gameWorld, Shape shape, Vec2 origin, Walkers walkerType) {
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
        attacking = false;
        state = State.IDLE;
    }

    public void toggleOnHit() {
        if (!dead) {
            hit = true;
            state = State.HIT;
            javax.swing.Timer hitTimer = new javax.swing.Timer(300, e -> {
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
        destroy();
    }

    public void makeGhostly() {
        if (!isGhostly) {
            getFixtureList().forEach(Fixture::destroy);
            solidFixtures.forEach(this::constructGhostlyFixture); // since they must be ghostly in Ghostly form
            ghostlyFixtures.forEach(this::constructGhostlyFixture);
            Sensor ghostSensor = new Sensor(this, SHAPE); // sensor to use for detect stuff in the future (since collisions don't occur)
            setGravityScale(0);
            isGhostly = true;
            return;
        }
        System.err.println("Warning: WalkerFrame.makeGhostly() called on a Ghostly WalkerFrame " + getName() + ", Ignoring method call.");
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
        System.err.println("Warning: WalkerFrame.makeSolid() called on a solid WalkerFrame " + getName() + ", Ignoring method call.");
    }

    public void beginDeath() {
        makeGhostly();
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
    public boolean getAttacking() {
        return attacking;
    }

    public boolean getHit() {
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

    public Walkers getWalkerType() {
        return WALKER_TYPE;
    }

    public boolean getCooldown() {
        return isCooldown;
    }
    public boolean isGhostly() {
        return isGhostly;
    }
}
