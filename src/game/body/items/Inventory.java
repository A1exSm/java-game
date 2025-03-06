package game.body.items;
// Imports
import game.Game;
import game.enums.items.ItemBehaviour;
import org.jbox2d.common.Vec2;

import java.util.ArrayList;

// Class
public class Inventory {
    // Fields
    private final int CAPACITY;
    private final ArrayList<InventoryItem> storage;
    // Constructor
    public Inventory(int capacity) {
        if (capacity % 2 != 0) {
            System.err.println("Error: Capacity must be an even number! Setting capacity to 4");
            CAPACITY = 4;
        } else {
            CAPACITY = capacity;
        }
        storage = new ArrayList<>();
        for (int i = 0; i < CAPACITY; i++) {
            storage.add(null);
        }
    }
    // Methods
    public boolean addItem(ItemFrame item) {
        for (InventoryItem inventoryItem : storage) {
            if (inventoryItem == null) {continue;} // skip null values
            if (inventoryItem.name().equals(item.getName())) {
                return false;
            }
        }
        for (int i = 0; i < CAPACITY; i++) {
            if (storage.get(i) == null) {
                storage.set(i, new InventoryItem(item, item.behaviour, item.getName()));
                return true;

            }
        }
        return false;
    }

    public void removeItem(ItemFrame item) {
        for (int i = 0; i < CAPACITY; i++) {
            if (storage.get(i) != null) {
                if (storage.get(i).name().equals(item.getName())) {
                    storage.set(i, null);
                    return;
                }
            }
        }
    }

    public int getCapacity() {
        return CAPACITY;
    }

    public int getSize() {
        int count = 0;
        for (InventoryItem inventoryItem : storage) {
            if (inventoryItem != null) {
                count++;
            }
        }
        return count;
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

    public void drop(int index) {
        if (storage.get(index) != null) {
            Vec2 playerPos = Game.gameWorld.getPlayer().getPosition();
            Vec2 dropPos = new Vec2(playerPos.x, playerPos.y -  (Game.gameWorld.getPlayer().HALF_Y/1.7f));
            storage.get(index).item().drop(dropPos);
            storage.set(index, null);
        }
    }

    public void use(int index) {
        if (storage.get(index) != null) {
            if(storage.get(index).behaviour() == ItemBehaviour.CONSUMABLE) {
                storage.get(index).item().consume();
            } else {
                storage.get(index).item().use();
            }
        }
    }
}
