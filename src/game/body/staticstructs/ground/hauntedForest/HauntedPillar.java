package game.body.staticstructs.ground.hauntedForest;
// Imports

import city.cs.engine.*;
import game.body.staticstructs.ground.GroundFrame;
import game.body.walkers.PlayerWalker;
import game.core.GameWorld;
import game.core.console.Console;
import game.levels.HauntedForest;
import game.levels.LevelFrame;
import game.utils.GameBodyImage;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
public class HauntedPillar extends GroundFrame {
    // Fields
    public static final GameBodyImage PILLAR_IMAGE= new GameBodyImage("data/HauntedForest/Props/pillarA.png", 6f);
    public static final GameBodyImage PILLAR_FACE_IMAGE = new GameBodyImage("data/HauntedForest/Props/pillar_FACe.png", 6f);
    private LevelFrame level = null;
    // Constructor
    public HauntedPillar(LevelFrame level, float x, float y) {
        // initial setup
        super(level.getGameWorld());
        this.level = level;
        setup(new Vec2(x, y));
    }

    public HauntedPillar(LevelFrame level, float x, float y, float yDestination) {
        this(level, x, y);
        addElevatorListener(yDestination);
    }

    // Methods
    @Override
    public void paint() {
        addImage(PILLAR_IMAGE).setOffset(new Vec2(0, -PILLAR_IMAGE.getHalfDimensions().y));
        addImage(PILLAR_FACE_IMAGE).setOffset(new Vec2(0, PILLAR_FACE_IMAGE.getHalfDimensions().y));
    }

    private void setup(Vec2 position) {
        halfDimensions.x = PILLAR_FACE_IMAGE.getHalfDimensions().x;
        halfDimensions.y = PILLAR_FACE_IMAGE.getHalfDimensions().y + PILLAR_IMAGE.getHalfDimensions().y;
        setPosition(position);
        new SolidFixture(this, new BoxShape(halfDimensions.x, halfDimensions.y));
        paint();
    }

    private void addElevatorListener(float yDestination) {
        addCollisionListener(e->{
            if (e.getOtherBody() instanceof PlayerWalker player) {
                if (player.getPosition().y - PlayerWalker.HALF_Y/2 >= yTop && player.getPosition().x <= originPos.x + halfDimensions.x && player.getPosition().x >= originPos.x - halfDimensions.x) {
                    new HauntedElevator(level.getGameWorld(), new Vec2(halfDimensions.x, halfDimensions.y), new Vec2(getPosition().x, getPosition().y), yDestination);
                    level.removeGroundFrame(this.getName());
                }
            }
        });
    }
}
