package game.core;

import city.cs.engine.EngineerView;
import game.Game;
import game.core.console.Console;
import game.enums.Environments;
import game.levels.MagicCliff;
import game.menu.GameJMenuBar;
import game.menu.MainMenu;
import game.menu.Options;
import game.menu.SelectLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;

/**
 * The `GameFrame` class extends `JFrame` and represents the main window for the game.<br>
 * It provides constructors to initialise the frame with different types of views and
 * sets up the frame with common properties.
 */
public class GameFrame extends JFrame {
    private final Game game;
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
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_F1) {
                    if (Game.gameView == null) {
                        Console.toggleConsole();
                    }
                }
            }
        });
    }
    /**
     * Switches the layout of the frame to the specified layout.
     *
     * @param Layout the layout to switch to
     */
    public void switchLayout(Environments Layout) {
        switch(Layout) {
            case Main_Menu -> {
                newLayout(new MainMenu(game).getMenuPanel());
            }
            case LEVEL_SELECT -> {
                newLayout(new SelectLevel(game).getPanel());
            }
            case OPTIONS -> {
                newLayout(new Options(game).getOptionsPanel());
            }
            default -> {Console.error("GameFrame.switchLayout() called with invalid environment, if attempting to start a level, use the GameFrame.selectLevel() method");}
        }
    }

    private void newLayout(JPanel panel) {
        getContentPane().removeAll();
        if (getMenuBar() != null) {
            setJMenuBar(null);
        }
        add(panel);
        revalidateFrame();
    }

    /**
     * Selects the level to play.
     *
     * @param environment the environment of the level
     * @param level       the level number
     */
    public void selectLevel(Environments environment, int level) {
        addGameView(environment, level);
    }
    /**
     * Adds the game view to the frame.
     */
    private void addGameView(Environments environment, int level) {
        getContentPane().removeAll();
        game.startGame(environment, level);
        GameView userView = Game.gameView;
        add(userView);
        userView.setName("View");
        userView.setBounds(0, 0, 1200, 630);
        if (Game.isDebugOn()) {
            setJMenuBar(new GameJMenuBar());
        }
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
