package game.body.walkers.steplisteners;
// Imports
import game.body.walkers.mobs.WormWalker;
import game.core.GameWorld;
import game.body.walkers.mobs.MobWalker;
import game.enums.Direction;
import game.enums.State;
import org.jbox2d.common.Vec2;

// Class
public final class AggressiveStepListener extends MobStepListener {
    // Fields
    private final float CHASE_DISTANCE;
    private final javax.swing.Timer attackTimer;
    // Constructor
    public AggressiveStepListener(MobWalker mob, GameWorld gameWorld, float chaseDistance) {
        super(mob, gameWorld);
        this.CHASE_DISTANCE = chaseDistance; // the distance at which the mob chases until (how close not how far), then attacks after reaching.
        attackTimer = new javax.swing.Timer(400, e -> attemptAttack(mob.getPosition().x));
        attackTimer.setRepeats(false);
    }
    // Methods
    @Override
    public void handleMobMovement(Vec2 pos) {
        if (!mob.isAttacking()) {
            if (isPlayerInRange(pos, false)) {
                float playerDirectionX = getPlayerDirectionX(pos.x);
                if (playerDirectionX == 0) {
                    mob.stopWalking();
                    if (mob instanceof WormWalker) {
                        mob.attack();
                    } else if (!attackTimer.isRunning()) {
                        attackTimer.start();
                        mob.attack();
                    }

                } else if (playerDirectionX == -5 || playerDirectionX == 5) {
                    mob.setState(State.RUN);
                    mob.startWalking(playerDirectionX);
                }
            } else {
                patrolArea(pos);
            }
        }
    }

    public int getPlayerDirectionX(float mobX) {
        float playerX = gameWorld.getPlayer().getPosition().x;
        boolean isPlayerLeft = playerX < mobX;
        if (isPlayerBehindX(mobX, playerX)) {
            if (!isPlayerInRange(mob.getPosition(), true)) {
                return -1;
            }
        }
        mob.setDirection(isPlayerLeft ? Direction.LEFT : Direction.RIGHT);
        return isInChasedDistanceX(mobX, playerX, isPlayerLeft);
    }

    private void attemptAttack(float mobX) {
        float playerX = gameWorld.getPlayer().getPosition().x;
        boolean isPlayerLeft = playerX < mobX;
        if (isPlayerBehindX(mobX, gameWorld.getPlayer().getPosition().x)) {
            return;
        }
        if (isInChasedDistanceX(mobX, playerX, isPlayerLeft) == 0) {
            mob.damagePlayer();
        }

    }

    private boolean isPlayerBehindX(float mobX, float playerX) {
        boolean isPlayerLeft = playerX < mobX;
        Direction direction = mob.getDirection();
        return (isPlayerLeft && direction != Direction.LEFT) || (!isPlayerLeft && direction != Direction.RIGHT);
    }

    private int isInChasedDistanceX(float mobX, float playerX, boolean isPlayerLeft) {
        float dist = (playerX) - (mobX); // removed the calculation of halfDimensions due to mobs turning ghostly, meaning they can be within that distance
        if (isPlayerLeft) {
            return (dist >  -CHASE_DISTANCE ? 0 : -5);
        } else {
            return (dist < CHASE_DISTANCE ? 0 : 5);
        }
    }
}
