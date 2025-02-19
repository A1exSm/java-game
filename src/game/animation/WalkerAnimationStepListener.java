package game.animation;
// Imports

import game.GameWorld;
import game.body.walkers.WalkerFrame;
import game.body.walkers.mobs.MobWalker;
import game.enums.State;

import java.awt.*;
import java.util.HashMap;

import static game.enums.State.*;

// Class
public class WalkerAnimationStepListener {
    // Fields
    private  FrameHandler currentHandler;
    private javax.swing.Timer animationTimer;
    private int timerCount;
    private final MobWalker walker;
    private final GameWorld gameWorld;
    private final HashMap<State, FrameHandler> animations = new HashMap<>();
    // Constructor
    public WalkerAnimationStepListener(MobWalker walker) {
        this.walker = walker;
        this.gameWorld = walker.getGameWorld();
        populateHashMap();
    }
    // Methods
    private void populateHashMap() {
        for (State state : walker.getSupportedStates()) {
            animations.put(state, new FrameHandler(walker, state));
        }
    }

    public void step() {
        if (gameWorld.isRunning()) {
            playAnimation(walker.getState());
        }
    }

    private void playAnimation(State state) {
        if (currentHandler == null || currentHandler != animations.get(state)) {
            currentHandler = animations.get(state);
            currentHandler.resetFrame();
            if (currentHandler == animations.get(ATTACK1)) invokeAttackTimer();
            else if (currentHandler == animations.get(DEATH)) {invokeDeathTimer();}
            else toggleTimer();
        }
    }

    private void toggleTimer() {
        if (animationTimer != null) {
            if (!animationTimer.isRunning()) {
                animationTimer.restart();
            }
        } else setTimer(200);
    }

    private void checkTimer() {
        if (timerCount == currentHandler.getNumFrames()) {
            if (currentHandler == animations.get(ATTACK1)) walker.toggleOffAttack();
            if (currentHandler == animations.get(DEATH)) walker.die();
            timerCount = 1;
            animationTimer.restart();
        }
    }

    private void setTimer(int delay) {
        timerCount = 1;
        animationTimer = new javax.swing.Timer(delay, e ->{
            timerCount++;
            currentHandler.incrementFrame(walker.getDirection());
            checkTimer();
        });
        animationTimer.setRepeats(true);
        animationTimer.start();
    }

    private void invokeAttackTimer() {
        animationTimer.stop();
        setTimer(100);
    }
    private void invokeDeathTimer() {
        animationTimer.stop();
        setTimer(300);
    }

    // Timer Destruction events
    public void remove() {
        endAnimations();
    }

    private void endAnimations() {
        animationTimer.setRepeats(false); // ensures timer does not continue after walker is dead
    }
}
