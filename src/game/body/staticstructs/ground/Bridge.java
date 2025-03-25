package game.body.staticstructs.ground;
// Imports
import city.cs.engine.AttachedImage;
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import game.body.fixtures.BridgePart;
import game.core.GameWorld;
import game.enums.Direction;
import game.exceptions.InvalidBridgeException;
import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
// Class
public class Bridge extends GroundFrame {
    // Fields
    private final HashMap<BridgePart, AttachedImage> bridgeParts = new HashMap<>();
    public static final float MAX_DISTANCE = 500f;
    public static final float MIN_DISTANCE = 10f;
    private final GroundFrame bridgeStart;
    private final GroundFrame bridgeEnd;
    // Constructor
    public Bridge(GameWorld gameWorld, GroundFrame bridgeStart, GroundFrame bridgeEnd) {
        super(gameWorld);
        this.bridgeStart = bridgeStart;
        this.bridgeEnd = bridgeEnd;
        exceptionHandling();
        buildBridge();


    }
    // Methods | private | setup
    private void buildBridge() {
        float dist;
        originPos.y = bridgeStart.originPos.y + bridgeStart.halfDimensions.y;
        halfDimensions.y = 2f;// where halfDimensions.y = 0.5f, centre.y = 0;
        float xStart;
        float xEnd;
        Direction startSide = calcDirection();
        if (startSide.equals(Direction.RIGHT)) {
            dist = Math.abs((bridgeStart.originPos.x + bridgeStart.halfDimensions.x) - (bridgeEnd.originPos.x - bridgeEnd.halfDimensions.x));
            xStart = (bridgeStart.originPos.x + bridgeStart.halfDimensions.x);
            xEnd = (bridgeEnd.originPos.x - bridgeEnd.halfDimensions.x);
            originPos.x = xStart + (dist / 2);
        } else {
            dist= Math.abs((bridgeStart.originPos.x - bridgeStart.halfDimensions.x) - (bridgeEnd.originPos.x + bridgeEnd.halfDimensions.x));
            xStart = (bridgeStart.originPos.x - bridgeStart.halfDimensions.x);
            xEnd = (bridgeEnd.originPos.x + bridgeEnd.halfDimensions.x);
            originPos.x = xStart - (dist / 2);
        }
        halfDimensions.x = dist/2;
        setPosition(new Vec2(originPos.x, originPos.y));
        startBridge(startSide);
        middleBridge(startSide);
        endBridge(startSide);

    }

    private void startBridge(Direction startSide) {
        if (startSide.equals(Direction.RIGHT)) {
            bridgeParts.put(
                    new BridgePart(this, new Vec2(-(halfDimensions.x)+2, -1.5f), "bridgePartStart"),
                    new AttachedImage(this, new BodyImage("data/Magic_Cliffs/bridge/left.png"), 4f, 0,new Vec2(-(halfDimensions.x)+1.26f, -1.5f))
            );
        } else {
            bridgeParts.put(
                    new BridgePart(this, new Vec2((halfDimensions.x)-2, -1.5f), "bridgePartStart"),
                    new AttachedImage(this, new BodyImage("data/Magic_Cliffs/bridge/right.png"), 4f, 0,new Vec2((halfDimensions.x)-1.26f, -1.5f))
            );
        }
    }

    private void endBridge(Direction startSide) {
        if (startSide.equals(Direction.RIGHT)) {
            bridgeParts.put(
                    new BridgePart(this, new Vec2((halfDimensions.x)-2, -1.5f), "bridgePartEnd"),
                    new AttachedImage(this, new BodyImage("data/Magic_Cliffs/bridge/right.png"), 4f, 0,new Vec2((halfDimensions.x)-1.26f, -1.5f))
            );
        } else {
            bridgeParts.put(
                    new BridgePart(this, new Vec2(-(halfDimensions.x)+2, -1.5f), "bridgePartEnd"),
                    new AttachedImage(this, new BodyImage("data/Magic_Cliffs/bridge/left.png"), 4f, 0,new Vec2(-(halfDimensions.x)+1.26f, -1.5f))
            );
        }
        System.out.println(getBridgePart("bridgePartEnd").getXPos());
    }

    private void middleBridge(Direction startSide) {
        for (int i = 0; i < 8; i += 4) {
            if (startSide.equals(Direction.RIGHT)) {
                bridgeParts.put(
                        new BridgePart(this, new Vec2(halfDimensions.x + 6f + i, -1.5f), "bridgePartMiddle"),
                        new AttachedImage(this, new BodyImage("data/Magic_Cliffs/bridge/middle.png"), 4f, 0,new Vec2((halfDimensions.x + 6 + i)-1.26f, -1.5f))
                );
            } else {
                bridgeParts.put(
                        new BridgePart(this, new Vec2(halfDimensions.x + 6f + i, -1.5f), "bridgePartMiddle"),
                        new AttachedImage(this, new BodyImage("data/Magic_Cliffs/bridge/middle.png"), 4f, 0,new Vec2(i, -1.5f))
                );
            }
        }
//        for (float i = halfDimensions.x - 6f; i > 0; i -= 4) {
//            if (startSide.equals(Direction.RIGHT)) {
//                bridgeParts.put(
//                        new BridgePart(this, new Vec2(i, -1.5f), "bridgePartMiddle"),
//                        new AttachedImage(this, new BodyImage("data/Magic_Cliffs/bridge/middle.png"), 4f, 0,new Vec2(i, -1.5f))
//                );
//            } else {
//                AttachedImage A = new AttachedImage(this, new BodyImage("data/Magic_Cliffs/bridge/middle.png"), 4f, 0,new Vec2(i, -1.5f));
//                A.flipHorizontal();
//                bridgeParts.put(
//                        new BridgePart(this, new Vec2(i, -1.5f), "bridgePartMiddle"),
//                        A
//                );
//            }
//        }
    }

    private Direction calcDirection() {
        if (bridgeStart.getOriginPos().x < bridgeEnd.getOriginPos().x) {
            return Direction.RIGHT;
        } else {
            return Direction.LEFT;
        }
    }
    // methods private
    private BridgePart getBridgePart(String name) {
        for (BridgePart part : bridgeParts.keySet()) {
            if (part.getName().equals(name)) {
                return part;
            }
        }
        return null;
    }
    // Methods | Private | Exception handling
    private void exceptionHandling() {
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
}
