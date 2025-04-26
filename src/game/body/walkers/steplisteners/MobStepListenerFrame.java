package game.body.walkers.steplisteners;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import org.jbox2d.common.Vec2;
/**
 * Interface for the StepListener, used to implement WalkerBehaviour
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 03-03-2025
 */
public interface MobStepListenerFrame extends StepListener {
    /**
     * {@inheritDoc}
     */
    void preStep(StepEvent stepEvent);
    /**
     * {@inheritDoc}
     */
    void postStep(StepEvent stepEvent);
    /**
     * Handles the core movement and behaviour logic
     * checks and responses for the mob.
     * @param pos the position of the mob
     */
    void handleMobMovement(Vec2 pos);
    /**
     * Checks if mob is in range of player
     * @param pos the position of the mob
     * @param playerBehind boolean value for if the player is behind the mob
     * @return {@code true} if the mob is in range, {@code false} otherwise
     */
    boolean isPlayerInRange(Vec2 pos, boolean playerBehind);
    /**
     * Has the mob walk within a given range,
     * and change its direction if it reaches the edge of the range.
     * Handles any additional logic to the patrol
     * @param pos the position of the mob
     */
    void patrolArea(Vec2 pos);
    /**
     * Checks if the player is within radiusX of the mob
     * @param mobX the x position of the mob
     * @param radiusX the radius to check for
     * @return {@code true} if the player is within radiusX, {@code false} otherwise
     */
    boolean nearViewX(float mobX, float radiusX);
    /**
     * Checks if the player is within radiusY of the mob
     * @param mobY the y position of the mob
     * @param radiusY the radius to check for
     * @return {@code true} if the player is within radiusY, {@code false} otherwise
     */
    boolean nearViewY(float mobY, float radiusY);
    /**
     * A remove function to allow
     * for custom and clean removal
     * of the listener
     */
    void remove();
}
