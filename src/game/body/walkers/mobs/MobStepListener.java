package game.body.walkers.mobs;
// Imports
import game.animation.WalkerAnimationStepListener;
import game.body.walkers.PlayerWalker;
import game.enums.Direction;
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.GameWorld;
import game.enums.State;
import org.jbox2d.common.Vec2;

// Class
public class MobStepListener implements StepListener {
    // Fields
    private final MobWalker mob;
    private final GameWorld world;
    private final WalkerAnimationStepListener animations;
    private static final float CHASE_DISTANCE = 3.0f;

    // Constructor
    public MobStepListener(GameWorld world, MobWalker mob) {
        this.world = world;
        this.mob = mob;
        world.addStepListener(this);
        animations = new WalkerAnimationStepListener(mob);
    }

    // Override Methods
    @Override
    public void preStep(StepEvent stepEvent) {
        Vec2 pos = mob.getPosition();
        if (mob.getHit() || mob.isDead()) {
            mob.stopWalking();
        } else {
            if (nearViewX(pos, 20) && nearViewY(pos, 10) && !world.getPlayer().isDead()) {
                float playerDirection = getPlayerDirectionX(pos);
                if (playerDirection == 0) {
                    mob.stopWalking();
                    mob.attack(world.getPlayer());
                } else {
                    mob.setState(State.RUN);
                    mob.startWalking(playerDirection);
                }
            } else {
                if (mob.getLinearVelocity().x < 1 && mob.getLinearVelocity().y > -1) {
                    if (mob.getDirection() == Direction.RIGHT) mob.startWalking(2);
                    else mob.startWalking(-2);
                }

                if (pos.x >= (mob.ORIGIN_X) + 30) {
                    mob.startWalking(-2);
                    mob.setDirection(Direction.LEFT);
                } else if (pos.x <= (mob.ORIGIN_X) - 30) {
                    mob.startWalking(2);
                    mob.setDirection(Direction.RIGHT);
                }
            }
        }
        if (nearViewX(pos, 40)) {
            animations.step();
        }
    }

    @Override
    public void postStep(StepEvent stepEvent) {

    }

    private boolean nearViewX(Vec2 mobPos, float radius) {
        Vec2 playerPos = world.getPlayer().getPosition();
        return playerPos.x < (mobPos.x + radius) && playerPos.x > (mobPos.x - radius); // since the world width is 60, thus -30 < player.pos < 30 and +10 so things start before the player comes
    }

    private boolean nearViewY(Vec2 mobPos, float radius) {
        Vec2 playerPos = world.getPlayer().getPosition();
        return playerPos.y < (mobPos.y + radius) && playerPos.y > (mobPos.y - radius);
    }

    private int getPlayerDirectionX(Vec2 mobPos) {
        // variable Assignment
        float playerX = world.getPlayer().getPosition().x;
        boolean isPlayerLeft = playerX < mobPos.x;
        float dist;
        // main logic
        if (isPlayerLeft) {
            dist = (playerX + world.getPlayer().HALF_X) - (mobPos.x - mob.HALF_X);
        } else {
            dist = (playerX - world.getPlayer().HALF_X) - (mobPos.x + mob.HALF_X);
        }
        mob.setDirection(isPlayerLeft ? Direction.LEFT : Direction.RIGHT);
        // return logic
        if (isPlayerLeft) {
            return (dist > -CHASE_DISTANCE ? 0 : -5);
        } else {
            return (dist < CHASE_DISTANCE ? 0 : 5);
        }
    }

    public void remove() {
        animations.remove();
        world.removeStepListener(this);
    }
}
