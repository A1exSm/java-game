package game.body.walkers.mobs;
// Imports
import city.cs.engine.BoxShape;
import game.core.GameWorld;
import game.enums.State;
import game.enums.WalkerBehaviour;
import game.enums.WalkerType;
import jdk.jfr.Timespan;
import org.jbox2d.common.Vec2;
// Class
/**
 * The HuntressWalker class represents a specific type of walker in the game, extending the MobWalker class.
 * It defines the shape, supported states, and default behaviour for the Huntress character.
 * @author Alexander Smolowitz
 * @Deprecated This class is deprecated due to its files/assets
 * lacking the ability to follow the progression of game
 * development
 */
@Deprecated
public final class HuntressWalker extends MobWalker {
    // Fields
    public static final float HALF_X = 0.8f;
    public static final float HALF_Y = 1.9f;
    private static final State[] SUPPORTED_STATES = new State[]{ State.ATTACK1, State.ATTACK2, State.ATTACK3, State.DEATH, State.FALL, State.HIT, State.IDLE, State.JUMP, State.RUN };
    public static final WalkerBehaviour DEFAULT_BEHAVIOUR = WalkerBehaviour.AGGRESSIVE;
    public static final float CHASE_DISTANCE = 4.0f;
    // Constructor
    public HuntressWalker(GameWorld gameWorld, Vec2 origin) {
        super(gameWorld,  new BoxShape(0.8f, 1.9f), origin, WalkerType.HUNTRESS);
    }
    // Methods
    @Override
    public State[] getSupportedStates() {
        return SUPPORTED_STATES;
    }

}
