package game.body.staticstructs.ground.hauntedForest;
// Imports
import city.cs.engine.*;
import game.body.staticstructs.ground.GroundFrame;
import game.body.walkers.PlayerWalker;
import game.levels.LevelFrame;
import game.utils.GameBodyImage;
import org.jbox2d.common.Vec2;
// Class
/**
 * HauntedPillar class represents a pillar in the haunted forest level.
 * It extends the GroundFrame class and provides functionality for
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 04-04-2025
 */
public class HauntedPillar extends GroundFrame {
    // Fields
    /**
     * An image of a pillar.<br>
     * <img src="doc-files/pillarA.png" alt="Pillar Image">
     */
    public static final GameBodyImage PILLAR_IMAGE= new GameBodyImage("data/HauntedForest/Props/pillarA.png", 6f);
    /**
     * An image of a pillar face.<br>
     * <img src="doc-files/pillar_face.png" alt="Pillar Face Image">
     */
    public static final GameBodyImage PILLAR_FACE_IMAGE = new GameBodyImage("data/HauntedForest/Props/pillar_face.png", 6f);
    private LevelFrame level = null;
    // Constructor
    /**
     * Constructor for HauntedPillar.<br>
     * Creates a new instance of HauntedPillar with the specified parameters.
     * @param level the level frame
     * @param x the x-coordinate of the pillar
     * @param y the y-coordinate of the pillar
     */
    public HauntedPillar(LevelFrame level, float x, float y) {
        // initial setup
        super(level.getGameWorld());
        this.level = level;
        setup(new Vec2(x, y));
    }
    /**
     * Constructor for HauntedPillar with elevator functionality.<br>
     * Creates a new instance of HauntedPillar with the specified parameters.
     * @param level the level frame
     * @param x the x-coordinate of the pillar
     * @param y the y-coordinate of the pillar
     * @param yDestination the y-coordinate destination for the elevator
     */
    public HauntedPillar(LevelFrame level, float x, float y, float yDestination) {
        this(level, x, y);
        addElevatorListener(yDestination);
    }
    // Methods
    /**
     * {@inheritDoc}
     */
    @Override
    public void paint() {
        addImage(PILLAR_IMAGE).setOffset(new Vec2(0, -PILLAR_IMAGE.getHalfDimensions().y));
        addImage(PILLAR_FACE_IMAGE).setOffset(new Vec2(0, PILLAR_FACE_IMAGE.getHalfDimensions().y));
    }
    /**
     * Sets up the pillar with the specified position.
     * @param position the position of the pillar
     */
    private void setup(Vec2 position) {
        halfDimensions.x = PILLAR_FACE_IMAGE.getHalfDimensions().x;
        halfDimensions.y = PILLAR_FACE_IMAGE.getHalfDimensions().y + PILLAR_IMAGE.getHalfDimensions().y;
        setPosition(position);
        new SolidFixture(this, new BoxShape(halfDimensions.x, halfDimensions.y));
        paint();
    }
    /**
     * Adds an elevator listener to the pillar.
     * @param yDestination the y-coordinate destination for the elevator
     */
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
