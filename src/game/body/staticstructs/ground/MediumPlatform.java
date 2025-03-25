package game.body.staticstructs.ground;
// Imports

import city.cs.engine.AttachedImage;
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import game.core.GameWorld;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
public class MediumPlatform extends GroundFrame {
    // Fields
    private final BodyImage middle = new BodyImage("data/Magic_Cliffs/ground/light_large.png", 4.5f); // width of image is 5.68f when height is 4.5f
    private final BodyImage side = new BodyImage("data/Magic_Cliffs/ground/light_left.png", 15.5f); // 5.1f
    private final float middleWidth = 5.68f;
    private final float sideWidth = 5.1f;
    private boolean tree = false;
    // Constructor
    /**
     * Constructor for Ground.<br>
     * Creates a new instance of Ground with the specified parameters.
     *
     * @param gameWorld the game world
     * @param x the x-coordinate of the ground
     * @param y the y-coordinate of the ground
     */
    public MediumPlatform(GameWorld gameWorld, float x, float y) {
        super(gameWorld, new Vec2(5f, 5), new Vec2(x, y));
        halfDimensions.x = 12;
        halfDimensions.y = 6;
        resetYTop();
        initImages();
        new SolidFixture(this, new BoxShape(12, 1, new Vec2(0, 5.5f)));
        new SolidFixture(this, new BoxShape(12, 4.9f, new Vec2(0, -0.1f))).setFriction(0.00005f); // ensures player cant jump back up using friction as it is know to be possible :)
    }
    // Methods | private | setup
    private void initImages() {
        addImage(new BodyImage("data/Magic_Cliffs/ground/background.png", 11f)).setOffset(new Vec2(0, -1.75f));
        if(tree){addImage(new BodyImage("data/Magic_Cliffs/misc/tree.png", 11f)).setOffset(new Vec2(0, halfDimensions.y*2));}
        AttachedImage leftSide = addImage(side);
        AttachedImage rightSide = addImage(side);;
        leftSide.setOffset(new Vec2(-halfDimensions.x+sideWidth, 0.5f));
        rightSide.setOffset(new Vec2(-(halfDimensions.x-sideWidth), 0.5f));
        rightSide.flipHorizontal();
        addImage(middle).setOffset(new Vec2(0, 4.8f));
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
