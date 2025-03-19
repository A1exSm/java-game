package game.body.walkers.steplisteners;
// Imports
import game.core.GameWorld;
import game.body.walkers.mobs.MobWalker;
import game.enums.Direction;
import game.enums.State;
import org.jbox2d.common.Vec2;

// Class
public class AggressiveStepListener extends MobStepListener {
    // Fields
    protected final float CHASE_DISTANCE;
    // Constructor
    public AggressiveStepListener(MobWalker mob, GameWorld gameWorld, float chaseDistance) {
        super(mob, gameWorld);
        this.CHASE_DISTANCE = chaseDistance; // the distance at which the mob chases until (how close not how far), then attacks after reaching.
    }
    // Methods
    @Override
    public void handleMobMovement(Vec2 pos) {
        if (isPlayerInRange(pos, false)) {
            float playerDirectionX = getPlayerDirectionX(pos.x);
            if (playerDirectionX == 0) {
                mob.stopWalking();
                mob.attack(gameWorld.getPlayer());
            } else if (playerDirectionX == -5 || playerDirectionX == 5) {
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
        if ((isPlayerLeft && mob.getDirection() != Direction.LEFT ) || (!isPlayerLeft &&  mob.getDirection() != Direction.RIGHT)) {
            if (!isPlayerInRange(mob.getPosition(), true)) {
                return -1;
            }
        }
        mob.setDirection(isPlayerLeft ? Direction.LEFT : Direction.RIGHT);
        if (isPlayerLeft) {
            float dist = (PlayerX + gameWorld.getPlayer().HALF_X) - (mobX - mob.HALF_X);
            return (dist >  -CHASE_DISTANCE ? 0 : -5);
        } else {
            float dist = (PlayerX - gameWorld.getPlayer().HALF_X) - (mobX + mob.HALF_X);
            return (dist < CHASE_DISTANCE ? 0 : 5);
        }
    }
}
