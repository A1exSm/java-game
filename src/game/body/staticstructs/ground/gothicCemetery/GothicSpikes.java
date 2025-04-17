package game.body.staticstructs.ground.gothicCemetery;
// Imports

import city.cs.engine.*;
import game.body.staticstructs.ground.GroundFrame;
import game.body.walkers.PlayerWalker;
import game.core.GameWorld;
import game.exceptions.IllegalLengthScaleException;
import game.utils.GameBodyImage;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
public class GothicSpikes extends GroundFrame {
    // Fields
    public static final GameBodyImage IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-Tiles/ground_spikes.png", 4f);
    private static javax.swing.Timer timer;
    private final int lengthScale;
    // Constructor
    public GothicSpikes(GameWorld gameworld, float x, float y, int lengthScale) {
        super(gameworld);
        this.lengthScale = IllegalLengthScaleException.checkLengthScale(lengthScale);
        halfDimensions.x = IMG.getHalfDimensions().x*lengthScale;
        halfDimensions.y = -IMG.getHalfDimensions().y/2;
        setPosition(new Vec2(x, y));
        new Sensor(this, new BoxShape(halfDimensions.x, halfDimensions.y/2, new Vec2(0, halfDimensions.y/3))).addSensorListener(new SensorListener() {
            @Override
            public void beginContact(SensorEvent sensorEvent) {
                if (sensorEvent.getContactBody() instanceof PlayerWalker player) {
                    if (timer != null) {
                        timer.stop();
                        timer = null;
                    }
                    player.takeDamage(100, "Spikes");
                    timer = new javax.swing.Timer(1500, e -> {
                        player.takeDamage(30, "Spikes");
                    });
                    timer.setRepeats(true);
                    timer.start();
                }
            }

            @Override
            public void endContact(SensorEvent sensorEvent) {
                if (sensorEvent.getContactBody() instanceof PlayerWalker player) {
                    if (timer != null) {
                        timer.stop();
                        timer = null;
                    }
                }

            }
        });
        new SolidFixture(this, new BoxShape(halfDimensions.x, halfDimensions.y/2, new Vec2(0, -halfDimensions.y/2)));
        paint();
    }
    // Methods | Private
    public void paint() {
        
        if (lengthScale == 1) {
            addImage(IMG);
        } else {
            for (float i = -halfDimensions.x + IMG.getHalfDimensions().x; i < halfDimensions.x; i+= IMG.getDimensions().x) {
                addImage(IMG).setOffset(new Vec2(i, 0));
            }
        }
    }
}
