package game.body.walkers.steplisteners;
// Imports
import game.GameWorld;
import game.body.walkers.mobs.MobWalker;
import game.enums.Direction;
import game.enums.State;
import org.jbox2d.common.Vec2;

// Class
public class AggressiveStepListener extends MobStepListener implements MobStepListenerFrame {
    // Fields
    // Constructor
    public AggressiveStepListener(MobWalker mob, GameWorld gameWorld) {
        super(mob, gameWorld);
    }
    // Methods
    @Override
    public void handleMobMovement(Vec2 pos) {
        if (isPlayerInRange(pos)) {
            float playerDirectionX = getPlayerDirectionX(pos.x);
            if (playerDirectionX == 0) {
                mob.stopWalking();
                mob.attack(gameWorld.getPlayer());
            } else {
                mob.setState(State.RUN);
                mob.startWalking(playerDirectionX);
            }
        } else {
            patrolArea(pos);
        }
    }

    public int getPlayerDirectionX(float mobX) {
        float PlayerX = gameWorld.getPlayer().getPosition().x;
        boolean isPlayerLeft = PlayerX < mobX;
        mob.setDirection(isPlayerLeft ? Direction.LEFT : Direction.RIGHT);
        if (isPlayerLeft) {
            float dist = (PlayerX + gameWorld.getPlayer().HALF_X) - (mobX - mob.HALF_X);
            return (dist > -CHASE_DISTANCE ? 0 : -5);
        } else {
            float dist = (PlayerX - gameWorld.getPlayer().HALF_X) - (mobX + mob.HALF_X);
            return (dist < CHASE_DISTANCE ? 0 : 5);
        }
    }
}
