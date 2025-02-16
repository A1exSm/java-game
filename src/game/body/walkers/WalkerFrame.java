package game.body.walkers;
// Imports

import game.GameWorld;
import game.enums.Direction;
import game.enums.State;
import city.cs.engine.Shape;
import city.cs.engine.Walker;
import game.enums.Walkers;
import org.jbox2d.common.Vec2;

// Class
public class WalkerFrame extends Walker {
    // Fields
    public final float ORIGIN_X;
    public final float ORIGIN_Y;
    private boolean attacking = false;
    private boolean hit = false;
    private State state = State.IDLE;
    private Direction direction = Direction.RIGHT;
    private final GameWorld gameWorld;
    private boolean dead = false;
    private final Walkers WALKER_TYPE; // for now, walkers cannot transform into another
    // Constructor
    public WalkerFrame(GameWorld gameWorld, Shape shape, Vec2 origin, Walkers walkerType) {
        super(gameWorld, shape);
        // Initialising constants
        this.ORIGIN_X = origin.x;
        this.ORIGIN_Y = origin.y;
        this.WALKER_TYPE = walkerType;
        setPosition(origin);
        this.gameWorld = gameWorld;
    }
    // Methods

    // Toggle Setter Methods
    public void toggleOnAttack() {
        attacking = true;
    }

    public void toggleOffAttack() {
        attacking = false;
    }

    public void toggleOnHit() {
        hit = true;
        state = State.HIT;
    }

    public void toggleOffHit() {
        hit = false;
        if (!dead) {
            state = State.IDLE;
        }
    }
    // Destruction Functions
    public void die() {
        destroy();
    }

    // Setters
    public void setState(State state) {
        this.state = state;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void beginDeath() {
        dead = true;
        state = State.DEATH;
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
}
