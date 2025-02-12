package game.utils;
// Imports
import game.GameWorld;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
// Class
public class GameMenu extends MenuBar {
    // Fields
    private final MenuBar gameMenu = new MenuBar();
    // Constructor
    public GameMenu(JFrame frame, GameWorld gameWorld) {
        super();
        addSettings(gameWorld);
        frame.setMenuBar(gameMenu);
    }
    // Methods
    private void addSettings(GameWorld gameWorld) {
        Menu settings = new Menu("Settings");
        gameMenu.add(settings);
        MenuItem pause = new MenuItem("Pause", new MenuShortcut(KeyEvent.VK_P, false));
        settings.add(pause);
        pause.setActionCommand("pause");
        pause.addActionListener(i->{
            if (i.getActionCommand().equals("pause")) {
                gameWorld.togglePause();
                if (gameWorld.isRunning()) pause.setLabel("Pause");
                else pause.setLabel("Unpause");
            }
        });
    }
}
