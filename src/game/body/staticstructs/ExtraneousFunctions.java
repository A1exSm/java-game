package game.body.staticstructs;
// Imports
import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import game.GameWorld;
import org.jbox2d.common.Vec2;
// Class
class ExtraneousFunctions {
    // Fields
    private final GameWorld world;
    protected ExtraneousFunctions(GameWorld world) {
        this.world = world;
    }
    // Constructor
    private void Stairs(Vec2 halfStepDimensions, Vec2 originPos, int numSteps, String vertical, String horizontal) {
        if (vertical.equals("DOWN")) halfStepDimensions.y = -halfStepDimensions.y;
        if (horizontal.equals("LEFT")) halfStepDimensions.x = -halfStepDimensions.x;
        for (int i = 0; i < numSteps; i++) {
            new StaticBody(world, new BoxShape(halfStepDimensions.x, halfStepDimensions.y, new Vec2(originPos.x + ((halfStepDimensions.x * 2) * i), originPos.y + ((halfStepDimensions.y * 2) * i))));
        }
    }
}
