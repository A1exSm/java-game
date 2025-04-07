package game.body.staticstructs.ground.hauntedForest;
// Imports

import city.cs.engine.*;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import game.enums.Direction;
import game.exceptions.IllegalLengthScaleException;
import game.utils.GameBodyImage;
import org.jbox2d.common.Vec2;

import java.awt.*;

/**
 * A platform which uses the HauntedForest tile set.
 */
// Class
public class HauntedFlatPlatform extends GroundFrame {
    // Fields
    public static final GameBodyImage IMG_A = new GameBodyImage("data/HauntedForest/tiles/flat_A.png", 8f);
    public static final GameBodyImage IMG_B = new GameBodyImage("data/HauntedForest/tiles/flat_B.png", 8f);
    private final float[] yPositions = new float[2]; // [0] = tree, [1] = pillar
    private final GameBodyImage[] images = new GameBodyImage[]{null, null}; // [0] = tree, [1] = pillar
    private final int lengthScale;
    // Constructor
    /**
     * Constructor for HauntedFlatPlatform.<br>
     * Creates a new instance of HauntedFlatPlatform with the specified parameters.
     *
     * @param gameWorld the game world
     * @param x the x-coordinate of the platform
     * @param y the y-coordinate of the platform
     * @param lengthScale the length scale of the platform (must be >= 1
     */
    public HauntedFlatPlatform(GameWorld gameWorld, float x, float y, int lengthScale) {
        super(gameWorld);
        this.lengthScale = IllegalLengthScaleException.checkLengthScale(lengthScale);
        halfDimensions.x = IMG_A.getDimensions().x*lengthScale;
        halfDimensions.y = 1;
        setPosition(new Vec2(x, y));
        new SolidFixture(this, new BoxShape(halfDimensions.x, halfDimensions.y));
        paint();
    }
    // Methods | public
    @Override
    public void paint() {
        removeAllImages();
        if (images[0] != null) {
            new AttachedImage(this, images[0], 1.5f, 0, new Vec2(1, yPositions[0]));
        }
        if (images[1] != null) {
            new AttachedImage(this, images[1], 1f, 0, new Vec2(1, yPositions[1]));
        }
        if (lengthScale == 1) {
            GameBodyImage img = ((int)(Math.random() * 101) % 12 != 0) ? IMG_A : IMG_B;
            addImage(img).setOffset(new Vec2(0, -img.getHalfDimensions().y/2));
        } else {
            for (float i = -halfDimensions.x + IMG_A.getHalfDimensions().x; i < halfDimensions.x; i+= IMG_A.getDimensions().x) {
                GameBodyImage img = ((int)(Math.random() * 101) % 12 != 0) ? IMG_A : IMG_B;
                addImage(img).setOffset(new Vec2(i, -img.getHalfDimensions().y/2));
            }
        }
    }
    /**
     * Adds a tree image to the platform.
     * @param random if true, a random tree is added, else a default tree is added.
     */
    public void addTree(boolean random) {
        if(images[0] != null) {
            System.err.println("Warning: Tree already attached to this platform!");
            return;
        }
        if (random) {
            if ((int)(Math.random() * 101) % 2 == 0) {
                images[0] = new GameBodyImage("data/HauntedForest/Props/tree-2.png", 8f);
                yPositions[0] = 6.5f;
            }
        }
        if (images[0] == null) {
            images[0] = new GameBodyImage("data/HauntedForest/Props/tree.png", 8f);
            yPositions[0] = 5.5f;
        }
        paint();
    }
    /**
     * Adds a pillar image to the platform.
     */
    public void addPillar() {
        if (images[1] != null) {
            System.err.println("Warning: Pillar already attached to this platform!");
            return;
        }
        images[1] = new GameBodyImage("data/HauntedForest/Props/pillarB.png", 3f);
        yPositions[1] = 2.5f;
        paint();
    }
}
