package game.body.items;

import city.cs.engine.Shape;

public interface ItemFrame {
    void use();
    void drop();
    void pickUp();
    void destroy();
    void setItem();
    void getItem();
    void setItemFrame();
    void getItemFrame();
    void addSensor(Shape shape);
}
