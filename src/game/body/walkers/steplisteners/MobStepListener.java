package game.body.walkers.steplisteners;
// Imports

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.GameWorld;
import game.animation.WalkerAnimationStepListener;
import game.body.walkers.mobs.MobWalker;
import game.enums.Direction;
import game.enums.State;
import org.jbox2d.common.Vec2;

// Class
public class MobStepListener implements MobStepListenerFrame {
    // Fields
    protected final MobWalker mob;
    protected final WalkerAnimationStepListener animationsListener;
    protected static final float CHASE_DISTANCE = 3.0f;
    protected static final float CHASE_RADIUS_X = 30.0f;
    protected static final float CHASE_RADIUS_Y = 10.0f;
    protected static final float ANIMATION_RADIUS = 40.0f;
    protected static final float VIEW_RADIUS_X = 10.0f;
    protected static final float VIEW_RADIUS_Y = 3.0f;
    protected static final float WALK_SPEED = 2.0f;
    protected final GameWorld gameWorld;
    // Constructor
    public MobStepListener(MobWalker mob, GameWorld gameWorld) {
        this.mob = mob;
        this.gameWorld = gameWorld;
        animationsListener = new WalkerAnimationStepListener(mob);
        gameWorld.addStepListener(this);
    }
    // Methods
    @Override
    public void preStep(StepEvent stepEvent) {
        Vec2 mobPos = mob.getPosition();
        // movement handling
        if (mob.getHit() || mob.isDead()) { // don't want the mob to move if it is hit or dead
            mob.stopWalking();
        } else {
            handleMobMovement(mobPos);
        }
        // animation handling
        if (nearViewX(mobPos.x, ANIMATION_RADIUS)) {
            animationsListener.step();
        }
    }

    @Override
    public void postStep(StepEvent stepEvent) {}

    @Override
    public void handleMobMovement(Vec2 pos) {}

    @Override
    public boolean isPlayerInRange(Vec2 pos) {
        return nearViewX(pos.x, VIEW_RADIUS_X) && nearViewY(pos.y, VIEW_RADIUS_Y) && !gameWorld.getPlayer().isDead();
    }

    @Override
    public void patrolArea(Vec2 pos) {
        if (mob.getLinearVelocity().x < 1 && mob.getLinearVelocity().y > -1) {
            if (mob.getDirection() == Direction.RIGHT) {
                mob.startWalking(WALK_SPEED);
            } else if (mob.getDirection() == Direction.LEFT) { // I have added an else if here as I have been pondering adding up and down as directions (flying mobs maybe <S S>)
                mob.startWalking(-WALK_SPEED);
            }
        }
        if (pos.x >= (mob.ORIGIN_X) + CHASE_RADIUS_X) {
            mob.startWalking(-WALK_SPEED);
            mob.setDirection(Direction.LEFT);
        } else if (pos.x <= (mob.ORIGIN_X) - CHASE_RADIUS_X) {
            mob.startWalking(WALK_SPEED);
            mob.setDirection(Direction.RIGHT);
        }
    }

    @Override
    public boolean nearViewX(float mobX, float radiusX) {
        float playerX = gameWorld.getPlayer().getPosition().x;
        return playerX < (mobX + radiusX) && playerX > (mobX - radiusX);
    }

    @Override
    public boolean nearViewY(float mobY, float radiusY) {
        float playerY = gameWorld.getPlayer().getPosition().y;
        return playerY < (mobY + radiusY) && playerY > (mobY - radiusY);
    }

    protected int getPlayerDirectionX(float mobX) {
        return 1;
    }

    @Override
    public void remove() {
        animationsListener.remove();
        gameWorld.removeStepListener(this);
    }
}
