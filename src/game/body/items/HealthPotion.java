package game.body.items;
// Imports

import city.cs.engine.*;
import game.Game;
import game.core.GameWorld;
import game.core.console.Console;
import game.enums.items.ItemBehaviour;
import game.enums.items.ItemType;
import game.enums.items.ItemSize;
import game.enums.items.PotionType;
import org.jbox2d.common.Vec2;

// Class
/**
 * HealthVial class is a subclass of the ItemBody class and implements the ItemFrame interface.
 * This class is used to create a health vial item that can be picked up by the player to increase their health points.
 */
public class HealthPotion extends ItemFrame {
    // Fields
    private int potionStrength;
    // Constructor
    /**
     * Constructs an ItemBody object.
     *
     * @param size    the size of the item.
     * @param position the position the item is set in the game world.
     */
    public HealthPotion(ItemSize size, Vec2 position, GameWorld world) {
        super(new PolygonShape(-0.175f,0.9f, -0.579f,-0.9f, 0.565f,-0.99f, 0.165f,0.99f), ItemType.HEALTH_POTION, size, ItemBehaviour.CONSUMABLE, world);

        potionStrength = setItem(size);
        setPosition(position);
    }

    /**
     * Constructs an ItemBody object.
     *
     * @param size ItemSize
     */
    public HealthPotion(ItemSize size, GameWorld world) {
        super(new PolygonShape(-0.175f,0.9f, -0.579f,-0.9f, 0.565f,-0.99f, 0.165f,0.99f), ItemType.HEALTH_POTION, size, ItemBehaviour.CONSUMABLE, world);
        setItem(size);
    }
    @Override
    public String getImgPath(ItemSize size) {
        return String.format("data/Items/Potions/%s/redPotion.gif", size.name().toLowerCase());
    }


    // Methods | Public | @Override

    @Override
    public void use() {
        if (pickUp(GameWorld.playerInventory)) {return;}
        if (!Game.gameWorld.getPlayer().isHealthFull()) {
            Game.gameWorld.getPlayer().addHealthPoints(potionStrength);
            destroyItem();
        }
    }

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
