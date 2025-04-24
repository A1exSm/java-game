package game.menu;
// Imports

import game.Game;
import game.core.console.Console;
import game.enums.Environments;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
// Class
public class MainMenu { // Unfortunately, most of my Menus were created before I knew about this amazing GUI builder :(
    // Fields
    private JPanel menuPanel;
    private JPanel innerPanel;
    private JButton continueButton;
    private JButton levelSelectButton;
    private JButton optionsButton;
    private JPanel continuePanel;
    private JPanel levelPanel;
    private JPanel optionsPanel;
    private JPanel quitPanel;
    private JButton quitButton;

    // Constructor
    public MainMenu(Game game) {
        continueButton.addActionListener(e -> {
            continueGame(game);
        });
        quitButton.addActionListener(e -> {
            game.exitWindow();
        });
        levelSelectButton.addActionListener(e -> {
            game.getFrame().switchLayout(Environments.LEVEL_SELECT);
        });
        optionsButton.addActionListener(e -> {;
            game.getFrame().switchLayout(Environments.OPTIONS);
        });
        /*
        for no apparent reason when running on my Mac instead of windows, the opaque check box is ignored :/ we set re-update it here.
        If we check whether isOpaque is true, it is true, but the properties of opaque are not applied, thus we re-update it here to fix things.
         */
        if (System.getProperty("os.name").contains("Mac")) {
            continueButton.setOpaque(true);
            levelSelectButton.setOpaque(true);
            optionsButton.setOpaque(true);
            quitButton.setOpaque(true);
        }
        Options.addSounds(menuPanel);
    }

    private void continueGame(Game game) {
        // 10,000 = move to nextLevel, 0 = none unlocked
        if (Game.magicData.getHighestUnlocked() != 0 && Game.magicData.getHighestUnlocked() != 10000) {
            if (Game.magicData.getHighestUnlocked() == -1) {
                game.getFrame().selectLevel(Environments.MAGIC_CLIFF, 2);
            } else {
                game.getFrame().selectLevel(Environments.MAGIC_CLIFF, 1);
            }
        } else if (Game.hauntedData.getHighestUnlocked() != 0 && Game.hauntedData.getHighestUnlocked() != 10000) {
            if (Game.hauntedData.getHighestUnlocked() == -1) {
                game.getFrame().selectLevel(Environments.HAUNTED_FOREST, 2);
            } else {
                game.getFrame().selectLevel(Environments.HAUNTED_FOREST, 1);
            }
        } else { // since its last level if 0, we assume the user has completed the game
            if (Game.gothicData.getHighestUnlocked() == -1) {
                game.getFrame().selectLevel(Environments.GOTHIC_CEMETERY, 2);
            } else {
                game.getFrame().selectLevel(Environments.GOTHIC_CEMETERY, 1);
            }
        }
    }
    // Methods
    public JPanel getMenuPanel() {
        return menuPanel;
    }
}