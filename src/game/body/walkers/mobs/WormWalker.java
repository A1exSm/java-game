package game.body.walkers.mobs;
// Imports

import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import game.body.staticstructs.ground.GroundFrame;
import game.body.staticstructs.ground.hauntedForest.HauntedBackdrop;
import game.core.GameWorld;
import game.body.projectiles.Projectile;
import game.enums.Direction;
import game.enums.State;
import game.enums.WalkerBehaviour;
import game.enums.WalkerType;
import org.jbox2d.common.Vec2;

// Class
public class WormWalker extends MobWalker { // worm does not have additional fixtures due to animation constraints (it moves ALOT)
    // Fields
    protected static final BoxShape SHAPE = new BoxShape(2, 2);
    public static final float HALF_X = 2.0f;
    public static final float HALF_Y = 2.0f;
    public static final State[] SUPPORTED_STATES = new State[]{
            State.ATTACK1, State.DEATH, State.HIT, State.IDLE, State.RUN
    };
    public static final WalkerBehaviour DEFAULT_BEHAVIOUR = WalkerBehaviour.AGGRESSIVE;
    public static final float CHASE_DISTANCE = 15.0f;
    // Constructor
    public WormWalker(GameWorld gameWorld, Vec2 origin) {
        super(gameWorld, SHAPE, origin, WalkerType.WORM);
        GameWorld.addMob(this);
    }
    // Methods
    public void shootProjectile() {
        Vec2 spawnLocation = getPosition();
        if (getDirection() == Direction.LEFT) {
            spawnLocation.x -= (HALF_X + 0.6f); // adds half_width of projectile +0.1 to ensure it does not collide with its marksman.
        } else {
            spawnLocation.x += (HALF_X + 0.6f);
        }
        spawnLocation.y += (HALF_Y - 0.5f);
        new Projectile(this, getDirection(), 60 ,spawnLocation);
        soundFX.attack1();
        getGameWorld().rePlaceBodies(spawnLocation);
    }
}
