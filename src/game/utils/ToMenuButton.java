package game.utils;
// Imports
import game.Game;
import javax.swing.*;
import java.awt.*;
/**
 * Takes the player back to the main menu
 * after winning or losing the game.
 */
// Class
public class ToMenuButton extends JButton {
    // Constructor
    /**
     * Creates a new ToMenuButton instance.
     * Ensures the preferred size is twice the size of the button.
     * Adds the necessary listeners and sets the background colour.
     */
    public ToMenuButton() {
        super("Back to Menu");
        setBackground(new Color(94, 43, 48, 0));
        setSize(getPreferredSize().width*2, getPreferredSize().height*2);
        setFocusable(false);
        setRolloverEnabled(false);
        addActionListener(e->{
            Game.gameWorld.getLevel().stop();
            Game.switchToMainMenu();
        });
    }
}
