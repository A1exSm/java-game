package game.body.staticstructs.ground;
// Imports

import city.cs.engine.AttachedImage;
import game.core.GameWorld;
import game.utils.GameBodyImage;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
public abstract class Prop extends GroundFrame {
    // Fields
    private final GameBodyImage img;
    private boolean flipped = false;
    // Constructor
    public Prop(GameWorld gameWorld, GameBodyImage img) {
        super(gameWorld);
        this.img = img;
        paint();
    }
    // Methods
    public void paint() {
        removeAllImages();
        AttachedImage tempImg = addImage(img);
        if (flipped) {
            tempImg.flipHorizontal();
        }
    }

    public GameBodyImage getImage() {
        return img;
    }
    public void flip() {
        flipped = !flipped;
        paint();
    }
}
