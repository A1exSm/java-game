package game.body.staticstructs;
// Imports
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import game.body.staticstructs.ground.TempGround;
import game.core.GameWorld;
import org.jbox2d.common.Vec2;
// Class
/**
 * Trampoline class
 * This class creates a trampoline object in the game world.
 * It consists of a mid-piece and two legs, with a bouncy part on top.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 03-02-2025
 * @deprecated replaced by
 * {@link game.body.staticstructs.ground.hauntedForest.HauntedPillar HauntedPillar's}
 * HauntedElevator, which allows fast vertical travel.
 */
@Deprecated
public class Trampoline {
    // Constructor
    /**
     * Creates a trampoline object in the game world.
     * @param gameWorld The game world where the trampoline is created.
     * @param pos The position of the trampoline in the game world.
     */
    public Trampoline(GameWorld gameWorld, Vec2 pos) {
        TempGround midPiece = new TempGround(gameWorld, new Vec2(2, 0.2f), pos);
        new SolidFixture(midPiece, new BoxShape(0.25f, 1, new Vec2(-2.25f, -0.2f)));
        new SolidFixture(midPiece, new BoxShape(0.25f, 1, new Vec2(2.25f, -0.2f)));
        SolidFixture bouncyPart = new SolidFixture(midPiece, new BoxShape(2, 0.25f));
        bouncyPart.setRestitution(2f);
        midPiece.addImage(new BodyImage("data/Trampoline.png", 5f));
    }
}
