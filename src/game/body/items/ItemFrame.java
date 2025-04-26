package game.body.items;
// Imports
import city.cs.engine.*;
import city.cs.engine.Shape;
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
 * Abstract class for all item bodies in the game.
 * This class implements the itemInterface and extends the StaticBody class.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 02-03-2025
 * @see game.body.items.itemInterface
 * @see city.cs.engine.StaticBody
 */
public abstract class ItemFrame extends StaticBody implements itemInterface {
    // Fields
    private final ItemBehaviour behaviour; // can be public since it is final
    private final GameWorld gameWorld;
    private final Timer dropTimer;
    private final ItemSize itemSize;
    private Sensor itemSensor;
    private static int itemCount = -1;
    private boolean inInventory = false;
    // Constructor
    /**
     * Constructs an ItemBody object.
     * @param shape is of the <i>Shape</i> parent class, meaning that the item supports being a polygon, square or circle.
     * @param itemType the item type of the ItemBody enums, allows for potential static references of future item classes with only the ItemBody accessible.
     * @param size the size of the item
     * @param behaviour the behaviour of the item
     * @param gameWorld the game world to add the item to
     * @see game.enums.items.ItemType
     * @see game.enums.items.ItemSize
     * @see game.enums.items.ItemBehaviour
     * @see game.core.GameWorld
     */
    public ItemFrame(Shape shape, ItemType itemType, ItemSize size, ItemBehaviour behaviour, GameWorld gameWorld) {
        super(gameWorld);
        this.gameWorld = gameWorld;
        itemSize = size;
        setImage();
        itemCount++;
        setName(itemType.name()+itemCount);
        addFixtures(shape);
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
    /**
     * Gets the item behaviour
     * @return the item behaviour
     * @see ItemBehaviour
     */
    public ItemBehaviour getBehaviour() {
        return behaviour;
    }

    /**
     * Adds a ghostly fixture to the item body
     * using the given shape.
     * @param shape the shape to add
     * @see GhostlyFixture
     * @see Fixture
     * @see Shape
     */
    private void addFixtures(Shape shape) {
        new GhostlyFixture(this, shape);
    }
    /**
     * Sets the image of the item body.
     * This method is called in the constructor to set the image of the item body.<br>
     * It uses the getImagePath() method to get the image path of the item body.
     * @see BodyImage
     */
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
    /**
     * Sets the boolean value for whether the item is in the inventory or not.<br>
     * {@code true} if the item is in the inventory, {@code false} otherwise.
     * @param inInventory the boolean value to set
     */
    public void setInInventory(boolean inInventory) {
        if(this.inInventory == inInventory) {
            Console.warning("ItemFrame.setInInventory() called with the same value as current!, returning.");
            return;
        }
        this.inInventory = inInventory;
    }
    /**
     * {@inheritDoc itemInterface#use()}
     */
    @Override
    public abstract void use();
    /**
     * {@inheritDoc itemInterface#getImgPath(ItemSize)}
     */
    @Override
    public abstract String getImgPath(ItemSize size);
    /**
     * {@inheritDoc itemInterface#drop(Vec2)}
     */
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
     * {@inheritDoc itemInterface#pickUp(Inventory)}
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
    /**
     * {@inheritDoc itemInterface#hide()}
     */
    @Override
    public void hide() {
        removeAllImages();
        removeSensor();
    }
    /**
     * {@inheritDoc itemInterface#destroyItem()}
     * @throws NullPointerException if an attempt to remove an item from the inventory is made when the item is not in the inventory.
     */
    @Override
    public void destroyItem() {
        if (inInventory) {
            gameWorld.getPlayerInventory().removeItem(this);
            setInInventory(false);
        }
        this.destroy();
    }
    /**
     * {@inheritDoc itemInterface#setItem(ItemSize)}
     */
    @Override
    public abstract int setItem(ItemSize size);
    /**
     * {@inheritDoc itemInterface#addSensor(Shape)}
     */
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
    /**
     * {@inheritDoc itemInterface#removeSensor()}
     */
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
