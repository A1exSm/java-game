package game.body.walkers;
// Imports
import city.cs.engine.*;
import game.body.staticstructs.HauntedBackdrop;
import game.core.GameWorld;
import game.animation.PlayerStepListener;
import game.body.walkers.mobs.MobWalker;
import game.enums.Direction;
import game.enums.State;
import game.enums.Walkers;
import org.jbox2d.common.Vec2;
import java.util.ArrayList;

// Class
public final class PlayerWalker extends WalkerFrame {
    // Fields
    public final float HALF_X = 1;
    public final float HALF_Y = 2; // not using Vec2 since objects' contents can be changed when final
    private SensorListener attackRight;
    private SensorListener attackLeft;
    private Sensor rightSensor;
    private Sensor leftSensor;
    private final ArrayList<MobWalker> inRightSensor = new ArrayList<>();
    private final ArrayList<MobWalker> inLeftSensor = new ArrayList<>();
    private final PlayerStepListener stepListener;
    public static final State[] SUPPORTED_STATES = new State[]{
            State.ATTACK1, State.ATTACK2, State.DEATH, State.FALL,
            State.HIT, State.IDLE, State.JUMP, State.RUN
    };
    private static final int MAX_HP = 1000;
    private int healthPoints = 1000;
    public boolean destroyed = false;
    private final int damage = 350;
    // Constructor
    public PlayerWalker(GameWorld gameWorld) {
        super(gameWorld, new BoxShape(0.2f,1.6F), new Vec2(0,3), Walkers.PLAYER);
        setName("Player");
        constructFixtures();
        createSensorListeners();
        rightSensor = new Sensor(this, new BoxShape(3, 1.5f, new Vec2(4, 0)));
        leftSensor = new Sensor(this, new BoxShape(3, 1.5f, new Vec2(-4, 0)));
        addSensorListeners();
        stepListener = new PlayerStepListener(gameWorld, this);
    }
    // Methods | Private | Fixtures
    private void constructFixtures() {
        constructSolidFixture(new PolygonShape(1.08f,-0.83f, 0.9f,-1.84f, 0f,-1.84f, 0f,0.07f, 0.5f,0.07f));
        constructSolidFixture(new PolygonShape(-1.08f,-0.83f, -0.9f,-1.84f, 0f,-1.84f, 0f,0.07f, -0.5f,0.07f));
        constructSolidFixture(new PolygonShape(0.0f,1.66f, -1.22f,0.58f, -0.43f,-0.07f, 0.0f,-0.04f));
        constructSolidFixture(new PolygonShape(1.22f,0.58f, 0.43f,-0.07f, 0.0f,-0.04f, 0.0f,1.66f));
        constructSolidFixture(new PolygonShape(-0.18f,1.48f, -0.5f,1.87f, 0.0f,1.98f, 0.0f,1.48f));
        constructSolidFixture(new PolygonShape(0.18f,1.48f, 0.5f,1.87f, 0.0f,1.98f, 0.0f,1.48f));
    }
    //Methods | Private | Sensors
    private void reconstructSensors() {
        if (rightSensor.getBody() == null && leftSensor.getBody() == null) {
            rightSensor = new Sensor(this, new BoxShape(3, 1.5f, new Vec2(4, 0)));
            leftSensor = new Sensor(this, new BoxShape(3, 1.5f, new Vec2(-4, 0)));
            addSensorListeners();
            return;
        }
        System.err.println("Constructing Player Sensors: Sensors already exist!");
    }

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

    private void addSensorListeners() {
        rightSensor.addSensorListener(attackRight);
        leftSensor.addSensorListener(attackLeft);
    }

    private void updateSensor(SensorEvent e, ArrayList<MobWalker> sensorArray) {
        for (MobWalker mob : GameWorld.getMobs()) {
            if (e.getContactBody().getName().equals(mob.getName())) {
                if (!sensorArray.contains(mob)) {
                    sensorArray.add(mob);
                }
            }
        }
    }
    // Methods | Public | State Changes
    public void makePlayerSolid() {
        makeSolid();
        reconstructSensors();
    }
    public void makePlayerGhostly() {
        makeGhostly();
        reconstructSensors();
    }
    // Methods | Public | Attack Sensors
    public void checkMob() {
        // x handling
        inRightSensor.removeIf(mob -> (mob.getPosition().x > (getPosition().x + 7 + mob.HALF_X)) || mob.getPosition().x < getPosition().x);
        inLeftSensor.removeIf(mob -> (mob.getPosition().x < (getPosition().x -7) + (-mob.HALF_X)) || mob.getPosition().x > getPosition().x);
        // y handling
        inRightSensor.removeIf(mob -> mob.getPosition().y + mob.ORIGIN_Y < getPosition().y - 1.5f || mob.getPosition().y - mob.ORIGIN_Y > getPosition().y + 1.5f);
        inLeftSensor.removeIf(mob -> mob.getPosition().y + mob.ORIGIN_Y < getPosition().y - 1.5f || mob.getPosition().y - mob.ORIGIN_Y > getPosition().y + 1.5f);
    }

    public void hurtMob() {
        soundFX.attack1(this);
        ArrayList<MobWalker> temp;
        if (getDirection() == Direction.RIGHT) temp = new ArrayList<>(inRightSensor);
        else temp = new ArrayList<>(inLeftSensor);
        for (MobWalker mob : temp) {
            mob.toggleOnHit();
            javax.swing.Timer timer1 = new javax.swing.Timer(100, e -> { // delay timer so that it looks like they were hurt as animation blade hits them
                mob.takeDamage(damage);
            });
            timer1.setRepeats(false);
            timer1.start();
        }
    }
    // Methods | Public | Health
    public void takeDamage(int damage, WalkerFrame  walker) {
        System.out.println(getName() + " taking damage from " + walker.getName() + ": " + healthPoints + " - " + damage);
        healthPoints -= damage;
        toggleOnHit();
        if (healthPoints <= 0) {
            setLinearVelocity(new Vec2(0, 0));
            stopWalking();
            beginDeath();
        }
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getMaxHealth() {
        return MAX_HP;
    }

    public void addHealthPoints(int healthPoints) {
        this.healthPoints += healthPoints;
        if (this.healthPoints > MAX_HP) this.healthPoints = MAX_HP;
    }

    public boolean isHealthFull() {
        return healthPoints == MAX_HP;
    }
    // Methods | Public | @Override
    @Override
    public void die() {
        if (destroyed) return;
        stepListener.remove();
        removeAllImages();
        if (getDirection() == Direction.LEFT) addImage(new BodyImage("data/PlayerPNG/death/tile005.png", 18f)).flipHorizontal();
        else addImage(new BodyImage("data/PlayerPNG/death/tile005.png", 18f));
        destroyed = true;
    }
    // Methods | Public | Movement @Override
    @Override
    public void jump(float speed) {
        if (isOnSurface() && !isGhostly()) {
            super.jump(10);
            soundFX.jump();
        }
    }
    @Override
    public void startWalking(float speed) {
        super.startWalking(speed);
        soundFX.run();
    }
    @Override
    public void stopWalking() {
        super.stopWalking();
        soundFX.stopFX(State.RUN);
    }
    // Methods | Private | Movement
    public boolean isOnSurface() { // attempt at preventing jumping on surfaces, flawed cus we need the body in contacts half-height
        for (Body body : getBodiesInContact()) {
            if (body.getPosition().y < getPosition().y-2) {
                return true;
            } else if (body instanceof HauntedBackdrop) {
                return true;
            }
        }
        return false;
    }
    // Methods | Public | Level
}
