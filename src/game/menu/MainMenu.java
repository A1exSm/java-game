package game.menu;
// Imports

import game.Game;
import game.enums.Environments;

import javax.swing.*;

/**
 *
 */
// Class
public class MainMenu { // Unfortunately, most of my Menus were created before I knew about this amazing GUI builder :(
    // Fields
    private JPanel menuPanel;
    private JPanel innerPanel;
    private JButton startButton;
    private JButton levelSelectButton;
    private JButton optionsButton;
    private JPanel startPanel;
    private JPanel levelPanel;
    private JPanel optionsPanel;
    private JPanel quitPanel;
    private JButton quitButton;

    // Constructor
    public MainMenu(Game game) {
        startButton.addActionListener(e -> {
//            game.getFrame().switchLayout(Environments.MAGIC_CLIFF);
        });
        quitButton.addActionListener(e -> {
            game.exitWindow();
        });
        levelSelectButton.addActionListener(e -> {
            game.getFrame().switchLayout(Environments.Level_Select);
        });
        optionsButton.addActionListener(e -> {;
//            game.getFrame().switchLayout(Environments.Options); // not implemented yet
        });
    }
    // Methods
    public JPanel getMenuPanel() {
        return menuPanel;
    }
}