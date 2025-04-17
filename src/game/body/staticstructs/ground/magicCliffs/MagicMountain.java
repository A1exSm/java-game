package game.body.staticstructs.ground.magicCliffs;
// Imports

import city.cs.engine.BodyImage;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
public class MagicMountain extends GroundFrame {
    // Fields

    // Constructor
    public MagicMountain(GameWorld gameWorld, float x, float y) { // should rather create the image in an editor, then create a body.
        super(gameWorld);
        setPosition(new Vec2(x,y));
        paint();

    }
    // Methods | Public
    @Override
    public void paint() {
        
        addImage(new BodyImage("data/MagicCliffs/cliff/dark_centre.png", 11f));
        addImage(new BodyImage("data/MagicCliffs/cliff/dark_inside_large.png", 5.5f)).setOffset(new Vec2(-7.45f, -2.75f));
        addImage(new BodyImage("data/MagicCliffs/cliff/dark_inside_large.png", 5.5f)).setOffset(new Vec2(-7.45f, 2.75f));
        addImage(new BodyImage("data/MagicCliffs/cliff/dark_left_protrusion.png", 3f)).setOffset(new Vec2(-12.2f, 7f));
        addImage(new BodyImage("data/MagicCliffs/cliff/dark_left_side.png", 4.5f)).setOffset(new Vec2(-12.45f, 3.25f));
        addImage(new BodyImage("data/MagicCliffs/cliff/dark_left_side.png", 4.5f)).setOffset(new Vec2(-12.45f, -1.25f));
    }
}
