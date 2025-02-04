package game;

import javax.swing.*;

class GameFrame extends JFrame {
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
