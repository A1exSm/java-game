package game;

// Imports
import city.cs.engine.*;
import game.core.*;
import game.enums.SoundGroups;
import game.utils.Controls;
import game.menu.JMenuPanel;
import org.jbox2d.common.Vec2;

import javax.swing.*;

/**
 * The main class for the game.
 * @author Alexander, Smolowitz, alexander.smolowitz@city.ac.uk
 * @version 1.0
 * @since 2023-10-23
 */
public class Game {
    // Fields
    /**
     * The GameWorld instance in which bodies move and interact.<br>
     * A public static field allowing for non-static methods to be accessed by calling Game.gameWorld.method();
     * @see GameWorld
     */
    public static GameWorld gameWorld;

    /**
     * The GameView instance in which the game is displayed.<br>
     * A public static field allowing for non-static methods to be accessed by calling Game.gameView.method();<br>
     * Currently used by {@link game.menu.GameJMenuBar GameJMenuBar} to access {@link GameView#jMenuPanel} and call {@link JMenuPanel#toggleMenu()}.
     * @see GameView
     * @see game.menu.GameJMenuBar
     * @see JMenuPanel#toggleMenu()
     */
    public static GameView gameView;

    /**
     * A private instance of GameFrame.<br>
     * Static field allowing for the frame to be disposed in {@link #exit()}.
     * @see GameFrame
     */
    private static GameFrame frame;

    /**
     * A static field used to hold the state of debug mode.<br>
     * Allows for the debug mode to be toggled in {@link #debugOn()}.
     * @see #debugOn()
     */
    private static boolean debugOn;

    /**
     * A private boolean field used to hold the state of the game pause.<br>
     * Allows for the game to be toggled between paused and resumed using {@link #togglePause()}.
     * @see #togglePause()
     */
    private boolean isPaused;

    /**
     * A static instance of {@link GameTime}.<br>
     * Allows for the game time to act accordingly when {@link #togglePause()} is called.
     * @see #togglePause()
     */
    public static GameTime gameTime;

    /**
     * A static instance of {@link GameSound} which stores the main game soundtrack.
     * @see GameSound
     */
    private static GameSound gameMusic = GameSound.createSound("data/Audio/Music/time_for_adventure.wav", SoundGroups.MUSIC, true);

    /**
     * The Constructor.<br>
     * Initialises {@link Game#gameWorld}, {@link Game#gameView} and {@link Game#frame}.<br>
     * Creates a new instance of {@link Controls}.<br>
     * Calls {@link #viewTracker()}
     * @param debugOn a value of {@code true} starts the game with debug mode on.
     */
    public Game(Boolean debugOn) {
        frame = new GameFrame(this);
        frame.switchLayout("MainMenu");
    }
    /**
     * Starts the game.<br>
     * Initialises {@link Game#gameWorld} and {@link Game#gameView}.<br>
     * Creates a new instance of {@link Controls}.<br>
     * Calls {@link #viewTracker()}
     */
    public void startGame(String level) {
        gameWorld = new GameWorld(this, level);
        gameView = new GameView(gameWorld, 1200, 630);
        new Controls(gameWorld, gameWorld.getPlayer(), gameView);
        viewTracker();
    }
    // Static | Debug Methods
    /**
     * Toggles the debug mode on and off.<br>
     * When debug mode is on, the grid resolution is set to 1, and all bodies are outlined.<br>
     * When debug mode is off, the grid resolution is set to 0, and all bodies have setAlwaysOutline(false).
     */
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
    /**
     * Exits the game after confirming with the user.<br>
     * Displays a confirmation dialogue and disposes the frame if the user confirms.
     *
     */
    public void exitWindow() {
        int answer = JOptionPane.showConfirmDialog(Game.gameView, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            frame.dispose();
            System.exit(0);
        }
    }
    /**
     * Exits the game to menu after confirming with the user.
     * @return true if the user confirms the exit, false otherwise.
     */
    public static boolean exit() {
        int answer = JOptionPane.showConfirmDialog(Game.gameView, "Are you sure you want to quit to the main menu?", "Quit", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            gameWorld.stop();
            gameView = null;
            gameWorld = null;
            gameTime = null;
            frame.switchLayout("MainMenu");
            return true;
        }
        return false;
    }

    // Public | Music | Getter
    /**
     * Gets the main soundtrack field.
     * @return the game music.
     */
    public static GameSound getGameMusic() {
        return gameMusic;
    }

    // Static | frame dimensions
    /**
     * Gets the dimensions of the frame.
     * @return a Vec2 object containing the width and height of the frame.
     */
    public static Vec2 getFrameDimensions() {
        return new  Vec2(frame.getWidth(), frame.getHeight());
    }

    // Settings
    /**
     * Toggles the game pause state.<br>
     * Pauses the game if it is running, and resumes the game if it is paused.<br>
     * Handles all necessary timers and interfaces.
     */
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

    /**
     * Adds a step listener to the game world to centre the player and check for game over.<br>
     * create a {@link StepListener} which centres the view position at the player's position.
     */
    private void viewTracker() {
        gameWorld.addStepListener(new StepListener() {
            @Override
            public void preStep(StepEvent event) {
//                view.setCentre(new Vec2(player.getPosition().x, player.getPosition().y+10)); // +10 so that the view does not show the void under the ground
                Vec2 playerPos = gameWorld.getPlayer().getPosition();
                gameView.setCentre(new Vec2(playerPos.x, playerPos.y + 2));
                if(gameWorld.getPlayer().destroyed) {gameView.gameOver();}
            }
            @Override
            public void postStep(StepEvent event) {
            }
        });
    }

    /**
     * Scales the given dimensions to fit within the maximum dimensions while maintaining the aspect ratio.
     * @param width the original width.
     * @param height the original height.
     * @param maxWidth the maximum width.
     * @param maxHeight the maximum height.
     * @return an array containing the scaled width and height.
     */
    public static int[] getScaledDimensions(int width, int height, int maxWidth, int maxHeight) {
        int scaleWidth = maxWidth / width;
        int scaleHeight = maxHeight / height;
        int scaleFactor = Math.max(1, Math.min(scaleWidth, scaleHeight));
        int newWidth = width * scaleFactor;
        int newHeight = height * scaleFactor;
        return new int[]{newWidth, newHeight};
    }
    /**
     * Gets the frame field.
     * @return the frame.
     */
    public GameFrame getFrame() {
        return frame;
    }

    /**
     * The main method to start the game.
     * @param args ...
     */
    public static void main(String[] args) {
        new Game(false);
    }
}
