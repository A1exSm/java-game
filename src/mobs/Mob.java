package mobs;
// Imports

import city.cs.engine.BoxShape;
import city.cs.engine.Walker;
import game.GameWorld;
import org.jbox2d.common.Vec2;

// Class
public class Mob extends Walker {
    // Fields
    private final Vec2 halfSize = new Vec2(1f, 2f);
    public float ORIGIN_X = 5;
    public float ORIGIN_Y = 2;
    public boolean isWalking = false;
    // Constructor
    public Mob(GameWorld world) {
        super(world, new BoxShape(1,2));
        setPosition(new Vec2(ORIGIN_X, ORIGIN_Y));
        world.addStepListener(new MobStepListener(world, this));
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
                        } else {
                            this.startWalking(-2);
                        }
                    }
                }
            }
        });
    }
}
