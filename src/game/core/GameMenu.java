package game.core;
// Imports
import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

// Class
public class GameMenu extends MenuBar {
    // Fields
    private final MenuBar gameMenu = new MenuBar();
    private final GameWorld gameWorld;
    private Menu settings;
    private MenuItem pause;
//    private MenuItem restart;
    private MenuItem exit;
    private MenuItem DebugToggle;
    // Constructor
    public GameMenu(JFrame frame, GameWorld gameWorld) {
        super();
        this.gameWorld = gameWorld;
        addSettings();
        frame.setMenuBar(gameMenu);
    }
    // Methods
    private void addSettings() {
        settings = new Menu("Settings");
        gameMenu.add(settings);
        createSettingsItems();
    }

    private void createSettingsItems() {
        pause = new MenuItem("Pause", new MenuShortcut(KeyEvent.VK_P, false)); // changes modifier to CTRL
//        restart = new MenuItem("Restart Game", new MenuShortcut(KeyEvent.VK_R, false));
        exit = new MenuItem("Exit", new MenuShortcut(KeyEvent.VK_E, false));
        DebugToggle = new MenuItem("Debug Toggle", new MenuShortcut(KeyEvent.VK_D, false));
        settings.add(pause);
//        settings.add(restart);
        settings.add(exit);
        settings.add(DebugToggle);
        addSettingsActionListeners();
    }

    private void addSettingsActionListeners() {
        // Pause
        pause.setActionCommand("pause");
        pause.addActionListener(i->{
            if (i.getActionCommand().equals("pause")) {
                gameWorld.togglePause();
                if (gameWorld.isRunning()) pause.setLabel("Pause");
                else pause.setLabel("Unpause");
            }
        });
//        // Reset
//        restart.setActionCommand("restart");
//        restart.addActionListener(i->{
//            if (i.getActionCommand().equals("restart")) {
//                Game.restart();
//            }
//        });
        // Exit
        exit.setActionCommand("exit");
        exit.addActionListener(i->{
            if (i.getActionCommand().equals("exit")) {
                Game.exit();
            }
        });

        // Debug Toggle
        DebugToggle.setActionCommand("debug");
        DebugToggle.addActionListener(i->{
            if (i.getActionCommand().equals("debug")) {
                Game.debugOn();
            }
        });
    }
}
