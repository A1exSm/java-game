package game.body.items.dropTable;
import game.enums.items.ItemSize;
/**
 * Represents an entry in the item drop table, containing the item size and its drop chance.
 * Each entry has a specific size and drop chance.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 23-04-2025
 */
record SizeDropEntry(ItemSize itemSize, int dropChance) {}
