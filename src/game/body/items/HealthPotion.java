package game.body.items;
// Imports
import city.cs.engine.*;
import game.Game;
import game.core.GameWorld;
import game.core.console.Console;
import game.enums.items.ItemBehaviour;
import game.enums.items.ItemType;
import game.enums.items.ItemSize;
import org.jbox2d.common.Vec2;
// Class
/**
 * HealthVial class is a subclass of the ItemBody class.
 * This class is used to create a health vial item that can be picked up by the player to increase their health points.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 06-03-2025
 * @see ItemFrame
 */
public final class HealthPotion extends ItemFrame {
    // Fields
    private int potionStrength;
    // Constructor
    /**
     * Constructs a potion with the given size and position, and adds it to the world.
     * @param size The size of the potion.
     * @param position The position of the potion.
     * @param world The game world to which the potion will be added.
     * @see ItemSize
     * @see GameWorld
     * @see Vec2
     */
    public HealthPotion(ItemSize size, Vec2 position, GameWorld world) {
        super(new PolygonShape(-0.175f,0.9f, -0.579f,-0.9f, 0.565f,-0.99f, 0.165f,0.99f), ItemType.HEALTH_POTION, size, ItemBehaviour.CONSUMABLE, world);
        potionStrength = setItem(size);
        setPosition(position);
    }
    /**
     * Constructs a potion with the given size and adds it to the world.
     * @param size The size of the potion.
     * @param world The game world to which the potion will be added.
     * @see ItemSize
     * @see GameWorld
     * @deprecated health potions no longer get moved after being created
     */
    @Deprecated
    public HealthPotion(ItemSize size, GameWorld world) {
        super(new PolygonShape(-0.175f,0.9f, -0.579f,-0.9f, 0.565f,-0.99f, 0.165f,0.99f), ItemType.HEALTH_POTION, size, ItemBehaviour.CONSUMABLE, world);
        setItem(size);
    }
    // Methods | Public | @Override
    /**
     * {@inheritDoc ItemFrame#getImgPath(ItemSize)}
     */
    @Override
    public String getImgPath(ItemSize size) {
        return String.format("data/Items/Potions/%s/redPotion.gif", size.name().toLowerCase());
    }
    /**
     * {@inheritDoc ItemFrame#use()}
     */
    @Override
    public void use() {
        if (pickUp(getGameWorld().getPlayerInventory())) {return;}
        if (!Game.gameWorld.getPlayer().isHealthFull()) {
            Game.gameWorld.getPlayer().addHealthPoints(potionStrength);
            destroyItem();
        }
    }
    /**
     * {@inheritDoc ItemFrame#setItem(ItemSize)}
     */
    @Override
    public int setItem(ItemSize size) {
        switch (size) {
            case LARGE -> {return 500;}
            case MEDIUM -> {return 250;}
            case SMALL -> {return 125;}
            default -> {
                Console.warning("Unsupported ItemSize " + size + " for " + getName() + ", Destroying item!");
                destroyItem();
                return -1;
            }
        }
    }
}
