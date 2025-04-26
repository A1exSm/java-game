package game.body.items;

import game.enums.items.ItemBehaviour;
/**
 * InventoryItem class represents an item in the inventory.
 * It contains the item frame, its behaviour, and its name.
 * This class is used to store items in the inventory.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 04-03-2025
 */
public record InventoryItem(ItemFrame item, ItemBehaviour behaviour, String name) {}
