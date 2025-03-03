package game.body;
// Imports

import city.cs.engine.BodyImage;
import city.cs.engine.CircleShape;
import city.cs.engine.DynamicBody;
import city.cs.engine.SolidFixture;
import game.Game;
import game.body.walkers.WalkerFrame;
import game.enums.Direction;
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
        if (direction == Direction.LEFT) {addImage(new BodyImage("data/projectiles/fireball.gif", 10)).flipHorizontal();}
        else if (direction == Direction.RIGHT) {addImage(new BodyImage("data/projectiles/fireball.gif", 10));}
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
            System.out.println(getName() +  " hit the " + e.getOtherBody().getName());
            if (e.getOtherBody().getName().equals("Player")) {
                Game.gameWorld.getPlayer().takeDamage(damage, walker);
            }
            this.destroy();
        });
    }
}
