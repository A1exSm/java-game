package game.body.fixtures;
// Imports

import city.cs.engine.*;
import game.body.staticstructs.ground.Bridge;
import org.jbox2d.common.Vec2;

/**
 * Extends {@link SolidFixture} and represents a part of a bridge.
 */
// Class
public class BridgePart extends SolidFixture {
    // Fields
    private String name;
    private float xPos;
    // Constructor
    public BridgePart(Bridge parent, Vec2 offset) {
        super(parent, new BoxShape(2, parent.getHalfDimensions().y, offset));
        xPos = parent.getOriginPos().x + offset.x;
    }
    public BridgePart(Bridge parent, Vec2 offset, String name) {
        super(parent, new BoxShape(2, parent.getHalfDimensions().y, offset));
        this.name = name;
        xPos = parent.getOriginPos().x + offset.x;
    }
    // Methods
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getXPos() {
        return xPos;
    }
}
