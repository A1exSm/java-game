package game.body.staticstructs.ground;
// Imports
import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import game.core.GameWorld;
import game.core.console.Console;
import org.jbox2d.common.Vec2;
// Class
public abstract class GroundFrame extends StaticBody {
    // Fields
    protected final Vec2 halfDimensions;
    protected final Vec2 originPos;
    protected float yTop = 0;
    private static int count = -1;
    private boolean propEnabled = false;
    private Prop[] propNodes;
    // constructor
    public GroundFrame(GameWorld gameWorld, Vec2 halfDimensions, Vec2 originPos) {
        super(gameWorld, new BoxShape(halfDimensions.x, halfDimensions.y));
        this.halfDimensions = halfDimensions;
        this.originPos = originPos;
        this.setPosition(originPos);
        this.setName("Ground"+(++count));
    }
    public GroundFrame(GameWorld gameWorld) {
        super(gameWorld);
        this.halfDimensions = new Vec2();
        this.originPos = new Vec2();
        this.setName("Ground"+(++count));
    }
    // override
    @Override
    public void setPosition(Vec2 pos) {
        super.setPosition(pos);
        if (pos != originPos) {
            originPos.x = pos.x;
            originPos.y = pos.y;
        }
        resetYTop();
    }

    // Getters
    public Vec2 getHalfDimensions() {
        return halfDimensions;
    }
    public Vec2 getOriginPos() {
        return originPos;
    }
    public float getYTop() {
        return yTop;
    }
    // Methods | Public
    public void resetYTop() {
        yTop = originPos.y + halfDimensions.y;
    }
    public void setPropEnabled(boolean propEnabled, int nodeCount) {
        this.propEnabled = propEnabled;
        if (propNodes == null) {
            propNodes = new Prop[nodeCount];
        }

    }
    public boolean isPropEnabled() {
        return propEnabled;
    }
    public Prop addProp(Prop prop, int index) {
        if (!propEnabled) {
            Console.warning("Prop is not enabled for this ground frame, deleting prop!");
            prop.destroy();
            return prop;
        }
        if (index > propNodes.length || index < 0) {
            if (index != -1) {
                Console.warning("Prop index out of bounds, deleting prop!");
                prop.destroy();
                return prop;
            }
            for (int i = 0; i < propNodes.length; i++) {
                if (propNodes[i] == null) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                Console.warning("No space for prop, deleting prop!");
                prop.destroy();
                return prop;
            }
        }
        if (propNodes[index] != null) {
            Console.warning("Prop already exists at index " + index + ", deleting new prop!");
            prop.destroy();
            return prop;
        }
        float space = (getHalfDimensions().x*2)/propNodes.length;
        prop.setPosition(new Vec2((originPos.x - halfDimensions.x) + space/2 + (index * space), yTop + prop.getImage().getHalfDimensions().y));
        propNodes[index] = prop;
        return prop;
    }
    public void removeProp(int index) {
        if (propNodes[index] == null) {
            Console.warning("Prop does not exist at index " + index + ", cannot remove prop!");
            return;
        }
        propNodes[index].destroy();
        propNodes[index] = null;
    }
    // Methods | Public | Static
    public static float randIntRange(float min, float max) {
        return min + (float) (Math.random() * (max - min));
    }
    // Methods | Public | Abstract
    public abstract void paint();
}
