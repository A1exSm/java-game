package game.animation;
// Imports
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.body.walkers.PlayerWalker;
import game.enums.State;
import org.jbox2d.common.Vec2;
import java.util.HashMap; // I hope this is allowed :)
import game.GameWorld;
// importing my enums as static constants for ease of use.
import javax.swing.*;

import static game.enums.State.*;
import static game.enums.Direction.*;
// Class
public class AnimationStepListener implements StepListener {
    // Fields
    private PlayerAnimation currentAnimation;
    private javax.swing.Timer timer;
    private int timerCount;
    private Vec2 linearVelocity;
    private final PlayerWalker player;
    private final GameWorld world;
    private final HashMap<State, PlayerAnimation> animations = new HashMap<>();
    // Constructor
    public AnimationStepListener(GameWorld world, PlayerWalker player) {
        this.player = player;
        this.world = world;
        world.addStepListener(this);
        hashMapSetup(); // creates a hashmap between PlayerState and PlayerAnimation for all states.
        setAnimation(IDLE); // initial state
    }
    // Constructor Methods
    private void hashMapSetup() {
        for (State state : State.values()) {
            animations.put(state, new PlayerAnimation(world, player, state));
        }
    }
    // Override Methods
    @Override
    public void preStep(StepEvent stepEvent) {
        if (world.isRunning()) {
            linearVelocity = player.getLinearVelocity();
            findDirection();
            setState();
        } else if (timer!=null) {
            if (timer.isRunning()) timer.stop();
        }

    }
    @Override
    public void postStep(StepEvent stepEvent) {
        player.checkWizards();
    }
    // Animation Methods
    private void findDirection() {
        if (linearVelocity.x > 2) {
            player.setDirection(RIGHT);
        } else if (linearVelocity.x < -2) {
            player.setDirection(LEFT);
        }
        currentAnimation.cycleFrame(player.getDirection());
    }
    private void setState() { // detects what sort of movement the player is performing based of the player's velocity.
        if (player.getAttacking()) {
            player.setState(ATTACK1); // currently only handles ATTACK1, still thinking of a use for ATTACK2, Maybe a finisher move or Combo?
        } else {
            if (linearVelocity.y > 2) player.setState(JUMP); // threshold of 2 to prevent jitter between surfaces.
            else if (linearVelocity.y < -2 || player.getBodiesInContact().isEmpty()) player.setState(FALL); // check if player is in contact with other bodies, as we don't want a random idle game.animation when the player jumps/falls slower than 2.
            else if (linearVelocity.x > 2 || linearVelocity.x < -2) player.setState(RUN);
            else player.setState(IDLE);
        }
        setAnimation(player.getState());
    }
    private void setAnimation(State state) {
        if (currentAnimation == null || currentAnimation != animations.get(state)) { // we just skip this if it's the same state
            currentAnimation = animations.get(state);
            if (currentAnimation == animations.get(ATTACK1)) { // separate sequence for attack timer
                invokeAttackTimer();
            } else {
                toggleTimer();
            }
        }
    }
    // Timer Methods
    private void checkTimer(Timer timer) {
        if (timerCount == currentAnimation.getNumFrames()) { // although PlayerAnimation.incrementFrame ensures we don't go out of index there, we need to also do that on this side, might be worth making a function which handles both...
            if (currentAnimation == animations.get(ATTACK1)) world.togglePlayerAttack();
            timerCount = 0;
            timer.restart(); // timer needs to restart so game.animation doesn't "stall" - where we have to wait for one game.animation to finish for the next to start
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
        setTimer(100); // attack needs to be faster so we set it to 100ms, typical gifs (as far as ik) have a 200ms gap between frames.
        player.hurtWizards();
    }
    private void setTimer(int delay) {
        timerCount = 0;
        timer = new javax.swing.Timer(delay, e -> {
            timerCount++;
            currentAnimation.incrementFrame(player.getDirection());
            checkTimer(timer);
        });
        timer.setRepeats(true);
        timer.start();
    }
}
