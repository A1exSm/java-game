package game;
// Imports
import javax.swing.*;
// Class
class GameFrame extends JFrame {
    // Constructor
    protected GameFrame(String title, GameView view) {
        super(title);
        add(view);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setResizable(false);
        setVisible(true);
        pack();
    }
}
