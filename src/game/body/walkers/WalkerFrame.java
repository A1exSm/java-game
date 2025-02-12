package game.body.walkers;
// Imports

import game.GameWorld;
import game.animation.Direction;
import game.animation.PlayerState;
import city.cs.engine.Shape;
import city.cs.engine.Walker;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

// Class
public class WalkerFrame extends Walker {
    // Fields
    public final float ORIGIN_X;
    public final float ORIGIN_Y;
    private boolean attacking = false;
    private boolean hit = false;
    private PlayerState state = PlayerState.IDLE;
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
    }

    public void toggleOffHit() {
        hit = false;
    }


    // Setters
    public void setState(PlayerState state) {
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

    public PlayerState getState() {
        return state;
    }

    public Direction getDirection() {
        return direction;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }
}
