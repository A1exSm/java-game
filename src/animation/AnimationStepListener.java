package animation;
// Imports
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import city.cs.engine.Walker;
import org.jbox2d.common.Vec2;
import java.util.HashMap; // I hope this is allowed :)
import game.GameWorld;
// importing my enums as static constants for ease of use.
import javax.swing.*;

import static animation.PlayerState.*;
import static animation.Direction.*;
// Class
public class AnimationStepListener implements StepListener {
    // Fields
    private Direction direction = RIGHT; // initial direction
    private PlayerAnimation currentAnimation;
    private javax.swing.Timer timer;
    private int timerCount;
    private Vec2 linearVelocity;
    private final Walker player;
    private final GameWorld world;
    private final HashMap<PlayerState, PlayerAnimation> animations = new HashMap<>();
    // Constructor
    public AnimationStepListener(GameWorld world, Walker player) {
        this.player = player;
        this.world = world;
        world.addStepListener(this);
        hashMapSetup(); // creates a hashmap between PlayerState and PlayerAnimation for all states.
        setAnimation(IDLE); // initial state
    }
    // Constructor Methods
    private void hashMapSetup() {
        for (PlayerState state : PlayerState.values()) {
            animations.put(state, new PlayerAnimation(world, player, state));
        }
    }
    // Override Methods
    @Override
    public void preStep(StepEvent stepEvent) {
        linearVelocity = player.getLinearVelocity();
        getDirection();
        getState();

    }
    @Override
    public void postStep(StepEvent stepEvent) {

    }
    // Animation Methods
    private void getState() { // detects what sort of movement the player is performing based of the player's velocity.
        if (world.getPlayerAttack()) {
            setAnimation(ATTACK1);
        } else {
            if (linearVelocity.y > 2) setAnimation(JUMP); // threshold of 2 to prevent jitter between surfaces.
            else if (linearVelocity.y < -2 || player.getBodiesInContact().isEmpty())
                setAnimation(FALL); // check if player is in contact with other bodies, as we don't want a random idle animation when the player jumps/falls slower than 2.
            else if (linearVelocity.x > 2 || linearVelocity.x < -2) setAnimation(RUN);
            else setAnimation(IDLE);
        }
    }
    private void checkTimer(Timer timer) {
        if (timerCount == currentAnimation.getNumFrames()) {
            if (currentAnimation == animations.get(ATTACK1)) world.togglePlayerAttack();
            timerCount = 0;
            timer.restart();
        }
    }
    private void setAnimation(PlayerState state) {
        if (currentAnimation != animations.get(state)) { // we just skip this if it's the same state
            currentAnimation = animations.get(state);
            if (currentAnimation == animations.get(ATTACK1)) { // separate sequence for attack timer
                invokeAttackTimer();
            } else {
                toggleTimer();
            }
        }
    }
    private void toggleTimer() {
        if (timer != null) {
            if (!timer.isRunning()) {
                timer.restart();
            }
        } else {
            setTimer(200);
        }
    }
    private void invokeAttackTimer() {
        timer.stop();
        setAnimation(ATTACK1);
        setTimer(100);
    }
    private void setTimer(int delay) {
        timerCount = 0;
        timer = new javax.swing.Timer(delay, e -> {
            timerCount++;
            currentAnimation.incrementFrame(direction);
            checkTimer(timer);
        });
        timer.setRepeats(true);
        timer.start();
    }
    private void getDirection() {
        if (linearVelocity.x > 2) {
            direction = Direction.RIGHT;
        } else if (linearVelocity.x < -2) {
            direction = Direction.LEFT;
        }
        currentAnimation.cycleFrame(direction);
    }
}
