package game.mobs;
// Imports
import game.animation.Direction;
import game.animation.PlayerState;
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.GameWorld;
import org.jbox2d.common.Vec2;

// Class
public class MobStepListener implements StepListener {
    // Fields
    private final Mob mob;
    private final GameWorld world;
    protected Direction lastDirection = Direction.RIGHT;
    // Constructor
    public MobStepListener(GameWorld world, Mob mob ) {
        this.world = world;
        this.mob = mob;
    }
    // Override Methods
    @Override
    public void preStep(StepEvent stepEvent) {
        Vec2 pos = mob.getPosition();
        if (pos.x >= (mob.ORIGIN_X) + 30) {
            mob.startWalking(-2);
            lastDirection = Direction.LEFT;
        } else if (pos.x <= (mob.ORIGIN_X) - 30) {
            mob.startWalking(2);
            lastDirection = Direction.RIGHT;
        } else if (mob.getLinearVelocity().x < 1 && mob.getLinearVelocity().x > -1) {
            mob.setPosition(new Vec2(mob.getPosition().x + 1, mob.getPosition().y)); // has a tendency to get stuck for no apparent reason which debugging can explain so we have to treat it like a baby and free it
        }
        if (nearView(pos)) {
            mob.animation(PlayerState.IDLE, lastDirection);
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
