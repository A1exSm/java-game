package game.body.walkers.steplisteners;
// Imports

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.Game;
import game.body.walkers.WalkerFrame;
import game.body.walkers.mobs.MobWalker;
import org.jbox2d.common.Vec2;

// Class
public class PassthroughListener implements StepListener {
    // Fields
    private final WalkerFrame otherWalker;
    private final WalkerFrame walker;
    private final Vec2 halfDimensions;
    private final javax.swing.Timer solidTimer;
    private static final float SAFETY_MARGIN = 0.5f;
    private boolean canBecomeSolid = false;
    // Constructor
    public PassthroughListener(WalkerFrame walker, WalkerFrame otherWalker) {
        this.walker = walker;
        this.otherWalker = otherWalker;
        this.halfDimensions = new Vec2(otherWalker.getWalkerType().getHalfDimensions().x + SAFETY_MARGIN, otherWalker.getWalkerType().getHalfDimensions().y + SAFETY_MARGIN);
        Game.gameWorld.addStepListener(this);
        solidTimer = new javax.swing.Timer(300, e -> {
            canBecomeSolid = true;
        });
        solidTimer.setRepeats(false);
    }
    // Methods
    @Override
    public void preStep(StepEvent stepEvent) {
        if (walker.isDead() || otherWalker.isDead()) {
            if (!walker.isSolid() && !walker.isDead()) {
                walker.makeSolid();
            }
            Game.gameWorld.removeStepListener(this);
        }
    }
    
    @Override
    public void postStep(StepEvent stepEvent) {
        if (walker.isDead() || otherWalker.isDead()) {return;}
        if (walker.getFixtureList().isEmpty()) {return;}
        if (!walker.intersects(otherWalker.getPosition(), halfDimensions.x, halfDimensions.y)) {
            if (canBecomeSolid) {
                walker.makeSolid();
                Game.gameWorld.removeStepListener(this);
            } else if (!solidTimer.isRunning()) {
                solidTimer.start();
            }
        } else {
            canBecomeSolid = false;

        }
    }
}
