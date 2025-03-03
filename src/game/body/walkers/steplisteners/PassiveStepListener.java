package game.body.walkers.steplisteners;
// Imports

import game.GameWorld;
import game.body.walkers.mobs.MobWalker;
import org.jbox2d.common.Vec2;

// Class
public class PassiveStepListener extends MobStepListener {
    // Fields
    // Constructor
    public PassiveStepListener(MobWalker mob, GameWorld gameWorld) {
        super(mob, gameWorld);
    }
    // Methods
    @Override
    public void handleMobMovement(Vec2 pos) {
            patrolArea(pos);
    }
}
