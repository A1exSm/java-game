package game;

// Imports
import city.cs.engine.*;
import game.core.*;
import game.utils.Controls;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

// Class
public class Game {
    // Fields
    public static GameWorld gameWorld; // allow for game to restart
    public static GameView gameView; // used for notifications, for now, thus will be public
    private static GameFrame frame;
    private static boolean debugOn;
    private boolean isPaused;
    public static GameTime gameTime;
    private static final GameSound gameMusic = GameSound.createSound("data/Audio/Music/time_for_adventure.wav", true);

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
        new Controls(gameWorld, gameWorld.getPlayer(), gameView);
        viewTracker();
    }
    // Static | Debug Methods
    public static void debugOn() {
        gameView.setGridResolution(!debugOn ? 1 : 0);
        for (Body body : gameWorld.getDynamicBodies()) {
            body.setAlwaysOutline(!debugOn);
        }
        for (Body body : gameWorld.getStaticBodies()) {
            body.setAlwaysOutline(!debugOn);
        }
        debugOn = !debugOn;
    }

    // Static | Exit Method
    public static boolean exit() {
        int answer = JOptionPane.showConfirmDialog(Game.gameView, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            frame.dispose();
            System.exit(0);
            return true;
        }
        return false;


    }
    // Public | Music
    public static GameSound getGameMusic() {
        return gameMusic;
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
            gameView.resumeInterface();
            gameWorld.start();
        }
        else if (!isPaused) {
            // 1 & 2 ensure player does not get stuck in movement since inputs won't be checked during pause
            gameWorld.getPlayer().stopWalking(); // 1
            gameWorld.getPlayer().setLinearVelocity(new Vec2(0, gameWorld.getPlayer().getLinearVelocity().y)); // 2
            isPaused = true;
            gameTime.toggleTimer();
            gameView.pauseInterface();
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
                if(gameWorld.getPlayer().destroyed) {gameView.gameOver();}
            }
            @Override
            public void postStep(StepEvent event) {
            }
        });
    }

    public static int[] getScaledDimensions(int width, int height, int maxWidth, int maxHeight) { // This is my tailored version of get scaled instance
        int scaleWidth = maxWidth / width;
        int scaleHeight = maxHeight / height;
        int scaleFactor = Math.max(1, Math.min(scaleWidth, scaleHeight));
        int newWidth = width * scaleFactor;
        int newHeight = height * scaleFactor;
        return new int[]{newWidth, newHeight};
    }




    public static void main(String[] args) {

        new Game(false);
    }
}
