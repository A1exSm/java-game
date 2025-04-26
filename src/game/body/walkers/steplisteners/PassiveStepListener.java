package game.body.walkers.steplisteners;
// Imports
import game.core.GameWorld;
import game.body.walkers.mobs.MobWalker;
import org.jbox2d.common.Vec2;
// Class
/**
 * A passive step listener for non-hostile/passive Mobs.
 * As of 25-04-2025, there is no use for this as there is no peace and love in my game.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 03-03-2025
 */
public final class PassiveStepListener extends MobStepListener {
    // Constructor
    /**
     * Provides movement and reactions for non-hostile/passive Mobs.
     * @param mob the mob to listen to
     * @param gameWorld the game world to listen to
     */
    public PassiveStepListener(MobWalker mob, GameWorld gameWorld) {
        super(mob, gameWorld);
    }
    // Methods
    /**
     * {@inheritDoc}
     */
    @Override
    public void handleMobMovement(Vec2 pos) {
            patrolArea(pos);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected float getPlayerDirectionX(float mobX) {
            return -5; // move left
    }
}
