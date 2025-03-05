package game.body.items;
// Imports

import game.enums.items.ItemBehaviour;
import game.enums.items.Items;
import javax.swing.*;
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
    public void addItem(ItemBody item) {
        for (InventoryItem inventoryItem : storage) {
            if (inventoryItem == null) {continue;}
            if (inventoryItem.name().equals(item.getName())) {
                System.err.println("Item already in inventory! Cannot add item");
                return;
            }
        }
        for (int i = 0; i < CAPACITY; i++) {
            if (storage.get(i) == null) {
                storage.set(i, new InventoryItem(item, item.getItemType(), item.getItemSize(),item.behaviour, item.getName()));
                return;

            }
        }
        System.err.println("Inventory is full! Cannot add item");
    }

    public void removeItem(ItemBody item) {
        for (int i = 0; i < CAPACITY; i++) {
            if (storage.get(i) != null) {
                if (storage.get(i).name().equals(item.getName())) {
                    storage.set(i, null);
                    return;
                }
            }
        }
    }

    public HealthVial getHealthVial(String name) {
        for (InventoryItem inventoryItem : storage) {
            if (inventoryItem.itemType() == Items.VIAL) {
                if (inventoryItem.item().getName().equals(name)) {
                    return (HealthVial) inventoryItem.item();
                }
            }
        }
        System.err.println("Item not found in inventory! Returning null");
        return null;
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
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i) != null) {
                path.add(storage.get(i).item().getImagePath());
            } else {
                path.add(null);
            }
        }
        return path;
    }

    /**
     * Returns the inventory names for all inventoryItems with a buffer zone.
     * @param buffer the buffer zone of null values before names are added.
     * @return inventory names for all inventoryItems.
     */
    public ArrayList<String> getInventoryNames(int buffer) {
        ArrayList<String> inventoryNames = new ArrayList<>();
        // adding buffer zone
        for (int i = 0; i < buffer-1; i++) {inventoryNames.add(null);}
        // adding names
        for (InventoryItem item : storage) {
            if (item != null) {
                inventoryNames.add(item.name());
            } else {
                inventoryNames.add(null);
            }
        }
        return inventoryNames;
    }

    public void use(int index) {
        if (storage.get(index) != null) {
            if(storage.get(index).behaviour() == ItemBehaviour.CONSUMABLE) {
                storage.get(index).item().consume();
            }
        }
    }
}
