package game.utils;
// Imports

import game.Game;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
// Class
public class ToMenuButton extends JButton {
    // Fields

    // Constructor
    public ToMenuButton(String winLoss) { // 51*5
        super("Back to Menu");
        setBackground(new Color(94, 43, 48, 0));
        setSize(getPreferredSize().width*2, getPreferredSize().height*2);
        setFocusable(false);
        setRolloverEnabled(false);
        addActionListener(e->{
            Game.gameWorld.environment.stop(winLoss);
            Game.exitToMainMenu();
        });
    }

    // Methods
}
