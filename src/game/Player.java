package game;
// Imports
import animation.AnimationStepListener;
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.DynamicBody;
import city.cs.engine.Walker;
import org.jbox2d.common.Vec2;
// Class
class Player extends Walker {
    // Fields
    private final Vec2 halfSize = new Vec2(1f, 2f);
    protected boolean isAttacking = false; // it would see that ATTACK1 goes x +/-7 outwards
    // Constructor
    protected Player(GameWorld world) {
        super(world, new BoxShape(1, 2));
        setName("Player");
        setPosition(new Vec2(0, 3f));
//        action("IDLEr");
    }
    protected void attack() {
        if (!isAttacking) {
            isAttacking = true;
        }
    }
    private void isHitting() { // 6
        if (isAttacking) {
            for (DynamicBody body : getWorld().getDynamicBodies()) {
                if (AnimationStepListener.getDirection() == animation.Direction.RIGHT) {
                    if (body.getPosition().x > getPosition().x && body.getPosition().x < getPosition().x + 6) {
                        body.getName();
                    }
                } else {
                    if (body.getPosition().x < getPosition().x && body.getPosition().x > getPosition().x - 7) {
                        System.out.println("HIT");
                    }
                }
//                if (body.getPosition().x < )
            }
        }
    }
    // Getters
    public Vec2 getHalfSize() { // will be used to check if mouse is within players hit box :)
        return halfSize;
    }
}


