package game.body.staticstructs.ground;
// Imports
import city.cs.engine.AttachedImage;
import game.core.GameWorld;
import game.utils.GameBodyImage;
/**
 * Prop class serves as a parent class for all props in the game.
 *  @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 *  @since 08-04-2025
 */
// Class
public abstract class Prop extends GroundFrame {
    // Fields
    private final GameBodyImage img;
    private boolean flipped = false;
    // Constructor
    /**
     * Constructor for the Prop class.
     * @param gameWorld The game world to which this prop belongs.
     * @param img The image to be used for this prop.
     * @see GameBodyImage
     */
    public Prop(GameWorld gameWorld, GameBodyImage img) {
        super(gameWorld);
        this.img = img;
        paint();
    }
    // Methods
    /**
     * {@inheritDoc GroundFrame#paint()}
     */
    @Override
    public void paint() {
        removeAllImages();
        AttachedImage tempImg = addImage(img);
        if (flipped) {
            tempImg.flipHorizontal();
        }
    }
    /**
     * Returns the image associated with this prop.
     * @return The image of this prop.
     */
    public GameBodyImage getImage() {
        return img;
    }
    /**
     * Flips the prop's image horizontally.
     */
    public void flip() {
        flipped = !flipped;
        repaint();
    }
}
