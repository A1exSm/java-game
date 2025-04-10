package game.animation;
// Imports
import game.Game;
import game.core.GameWorld;
import game.body.walkers.mobs.MobWalker;
import game.enums.State;
import game.enums.Walkers;
import java.util.HashMap;
import static game.enums.State.*;
// Class
/**
 * The MobAnimationStepListener class manages the animation steps for a MobWalker.
 * It handles the animation state transitions and timing for the walker's animations.
 */
public class MobAnimationStepListener {
    // Fields
    private  FrameHandler currentHandler;
    private javax.swing.Timer animationTimer;
    private int timerCount;
    private final MobWalker walker;
    private final GameWorld gameWorld;
    private final HashMap<State, FrameHandler> animations = new HashMap<>();
    // Constructor
    /**
     * Initializes the listener with the specified MobWalker and populates the animation handlers.
     *
     * @param walker The MobWalker object for which the animations are managed.
     */
    public MobAnimationStepListener(MobWalker walker) {
        this.walker = walker;
        this.gameWorld = walker.getGameWorld();
        populateHashMap();
    }
    // Methods
    /**
     * Populates the animations HashMap with FrameHandler objects for each supported state of the walker.
     */
    private void populateHashMap() {
        for (State state : walker.getSupportedStates()) {
            animations.put(state, new FrameHandler(walker, state));
        }
    }
    /**
     * Steps through the animation if the game world is running.
     */
    public void step() {
        if (gameWorld.isRunning()) {
            playAnimation(walker.getState());
        }
    }
    /**
     * Plays the animation for the specified state.
     * Resets the frame and invokes the appropriate timer based on the state.
     * @param state The current state of the walker.
     */
    private void playAnimation(State state) {
        if (currentHandler == null || currentHandler != animations.get(state)) {
            currentHandler = animations.get(state);
            currentHandler.resetFrame();
            if (currentHandler == animations.get(ATTACK1)) {
                invokeAttackTimer();
                if (walker.getWalkerType() != Walkers.WORM) {
                    walker.soundFX.attack1();
                }
            } else if (currentHandler == animations.get(DEATH)) {
                invokeDeathTimer();
            } else {
                toggleTimer();
            }
        }
    }
    /**
     * Toggles the animation timer, restarting it if it is not running.
     */
    private void toggleTimer() {
        if (animationTimer != null) {
            if (!animationTimer.isRunning()) {
                animationTimer.restart();
            }
        } else setTimer(200);
    }
    /**
     * Checks the timer and handles the end of the animation cycle.
     * Resets the timer count and restarts the timer if necessary.<br>
     * Also handles certain {@link MobWalker} logic
     */
    private void checkTimer() {
        if (timerCount == currentHandler.getNumFrames()) {
            if (currentHandler == animations.get(ATTACK1)) {walker.toggleOffAttack();}
            if (currentHandler == animations.get(DEATH)) {walker.die();}
            timerCount = 1;
            animationTimer.restart();
        }
    }
    /**
     * Sets the animation timer with the specified delay.
     * @param delay The delay in milliseconds for the timer.
     */
    private void setTimer(int delay) {
        timerCount = 1;
        animationTimer = new javax.swing.Timer(delay, e ->{
            if (gameWorld.isRunning()) { // ensures animation pauses when game pauses
                timerCount++;
                currentHandler.incrementFrame(walker.getDirection());
                checkTimer();
            }
        });
        animationTimer.setRepeats(true);
        animationTimer.start();
    }
    /**
     * Invokes the attack timer with a shorter delay.
     */
    private void invokeAttackTimer() {
        animationTimer.stop();
        setTimer(100);
    }
    /**
     * Invokes the death timer with a longer delay.
     */
    private void invokeDeathTimer() {
        animationTimer.stop();
        setTimer(300);
    }

    // Timer Destruction events
    /**
     * ends the Animations
     */
    public void remove() {
        endAnimations();
    }
    /**
     * Ends the animations by stopping the timer.
     */
    private void endAnimations() {
        if (animationTimer != null) animationTimer.setRepeats(false); // ensures timer does not continue after walker is dead
    }
}
