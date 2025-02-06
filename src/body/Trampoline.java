package body;
// Imports
import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import game.GameWorld;
import org.jbox2d.common.Vec2;
// Class
public class Trampoline {
    // Constructor
    public Trampoline(GameWorld world, Vec2 pos) {
        Ground midPiece = new Ground(world, new Vec2(2, 0.2f), pos);
        SolidFixture legL = new SolidFixture(midPiece, new BoxShape(0.25f, 1, new Vec2(-2.25f, -0.2f)));
        SolidFixture lefR = new SolidFixture(midPiece, new BoxShape(0.25f, 1, new Vec2(2.25f, -0.2f)));
        SolidFixture bouncyPart = new SolidFixture(midPiece, new BoxShape(2, 0.25f));
        bouncyPart.setRestitution(2f);
        midPiece.addImage(new BodyImage("data/Trampoline.png", 5f));
    }
}
