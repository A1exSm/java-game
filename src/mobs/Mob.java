package mobs;
// Imports
import animation.Direction;
import animation.PlayerState;
import city.cs.engine.*;
import game.GameWorld;
import org.jbox2d.common.Vec2;

// Class
public class Mob extends Walker {
    // Fields
    private final Vec2 halfSize = new Vec2(1f, 2f);
    public float ORIGIN_X = -50;
    public float ORIGIN_Y = 2;
    public boolean isWalking = false;
    private final MobStepListener mobStepListener;
    // Constructor
    public Mob(GameWorld world) {
        super(world, new BoxShape(1,2));
//        new GhostlyFixture(this, new BoxShape(1,2, new Vec2(0, 1f)));
        this.setName("Wizard");
        setPosition(new Vec2(ORIGIN_X, ORIGIN_Y));
        mobStepListener = new MobStepListener(world, this);
        world.addStepListener(mobStepListener);
        detectPlayerCollision();
    }
    // Methods
    public Vec2 getHalfSize() {
        return halfSize;
    }
    private void detectPlayerCollision() {
        this.addCollisionListener(e -> {
            if (e.getOtherBody().getName() != null) {
                if (e.getOtherBody().getName().equals("Player")) { // Is the object we collided with the player?
                    if (e.getNormal().y == 0) { // if it is, are we running into the player? (y == 0 suggests that the collision was on the x not from above - y)
                        if (e.getNormal().x < 0) { // if so lets walk in the opposite direction to the collision
                            this.startWalking(2);
                            mobStepListener.lastDirection = Direction.RIGHT;
                        } else {
                            this.startWalking(-2);
                            mobStepListener.lastDirection = Direction.LEFT;
                        }
                    }
                }
            }
        });
    }
    protected void animation(PlayerState mobState, Direction direction) {
        removeAllImages();
        AttachedImage image;
        switch (mobState) {
            case IDLE -> {
                image = new AttachedImage(this,new BodyImage("data/WizardGifs/IDLE.gif", 18f),1f, 0, new Vec2(0, 1));
                if (direction == Direction.LEFT) image.flipHorizontal();
            }
            default -> {}
        }
    }
}
