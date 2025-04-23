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
public class MobWalker extends WalkerFrame {
    // Fields
    private static int mobCount = -1;
    private MobStepListener mobStepListener;
    private static final int MAX_HP = 1000;
    private int healthPoints = 1000;
    private WalkerBehaviour behaviour;
    private static final DropTable dropTable = new DropTable();
    // Constructor
    public MobWalker(GameWorld gameWorld, BoxShape boxShape, Vec2 origin, WalkerType mobType) {
        super(gameWorld, boxShape, origin, mobType);
        switch  (mobType) {
            case WIZARD -> {
                behaviour = WizardWalker.DEFAULT_BEHAVIOUR;
            }
            case WORM -> {
                behaviour = WormWalker.DEFAULT_BEHAVIOUR;
            }
            case HUNTRESS -> {
                behaviour = HuntressWalker.DEFAULT_BEHAVIOUR;
            }
            default -> {
                behaviour = WalkerBehaviour.PASSIVE;
                Console.error("Error: Invalid mob type, defaulting.");
            }
        }
        updateName(String.valueOf(++mobCount));
        collisions();
        startWalking(2);
        GameWorld.addMob(this);
        setMobStepListener(behaviour);
    }
    // Methods
    private void collisions() {
        this.addCollisionListener(e -> {
            if (e.getOtherBody() instanceof WalkerFrame walker) {
                Vec2 normal = e.getNormal();
                if (normal.y == 0) {
                    if (walker.isSolid() && this.isSolid()) {
                        makeGhostly();
                        new PassthroughListener(this, walker);
                    }
                }
            }
        });
    }

    public void updateName(String identifier) {
        setName(getWalkerType().name().toLowerCase() + identifier);
    }

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

    public void damagePlayer() {
        if (isAttacking()) {
            Game.gameWorld.getPlayer().takeDamage(125, this.getWalkerType().name());
        }
    }


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
    public State[] getSupportedStates() {
        switch (getWalkerType()) {
            case WIZARD -> {return WizardWalker.SUPPORTED_STATES;}
            case WORM -> {return WormWalker.SUPPORTED_STATES;}
            case HUNTRESS ->  {return HuntressWalker.SUPPORTED_STATES;}
            default -> {return null;}
        }
    }

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

    public WalkerBehaviour getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(WalkerBehaviour behaviour) {
        if (this.behaviour == behaviour) {return;}
        this.behaviour = behaviour;
        getGameWorld().removeStepListener(mobStepListener);
        setMobStepListener(behaviour);
    }

    public Shape getShape() {
        switch (getWalkerType()) {
            case WIZARD -> {return WizardWalker.SHAPE;}
            case WORM -> {return WormWalker.SHAPE;}
            case HUNTRESS -> {return HuntressWalker.SHAPE;}
            default -> {
                Console.error("Invalid mob type: " + getWalkerType() + ", defaulting to null.");
                return null;
            }
        }
    }

    public Vec2 getHalfDimensions() {
        return getWalkerType().getHalfDimensions();
    }
}
