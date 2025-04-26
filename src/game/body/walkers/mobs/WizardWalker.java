package game.body.walkers.mobs;
// Imports
import city.cs.engine.*;
import game.core.GameWorld;
import game.enums.State;
import game.enums.WalkerBehaviour;
import game.enums.WalkerType;
import org.jbox2d.common.Vec2;
// Class

/**
 * A Wizard mob that extends MobWalker, and is used to represent a wizard in the game.
 * This class defines the shape, size, and behaviour of the wizard
 * within the MobWalker API.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 13-02-2025
 */
public final class WizardWalker extends MobWalker {
    // Fields
    /**
     * Half the width of the wizard body.
     */
    public static final float HALF_X = 0.8f;
    /**
     * Half the height of the wizard body.
     */
    public static final float HALF_Y = 1.9f;
    /**
     * The distance at which a wizard will chase
     * up to the player, before attacking.
     */
    public static final float CHASE_DISTANCE = 4.0f;
    /**
     * The behaviour of a wizard
     */
    public static final WalkerBehaviour DEFAULT_BEHAVIOUR = WalkerBehaviour.AGGRESSIVE;
    private static final State[] SUPPORTED_STATES = new State[]{ State.ATTACK1, State.ATTACK2, State.DEATH, State.FALL, State.HIT, State.IDLE, State.JUMP, State.RUN };
    // Constructor
    /**
     * Creates a new WizardWalker at the given position in the provided {@link GameWorld}.
     * @param gameWorld The game world in which the wizard will exist.
     * @param origin The initial position of the wizard in the game world.
     */
    public WizardWalker(GameWorld gameWorld, Vec2 origin) {
        super(gameWorld, new BoxShape(0.8f, 1.9f), origin, WalkerType.WIZARD);
    }
    // Methods
    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public State[] getSupportedStates() {
        return SUPPORTED_STATES;
    }
}
