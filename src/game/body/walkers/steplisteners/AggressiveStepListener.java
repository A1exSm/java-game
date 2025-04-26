package game.body.walkers.steplisteners;
// Imports
import game.body.walkers.mobs.WormWalker;
import game.core.GameWorld;
import game.body.walkers.mobs.MobWalker;
import game.enums.Direction;
import game.enums.State;
import org.jbox2d.common.Vec2;
// Class
/**
 * An aggressive step listener for hostile/aggressive Mobs.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 03-03-2025
 */
public final class AggressiveStepListener extends MobStepListener {
    // Fields
    private final float CHASE_DISTANCE;
    private final javax.swing.Timer attackTimer;
    // Constructor
    /**
     * Provides movement and reactions for hostile/aggressive Mobs.
     * @param mob the mob to listen to
     * @param gameWorld the game world to listen to
     * @param chaseDistance the distance at which the mob chases until (how close not how far), then attacks after reaching.
     */
    public AggressiveStepListener(MobWalker mob, GameWorld gameWorld, float chaseDistance) {
        super(mob, gameWorld);
        this.CHASE_DISTANCE = chaseDistance; // the distance at which the mob chases until (how close not how far), then attacks after reaching.
        attackTimer = new javax.swing.Timer(400, e -> attemptAttack(mob.getPosition().x));
        attackTimer.setRepeats(false);
    }
    // Methods
    /**
     * {@inheritDoc}
     */
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
    /**
     * {@inheritDoc}
     */
    @Override
    protected float getPlayerDirectionX(float mobX) {
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
    /**
     * attacks player if within range and facing the player.
     * @param mobX the x position of the mob
     */
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
    /**
     * Checks if player is behind the mob.
     * @param mobX the mobs x-Positon
     * @param playerX the players x-Position
     * @return {@code true} if the player is behind the mob, {@code false} otherwise
     */
    private boolean isPlayerBehindX(float mobX, float playerX) {
        boolean isPlayerLeft = playerX < mobX;
        Direction direction = mob.getDirection();
        return (isPlayerLeft && direction != Direction.LEFT) || (!isPlayerLeft && direction != Direction.RIGHT);
    }
    /**
     * Checks if the player is within the chase distance of the mob,
     * and returns a float representing the speed and direction of the mob.
     * @param mobX the x position of the mob
     * @param playerX the x position of the player
     * @param isPlayerLeft boolean value for if the player is left of the mob
     * @return if to the right {@code 5}, if to the left {@code -5}, if within range {@code 0}
     */
    private float isInChasedDistanceX(float mobX, float playerX, boolean isPlayerLeft) {
        float dist = (playerX) - (mobX); // removed the calculation of halfDimensions due to mobs turning ghostly, meaning they can be within that distance
        if (isPlayerLeft) {
            return (dist >  -CHASE_DISTANCE ? 0 : -5);
        } else {
            return (dist < CHASE_DISTANCE ? 0 : 5);
        }
    }
}
