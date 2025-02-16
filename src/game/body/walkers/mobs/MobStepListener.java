package game.body.walkers.mobs;
// Imports
import game.animation.WalkerAnimationStepListener;
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
    // Constructor
    public MobStepListener(GameWorld world, MobWalker mob ) {
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
            if (mob.getLinearVelocity().x < 1 && mob.getLinearVelocity().y > -1 ) {
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
        if (nearView(pos)) {
            animations.step();
        }
    }

    @Override
    public void postStep(StepEvent stepEvent) {

    }
    private boolean nearView(Vec2 mobPos) {
        Vec2 playerPos = world.getPlayer().getPosition();
        return playerPos.x < (mobPos.x + 40) && playerPos.x > (mobPos.x - 40); // since the world width is 60, thus -30 < player.pos < 30 and +10 so things start before the player comes
    }
    // Methods
    public void remove() {
        animations.remove();
        world.removeStepListener(this);
    }
}
