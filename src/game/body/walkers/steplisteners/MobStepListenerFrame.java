package game.body.walkers.steplisteners;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import org.jbox2d.common.Vec2;

/**
 * Interface for the StepListener, used to implement WalkerBehaviour
 */
public interface MobStepListenerFrame extends StepListener {
    void preStep(StepEvent stepEvent);

    void postStep(StepEvent stepEvent);

    void handleMobMovement(Vec2 pos);

    boolean isPlayerInRange(Vec2 pos, boolean playerBehind);

    void patrolArea(Vec2 pos);

    boolean nearViewX(float xPos, float radiusX);

    boolean nearViewY(float yPos, float radiusY);

    void remove();
}
