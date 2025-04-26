package game.body.walkers.steplisteners;
// Imports
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.Game;
import game.body.walkers.WalkerFrame;
import org.jbox2d.common.Vec2;
// Class
/**
 * This class is used to make a walker pass through another walker.
 * It makes the walker solid when it is not intersecting with the other walker.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 03-03-2025
 */
public final class PassthroughListener implements StepListener {
    // Fields
    private final WalkerFrame otherWalker;
    private final WalkerFrame walker;
    private final Vec2 halfDimensions;
    private final javax.swing.Timer solidTimer;
    private static final float SAFETY_MARGIN = 0.5f;
    private boolean canBecomeSolid = false;
    // Constructor
    /**
     * Adds passthrough listener to the game world for the given walkers.
     * @param walker the walker that will pass through the other walker
     * @param otherWalker the other walker
     */
    public PassthroughListener(WalkerFrame walker, WalkerFrame otherWalker) {
        this.walker = walker;
        this.otherWalker = otherWalker;
        this.halfDimensions = new Vec2(otherWalker.getWalkerType().getHalfDimensions().x + SAFETY_MARGIN, otherWalker.getWalkerType().getHalfDimensions().y + SAFETY_MARGIN);
        solidTimer = new javax.swing.Timer(300, e -> canBecomeSolid = true);
        solidTimer.setRepeats(false);
        Game.gameWorld.addStepListener(this);
    }
    // Methods
    /**
     * This method is called before the step event.
     * Checks if either of the walkers are dead,
     * if so, removes the step listener.
     * {@inheritDoc}
     */
    @Override
    public void preStep(StepEvent stepEvent) {
        if (walker.isDead() || otherWalker.isDead()) {
            if (!walker.isSolid() && !walker.isDead()) {
                walker.makeSolid();
            }
            Game.gameWorld.removeStepListener(this);
        }
    }
    /**
     * This method is called after the step event.
     * If the walker is not intersecting with the other walker,
     * it will attempt to become solid again.
     * {@inheritDoc}
     */
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
