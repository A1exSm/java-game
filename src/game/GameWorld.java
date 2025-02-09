package game;
// Imports
import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import body.*;
import animation.*;
import mobs.Mob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Class
public class GameWorld extends World {
    // Fields
    private final GameView view;
    private final Player player;
    private boolean debugOn;
    protected boolean isPaused = false;
    public static GameTime gameTime;
    private ArrayList<Mob> mobs;
    // Constructor
    public GameWorld() {
        super();
        this.view = new GameView(this, 1200, 630);
        GameFrame frame = new GameFrame("GamePlayground", this.view);
        populate();
        player = new Player(this);
        new GameMenu(frame, this);
        new AnimationStepListener(this, player);
        new Controls(this, player, view);
        viewTracker();
        new PlayerFrames(PlayerState.RUN, this);
        // end of constructor start of a new world :)
        start();
    }
    // Debug Methods
    protected void debugOn() {
        view.setGridResolution(!debugOn ? 1 : 0);
        for (Body body : getDynamicBodies()) {
            body.setAlwaysOutline(!debugOn);
        }
        debugOn = !debugOn;
    }
    // Population methods
    private void populate() {
        Ground ground = new Ground(this, new Vec2(500, 0.5f), new Vec2(0, -0.5f));
        playGround(ground);
    }
    private void playGround(Ground ground) {
        float offset = 100f;
        new Ground(this, new Vec2(2, 2f), new Vec2(-5+offset, 2f));
        new SolidFixture(ground, new BoxShape(10, 1f, new Vec2(offset, 0)));
        mobs = new ArrayList<>(List.of(new Mob(this)));
//        DynamicBody toy = new DynamicBody(this, new DynamicPolygon(new Vec2[]{new Vec2(0, 0), new Vec2(), new Vec2(6, 0), new Vec2(6, 2), new Vec2(4, 4), new Vec2(2, 4), new Vec2(0, 2), new Vec2(0, 0)}));
//        toy.setLinearVelocity(new Vec2(offset, 1));
//        new Ground.Platform(this, new Vec2(20+offset, 4));
//        new Ground.Platform(this, new Vec2(27+offset, 7));
//        new Trampoline(this, new Vec2(-20+offset, 1));
    }
    // Method Override
    private void viewTracker() {
        addStepListener(new StepListener() {
            @Override
            public void preStep(StepEvent event) {
                view.setCentre(player.getPosition());
            }
            @Override
            public void postStep(StepEvent event) {
            }
        });
    }
    // Settings' Methods
    protected void togglePause() {
        if (isPaused) {
            isPaused = false;
            gameTime.toggleTimer();
            start();
        }
        else {
            isPaused = true;
            gameTime.toggleTimer();
            stop();
        }
    }
    // External Getters & Setters
    public void togglePlayerAttack() {
        player.isAttacking = false;
    }
    public boolean getPlayerAttack() {
        return player.isAttacking;
    }
    public ArrayList<Mob> getMobs() {
        return mobs;
    }
    public Walker getPlayer() {
        return player;
    }
}
