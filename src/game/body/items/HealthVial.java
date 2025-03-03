package game.body.items;
// Imports

import city.cs.engine.*;
import game.Game;
import game.body.walkers.PlayerWalker;
import game.enums.items.ItemSize;
import game.enums.items.Items;
import org.jbox2d.common.Vec2;

// Class
/**
 * HealthVial class is a subclass of the ItemBody class and implements the ItemFrame interface.
 * This class is used to create a health vial item that can be picked up by the player to increase their health points.
 */
public class HealthVial extends ItemBody implements ItemFrame {
    // Fields
    public static final float HALF_X = 1;
    public static final float HALF_Y = 1;
    private int potionStrength;
    // Constructor
    /**
     * Constructs an ItemBody object.
     *
     * @param size    the size of the item.
     * @param position the position the item is set in the game world.
     */
    public HealthVial(ItemSize size, Vec2 position) {
        super(new BoxShape(1,1), Items.VIAL, position);
        addSensor(new BoxShape(1,1));
        setItem(size);
    }
    // Methods
    @Override
    public void use() {
        Game.gameWorld.getPlayer().addHealthPoints(potionStrength);
        this.destroyItem();
    }

    @Override
    public void drop() {

    }

    @Override
    public void pickUp() {

    }

    @Override
    public void setItem(ItemSize size) {
        switch (size) {
            case SMALL -> potionStrength = 125;
            case BIG -> potionStrength = 250;
            case LARGE -> potionStrength = 500;
        }
    }

    @Override
    public void getItem() {

    }

    @Override
    public void setItemFrame() {

    }

    @Override
    public void getItemFrame() {

    }

    @Override
    public void addSensor(Shape shape) {
        Sensor itemSensor = new Sensor(this, shape);
        itemSensor.addSensorListener(new SensorListener() {
            @Override
            public void beginContact(SensorEvent e) {
                if (e.getContactBody() instanceof PlayerWalker) {
                    if (!Game.gameWorld.getPlayer().isHealthFull()) use();
                }
            }

            @Override
            public void endContact(SensorEvent e) {}
        });
    }

}
