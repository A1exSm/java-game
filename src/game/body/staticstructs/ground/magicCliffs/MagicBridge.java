package game.body.staticstructs.ground.magicCliffs;
// Imports
import city.cs.engine.*;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import game.core.console.Console;
import game.enums.Direction;
import game.exceptions.BridgeCollisionException;
import game.exceptions.BridgeIllegalHeightException;
import game.exceptions.BridgeNullPointException;
import game.exceptions.BridgeUnacceptableRangeException;
import org.jbox2d.common.Vec2;
/**
 * Level bridge between two {@link GroundFrame} instances.
 *  @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 *  @since 25-03-2025
 */
// Class
public class MagicBridge extends GroundFrame {
    // Fields
    /**
     * The maximum distance between the two {@link GroundFrame} instances.
     */
    public static final float MAX_DISTANCE = 500f;
    /**
     * The minimum distance between the two {@link GroundFrame} instances.
     */
    public static final float MIN_DISTANCE = 40f;
    private final GroundFrame bridgeStart;
    private final GroundFrame bridgeEnd;
    private SolidFixture bridgeFixture = null;
    private final Direction facingDirection;
    private float[] xPos;
    private String[] path;
    // Constructor
    /**
     * Creates a new {@link MagicBridge} instance.
     * @param gameWorld The {@link GameWorld} instance.
     * @param bridgeStart The starting {@link GroundFrame} instance.
     * @param bridgeEnd The ending {@link GroundFrame} instance.
     */
    public MagicBridge(GameWorld gameWorld, GroundFrame bridgeStart, GroundFrame bridgeEnd) {
        super(gameWorld);
        this.bridgeStart = bridgeStart;
        this.bridgeEnd = bridgeEnd;
        facingDirection = calcDirection();
        if (validateBridge()) {
            setup();
            paint();
        } else {
            destroy();
        }
    }
    // Methods | private | setup
    /**
     * Sets up the {@link MagicBridge} instance.
     * Calculates the distance between the two {@link GroundFrame} instances and sets the position and half-dimensions of the bridge.
     */
    private void setup() {
        float dist;
        halfDimensions.y = 2f;
        xPos = new float[2]; // float[0] = xStart, float[1] = xEnd
        path = new String[2]; // path[0] = start, path[1] = end
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
    }
    /**
     * {@inheritDoc GroundFrame#paint()}
     */
    @Override
    public void paint() {
        newBridgePart(path[0], xPos[0], 0); // bridge start
        middleBridge();
        newBridgePart(path[1], xPos[1], 0); // bridge end
    }
    /**
     * Creates the middle part of the bridge.
     * The middle part is created by creating a series of {@link AttachedImage} instances.
     */
    private void middleBridge() {
        float counterA = 0;
        float counterB = 0;
        // side A
        for (float i = -halfDimensions.x + 6.0f; i < 0; i += 4) {
            if (i + 2 > 0) {break;}
            newBridgePart("middle", i, 0.12f);
            counterA = i;
        }
        // side B
        for (float i = halfDimensions.x - 4.0f; i > 0; i -= 4) {
            if (i - 2 < 0) {break;}
            newBridgePart("middle", i, 0.12f);
            counterB = i;
        }
        // filling the difference
        if (counterA != 0) {
            counterB -= 2.44f;
            while (counterB > counterA + 1f) {
                newBridgePart("piece", counterB, 0);
                counterB -= 0.68f;
            }
        }
    }
    /**
     * Creates a new {@link AttachedImage} instance.
     * @param path The path to the image.
     * @param x The x position of the image.
     * @param y The y position of the image.
     */
    private void newBridgePart(String path, float x, float y) {
        new AttachedImage(this, new BodyImage("data/MagicCliffs/bridge/" + path + ".png"), 4f, 0, new Vec2(x, y));
    }
    /**
     * Calculates the direction of the bridge.
     * @return The direction of the bridge.
     */
    private Direction calcDirection() {
        if (bridgeStart.getOriginPos().x < bridgeEnd.getOriginPos().x) {
            return Direction.RIGHT;
        } else {
            return Direction.LEFT;
        }
    }
    // Methods | Private | Exception handling
    /**
     * Validates the bridge.
     * If an invalidation is found, the appropriate warning is output.
     * @return True if the bridge is valid, false otherwise.
     */
    protected boolean validateBridge() {
        boolean valid = true;
        try {
            BridgeUnacceptableRangeException.CheckBridgeDistance(bridgeStart, bridgeEnd);
        } catch (BridgeUnacceptableRangeException e) {
            Console.warning(e.getMessage() + ". Destroying bridge!");
            valid = false;
        }
        try {
            BridgeNullPointException.CheckBridgeNullPoint(bridgeStart, bridgeEnd);
        } catch (BridgeNullPointException e) {
            Console.warning(e.getMessage() + ". Destroying bridge!");
            if (valid) {valid = false;}
        }
        try {
            BridgeIllegalHeightException.CheckBridgeHeightMismatch(bridgeStart, bridgeEnd);
        } catch (BridgeIllegalHeightException e) {
            Console.warning(e.getMessage() + ". Destroying bridge!");
            if (valid) {valid = false;}
        }
        try {
            BridgeCollisionException.CheckBridgeCollision(halfDimensions, bridgeStart, bridgeEnd);
        } catch (BridgeCollisionException e) {
            Console.warning(e.getMessage() + ". Destroying bridge!");
            if (valid) {valid = false;}
        }
        return valid;
    }
    // Methods | Public
    /**
     * Makes the bridge ghostly.
     */
    public void becomeGhostly() {
        if (bridgeFixture == null) {
            Console.warning("BecomeGhostly called on an already ghostly bridge");
            return;
        }
        bridgeFixture.destroy();
        bridgeFixture = null;
        removeAllImages();
    }
    /**
     * Makes the bridge solid.
     */
    public void becomeSolid() {
        if (bridgeFixture != null) {
            Console.warning("BecomeSolid called on an already solid bridge");
            return;
        }
        paint();
    }
}
