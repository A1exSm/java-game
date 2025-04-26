package game.body.walkers;
// Imports
import city.cs.engine.*;
import game.Game;
import game.body.staticstructs.ground.hauntedForest.HauntedBackdrop;
import game.body.staticstructs.ground.GroundFrame;
import game.body.staticstructs.ground.gothicCemetery.GothicSlope;
import game.core.GameWorld;
import game.animation.PlayerStepListener;
import game.body.walkers.mobs.MobWalker;
import game.core.console.Console;
import game.enums.Direction;
import game.enums.State;
import game.enums.WalkerType;
import org.jbox2d.common.Vec2;
import java.util.ArrayList;
// Class

/**
 * The core class for the player.
 * It handles the player's movement, health, and interactions with the game world.
 * It also manages the player's attack sensors and the mobs within their range.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 03-02-2025
 */
public final class PlayerWalker extends WalkerFrame {
    // Fields
    /**
     * The half-x dimension of the player body.
     */
    public static final float HALF_X = 1f;
    /**
     * The half-y dimension of the player body.
     */
    public static final float HALF_Y = 2f;
    /**
     * The default damage dealt by the player.
     */
    public static final int DEFAULT_DAMAGE = 350;
    private SensorListener attackRight;
    private SensorListener attackLeft;
    private Sensor rightSensor;
    private Sensor leftSensor;
    private final ArrayList<MobWalker> inRightSensor = new ArrayList<>();
    private final ArrayList<MobWalker> inLeftSensor = new ArrayList<>();
    private final PlayerStepListener stepListener;
    private static final State[] SUPPORTED_STATES = new State[]{ State.ATTACK1, State.ATTACK2, State.DEATH, State.FALL, State.HIT, State.IDLE, State.JUMP, State.RUN };
    private String deathMessage;
    private static final int MAX_HP = 1000;
    private int healthPoints = 1000;
    private boolean destroyed = false;
    private int damage = DEFAULT_DAMAGE;
    // Constructor
    /**
     * Initializes the player with a specified game world.
     * Sets up the necessary fixtures, sensors and listeners.
     * @param gameWorld The GameWorld object representing the game world.
     */
    public PlayerWalker(GameWorld gameWorld) {
        super(gameWorld, new BoxShape(0.2f,1.6F), new Vec2(0,3), WalkerType.PLAYER);
        setName("Player");
        constructFixtures();
        createSensorListeners();
        rightSensor = new Sensor(this, new BoxShape(4, 1.5f, new Vec2(4, 0)));
        leftSensor = new Sensor(this, new BoxShape(4, 1.5f, new Vec2(-4, 0)));
        addSensorListeners();
        stepListener = new PlayerStepListener(gameWorld, this);
    }
    // Methods | Private | Fixtures
    /**
     * Constructs the additional polygon fixtures for the player body.
     * Polygons were created using {@link game.PolygonEditor}
     * @see PolygonShape
     */
    private void constructFixtures() {
        constructSolidFixture(new PolygonShape(1.08f,-0.83f, 0.9f,-1.84f, 0f,-1.84f, 0f,0.07f, 0.5f,0.07f));
        constructSolidFixture(new PolygonShape(-1.08f,-0.83f, -0.9f,-1.84f, 0f,-1.84f, 0f,0.07f, -0.5f,0.07f));
        constructSolidFixture(new PolygonShape(0.0f,1.66f, -1.22f,0.58f, -0.43f,-0.07f, 0.0f,-0.04f));
        constructSolidFixture(new PolygonShape(1.22f,0.58f, 0.43f,-0.07f, 0.0f,-0.04f, 0.0f,1.66f));
        constructSolidFixture(new PolygonShape(-0.18f,1.48f, -0.5f,1.87f, 0.0f,1.98f, 0.0f,1.48f));
        constructSolidFixture(new PolygonShape(0.18f,1.48f, 0.5f,1.87f, 0.0f,1.98f, 0.0f,1.48f));
    }
    //Methods | Private | Sensors
    /**
     * Reconstructs the sensors for the player.
     * This is called when the player is made solid or ghostly.
     * It ensures that the sensors are properly set up and listeners are added.
     */
    private void reconstructSensors() {
        if (rightSensor.getBody() == null && leftSensor.getBody() == null) {
            rightSensor = new Sensor(this, new BoxShape(4, 1.5f, new Vec2(4, 0)));
            leftSensor = new Sensor(this, new BoxShape(4, 1.5f, new Vec2(-4, 0)));
            addSensorListeners();
            return;
        }
        Console.warning("Constructing Player Sensors: Sensors already exist!");
    }
    /**
     * Creates the sensor listeners for the player.
     * These listeners handle the contact events for the sensors.
     */
    private void createSensorListeners() {
        attackRight = new SensorListener() {
            @Override
            public void beginContact(SensorEvent e) {
                updateSensor(e, inRightSensor);
            }
            @Override
            public void endContact(SensorEvent e) { checkMob(); }
        };
        attackLeft = new SensorListener() {
            @Override
            public void beginContact(SensorEvent e) {
                updateSensor(e, inLeftSensor);
            }
            @Override
            public void endContact(SensorEvent e) { checkMob(); }
        };
    }
    /**
     * Adds the sensor listeners to the sensors.
     * This is called when the sensors are created/reconstructed.
     */
    private void addSensorListeners() {
        rightSensor.addSensorListener(attackRight);
        leftSensor.addSensorListener(attackLeft);
    }
    /**
     * Updates the sensor with the mobs that are in contact with it.
     * This is called when a contact event occurs.
     * Only mobs that are not already in the sensor array are added.
     * @param e The SensorEvent object representing the contact event.
     * @param sensorArray The array list of mobs in the sensor.
     */
    private void updateSensor(SensorEvent e, ArrayList<MobWalker> sensorArray) {
        for (MobWalker mob : GameWorld.getMobs()) {
            if (e.getContactBody() instanceof MobWalker && e.getContactBody() == mob || mob.intersects(this)) {
                if (!sensorArray.contains(mob)) {
                    sensorArray.add(mob);
                }
            }
        }
        checkMob();
    }
    // Methods | Public | State Changes
    /**
     * Makes the player solid, allowing them to interact with the game world.
     * This is called when the player is ghostly.
     * Calls the super method {@link #makeSolid()}
     */
    public void makePlayerSolid() {
        makeSolid();
        reconstructSensors();
    }
    /**
     * Makes the player ghostly, allowing them to pass through the game world.
     * This is called when the player is solid.
     * Calls the super method {@link #makeGhostly()}
     */
    public void makePlayerGhostly() {
        makeGhostly();
        reconstructSensors();
    }
    // Methods | Public | Attack Sensors
    /**
     * Checks the mobs in the attack sensors.
     * This is called when the player attacks.
     * It removes any mobs that are no longer intersecting the sensor.
     */
    public void checkMob() {
        if (!isDead()) {
            inRightSensor.removeIf(mob -> !rightSensor.intersects(mob.getPosition(), mob.getHalfDimensions().x, mob.getHalfDimensions().y));
            inLeftSensor.removeIf(mob -> !leftSensor.intersects(mob.getPosition(), mob.getHalfDimensions().x, mob.getHalfDimensions().y));
        }
    }
    /**
     * Plays the attack sound effect,
     * starts a timer with a delay of 100 ms.
     * when the timer ends, it checks the direction of the player,
     * and then hurts all mobs in the corresponding sensor.
     */
    public void hurtMob() {
        soundFX.attack1(this);
        javax.swing.Timer timer1 = new javax.swing.Timer(100, e -> { // delay timer so that it looks like they were hurt as animation hits them
            ArrayList<MobWalker> temp;
            if (getDirection() == Direction.RIGHT) {
                temp = new ArrayList<>(inRightSensor);
            } else {
                temp = new ArrayList<>(inLeftSensor);
            }
            for (MobWalker mob : temp) {
                mob.toggleOnHit();
                mob.takeDamage(damage);
            }
        });
        timer1.setRepeats(false);
        timer1.start();

    }
    // Methods | Public | Health
    /**
     * Toggles the onHit animation for the player,
     * deducts health points, and checks for death.
     * Outputs debug information to the console.
     * @param damage The amount of damage taken.
     * @param attacker The name of the attacker, or just an attacker message.
     */
    public void takeDamage(int damage, String  attacker) {
        Console.debug(getName() + " taking damage from " + attacker + ": " + healthPoints + " - " + damage);
        healthPoints -= damage;
        toggleOnHit();
        if (healthPoints <= 0) {
            setLinearVelocity(new Vec2(0, 0));
            stopWalking();
            beginDeath();
            deathMessage = attacker;
        }
    }
    /**
     * gets the health points of the player.
     * @return the health points value
     */
    public int getHealthPoints() {
        return healthPoints;
    }
    /**
     * gets the player's max health points.
     * @return the MAX_HEALTH constant
     */
    public int getMaxHealth() {
        return MAX_HP;
    }
    /**
     * Checks if the player is out of the level bounds.
     * If so, it checks whether the player fell to death
     * or merely went out of bounds,
     * then calls the {@link game.core.GameView#blackScreenDeath(String) #blackScreenDeath(String)}
     * method with the appropriate message.
     * @param deathMessage the appropriate death message
     */
    public void outOfBounds(String deathMessage) {
        if (destroyed) return;
        stepListener.remove();
        removeAllImages();
        if (getDirection() == Direction.LEFT) addImage(new BodyImage("data/PlayerPNG/death/tile005.png", 18f)).flipHorizontal();
        else addImage(new BodyImage("data/PlayerPNG/death/tile005.png", 18f));
        Game.gameView.blackScreenDeath("FELL TO DEATH");
        destroyed = true;
    }
    /**
     * checks if the player's health is equal
     * to the MAX_HEALTH constant
     * @return {@code true} if the health points are equal to the max health
     */
    public boolean isHealthFull() {
        return healthPoints == MAX_HP;
    }
    // Methods | Public | @Override

    /**
     * Sets a death image, ends the game,
     * and removes the step listener.
     */
    @Override
    public void die() {
        if (destroyed) return;
        stepListener.remove();
        removeAllImages();
        if (getDirection() == Direction.LEFT) addImage(new BodyImage("data/PlayerPNG/death/tile005.png", 18f)).flipHorizontal();
        else addImage(new BodyImage("data/PlayerPNG/death/tile005.png", 18f));
        Game.gameView.gameOver("Died To " + deathMessage);
        destroyed = true;
    }
    // Methods | Public | Movement @Override
    /**
     * If the player is on a valid surface
     * Sets the player to jump with a speed of 10.
     * It also plays the jump sound effect.
     * @param speed The speed of the jump.
     */
    @Override
    public void jump(float speed) {
        if (isOnSurface() && isSolid()) {
            super.jump(10);
            soundFX.jump();
        }
    }
    /**
     * Calls the super {@link Walker#startWalking} method.
     * Plays the running sound effect.
     * @param speed The speed of the walk.
     */
    @Override
    public void startWalking(float speed) {
        super.startWalking(speed);
        soundFX.run();
    }
    /**
     * Calls the super {@link Walker#stopWalking} method.
     * Stops the running sound effect.
     */
    @Override
    public void stopWalking() {
        super.stopWalking();
        soundFX.stopFX(State.RUN);
    }
    // Methods | Private | Movement
    /**
     * Checks if the player is on a valid surface.
     * This is used to prevent jumping on surfaces.
     * Uses special cases when needed for specific
     * instances of {@link GroundFrame}.
     * @return {@code true} if the player is on a valid surface
     */
    public boolean isOnSurface() { // attempt at preventing jumping on surfaces, flawed cus we need the body in contacts half-height
        boolean onSurface = false;
        for (Body body : this.getBodiesInContact()) {
            if (onSurface) { // only runs while onSurface is false, meaning less unnecessary checks
                break;
            }
            if (body instanceof GroundFrame frame && !(body instanceof HauntedBackdrop) && !(body instanceof GothicSlope)) { // ensures player is on-top of flat ground
                onSurface =  frame.getPosition().y + frame.getHalfDimensions().y < getPosition().y-1.8;
            } else if (body.getPosition().y < getPosition().y-1.8 || body instanceof HauntedBackdrop) {
                onSurface = true;
            } else if (body instanceof GothicSlope slope) {
                if (getPosition().y - 2.8 < slope.getLineEquationYPos(getPosition().x)) {
                    setLinearVelocity(new Vec2(0, 0));
                    onSurface =  true;
                }
            }
        }
        return onSurface;
    }
    // Methods | Public | Potions
    /**
     * adds health points to the player's health field
     * @param healthPoints the number of health points to add
     */
    public void addHealthPoints(int healthPoints) {
        this.healthPoints += healthPoints;
        if (this.healthPoints > MAX_HP) this.healthPoints = MAX_HP;
    }
    /**
     * adds damage to the player's damage field
     * @param strength the number of damage points to add
     */
    public void addStrength(int strength) {
        damage += strength;
    }
    /**
     * removes damage from the player's damage field
     * @param strength the number of damage points to remove
     */
    public void removeStrength(int strength) {
        damage -= strength;
    }
    // Methods | Getters
    /**
     * gets the player's damage field value
     * @return the damage value
     */
    public int getDamage() {
        return damage;
    }
    /**
     * Gets the list of supported states for a player.
     * @return An array of supported states.
     * @see State
     */
    public static State[] getSupportedStates() {
        return SUPPORTED_STATES;
    }
}
