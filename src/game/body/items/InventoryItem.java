package game.body.items;

import game.enums.items.ItemSize;
import game.enums.items.Items;

public record InventoryItem(ItemBody item, Items itemType, ItemSize itemSize, String name) {
    // Fields

    //Constructor

    //Methods
    public HealthVial getItem() {
        if (itemType != Items.VIAL) {
            throw new IllegalArgumentException("Item is not a Health Vial");
        }
        return (HealthVial) item;
    }


}
