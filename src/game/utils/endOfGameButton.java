package game.utils;
// Imports

import game.Game;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
// Class
public class endOfGameButton extends JButton {
    // Fields

    // Constructor
    public endOfGameButton() { // 51*5
        super("Back to Menu");
        setBackground(new Color(94, 43, 48, 0));
        setBounds(520, 280, 200, 50);
        setFocusable(false);
        setRolloverEnabled(false);
        addActionListener(e->{
            Game.gameWorld.environment.stop();
            Game.exitToMainMenu();
        });
    }

    // Methods
}
