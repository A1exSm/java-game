package game.body.walkers.mobs;
// Imports

import city.cs.engine.BoxShape;
import game.GameWorld;
import game.body.Projectile;
import game.body.walkers.PlayerWalker;
import game.enums.Direction;
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
    public static final float CHASE_DISTANCE = 15.0f;
    // Constructor
    public WormWalker(GameWorld gameWorld, Vec2 origin) {
        super(gameWorld, new BoxShape(2, 2), origin, Walkers.WORM);
        GameWorld.addMob(this);
    }
    // Methods
    @Override
    public void attack(PlayerWalker player) {
        if (!getCooldown() && !getHit()) {
            toggleActionCoolDown();
            toggleOnAttack();
//            javax.swing.Timer attackTimer = new javax.swing.Timer(1200, e -> {
//                shootProjectile();
//            });
//            attackTimer.setRepeats(false);
//            attackTimer.start();
        }
    }

    public void shootProjectile() {
        Vec2 spawnLocation = getPosition();
        if (getDirection() == Direction.LEFT) {
            spawnLocation.x -= (HALF_X + 0.6f); // adds half_width of projectile +0.1 to ensure it does not collide with its marksman.
        } else {
            spawnLocation.x += (HALF_X + 0.6f);
        }
        spawnLocation.y += (HALF_Y - 0.5f);
        new Projectile(this, getDirection(), 60 ,spawnLocation);
    }
}
