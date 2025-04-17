package game.body.staticstructs.ground.hauntedForest;
// Imports

import city.cs.engine.*;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import game.core.console.Console;
import game.exceptions.IllegalLengthScaleException;
import game.utils.GameBodyImage;
import org.jbox2d.common.Vec2;
/**
 *
 */
// Class
public class HauntedBackdrop extends GroundFrame {
    // Fields
    public static final GameBodyImage IMG = new GameBodyImage("data/HauntedForest/tiles/back_drop_platform.png", 16f);
    public static float PLATFORM_HEIGHT  = 0.9f;
    public static float PLATFORM_Y = 7.125f;
    private final int lengthScale;


    // Constructor
    public HauntedBackdrop(GameWorld gameWorld, float x, float y, int lengthScale) {
        super(gameWorld);
        this.lengthScale = IllegalLengthScaleException.checkLengthScale(lengthScale);
        halfDimensions.x = IMG.getHalfDimensions().x*lengthScale;
        halfDimensions.y = IMG.getHalfDimensions().y;
        setPosition(new Vec2(x, y));
        setYTop(getPosition().y - halfDimensions.y + PLATFORM_HEIGHT);
        new GhostlyFixture(this, new BoxShape(0.1f, 0.1f));
        new SolidFixture(this, new BoxShape(IMG.getHalfDimensions().x*lengthScale, PLATFORM_HEIGHT, new Vec2(0, PLATFORM_Y)));
        new SolidFixture(this, new BoxShape(IMG.getHalfDimensions().x*lengthScale, PLATFORM_HEIGHT, new Vec2(0, -PLATFORM_Y)));
        paint();
    }
    // Methods
    @Override
    public void paint() {
        removeAllImages();
        if (lengthScale == 1) {
            addImage(IMG);
        } else {
            for (int i = 0; i < lengthScale; i++) {
                addImage(IMG).setOffset(new Vec2((-halfDimensions.x + IMG.getHalfDimensions().x) + i*IMG.getDimensions().x, 0));
            }
        }
    }

    @Override
    public void setPosition(Vec2 pos) {
        superSetPosition(pos);
        if (pos != originPos) {
            originPos.x = pos.x;
            originPos.y = pos.y;
        }
    }
}
