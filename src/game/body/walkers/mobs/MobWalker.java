package game.body.walkers.mobs;
// Imports
import game.Game;
import game.body.items.dropTable.DropTable;
import game.body.walkers.steplisteners.AggressiveStepListener;
import game.body.walkers.steplisteners.MobStepListener;
import game.body.walkers.steplisteners.PassiveStepListener;
import game.body.walkers.steplisteners.PassthroughListener;
import game.core.console.Console;
import city.cs.engine.*;
import game.core.GameWorld;
import game.body.walkers.WalkerFrame;
import game.enums.WalkerBehaviour;
import game.enums.State;
import game.enums.WalkerType;
import org.jbox2d.common.Vec2;
// Class
/**
 * The core abstract class for all mobs in the game.
 * Allows for easy creation of new mob types.
 * Core logic is handled here and passed to the necessary
 * parts, thus allowing for an API for MobWalkers.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 13-02-2025
 */
public abstract class MobWalker extends WalkerFrame {
    // Fields
    private static int mobCount = -1;
    private MobStepListener mobStepListener;
    private int healthPoints = 1000;
    private WalkerBehaviour behaviour;
    private static final DropTable dropTable = new DropTable();
    // Constructor
    /**
     *
     * @param gameWorld The game world that the mob is in.
     * @param boxShape The shape of the mob.
     * @param origin The origin of the mob.
     * @param mobType The type of mob.
     */
    public MobWalker(GameWorld gameWorld, BoxShape boxShape, Vec2 origin, WalkerType mobType) {
        super(gameWorld, boxShape, origin, mobType);
        switch  (mobType) {
            case WIZARD -> behaviour = WizardWalker.DEFAULT_BEHAVIOUR;
            case WORM -> behaviour = WormWalker.DEFAULT_BEHAVIOUR;
            case HUNTRESS -> {
                behaviour = HuntressWalker.DEFAULT_BEHAVIOUR;
                Console.warning("Warning: HuntressWalker is deprecated, and thus not fully compatible with the current game");
            }
            default -> {
                behaviour = WalkerBehaviour.PASSIVE;
                Console.error("Error: Invalid mob type, defaulting behaviour to passive.");
            }
        }
        updateName(String.valueOf(++mobCount));
        collisions();
        startWalking(2);
        GameWorld.addMob(this);
        setMobStepListener(behaviour);
    }
    // Methods
    /**
     * Adds a collision listener to the mob.
     */
    private void collisions() {
        this.addCollisionListener(e -> {
            if (e.getOtherBody() instanceof WalkerFrame walker) { // if other body is an instance of WalkerFrame, cast it to WalkerFrame as a pattern variable
                Vec2 normal = e.getNormal();
                if (normal.y == 0) { // check that the collision is along the x-axis and not y-axis
                    if (walker.isSolid() && this.isSolid()) { // ensure both walkers are solid
                        makeGhostly(); // make self ghostly
                        new PassthroughListener(this, walker);
                    }
                }
            }
        });
    }
    /**
     * Updates the name of the mob by adding the identifier to the end of the name.
     * @param identifier The identifier to add to the name.
     */
    public void updateName(String identifier) {
        setName(getWalkerType().name().toLowerCase() + identifier);
    }
    /**
     * Starts a 700 ms timer or a 2000 ms timer for a WormWalker,
     * when the timer ends, the mob attacks.
     */
    public void attack() {
        if (!getCooldown() && !isHit()) {
            toggleActionCoolDown();
            if (!isAttacking()) {
                javax.swing.Timer timer = new javax.swing.Timer(getWalkerType().equals(WalkerType.WORM) ? 2000 : 700, e -> toggleOffAttack());
                timer.setRepeats(false);
                timer.start();
                toggleOnAttack();
            }
        }
    }

    /**
     * Damages the player for 125 damage.
     */
    public void damagePlayer() {
        if (isAttacking()) {
            Game.gameWorld.getPlayer().takeDamage(125, this.getWalkerType().name());
        }
    }

    /**
     * Removes stepListeners,
     * calls {@link DropTable#dropItem(GameWorld, Vec2)} to drop an item.
     * Checks if all mobs are dead,
     * and then destroys itself.
     */
    @Override
    public void die() {
        mobStepListener.remove();
        Vec2 pos = new Vec2(getPosition().x, getPosition().y-(getWalkerType().getHalfDimensions().y/1.7f));
        dropTable.dropItem(getGameWorld(), pos);
        getGameWorld().getLevel().checkForMobsDead();
        Console.info(getName() + " has died.");
        destroy();
    }
    /**
     * Method for a mob to take damage and check if it is dead, allowing for the death system to begin.
     * @param damage may be changed to a WalkerType or something similar, used to access a static damage variable of a class.
     */
    public void takeDamage(int damage) {
        Console.debug(getName() + " taking damage: " + healthPoints + " - " + damage);
        healthPoints -= damage;
        if (healthPoints <= 0) {
            beginDeath();
        }
    }
    /**
     * Method to get the supported States.enum for the mob. Generally used for the Mob's animation frames.
     * @return array of states that the mob supports.
     */
    public abstract State[] getSupportedStates();
    /**
     * Sets the mob's behaviour stepListener,
     * which is used to determine how the mob moves and acts towards the player.
     * @param behaviour The behaviour of the mob.
     * @see WalkerBehaviour
     */
    private void setMobStepListener(WalkerBehaviour behaviour) {
        switch (behaviour) {
            case PASSIVE -> mobStepListener = new PassiveStepListener(this, getGameWorld());
            case AGGRESSIVE -> mobStepListener = new AggressiveStepListener(this, getGameWorld(), getChaseDistance());
            default -> {
                mobStepListener = new PassiveStepListener(this, getGameWorld());
                Console.error(this.getName() + ": Invalid behaviour, defaulting to passive.");
            }
        }
    }
    /**
     * Returns the value of the CHASE_DISTANCE constant
     * for the given mob type.
     * @return The chase distance of the mob.
     */
    private float getChaseDistance() {
        switch (getWalkerType()) {
            case WIZARD -> {return WizardWalker.CHASE_DISTANCE;}
            case WORM -> {return WormWalker.CHASE_DISTANCE;}
            case HUNTRESS -> {return HuntressWalker.CHASE_DISTANCE;}
            default -> {
                Console.error("Invalid mob type, defaulting to 3.0f");
                return 3.0f;
            }
        }
    }
    /**
     * Sets the WalkerBehaviour of the mob.
     * Changes the mobStepListener accordingly.
     * @param behaviour The behaviour of the mob.
     */
    public void setBehaviour(WalkerBehaviour behaviour) {
        if (this.behaviour == behaviour) {return;}
        this.behaviour = behaviour;
        getGameWorld().removeStepListener(mobStepListener);
        setMobStepListener(behaviour);
    }
    /**
     * Gets the mob's half-dimensions.
     * @return half-dimensions Vec2
     * @see Vec2
     */
    public Vec2 getHalfDimensions() {
        return getWalkerType().getHalfDimensions();
    }
}
