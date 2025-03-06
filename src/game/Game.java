package game;

// Imports
import city.cs.engine.Body;
import city.cs.engine.EngineerView;
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.core.*;
import game.utils.Controls;
import org.jbox2d.common.Vec2;

// Class
public class Game {
    // Fields
    public static GameWorld gameWorld; // allow for game to restart
    private static GameView gameView;
    private static GameFrame frame;
    private static boolean debugOn;
    private boolean isPaused;
    public static GameTime gameTime;

    // Constructor
    public Game(Boolean debugOn) {
        gameWorld = new GameWorld(this);
        gameView = new GameView(gameWorld, 1200, 630);
        if(debugOn) {
            frame = new GameFrame("EngineerView", new EngineerView(gameWorld, 1200, 630));
            Game.debugOn();
        } else {
            frame = new GameFrame("GamePlayground", gameView);
        }
        new GameMenu(frame, gameWorld);
        new Controls(gameWorld, gameWorld.getPlayer(), gameView);
        viewTracker();
//        gameView.setUI(new PanelUI() {
//            @Override
//            public void installUI(JComponent c) {
//                super.installUI(c);
//                c.add(new PopupMenu().show(, 0,0));
//            }
//        });
    }
    // Static | Debug Methods
    public static void debugOn() {
        gameView.setGridResolution(!debugOn ? 1 : 0);
        for (Body body : gameWorld.getDynamicBodies()) {
            body.setAlwaysOutline(!debugOn);
        }
//        for (Body game.body : getStaticBodies()) {
//            game.body.setAlwaysOutline(!debugOn);
//        }
        debugOn = !debugOn;
    }

    // Static | Exit Method
    public static void exit() {
        frame.dispose();
        System.exit(0);
    }

    // Static | frame dimensions
    public static Vec2 getFrameDimensions() {
        return new  Vec2(frame.getWidth(), frame.getHeight());
    }

    // Settings
    public void togglePause() {
        if (isPaused && !gameWorld.getPlayer().isDead()) {
            isPaused = false;
            gameTime.toggleTimer();
            gameWorld.start();
        }
        else if (!isPaused) {
            // 1 & 2 ensure player does not get stuck in movement since inputs won't be checked during pause
            gameWorld.getPlayer().stopWalking(); // 1
            gameWorld.getPlayer().setLinearVelocity(new Vec2(0, gameWorld.getPlayer().getLinearVelocity().y)); // 2
            isPaused = true;
            gameTime.toggleTimer();
            gameWorld.stop();
        }
    }

    // Tracks player to centre GameView.
    private void viewTracker() {
        gameWorld.addStepListener(new StepListener() {
            @Override
            public void preStep(StepEvent event) {
//                view.setCentre(new Vec2(player.getPosition().x, player.getPosition().y+10)); // +10 so that the view does not show the void under the ground
                gameView.setCentre(gameWorld.getPlayer().getPosition());
                if(gameWorld.getPlayer().destroyed) gameView.gameOver();
            }
            @Override
            public void postStep(StepEvent event) {
            }
        });
    }



    public static void main(String[] args) {

        new Game(false);
    }
}
