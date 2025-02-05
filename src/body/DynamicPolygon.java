package body;

import city.cs.engine.PolygonShape;
import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.Arrays;

public class DynamicPolygon extends PolygonShape {
    public DynamicPolygon(Vec2[] points) {
        super(new ArrayList<>(Arrays.asList(points)));
    }
}
