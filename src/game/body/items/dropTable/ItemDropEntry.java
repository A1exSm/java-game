package game.body.items.dropTable;
import game.core.console.Console;
import game.enums.items.ItemSize;
import game.enums.items.ItemType;
import java.util.List;

public record ItemDropEntry(ItemType itemType, List<SizeDropEntry> sizeEntryArray) {
    public void addSizeEntry(ItemSize size, int dropChance) {
        checkLimit(size, dropChance);
        checkExistence(size);
        sizeEntryArray.add(new SizeDropEntry(size, dropChance));
    }

    /**
     * Checks whether the cumulative drop chance of the sizeEntryArray is 10, as 10 represents 100%.<br>
     * Should only be called after all size entries have been added.
     */
    public void validate() {
        if (getCumulativeDropChance() < 10) {
            throw new IllegalStateException(Console.exceptionMessage(itemType + "'s sizeEntryArray does not add up to 10, with a cumulative drop chance of " + getCumulativeDropChance()));
        }
    }

    private void checkExistence(ItemSize size) {
        for (SizeDropEntry entry : sizeEntryArray) {
            if (entry.itemSize() == size) {
                throw new IllegalArgumentException(Console.exceptionMessage(itemType + "'s sizeEntryArray already contains itemSize " + size));
            }
        }
    }

    private void checkLimit(ItemSize size, int dropChance) {
        if (sizeEntryArray.isEmpty() && dropChance > 10) {
            throw new IllegalArgumentException(Console.exceptionMessage(itemType + "'s sizeEntryArray exceeds a cumulative drop chance of 10, with the addition of " + size + " with a drop chance of " + dropChance));
        }
        if (getCumulativeDropChance() + dropChance > 10) {
            throw new IllegalArgumentException(Console.exceptionMessage(itemType + "'s sizeEntryArray exceeds a cumulative drop chance of 10, with an original dropSize of " + getCumulativeDropChance() + " and the addition of " + size + " with a drop chance of " + dropChance));
        }
    }

    private int getCumulativeDropChance() {
        int cumulativeDropChance = 0;
        for (SizeDropEntry entry : sizeEntryArray) {
            cumulativeDropChance += entry.dropChance();
        }
        return cumulativeDropChance;
    }
}
