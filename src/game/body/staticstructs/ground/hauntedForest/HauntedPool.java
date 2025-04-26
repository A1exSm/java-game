package game.body.staticstructs.ground.hauntedForest;
// Imports
import city.cs.engine.*;
import game.Game;
import game.body.staticstructs.ground.GroundFrame;
import game.body.walkers.PlayerWalker;
import game.levels.LevelFrame;
import org.jbox2d.common.Vec2;
// Class
/**
 * HauntedPool class represents a haunted pool in the game.
 * The pool has a sensor that detects when a player enters it and applies a velocity effect.
 * It is a non-image-based class.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 18-04-2025
 */
public class HauntedPool extends GroundFrame {
    // Fields
    private final Sensor pool;
    private CollisionListener collisionListener = null;
    private StepListener stepListener = null;
    // Constructor
    /**
     * Constructor for HauntedPool.<br>
     * Creates a new instance of HauntedPool with the specified parameters.
     * @param level the level frame
     * @param x the x-coordinate of the pool
     * @param y the y-coordinate of the pool
     * @param halfWidth the half-width of the pool
     * @param halfHeight the half-height of the pool
     */
    public HauntedPool(LevelFrame level, float x, float y, float halfWidth, float halfHeight) {
        super(level.getGameWorld());
        halfDimensions.x = halfWidth;
        halfDimensions.y = halfHeight;
        setPosition(new Vec2(x, y));
        pool = new Sensor(this, new BoxShape(halfDimensions.x, halfDimensions.y - 0.5f));
        new SolidFixture(this, new BoxShape(halfDimensions.x, 0.5f, new Vec2(0, -halfDimensions.y + 0.5f)));
        addPoolSensorListener();
        paint();
        setYTop(getPosition().y -halfDimensions.y + 0.5f);
    }
    // Methods
    /**
     * {@inheritDoc}
     */
    @Override
    public void paint() {}
    /**
     * Adds a step listener to the game world that applies a negative velocity effect to the player when they enter the pool.
     */
    private void addStepListener() {
        if (stepListener != null) {
            return;
        }
        stepListener =  new StepListener() {
            @Override
            public void preStep(StepEvent stepEvent) {
                PlayerWalker player = Game.gameWorld.getPlayer();
                if (player.intersects(new Vec2(getPosition().x, getPosition().y+0.5f), halfDimensions.x,halfDimensions.y - 0.5f)) {
                    player.setLinearVelocity(new Vec2(player.getLinearVelocity().x, player.getLinearVelocity().y*0.85f));
                }
            }
            @Override
            public void postStep(StepEvent stepEvent) {}
        };
        Game.gameWorld.addStepListener(stepListener);
    }
    /**
     * Adds a sensor listener to the pool that detects when a player enters it.
     * When the player enters, it adds a collision listener and a step listener.
     */
    private void addPoolSensorListener() {
        pool.addSensorListener(new SensorListener() {
            @Override
            public void beginContact(SensorEvent sensorEvent) {
                addPoolCollisionListener();
                addStepListener();
            }

            @Override
            public void endContact(SensorEvent sensorEvent) {

            }
        });
    }
    /**
     * Adds a collision listener to the pool that destroys the pool sensor and removes the step listener when a player collides with it.
     */
    private void addPoolCollisionListener() {
        if (collisionListener == null) {
            collisionListener = e -> {
                if (e.getOtherBody() instanceof PlayerWalker) {
                    pool.destroy();
                    Game.gameWorld.removeStepListener(stepListener);
                    this.removeCollisionListener(collisionListener);
                }
            };
            addCollisionListener(collisionListener);
        }
    }
}
