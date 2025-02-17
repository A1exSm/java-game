package game.body.walkers;
// Imports

import city.cs.engine.*;
import game.GameWorld;
import game.animation.PlayerAnimationStepListener;
import game.enums.Direction;
import game.body.walkers.mobs.WizardWalker;
import game.enums.State;
import game.enums.Walkers;
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
    private final ArrayList<WizardWalker> inRightSensor = new ArrayList<>();
    private final ArrayList<WizardWalker> inLeftSensor = new ArrayList<>();
    private final PlayerAnimationStepListener stepListener;
    private static final int MAX_HP = 1000;
    private int healthPoints = 1000;
    public boolean destroyed = false;
    // Constructor
    public PlayerWalker(GameWorld gameWorld) {
        super(gameWorld, new BoxShape(1,2), new Vec2(0,3), Walkers.PLAYER);
        setName("Player");
        createSensorListeners();
        rightSensor = new Sensor(this, new BoxShape(3,1.5f, new Vec2(4,0)));
        leftSensor = new Sensor(this, new BoxShape(3,1.5f, new Vec2(-4,0)));
        addSensorListeners();
        stepListener = new PlayerAnimationStepListener(gameWorld, this);
    }
    // Methods
    private void createSensorListeners() {
        attackRight = new SensorListener() {
            @Override
            public void beginContact(SensorEvent e) {
                updateSensor(e, inRightSensor);
            }
            @Override
            public void endContact(SensorEvent e) {}
        };

        attackLeft = new SensorListener() {
            @Override
            public void beginContact(SensorEvent e) {
                updateSensor(e, inLeftSensor);
            }

            @Override
            public void endContact(SensorEvent e) {}
        };

    }
    public void addSensorListeners() {
        rightSensor.addSensorListener(attackRight);
        leftSensor.addSensorListener(attackLeft);
    }

    private void updateSensor(SensorEvent e, ArrayList<WizardWalker> sensorArray) {
        for (WizardWalker wizard : getGameWorld().getWizards()) {
            if (e.getContactBody().getName().equals(wizard.getName())) {
                if (!sensorArray.contains(wizard)) {
                    sensorArray.add(wizard);
                }
            }
        }
    }
    public void checkWizards() {
        // x handling
        inRightSensor.removeIf(wizard -> (wizard.getPosition().x > (getPosition().x + 7 + WizardWalker.HALF_X)) || wizard.getPosition().x < getPosition().x);
        inLeftSensor.removeIf(wizard -> (wizard.getPosition().x < (getPosition().x + (-4 - 3)) + (-WizardWalker.HALF_X)) || wizard.getPosition().x > getPosition().x);
        // y handling
        inRightSensor.removeIf(wizard -> wizard.getPosition().y + wizard.ORIGIN_Y < getPosition().y - 1.5f || wizard.getPosition().y - wizard.ORIGIN_Y > getPosition().y + 1.5f);
        inLeftSensor.removeIf(wizard -> wizard.getPosition().y + wizard.ORIGIN_Y < getPosition().y - 1.5f || wizard.getPosition().y - wizard.ORIGIN_Y > getPosition().y + 1.5f);
    }

    public void hurtWizards() {
        ArrayList<WizardWalker> temp = new ArrayList<>();
        if (getDirection() == Direction.RIGHT) temp = inRightSensor;
        else temp = inLeftSensor;
        for (WizardWalker wizard : temp) {
            javax.swing.Timer timer1 = new javax.swing.Timer(200, e -> { // delay timer so that it looks like they were hurt as animation blade hits them
                wizard.toggleOnHit();
                wizard.takeDamage(500);
            });
            timer1.setRepeats(false);
            timer1.start();
        }
    }

    // Player health
    public void takeDamage(int damage, Walkers  walker) {
        healthPoints -= damage;
        toggleOnHit();
        if (healthPoints <= 0) {
            beginDeath();
        }
    }

    @Override
    public void die() {
        getGameWorld().removeStepListener(stepListener);
        removeAllImages();
        if (getDirection() == Direction.LEFT) addImage(new BodyImage("data/PlayerPNG/death/tile005.png", 18f)).flipHorizontal();
        else addImage(new BodyImage("data/PlayerPNG/death/tile005.png", 18f));
        destroyed = true;
    }

}
