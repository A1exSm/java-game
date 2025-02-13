package game.body.walkers.mobs;
// Imports
import game.enums.Direction;
import city.cs.engine.*;
import game.GameWorld;
import game.body.walkers.WalkerFrame;
import org.jbox2d.common.Vec2;

// Class
public class MobWalker extends WalkerFrame {
    // Fields
    public final float HALF_X;
    public final float HALF_Y;
    private static int mobCount = -1;

    {
        mobCount++;
    }

    // Constructor
    public MobWalker(GameWorld gameWorld, BoxShape boxShape, Vec2 origin, Boolean patroller) {
        super(gameWorld, boxShape, origin);

        HALF_X = origin.x;
        HALF_Y = origin.y;

        this.setName("Mob" + mobCount);

        if (patroller) {
            patrolCollisions();
        }

        startWalking(2);
        MobStepListener mobStepListener = new MobStepListener(gameWorld, this);
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

    protected void animate() {
        removeAllImages();
        BodyImage bodyImage = new BodyImage("data/WizardGifs/IDLE.gif", 18f);
        switch (getState()) {
            case IDLE -> {
                AttachedImage image = new AttachedImage(this, bodyImage, 1f, 0, new Vec2(0, 1));
                if (getDirection() == Direction.LEFT) image.flipHorizontal();
            }
            default -> {
            }
        }
    }

}
