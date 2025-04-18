package game.body.staticstructs.ground.hauntedForest;
// Imports

import city.cs.engine.*;
import game.Game;
import game.body.staticstructs.ground.GroundFrame;
import game.body.walkers.PlayerWalker;
import game.core.console.Console;
import game.levels.LevelFrame;
import org.jbox2d.common.Vec2;

/**
 *
 */
// Class
public class HauntedPool extends GroundFrame {
    // Fields
    private final Sensor pool;
    private CollisionListener collisionListener = null;
    private StepListener stepListener = null;

    // Constructor
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
    @Override
    public void paint() {}

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
