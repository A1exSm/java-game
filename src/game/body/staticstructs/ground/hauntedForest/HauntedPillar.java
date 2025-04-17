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
    private HauntedForest level = null;
    // Constructor
    public HauntedPillar(GameWorld gameWorld, float x, float y, boolean isElevator, HauntedForest level) {
        // initial setup
        super(gameWorld);
        this.level = level;
        halfDimensions.x = PILLAR_FACE_IMAGE.getHalfDimensions().x;
        halfDimensions.y = PILLAR_FACE_IMAGE.getHalfDimensions().y + PILLAR_IMAGE.getHalfDimensions().y;
        setPosition(new Vec2(x, y));
        new SolidFixture(this, new BoxShape(halfDimensions.x, halfDimensions.y));
        paint();
        if (isElevator) {
            addBridgeDestruct(gameWorld);
        }
    }
    public HauntedPillar(GameWorld gameWorld, float x, float y, HauntedForest level) {
        super(gameWorld);
        this.level = level;
        halfDimensions.x = PILLAR_FACE_IMAGE.getHalfDimensions().x;
        halfDimensions.y = PILLAR_FACE_IMAGE.getHalfDimensions().y + PILLAR_IMAGE.getHalfDimensions().y;
        setPosition(new Vec2(x, y));
        new SolidFixture(this, new BoxShape(halfDimensions.x, halfDimensions.y));
        paint();
        level.add(this.getName() + ++count, this);
    }
    // Methods
    @Override
    public void paint() {
        addImage(PILLAR_IMAGE).setOffset(new Vec2(0, -PILLAR_IMAGE.getHalfDimensions().y));
        addImage(PILLAR_FACE_IMAGE).setOffset(new Vec2(0, PILLAR_FACE_IMAGE.getHalfDimensions().y));
    }

    private void addBridgeDestruct(GameWorld gameWorld) {
        addCollisionListener(e -> {
            if (e.getOtherBody() instanceof PlayerWalker player) {
                if (player.getPosition().y - PlayerWalker.HALF_Y/2 >= yTop && player.getPosition().x <= originPos.x + halfDimensions.x && player.getPosition().x >= originPos.x - halfDimensions.x) {
                    new HauntedElevator(gameWorld, new Vec2(halfDimensions.x, halfDimensions.y), new Vec2(getPosition().x, getPosition().y), 100, level);
                    level.removeGroundFrame(this.getName());
                }
            }
        });
    }
    public HauntedForest getLevel() {
        return level;
    }
}
