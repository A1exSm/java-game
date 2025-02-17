package game.body.walkers.mobs;
// Imports
import game.body.walkers.PlayerWalker;
import game.enums.Direction;
import city.cs.engine.*;
import game.GameWorld;
import game.body.walkers.WalkerFrame;
import game.enums.Walkers;
import org.jbox2d.common.Vec2;

// Class
public class MobWalker extends WalkerFrame {
    // Fields
    public final float HALF_X;
    public final float HALF_Y;
    private static int mobCount = -1;
    private final MobStepListener mobStepListener;

    {
        mobCount++;
    }

    // Constructor
    public MobWalker(GameWorld gameWorld, BoxShape boxShape, Vec2 origin, Boolean patroller, Walkers mobType) {
        super(gameWorld, boxShape, origin, mobType);
        if (mobType == Walkers.WIZARD) {
            HALF_X = WizardWalker.HALF_X;
            HALF_Y = WizardWalker.HALF_Y;
        } else {
            HALF_X = 0.0f;
            HALF_Y = 0.0f;
        }

        this.setName("Mob" + mobCount);

        if (patroller) {
            patrolCollisions();
        }

        startWalking(2);
        mobStepListener = new MobStepListener(gameWorld, this);
    }
    // Methods

    private void patrolCollisions() {
        this.addCollisionListener(e -> {
            Vec2 normal = e.getNormal();
            if (normal.y == 0) {
                if (e.getOtherBody().getName().equals("Player") || getWorld().getStaticBodies().contains(e.getOtherBody()) || getGameWorld().getWizards().contains(e.getOtherBody())) {
                    if (normal.x < 0) {
                        setDirection(Direction.RIGHT);
                        startWalking(2);
                    } else {
                        setDirection(Direction.LEFT);
                        startWalking(-2);
                    }
                }
            }
        });
    }

    public void attack(PlayerWalker player) {
        if (!getCooldown() && !getHit()) {
            toggleActionCoolDown();
            toggleOnAttack();
            player.takeDamage(100, getWalkerType());
        }
    }


    @Override
    public void die() {
        mobStepListener.remove();
        destroy();
    }

}
