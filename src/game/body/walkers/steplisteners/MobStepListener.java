package game.body.walkers.steplisteners;
// Imports

import city.cs.engine.Body;
import city.cs.engine.StepEvent;
import game.Game;
import game.body.staticstructs.ground.GroundFrame;
import game.core.GameWorld;
import game.animation.MobAnimationStepListener;
import game.body.walkers.mobs.MobWalker;
import game.core.console.Console;
import game.enums.Direction;
import game.enums.State;
import org.jbox2d.common.Vec2;

// Class
public abstract class MobStepListener implements MobStepListenerFrame {
    // Fields
    protected final MobWalker mob;
    protected final MobAnimationStepListener animationsListener;
    protected static final float PATROL_RADIUS_X = 30.0f;
    protected static final float PATROL_RADIUS_Y = 10.0f; // was hopefully going to be used for vertical patrols like maybe flying creates, even if not added, maybe I'll pick this up in the future :)
    protected static final float ANIMATION_RADIUS = 40.0f;
    protected static final float VIEW_RADIUS_X = 20.0f;
    protected static final float WALK_SPEED = 2.0f;
    protected final GameWorld gameWorld;
    private Boolean isOnPlatform = false;
    private GroundFrame currentPlatform;
    // Constructor
    public MobStepListener(MobWalker mob, GameWorld gameWorld) {
        this.mob = mob;
        this.gameWorld = gameWorld;
        animationsListener = new MobAnimationStepListener(mob);
        gameWorld.addStepListener(this);
    }
    // Methods
    @Override
    public void preStep(StepEvent stepEvent) {
        Vec2 mobPos = mob.getPosition();
        // movement handling
        if (mob.isHit() || mob.isDead()) { // don't want the mob to move if it is hit or dead
            mob.stopWalking();
            mob.setLinearVelocity(new Vec2(0, mob.getLinearVelocity().y));
            /*
             for some reason mobs were still moving, So I call serLinearVelocity to fix it.
             Same issue with all walkers, it would seem stopWalking() does not counteract the applied velocity
             Had this issue since the start with all walker types, might be worth creating my own stop walking method which sets the velocity to 0 and calls the original stop walking inside it.
            */
        } else {
            if(!isOnPlatform) {
                isOnPlatform = getPlatform();
            }
            handleMobMovement(mobPos);
        }
        // animation handling
        if (nearViewX(mobPos.x, ANIMATION_RADIUS) && gameWorld.isRunning()) {
            animationsListener.step();
        }
    }

    @Override
    public void postStep(StepEvent stepEvent) {
        if (mob.getLinearVelocity().y < 0) {
            if (gameWorld.getLevel().isOutOfBounds(mob)) {
                mob.die();
            }
        }
    }

    @Override
    public void handleMobMovement(Vec2 pos) {}

    @Override
    public boolean isPlayerInRange(Vec2 pos, boolean playerBehind) {
        if (playerBehind) {return nearViewX(pos.x, VIEW_RADIUS_X/2) && nearViewY(pos.y, mob.getHalfDimensions().y) && !gameWorld.getPlayer().isDead();}
        else {return nearViewX(pos.x, VIEW_RADIUS_X) && nearViewY(pos.y, mob.getHalfDimensions().y) && !gameWorld.getPlayer().isDead();}
    }

    @Override
    public void patrolArea(Vec2 pos) {
        if (mob.getState() != State.IDLE) {
            mob.setState(State.IDLE);
            if (mob.getDirection().equals(Direction.RIGHT)) {mob.startWalking(WALK_SPEED);}
            else {mob.startWalking(-WALK_SPEED);}
        }
        if (mob.getLinearVelocity().x < 1 && mob.getLinearVelocity().y > -1) {
            if (mob.getDirection() == Direction.RIGHT) {
                mob.startWalking(WALK_SPEED);
            } else if (mob.getDirection() == Direction.LEFT) { // I have added an else if here as I have been pondering adding up and down as directions (flying mobs maybe <S S>)
                mob.startWalking(-WALK_SPEED);
            }
        }
        if (!isOnPlatform) {
            checkBounds(pos, PATROL_RADIUS_X, mob.ORIGIN_X);
        } else {
            checkBounds(pos, currentPlatform.getHalfDimensions().x- (mob.getHalfDimensions().x + 1), currentPlatform.getOriginPos().x);
        }
    }

    private boolean getPlatform() {
        if (!isOnPlatform) {
            for (Body body : mob.getBodiesInContact()) {
                if (body instanceof GroundFrame ground) {
                    currentPlatform = ground;
                    return true;
                }
            }
        }
        return false;
    }

    private void checkBounds(Vec2 pos, float patrol_radius_x, float originPos) {
        if (pos.x >= (originPos) + patrol_radius_x) {
            mob.startWalking(-WALK_SPEED);
            mob.setDirection(Direction.LEFT);
        } else if (pos.x <= (originPos) - patrol_radius_x) {
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
