package game.body.walkers.mobs;
// Imports
import city.cs.engine.*;
import game.core.GameWorld;
import game.enums.State;
import game.enums.WalkerBehaviour;
import game.enums.WalkerType;
import org.jbox2d.common.Vec2;
// Class
public final class WizardWalker extends MobWalker {
    // Fields
    static final BoxShape SHAPE = new BoxShape(0.8f, 1.9f);
    public static final float HALF_X = 0.8f;
    public static final float HALF_Y = 1.9f;
    public static final State[] SUPPORTED_STATES = new State[]{
            State.ATTACK1, State.ATTACK2, State.DEATH, State.FALL,
            State.HIT, State.IDLE, State.JUMP, State.RUN
    };
    public static final WalkerBehaviour DEFAULT_BEHAVIOUR = WalkerBehaviour.AGGRESSIVE;
    public static final float CHASE_DISTANCE = 4.0f;
    // Constructor
    public WizardWalker(GameWorld gameWorld, Vec2 origin) {
        super(gameWorld, SHAPE, origin, WalkerType.WIZARD);
    }
    // Methods
}
