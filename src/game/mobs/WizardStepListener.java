package game.mobs;
// Imports
import game.animation.Direction;
import game.body.walkers.WizardWalker;
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.GameWorld;
import org.jbox2d.common.Vec2;

// Class
public class WizardStepListener implements StepListener {
    // Fields
    private final WizardWalker mob;
    private final GameWorld world;
    // Constructor
    public WizardStepListener(GameWorld world, WizardWalker mob ) {
        this.world = world;
        this.mob = mob;
    }
    // Override Methods
    @Override
    public void preStep(StepEvent stepEvent) {
        Vec2 pos = mob.getPosition();
        if (pos.x >= (mob.ORIGIN_X) + 30) {
            mob.startWalking(-2);
            mob.setDirection(Direction.LEFT);
        } else if (pos.x <= (mob.ORIGIN_X) - 30) {
            mob.startWalking(2);
            mob.setDirection(Direction.RIGHT);
        }
        if (nearView(pos)) {
            mob.animate();
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
