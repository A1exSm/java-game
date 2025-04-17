package game.core;
// Imports
import game.Game;
import game.body.items.Inventory;
import game.body.walkers.PlayerWalker;
import game.body.walkers.mobs.MobWalker;
import game.body.walkers.mobs.WizardWalker;
import city.cs.engine.*;
import game.body.walkers.mobs.WormWalker;
import game.core.console.Console;
import game.enums.Environments;
import game.enums.State;
import game.enums.WalkerBehaviour;
import game.enums.WalkerType;
import game.levels.GothicCemetery;
import game.levels.HauntedForest;
import game.levels.LevelFrame;
import game.levels.MagicCliff;
import game.animation.*;

import java.util.ArrayList;

// Class
/**
 * The `GameWorld` class extends `World` and represents the game world.
 * It handles the initialisation of the player, mobs, bodies, and various game elements.
 */
public class GameWorld extends World {
    // Fields
    private static Game game;
    private final PlayerWalker player;
    private static final ArrayList<MobWalker> mobs = new ArrayList<>();
    public static final Inventory playerInventory = new Inventory(4);
    private boolean toggleMobsPassive = false;
    public final LevelFrame environment;

    // Constructor
    /**
     * Constructs a `GameWorld` with the specified game.
     *
     * @param game the game instance
     */
    public GameWorld(Game game, Environments environment, int level) {
        super();
        GameWorld.game = game;
        player = new PlayerWalker(this);
        new WalkerAnimationFrames(State.RUN, WalkerType.PLAYER);
        this.environment = populate(environment, level);
        this.environment.start();
        // end of constructor start of a new world :)
        start();
    }

    // Population methods

    /**
     * Defines the level.
     */
    private LevelFrame populate(Environments environment, int level) {
        switch (environment) {
            case MAGIC_CLIFF -> {return new MagicCliff(this, level);}
            case HAUNTED_FOREST -> {return new HauntedForest(this, level);}
            case GOTHIC_CEMETERY -> {return new GothicCemetery(this, level);}
            default -> {throw new IllegalArgumentException(Console.exceptionMessage("Invalid environment: " + environment));}
        }
    }
    // External Getters & Setters
    /**
     * Toggles the player's attack state.
     */
    public void togglePlayerAttack() {
        player.toggleOffAttack();
    }
    /**
     * Gets the player walker instance.
     *
     * @return the player walker
     */
    public PlayerWalker getPlayer() {
        return player;
    }
    /**
     * Toggles the game's pause state.<br><br>
     * Since {@link Game#togglePause()} is not static, it can be accessed through 'Game.gameWorld.togglePause()'.
     */
    public void togglePause() {
        game.togglePause();
    }
    /**
     * Uses an item from the player's inventory at the specified index.<br>
     * index corresponds to the item's visual position in the inventory. starting from the left.
     *
     * @param index the index of the item to use
     */
    public static void useInventoryItem(int index) {
        playerInventory.use(index);
    }
    /**
     * Drops an {@link game.body.items.InventoryItem InventoryItem} from the player's inventory at the specified index.
     *
     * @param index the index of the item to drop
     */
    public static void dropInventoryItem(int index) {
        playerInventory.drop(index);
    }

    //Mobology
    /**
     * Gets the list of alive {@link MobWalker MobWalkers}.
     *
     * @return the list of alive Mobs
     */
    public static ArrayList<MobWalker> getMobs() {
        ArrayList<MobWalker> aliveMobs = new ArrayList<>();
        for (MobWalker mob : mobs) {
            if(!mob.isDead()) aliveMobs.add(mob);
        }
        return aliveMobs;
    }

    public static void addMob(MobWalker mob) {
        mobs.add(mob);
    }
    /**
     * Removes a mob from the game world.<br>
     * Possibly deprecated, as mobs are ignored by {@link #getMobs()} when they die.
     *
     * @param mob the mob to remove
     */
    public static void removeMob(MobWalker mob) {
        mobs.remove(mob);
    }
    /**
     * Finds a {@link WormWalker} by name.
     *
     * @param name the name of the {@link WormWalker}
     * @return the {@link WormWalker} with the specified name, or null if not found
     */
    public static WormWalker nameToWorm(String name) {
        for (MobWalker mob : mobs) {
            if (mob.getWalkerType().equals(WalkerType.WORM) && mob.getName().equals(name)) { // ensures correct type & name
                return (WormWalker) mob; // casts the mob to a WormWalker
            }
        }
        Console.warning("WormWalker with name: "+ name + " not found! Returning null.");
        return null;
    }
    /**
     * Finds a {@link WizardWalker} by name.
     *
     * @param name the name of the {@link WizardWalker}
     * @return the {@link WizardWalker} with the specified name, or null if not found
     */
    public static WizardWalker nameToWizard(String name) {
        for (MobWalker mob : mobs) {
            if (mob.getWalkerType().equals(WalkerType.WIZARD) && mob.getName().equals(name)) { // ensures correct type & name
                return (WizardWalker) mob; // casts the mob to a WormWalker
            }
        }
        Console.warning("WizardWalker with name: "+ name + " not found! Returning null.");
        return null;
    }
    /**
     * Toggles the state of all mobs to passive.
     */
    public void toggleMobsPassive() { // only meant to be called during setup
        toggleMobsPassive = !toggleMobsPassive;
        for (MobWalker mob : mobs) {
            mob.setBehaviour(WalkerBehaviour.PASSIVE);
        }
    }

}
