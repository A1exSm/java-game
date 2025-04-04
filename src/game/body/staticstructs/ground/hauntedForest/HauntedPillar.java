package game.body.staticstructs.ground.hauntedForest;
// Imports

import city.cs.engine.*;
import game.body.staticstructs.ground.GroundFrame;
import game.body.walkers.PlayerWalker;
import game.core.GameWorld;
import game.levels.HauntedForest;
import game.utils.GameBodyImage;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
public class HauntedPillar extends GroundFrame {
    // Fields
    private static int count = 0;
    public static final GameBodyImage PILLAR_IMAGE= new GameBodyImage("data/HauntedForest/Props/pillarA.png", 6f);
    public static final GameBodyImage PILLAR_FACE_IMAGE = new GameBodyImage("data/HauntedForest/Props/pillar_FACe.png", 6f);
    public final HauntedForest level;
    // Constructor
    public HauntedPillar(GameWorld gameWorld, float x, float y, boolean ghostly, boolean isElevator, HauntedForest level) {
        // initial setup
        super(gameWorld);
        this.level = level;
        halfDimensions.x = PILLAR_FACE_IMAGE.getHalfDimensions().x;
        halfDimensions.y = PILLAR_FACE_IMAGE.getHalfDimensions().y + PILLAR_IMAGE.getHalfDimensions().y;
        setPosition(new Vec2(x, y));
        if (ghostly) {
            new GhostlyFixture(this, new BoxShape(halfDimensions.x, halfDimensions.y));
        } else {
            new SolidFixture(this, new BoxShape(halfDimensions.x, halfDimensions.y));
        }
        // Images
        addImage(PILLAR_IMAGE).setOffset(new Vec2(0, -PILLAR_IMAGE.getHalfDimensions().y));
        addImage(PILLAR_FACE_IMAGE).setOffset(new Vec2(0, PILLAR_FACE_IMAGE.getHalfDimensions().y));
        // Destruction Listener
        if (isElevator) {
            addCollisionListener(e -> {
                if (e.getOtherBody() instanceof PlayerWalker player) {
                    if (player.getPosition().y - player.HALF_Y/2 >= yTop && player.getPosition().x <= originPos.x + halfDimensions.x && player.getPosition().x >= originPos.x - halfDimensions.x) {
                        new HauntedElevator(gameWorld, new Vec2(halfDimensions.x, halfDimensions.y), new Vec2(getPosition().x, getPosition().y), 100, level);
                        level.removeGroundFrame(this.getName());
                    }
                }
            });
        }
    }
    public HauntedPillar(GameWorld gameWorld, Vec2 pos, HauntedForest level) {
        super(gameWorld);
        this.level = level;
        halfDimensions.x = PILLAR_FACE_IMAGE.getHalfDimensions().x;
        halfDimensions.y = PILLAR_FACE_IMAGE.getHalfDimensions().y + PILLAR_IMAGE.getHalfDimensions().y;
        setPosition(pos);
        new SolidFixture(this, new BoxShape(halfDimensions.x, halfDimensions.y));
        // Images
        addImage(PILLAR_IMAGE).setOffset(new Vec2(0, -PILLAR_IMAGE.getHalfDimensions().y));
        addImage(PILLAR_FACE_IMAGE).setOffset(new Vec2(0, PILLAR_FACE_IMAGE.getHalfDimensions().y));
        level.addGroundFrame(this.getName() + ++count, this);
    }
    // Methods
}
