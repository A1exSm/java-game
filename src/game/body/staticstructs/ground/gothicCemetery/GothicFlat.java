package game.body.staticstructs.ground.gothicCemetery;
// Imports
import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import game.exceptions.IllegalLengthScaleException;
import game.utils.GameBodyImage;
import org.jbox2d.common.Vec2;
// Class
/**
 * A flat platform for the gothicvania cemetery level,
 * used as an abstract class for creating different types of flat platforms.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 06-04-2025
 */
abstract class GothicFlat extends GroundFrame {
    // Fields
    private final int lengthScale;
    private final GameBodyImage groundImage;
    // Constructor
    /**
     * Sets the position of the flat platform and its length scale,
     * specifies the number of prop nodes (equal to lengthScale)
     * and the image to be used for the flat platform.
     * @param gameWorld The game world to which this flat platform belongs.
     * @param x The x-coordinate of the flat platform's position.
     * @param y The y-coordinate of the flat platform's position.
     * @param lengthScale The scale factor for the length of the flat platform.
     * @param groundImage The image to be used for the flat platform.
     * @throws IllegalLengthScaleException if the length scale is less than 1.
     */
    GothicFlat(GameWorld gameWorld, float x, float y, int lengthScale, GameBodyImage groundImage) {
        super(gameWorld);
        this.groundImage = groundImage;
        halfDimensions.x = groundImage.getHalfDimensions().x*lengthScale;
        setPropEnabled(true, lengthScale);
        halfDimensions.y = 1;
        setPosition(new Vec2(x, y));
        new SolidFixture(this, new BoxShape(halfDimensions.x, halfDimensions.y));
        this.lengthScale = IllegalLengthScaleException.checkLengthScale(lengthScale);
        paint();
    }
    // Methods
    /**
     * {@inheritDoc}
     */
    @Override
    public void paint() {
        if (lengthScale == 1) {
            addImage(groundImage);
        } else {
            for (float i = -halfDimensions.x + groundImage.getHalfDimensions().x; i < halfDimensions.x; i+= groundImage.getDimensions().x) {
                addImage(groundImage).setOffset(new Vec2(i, 0));
            }
        }
    }
}
