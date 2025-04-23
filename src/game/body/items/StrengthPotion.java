package game.body.items;

import city.cs.engine.PolygonShape;
import game.Game;
import game.body.walkers.PlayerWalker;
import game.core.GameWorld;
import game.core.console.Console;
import game.enums.items.ItemBehaviour;
import game.enums.items.ItemSize;
import game.enums.items.ItemType;
import org.jbox2d.common.Vec2;

// Class
public class StrengthPotion extends ItemFrame {
    // Fields
    private final int potionStrength;

    // Constructor
    public StrengthPotion(ItemSize size, Vec2 position, GameWorld world) {
        super(new PolygonShape(-0.175f, 0.9f, -0.579f, -0.9f, 0.565f, -0.99f, 0.165f, 0.99f), ItemType.STRENGTH_POTION, size, ItemBehaviour.CONSUMABLE, world);
        potionStrength = setItem(size);
        setPosition(position);
    }
    // Methods | Protected | Static
    @Override
    public String getImgPath(ItemSize size) {
        return String.format("data/Items/Potions/%s/purplePotion.gif", size.name().toLowerCase());
    }


    // Methods | Override
    @Override
    public void use() {
        if (pickUp(GameWorld.playerInventory)) {
            return;
        }
        if (getGameWorld().getPlayer().getDamage() == 350) {
            getGameWorld().getPlayer().addStrength(potionStrength);
            javax.swing.Timer timer = new javax.swing.Timer(20000, e -> {
                getGameWorld().getPlayer().removeStrength(potionStrength);
            });
            timer.setRepeats(false);
            timer.start();
            destroyItem();
        }
    }

    @Override
    public int setItem(ItemSize size) {
        switch (size) {
            case LARGE -> {
                return PlayerWalker.DEFAULT_DAMAGE;
            }
            case SMALL -> {
                return (int) (PlayerWalker.DEFAULT_DAMAGE * 0.5f);
            }
            default -> {
                Console.warning("Unsupported ItemSize " + size + " for " + getName() + ", Destroying item!");
                destroyItem();
                return -1;
            }
        }
    }
}
