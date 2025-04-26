package game.body.projectiles;
// Imports
import city.cs.engine.*;
import game.Game;
import game.body.walkers.WalkerFrame;
import game.core.console.Console;
import game.enums.Direction;
import game.utils.GameBodyImage;
import org.jbox2d.common.Vec2;
// Class
/**
 * The Projectile class represents a projectile in the game.
 * It extends the DynamicBody class and is used to create projectiles that can be fired by players or enemies.
 * The class handles the projectile's movement, collision detection, and damage dealing.
 * @author Alexander Smolowitz, alexander.smolowitz@city.ac.uk
 * @since 06-03-2025
 */
public class Projectile extends DynamicBody {
    // Fields
    private final Direction direction;
    private final WalkerFrame walker;
    private final int damage;

    // Constructor
    /**
     * Constructs a new Projectile object.
     * @param walkerFrame The WalkerFrame object representing the entity that fired the projectile.
     * @param direction The direction in which the projectile is fired (LEFT or RIGHT).
     * @param damage The amount of damage the projectile will deal upon collision.
     * @param position The initial position of the projectile in the game world.
     */
    public Projectile(WalkerFrame walkerFrame, Direction direction, int damage, Vec2 position) {
        super(Game.gameWorld);
        this.direction = direction;
        this.walker = walkerFrame;
        this.damage = damage;
        setName(walkerFrame.getName() + "'s Projectile");
        setBullet(true);
        constructProjectile();
        setGravityScale(0.4f);
        initListener();
        flingProjectile(position);
    }
    // Methods
    /**
     * Constructs the projectile by adding a solid fixture and an image.
     */
    private void constructProjectile() {
        new SolidFixture(this, new CircleShape(0.5f));
        if (direction == Direction.LEFT) { addImage(new GameBodyImage("data/projectiles/fireball.gif", 10)).flipHorizontal(); }
        else if (direction == Direction.RIGHT) { addImage(new GameBodyImage("data/projectiles/fireball.gif", 10)); }
    }
    /**
     * Sets the initial velocity of the projectile based on its direction.
     * @param position The initial position of the projectile in the game world.
     */
    private void flingProjectile(Vec2 position) {
        setPosition(position);
        float velocityX = 10;
        if (direction == Direction.RIGHT) {
            setLinearVelocity(new Vec2(velocityX, 2));
        } else {
            setLinearVelocity(new Vec2(-velocityX, 2));
        }
    }
    /**
     * Initializes the collision listener for the projectile.
     * It detects collisions with other bodies and handles damage dealing.
     */
    private void initListener() {
        addCollisionListener(e ->{
            Console.debug(getName() +  " hit the " + e.getOtherBody().getName());
            if (e.getOtherBody().getName().equals("Player")) {
                Game.gameWorld.getPlayer().takeDamage(damage, walker.getWalkerType().name());
            }
            this.destroy();
        });
    }
}
