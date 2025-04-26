package game;

// Imports
import city.cs.engine.*;
import game.core.*;
import game.core.console.Console;
import game.enums.Environments;
import game.enums.SoundGroups;
import game.levels.LevelData;
import game.utils.Controls;
import game.menu.JMenuPanel;
import org.jbox2d.common.Vec2;
import javax.swing.*;
import java.awt.*;

/**
 * The main class for the game.<br><br>
 * As the entry point, it sets up the game world, view, and handles game state management.
 * <ul>
 *     <li>Used for static access to the instances of {@link GameWorld}, {@link GameView} and {@link LevelData} </li>
 *     <li>Handles switching between different levels/interfaces</li>
 * </ul>
 * @author Alexander, Smolowitz, alexander.smolowitz@city.ac.uk
 * @version 1.0
 * @since 2023-10-23
 */
public final class Game {
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
     * * @see GameView
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
    private static boolean debugOn = false;
    /**
     * A private boolean field used to hold the state of the game pause.<br>
     * Allows for the game to be toggled between paused and resumed using {@link #togglePause()}.
     * @see #togglePause()
     */
    private boolean isPaused;
    /**
     * A private instance of {@link Controls} which handles the controls for the game.
     * @see Controls
     */
    private Controls controls;
    /**
     * A static instance of {@link GameTime}.<br>
     * Allows for the game time to act accordingly when {@link #togglePause()} is called.
     * @see #togglePause()
     */
    public static GameTime gameTime;
    /**
     * A static instance of {@link GameSound} which stores the main game soundtrack.
     * Soundtrack from <a href='https://brackeysgames.itch.io/brackeys-platformer-bundle'>Brackeys' Platformer Bundle</a>.<br>
     * @see GameSound
     */
    private static final GameSound gameMusic = GameSound.createSound("data/Audio/Music/time_for_adventure.wav", SoundGroups.MUSIC, true);
    /**
     * A static instance of {@link LevelData} which stores the level data for {@link Environments#MAGIC_CLIFF}.
     * @see LevelData
     */
    public static LevelData magicData = new LevelData(Environments.MAGIC_CLIFF);
    /**
     * A static instance of {@link LevelData} which stores the level data for {@link Environments#HAUNTED_FOREST}.
     * @see LevelData
     */
    public static LevelData hauntedData = new LevelData(Environments.HAUNTED_FOREST);
    /**
     * A static instance of {@link LevelData} which stores the level data for {@link Environments#GOTHIC_CEMETERY}.
     * @see LevelData
     */
    public static LevelData gothicData = new LevelData(Environments.GOTHIC_CEMETERY);
    /**
     * Initialises the {@link GameFrame game frame} and sets the layout to {@link Environments#Main_Menu}.
     */
    public Game() {
        Console.log("Starting...");
        Console.log("OS: " + System.getProperty("os.name"));
        frame = new GameFrame(this);
        frame.switchLayout(Environments.Main_Menu);
    }
    /**
     * Initialises a new game world and view with the specified environment and level.<br>
     * Adds controls to the game world and sets up the view tracker.
     * @param environment the environment of the level
     * @param level the level number
     * @see GameWorld
     * @see GameView
     */
    public void startGame(Environments environment, int level) {
        gameWorld = new GameWorld(this, environment, level);
        gameView = new GameView(gameWorld, environment);
        controls = new Controls(gameWorld, gameWorld.getPlayer(), gameView);
        viewTracker();
    }
    // Static | Debug Methods
    /**
     * Toggles the debug mode {@code on} and {@code off}.<br>
     * When debug mode is {@code on}, the grid resolution is set to 1,
     * +and all bodies are outlined.<br>
     * When debug mode is {@code off}, the grid resolution is set to 0,
     * and all bodies have {@link Body#setAlwaysOutline(boolean) setAlwaysOutline(false)}.
     * @see Body
     * @see GameView
     */
    public static void debugOn() {
        if (gameWorld != null) {
            debugOn = !debugOn;
            gameView.setGridResolution(debugOn ? 1 : 0); // if debugOn is true, set grid resolution to 1, else set it to 0
            // setting the outline & outline colour of bodies
            for (Body body : gameWorld.getDynamicBodies()) {
                body.setAlwaysOutline(debugOn);
            }
            for (Body body : gameWorld.getStaticBodies()) {
                body.setLineColor(getColor(debugOn));
                body.setAlwaysOutline(debugOn);
            }
        }
    }
    // Static | Exit Method
    /**
     * Exits the game after confirming with the user.<br>
     * Displays a confirmation dialogue and disposes the frame if the user confirms.<br>
     * The JFrame has it defaultCloseOperation set to JFrame.EXIT_ON_CLOSE,
     * meaning that if a Console window is open, it will also be closed.
     * @see GameFrame
     * @see Console
     */
    public void exitWindow() {
        int answer = JOptionPane.showConfirmDialog(Game.gameView, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            Console.log("Closing game...");
            frame.dispose(); // releases all resources & JComponents associated with the JFrame
            System.exit(0); // terminates the JVM
        }
    }
    /**
     * Exits the game to the {@link Environments#Main_Menu MainMenu},
     * using {@link GameFrame#switchLayout(Environments)},
     *  after confirming with the user, using a {@link JOptionPane}.
     * @return {@code true} if the user confirms the exit, {@code false} otherwise.
     */
    public static boolean exit() {
        int answer = JOptionPane.showConfirmDialog(Game.gameView, "Are you sure you want to exit to the main menu?", "Quit", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            if (gameWorld != null) {
                switchToMainMenu();
                return true;
            }
            frame.switchLayout(Environments.Main_Menu);
            return true;
        }
        return false;
    }
    /**
     * Switches the game to the main menu layout.<br>
     * Disposes of the game world and view.<br>
     * Used when {@link #gameWorld} is running.
     * @see GameFrame#switchLayout(Environments)
     */
    public static void switchToMainMenu() {
        dispose();
        frame.switchLayout(Environments.Main_Menu);
    }
    /**
     * Stops the game world then sets the
     * gameView, gameWorld, and gameTime to null.
     */
    private static void dispose() {
        gameWorld.stop();
        gameView = null;
        gameWorld = null;
        gameTime = null;
    }
    /**
     * Exits the game to the main menu layout.<br>
     * Disposes of the game world and view.<br>
     * Used when {@link #gameWorld} is {@code null}.
     * @see GameFrame#switchLayout(Environments)
     * @throws IllegalStateException if {@link #gameWorld} is not {@code null}.
     */
    public static void exitToMainMenu() {
        if (gameWorld != null) {
            throw new IllegalStateException(Console.exceptionMessage("GameWorld is not null, call Game.SwitchToMainMenu() instead"));
        }
        frame.switchLayout(Environments.Main_Menu);
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
            controls.setDisable(false);
        }
        else if (!isPaused) {
            // 1 & 2 ensure player does not get stuck in movement since inputs won't be checked during pause
            gameWorld.getPlayer().stopWalking(); // 1
            gameWorld.getPlayer().setLinearVelocity(new Vec2(0, gameWorld.getPlayer().getLinearVelocity().y)); // 2
            isPaused = true;
            gameTime.toggleTimer();
            gameView.pauseInterface();
            gameWorld.stop();
            controls.setDisable(true);
        }
    }
    /**
     * Adds a step listener to the game world to centre the player and check for game over.<br>
     * create a {@link StepListener} which centres the view position at the player's position.
     */
    private void viewTracker() {
        // initial centre position
        Vec2 playerPos = gameWorld.getPlayer().getPosition();
        gameView.setCentre(new Vec2(playerPos.x, playerPos.y + 2));
        // add a step listener to the game world
        gameWorld.addStepListener(new StepListener() {
            @Override
            public void preStep(StepEvent event) {
            }
            @Override
            public void postStep(StepEvent event) {
                // centre the view at the player's position
                Vec2 playerPos = gameWorld.getPlayer().getPosition();
                gameView.setCentre(new Vec2(playerPos.x, playerPos.y + 2));
                // check if the player is outOfBounds
                if (gameWorld.getLevel().isOutOfBounds(gameWorld.getPlayer())) { gameWorld.getLevel().playerOutOfBounds(); }
            }
        });
    }
    /**
     * <ul>
     *     <li>Saves the current level data to the given backup path.</li>
     *     <li>Constructs new level data using the given path</li>
     *     <li>Saves new level data to default save location</li>
     * </ul>
     *
     * @param path the path to the new level data file.
     * @param backupPath the path to the backup level data file.
     * @see LevelData
     */
    public static void selectNewLevelData(String path, String backupPath) {
        LevelData.saveLevelDataWithPath(backupPath);
        magicData = new LevelData(Environments.MAGIC_CLIFF, path);
        hauntedData = new LevelData(Environments.HAUNTED_FOREST, path);
        gothicData = new LevelData(Environments.GOTHIC_CEMETERY, path);
        LevelData.saveLevelData();
    }
    /**
     * main
     * @param args args
     */
    public static void main(String[] args) {
        new Game();
    }
    // Public | Getters | Static
    /**
     * Returns {@code true} if debug mode is on, {@code false} otherwise.
     * @return {@code boolean} representing whether debug mode is on or off.
     */
    public static boolean isDebugOn() {
        return debugOn;
    }
    /**
     * Gets the main soundtrack field.
     * @return the game music.
     */
    public static GameSound getGameMusic() {
        return gameMusic;
    }
    /**
     * Gets the dimensions of the frame.
     * @return the width and height of the frame.
     * @see Vec2
     */
    public static Vec2 getFrameDimensions() {
        return new  Vec2(frame.getWidth(), frame.getHeight());
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
     * Gets the colour for the debug mode.<br>
     * If debug mode is on, returns {@link Color#CYAN}, otherwise returns a transparent colour.
     * @param debugOn the state of debug mode
     * @return the colour for the debug mode
     */
    public static Color getColor(boolean debugOn) {
        if (debugOn) {
            return Color.CYAN; // Very visible colour for debugOn == true
        } else {
            return new Color(0, 0, 0, 0); // alpha = 0 is what makes it transparent
        }
    }
    // Public | Getters
    /**
     * Gets the frame field.
     * @return the frame.
     */
    public GameFrame getFrame() {
        return frame;
    }
}