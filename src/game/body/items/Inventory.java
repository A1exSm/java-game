package game.body.items;
// Imports
import game.Game;
import game.body.walkers.PlayerWalker;
import game.core.console.Console;
import org.jbox2d.common.Vec2;
import java.util.ArrayList;
import java.util.List;
// Class
/**
 * Inventory class that stores items in a list.
 * The inventory has a fixed capacity and can add, remove, and use items.
 * The inventory maintains the order and position of items.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 04-03-2025
 */
public final class Inventory {
    // Fields
    private final int CAPACITY;
    private final List<InventoryItem> storage;
    // Constructor
    /**
     * Constructor for the Inventory class.
     * @param capacity the capacity of the inventory.
     * @throws IllegalArgumentException if the capacity is not an even number.
     */
    public Inventory(int capacity) {
        if (capacity % 2 != 0) {
            throw new IllegalArgumentException(Console.exceptionMessage("Capacity: " + capacity + " is not even, capacity must be an even number!"));
        }
        CAPACITY = capacity;
        storage = new ArrayList<>();
        for (int i = 0; i < CAPACITY; i++) {
            storage.add(null);
        }
    }
    // Methods
    /**
     * Adds an item to the inventory.
     * @param item the item to add.
     * @return {@code true} if the item was added, {@code false} if the item already exists in the inventory.
     */
    public boolean addItem(ItemFrame item) {
        for (InventoryItem inventoryItem : storage) {
            if (inventoryItem == null) {continue;} // skip null values
            if (inventoryItem.name().equals(item.getName())) {
                return false;
            }
        }
        for (int i = 0; i < CAPACITY; i++) {
            if (storage.get(i) == null) {
                storage.set(i, new InventoryItem(item, item.getBehaviour(), item.getName()));
                return true;
            }
        }
        return false;
    }
    /**
     * Removes an item from the inventory.
     * @param item the item to remove.
     * @throws NullPointerException if the item is not found in the inventory.
     */
    public void removeItem(ItemFrame item) {
        for (int i = 0; i < CAPACITY; i++) {
            if (storage.get(i) != null) {
                if (storage.get(i).name().equals(item.getName())) {
                    storage.set(i, null);
                    return;
                }
            }
        }
        throw new NullPointerException(Console.exceptionMessage("No such item :" + item.getName() + " in inventory!"));
    }
    /**
     * Returns the inventory image path for all inventoryItems with a buffer zone.
     * @param buffer the buffer zone of null values before Icons are added.
     * @return inventory image path for all inventoryItems.
     */
    public ArrayList<String> getInventoryPath(int buffer) {
        ArrayList<String> path = new ArrayList<>();
        // adding buffer zone
        for (int i = 0; i < buffer; i++) {path.add(null);}
        // adding icons
        for (InventoryItem inventoryItem : storage) {
            if (inventoryItem != null) {
                path.add(inventoryItem.item().getImagePath());
            } else {
                path.add(null);
            }
        }
        return path;
    }

    /**
     * Drops the item from the inventory at the specified index.
     * @param index the index of the item to drop.
     */
    public void drop(int index) {
        if (storage.get(index) != null) {
            Vec2 playerPos = Game.gameWorld.getPlayer().getPosition();
            Vec2 dropPos = new Vec2(playerPos.x, playerPos.y -  (PlayerWalker.HALF_Y/1.7f));
            storage.get(index).item().drop(dropPos);
            storage.set(index, null);
        }
    }
    /**
     * Uses the item from the inventory at the specified index
     * by calling {@link ItemFrame#use()} as per its API.
     * @param index the index of the item to use.
     */
    public void use(int index) {
        if (storage.get(index) != null) {
            storage.get(index).item().use();
        }
    }
}
