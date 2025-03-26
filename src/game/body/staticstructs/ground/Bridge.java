package game.body.staticstructs.ground;
// Imports
import city.cs.engine.AttachedImage;
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import game.core.GameWorld;
import game.enums.Direction;
import game.exceptions.InvalidBridgeException;
import org.jbox2d.common.Vec2;
import java.util.HashMap;
/**
 * Level bridge between two {@link GroundFrame} instances.
 */
// Class
public class Bridge extends GroundFrame {
    // Fields
    private final HashMap<String, AttachedImage> bridgeParts = new HashMap<>();
    public static final float MAX_DISTANCE = 500f;
    public static final float MIN_DISTANCE = 10f;
    private final GroundFrame bridgeStart;
    private final GroundFrame bridgeEnd;
    private SolidFixture bridgeFixture = null;

    // Constructor
    public Bridge(GameWorld gameWorld, GroundFrame bridgeStart, GroundFrame bridgeEnd) {
        super(gameWorld);
        this.bridgeStart = bridgeStart;
        this.bridgeEnd = bridgeEnd;
        validateBridge();
        buildBridge();
    }

    // Methods | private | setup
    private void buildBridge() {
        float dist;
        originPos.y = bridgeStart.originPos.y + bridgeStart.halfDimensions.y;
        halfDimensions.y = 2f;
        Direction facingDirection = calcDirection();
        float[] xPos = new float[2]; // float[0] = xStart, float[1] = xEnd
        String[] path = new String[2]; // path[0] = start, path[1] = end

        if (facingDirection.equals(Direction.RIGHT)) {
            dist = Math.abs((bridgeStart.originPos.x + bridgeStart.halfDimensions.x) - (bridgeEnd.originPos.x - bridgeEnd.halfDimensions.x));
            originPos.x = (bridgeStart.originPos.x + bridgeStart.halfDimensions.x) + (dist / 2);
            xPos[0] = -(dist / 2) + 1.26f;
            xPos[1] = (dist / 2) - 1.26f;
            path[0] = "left";
            path[1] = "right";
        } else {
            dist = Math.abs((bridgeStart.originPos.x - bridgeStart.halfDimensions.x) - (bridgeEnd.originPos.x + bridgeEnd.halfDimensions.x));
            originPos.x = (bridgeStart.originPos.x - bridgeStart.halfDimensions.x) - (dist / 2);
            xPos[0] = (dist / 2) - 1.26f;
            xPos[1] = -(dist / 2) + 1.26f;
            path[0] = "right";
            path[1] = "left";
        }
        bridgeFixture = new SolidFixture(this, new BoxShape(dist / 2, halfDimensions.y, new Vec2(0, -1.5f)));
        halfDimensions.x = dist / 2;
        setPosition(new Vec2(originPos.x, originPos.y));
        putBridgePart("bridgeStart", path[0], xPos[0], -1.5f);
        middleBridge();
        putBridgePart("bridgeEnd", path[1], xPos[1], -1.5f);

    }

    private void middleBridge() {
        int count = 0;
        float counterA = 0;
        float counterB = 0;
        // side A
        for (float i = -halfDimensions.x + 6.0f; i < 0; i += 4) {
            if (i + 2 > 0) {break;}
            putBridgePart("bridgePartMiddle" + count++, "middle", i, -1.38f);
            counterA = i;
        }
        // side B
        for (float i = halfDimensions.x - 4.0f; i > 0; i -= 4) {
            if (i - 2 < 0) {break;}
            putBridgePart("bridgePartMiddle" + count++, "middle", i, -1.38f);
            counterB = i;
        }
        // filling the difference
        if (counterA != 0) {
            counterB -= 2.44f;
            while (counterB > counterA + 1f) {
                putBridgePart("bridgePiece" + count++, "piece", counterB, -1.5f);
                counterB -= 0.68f;
            }
        }
    }

    private void putBridgePart(String name, String path, float x, float y) {
        bridgeParts.put(name, new AttachedImage(this, new BodyImage("data/Magic_Cliffs/bridge/" + path + ".png"), 4f, 0, new Vec2(x, y)));
    }

    private Direction calcDirection() {
        if (bridgeStart.getOriginPos().x < bridgeEnd.getOriginPos().x) {
            return Direction.RIGHT;
        } else {
            return Direction.LEFT;
        }
    }

    // Methods | Private | Exception handling
    private void validateBridge() {
        if (bridgeStart == null || bridgeEnd == null) {
            throw new InvalidBridgeException("BRIDGE_STOP_START", bridgeStart, bridgeEnd, 0);
        }
        if (bridgeStart.yTop != bridgeEnd.yTop) {
            throw new InvalidBridgeException("BRIDGE_HEIGHT_MISMATCH", bridgeStart, bridgeEnd, 0);
        }
        float dist = Math.abs(bridgeStart.getOriginPos().x - bridgeEnd.getOriginPos().x);
        if (dist > MAX_DISTANCE || dist < MIN_DISTANCE) {
            throw new InvalidBridgeException("BRIDGE_DISTANCE", bridgeStart, bridgeEnd, dist);
        }
    }
    // Methods | Public
    public void becomeGhostly() {
        if (bridgeFixture == null) {
            System.err.println("Warning: becomeGhostly called on an already ghostly bridge");
            return;
        }
        bridgeFixture.destroy();
        bridgeFixture = null;
        removeAllImages();
    }
    public void becomeSolid() {
        if (bridgeFixture != null) {
            System.err.println("Warning: becomeSolid called on an already solid bridge");
            return;
        }
        buildBridge();
    }
}
