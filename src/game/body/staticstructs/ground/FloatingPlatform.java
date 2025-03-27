package game.body.staticstructs.ground;
// Imports

import city.cs.engine.AttachedImage;
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import game.core.GameWorld;
import org.jbox2d.common.Vec2;

/**
 * Platform object which extends {@link GroundFrame}.
 */
// Class
public class FloatingPlatform extends GroundFrame {
    // Fields

    // Constructor
    /**
     * Constructor for Ground.<br>
     * Creates a new instance of Ground with the specified parameters.
     *
     * @param gameWorld the game world
     * @param x the x-coordinate of the ground
     * @param y the y-coordinate of the ground
     * @param size the size of the platform: {@code "MEDIUM"} or {@code "LARGE"}
     */
    public FloatingPlatform(GameWorld gameWorld, float x, float y, String size) {
        super(gameWorld);
        setPosition(new Vec2(x, y));
        switch (size) {
            case "MEDIUM" -> constructMedium();
            case "LARGE" -> constructLarge();
        }
    }
    // Methods
    private void constructMedium() {
        halfDimensions.set(2, 1.5f);
        new SolidFixture(this, new BoxShape(halfDimensions.x, halfDimensions.y));
        new AttachedImage(this, new BodyImage("data/Magic_Cliffs/ground/floating_medium.png"), 4f, 0, new Vec2(0, 0.5f));
    }
    private void constructLarge() {
        halfDimensions.set(4, 5.7f/2); // -1.9 -0.8 = -2.7, 1.4 + 1.5 = 2.9; dist (-2.7, 2.9) = 2.7 + 2.9 = 5.6
        new SolidFixture(this, new BoxShape(4, 1.5f, new Vec2(0, 1.4f)));
        new SolidFixture(this, new BoxShape(3, 0.5f, new Vec2(0.5f, -0.6f))).setFriction(0);
        new SolidFixture(this, new BoxShape(2, 0.8f, new Vec2(0, -1.9f))).setFriction(0);
        new AttachedImage(this, new BodyImage("data/Magic_Cliffs/ground/floating_large.png"), 7f, 0, new Vec2(0, 0));
    }
}
