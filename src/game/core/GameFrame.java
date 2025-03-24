package game.core;

import city.cs.engine.EngineerView;
import game.utils.menu.GameJMenuBar;

import javax.swing.*;

/**
 * The `GameFrame` class extends `JFrame` and represents the main window for the game.<br>
 * It provides constructors to initialise the frame with different types of views and
 * sets up the frame with common properties.
 */
public class GameFrame extends JFrame {

    /**
     * Constructs a `GameFrame` with the specified title and user view.
     *
     * @param title the title of the frame
     * @param userView the user view to be added to the frame
     * @see GameView
     */
    public GameFrame(String title, GameView userView) {
        super(title);
        add(userView);
        setup();
    }

    /**
     * Constructs a `GameFrame` with the specified title and engineer view.
     *
     * @param title the title of the frame
     * @param engineerView the engineer view to be added to the frame
     */
    public GameFrame(String title, EngineerView engineerView) {
        super(title);
        add(engineerView);
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
        setJMenuBar(new GameJMenuBar());
        setVisible(true);
        pack();
    }
}