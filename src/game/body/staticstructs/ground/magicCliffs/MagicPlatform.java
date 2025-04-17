package game.body.staticstructs.ground.magicCliffs;
// Imports

import city.cs.engine.AttachedImage;
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import game.core.console.Console;
import game.enums.PlatformType;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
public class MagicPlatform extends GroundFrame {
    // Fields
    private final PlatformType type;
    // Constructor
    /**
     * Constructor for Ground.<br>
     * Creates a new instance of Ground with the specified parameters.
     *
     * @param gameWorld the game world
     * @param x the x-coordinate of the ground
     * @param y the y-coordinate of the ground
     */
    public MagicPlatform(GameWorld gameWorld, float x, float y, PlatformType type) {
        super(gameWorld, new Vec2(5f, 5), new Vec2(x, y));
        if (type != PlatformType.GROUND && type != PlatformType.CLIFF_DARK && type != PlatformType.CLIFF_LIGHT) {
            throw new IllegalArgumentException(Console.exceptionMessage("Unsupported platform type: " + type + ". Accepted types:\n1.GROUND\n2.CLIFF_DARK\n3.CLIFF_LIGHT"));
        }
        this.type = type;
        setPropEnabled(true, 2);
        halfDimensions.x = 12;
        halfDimensions.y = 6;
        paint();
        new SolidFixture(this, new BoxShape(12, 1, new Vec2(0, 5.5f)));
        new SolidFixture(this, new BoxShape(12, 4.9f, new Vec2(0, -0.1f))).setFriction(0.00005f); // ensures player cant jump back up using friction as it is know to be possible :)
    }

    // Method | public
    @Override
    public void paint() {
        if (type.equals(PlatformType.GROUND)) {addImage(new BodyImage("data/MagicCliffs/ground/background.png", 11f)).setOffset(new Vec2(0, -1.75f));}
        AttachedImage leftSide = addImage(type.getSideBody());
        AttachedImage rightSide = addImage(type.getSideBody());
        leftSide.setOffset(new Vec2(-halfDimensions.x+type.getSideWidth(), type.getSideY()));
        rightSide.setOffset(new Vec2(-(halfDimensions.x-type.getSideWidth()), type.getSideY()));
        rightSide.flipHorizontal();
        addImage(type.getMiddleBody()).setOffset(new Vec2(type.getMiddleX(), type.getMiddleY()));
        if (type.equals(PlatformType.CLIFF_LIGHT) || type.equals(PlatformType.CLIFF_DARK)) {
            addImage(type.getMiddleBody()).setOffset(new Vec2(-type.getMiddleX(), type.getMiddleY()));
        }
    }
}
