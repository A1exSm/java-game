package game.body.items.dropTable;

import city.cs.engine.StaticBody;
import game.body.items.HealthPotion;
import game.body.items.StrengthPotion;
import game.body.staticstructs.ground.hauntedForest.HauntedBackdrop;
import game.core.GameWorld;
import game.enums.items.ItemSize;
import game.enums.items.ItemType;
import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DropTable {
    // Fields
    private static final Map<ItemDropEntry, Integer> dropTable = new HashMap<>();
    static {
        // creating entries
        newEntry(new ItemDropEntry(ItemType.HEALTH_POTION, new ArrayList<>()), 4);
        newEntry(new ItemDropEntry(ItemType.STRENGTH_POTION, new ArrayList<>()), 2);
        // HEALTH_POTION
        getDropEntry(ItemType.HEALTH_POTION).addSizeEntry(ItemSize.LARGE, 2);
        getDropEntry(ItemType.HEALTH_POTION).addSizeEntry(ItemSize.MEDIUM, 3);
        getDropEntry(ItemType.HEALTH_POTION).addSizeEntry(ItemSize.SMALL, 5);
        // STRENGTH_POTION
        getDropEntry(ItemType.STRENGTH_POTION).addSizeEntry(ItemSize.LARGE, 3);
        getDropEntry(ItemType.STRENGTH_POTION).addSizeEntry(ItemSize.SMALL, 7);
        validateEntries();
    }
    // Private | Static | Methods
    private static void newEntry(ItemDropEntry entry, int dropChance) {
        if (dropTable.containsKey(entry)) {
            throw new IllegalArgumentException("Drop entry " + entry.itemType() + " already exists in the drop table.");
        }
        dropTable.put(entry, dropChance);
        int cumulativeDropChance = 0;
        for (int value : dropTable.values()) {
            cumulativeDropChance += value;
            if (cumulativeDropChance > 10) { // 10 represents a 100% drop chance, which is the maximum
                throw new IllegalArgumentException("Cumulative drop chance exceeds 10 for entry " + entry.itemType());
            }
        }
    }

    private static ItemDropEntry getDropEntry(ItemType itemType) {
        for (Map.Entry<ItemDropEntry, Integer> entry : dropTable.entrySet()) {
            if (entry.getKey().itemType() == itemType) {
                return entry.getKey();
            }
        }
        throw new NullPointerException("No drop entry found for type " + itemType);
    }

    private static void validateEntries() {
        for (ItemDropEntry itemDropEntry  : dropTable.keySet()) {
            itemDropEntry.validate();
        }
    }

    // Public | Methods
    public void dropItem(GameWorld gameWorld, Vec2 position) {
        Random rand = new Random();
        ItemDropEntry entry = getRandomEntry(rand.nextInt(10));
        if (entry != null) {
            ItemSize size = getRandomSize(entry, rand.nextInt(10));
            spawnItem(gameWorld, position, entry.itemType(), size);

        }
    }
    // Private | Methods
    /**
     * Determines if an item should be dropped based on a random value,
     * and if so, which type of item.
     *
     * @param randInt A random integer between 0-99 representing percentage chance
     * @return The selected ItemDropEntry based on probability distribution,
     *         or null if no item is dropped (when randInt is higher than total drop chance)
     */
    private ItemDropEntry getRandomEntry(int randInt) {
        int pointer = 0;
        for (Map.Entry<ItemDropEntry, Integer> entry : dropTable.entrySet()) {
            pointer += entry.getValue();
            if (randInt < pointer) {
                return entry.getKey();
            }
        }
        return null;
    }

    private ItemSize getRandomSize(ItemDropEntry entry, int randInt) {
        int pointer = 0;
        for (SizeDropEntry sizeEntry : entry.sizeEntryArray()) {
            pointer += sizeEntry.dropChance();
            if (randInt < pointer) {
                return sizeEntry.itemSize();
            }
        }
        throw new IllegalStateException("No size found for random integer " + randInt + ", size should have added to 100!");
    }

    private void spawnItem(GameWorld gameWorld, Vec2 position, ItemType itemType, ItemSize size) {
        switch (itemType) {
            case HEALTH_POTION -> new HealthPotion(size, position,gameWorld);
            case STRENGTH_POTION -> new StrengthPotion(size, position, gameWorld);
            default -> throw new IllegalArgumentException("Unexpected value: " + itemType);
        }
       gameWorld.rePlaceBodies(position);
    }

}
