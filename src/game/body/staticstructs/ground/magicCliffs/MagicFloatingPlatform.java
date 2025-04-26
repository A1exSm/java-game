package game.body.staticstructs.ground.magicCliffs;
// Imports
import city.cs.engine.AttachedImage;
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import game.core.console.Console;
import org.jbox2d.common.Vec2;
// Class
/**
 * Platform object which extends {@link GroundFrame}.
 * This class represents a floating island looking thing in the game world.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 17-04-2025
 */
public class MagicFloatingPlatform extends GroundFrame {
    // Fields
    private final String size;
    // Constructor
    /**
     * Constructor for Ground.<br>
     * Creates a new instance of Ground with the specified parameters.
     * @param gameWorld the game world
     * @param x the x-coordinate of the ground
     * @param y the y-coordinate of the ground
     * @param size the size of the platform: {@code "MEDIUM"} or {@code "LARGE"}
     * @throws IllegalArgumentException if the size is not "MEDIUM" or "LARGE"
     */
    public MagicFloatingPlatform(GameWorld gameWorld, float x, float y, String size) {
        super(gameWorld);
        setPosition(new Vec2(x, y));
        setPropEnabled(true, 1);
        this.size = size;
        if (!size.equals("MEDIUM") && !size.equals("LARGE")) {
            throw new IllegalArgumentException(Console.exceptionMessage("Invalid size: " + size + ". Req: MEDIUM or LARGE."));
        }
        paint();
    }
    // Methods | Private
    /**
     * Constructs the fixtures for a medium-sized floating platform.
     */
    private void constructMedium() {
        halfDimensions.set(2, 1.5f);
        new SolidFixture(this, new BoxShape(halfDimensions.x, halfDimensions.y));
        new AttachedImage(this, new BodyImage("data/MagicCliffs/ground/floating_medium.png"), 4f, 0, new Vec2(0, 0.5f));
    }
    /**
     * Constructs the fixtures for a large-sized floating platform.
     */
    private void constructLarge() {
        halfDimensions.set(4, 5.7f/2); // -1.9 -0.8 = -2.7, 1.4 + 1.5 = 2.9; dist (-2.7, 2.9) = 2.7 + 2.9 = 5.6
        new SolidFixture(this, new BoxShape(4, 1.5f, new Vec2(0, 1.4f)));
        new SolidFixture(this, new BoxShape(3, 0.5f, new Vec2(0.5f, -0.6f))).setFriction(0);
        new SolidFixture(this, new BoxShape(2, 0.8f, new Vec2(0, -1.9f))).setFriction(0);
        new AttachedImage(this, new BodyImage("data/MagicCliffs/ground/floating_large.png"), 7f, 0, new Vec2(0, 0));
    }
    // Methods | Public
    /**
     * {@inheritDoc GroundFrame#paint()}
     */
    @Override
    public void paint() {
        switch (size) {
            case "MEDIUM" -> constructMedium();
            case "LARGE" -> constructLarge();
        }
    }
}
