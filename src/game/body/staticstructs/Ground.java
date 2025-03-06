package game.body.staticstructs;
// Imports
import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import game.core.GameWorld;
import org.jbox2d.common.Vec2;
// Class
public class Ground extends StaticBody {
    // Fields
    private final Vec2 halfDimensions;
    private final Vec2 originPos;
    private static int count = -1;
    {
        count ++;
    }
    // constructor
    public Ground(GameWorld gameWorld, Vec2 halfDimensions, Vec2 originPos) {
        super(gameWorld, new BoxShape(halfDimensions.x, halfDimensions.y));
        this.halfDimensions = halfDimensions;
        this.originPos = originPos;
        this.setPosition(originPos);
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
    // subClass - Will probs be moved to its own file if it grows, but most likely won't make it to later versions anyway.
    public static class Platform extends Ground {
        public Platform(GameWorld gameWorld, Vec2 originPos) {
            super(gameWorld, new Vec2(5, 0.5f), originPos);
            setName("Platform"+count);
        }
    }
}
