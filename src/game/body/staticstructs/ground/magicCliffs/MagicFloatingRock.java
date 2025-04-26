package game.body.staticstructs.ground.magicCliffs;
// Imports
import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import game.utils.GameBodyImage;
import org.jbox2d.common.Vec2;
// Class
/**
 * Abstract class for the magic floating rocks.
 * This class is extended by the specific types of magic floating rocks.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 17-04-2025
 */
abstract class MagicFloatingRock extends GroundFrame {
    // Fields
    private final GameBodyImage bodyImage;
    // Constructor
    /**
     * A floating rock.
     * @param gameWorld The game world.
     * @param x The x position of the rock.
     * @param y The y position of the rock.
     * @param bodyImage The image of the rock.
     */
    MagicFloatingRock(GameWorld gameWorld, float x, float y, GameBodyImage bodyImage) {
        super(gameWorld);
        this.bodyImage = bodyImage;
        halfDimensions.x = bodyImage.getHalfDimensions().x;
        halfDimensions.y = bodyImage.getHalfDimensions().y;
        setPosition(new Vec2(x,y));
        setPropEnabled(true, 1);
        new SolidFixture(this, new BoxShape(halfDimensions.x, halfDimensions.y));
        paint();
    }
    // Methods
    /**
     * {@inheritDoc}
     */
    @Override
    public void paint() {
        addImage(bodyImage);
    }
}
