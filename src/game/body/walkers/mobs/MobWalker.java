package game.body.walkers.mobs;
// Imports
import game.body.items.HealthVial;
import game.body.walkers.PlayerWalker;
import game.body.walkers.steplisteners.AggressiveStepListener;
import game.body.walkers.steplisteners.MobStepListener;
import game.body.walkers.steplisteners.PassiveStepListener;
import game.body.walkers.steplisteners.PassthroughListener;
import game.enums.Direction;
import city.cs.engine.*;
import game.GameWorld;
import game.body.walkers.WalkerFrame;
import game.enums.WalkerBehaviour;
import game.enums.items.ItemSize;
import game.enums.State;
import game.enums.Walkers;
import org.jbox2d.common.Vec2;

// Class
public class MobWalker extends WalkerFrame {
    // Fields
    public final float HALF_X;
    public final float HALF_Y;
    private static int mobCount = -1;
    private MobStepListener mobStepListener;
    private static final int MAX_HP = 1000;
    private int healthPoints = 1000;
    private WalkerBehaviour behaviour;

    {
        mobCount++;
    }

    // Constructor
    public MobWalker(GameWorld gameWorld, BoxShape boxShape, Vec2 origin, Walkers mobType) {
        super(gameWorld, boxShape, origin, mobType);
        switch  (mobType) {
            case WIZARD -> {
                HALF_X = WizardWalker.HALF_X;
                HALF_Y = WizardWalker.HALF_Y;
                behaviour = WizardWalker.DEFAULT_BEHAVIOUR;
            }
            case WORM -> {
                HALF_X = WormWalker.HALF_X;
                HALF_Y = WormWalker.HALF_Y;
                behaviour = WormWalker.DEFAULT_BEHAVIOUR;
            }
            default -> {
                HALF_X = 0.0f;
                HALF_Y = 0.0f;
                behaviour = WalkerBehaviour.PASSIVE;

            }
        }
        initName();
        collisions();
        startWalking(2);
        GameWorld.addMob(this);

        setMobStepListener(behaviour);
    }
    // Methods
    protected void initName() {
        setName(getWalkerType().name().toLowerCase() + mobCount);
    }


    private void collisions() {
        this.addCollisionListener(e -> {
            Vec2 normal = e.getNormal();
            if (normal.y == 0) {
                if (GameWorld.getMobs().contains(e.getOtherBody())) {
                    makeGhostly();
                    for (MobWalker mob : GameWorld.getMobs()) {
                        if (mob == e.getOtherBody()) {
                            javax.swing.Timer timer = new javax.swing.Timer(200, event -> {
                                new PassthroughListener(this, mob);
                            });
                            timer.setRepeats(false);
                            timer.start();
                        }
                    }
                } else if ((e.getOtherBody().getName().equals("Player") && this.behaviour != WalkerBehaviour.AGGRESSIVE) || getWorld().getStaticBodies().contains(e.getOtherBody())) {
                    if (normal.x < 0) {
                        setDirection(Direction.RIGHT);
                        startWalking(2);
                    } else {
                        setDirection(Direction.LEFT);
                        startWalking(-2);
                    }
                }
            }
        });
    }


    public void attack(PlayerWalker player) {
        if (!getCooldown() && !getHit()) {
            toggleActionCoolDown();
            toggleOnAttack();
            player.takeDamage(125, getWalkerType());
        }
    }


    @Override
    public void die() {
        mobStepListener.remove();
        new HealthVial(ItemSize.SMALL, new Vec2(getPosition().x, getPosition().y-(HALF_Y/1.7f))); // -HALF_Y/1.7f to make the item appear slightly buried in the ground under the mob
        destroy();
    }

    /**
     * Method for a mob to take damage and check if it is dead, allowing for the death system to begin.
     * @param damage may be changed to a WalkerType or something similar, used to access a static damage variable of a class.
     */
    public void takeDamage(int damage) {
        System.out.println(getName() + " taking damage: " + healthPoints + " - " + damage);
        healthPoints -= damage;
        toggleOnHit();
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
            default -> {return null;}
        }
    }

    private void setMobStepListener(WalkerBehaviour behaviour) {
        switch (behaviour) {
            case PASSIVE -> mobStepListener = new PassiveStepListener(this, getGameWorld());
            case AGGRESSIVE -> mobStepListener = new AggressiveStepListener(this, getGameWorld());
            default -> {
                mobStepListener = new PassiveStepListener(this, getGameWorld());
                System.err.println(this.getName() + ": Invalid behaviour, defaulting to passive.");
            }
        }
    }

    public WalkerBehaviour getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(WalkerBehaviour behaviour) {
        this.behaviour = behaviour;
        getGameWorld().removeStepListener(mobStepListener);
        setMobStepListener(behaviour);
    }

}
