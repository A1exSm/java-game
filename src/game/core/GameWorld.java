package game.core;
// Imports
import game.Game;
import game.body.items.Inventory;
import game.body.staticstructs.ground.hauntedForest.HauntedBackdrop;
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
import org.jbox2d.common.Vec2;
import java.util.ArrayList;

// Class
/**
 * Extends {@link World} and represents the game world.
 *  @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 *  @since 30-01-2025
 */
public final class GameWorld extends World {
    // Fields
    private final Game game;
    private final PlayerWalker player;
    private static final ArrayList<MobWalker> mobs = new ArrayList<>();
    private final Inventory playerInventory = new Inventory(4);
    private boolean toggleMobsPassive = false;
    private final LevelFrame environment;
    // Constructor
    /**
     * Constructs a new GameWorld with the specified game, environment, and level.
     *
     * @param game the game instance
     * @param environment the environment type
     * @param level the level number
     */
    public GameWorld(Game game, Environments environment, int level) {
        super();
        this.game = game;
        player = new PlayerWalker(this);
        new WalkerAnimationFrames(State.RUN, WalkerType.PLAYER);
        this.environment = populate(environment, level);
        this.environment.start();
        // end of constructor start of a new world
        start();
    }

    /**
     * Populates the game world with the specified environment and level.
     * @param environment the environment type
     * @param level the level number
     * @return the populated level
     * @throws IllegalArgumentException if the environment is not:
     * {@link Environments#MAGIC_CLIFF}, {@link Environments#HAUNTED_FOREST}, or {@link Environments#GOTHIC_CEMETERY}
     * @see Environments
     * @see LevelFrame
     */
    private LevelFrame populate(Environments environment, int level) {
        switch (environment) {
            case MAGIC_CLIFF -> {return new MagicCliff(this, level);}
            case HAUNTED_FOREST -> {return new HauntedForest(this, level);}
            case GOTHIC_CEMETERY -> {return new GothicCemetery(this, level);}
            default -> throw new IllegalArgumentException(Console.exceptionMessage("Invalid environment: " + environment));
        }
    }
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
     * Gets the current game level child of {@link LevelFrame}.
     *
     * @return the current level
     */
    public LevelFrame getLevel() {
        return environment;
    }
    /**
     * Uses an item from the player's inventory at the specified index.<br>
     * index corresponds to the item's visual position in the inventory. starting from the left.
     *
     * @param index the index of the item to use
     */
    public void useInventoryItem(int index) {
        playerInventory.use(index);
    }
    /**
     * Drops an {@link game.body.items.InventoryItem InventoryItem} from the player's inventory at the specified index.
     *
     * @param index the index of the item to drop
     */
    public void dropInventoryItem(int index) {
        playerInventory.drop(index);
    }
    //Mobology
    /**
     * Gets the list of alive {@link MobWalker MobWalkers}.
     *
     * @return the list of living Mobs
     */
    public static ArrayList<MobWalker> getMobs() {
        ArrayList<MobWalker> aliveMobs = new ArrayList<>();
        for (MobWalker mob : mobs) {
            if(!mob.isDead()) aliveMobs.add(mob);
        }
        return aliveMobs;
    }
    /**
     * Adds a mob to the local mobs list
     * @param mob the mob to add
     * @see MobWalker
     */
    public static void addMob(MobWalker mob) {
        mobs.add(mob);
    }
    /**
     * Removes a mob from the game world.<br>
     *
     * @param mob the mob to remove
     * @deprecated as mobs are ignored by the new {@link #getMobs()} when they die.
     */
    @Deprecated
    public static void removeMob(MobWalker mob) {
        mobs.remove(mob);
    }
    /**
     * Finds a {@link WormWalker} by name.
     *
     * @param name the name of the {@link WormWalker}
     * @return the {@link WormWalker} with the specified name, or null if not found
     * @deprecated due to {@link MobWalker#getWalkerType()}
     * and the move to the use of 'instanceof' to check for types.
     */
    @Deprecated
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
     * @deprecated due to {@link MobWalker#getWalkerType()}
     * and the move to the use of 'instanceof' to check for types.
     */
    @Deprecated
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
     * Replaces bodies where pos is inside it's bounds, as to allow for drawing images at a lower z-index.
     * @param pos the position to check against
     */
    public void rePlaceBodies(Vec2 pos) {
        for (StaticBody body : getStaticBodies()) {
            if (body instanceof HauntedBackdrop backdrop) {
                if (backdrop.getPos().x + backdrop.getHalfDimensions().x > pos.x && backdrop.getPos().x - backdrop.getHalfDimensions().x < pos.x) {
                    if (backdrop.getPos().y + backdrop.getHalfDimensions().y > pos.y && backdrop.getPos().y - backdrop.getHalfDimensions().y < pos.y) {
                        backdrop.duplicate();
                        backdrop.destroy();
                    }
                }
            }
        }
    }
    /**
     * Toggles the state of all mobs to passive.
     * This is only needed during development
     */
    public void toggleMobsPassive() { // only meant to be called during setup
        toggleMobsPassive = !toggleMobsPassive;
        for (MobWalker mob : mobs) {
            mob.setBehaviour(WalkerBehaviour.PASSIVE);
        }
    }
    /**
     * gets the local stored instance of {@link Game}
     * @return the game
     */
    public Game getGame() {
        return game;
    }
    /**
     * Gets the player's inventory.
     * @return the player's inventory
     * @see Inventory
     */
    public Inventory getPlayerInventory() {
        return playerInventory;
    }
}
