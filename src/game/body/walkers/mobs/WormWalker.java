package game.body.walkers.mobs;
// Imports
import city.cs.engine.BoxShape;
import game.core.GameWorld;
import game.body.projectiles.Projectile;
import game.enums.Direction;
import game.enums.State;
import game.enums.WalkerBehaviour;
import game.enums.WalkerType;
import org.jbox2d.common.Vec2;
// Class
/**
 * A Worm mob that extends MobWalker, and is used to represent a worm in the game.
 * This class defines the shape, size, and behaviour of a worm
 * within the MobWalker API.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 19-02-2025
 */
public class WormWalker extends MobWalker { // worm does not have additional fixtures due to animation constraints (it moves a lot)
    // Fields
    /**
     * Half the height of the worm body.
     */
    public static final float HALF_X = 2.0f;
    /**
     * Half the height of the worm body.
     */
    public static final float HALF_Y = 2.0f;
    /**
     * The behaviour of a worm
     */
    public static final WalkerBehaviour DEFAULT_BEHAVIOUR = WalkerBehaviour.AGGRESSIVE;
    /**
     * The distance at which a worm will chase
     * up to the player, before attacking.
     */
    public static final float CHASE_DISTANCE = 15.0f;
    private static final State[] SUPPORTED_STATES = new State[]{ State.ATTACK1, State.DEATH, State.HIT, State.IDLE, State.RUN };
    // Constructor
    /**
     * Creates a new WormWalker at the given position in the provided {@link GameWorld}.
     * @param gameWorld The game world in which the worm will exist.
     * @param origin The initial position of the worm in the game world.
     */
    public WormWalker(GameWorld gameWorld, Vec2 origin) {
        super(gameWorld, new BoxShape(2, 2), origin, WalkerType.WORM);
        GameWorld.addMob(this);
    }
    // Methods
    /**
     * Shoots calculates the spawn location of the projectile
     * its direction, and then initialises the projectile.
     * Replaces the background bodies at the spawnLocation
     * to ensure that the projectile is visible.
     */
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
    /**
     * {@inheritDoc}
     */
    @Override
    public State[] getSupportedStates() {
        return SUPPORTED_STATES;
    }
}
