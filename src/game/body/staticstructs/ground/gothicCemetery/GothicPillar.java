package game.body.staticstructs.ground.gothicCemetery;
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
public final class GothicPillar extends GroundFrame {
    // Fields
    public static final GameBodyImage IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-Tiles/ground_pillar.png", 12f);
    private static final GameBodyImage VINES_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-Tiles/pillar_vines.png", 4);
    private final boolean vines;
    private float vineYOffset;
    // Constructor
    public GothicPillar(GameWorld gameWorld, float x, float y) {
        super(gameWorld);
        halfDimensions.x = IMG.getHalfDimensions().x;
        halfDimensions.y = 1;
        setPosition(new Vec2(x, y));
        new SolidFixture(this, new BoxShape(halfDimensions.x, halfDimensions.y));
        vines = (int) (Math.random() * 101) % 2 != 0;
        if (vines) {vineYOffset = GroundFrame.randRangeFloat(4.3f, 5.0f);}
        paint();

    }
    // Methods
    public void paint() {
        addImage(IMG).setOffset(new Vec2(0, 3.3f));
        if (vines) {addImage(VINES_IMG).setOffset(new Vec2(0, vineYOffset));}
    }
}
