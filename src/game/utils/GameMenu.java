package game.utils;
// Imports
import game.Game;
import game.GameWorld;
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
    private MenuItem restart;
    private MenuItem exit;
    // Constructor
    public GameMenu(JFrame frame, GameWorld gameWorld) {
        super();
        this.gameWorld = gameWorld;
        addSettings();
        frame.setMenuBar(gameMenu);
    }
    // Methods
    private void addSettings() {
        Menu settings = new Menu("Settings");
        gameMenu.add(settings);
        createSettingsItems(settings);
    }

    private void createSettingsItems(Menu settings) {
        pause = new MenuItem("Pause", new MenuShortcut(KeyEvent.VK_P, false));
//        restart = new MenuItem("Restart Game", new MenuShortcut(KeyEvent.VK_R, false));
        exit = new MenuItem("Exit", new MenuShortcut(KeyEvent.VK_E, false));
        settings.add(pause);
//        settings.add(restart);
        settings.add(exit);
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
    }
}
