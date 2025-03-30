package game.core;
// Imports

import game.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
// Class
public class MainMenu { // Unfortunately, most of my Menus were created before I knew about this amazing GUI builder :(
    // Fields
    private final Game game;
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
        this.game = game;
        startButton.addActionListener(e -> {
            game.getFrame().switchLayout("Game");
        });
        quitButton.addActionListener(e -> {
            game.exitWindow();
        });
    }
    // Methods
    public JPanel getMenuPanel() {
        return menuPanel;
    }
}