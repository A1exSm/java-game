package game.core;
// Imports
import game.Game;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

// Class
public class GameMenu extends MenuBar {
    // Fields
    private final MenuBar gameMenu = new MenuBar();
    private final GameWorld gameWorld;
    // Constructor
    public GameMenu(JFrame frame, GameWorld gameWorld) {
        super();
        this.gameWorld = gameWorld;
        addSettings();
        addPlayerItems();
        frame.setMenuBar(gameMenu);
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        frame.setJMenuBar(menuBar);
        file.setText("File");
        file.add(new JMenuItem("Exit"));


    }
    // Methods
    private void addSettings() {
        Menu settings = new Menu("Settings");
        gameMenu.add(settings);
        createSettingsItems(settings);
    }

    private void addPlayerItems() {
        Menu playerMenu = new Menu("Player");
        gameMenu.add(playerMenu);
        createPlayerItems(playerMenu);
    }

    private void createPlayerItems(Menu playerMenu) {
        MenuItem makeGhostly = new MenuItem("Ghostly", new MenuShortcut(KeyEvent.VK_G, false));
        MenuItem makeSolid = new MenuItem("Solid", new MenuShortcut(KeyEvent.VK_S, false));
        MenuItem location1 = new MenuItem("Location 1", new MenuShortcut(KeyEvent.VK_1, false));
        MenuItem location2 = new MenuItem("Location 2", new MenuShortcut(KeyEvent.VK_2, false));
        playerMenu.add(makeGhostly);
        playerMenu.add(makeSolid);
        playerMenu.add(location1);
        playerMenu.add(location2);
        // Action Listeners
        makeGhostly.setActionCommand("ghostly");
        makeGhostly.addActionListener(e -> {
            if (e.getActionCommand().equals("ghostly")) {
                gameWorld.getPlayer().makePlayerGhostly();
            }
        });
        makeSolid.setActionCommand("solid");
        makeSolid.addActionListener(e -> {
            if (e.getActionCommand().equals("solid")) {
                gameWorld.getPlayer().makePlayerSolid();
            }
        });
        location1.setActionCommand("location1");
        location1.addActionListener(e -> {
            if (e.getActionCommand().equals("location1")) {
                gameWorld.getPlayer().setPosition(new Vec2(0, 2));
            }
        });
        location2.setActionCommand("location2");
        location2.addActionListener(e -> {
            if (e.getActionCommand().equals("location2")) {
                gameWorld.getPlayer().setPosition(new Vec2(-25, 202));
            }
        });
    }

    private void createSettingsItems(Menu settings) {
        MenuItem pause = new MenuItem("Pause", new MenuShortcut(KeyEvent.VK_P, false)); // changes modifier to CTRL
        MenuItem exit = new MenuItem("Exit", new MenuShortcut(KeyEvent.VK_E, false));
        MenuItem DebugToggle = new MenuItem("Debug Toggle", new MenuShortcut(KeyEvent.VK_D, false));
        settings.add(pause);
        settings.add(exit);
        settings.add(DebugToggle);
        addSettingsActionListeners(pause, exit, DebugToggle);
    }

    private void addSettingsActionListeners(MenuItem ... items) { // ... is a varargs parameter, treated like an array :)
        // Pause
        items[0].setActionCommand("pause");
        items[0].addActionListener(i-> {
            if (i.getActionCommand().equals("pause")) {
                gameWorld.togglePause();
                if (gameWorld.isRunning()) items[0].setLabel("Pause");
                else items[0].setLabel("Unpause");
            }
        });
        // Exit
        items[1].setActionCommand("exit");
        items[1].addActionListener(i->{
            if (i.getActionCommand().equals("exit")) {
                Game.exit();
            }
        });

        // Debug Toggle
        items[2].setActionCommand("debug");
        items[2].addActionListener(i->{
            if (i.getActionCommand().equals("debug")) {
                Game.debugOn();
            }
        });
    }
}
