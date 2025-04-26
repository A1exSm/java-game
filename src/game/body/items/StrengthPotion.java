package game.body.items;
// Imports
import city.cs.engine.PolygonShape;
import game.body.walkers.PlayerWalker;
import game.core.GameWorld;
import game.core.console.Console;
import game.enums.items.ItemBehaviour;
import game.enums.items.ItemSize;
import game.enums.items.ItemType;
import org.jbox2d.common.Vec2;
// Class
/**
 * StrengthPotion class represents a potion that increases the player's strength.
 * It extends the ItemFrame class and can be used by the player or stored in the inventory.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 23-04-2025
 */
public class StrengthPotion extends ItemFrame {
    // Fields
    private final int potionStrength;
    // Constructor
    /**
     * Constructor for the StrengthPotion class.
     * @param size The size of the potion ({@link ItemSize#LARGE LARGE} or {@link ItemSize#SMALL SMALL}).
     * @param position The position of the potion in the game world.
     * @param world The game world where the potion exists.
     */
    public StrengthPotion(ItemSize size, Vec2 position, GameWorld world) {
        super(new PolygonShape(-0.175f, 0.9f, -0.579f, -0.9f, 0.565f, -0.99f, 0.165f, 0.99f), ItemType.STRENGTH_POTION, size, ItemBehaviour.CONSUMABLE, world);
        potionStrength = setItem(size);
        setPosition(position);
    }
    // Methods | Override
    /**
     * {@inheritDoc ItemFrame#getImagePath(ItemSize)}
     */
    @Override
    public String getImgPath(ItemSize size) {
        return String.format("data/Items/Potions/%s/purplePotion.gif", size.name().toLowerCase());
    }
    /**
     * {@inheritDoc ItemFrame#use())}
     */
    @Override
    public void use() {
        if (pickUp(getGameWorld().getPlayerInventory())) {
            return;
        }
        if (getGameWorld().getPlayer().getDamage() == 350) {
            getGameWorld().getPlayer().addStrength(potionStrength);
            javax.swing.Timer timer = new javax.swing.Timer(20000, e -> getGameWorld().getPlayer().removeStrength(potionStrength));
            timer.setRepeats(false);
            timer.start();
            destroyItem();
        }
    }
    /**
     * {@inheritDoc ItemFrame#setItem(ItemSize)}
     */
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
