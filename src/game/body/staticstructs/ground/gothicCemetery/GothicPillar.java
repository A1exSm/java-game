package game.body.staticstructs.ground.gothicCemetery;
// Imports
import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import game.utils.GameBodyImage;
import org.jbox2d.common.Vec2;
// Class
/**
 * A child of GroundFrame,
 * this class is used to create a pillar in the gothic cemetery.
 * It has a 50% chance of having vines on it.
 * The vines are randomly placed on the pillar.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 06-04-2025
 */
public final class GothicPillar extends GroundFrame {
    // Fields
    /**
     * The image of the pillar. <br>
     * <img src="doc-files/ground_pillar.png" alt="a pillar">
     */
    public static final GameBodyImage IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-Tiles/ground_pillar.png", 12f);
    /**
     * The image of the vines. <br>
     * <img src="doc-files/pillar_vines.png" alt="pillar Vines">
     */
    private static final GameBodyImage VINES_IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-Tiles/pillar_vines.png", 4);
    private final boolean vines;
    private float vineYOffset;
    // Constructor
    /**
     * Creates a new GothicPillar object.
     * Adds vines to the pillar with a 50% chance.
     * @param gameWorld the game world
     * @param x the x position of the pillar
     * @param y the y position of the pillar
     */
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
    /**
     * {@inheritDoc}
     */
    @Override
    public void paint() {
        addImage(IMG).setOffset(new Vec2(0, 3.3f));
        if (vines) {addImage(VINES_IMG).setOffset(new Vec2(0, vineYOffset));}
    }
}
