package game.body.staticstructs.ground.magicCliffs;
// Imports

import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import game.utils.GameBodyImage;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
abstract class MagicFloatingRock extends GroundFrame {
    // Fields
    private final GameBodyImage bodyImage;
    // Constructor
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
    @Override
    public void paint() {
        addImage(bodyImage);
    }
}
