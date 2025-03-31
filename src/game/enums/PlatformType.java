package game.enums;

import city.cs.engine.BodyImage;
import org.jbox2d.common.Vec2;

public enum PlatformType {
    CLIFF_DARK,
    CLIFF_LIGHT,
    GROUND;
    static {
        // CLIFF_DARK | Setup
        CLIFF_DARK.side = new BodyImage("data/Magic_Cliffs/cliff/dark_left.png", 15.5f);
        CLIFF_DARK.middle = new BodyImage("data/Magic_Cliffs/cliff/dark_middle.png", 15.5f);
        CLIFF_DARK.sideWidth = 4.8f;
        CLIFF_DARK.middleX = -1.28f;
        CLIFF_DARK.middleY = -0.5f;
        CLIFF_DARK.sideY = -0.5f;
        // CLIFF_LIGHT | Setup
        CLIFF_LIGHT.side = new BodyImage("data/Magic_Cliffs/cliff/light_left.png", 15.5f);
        CLIFF_LIGHT.middle = new BodyImage("data/Magic_Cliffs/cliff/dark_middle.png", 15.5f);
        CLIFF_LIGHT.sideWidth = 4.8f;
        CLIFF_LIGHT.middleX = -1.28f;
        CLIFF_LIGHT.middleY = -0.5f;
        CLIFF_LIGHT.sideY = -0.5f;
        // GROUND | Setup
        GROUND.side = new BodyImage("data/Magic_Cliffs/ground/light_left.png", 15.5f);
        GROUND.middle = new BodyImage("data/Magic_Cliffs/ground/light_large.png", 4.5f);
        GROUND.sideWidth = 5.1f;
        GROUND.middleX = 0;
        GROUND.middleY = 4.8f;
        GROUND.sideY = 0.5f;
    }

    private BodyImage middle;
    private BodyImage side;
    private float sideWidth;
    private float middleX;
    private float middleY;
    private float sideY;
    // Methods
    public BodyImage geMiddleBody() {
        return middle;
    }
    public BodyImage getSideBody() {
        return side;
    }
    public float getSideWidth() {
        return sideWidth;
    }
    public float getMiddleX() {
        return middleX;
    }
    public float getMiddleY() {
        return middleY;
    }
    public float getSideY() {
        return sideY;
    }
}
