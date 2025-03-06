package game.body.items;
// Imports

import city.cs.engine.*;
import game.Game;
import game.core.GameWorld;
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
    private final PotionType potionType;
    private final ItemSize itemSize;
    // Constructor
    /**
     * Constructs an ItemBody object.
     *
     * @param size    the size of the item.
     * @param position the position the item is set in the game world.
     */
    public HealthPotion(ItemSize size, Vec2 position) {
        super(new PolygonShape(-0.175f,0.9f, -0.579f,-0.9f, 0.565f,-0.99f, 0.165f,0.99f), ItemType.HEALTH_POTION, size, ItemBehaviour.CONSUMABLE);
        this.itemSize = size;
        this.potionType = PotionType.VIAL;
        setItem(size);
        setPosition(position);
    }

    /**
     * Constructs an ItemBody object.
     *
     * @param size ItemSize
     */
    public HealthPotion(ItemSize size) {
        super(new PolygonShape(-0.175f,0.9f, -0.579f,-0.9f, 0.565f,-0.99f, 0.165f,0.99f), ItemType.HEALTH_POTION, size, ItemBehaviour.CONSUMABLE);
        this.itemSize = size;
        this.potionType = PotionType.VIAL;
        setItem(size);
    }
    // Methods | Protected | Static
    protected static String getImgPath(ItemSize size) {
        return String.format("data/Items/Potions/%s/redPotion.gif", size.name().toLowerCase());
    }


    // Methods | Public | @Override
    @Override
    public void consume() {
        use();
    }

    @Override
    public void use() {
        if (!isInInventory()) {
            pickUp(GameWorld.playerInventory);
        } else if (!Game.gameWorld.getPlayer().isHealthFull()) {
            Game.gameWorld.getPlayer().addHealthPoints(potionStrength);
            this.destroyItem();
        }
    }

    @Override
    public void setItem(ItemSize size) {
        switch (size) {
            case LARGE -> potionStrength = 500;
            case MEDIUM -> potionStrength = 250;
            case SMALL -> potionStrength = 125;
        }
    }
}
