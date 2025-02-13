package game.body.walkers;
// Imports

import game.GameWorld;
import game.enums.Direction;
import game.enums.State;
import city.cs.engine.Shape;
import city.cs.engine.Walker;
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
    // Constructor
    public WalkerFrame(GameWorld gameWorld, Shape shape, Vec2 origin) {
        super(gameWorld, shape);
        // Initialising constants
        this.ORIGIN_X = origin.x;
        this.ORIGIN_Y = origin.y;
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
        state = State.IDLE;
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
}
