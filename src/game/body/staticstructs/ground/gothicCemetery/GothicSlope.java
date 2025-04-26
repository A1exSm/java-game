package game.body.staticstructs.ground.gothicCemetery;
// Imports
import city.cs.engine.*;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import game.core.console.Console;
import game.enums.Direction;
import game.exceptions.IllegalLengthScaleException;
import game.utils.GameBodyImage;
import org.jbox2d.common.Vec2;
import java.awt.*;
// Class
/**
 * An abstract class used to make slopes
 * which act as a slope for a player to go up and down.
 *
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 06-04-2025
 */
public abstract class GothicSlope extends GroundFrame { // this class is witchcraft, all you have to know is that it is reactive to the image and lengthScale :))))
    // Fields
    /**
     * The image used to fill the centre of the slope object. <br>
     * <img src="doc-files/fill.png" alt="GothicSlope middle image">
     */
    public static final GameBodyImage FILL_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-Tiles/fill.png", 4f);
    private final int lengthScale;
    private final GameBodyImage bodyImage;
    private final Direction direction;
    private final int divVal;
    private float yOffset;
    // Constructor
    /**
     * Creates a slope object with the given image,
     * direction and lengthScale.
     * @param gameWorld The game world to which this slope belongs.
     * @param x The x position of the slope.
     * @param y The y position of the slope.
     * @param lengthScale The length scale of the slope.
     * @param direction The direction of the slope (UP or DOWN).
     * @param bodyImage The image used to paint the slope.
     * @param divVal The divisor value for the height of the slope.
     * @throws IllegalArgumentException if the direction is not UP or DOWN.
     */
    GothicSlope(GameWorld gameWorld, float x, float y, int lengthScale, Direction direction, GameBodyImage bodyImage, int divVal) {
        super(gameWorld);
        this.bodyImage = bodyImage;
        this.lengthScale = IllegalLengthScaleException.checkLengthScale(lengthScale);
        this.divVal = divVal;
        halfDimensions.x = bodyImage.getDimensions().x *lengthScale;
        halfDimensions.y = 2 + (lengthScale -0.25f) * bodyImage.getDimensions().y/divVal;
        setPosition(new Vec2(x, y));
        this. direction = direction;
        float[] vertices = getFloats();
        new SolidFixture(this, new PolygonShape(vertices[0], vertices[1], vertices[2], vertices[3], vertices[4], vertices[5]));
        setFillColor(Color.RED);
        paint();
    }
    // Methods | Private
    /**
     * {@inheritDoc}
     */
    @Override
    public void paint() {
        AttachedImage image;
        for (int i = 0; i < lengthScale * 2; i++) {
            image = addImage(bodyImage);
            image.setOffset(new Vec2((-halfDimensions.x + bodyImage.getHalfDimensions().x)+ (bodyImage.getDimensions().x * i), (i * bodyImage.getHalfDimensions().y/divVal + (float) 2 /divVal) + yOffset));
            if (direction != Direction.UP) {
                image.flipHorizontal();
            }
        }
    }
    /**
     * Returns the vertices of the slope based on the direction.
     * @return The vertices of the slope.
     */
    private float[] getFloats() {
        float[] vertices;
        switch (direction) {
            case DOWN -> {
                vertices = new float[]{-halfDimensions.x, bodyImage.getHalfDimensions().y/12, -halfDimensions.x, -halfDimensions.y + (bodyImage.getHalfDimensions().y/1.5f), halfDimensions.x, -halfDimensions.y + (bodyImage.getHalfDimensions().y/1.5f)};
                yOffset = -halfDimensions.y;
            }
            case UP -> {
                vertices = new float[]{-halfDimensions.x,2 + -bodyImage.getHalfDimensions().y + (bodyImage.getHalfDimensions().y * 0.5f), halfDimensions.x, 2 + -bodyImage.getHalfDimensions().y + (bodyImage.getHalfDimensions().y * 0.5f), halfDimensions.x, 2 + (lengthScale -0.25f) * bodyImage.getDimensions().y/divVal};
                yOffset = 0;
            }
            default -> {throw new IllegalArgumentException(Console.exceptionMessage("Invalid verticalDirection: " + direction + ". Req: UP or DOWN."));}
        }
        return vertices;
    }
    // Methods | Public
    /**
     * gets a position along the line equation of the slope.
     * @param playerXPos the x position of the player.
     * @return the yPos of the slope at playerXpos.
     */
    public float getLineEquationYPos(float playerXPos) {
        float[] vertices = getFloats();
        float x1 = originPos.x + vertices[0];
        float x2 = originPos.x + vertices[4];
        float y1 = originPos.y + vertices[1];
        float y2 = originPos.y + vertices[5];
        float m = (y2 - y1)/(x2 - x1);
        return m * playerXPos + (y1 - m * x1);
    }
}
