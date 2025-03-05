package game.body.items;
// Imports

import city.cs.engine.*;
import game.Game;
import game.GameWorld;
import game.enums.items.ItemBehaviour;
import game.enums.items.ItemSize;
import game.enums.items.Items;
import org.jbox2d.common.Vec2;

// Class
/**
 * The ItemBody class is a dynamic body that represents an item in the game world.
 * It is Dynamic, as to allow for the Item to <i>drop</i>.
 */
public class ItemBody extends DynamicBody {
    // Fields
    private final Items itemType;
    public final ItemBehaviour behaviour; // can be public since it is final
//    private boolean onSurface = false;
    private static int itemCount = -1;
    private boolean destroyed = false;
    private final ItemSize itemSize;
    private boolean inInventory = false;
    // Constructor
    /**
     * Constructs an ItemBody object.
     * @param shape is of the <i>Shape</i> parent class, meaning that the item supports being a polygon, square or circle.
     * @param itemType the item type of the ItemBody enums, allows for potential static references of future item classes with only the ItemBody accessible.
     * @param position the position the item is set in the game world.
     */
    public ItemBody(Shape shape, Items itemType, ItemSize itemSize, Vec2 position, ItemBehaviour behaviour) {
        super(Game.gameWorld);
        this.itemType = itemType;
        setImage();
        itemCount++;
        this.itemSize = itemSize;
        setName(itemType.name()+itemCount);
        addFixtures(shape);
        setPosition(position);
        setGravityScale(0);
        this.behaviour = behaviour;
    }
    // Methods
    public void consume() {}

    /**
     * Returns the item type of the item body.
     * @return Items (itemType)
     */
    public Items getItemType() {
        return itemType;
    }

    public ItemSize getItemSize() {
        return itemSize;
    }

    private void addFixtures(Shape shape) {
        new GhostlyFixture(this, shape);
    }

    private void setImage() {
        addImage(new BodyImage(getImagePath(), 2));
    }
    /**
     * Returns the image path of the item body.
     * @return String.
     */
    public String getImagePath() {
        switch (itemType) {
            case VIAL -> {
                return "data/Items/Vials/Small Vial.gif";
            }
            case TONIC -> {
                return "data/Items/tonic.png";
            }
            case JAR -> {
                return "data/Items/jar.png";
            }
        }
        System.err.println("Error: ItemType not found! returning empty jar image");
        return "data/Items/emptyJar.gif";
    }

    /**
     * Returns the boolean true, if the item is destroyed, and false if not.
     * @return boolean
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Destroys the item body.
     */
    public void destroyItem() {
        destroyed = true;
        if (inInventory) {
            GameWorld.playerInventory.removeItem(this);
        }
        this.destroy();
    }

    public boolean isInInventory() {
        return inInventory;
    }

    public void setInInventory(boolean inInventory) {
        this.inInventory = inInventory;
    }

}
