package game.body.walkers;
// Imports
import game.animation.Direction;
import game.animation.PlayerState;
import city.cs.engine.*;
import game.GameWorld;
import game.mobs.WizardStepListener;
import org.jbox2d.common.Vec2;

// Class
public class WizardWalker extends WalkerFrame {
    // Fields
    public static final float HALF_X = 1;
    public static final float HALF_Y = 2;
    private static int wizardCount = -1;
    private final WizardStepListener stepListener;
    {
        wizardCount++;
    }
    // Constructor
    public WizardWalker(GameWorld gameWorld, Vec2 origin) {
        super(gameWorld, new BoxShape(1,2), origin);
        setName("Wizard"+wizardCount);
        stepListener = new WizardStepListener(gameWorld, this);
        gameWorld.addStepListener(stepListener);
        patrolCollisions();
        startWalking(2);
    }

    // Methods
    private void patrolCollisions() {
        this.addCollisionListener( e -> {
            Vec2 normal = e.getNormal();
            if (normal.y == 0) {
                if (e.getOtherBody().getName().equals("Player") || getWorld().getStaticBodies().contains(e.getOtherBody())) { // checks if colliding with player and if the collision is from the side not top.
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

    public void animate() {
        removeAllImages();
        BodyImage bodyImage = new BodyImage("data/WizardGifs/IDLE.gif",18);
        if (getState() == PlayerState.IDLE) {
            AttachedImage image = new AttachedImage(this, bodyImage, 1, 0, new Vec2(0, 1));
            if (getDirection() == Direction.LEFT) image.flipHorizontal();
        }

    }
}
