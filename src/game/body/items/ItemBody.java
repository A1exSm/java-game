package game.body.items;
// Imports

import city.cs.engine.*;
import game.Game;
import game.GameWorld;
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
    private boolean onSurface = false;
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
    public ItemBody(Shape shape, Items itemType, ItemSize itemSize, Vec2 position) {
        super(Game.gameWorld);
        this.itemType = itemType;
        chooseImage();
        itemCount++;
        this.itemSize = itemSize;
        setName(itemType.name()+itemCount);
        addFixtures(shape);
        setPosition(position);
        setGravityScale(0);
    }
    // Methods
    /**
     * Returns the item type of the item body.
     * @return the item type of the item body.
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

    private void chooseImage() {
        switch (itemType) {
            case VIAL -> addImage(new BodyImage("data/Items/Vials/Small Vial.gif", 2));
            case TONIC -> addImage(new BodyImage("data/Items/tonic.png", 2));
            case JAR -> addImage(new BodyImage("data/Items/jar.png", 2));
        }
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
