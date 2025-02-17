package game.body.walkers;
// Imports
import city.cs.engine.BoxShape;
import city.cs.engine.Sensor;
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
    private boolean isCooldown = false;

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
        state = State.ATTACK1;
    }

    public void toggleOffAttack() {
        attacking = false;
        state = State.IDLE;
    }

    public void toggleOnHit() {
        hit = true;
        state = State.HIT;
        javax.swing.Timer hitTimer = new javax.swing.Timer(300, e -> {
            toggleOffHit();
        });
        hitTimer.setRepeats(false);
        hitTimer.start();
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
    public void beginDeath() {
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
}
