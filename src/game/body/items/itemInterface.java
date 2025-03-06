package game.body.items;

import city.cs.engine.Shape;
import game.enums.items.ItemSize;
import org.jbox2d.common.Vec2;

interface itemInterface {
    void use();
    void drop(Vec2 dropLocation);
    void pickUp(Inventory inventory);
    void hide();
    void destroyItem();
    void setItem(ItemSize size);
    void addSensor(Shape shape);
    void removeSensor();

}
