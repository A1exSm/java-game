package game.body.staticstructs.ground.gothicCemetery;
// Imports

import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import game.exceptions.IllegalLengthScaleException;
import game.utils.GameBodyImage;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
abstract class GothicFlat extends GroundFrame {
    // Fields
    private final int lengthScale;
    private final GameBodyImage groundImage;
    // Constructor
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
