package game.body.items;

import city.cs.engine.Shape;
import game.enums.items.ItemSize;
import org.jbox2d.common.Vec2;

interface itemInterface {
    String getImgPath(ItemSize size);
    void use();
    void drop(Vec2 dropLocation);
    boolean pickUp(Inventory inventory);
    void hide();
    void destroyItem();
    int setItem(ItemSize size);
    void addSensor(Shape shape);
    void removeSensor();

}
