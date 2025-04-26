package game.body.items;

import city.cs.engine.Shape;
import game.enums.items.ItemSize;
import org.jbox2d.common.Vec2;
/**
 * The itemInterface interface defines the methods that an item must implement.
 * It is used to create a consistent API for all items in the game.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 05-03-2025
 */
interface itemInterface {
    /**
     * Returns the image path of the item body.
     * @param size the size of the item.
     * @return String.
     */
    String getImgPath(ItemSize size);
    /**
     * Attempts to use the item.
     * If successful, {@link #destroyItem()} is called.
     * Returns if unsuccessful.
     */
    void use();
    /**
     * Removes the item from the inventory
     * and drops it at the specified location.
     * @param dropLocation the location to drop the item.
     */
    void drop(Vec2 dropLocation);
    /**
     * Picks up the item and adds it to the inventory.
     * @param inventory the inventory to add the item to.
     * @return true if the item was picked up, false otherwise.
     */
    boolean pickUp(Inventory inventory);
    /**
     * Removes all images and visible sensors from the item.
     */
    void hide();
    /**
     * Destroys the item body.<br>
     * If the item is in the inventory, it is removed from the inventory first.
     */
    void destroyItem();
    /**
     * Sets the item properties and usage based on the size.
     * @param size the size of the item.
     * @return int the status value of the item.
     */
    int setItem(ItemSize size);
    /**
     * Creates a sensor for the item,
     * and its appropriate sensor listeners
     * @param shape the shape to add as a sensor.
     */
    void addSensor(Shape shape);
    /**
     * Removes the sensor and sensorListeners from the item.
     */
    void removeSensor();

}
