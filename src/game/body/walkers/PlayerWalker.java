package game.body.walkers;
// Imports

import city.cs.engine.*;
import game.CollisionHandler;
import game.GameWorld;
import game.animation.Direction;
import org.jbox2d.common.Vec2;

import java.util.ArrayList;

// Class
public class PlayerWalker extends WalkerFrame {
    // Fields
    public final float HALF_X = 1;
    public final float HALF_Y = 2; // not using Vec2 since objects' contents can be changed when final
    private SensorListener attackRight;
    private SensorListener attackLeft;
    private final Sensor rightSensor;
    private final Sensor leftSensor;
    private ArrayList<WizardWalker> inSensor = new ArrayList<>();
    // Constructor
    public PlayerWalker(GameWorld gameWorld) {
        super(gameWorld, new BoxShape(1,2), new Vec2(0,3));
        setName("Player");
        createSensorListeners();
        rightSensor = new Sensor(this, new BoxShape(3,2, new Vec2(4,0)));
        leftSensor = new Sensor(this, new BoxShape(3,2, new Vec2(-4,0)));
        addSensorListeners();
    }
    // Methods
    private void createSensorListeners() {
        attackRight = new SensorListener() {
            @Override
            public void beginContact(SensorEvent e) {
                if (getAttacking() && getDirection() == Direction.RIGHT) {}
            }

            @Override
            public void endContact(SensorEvent e) {
            }
        };
        attackLeft = new SensorListener() {
            @Override
            public void beginContact(SensorEvent e) {
                updateSensor(getDirection(), e);
            }

            @Override
            public void endContact(SensorEvent e) {
            }
        };
    }

    public void addSensorListeners() {
        rightSensor.addSensorListener(attackRight);
        leftSensor.addSensorListener(attackLeft);
    }

    private void updateSensor(Direction direction, SensorEvent e) {
        for (WizardWalker wizard : getGameWorld().getWizards()) {
            if (e.getContactBody().getName().equals(wizard.getName())) {
                if (!inSensor.contains(wizard)) {
                    inSensor.add(wizard);
                }
            }
        }
    }
    public void checkWizards() {
        inSensor.removeIf(wizard -> wizard.getPosition().x < (getPosition().x + (-4 - 3)) + (-WizardWalker.HALF_X));
    }

    public void hurtWizards() {
        for (WizardWalker wizard : inSensor) {
            System.out.println("Hurting wizard " + wizard.getName());
        }
    }

}
