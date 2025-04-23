package game.body.items;
// Imports

import city.cs.engine.*;
import city.cs.engine.Shape;
import game.Game;
import game.body.walkers.PlayerWalker;
import game.core.GameWorld;
import game.core.console.Console;
import game.enums.items.ItemBehaviour;
import game.enums.items.ItemType;
import game.enums.items.ItemSize;
import org.jbox2d.common.Vec2;
import javax.swing.*;
import java.awt.*;

// Class
/**
 * The ItemBody class is a dynamic body that represents an item in the game world.
 * It is Dynamic, as to allow for the Item to <i>drop</i>.
 */
public abstract class ItemFrame extends DynamicBody implements itemInterface {
    // Fields
    public final ItemBehaviour behaviour; // can be public since it is final
    private final GameWorld gameWorld;
    private final Timer dropTimer;
    private final ItemType itemType;
    private final ItemSize itemSize;
//    private boolean onSurface = false;
    private Sensor itemSensor;
    private static int itemCount = -1;
    private boolean inInventory = false;
    // Constructor
    /**
     * Constructs an ItemBody object.
     * @param shape is of the <i>Shape</i> parent class, meaning that the item supports being a polygon, square or circle.
     * @param itemType the item type of the ItemBody enums, allows for potential static references of future item classes with only the ItemBody accessible.
     */
    public ItemFrame(Shape shape, ItemType itemType, ItemSize size, ItemBehaviour behaviour, GameWorld gameWorld) {
        super(gameWorld);
        this.gameWorld = gameWorld;
        itemSize = size;
        setImage();
        this.itemType = itemType;
        itemCount++;
        setName(itemType.name()+itemCount);
        addFixtures(shape);
        setGravityScale(0);
        this.behaviour = behaviour;
        addSensor(shape);
        dropTimer = new Timer(2000, e -> addSensor(shape));
        dropTimer.setRepeats(false);
        Color transparent = new Color(0,0,0,0);
        setFillColor(transparent);
        setLineColor(transparent);
    }
    // Methods
    /**
     * gets the assigned game world.
     * @return the game world.
     * @see GameWorld
     */
    public GameWorld getGameWorld() {
        return gameWorld;
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
        String path = getImgPath(itemSize);
        if (path != null) {
            return path;
        }
        Console.errorTrace("ItemType not found! returning my image of choice");
        return "data/Items/Potions/large/purplePotion.gif";
    }

    public void setInInventory(boolean inInventory) {
        if(this.inInventory == inInventory) {
            Console.warning("ItemFrame.setInInventory() called with the same value as current!, returning.");
            return;
        }
        this.inInventory = inInventory;
    }


    @Override
    public abstract void use();

    @Override
    public abstract String getImgPath(ItemSize size);

    @Override
    public void drop(Vec2 dropLocation) {
        if (!dropTimer.isRunning()) {
            dropTimer.start();
            setInInventory(false);
            setPosition(dropLocation);
            setImage();
        }
    }

    /**
     * Picks up the item and adds it to the inventory.
     * @param inventory the inventory to add the item to.
     * @return true if the item was picked up, false otherwise.
     */
    @Override
    public boolean pickUp(Inventory inventory) {
        if (!inInventory) {
            if (inventory.addItem(this)) {
                setInInventory(true);
                hide();
                return true;
            }
        }
        return false;
    }

    @Override
    public void hide() {
        removeAllImages();
        removeSensor();
    }

    /**
     * Destroys the item body.
     */
    @Override
    public void destroyItem() {
        if (inInventory) {
            GameWorld.playerInventory.removeItem(this);
            setInInventory(false);
        }
        this.destroy();
    }

    @Override
    public abstract int setItem(ItemSize size);

    @Override
    public void addSensor(Shape shape) {
        // Checking for existing sensor
        if (itemSensor != null) {
            Console.warning("ItemFrame.addSensor() called on an item with an existing sensor! Returning.");
            return;
        }
        // Main logic
        itemSensor = new Sensor(this, shape);
        itemSensor.addSensorListener(new SensorListener() {
            @Override
            public void beginContact(SensorEvent e) {
                if (e.getContactBody() instanceof PlayerWalker) {
                    use();
                }
            }

            @Override
            public void endContact(SensorEvent e) {}
        });
    }

    @Override
    public void removeSensor() {
        // Checking for null sensor
        try {
            itemSensor.removeAllSensorListeners();
        } catch (NullPointerException e) {
            Console.warning("ItemFrame.removeSensor() called on an item with no sensor! Returning.");
            return;
        }
        // Main logic
        itemSensor.destroy();
        itemSensor = null;
    }
}
