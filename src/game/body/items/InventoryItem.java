package game.body.items;

import game.enums.items.ItemBehaviour;

public record InventoryItem(ItemFrame item, ItemBehaviour behaviour, String name) {
    /*
    Question | Why am I using a Record?
    Answer | 1:
    The original choice was to use a HashMap, however, hashmaps are not ordered, and I needed to keep the position of items in an inventory.
    The next choice was to use a record, which is a class that is immutable and has a fixed number of fields. This was a much more secure Data type.
    Answer | 2:
    I have never used Records, thus I wanted to learn about them. What's a better way to learn than to use them in a project?
     */
}
