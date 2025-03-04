package game.body.items;
// Imports

import game.enums.items.Items;

import java.security.DrbgParameters;
import java.util.ArrayList;

// Class
public class Inventory {
    // Fields
    private final int CAPACITY;
    private final ArrayList<InventoryItem> storage = new ArrayList<>();
    // Constructor
    public Inventory(int capacity) {
        if (capacity % 2 != 0) {
            System.err.println("Error: Capacity must be an even number! Setting capacity to 4");
            CAPACITY = 4;
        } else {
            CAPACITY = capacity;
        }
    }
    // Methods
    public void addItem(ItemBody item) {
        if (storage.size() >= CAPACITY) {
            System.err.println("Inventory is full! Cannot add item");
        } else {
            for (InventoryItem inventoryItem : storage) {
                if (inventoryItem.name().equals(item.getName())) {
                    System.err.println("Item already in inventory! Cannot add item");
                    return;
                }
            }
        }
        storage.add(new InventoryItem(item, item.getItemType(), item.getItemSize(), item.getName()));
    }

    public void removeItem(ItemBody item) {
        storage.removeIf(inventoryItem -> inventoryItem.getItem().equals(item));
    }

    public HealthVial getHealthVial(HealthVial item) {
        for (InventoryItem inventoryItem : storage) {
            if (inventoryItem.itemType() == Items.VIAL) {
                if (inventoryItem.getItem() == item) {
                    return inventoryItem.getItem();
                }
            }
        }
        System.err.println("Item not found in inventory! Returning null");
        return null;
    }

    public int getSize() {
        return storage.size();
    }


}
