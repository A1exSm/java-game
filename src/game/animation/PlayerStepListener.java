package game.animation;
// Imports
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.body.walkers.PlayerWalker;
import game.enums.State;
import org.jbox2d.common.Vec2;
import java.util.HashMap; // I hope this is allowed :)
import game.core.GameWorld;
// importing my enums as static constants for ease of use.
import javax.swing.*;
import static game.enums.State.*;
import static game.enums.Direction.*;
// Class
/**
 * The PlayerStepListener class implements {@link StepListener} to manage the animation and state transitions for the PlayerWalker.
 * It handles the player's movement, direction, and animation state based on the game world events.
 */
public class PlayerStepListener implements StepListener {
    // Fields
    private FrameHandler currentAnimation;
    private javax.swing.Timer timer;
    private int timerCount;
    private Vec2 linearVelocity;
    private final PlayerWalker player;
    private final GameWorld world;
    private final HashMap<State, FrameHandler> animations = new HashMap<>();
    // Constructor
    /**
     * Constructor for PlayerStepListener.
     * Initializes the listener with the specified game world and player walker.
     * Sets up the animation handlers and initial state.
     * @param world The GameWorld object representing the game world.
     * @param player The PlayerWalker object representing the player.
     */
    public PlayerStepListener(GameWorld world, PlayerWalker player) {
        this.player = player;
        this.world = world;
        world.addStepListener(this);
        hashMapSetup(); // creates a hashmap between PlayerState and PlayerAnimation for all states.
        setAnimation(IDLE); // initial state
    }
    // Constructor Methods
    /**
     * Sets up the animations HashMap with {@link FrameHandler} objects for each supported state of the player.
     */
    private void hashMapSetup() {
        for (State state : PlayerWalker.SUPPORTED_STATES) {
            animations.put(state, new FrameHandler(player, state));
        }
    }
    // Override Methods
    /**
     * Called before each physics step.
     * Updates the player's direction, state, and animation based on the current velocity and game state.
     * @param stepEvent The StepEvent object representing the step event.
     */
    @Override
    public void preStep(StepEvent stepEvent) {
        if (world.isRunning()) {
            player.checkMob();
            linearVelocity = player.getLinearVelocity();
            findDirection();
            setState();
        } else if (timer!=null) {
            if (timer.isRunning()) timer.stop();
        }

    }
    @Override
    public void postStep(StepEvent stepEvent) {}
    // Animation Methods
    private void findDirection() {
        if (linearVelocity.x > 2) {
            player.setDirection(RIGHT);
        } else if (linearVelocity.x < -2) {
            player.setDirection(LEFT);
        }
        currentAnimation.cycleFrame(player.getDirection());
    }
    /**
     * Determines the player's direction based on the current velocity.
     * Updates the current animation frame based on the direction.
     */
    private void setState() { // detects what sort of movement the player is performing based of the player's velocity.
        if (player.getState() != DEATH) {
            if (player.getAttacking()) {
                player.setState(ATTACK1); // currently only handles ATTACK1, still thinking of a use for ATTACK2, Maybe a finisher move or Combo?
            } else {
                if (linearVelocity.y > 2) player.setState(JUMP); // threshold of 2 to prevent jitter between surfaces.
                else if (linearVelocity.y < -2 || player.getBodiesInContact().isEmpty())
                    player.setState(FALL); // check if player is in contact with other bodies, as we don't want a random idle game.animation when the player jumps/falls slower than 2.
                else if (linearVelocity.x > 2 || linearVelocity.x < -2) player.setState(RUN);
                else {
                    player.setState(IDLE);
                }
            }
        }
        setAnimation(player.getState());
    }
    /**
     * Sets the player's state based on the current velocity and contact with other bodies.
     * Updates the animation based on the new state.
     */
    private void setAnimation(State state) {
        if (currentAnimation == null || currentAnimation != animations.get(state)) { // we just skip this if it's the same state
            currentAnimation = animations.get(state);
            if (currentAnimation == animations.get(ATTACK1)) invokeAttackTimer();
            if (currentAnimation == animations.get(DEATH)) invokeDeathTimer();
            toggleTimer();
        }
    }
    // Timer Methods
    /**
     * Checks the timer and handles the end of the animation cycle.
     * Resets the timer count and restarts the timer if necessary.
     */
    private void checkTimer() {
        if (timerCount == currentAnimation.getNumFrames()) { // although PlayerAnimation.incrementFrame ensures we don't go out of index there, we need to also do that on this side, might be worth making a function which handles both...
            if (currentAnimation == animations.get(ATTACK1)) world.togglePlayerAttack();
            if (currentAnimation == animations.get(DEATH)) {
                player.die();
                return;
            }
            timerCount = 0;
            timer.restart(); // timer needs to restart so game.animation doesn't "stall" - where we have to wait for one game.animation to finish for the next to start
        }
    }
    /**
     * Toggles the animation timer, restarting it if it is not running.
     */
    private void toggleTimer() {
        if (timer != null) {
            if (!timer.isRunning()) {
                timer.restart();
            }
        } else {
            setTimer(200);
        }
    }
    /**
     * Invokes the attack timer with a shorter delay.
     * Sets the animation to ATTACK1 and triggers the player's attack action.
     */
    private void invokeAttackTimer() {
        timer.stop();
        setAnimation(ATTACK1);
        setTimer(100); // attack needs to be faster so we set it to 100ms, typical gifs (as far as ik) have a 200ms gap between frames.
        player.hurtMob();
    }
    /**
     * Invokes the death timer with a longer delay.
     */
    private void invokeDeathTimer() {
        timer.stop();
        setTimer(300);
    }
    /**
     * Sets the animation timer with the specified delay.
     * @param delay The delay in milliseconds for the timer.
     */
    private void setTimer(int delay) {
        timerCount = 0;
        timer = new javax.swing.Timer(delay, e -> {
            timerCount++;
            currentAnimation.incrementFrame(player.getDirection());
            checkTimer();
        });
        timer.setRepeats(true);
        timer.start();
    }
    /**
     * Removes the animation timer and step listener from the game world.
     */
    public void remove() {
        this.timer.stop();
        this.timer = null;
        world.removeStepListener(this);
    }
}
