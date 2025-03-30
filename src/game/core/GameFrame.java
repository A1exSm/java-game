package game.core;

import city.cs.engine.EngineerView;
import game.Game;
import game.utils.menu.GameJMenuBar;

import javax.swing.*;
import java.awt.*;

/**
 * The `GameFrame` class extends `JFrame` and represents the main window for the game.<br>
 * It provides constructors to initialise the frame with different types of views and
 * sets up the frame with common properties.
 */
public class GameFrame extends JFrame {
    private final Game game;
    private GameView userView;
    private EngineerView engineerView;
    /**
     * Constructs a `GameFrame`
     *
     */
    public GameFrame(Game game) {
        super("Slasher");
        this.game = game;
        setup();
    }

    /**
     * Sets up the frame with common properties such as default close operation,
     * location by platform, non-resizable, menu bar, visibility, and then packs them.
     */
    private void setup() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setResizable(false);
        setMinimumSize(new Dimension(1200, 694));
        pack();
        setVisible(true);
    }
    /**
     * Switches the layout of the frame to the specified layout.
     *
     * @param Layout the layout to switch to
     */
    public void switchLayout(String Layout) {
        switch(Layout) {
            case "MainMenu" -> {
                getContentPane().removeAll();
                setJMenuBar(null);
                add(new MainMenu(game).getMenuPanel());
                revalidateFrame();
            }
            case "Game" -> {addGameView();}
            default -> {throw new IllegalArgumentException("Error: Invalid layout specified.");}
        }
    }
    /**
     * Adds the game view to the frame.
     */
    private void addGameView() {
        // check for existing view
        for (Component component : getContentPane().getComponents()) {
            if (component instanceof GameView) {
                throw new IllegalStateException("Error: Game view component is already present.");
            }
        }
        getContentPane().removeAll();
        game.startGame();
        userView = Game.gameView;
        add(userView);
        userView.setName("View");
        userView.setBounds(0, 0, 1200, 630);
        setJMenuBar(new GameJMenuBar());
        userView.requestFocus();
        revalidateFrame();
    }
    /**
     * revalidates the frame and packs it.
     */
    private void revalidateFrame() {
        revalidate();
        pack();
    }
}
