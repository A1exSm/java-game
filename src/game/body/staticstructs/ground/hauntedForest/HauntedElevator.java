package game.body.staticstructs.ground.hauntedForest;
// Imports

import city.cs.engine.*;
import game.core.GameWorld;
import game.levels.HauntedForest;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
class HauntedElevator extends DynamicBody {
    // Fields
    private static int count = 0;
    public final Vec2 halfDimensions;
    // Constructor
    HauntedElevator(GameWorld gameWorld, Vec2 dimensions, Vec2 pos, float yDestination, HauntedForest level) {
        super(gameWorld);
        // initial setup
        setName("Elevator" + (++count));
        setPosition(pos);
        halfDimensions = dimensions;
        new SolidFixture(this, new BoxShape(halfDimensions.x, halfDimensions.y));
        // Images
         addImage(HauntedPillar.PILLAR_IMAGE).setOffset(new Vec2(0, -HauntedPillar.PILLAR_IMAGE.getHalfDimensions().y));
         addImage(HauntedPillar.PILLAR_FACE_IMAGE).setOffset(new Vec2(0, HauntedPillar.PILLAR_FACE_IMAGE.getHalfDimensions().y));
         // StepListener
        gameWorld.addStepListener(new StepListener() {
            @Override
            public void preStep(StepEvent stepEvent) {
                if (getPosition().y >= yDestination) {
                    new HauntedPillar(gameWorld, getPosition(), level);
                    destroy();
                    gameWorld.getPlayer().setLinearVelocity(new Vec2(getLinearVelocity().x, 0));
                    gameWorld.removeStepListener(this);
                } else {
                    setLinearVelocity(new Vec2(0, 20));
                    setAngle(0);
                }
            }

            @Override
            public void postStep(StepEvent stepEvent) {

            }
        });
    }
    // Methods
}
