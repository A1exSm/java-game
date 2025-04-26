package game.body.items.dropTable;
import game.core.console.Console;
import game.enums.items.ItemSize;
import game.enums.items.ItemType;
import java.util.List;
/**
 * Represents an entry in the item drop table, containing the item type and its size drop entries.
 * Each entry has a list of size drop entries, each with a specific size and drop chance.
 * The cumulative drop chance of all size entries must equal 10 (100%).
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 23-04-2025
 */
public record ItemDropEntry(ItemType itemType, List<SizeDropEntry> sizeEntryArray) {
    /**
     * Adds a new size entry to the item drop entry {@link #sizeEntryArray}.
     * @param size the size of the item.
     * @param dropChance the drop chance of the item. (must be between 0 and 10, inclusive)
     * @throws IllegalArgumentException if the size entry already exists, or if the cumulative drop chance exceeds 10.
     */
    public void addSizeEntry(ItemSize size, int dropChance) {
        checkLimit(size, dropChance);
        checkExistence(size);
        sizeEntryArray.add(new SizeDropEntry(size, dropChance));
    }

    /**
     * Checks whether the cumulative drop chance of the sizeEntryArray is 10, as 10 represents 100%.<br>
     * Should only be called after all size entries have been added.
     * @throws IllegalStateException if the cumulative drop chance of {@link #sizeEntryArray} is not 10.
     */
    public void validate() {
        if (getCumulativeDropChance() < 10) {
            throw new IllegalStateException(Console.exceptionMessage(itemType + "'s sizeEntryArray does not add up to 10, with a cumulative drop chance of " + getCumulativeDropChance()));
        }
    }
    /**
     * Checks that the given {@link ItemSize} does not already exist in the {@link #sizeEntryArray}.
     * @param size the size to check for.
     * @throws IllegalArgumentException if the size already exists in the sizeEntryArray.
     */
    private void checkExistence(ItemSize size) {
        for (SizeDropEntry entry : sizeEntryArray) {
            if (entry.itemSize() == size) {
                throw new IllegalArgumentException(Console.exceptionMessage(itemType + "'s sizeEntryArray already contains itemSize " + size));
            }
        }
    }
    /**
     * Checks that the given drop chance does not exceed 10 if {@link #sizeEntryArray} is empty,
     * and that the cumulative drop chance of the sizeEntryArray does not exceed 10.
     * @param size the size to check for.
     * @param dropChance the drop chance to check for.
     * @throws IllegalArgumentException if the drop chance exceeds 10, or if the cumulative drop chance exceeds 10.
     */
    private void checkLimit(ItemSize size, int dropChance) {
        if (sizeEntryArray.isEmpty() && dropChance > 10) {
            throw new IllegalArgumentException(Console.exceptionMessage(itemType + "'s sizeEntryArray exceeds a cumulative drop chance of 10, with the addition of " + size + " with a drop chance of " + dropChance));
        }
        if (getCumulativeDropChance() + dropChance > 10) {
            throw new IllegalArgumentException(Console.exceptionMessage(itemType + "'s sizeEntryArray exceeds a cumulative drop chance of 10, with an original dropSize of " + getCumulativeDropChance() + " and the addition of " + size + " with a drop chance of " + dropChance));
        }
    }
    /**
     * Returns the cumulative drop chance of all size entries in the sizeEntryArray.
     * @return the cumulative drop chance.
     */
    private int getCumulativeDropChance() {
        int cumulativeDropChance = 0;
        for (SizeDropEntry entry : sizeEntryArray) {
            cumulativeDropChance += entry.dropChance();
        }
        return cumulativeDropChance;
    }
}
