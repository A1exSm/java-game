package game.body.staticstructs.ground;
// Imports
import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import game.core.GameWorld;
import org.jbox2d.common.Vec2;
// Class
public abstract class GroundFrame extends StaticBody {
    // Fields
    protected final Vec2 halfDimensions;
    protected final Vec2 originPos;
    protected float yTop = 0;
    private static int count = -1;
    // constructor
    public GroundFrame(GameWorld gameWorld, Vec2 halfDimensions, Vec2 originPos) {
        super(gameWorld, new BoxShape(halfDimensions.x, halfDimensions.y));
        this.halfDimensions = halfDimensions;
        this.originPos = originPos;
        this.setPosition(originPos);
        count ++;
        this.setName("Ground"+count);
    }
    public GroundFrame(GameWorld gameWorld) {
        super(gameWorld);
        this.halfDimensions = new Vec2();
        this.originPos = new Vec2();
        count ++;
        this.setName("Ground"+count);
    }
    // Getters
    public Vec2 getHalfDimensions() {
        return halfDimensions;
    }
    public Vec2 getOriginPos() {
        return originPos;
    }
    public static int getCount() {
        return count;
    }
    public float getYTop() {
        return yTop;
    }
    // Methods | Protected
    protected void resetYTop() {
        yTop = originPos.y + halfDimensions.y;
    }
}
