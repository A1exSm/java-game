package game;
// Imports
import game.body.dynamicstructs.DynamicPolygon;
import game.body.staticstructs.Ground;
import game.body.staticstructs.Trampoline;
import game.body.walkers.PlayerWalker;
import game.body.walkers.mobs.WizardWalker;
import city.cs.engine.*;
import game.enums.State;
import game.enums.Walkers;
import game.utils.*;
import org.jbox2d.common.Vec2;
import game.animation.*;

import java.util.ArrayList;

// Class
public class GameWorld extends World {
    // Fields
    private final GameView view;
    private final PlayerWalker player;
    private boolean debugOn;
    private boolean isPaused = false;
    public static GameTime gameTime;
    private static ArrayList<WizardWalker> wizards = new ArrayList<>();
    // Constructor
    public GameWorld() {
        super();
        this.view = new GameView(this, 1200, 630);
        GameFrame frame = new GameFrame("GamePlayground", this.view);
        player = new PlayerWalker(this);
        new GameMenu(frame, this);
        new AnimationStepListener(this, player);
        new Controls(this, player, view);
        viewTracker();
        new WalkerAnimationFrames(State.RUN, Walkers.PLAYER);
        populate();
        // end of constructor start of a new world :)
        start();
    }
    // Debug Methods
    protected void debugOn() {
        view.setGridResolution(!debugOn ? 1 : 0);
        for (Body body : getDynamicBodies()) {
            body.setAlwaysOutline(!debugOn);
        }
//        for (Body game.body : getStaticBodies()) {
//            game.body.setAlwaysOutline(!debugOn);
//        }
        debugOn = !debugOn;
    }
    // Population methods
    private void groundImg() {
        for (float i = -500+7; i < 500; i+=12) {
            new Ground(this, new Vec2(7, 7), new Vec2(i, -7)).addImage(new BodyImage("data/ground_tiles/tile_11.png",14f));
        }
    }
    private void groundImg2() {
        for (float i = -500+6.6f; i < 500; i+=12) {
            Ground temp = new Ground(this, new Vec2(6, 2.5f), new Vec2(i, -7.5f));
            temp.addImage(new BodyImage("data/ground_tiles/tile_0x2.png",5f)).setOffset(new Vec2(0, 5f));
        }
    }
    private void populate() {
        groundImg2();
        new Ground(this, new Vec2(500, 2f), new Vec2(0, -2f)); // changed from 2.5 to 2 so that fillColor is not visible
        playGround();
    }
    private void playGround() {
        float offset = 100f;
        new Ground(this, new Vec2(2, 2f), new Vec2(-5+offset, 2f));
        DynamicBody toy = new DynamicBody(this, new DynamicPolygon(new Vec2[]{new Vec2(0, 0), new Vec2(), new Vec2(6, 0), new Vec2(6, 2), new Vec2(4, 4), new Vec2(2, 4), new Vec2(0, 2), new Vec2(0, 0)}));
        toy.setLinearVelocity(new Vec2(offset, 1));
        toy.setName("Toy");
        new Ground.Platform(this, new Vec2(20+offset, 4));
        new Ground.Platform(this, new Vec2(27+offset, 7));
        new Trampoline(this, new Vec2(-20+offset, 1));
        initWizards();
    }

    // Store
    private void initWizards() {
        new WizardWalker(this, new Vec2(80,2));
        new WizardWalker(this, new Vec2(110, 2));
        new WizardWalker(this, new Vec2(-10,2));
        new WizardWalker(this, new Vec2(-30,2));
        new WizardWalker(this, new Vec2(-80,2));
        new WizardWalker(this, new Vec2(-110,2));
    }
    // Method Override
    private void viewTracker() {
        addStepListener(new StepListener() {
            @Override
            public void preStep(StepEvent event) {
//                view.setCentre(new Vec2(player.getPosition().x, player.getPosition().y+10)); // +10 so that the view does not show the void under the ground
                view.setCentre(player.getPosition());
            }
            @Override
            public void postStep(StepEvent event) {
            }
        });
    }
    // Settings' Methods
    public void togglePause() {
        if (isPaused) {
            isPaused = false;
            gameTime.toggleTimer();
            start();
        }
        else {
            // 1 & 2 ensure player does not get stuck in movement since inputs won't be checked during pause
            player.stopWalking(); // 1
            player.setLinearVelocity(new Vec2(0, player.getLinearVelocity().y)); // 2
            isPaused = true;
            gameTime.toggleTimer();
            stop();
        }
    }
    // External Getters & Setters
    public void togglePlayerAttack() {
        player.toggleOffAttack();
    }
    public boolean getPlayerAttack() {
        return player.getAttacking();
    }
    public ArrayList<WizardWalker> getWizards() {
        return wizards;
    }
    public PlayerWalker getPlayer() {
        return player;
    }
    public static void addWizard(WizardWalker wizard) {
        wizards.add(wizard);
    }
    public static void removeWizard(String wizardName) {
        wizards.removeIf(w -> w.getName().equals(wizardName));
    }
}
