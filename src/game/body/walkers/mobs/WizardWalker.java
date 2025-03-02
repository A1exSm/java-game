package game.body.walkers.mobs;
// Imports
import city.cs.engine.*;
import game.GameWorld;
import game.enums.State;
import game.enums.Walkers;
import org.jbox2d.common.Vec2;
// Class
public class WizardWalker extends MobWalker {
    // Fields
    public static final float HALF_X = 1.0f;
    public static final float HALF_Y = 2.0f;
    public static final State[] SUPPORTED_STATES = new State[]{
            State.ATTACK1, State.ATTACK2, State.DEATH, State.FALL,
            State.HIT, State.IDLE, State.JUMP, State.RUN
    };
    // Constructor
    public WizardWalker(GameWorld gameWorld, Vec2 origin) {
        super(gameWorld, new BoxShape(1,2), origin, true, Walkers.WIZARD);
    }

}
