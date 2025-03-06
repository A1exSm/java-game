package game.body.walkers.steplisteners;
// Imports

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.Game;
import game.body.walkers.mobs.MobWalker;

// Class
public class PassthroughListener implements StepListener {
    // Fields
    private final MobWalker passThroughMob;
    private final MobWalker mob;
    // Constructor
    public PassthroughListener(MobWalker mob, MobWalker passThroughMob) {
        this.mob = mob;
        this.passThroughMob = passThroughMob;
        Game.gameWorld.addStepListener(this);
    }
    // Methods
    @Override
    public void preStep(StepEvent stepEvent) {
        if ((mob.getPosition().x - mob.HALF_X) > (passThroughMob.getPosition().x + passThroughMob.HALF_X) || (mob.getPosition().x + mob.HALF_X) < (passThroughMob.getPosition().x - passThroughMob.HALF_X)) {
            mob.makeSolid();
            Game.gameWorld.removeStepListener(this);
        }

    }
    
    @Override
    public void postStep(StepEvent stepEvent) {
    }
}
