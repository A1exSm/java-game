package game.body.items;

import city.cs.engine.Shape;
import game.enums.items.ItemSize;

public interface ItemFrame {
    void use();
    void drop();
    void pickUp();
    void destroy();
    void setItem(ItemSize size);
    void getItem();
    void setItemFrame();
    void getItemFrame();
    void addSensor(Shape shape);
}
