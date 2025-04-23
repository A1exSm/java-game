package game.body.projectiles;
// Imports

import city.cs.engine.*;
import game.Game;
import game.body.staticstructs.ground.GroundFrame;
import game.body.walkers.WalkerFrame;
import game.core.console.Console;
import game.enums.Direction;
import game.utils.GameBodyImage;
import org.jbox2d.common.Vec2;

// Class
public class Projectile extends DynamicBody {
    // Fields
    private final Direction direction;
    private final WalkerFrame walker;
    private final int damage;
    public float velocityX = 10;
    // Constructor
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
    private void constructProjectile() {
        new SolidFixture(this, new CircleShape(0.5f));
        if (direction == Direction.LEFT) {addImage(new GameBodyImage("data/projectiles/fireball.gif", 10)).flipHorizontal();}
        else if (direction == Direction.RIGHT) {addImage(new GameBodyImage("data/projectiles/fireball.gif", 10));}
    }

    private void flingProjectile(Vec2 position) {
        setPosition(position);
        if (direction == Direction.RIGHT) {
            setLinearVelocity(new Vec2(velocityX, 2));
        } else {
            setLinearVelocity(new Vec2(-velocityX, 2));
        }
    }

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
