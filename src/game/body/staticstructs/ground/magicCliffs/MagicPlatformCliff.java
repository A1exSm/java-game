package game.body.staticstructs.ground.magicCliffs;
// Imports
import city.cs.engine.AttachedImage;
import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import game.exceptions.IllegalLengthScaleException;
import game.utils.GameBodyImage;
import org.jbox2d.common.Vec2;
// Class
/**
 * An abstract class representing a cliff in the game world.
 * It extends the GroundFrame class and provides functionality for creating and rendering a cliff with a specific length scale.
 * The cliff is represented by an image and can be painted on the game world.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 17-04-2025
 */
public abstract class MagicPlatformCliff extends GroundFrame {
    // Fields
    /**
     * The image representing the cliff centre.<br>
     * <img src="doc-files/dark_middle.png" alt="dark_middle.png">
     */
    public static final GameBodyImage IMG = new GameBodyImage("data/MagicCliffs/cliff/dark_middle.png", 11f);
    private final GameBodyImage groundImage;
    private final int lengthScale;
    // Constructor
    /**
     * Creates a new MagicPlatformCliff object with the specified parameters.
     * @param gameWorld the game world in which the cliff will be created
     * @param x the x-coordinate of the cliff's position
     * @param y the y-coordinate of the cliff's position
     * @param lengthScale the length scale of the cliff (must be greater than or equal to 1)
     * @param img the image representing the cliff
     * @throws IllegalLengthScaleException if the length scale is less than 1
     */
     MagicPlatformCliff(GameWorld gameWorld, float x, float y, int lengthScale, GameBodyImage img) {
        super(gameWorld);
        groundImage = img;
        halfDimensions.x = groundImage.getHalfDimensions().x*2 + IMG.getHalfDimensions().x * (lengthScale -1);
        halfDimensions.y = 1;
        this.lengthScale = IllegalLengthScaleException.checkLengthScale(lengthScale);
        setPropEnabled(true, 2);
        setPosition(new Vec2(x, y));
        new SolidFixture(this, new BoxShape(halfDimensions.x,1));
        paint();
    }
    // Methods
    /**
     * {@inheritDoc}
     */
    @Override
    public void paint() {
         new AttachedImage(this, groundImage, 1f, 0, new Vec2(-halfDimensions.x + groundImage.getHalfDimensions().x, -groundImage.getHalfDimensions().y + 2));
         new AttachedImage(this, groundImage, 1f, 0, new Vec2(-halfDimensions.x + groundImage.getHalfDimensions().x, -groundImage.getHalfDimensions().y + 2)).flipHorizontal(); // offset is the same since this is flipped
         if (lengthScale > 1) {
            for (float i = -halfDimensions.x + groundImage.getDimensions().x + IMG.getHalfDimensions().x; i < halfDimensions.x - groundImage.getDimensions().x; i+= IMG.getDimensions().x) {
                new AttachedImage(this, IMG, 1f, 0, new Vec2(i, -IMG.getHalfDimensions().y + 2));
            }
        }
    }
}
