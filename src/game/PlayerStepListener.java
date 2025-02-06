package game;
// Imports
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import org.jbox2d.common.Vec2;
// Class
class PlayerStepListener implements StepListener {
    // Fields
    private final Player player;
    private boolean lastRight = true;
    // Constructor
    protected PlayerStepListener(GameWorld world, Player player) {
        this.player = player;
        world.addStepListener(this);
    }
    // Methods
    @Override
    public void preStep(StepEvent stepEvent) {
    }
    @Override
    public void postStep(StepEvent stepEvent) {}
}

