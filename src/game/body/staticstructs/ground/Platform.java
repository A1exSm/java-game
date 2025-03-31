package game.body.staticstructs.ground;
// Imports

import city.cs.engine.AttachedImage;
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import game.core.GameWorld;
import game.enums.PlatformType;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
public class Platform extends GroundFrame {
    // Fields
    private boolean tree = false;
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
    public Platform(GameWorld gameWorld, float x, float y, PlatformType type) {
        super(gameWorld, new Vec2(5f, 5), new Vec2(x, y));
        this.type = type;
        halfDimensions.x = 12;
        halfDimensions.y = 6;
        resetYTop();
        initImages();
        new SolidFixture(this, new BoxShape(12, 1, new Vec2(0, 5.5f)));
        new SolidFixture(this, new BoxShape(12, 4.9f, new Vec2(0, -0.1f))).setFriction(0.00005f); // ensures player cant jump back up using friction as it is know to be possible :)
    }
    // Methods | private | setup
    private void initImages() {
        if (type.equals(PlatformType.GROUND)) {addImage(new BodyImage("data/Magic_Cliffs/ground/background.png", 11f)).setOffset(new Vec2(0, -1.75f));}
        if(tree){addImage(new BodyImage("data/Magic_Cliffs/misc/tree.png", 11f)).setOffset(new Vec2(0, halfDimensions.y*2));}
        AttachedImage leftSide = addImage(type.getSideBody());
        AttachedImage rightSide = addImage(type.getSideBody());
        leftSide.setOffset(new Vec2(-halfDimensions.x+type.getSideWidth(), type.getSideY()));
        rightSide.setOffset(new Vec2(-(halfDimensions.x-type.getSideWidth()), type.getSideY()));
        rightSide.flipHorizontal();
        addImage(type.geMiddleBody()).setOffset(new Vec2(type.getMiddleX(), type.getMiddleY()));
        if (type.equals(PlatformType.CLIFF_LIGHT) || type.equals(PlatformType.CLIFF_DARK)) {
            addImage(type.geMiddleBody()).setOffset(new Vec2(-type.getMiddleX(), type.getMiddleY()));
        }
    }
    // Method | public
    /**
     * Adds a tree image to the platform.
     */
    public void addTree() {
        if (tree) {
            System.err.println("Warning: Tree already exists on this platform.");
            return;
        }
        removeAllImages();
        tree = true;
        initImages();
    }
}
