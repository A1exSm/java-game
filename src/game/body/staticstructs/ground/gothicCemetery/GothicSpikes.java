package game.body.staticstructs.ground.gothicCemetery;
// Imports
import city.cs.engine.*;
import game.body.staticstructs.ground.GroundFrame;
import game.body.walkers.PlayerWalker;
import game.body.walkers.mobs.MobWalker;
import game.core.GameWorld;
import game.exceptions.IllegalLengthScaleException;
import game.utils.GameBodyImage;
import org.jbox2d.common.Vec2;
// Class
/**
 * Gothic Spikes
 * This class represents a groundFrame object in the game world that deals damage to players and mobs that come into contact with it.
 * The spikes are represented by an image and have a specified length scale.
 * The spikes deal damage over time while the player or mob is in contact with them.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 07-04-2025
 */
public class GothicSpikes extends GroundFrame {
    // Fields
    /**
     * The image representing the spikes. <br>
     * <img src="doc-files/ground_spikes.png" alt="Gothic Spikes">
     */
    public static final GameBodyImage IMG = new GameBodyImage("data/GothicvaniaCemetery/sliced-Tiles/ground_spikes.png", 4f);
    private static javax.swing.Timer timer;
    private final int lengthScale;
    // Constructor
    /**
     * Creates a new GothicSpikes object with the specified position and length scale.
     * Uses its own local image to represent the spikes.
     * Add a sensor with a listener to detect contact with players and mobs.
     * After contact with a mob/ player, a step listener is added to deal damage over time,
     * until the mob/player leaves the sensor.
     * @param gameworld The game world to which this object belongs.
     * @param x The x-coordinate of the object's position.
     * @param y The y-coordinate of the object's position.
     * @param lengthScale The length scale of the spikes (1 for normal size, 2 for double size).
     * @throws IllegalLengthScaleException if the length scale is less than 1.
     */
    public GothicSpikes(GameWorld gameworld, float x, float y, int lengthScale) {
        super(gameworld);
        this.lengthScale = IllegalLengthScaleException.checkLengthScale(lengthScale);
        halfDimensions.x = IMG.getHalfDimensions().x*lengthScale;
        halfDimensions.y = -IMG.getHalfDimensions().y/2;
        setPosition(new Vec2(x, y));
        new Sensor(this, new BoxShape(halfDimensions.x, halfDimensions.y/2, new Vec2(0, halfDimensions.y/3))).addSensorListener(new SensorListener() {
            @Override
            public void beginContact(SensorEvent sensorEvent) {
                if (sensorEvent.getContactBody() instanceof PlayerWalker player) { // we can change this whole thing to a list of walkers intersecting the sensor and every time the timer ends, hurt them.
                    if (timer != null) {
                        timer.stop();
                        timer = null;
                    }
                    player.takeDamage(100, "Spikes");
                    timer = new javax.swing.Timer(1500, e -> player.takeDamage(30, "Spikes"));
                    timer.setRepeats(true);
                    timer.start();
                } else if (sensorEvent.getContactBody() instanceof MobWalker mob) {
                    if (timer != null) {
                        timer.stop();
                        timer = null;
                    }
                    mob.takeDamage(100);
                    timer = new javax.swing.Timer(1500, e -> mob.takeDamage(30));
                    timer.setRepeats(true);
                    timer.start();
                }
            }
            @Override
            public void endContact(SensorEvent sensorEvent) {
                if (sensorEvent.getContactBody() instanceof PlayerWalker) {
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
    // Methods
    /**
     * {@inheritDoc}
     */
    @Override
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
