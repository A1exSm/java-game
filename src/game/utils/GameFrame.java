package game.utils;
// Imports
import javax.swing.*;
// Class
public class GameFrame extends JFrame {
    // Constructor
    public GameFrame(String title, GameView view) {
        super(title);
        add(view);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        pack();
    }
}
