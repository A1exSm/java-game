package game.body.walkers.mobs;
// Imports
import game.Game;
import game.body.items.HealthPotion;
import game.body.walkers.PlayerWalker;
import game.body.walkers.steplisteners.AggressiveStepListener;
import game.body.walkers.steplisteners.MobStepListener;
import game.body.walkers.steplisteners.PassiveStepListener;
import game.body.walkers.steplisteners.PassthroughListener;
import game.enums.Direction;
import city.cs.engine.*;
import game.core.GameWorld;
import game.body.walkers.WalkerFrame;
import game.enums.WalkerBehaviour;
import game.enums.items.ItemSize;
import game.enums.State;
import game.enums.Walkers;
import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.HashMap;

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
    private static final HashMap<ItemSize, int[]> dropRates = new HashMap<>();

    static {
        dropRates.put(ItemSize.SMALL, new int[]{
            17, 45, 12, 78, 91, 99, 97, 49, 56, 21, 35, 50, 41, 64, 26, 23, 7, 24, 29, 42, 71, 98, 59, 62, 77, 30, 96, 72, 85, 92, 75, 28, 93, 18, 47, 38, 61, 34, 9, 4, 31, 86, 13, 73, 20, 2, 15, 76, 3, 48
        });
        dropRates.put(ItemSize.MEDIUM, new int[]{
                9, 71, 86, 75, 29, 98, 72, 41, 28, 34, 76, 96, 2, 18, 30, 59, 73, 64, 78, 3, 42, 20, 4, 92, 99
        });
        dropRates.put(ItemSize.LARGE, new int[] {
                28, 41, 42, 3, 96, 34, 73, 59, 92, 76
        });
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
            case HUNTRESS -> {
                HALF_X = HuntressWalker.HALF_X;
                HALF_Y = HuntressWalker.HALF_Y;
                behaviour = HuntressWalker.DEFAULT_BEHAVIOUR;
            }
            default -> {
                HALF_X = 0.0f;
                HALF_Y = 0.0f;
                behaviour = WalkerBehaviour.PASSIVE;
                System.err.println("Error: Invalid mob type, defaulting.");
            }
        }
        mobCount++;
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
    private void dropLoot() {
        int randInt = (int) (Math.random() * 100);
        for (ItemSize size : dropRates.keySet()) {
            for (int i : dropRates.get(size)) {
                if (randInt == i) {
                    new HealthPotion(size, new Vec2(getPosition().x, getPosition().y-(HALF_Y/1.7f)));
                    return;
                }
            }
        }
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


    public void attack() {
        if (!getCooldown() && !getHit()) {
            toggleActionCoolDown();
            toggleOnAttack();
        }
    }

    public void damagePlayer() {
        if (getAttacking()) {
            Game.gameWorld.getPlayer().takeDamage(125, this.getWalkerType().name());
        }
    }


    @Override
    public void die() {
        mobStepListener.remove();
        dropLoot();
        destroy();
    }

    /**
     * Method for a mob to take damage and check if it is dead, allowing for the death system to begin.
     * @param damage may be changed to a WalkerType or something similar, used to access a static damage variable of a class.
     */
    public void takeDamage(int damage) {
        System.out.println(getName() + " taking damage: " + healthPoints + " - " + damage);
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
                System.err.println(this.getName() + ": Invalid behaviour, defaulting to passive.");
            }
        }
    }

    private float getChaseDistance() {
        switch (getWalkerType()) {
            case WIZARD -> {return WizardWalker.CHASE_DISTANCE;}
            case WORM -> {return WormWalker.CHASE_DISTANCE;}
            case HUNTRESS -> {return HuntressWalker.CHASE_DISTANCE;}
            default -> {
                System.err.println("Invalid mob type, defaulting to 3.0f");
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

}
