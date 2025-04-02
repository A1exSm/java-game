package game.body.staticstructs.ground.magicCliffs;
// Imports
import city.cs.engine.*;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import game.enums.Direction;
import game.exceptions.BridgeCollisionException;
import game.exceptions.BridgeIllegalHeightException;
import game.exceptions.BridgeNullPointException;
import game.exceptions.BridgeUnacceptableRangeException;
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
    public static final float MIN_DISTANCE = 40f;
    private final GroundFrame bridgeStart;
    private final GroundFrame bridgeEnd;
    private SolidFixture bridgeFixture = null;
    private final Direction facingDirection;

    // Constructor
    public Bridge(GameWorld gameWorld, GroundFrame bridgeStart, GroundFrame bridgeEnd) {
        super(gameWorld);
        this.bridgeStart = bridgeStart;
        this.bridgeEnd = bridgeEnd;
        facingDirection = calcDirection();
        if (validateBridge()) {
            buildBridge();
        } else {
            destroy();
        }
    }

    // Methods | private | setup
    private void buildBridge() {
        float dist;
        halfDimensions.y = 2f;
        float[] xPos = new float[2]; // float[0] = xStart, float[1] = xEnd
        String[] path = new String[2]; // path[0] = start, path[1] = end

        if (facingDirection.equals(Direction.RIGHT)) {
            dist = Math.abs((bridgeStart.getOriginPos().x + bridgeStart.getHalfDimensions().x) - (bridgeEnd.getOriginPos().x - bridgeEnd.getHalfDimensions().x));
            originPos.x = (bridgeStart.getOriginPos().x + bridgeStart.getHalfDimensions().x) + (dist / 2);
            xPos[0] = -(dist / 2) + 1.26f;
            xPos[1] = (dist / 2) - 1.26f;
            path[0] = "left";
            path[1] = "right";
        } else {
            dist = Math.abs((bridgeStart.getOriginPos().x - bridgeStart.getHalfDimensions().x) - (bridgeEnd.getOriginPos().x + bridgeEnd.getHalfDimensions().x));
            originPos.x = (bridgeStart.getOriginPos().x - bridgeStart.getHalfDimensions().x) - (dist / 2);
            xPos[0] = (dist / 2) - 1.26f;
            xPos[1] = -(dist / 2) + 1.26f;
            path[0] = "right";
            path[1] = "left";
        }
        bridgeFixture = new SolidFixture(this, new BoxShape((dist / 2) - 0.1f, halfDimensions.y, new Vec2(0, 0)));
        halfDimensions.x = dist / 2;
        setPosition(new Vec2(originPos.x,(bridgeStart.getOriginPos().y + bridgeStart.getHalfDimensions().y) - 1.5f));
        putBridgePart("bridgeStart", path[0], xPos[0], 0);
        middleBridge();
        putBridgePart("bridgeEnd", path[1], xPos[1], 0);
    }

    private void middleBridge() {
        int count = 0;
        float counterA = 0;
        float counterB = 0;
        // side A
        for (float i = -halfDimensions.x + 6.0f; i < 0; i += 4) {
            if (i + 2 > 0) {break;}
            putBridgePart("bridgePartMiddle" + count++, "middle", i, 0.12f);
            counterA = i;
        }
        // side B
        for (float i = halfDimensions.x - 4.0f; i > 0; i -= 4) {
            if (i - 2 < 0) {break;}
            putBridgePart("bridgePartMiddle" + count++, "middle", i, 0.12f);
            counterB = i;
        }
        // filling the difference
        if (counterA != 0) {
            counterB -= 2.44f;
            while (counterB > counterA + 1f) {
                putBridgePart("bridgePiece" + count++, "piece", counterB, 0);
                counterB -= 0.68f;
            }
        }
    }

    private void putBridgePart(String name, String path, float x, float y) {
        bridgeParts.put(name, new AttachedImage(this, new BodyImage("data/MagicCliffs/bridge/" + path + ".png"), 4f, 0, new Vec2(x, y)));
    }

    private Direction calcDirection() {
        if (bridgeStart.getOriginPos().x < bridgeEnd.getOriginPos().x) {
            return Direction.RIGHT;
        } else {
            return Direction.LEFT;
        }
    }

    // Methods | Private | Exception handling
    protected boolean validateBridge() {
        boolean valid = true;
        try {
            BridgeUnacceptableRangeException.CheckBridgeDistance(bridgeStart, bridgeEnd);
        } catch (BridgeUnacceptableRangeException e) {
            System.err.println(e.getMessage() + ". Destroying bridge!");
            if (valid) {valid = false;}
        }
        try {
            BridgeNullPointException.CheckBridgeNullPoint(bridgeStart, bridgeEnd);
        } catch (BridgeNullPointException e) {
            System.err.println(e.getMessage() + ". Destroying bridge!");
            if (valid) {valid = false;}
        }
        try {
            BridgeIllegalHeightException.CheckBridgeHeightMismatch(bridgeStart, bridgeEnd);
        } catch (BridgeIllegalHeightException e) {
            System.err.println(e.getMessage() + ". Destroying bridge!");
            if (valid) {valid = false;}
        }
        try {
            BridgeCollisionException.CheckBridgeCollision(halfDimensions, bridgeStart, bridgeEnd);
        } catch (BridgeCollisionException e) {
            System.err.println(e.getMessage() + ". Destroying bridge!");
            if (valid) {valid = false;}
        }
        return valid;
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
