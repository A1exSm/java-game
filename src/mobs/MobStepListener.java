package mobs;
// Imports
import animation.Direction;
import animation.PlayerState;
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.GameWorld;
import org.jbox2d.common.Vec2;

// Class
public class MobStepListener implements StepListener {
    // Fields
    private final Mob mob;
    private final GameWorld world;
    protected Direction lastDirection;
    // Constructor
    public MobStepListener(GameWorld world, Mob mob ) {
        this.world = world;
        this.mob = mob;
    }
    // Override Methods
    @Override
    public void preStep(StepEvent stepEvent) {
        Vec2 pos = mob.getPosition();
        if (nearView(pos)) {
            if (pos.x == mob.ORIGIN_X || !mob.isWalking) {
                mob.startWalking(2);
                lastDirection = Direction.RIGHT;
            } else if (pos.x + 2 > (mob.ORIGIN_X + 1f) + 30) {
                mob.startWalking(-2);
                lastDirection = Direction.LEFT;
            } else if (pos.x - 2 < (mob.ORIGIN_X - 1f) - 30) {
                mob.startWalking(2);
                lastDirection = Direction.RIGHT;
            }
            mob.isWalking = true;
            mob.animation(PlayerState.IDLE, lastDirection);
        } else {
            mob.stopWalking();
            mob.isWalking = false;
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
}
