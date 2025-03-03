package game.body.walkers.mobs;
// Imports

import city.cs.engine.BoxShape;
import game.GameWorld;
import game.enums.State;
import game.enums.WalkerBehaviour;
import game.enums.Walkers;
import org.jbox2d.common.Vec2;

// Class
public class WormWalker extends MobWalker{
    // Fields
    public static final float HALF_X = 2.0f;
    public static final float HALF_Y = 2.0f;
    public static final State[] SUPPORTED_STATES = new State[]{
            State.ATTACK1, State.DEATH, State.HIT, State.IDLE, State.RUN
    };
    public static final WalkerBehaviour DEFAULT_BEHAVIOUR = WalkerBehaviour.AGGRESSIVE;
    // Constructor
    public WormWalker(GameWorld gameWorld, Vec2 origin) {
        super(gameWorld, new BoxShape(2, 2), origin, Walkers.WORM);
        GameWorld.addMob(this);
    }

    // Methods
}
