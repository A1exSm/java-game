package game.body.staticstructs.ground.hauntedForest;
// Imports
import city.cs.engine.*;
import game.core.GameWorld;
import org.jbox2d.common.Vec2;
// Class
/**
 * A moving elevator which spawns a HauntedPillar when it reaches its destination.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 04-04-2025
 */
class HauntedElevator extends DynamicBody {
    // Fields
    private static int count = 0;
    // Constructor
    /**
     * Constructor for HauntedElevator.<br>
     * Creates a new instance of HauntedElevator with the specified parameters.
     * @param gameWorld the game world
     * @param dimensions the dimensions of the elevator
     * @param pos the position of the elevator
     * @param yDestination the y-coordinate of the destination
     */
    HauntedElevator(GameWorld gameWorld, Vec2 dimensions, Vec2 pos, float yDestination) {
        super(gameWorld);
        // initial setup
        setName("Elevator" + (++count));
        setPosition(pos);
        new SolidFixture(this, new BoxShape(dimensions.x, dimensions.y));
        // Images
         addImage(HauntedPillar.PILLAR_IMAGE).setOffset(new Vec2(0, -HauntedPillar.PILLAR_IMAGE.getHalfDimensions().y));
         addImage(HauntedPillar.PILLAR_FACE_IMAGE).setOffset(new Vec2(0, HauntedPillar.PILLAR_FACE_IMAGE.getHalfDimensions().y));
         // StepListener
        gameWorld.addStepListener(new StepListener() {
            @Override
            public void preStep(StepEvent stepEvent) {
                if (getPosition().y >= yDestination) {
                    new HauntedPillar(gameWorld.getLevel(), getPosition().x, getPosition().y);
                    destroy();
                    gameWorld.getPlayer().setLinearVelocity(new Vec2(getLinearVelocity().x, 0));
                    gameWorld.removeStepListener(this);
                } else {
                    setLinearVelocity(new Vec2(0, 50));
                    setAngle(0);
                }
            }
            @Override
            public void postStep(StepEvent stepEvent) {}
        });
    }
}
