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
//            game.getFrame().switchLayout(Environments.MAGIC_CLIFF);
            Console.debug(levelSelectButton.getBackground().toString());
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
    }
    // Methods
    public JPanel getMenuPanel() {
        return menuPanel;
    }
}